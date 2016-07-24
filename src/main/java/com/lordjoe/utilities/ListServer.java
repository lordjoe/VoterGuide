package com.lordjoe.utilities;

import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;


/**
 * See  http://www.javaworld.com/javaworld/jw-06-1999/jw-06-javamail-p2.html
 * This class provides basic List Server type capabilities, that is, it retrieves emails for a specific user
 * (e.g. listserv@host.com) broadcasts it to all email ids provided in the emailListFile.  The emailList file
 * contains one valid email id (user@host.com) per line.
 *
 * @author  Anil Hemrajani
 * @author  Tony Nemil
 */
public class ListServer
{
    private static final String INBOX = "INBOX", POP_MAIL="pop3", SMTP_MAIL="smtp";
    private boolean debugOn = false;
    private String _smtpHost = null,
                   _pop3Host = null,
                   _user = null,
                   _password = null,
                   _emailListFile = null,
                   _fromName = null;
    private InternetAddress[] _toList = null;


    /**  main() is used to start an instance of the ListServer
    */
    public static void main(String args[])
                       throws Exception
    {
	//  check usage
	//
        if (args.length < 6)
        {
            System.err.println("Usage: java ListServer SMTPHost POP3Host user password EmailListFile CheckPeriodFromName");
            System.exit(1);
        }

        //  Assign command line arguments to meaningful variable names
	//
        String smtpHost = args[0],
               pop3Host = args[1],
               user = args[2],
               password = args[3],
               emailListFile=args[4],
               fromName = null;

        int checkPeriod = Integer.parseInt(args[5]);

        if (args.length > 6)
            fromName = args[6];

        // Process every "checkPeriod" minutes
	//
        ListServer ls = new ListServer();
        ls.setDebug(false);

        while (true)
        {
            ls.debugMsg("SESSION START");
            ls.process(smtpHost, pop3Host, user, password, emailListFile, fromName);
            ls.debugMsg("SESSION END (Going to sleep for " + checkPeriod + " minutes)");
            Thread.sleep(checkPeriod*1000*60);
        }
    }


    /**  process() checks for new messages and calls processMsg() for every new message
    */
    public void process(String smtpHost, String pop3Host, String user, String password, String emailListFile, String fromName)
           throws Exception
    {
        _smtpHost = smtpHost;
        _pop3Host = pop3Host;
        _user = user;
        _password = password;
        _emailListFile = emailListFile;
        if (fromName != null)
           _fromName = fromName;

        // Read in email list file into java.util.Vector
        //
        Vector vList = new Vector(10);
        BufferedReader listFile = new BufferedReader(new FileReader(emailListFile));
        String line = null;
        while ((line = listFile.readLine()) != null)
        {
           vList.addElement(new InternetAddress(line));
        }
        listFile.close();
        debugMsg("Found " + vList.size() + " email ids in list");

        _toList = new InternetAddress[vList.size()];
        vList.copyInto(_toList);
        vList = null;

	//
        // Get individual emails and broadcast them to all email ids
	//

        // Get a Session object
	//
        Properties sysProperties = System.getProperties();
        Session session = Session.getDefaultInstance(sysProperties, null);
        session.setDebug(debugOn);

        // Connect to host
	//
        Store store = session.getStore(POP_MAIL);
        store.connect(pop3Host, -1, _user, _password);


        // Open the default folder
	//
        Folder folder = store.getDefaultFolder();
        if (folder == null)
            throw new NullPointerException("No default mail folder");

        folder = folder.getFolder(INBOX);
        if (folder == null)
            throw new NullPointerException("Unable to get folder: " + folder);

        // Get message count
	//
        folder.open(Folder.READ_WRITE);
        int totalMessages = folder.getMessageCount();
        if (totalMessages == 0)
        {
            debugMsg(folder + " is empty");
            folder.close(false);
            store.close();
            return;
        }

        // Get attributes & flags for all messages
        //
        Message[] messages = folder.getMessages();
        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(FetchProfile.Item.FLAGS);
        fp.add("X-Mailer");
        folder.fetch(messages, fp);

        // Process each message
        //
        for (int i = 0; i < messages.length; i++)
        {
            if (!messages[i].isSet(Flags.Flag.SEEN))
                processMsg(smtpHost, messages[i]);
            messages[i].setFlag(Flags.Flag.DELETED, true);
        }

        folder.close(true);
        store.close();
    }


    /** processMsg() parses any newly received messages and calls
        sendMsg() to broadcast the message
    */
    private void processMsg(String smtpHost, Message message)
                 throws Exception
    {
        String replyTo=_user, subject, xMailer, messageText;
        Date sentDate;
        int size;
        Address[] a=null;


        // Get Headers (from, to, subject, date, etc.)
	//
        if ((a = message.getFrom()) != null)
             replyTo = a[0].toString();

        subject  = message.getSubject();
        sentDate = message.getSentDate();
        size     = message.getSize();
        String[] hdrs = message.getHeader("X-Mailer");
        if (hdrs != null)
            xMailer = hdrs[0];


        // Send message
	//
        sendMsg(_user, sentDate, replyTo, subject, message);
    }


    /**  sendMsg() broadcasts a message to all subscribers
    */
    private void sendMsg(String from, Date sentDate, String replyTo,
                         String subject, Message message)
                 throws Exception
    {
        // create some properties and get the default Session
        //
        Properties props = new Properties();
        props.put("mail.smtp.host", _smtpHost);
        Session session = Session.getDefaultInstance(props, null);

        // create a message
        //
        Address replyToList[] = { new InternetAddress(replyTo) };
        Message newMessage = new MimeMessage(session);
        if (_fromName != null)
            newMessage.setFrom(new InternetAddress(from,
                               _fromName + " on behalf of " + replyTo));
        else
            newMessage.setFrom(new InternetAddress(from));
        newMessage.setReplyTo(replyToList);
        newMessage.setRecipients(Message.RecipientType.BCC, _toList);
        newMessage.setSubject(subject);
        newMessage.setSentDate(sentDate);

        // Set message contents
        //
        Object content = message.getContent();
        String debugText = "Subject: " + subject + ", Sent date: " + sentDate;
        if (content instanceof Multipart)
        {
            debugMsg("Sending Multipart message (" + debugText + ")");
            newMessage.setContent((Multipart)message.getContent());
        }
        else
        {
            debugMsg("Sending Text message (" + debugText + ")");
            newMessage.setText((String)content);
        }

        // Send newMessage
        //
        Transport transport = session.getTransport(SMTP_MAIL);
        transport.connect(_smtpHost, _user, _password);
        transport.sendMessage(newMessage, _toList);
    }


    private void debugMsg(String s)
    {
        if (debugOn)
            System.out.println(new Date() + "> " + s);
    }


    public void setDebug(boolean state) { debugOn = state; }
}
