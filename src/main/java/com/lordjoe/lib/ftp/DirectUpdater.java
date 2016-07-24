

package com.lordjoe.lib.ftp;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.lordjoe.utilities.*;

public class DirectUpdater extends Frame implements Runnable,ActionListener
{
    public static final boolean DEBUGGING    = true;	 // positive preliminary
    public static final int MAX_RETRIES    = 3;	 //  NUmber of times to restart
    public static final int DISPLAY_TIMEOUT  = 15;	 //  Seconds for a message to display before timeout

    public static final Color TryColors[] = 
        { Color.lightGray, Color.yellow, Color.pink,Color.red };

    public static final String PROPERTY_FILE_NAME = "Updater.properties";
    public static final String USER_TAG = "user";
    public static final String HOST_TAG = "host";
    public static final String PASSWORD_TAG = "password";
    public static final String REMOTE_TARGET_TAG = "target_directory";
    public static final String LOCAL_TARGET_TAG = "local_directory";
    public static final String DIALUP_TARGET_TAG = "dialup_connection";
    
    //
    // GUI Elements
    //
    private Label m_RemoteDirectory;
    private Label m_LocalDirectory;
    private Label m_Operation;
    private Label m_Task;
    private Label m_Connection;
    private Panel m_LabelPanel;
    
    private TextArea m_Display;
    
    private Button m_RestartButton;
    private Button m_AbortButton;
    private Panel m_ButtonPanel;
    
    
    private FtpClient m_Client;      // object managing the connection
    private Properties m_UpdateProp; // properties describing what id below
    private String m_user;          // user name for host
    private String m_password;      // password for host
    private String m_host;          // host URL
    private String m_RemoteTarget;  // name of the remote directory 
    private String m_LocalTarget;   // name of the local directory
    private String m_Dialup;        // optional Dialup
    
    private Thread  m_TaskThread;
    private boolean m_Upload;  // true if uploading 
    
    private RuntimeException m_Exception; // used to set an exception to be thrown in the transfer thread 
    
    
    private static DirectUpdater gInstance; // this class is a singleton - means Client does not
                                            // need a pointer to an instance
    
    public DirectUpdater() {
        gInstance = this;               // singleton instance is this
        m_Client = new FtpClient();     // make the client
        m_UpdateProp = new Properties(); 
        init();
    }
    
    /**
        Actually fo the transfer - transfer is done ina separate thread
    */
    public void go() 
    {
        m_TaskThread = new Thread(this,"Direct Updater");
        m_TaskThread.setDaemon(true);
        m_TaskThread.start();
    }
     
    public void run() 
    {
        boolean success = false;
        int DialCount = 0 ;
        int LingerTime = 3000; // miiilset to stay up after completion
        TimedDialog inform = null;
        String TitleString;
        int Try = 0;
        if(m_Dialup != null)    // add one try if we need to dial
            Try = -1;
        for(; Try < MAX_RETRIES; Try++) 
        {
            if(m_Upload)
                TitleString = "Uploading Files";
            else
                TitleString = "Downloading Files";
            if(Try > 0)
                TitleString += "Try " + (Try + 1);
            setTryColors(Try); 
            
            if(Try > MAX_RETRIES - 1) // no more retries
                m_RestartButton.setEnabled(false);
                
            setTitle(TitleString);
            try {
                boolean result = TestConnection.testInternetConnection();
                if(!result) {
                  throw new ConnectionLostException("Cannot talk to the Net");
                }
                if(m_Upload) 
                    success = uploadDirectory();
                else
                    success = downloadDirectory();
                if(success)
                    break;
            }
            catch(UserCanceledException ex) 
            {
                success = false;
                LingerTime = 0; // just goes - user expects this
                break;
            }
            // These cause a retry
            catch(TransferFailureException ex) 
            {
                if(Try < MAX_RETRIES - 1) {
                    inform = new TimedDialog(this,DISPLAY_TIMEOUT,
                        "There were problems with the transfer",
                        "The program will retry");
                    inform.setDialogColor(TryColors[1]);
                }
                else {
                    inform = new TimedDialog(this,DISPLAY_TIMEOUT,
                        "There were problems with the transfer",
                         "Maximum retrys reached\r\n" +
                       "The program will abort");
                    inform.setDialogColor(TryColors[3]);
                    LingerTime = 1000 * DISPLAY_TIMEOUT; // let user read problem
                }
                inform.show();
                success = false;
            }
             catch(UserRestartException ex) 
            {
                inform = new TimedDialog(this,DISPLAY_TIMEOUT,
                    "Restarting the transfer",
                    "Restart at user request.\r\n" + 
                    (MAX_RETRIES - Try) + "More Restarts Allowed" );
                inform.setDialogColor(TryColors[0]);
                success = false;
            }
        } // for(int Try = 0; Try < MAX_RETRIES; Try++) 
        
        if(success)
            setTask("Done - Succeeded");
        else
            setTask("Done - Failed");
        
        if(LingerTime > 0) {
            try {
                Thread.currentThread().sleep(LingerTime);
            }
            catch(InterruptedException ex) {}
        }
        System.exit(0);
    }
    
    public synchronized void setException(RuntimeException t)
    {
        m_Exception = t;
    }

    public RuntimeException getException()
    {
        return(m_Exception);
    }

    
    public static void displayLine(String line)
    {
        String realLine = line + "\r\n";
        System.out.println(line);
        // gInstance.appendText(realLine);
    }
    
    public static void displayString(String text)
    {
        System.out.print(text);
    //    gInstance.appendText(text);
    }
    
     public void appendText(String newText) 
     {
        // here is a good spot to abort or restart
        RuntimeException problem = getException();
        if(problem != null) {
            setException(null); // clear
            throw problem;
        }
        m_Display.append(newText);
        Thread.currentThread().yield();
   //    m_Display.repaint(100);
     }

    public void setUpload(boolean doit) 
     {
        m_Upload = doit;
        if(doit)
            m_Operation.setText("Uploading");
        else
            m_Operation.setText("Downloading");
     }
     
     
    public void setTask(String newTask) 
     {
        m_Task.setText(newTask);
     }
    
     public boolean makeConnection() {
        if(m_Client.makeConnection(m_host) != null) return(false); // fail
        if(!m_Client.setUser(m_user,m_password)) return(false); // fail
        if(!m_Client.setStartDirectory(m_RemoteTarget)) return(false); // fail
        return(true);
     }
  
    class MyWindowAdapter extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            System.exit(0); // probably not good
        }
    }

    protected void setTryColors(int Try)
    {
        Try = Math.min(Math.max(0,Try),TryColors.length - 1);
        m_LabelPanel.setBackground(TryColors[Try]);
        m_RemoteDirectory.setBackground(TryColors[Try]);
        m_LocalDirectory.setBackground(TryColors[Try]);
        m_Operation.setBackground(TryColors[Try]);
        m_Task.setBackground(TryColors[Try]);
        m_Connection.setBackground(TryColors[Try]);
        m_ButtonPanel.setBackground(TryColors[Try]);
    }
    
    protected void init() {
        if(!loadProperties()) return;
        if(!readProperties()) return;
        
        setLayout(new BorderLayout());
        
        m_LabelPanel = new Panel();
        m_LabelPanel.setLayout(new GridLayout(4,1));
        m_LabelPanel.setBackground(TryColors[0]);
        add(m_LabelPanel,BorderLayout.NORTH);
        
         m_RemoteDirectory = new Label("Remote Directory - " + m_RemoteTarget);
         m_LabelPanel.add(m_RemoteDirectory);
         
         m_LocalDirectory  = new Label("Local Directory - " + m_LocalTarget);
         m_LabelPanel.add(m_LocalDirectory);

         m_Operation  = new Label("Download");
         m_LabelPanel.add(m_Operation);

         m_Task  = new Label("Connecting");
         m_LabelPanel.add(m_Task);

         m_Connection  = new Label("Connection - Existing");
         m_LabelPanel.add(m_Task);
         
         m_Display = new TextArea(80,24);
         add(m_Display,BorderLayout.CENTER);

        m_ButtonPanel = new Panel();
        m_ButtonPanel.setLayout(new FlowLayout());
        m_ButtonPanel.setBackground(TryColors[0]);
        add(m_ButtonPanel,BorderLayout.SOUTH);

         m_RestartButton  = new Button("Restart");
         m_RestartButton.addActionListener(this);
         m_ButtonPanel.add(m_RestartButton);

         m_AbortButton  = new Button("Abort");
         m_AbortButton.addActionListener(this);
         m_ButtonPanel.add(m_AbortButton);

        setLocation(100,150);
        setSize(400,350);
        setVisible(true);
        
        addWindowListener(new MyWindowAdapter());
    }
        
    public void actionPerformed(ActionEvent ev)
    {
        Object source = ev.getSource();
        if(source == m_AbortButton) {
            setException(new UserCanceledException());
            return;
        }
        if(source == m_RestartButton) {
            setException(new UserRestartException());
            return;
        }
    }
    
    protected  boolean readProperties() 
    {
        m_user      = (String)m_UpdateProp.get(USER_TAG);
        m_password  = (String)m_UpdateProp.get(PASSWORD_TAG);
        m_host      = (String)m_UpdateProp.get(HOST_TAG);
        m_RemoteTarget    = (String)m_UpdateProp.get(REMOTE_TARGET_TAG);
        m_LocalTarget    = (String)m_UpdateProp.get(LOCAL_TARGET_TAG);
        m_Dialup         = (String)m_UpdateProp.get(DIALUP_TARGET_TAG);
        return(m_user != null && 
               m_password != null &&
               m_host != null &&
               m_LocalTarget != null &&
               m_RemoteTarget != null );
    }
    
    protected  boolean loadProperties() 
    {
        try {
            FileInputStream TheFile = new FileInputStream(PROPERTY_FILE_NAME);
            m_UpdateProp.load(TheFile);
        }
        catch(IOException ex) {
            return(false);
        }
        return(true);
    }
    
    protected boolean uploadDirectory()
    {
        boolean success = true;
        try {
            setTask("Creating Manifest");
            File[] TheFiles = Manifest.getManifestFiles(m_LocalTarget);
            Manifest.writeFilesManifest(TheFiles,m_LocalTarget);
            DirectoryStructure TheStructure = new DirectoryStructure(new File(m_LocalTarget),null);
            
            String testDirectory = m_LocalTarget.replace(File.separatorChar,'/') + "/";
            if(makeConnection()) {
                setTask("Creating Remote Directories");
                m_Client.guaranteeDirectories(TheStructure);
                //
                // Step 1 do full upload to temp files
                for(int i = 0; i < TheFiles.length; i++) {
                    String test = TheFiles[i].getParent();
                    setTask("Uploading " + TheFiles[i].getName());
                    if(!m_Client.safeUploadTempFile(TheFiles[i],testDirectory,m_RemoteTarget)) 
                    {
                        success = false;
                        break; // stop on failure
                    }
                }
                //
                // Step 2 rename all files
                if(success) {
                    for(int i = 0; i < TheFiles.length; i++) {
                        setTask("Uploading " + TheFiles[i].getName());
                        if(!m_Client.safeRenameTempFile(TheFiles[i],testDirectory,m_RemoteTarget)) 
                        {
                            success = false;
                            break; // stop on failure
                        }
                    }
                }
                if(success) // now upload the Manifest
                    success = m_Client.safeUploadFile(new File(Manifest.MANIFEST_NAME),testDirectory,m_RemoteTarget);
            }
            else {
                success = false;
            }
        }
        finally {
            m_Client.closeConnection();
        }
        return(success);
    }

    protected boolean downloadDirectory()
    {
        boolean ret = false; // pessamistic
        setTask("Downloading");
        if(m_Client.makeConnection(m_host) != null) {
            m_Client.closeConnection();
            return(false); // fail
        }
        try {
            if(!m_Client.setUser(m_user,m_password)) return(false); // fail
            if(!m_Client.setDirectory(m_RemoteTarget)) return(false); // fail
            
            setTask("Getting Manifest");
            File oldCopy = new File(Manifest.REMOTE_MANIFEST_NAME);
            oldCopy.delete();
            if(oldCopy.exists())
                return(false); // fail
            if(!m_Client.downloadAs(Manifest.MANIFEST_NAME,Manifest.REMOTE_MANIFEST_NAME))
                return(false); // fail
            
            setTask("Reading Manifest");
            IFileDescriptor[] RemoteFiles = Manifest.getDescribedFiles(Manifest.REMOTE_MANIFEST_NAME);
            DirectoryStructure RequiredDirectories = Manifest.getDescribedDirectories(RemoteFiles);
            if(RequiredDirectories.guaranteeExistance(m_LocalTarget)) {
                for(int i = 0; i < RemoteFiles.length; i++) {
                    if(!downloadToTempAsNeeded(RemoteFiles[i]))
                        return(false); // fail and give up
                }
                setTask("Renaming Files");
                for(int i = 0; i < RemoteFiles.length; i++) {
                    if(!deleteAndRenameAsNeeded(RemoteFiles[i]))
                        return(false); // fail and give up
                }
            }
            ret = true; // success
        } // end try
        finally {
            m_Client.closeConnection();
        }
        setTask("Done");
        return(ret);
    }
    
    protected boolean downloadAsNeeded(IFileDescriptor SomeFile)
    {
        String testName = m_LocalTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
        boolean DownloadRequired = true;
        File testFile = new File(testName);
        if(testFile.exists()) {
            if(SomeFile.getDateString().equals(Manifest.makeTimeString(testFile.lastModified()))) 
                DownloadRequired = false;
        }
        if(!DownloadRequired) 
            return(true); // all OK
        return(downloadDescribedFile(SomeFile));
    }
    protected boolean downloadToTempAsNeeded(IFileDescriptor SomeFile)
    {
        String remoteName;
        String testName;
        if(SomeFile.getPath() == null || SomeFile.getPath().length() == 0) {
           remoteName = m_RemoteTarget  + "/" + SomeFile.getName();
           testName = m_LocalTarget  + "/" + SomeFile.getName();
        }
        else {
           remoteName = m_RemoteTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
           testName = m_LocalTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
        }
        String tempName = testName + ".tmp";
        File testFile = new File(testName);
        File tempFile = new File(tempName);
        
        try {
            tempFile.delete();
            if(tempFile.exists())
                return(false); // huh - cannot delete temp file
            
            if(testFile.exists()) {
                 if(SomeFile.getDateString().equals(Manifest.makeTimeString(testFile.lastModified()))) 
                    return(true); // all OK
            }
            setTask("Downloading " + SomeFile.getName());
            // do download here
            return(m_Client.downloadAs(remoteName,tempName));
        }
        catch(SecurityException ex) 
        {
            return(false); // fail
        }
    }


    protected boolean deleteAndRenameAsNeeded(IFileDescriptor SomeFile)
    {
        String testName;
        if(SomeFile.getPath() == null || SomeFile.getPath().length() == 0) {
           testName = m_LocalTarget  + "/" + SomeFile.getName();
        }
        else {
           testName = m_LocalTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
        }
        String tempName = testName + ".tmp";
        File testFile = new File(testName);
        File tempFile = new File(tempName);
        
        if(!tempFile.exists())
            return(true); // nothing to do
            
        if(testFile.exists()) {
            if(!testFile.delete())
                return(false); // fail
        }
        if(!tempFile.renameTo(testFile))
            return(false); // fail
        String ProperDate = SomeFile.getDateString();
        try {
            String modString = "touch " + testName + " -t " + ProperDate;
            Process  p = Runtime.getRuntime().exec(modString);
            return(p != null); // success
        }
        catch(IOException ex) 
        {
            return(false); // fail
        }
        catch(SecurityException ex) 
        {
            return(false); // fail
        }
    }
    
    protected boolean downloadDescribedFile(IFileDescriptor SomeFile)
    {
        String remoteName;
        String testName;
        if(SomeFile.getPath() == null || SomeFile.getPath().length() == 0) {
           remoteName = m_RemoteTarget  + "/" + SomeFile.getName();
           testName = m_LocalTarget  + "/" + SomeFile.getName();
        }
        else {
           remoteName = m_RemoteTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
           testName = m_LocalTarget + "/" + SomeFile.getPath() + "/" + SomeFile.getName();
        }
        setTask("Downloading " + SomeFile.getName());
        String tempName = testName + ".tmp";
        File testFile = new File(testName);
        File tempFile = new File(tempName);
        
        tempFile.delete(); // make sure no prevoius copies
        // do download here
        if(!m_Client.downloadAs(remoteName,tempName));
        
        if(testFile.exists()) {
            if(!testFile.delete())
                return(false); // fail
        }
        if(!tempFile.renameTo(testFile))
            return(false); // fail
        String ProperDate = SomeFile.getDateString();
        try {
            String modString = "touch " + testName + " -t " + ProperDate;
            Process  p = Runtime.getRuntime().exec(modString);
            return(p != null); // success
        }
        catch(IOException ex) 
        {
            return(false); // fail
        }
        catch(SecurityException ex) 
        {
            return(false); // fail
        }
    }
    
    protected static boolean testDates()
    {
        String test;
        
        File t1 = new File("jan199.txt");
        File t2 = new File("jan299.txt");
        File t3 = new File("jan298.txt");
        File t4 = new File("jan185.txt");
        test = Manifest.makeTimeString(t1.lastModified());
        test = Manifest.makeTimeString(t2.lastModified());
        test = Manifest.makeTimeString(t3.lastModified());
        test = Manifest.makeTimeString(t4.lastModified());
        return(true);
    }
    
    public static void main(String[] args) 
    {
        DirectUpdater theClient = new DirectUpdater();

        String testDir = null;
  //      if(testDates())
   //         return;
        // test code
        if(args == null || args.length == 0) {
            theClient.setUpload(false);
        }
        else {
            if(args[0].equalsIgnoreCase("upload"))
                theClient.setUpload(true);
            else
                theClient.setUpload(false);
        }
        theClient.go();
    }
    
}
