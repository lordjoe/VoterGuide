package com.lordjoe.lib.ftp;

import com.lordjoe.utilities.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
Ftp class:  by Joseph Szabo<p>

<pre>
$Id: FtpClient.java,v 1.1 2007/11/27 23:11:34 smlewis Exp $
$Log: FtpClient.java,v $
Revision 1.1  2007/11/27 23:11:34  smlewis
first checkin

Revision 1.4  2006/08/29 18:45:43  slewis
INterfaced out sa couple o ftoiolkit classes

Revision 1.3  2006/01/19 17:43:12  slewis
assed si,u,ated kloehn and more b3 operations

Revision 1.2  2005/12/02 17:13:09  slewis
added lordjoe ftp package

Revision 1.1.1.1  2002/08/23 02:39:35  slewis
no message

Revision 1.1.1.1  2002/06/13 16:23:40  validio
no message

Revision 1.8  2002/02/27 22:34:07  steve
New way of working attachments - must fix enterprise.xml

Revision 1.7  2002/02/27 01:14:17  steve
May do better on attachments

Revision 1.6  2002/02/26 23:34:55  iain
no message

Revision 1.5  2002/02/25 23:15:41  steve
Added code to place appointment in recipients calendar

Revision 1.4  2002/01/11 22:15:16  mike
no message

Revision 1.3  2002/01/10 18:50:27  steve
ALtered to support XML Data format for passing params

Revision 1.2  2001/10/09 19:24:10  steven
no message

Revision 1.1  2001/07/12 22:22:33  steven
no message

Revision 1.1  1996/08/22 02:55:48  jszabo
Initial revision

</pre>

<script>
<!--
document.writeln("Last Modified: " + document.lastModified); 
// --->
</script>

<p>
Here is the source (people dont find the link sometimes): <a href =
http://remus.rutgers.edu/~jszabo/myjava/Ftp.java>http://remus.rutgers.edu/~jszabo/myjava/Ftp.java</a>.<br>

A very-beta device-independent bare-bones implementation of an ftp
client, capable of regets, or resuming an interrupted download,
something no web browser can do (and http can't do).  Runs as a
text-based standalone application.  All ftpd commands that dont use a
data port work (quit is a little awkward, pressing return a few times
after quit exceptions you out of there).  Here are some useful
commands:

<pre>
user - send username
pass - send password (visibly)
cwd  - change working directory
stat - current status
rnfr - rename from
rnto - rename to
dele - delete file
mkd  - make directory
rmd  - remove directory
help - cryptic online help
pwd  - print working directory
cdup - change directory one up tree
syst - print system type
type - set type to i (image/binary) or a (ascii/text)
mdtm - show last modification time of a file
size - show size of a file<p>

site chmod - change mode on file permissions
site umask - user mask, what mode not to make new files (should be 22
    at least)
site help  - cryptic help for site commands
</pre>

Hit the end of file key to quit gracefully (control z in dos, control
d in unix).  There's no abort yet.<p>

Also these commands are supported, they all involve using an extra
data port:

<pre>
list - long listing of files
nlst - short listing
retr - retrieve file (restart supported)
stor - upload and store a file (restart in ftpd doesn't work)
stou - upload and store a file with a unique name, dont overwrite
rest - restart at byte offset to resume an interrupted download with retr
</pre>

So basically you can start like this:
<pre>
java Ftp ftp.uu.net
</pre>
login with user and pass (password is echoed),
use cwd to change directories, use list or nlst to list files, and
retr to retrieve or download a file (binary mode only).  You can use
rest right before retr to resume an interrupted download:<br>
<pre>
rest numberbytesihavealready
retr filename
</pre>

You can now upload with stor or stou.  Haven't done appe (append
localfile remotefile).<p>

Here's my email: <a href =
mailto:jszabo@remus.rutgers.edu>jszabo@remus.rutgers.edu</a>.  Here's
my homepage: <a href =
http://remus.rutgers.edu/~jszabo>http://remus.rutgers.edu/~jszabo</a>.<br>

Here is the class: <a href =
http://remus.rutgers.edu/~jszabo/myjava/Ftp.class>http://remus.rutgers.edu/~jszabo/myjava/Ftp.class</a>.<p>

The author tag  doesn't show up in javadoc 1.0.  It's right here:

@author    Joseph Szabo
*/


// todo rewrite with readers 
@SuppressWarnings(value = "deprecated")
public class FtpClient
{
    private static InetAddress gOverrideLocalIP;
    private static ILogger gLogger = StreamLogger.getConsole();

    public static ILogger getFTPLogger()
    {
        return(gLogger);
    }
    
    public static void logLine(String in)
    {
        if(gLogger != null)
            gLogger.debug(in);
    }
    
    public static void setFTPLogger(ILogger in)
    {
	if (in == null)
	    throw new IllegalArgumentException("FTP Logger must not be null");
        gLogger = in;
    }
    
    public static void setOverrideLocalIP(InetAddress s)
    {
        gOverrideLocalIP = s;
    }
    
    public static InetAddress getLocalIP()
    {
        InetAddress localip = null;
	    try {
            if(gOverrideLocalIP != null) {
                return(gOverrideLocalIP);
            }
            else {
	            localip = InetAddress.getLocalHost();
	        }
	        return(localip);
	    } 
	    catch(UnknownHostException e) 
	    {
		    throw new TransferFailureException("can't get local host");
	    }
	}
    
    
    public static final int TIMEOUT_INTERVAL = 6000;
    // at what offset to resume a file transfer
    long m_restartpoint = 0L;

    // ftpd result code prefixes
    static final int PRELIM    = 1;	 // positive preliminary
    static final int COMPLETE  = 2;	 // positive completion
    static final int CONTINUE  = 3;	 // positive intermediate
    static final int TRANSIENT = 4;	 // transient negative completion
    static final int ERROR     = 5;	 // permanent negative completion

    private Socket m_echoSocket = null;
    private PrintStream m_os = null;
    private DataInputStream m_is = null;
    private String m_CurrentRelativeDirectory;
    private String m_CurrentDirectory;
    /** 
     * Main loop that parses commands and sends them to the
     * appropriate function.
     *
     * @param args    args[0] m_is the name of the host to ftp to  
     */
     
     public String makeConnection(String host)
     {
        DataInputStream stdIn = new DataInputStream(System.in);

        try {
            /* there is a but in the JDK 1.5 socket class which
             * does a DNS lookup in raw IP Addresses - this
             * forces that not to happen
             */
             InetAddress addr = Util.getINetAddress(host);
            m_echoSocket = new Socket(addr, 21);  // ftp port
            m_echoSocket.setSoTimeout(TIMEOUT_INTERVAL);
            m_os = new PrintStream(m_echoSocket.getOutputStream());
            m_is = new DataInputStream(m_echoSocket.getInputStream());
        } 
        catch (UnknownHostException e) {
            throw new ConnectionLostException("dont know about host: " + host);
        } 
        catch (SocketException e) {
            throw new ConnectionLostException("Couldn't set Socket Timeout");
        }
        catch (IOException e) {
            throw new ConnectionLostException("Couldn't get I/O for the connection to: " + host);
        }
        
        if(replyIsSuccess(getreply(m_is)))
            return(null); // all OK
        return("cannot logon");
     }

     public boolean setUser(String User,String password) 
     {
        int reply = sendCommand("USER " + User);
        if(!replyIsSuccess(reply))
            return(false);
        reply = sendCommand("PASS " + password);
        return(replyIsSuccess(reply));
     } // success

     public boolean setStartDirectory(String Dir) 
     {
        boolean ret = setDirectory(Dir);
        m_CurrentRelativeDirectory = ""; // now at Base directory
        m_CurrentDirectory = Dir;
        return(ret);
     } // success
     
     public boolean setDirectory(String Dir) 
     {
        int reply = sendCommand("CWD " + Dir);
         return(replyIsSuccess(reply));
     } // success
     
     public boolean delete(String FileName) 
     {
        String cmd = "DELE " + FileName;
        String reply = sendCommandWithReply(cmd);
        if(reply.startsWith("250")) return(true); // file exists and was deleted
        if(reply.startsWith("550"))
        {
            if(reply.indexOf("No such file") > 0 || reply.indexOf("cannot find") > 0 )
                return(true); // never evisted
            else {
                if(gLogger != null)
                    gLogger.error("FTP code 550 on delete not understood '" + reply +"'");
            }
                
        }
        
        return(false); // delete failed
     }
     
     public boolean rename(String OldFileName,String newFileName) 
     {
        int reply = sendCommand("RNFR " + OldFileName);
        if(!replyIsSuccess(reply))
            return(false); // fail
        reply = sendCommand("RNTO " + newFileName);    
        return(replyIsSuccess(reply));
     }
     
     public String[] getList(String path) 
     {
        int reply = 0;
        String command = "List";
        if(path != null)
            command += " " + path;
        String ListData = captureResponse(command);
        String[] ret = Util.parseLines(ListData);
        return(ret);
     } // success
     
     
     public IFileDescriptor[] getRemoteFiles(String path) 
     {
        
        String[] items =  getList(path); 
        Vector holder = new Vector();
        for(int i = 0; i < items.length; i++) {
            String[] tokens = Util.parseWhiteSpaceDelimited(items[i]);
            if(tokens.length >= RemoteFileDescriptor.REQUIRED_TOKENS) {
                holder.addElement(new RemoteFileDescriptor(path,tokens));
            }
        }
        IFileDescriptor[] ret = new IFileDescriptor[holder.size()];
        holder.copyInto(ret);
        return(ret);
     }
     
     protected String captureResponse(String command) 
     {
        ByteArrayOutputStream data_os = new ByteArrayOutputStream(1024);
        
        PrintStream saveOut = new PrintStream(new BufferedOutputStream(data_os));
        
//        doDataPort(command, false, m_is,saveOut);
        doCaptureResponse(command, saveOut,false);
        
        String ListData = data_os.toString();
        return(ListData);
     }
     
     public boolean guaranteeDirectories(DirectoryStructure TheStructure)
     {
        DirectoryStructure[] Subdirectories = TheStructure.getSubdirectories();
        if(Subdirectories == null) 
            return(true); // nothing to do
        boolean ret = true;
        for(int i = 0; i < Subdirectories.length; i++) {
            ret &= guaranteeDirectory(Subdirectories[i]);
            if(!ret) return(false); // give up
        }
        return(ret);
     }
     
     public boolean guaranteeDirectory(DirectoryStructure TheStructure)
     {
        int reply = sendCommand("CWD " + TheStructure.getName());
        if(!replyIsSuccess(reply)) {
            reply = sendCommand("MKD " + TheStructure.getName());
            if(!replyIsSuccess(reply)) return(false); // give up
            reply = sendCommand("CWD " + TheStructure.getName());
            if(!replyIsSuccess(reply)) return(false); // give up
        }
        boolean ret = guaranteeDirectories(TheStructure);
        reply = sendCommand("CWD .."); // back to parent
        return(ret & replyIsSuccess(reply));
     }
     

     public String[] getSmallList(String path) 
     {
        int reply = 0;
        String command = "NLST";
        if(path != null)
            command += " " + path;
        
        doDataPort(command, false, m_is, m_os);
        return(new String[0]);
     } // success

     public boolean download(String path) 
     {
        String command = "RETR " + path;
        return(download(command, m_is, m_os,path));
     } // success

      public boolean downloadAs(String RemotePath,String LocalPath,boolean Delete) 
     {
        boolean ret = downloadAs( RemotePath, LocalPath);
        if(ret && Delete) 
            delete(RemotePath);
        return(ret);
     } // success
     
    public boolean downloadAs(String RemotePath,String LocalPath) 
     {
        String command = "RETR " + RemotePath;
        
        return(download(command, m_is, m_os,LocalPath));
     } // success

     public boolean uploadFile(File TheFile,String LocalTarget,String RemoteTarget)
     {
        String FileName = TheFile.getName();
        String TargetDirectory = makeTargetDirectory(TheFile,LocalTarget,RemoteTarget);
        if(setDirectory(TargetDirectory))
            return(upload("STOR " + FileName, m_is, m_os,TheFile));

        return(false); // fail
     }
      public boolean uploadAs(File localFile,String localDir,String remotePath,boolean Delete) 
     {
        boolean ret = uploadAs( localFile,localDir, remotePath);
        if(ret && Delete) 
            localFile.delete();
        return(ret);
     } // success
     
    public boolean uploadAs(File theFile,String localDir,String RemotePath) 
     {
        if(!theFile.exists() || !theFile.canRead())
            throw new TransferFailureException("File '" + theFile.getName() + "' does not exist (or cannot be read");

        return(safeUploadFile(theFile,localDir,RemotePath));
        
     } // success
     
     public boolean safeUploadFile(File TheFile,String localDirectory,String RemoteTarget)
     {
        String FileName = makeFileName(RemoteTarget);
        String TempFileName = FileName + "tmp";
        String TargetDirectory = makeTargetDirectory(RemoteTarget);
        if(!Util.isEmptyString(TargetDirectory)) {
            if(!setDirectory(TargetDirectory)) 
                return(false); // fail
        }
        boolean ret = delete(TempFileName); // make sure upload OK
        if(!ret) return(false); // fail
        ret = upload("STOR " + TempFileName, m_is, m_os,TheFile);
        if(!ret) return(false); // fail
        ret = delete(FileName); // make sure rename ok
        if(!ret) return(false); // fail
           
        return(rename(TempFileName,FileName));
     }
     
     public static String makeFileName(String in)
     {
        int index = in.lastIndexOf("/");
        if(index == -1) 
            return(in);
        return(in.substring(index + 1));
     }

    public static String makeTargetDirectory(String in)
     {
        int index = in.lastIndexOf("/");
        if(index == -1 || index == 1)
            return("");
        return(in.substring(index - 1));
     }

     
     public boolean safeUploadTempFile(File TheFile,String LocalTarget,String RemoteTarget)
     {
        String FileName = TheFile.getName();
        String TempFileName = FileName + "tmp";
        String TargetDirectory = makeTargetDirectory(TheFile,LocalTarget,RemoteTarget);
        if(setDirectory(TargetDirectory)) {
            boolean ret = upload("STOR " + TempFileName, m_is, m_os,TheFile);
            if(!ret) return(false); // fail
        }
        return(true); // success
     }
     
     public boolean safeRenameTempFile(File TheFile,String localDirectory,String RemoteTarget)
     {
        String FileName = TheFile.getName();
        String TempFileName = FileName + "tmp";
        String TargetDirectory = makeTargetDirectory(TheFile,localDirectory,RemoteTarget);
        if(!setDirectory(TargetDirectory)) 
           return(false); // fail
        if(!delete(FileName)) // make sure delete ok
            return(false); // fail           
        return(rename(TempFileName,FileName));
     }

     public String makeTargetDirectory(File TheFile,String LocalTarget,String RemoteTarget)
     {
        String parent = TheFile.getParent();
        if(parent == null || parent.length() == 0)
            return(RemoteTarget);
        String test = TheFile.getParent().replace(File.separatorChar,'/');
        String ret = RemoteTarget + "/" + test.substring(LocalTarget.length());
        return(ret);
     }
     
    public void quit() {
     if(m_os != null) {
      int reply = sendCommand("QUIT");
        closeConnection();
     }
    }
    
    public void finalize()
    {
        closeConnection();
    }


     public void closeConnection()
     {
        try { 
            if(m_os != null) {
		        m_os.close();
		        m_os = null;
		    }
            if(m_is != null) {
		        m_is.close();
		        m_is = null;
		    }
            if(m_echoSocket != null) {
		        m_echoSocket.close();
		        m_echoSocket = null;
		    }

            }catch (IOException e) {
                // ignore 
            }
     }

     public String executeCommand(String command) 
     {
          if (m_echoSocket == null && m_os == null && m_is == null) return(null);
		    // get intro
		    getreply(m_is);

		// read and write loop
		        if(command.startsWith("list"))
			        doDataPort(command, false, m_is, m_os);
		        else if(command.startsWith("retr"))
			        doDataPort(command, true, m_is, m_os);
		        else if(command.startsWith("rest"))
			        rest(command, m_is, m_os);
		        else if(command.startsWith("stor") ||
			        command.startsWith("stou"))
			        upload(command, m_is, m_os);
		        else {
			        m_os.println(command);

			        // get server response
			        getreply(m_is);
			    }

		    return(null);
   }
   
   public String sendCommandWithReply(String command) 
   {
        if (m_os == null || m_is == null)
        return("599 no connection");
		m_os.println(command);
		logLine(command);
		return(getReplyText(m_is));
   }
   
     public int sendCommand(String command)
     {
          String reply = sendCommandWithReply(command);
	      return(Integer.parseInt(reply.substring(0, 1)));
   }
   
/** 
 * Gets server text reply from the control port after an
 * ftp command has been entered.  It knows the last line of the
 * response because it begins with a 3 digit number and a space, (a
 * dash instead of a space would be a continuation).  Note, no special
 * IAC telnet commands are processed here.  Seems to work anyway.
 *
 * @param m_is    input data stream on the ftp control port socket  
 * @return      returns the first character of the last line as an
 * integer (ftpd return code)
 */

@SuppressWarnings(value = "deprecated")
    public  String getReplyText(DataInputStream is){

	    String sockoutput;

	    // get reply (intro)
	    try {
	        do {
		    sockoutput = is.readLine();
		        logLine(sockoutput);
	       } 
	       while(!(Character.isDigit(sockoutput.charAt(0)) &&
		            Character.isDigit(sockoutput.charAt(1)) &&
		            Character.isDigit(sockoutput.charAt(2)) && 
		            sockoutput.charAt(3) == ' '));
        	    
        } 
        catch(InterruptedIOException ex) {
            throw new TransferFailureException("Timed out");
        }
        catch (IOException e) {
            throw new TransferFailureException("598 Error getting reply from controlport");
	    }
	    return(sockoutput);

    } // end getreply



/** 
 * Gets server text reply from the control port after an
 * ftp command has been entered.  It knows the last line of the
 * response because it begins with a 3 digit number and a space, (a
 * dash instead of a space would be a continuation).  Note, no special
 * IAC telnet commands are processed here.  Seems to work anyway.
 *
 * @param m_is    input data stream on the ftp control port socket  
 * @return      returns the first character of the last line as an
 * integer (ftpd return code)
 */

    public  int getreply(DataInputStream is){

	    String sockoutput;

	    // get reply (intro)
	    try {
	        do {
		        sockoutput = is.readLine();
		        logLine(sockoutput);
	        } 
	        while(!(Character.isDigit(sockoutput.charAt(0)) &&
		        Character.isDigit(sockoutput.charAt(1)) &&
		        Character.isDigit(sockoutput.charAt(2)) && 
		        sockoutput.charAt(3) == ' '));
	}
        catch(InterruptedIOException ex) {
            throw new TransferFailureException("Timed out");
        }
        catch (IOException e) {
            throw new TransferFailureException("598 Error getting reply from controlport");
	    }
	    return(Integer.parseInt(sockoutput.substring(0, 1)));

    } // end getreply


    /** 

    Performs downloading commands that need a data port.  Tells server
    which data port it will listen on, then sends command on control port,
    then listens on data port for server response.

    @param command           list, nlist, or retr plus arguments
    @param saveToFile        whether to save to file or print to screen
                                from data port
    @param incontrolport     input stream on the control port socket
    @param outcontrolport    output stream on the control port socket.  
    */

    public  void doCaptureResponse(String command, OutputStream out,boolean UseBinary)
    {
	    ServerSocket serverSocket = getServerSocket();
	    port(serverSocket, m_is, m_os);
	    if(serverSocket == null) return;
	    // set binary type transfer
	    if(UseBinary){
	        m_os.println("type i");
		   // logLine("type i");
	        getreply(m_is);
	    }
	    m_os.println(command);
	    logLine(command);
	
	    int result = getreply(m_is);

	    // guess this should be an exception if false
	    if(result == PRELIM)
	    {
	        // connect to data port
	        Socket clientSocket = null;
	        try {
		    clientSocket = serverSocket.accept();
	        } catch (IOException e) {
		        throw new TransferFailureException("Accept failed: " +
				        serverSocket.getLocalPort() + ", " + e);
	        }

	        try {
		        InputStream Input = clientSocket.getInputStream();

		        byte b[] = new byte[1024];  // 1K blocks I guess
		        int amount;

		        while((amount = Input.read(b)) != -1){
			        out.write(b, 0, amount);
                }
		        out.close();
		        getreply(m_is); // why is cast needed???

		        Input.close();

		        // clean up when done
		        clientSocket.close();

	        } 
            catch(InterruptedIOException ex) {
                throw new TransferFailureException("Timed out");
            }
            catch (IOException e) {
                throw new TransferFailureException("598 Error getting reply from controlport");
	        }

	    } // if(result == PRELIM)
	}
      

    protected  ServerSocket getServerSocket()
    {
	    ServerSocket serverSocket = null;

	    try {
	        serverSocket = new ServerSocket(0);
            serverSocket.setSoTimeout(TIMEOUT_INTERVAL);
	    }
        catch (SocketException e) {
            throw new ConnectionLostException("Cannot Get Socket");
        }
	    catch (IOException e) {
	        throw new ConnectionLostException("Could not get port for listening:  " + 
			        serverSocket.getLocalPort() + ", " + e);
	    }
	    return serverSocket;
	}
	    
    /** 

    Performs downloading commands that need a data port.  Tells server
    which data port it will listen on, then sends command on control port,
    then listens on data port for server response.

    @param command           list, nlist, or retr plus arguments
    @param incontrolport     input stream on the control port socket
    @param outcontrolport    output stream on the control port socket.  
    @param LocalName        namer of file on local machine
    */

    public  boolean download(String command, DataInputStream incontrolport,
		     PrintStream outcontrolport,String LocalName)
    {
	    ServerSocket serverSocket = getServerSocket();
	    if(serverSocket == null) 
	        return(false); // fail


	    port(serverSocket, incontrolport, outcontrolport);

	    // set binary type transfer
	    outcontrolport.println("type i");
		// logLine("type i");
	    getreply(incontrolport);

	    // ok, send command

	
	    if(m_restartpoint != 0){
	        // have to do right before retr
	        outcontrolport.println("rest " + m_restartpoint);
	        logLine("rest " + m_restartpoint);
	        getreply(incontrolport);
	    }

	    outcontrolport.println(command);
	    logLine(command);
	
	    int result = getreply(incontrolport);

	    // guess this should be an exception if false
	    if(result == PRELIM){

	        // connect to data port
	        Socket clientSocket = null;
	        try {
		    clientSocket = serverSocket.accept();
	        } catch (IOException e) {
		        throw new TransferFailureException("Accept failed: " +
				        serverSocket.getLocalPort() + ", " + e);
	        }

	        try {
		    InputStream m_is = clientSocket.getInputStream();

		    byte b[] = new byte[1024];  // 1K blocks I guess
		    int amount;

		    // open file
		    RandomAccessFile outfile = new RandomAccessFile(LocalName, "rw");

		    // do restart if desired
		    if(m_restartpoint != 0){

		    logLine("seeking to " + m_restartpoint);
			outfile.seek(m_restartpoint);
			m_restartpoint = 0;
		    }

		    while((amount = m_is.read(b)) != -1){
			    outfile.write(b, 0, amount);
		       // DirectUpdater.displayString("#");
		    }
		
		    logLine("ftp download complete");
		    outfile.close();

		    getreply(incontrolport);

		    m_is.close();

		    // clean up when done
		    clientSocket.close();
        }
        catch(InterruptedIOException ex) {
            throw new TransferFailureException("Timed out");
        }
	    catch (IOException e) {
	        e.printStackTrace();
            throw new TransferFailureException("IO Exception");
	    }
        return(true); // succeed
	 } // end if PRELIM

	    else{
	        logLine("Error calling for download");
	        try {
		        serverSocket.close();
	        }
	        catch (IOException e) {
		        logLine("Error closing server socket.");
	        }
	    }
	    return(false); // fail
   } // end download()


    /** 

    Performs downloading commands that need a data port.  Tells server
    which data port it will listen on, then sends command on control port,
    then listens on data port for server response.

    @param command           list, nlist, or retr plus arguments
    @param saveToFile        whether to save to file or print to screen
                                from data port
    @param incontrolport     input stream on the control port socket
    @param outcontrolport    output stream on the control port socket.  
    */

   public  boolean doDataPort(String command, boolean
		     saveToFile, DataInputStream incontrolport,
		     PrintStream outcontrolport)
    {
	    ServerSocket serverSocket = getServerSocket();
	    if(serverSocket == null) return(false); // fail


	    port(serverSocket, incontrolport, outcontrolport);

	    // set binary type transfer
	    if(saveToFile){
	        outcontrolport.println("type i");
		 //   logLine("type i");
	        getreply(incontrolport);
	    }

	    // ok, send command

	
	    if(m_restartpoint != 0){
	        // have to do right before retr
	        outcontrolport.println("rest " + m_restartpoint);
	        logLine("rest " + m_restartpoint);
	        getreply(incontrolport);
	    }

	    outcontrolport.println(command);
	    logLine(command);
	
	    int result = getreply(incontrolport);

	    // guess this should be an exception if false
	    if(result == PRELIM){

	        // connect to data port
	        Socket clientSocket = null;
	        try {
		    clientSocket = serverSocket.accept();
	        } catch (IOException e) {
		        throw new TransferFailureException("Accept failed: " +
				        serverSocket.getLocalPort() + ", " + e);
	        }

	        try {
		    InputStream m_is = clientSocket.getInputStream();

		    byte b[] = new byte[1024];  // 1K blocks I guess
		    int amount;

		    if(saveToFile){
		        // get filename argument
		        StringTokenizer stringtokens = new StringTokenizer(command);
		        stringtokens.nextToken();
		        String filename = stringtokens.nextToken();

		        // open file
		        RandomAccessFile outfile = new RandomAccessFile(filename, "rw");

		        // do restart if desired
		        if(m_restartpoint != 0){

		        logLine("seeking to " + m_restartpoint);
			    outfile.seek(m_restartpoint);
			    m_restartpoint = 0;
		        }

		        while((amount = m_is.read(b)) != -1){
			        outfile.write(b, 0, amount);
		           // DirectUpdater.displayString("#");
		        }
		        logLine("ftp dataport complete");
		        outfile.close();
		    } // end if savetofile

		    else {
		        while((amount = m_is.read(b)) != -1) {
		            if(DirectUpdater.DEBUGGING) {
		           //     System.out.write(b, 0, amount);  // write to screen
		                String msg = new String(b, 0, amount);
		                logLine(msg);
		            }
		        }
		    }

		    getreply(incontrolport);

		    m_is.close();

		    // clean up when done
		    clientSocket.close();

	        } catch (InterruptedIOException e) {
		        throw new TransferFailureException("Timed out");
	        }
	        catch (IOException e) {
		        throw new TransferFailureException("IO Error on Transfer");
	        }

            return(true); // succeed
	    } // end if PRELIM

	    else{
	        getFTPLogger().error("Error calling for download");
	        try {
		        serverSocket.close();
	        }
	        catch (IOException e) {
		        getFTPLogger().error("Error closing server socket.",e);
	        }
	    }
	    return(false); // fail
   } // end dodataport()


    /**
    * Set the restart point for the next retr.  This way you can
    * resume an interrupted upload or download just like with zmodem.
    * Actually it doesn't send the rest command until right before the
    * retr (it has to be that way), but it will remember to do it.
    *
    * @param command          The command line that begins with rest.
    * @param incontrolport    For reading from the ftp control port.
    * @param outcontrolport   For writing to the ftp control port.
    */

    public  void rest(String command, DataInputStream
			    incontrolport, PrintStream
			    outcontrolport){

	    StringTokenizer stringtokens = new StringTokenizer(command);
	    stringtokens.nextToken();

	    // put second argument here
            m_restartpoint = Integer.parseInt(stringtokens.nextToken());
		logLine("restart noted");
    }

    /** 

    Upload a file in binary mode using either stor or stou.  Looks like
    restart doesn't work with stores, unfortunately.  Ftpd at least on my
    solaris account zeros out the old half of the file already
    transferred.

    @param command           stor or stou plus arguments
    @param incontrolport     input stream on the control port socket
    @param outcontrolport    output stream on the control port socket.  
    */

    public  boolean upload(String command, DataInputStream
		     incontrolport, PrintStream outcontrolport){

	ServerSocket serverSocket = null;

	try {
	    serverSocket = new ServerSocket(0);
        serverSocket.setSoTimeout(TIMEOUT_INTERVAL);
	}
    catch (SocketException e) {
	    throw new ConnectionLostException("Cannot Create Socket");
    }
	catch (IOException e) {
	    throw new ConnectionLostException("Could not get port for listening:  " + 
			       serverSocket.getLocalPort() + ", " + e);
	}

	port(serverSocket, incontrolport, outcontrolport);

	// set binary type transfer
	outcontrolport.println("type i");
	// logLine("type i");
	getreply(incontrolport);

	// send restart if desired
	if(m_restartpoint != 0){
	    // have to do right before retr?
	    outcontrolport.println("rest " + m_restartpoint);
		logLine("rest " + m_restartpoint);
	    getreply(incontrolport);
	}

	// ok, send command	
	outcontrolport.println(command);
	logLine(command);
	int result = getreply(incontrolport);

	// guess this should be an exception if false
	if(result == PRELIM){

	    // listen on data port
	    Socket clientSocket = null;
	    try {
		clientSocket = serverSocket.accept();
	    } catch (IOException e) {
		    throw new TransferFailureException("Accept failed: " +
				   serverSocket.getLocalPort() + ", " + e);
	    }

	    try {
		OutputStream outdataport = clientSocket.getOutputStream();

		byte b[] = new byte[1024];  // 1K blocks I guess

		// get filename argument
		StringTokenizer stringtokens = new
		    StringTokenizer(command);
		stringtokens.nextToken();
		String filename = stringtokens.nextToken();

		// open file
		RandomAccessFile infile = new
		    RandomAccessFile(filename, "r");

		// do restart if desired
		if(m_restartpoint != 0){
		    logLine("seeking to " + m_restartpoint);
		    infile.seek(m_restartpoint);
		    m_restartpoint = 0;
		}

		// do actual upload
		int amount;

		// *** read returns 0 at end of file, not -1 as in api

//		while((amount = infile.read(b)) != -1){

//		while((amount = infile.read(b)) != 0){

		while ((amount = infile.read(b)) > 0) {

		    outdataport.write(b, 0, amount);
		  //  DirectUpdater.displayString("#");
		}
		logLine("ftp upload complete");

		infile.close();
		outdataport.close();

		// clean up when done
		clientSocket.close();
		serverSocket.close();

		result = getreply(incontrolport);


	    } catch (IOException e) {
		    throw new TransferFailureException("IO Failure");
	    }

	    return(result == COMPLETE);
	    
	} // end if PRELIM

	else{
	    getFTPLogger().error("Error calling for download");
	    try {
		    serverSocket.close();
	    }
	    catch (IOException e) {
		    getFTPLogger().error("Error closing server socket.",e);
	    }
	    return(false);
	}

    }// end upload

  /** 

    Upload a file in binary mode using either stor or stou.  Looks like
    restart doesn't work with stores, unfortunately.  Ftpd at least on my
    solaris account zeros out the old half of the file already
    transferred.

    @param command           stor or stou plus arguments
    @param incontrolport     input stream on the control port socket
    @param outcontrolport    output stream on the control port socket.  
    */

    public  boolean upload(String command, DataInputStream
		     incontrolport, PrintStream outcontrolport,File TheFile)
    {

	ServerSocket serverSocket = null;

	try {
	    serverSocket = new ServerSocket(0);
        serverSocket.setSoTimeout(TIMEOUT_INTERVAL);
	}
	catch (IOException e) {
	    throw new TransferFailureException("Could not get port for listening:  " + 
			       serverSocket.getLocalPort() + ", " + e);
	}

	port(serverSocket, incontrolport, outcontrolport);

	// set binary type transfer
	outcontrolport.println("type i");
	//logLine("type i");
	getreply(incontrolport);

	// send restart if desired
	if(m_restartpoint != 0){
	    // have to do right before retr?
	    outcontrolport.println("rest " + m_restartpoint);
		logLine("rest " + m_restartpoint);
	    getreply(incontrolport);
	}

	// ok, send command	
	outcontrolport.println(command);
	logLine(command);
	int result = getreply(incontrolport);

	// guess this should be an exception if false
	if(result == PRELIM){

	    // listen on data port
	    Socket clientSocket = null;
	    try {
		clientSocket = serverSocket.accept();
	    } catch (IOException e) {
		    throw new TransferFailureException("Accept failed: " +
				   serverSocket.getLocalPort() + ", " + e);
	    }

	    try {
		OutputStream outdataport = clientSocket.getOutputStream();

		byte b[] = new byte[1024];  // 1K blocks I guess

		// open file
		RandomAccessFile infile = new
		    RandomAccessFile(TheFile.getPath(), "r");

		// do restart if desired
		if(m_restartpoint != 0){
		    logLine("seeking to " + m_restartpoint);
		    infile.seek(m_restartpoint);
		    m_restartpoint = 0;
		}

		// do actual upload
		int amount;

		// *** read returns 0 at end of file, not -1 as in api

//		while((amount = infile.read(b)) != -1){

//		while((amount = infile.read(b)) != 0){

		while ((amount = infile.read(b)) > 0) {

		    outdataport.write(b, 0, amount);
		    // DirectUpdater.displayString("#");
		}
		logLine("ftp upload complete");

		infile.close();
		outdataport.close();

		// clean up when done
		clientSocket.close();
		serverSocket.close();

		result = getreply(incontrolport);


	    } catch (IOException e) {
		    throw new TransferFailureException("IO Exception on transfer");
	    }

	    return(result == COMPLETE);
	    
	} // end if PRELIM

	else{
	    getFTPLogger().error("Error calling for download");
	    try {
		serverSocket.close();
	    }
	    catch (IOException e) {
		    getFTPLogger().error("Error closing server socket.",e);
	    }
	    return(false);
	}

    }// end upload



    /** Get ip address and port number from serverSocket and send them
        via the port command to the ftp server, and getting a valid
        response.

        @param  serverSocket     Socket to get info from.
        @return                  true or false depending on success */

    public boolean port(ServerSocket serverSocket,
			       DataInputStream incontrolport,
			       PrintStream outcontrolport)
    {
	    int localport = serverSocket.getLocalPort();
	    logLine("Will listen on port, " + localport);

	    // get local ip address
	    InetAddress inetaddress = serverSocket.getInetAddress();
	    
	    InetAddress localip = getLocalIP();

	    // get ip address in high byte order
	    byte[] addrbytes = localip.getAddress();

	    // tell server what port we are listening on
	    short addrshorts[] = new short[4];

	    // problem:  bytes greater than 127 are printed as negative numbers
	    for(int i = 0; i <= 3; i++){
	        addrshorts[i] = addrbytes[i];
	        if(addrshorts[i] < 0)
		    addrshorts[i] += 256;
	    }

	    outcontrolport.println("port " + addrshorts[0] + "," +
			    addrshorts[1] + "," + addrshorts[2] + "," +
			    addrshorts[3] + "," + ((localport & 0xff00) >>
			    8) + "," + (localport & 0x00ff));

	    // echo for myself
	    logLine("port " + addrshorts[0] + "," + addrshorts[1]
			    + "," + addrshorts[2] + "," + addrshorts[3] + ","
			    + ((localport & 0xff00) >> 8) + "," +
			    (localport & 0x00ff));

	    int result = getreply(incontrolport);

	    return(result == COMPLETE);

    }// end port

    public boolean replyIsSuccess(int reply) {
        return(reply >= PRELIM && reply < TRANSIENT);
    }

    public boolean replyIsPrelim(int reply) {
        return(reply == PRELIM);
    }
    
    public boolean replyIsComplete(int reply) {
        return(reply == COMPLETE);
    }
    public boolean replyIsContinue(int reply) {
        return(reply == CONTINUE);
    }
    
    protected String[] readReplyLines() {
        return(new String[0]);
    }
    
    public static void main(String[] items) 
    {
        DirectUpdater TheUpdater = new DirectUpdater();
        String user = "slewis";
        String password = "lithium7";
        String host = "10.1.130.71"; // 118";
        String Target = "c:\\";
        FtpClient TheClient = new FtpClient();        
        if(TheClient.makeConnection(host) != null) 
            return; // fail
        if(!TheClient.setUser(user,password)) 
            return; // fail
        if(!TheClient.setStartDirectory("Onvia/temp")) 
            return; // fail
        String[] files =  TheClient.getList("*");
        for(int i = 0; i < files.length; i++) {
            System.out.println(files[i]); // test
        }
        TheClient.downloadAs("Rule01.java","c:/temp/foo.java");
        
    }
} // Class Ftp



    
