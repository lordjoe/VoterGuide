//=====================================================================
//
//	PKG:	nettools.protocol
//	FILE:	OCTestConnection.java
//	AUTHOR:	Steve Lewis
//	DATE:	3 fewb 99
//
//	This class will tests the viability of the connection by using the NTP
//  protocol to get the time from tick.usno.navy.mil
//
//=====================================================================

package com.lordjoe.lib.ftp;		

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.Date;
import com.lordjoe.utilities.Assertion;

public class TestConnection
{
    public static final int PING_PORT = 7;
    public static final int IPPORT_NTP = 123;
    public static final int BUFFER_SIZE = 48;
    public static final int TIMEOUT_INTERVAL = 3000; // 3 sec
    private String m_host[] = { "bigben.cac.washington.edu" , "tick.usno.navy.mil" };
    private boolean m_SNTP = true;    
    
    
    private int m_Timeout = TIMEOUT_INTERVAL; // millisec for timeout
    private String[] m_IPAddress;  
	private InetAddress[] m_INet;			// who the packet is going to be sent to.
	private DatagramSocket m_Socket= null;		// this will listen for the return packet.
	byte[] m_inBuffer  = new byte[BUFFER_SIZE];		// buffer for in packet
	DatagramPacket m_InPacket;
	byte[] m_outBuffer = new byte[BUFFER_SIZE];		// buffer for out packet
	DatagramPacket m_OutPacket;
	private int m_State; // state of the current test negative is bad
	private Thread m_TestThread;
	
	/* 
	   The original creation can timeout 
	   of an internet lookup fails.
	   This approach using thread and watchdog thread
	   will always
	   timeout even of the original thread blocks
	*/
	public static boolean testInternetConnection()
	{
	    ConnectionTimer TheTest = new ConnectionTimer();
	    Thread TestThread = new Thread(TheTest,"Connection Test Thread");
	    TestThread.setDaemon(true);
	    TestThread.start();
        try {
            TestThread.join(TIMEOUT_INTERVAL);
        }
            // We do not care if this happens
        catch( InterruptedException ex ) {}
        if(TestThread.isAlive())
            return(false);
        else
            return(TheTest.succeeded());
    }
    
    protected static class ConnectionTimer implements Runnable
    {
        private boolean m_success;
        
        public void run() 
        {
            m_success = true;
            TestConnectionRunner Test = new TestConnectionRunner();
            Thread TestConnectionThread = new Thread(Test);
            TestConnectionThread.setDaemon(true);
            TestConnectionThread.start();
            try {
                TestConnectionThread.join(TIMEOUT_INTERVAL);
            }
             // We do not care if this happens
           catch( InterruptedException ex ) {}
            
            if(TestConnectionThread.isAlive())
                m_success = false; // timed out
            else
                m_success = Test.succeeded();
        }
        public boolean succeeded() { return(m_success); }
    // end inner class PingTimer
    }
    protected static class TestConnectionRunner implements Runnable
    {
        private boolean m_success;
	    public void run() {
            TestConnection TheTest = new TestConnection();
            m_success = TheTest.test(); // ping remote system
	    }
        public boolean succeeded() { return(m_success); }
	// end inner class TestConnectionRunner
	}
    
	public TestConnection() 
	{
	    setConnections(m_host);
	}
	
/*	public TestConnection(String Address) 
	{
	    this();
	    setConnection(Address);
	}
	*/
   
    protected void setConnections(String[] Address)
    {
        if(m_IPAddress == null) {
            m_IPAddress = new String[Address.length] ;
            m_INet = new InetAddress[Address.length];			// who the packet is going to be sent to.
        }
        for(int i = 0; i < Address.length; i++) {
            setConnection(Address[i],i);
        }
    }
    
        
        
    protected void setConnection(String Address,int i)
    {
        if(Address.equals(m_IPAddress[i]))
            return;
        m_IPAddress[i] = Address;
		try{
			m_INet[i] = InetAddress.getByName(m_IPAddress[i]);
			String test = m_INet[i].getHostAddress();
			test = m_INet[i].getHostName();
		}
		catch(UnknownHostException e)
		{
 			m_INet = null;
 			m_InPacket = null;
 			m_OutPacket = null;
			throw new ConnectionLostException("Unknown Host Exception.");
		}
    }
    
    public synchronized boolean test() 
    {
        if(m_TestThread == null) {
            m_TestThread = new Thread(new PingTimer(),"Ping Timer");
            m_TestThread.setDaemon(true);
            m_TestThread.start();
        }
        while(m_State == 0) {
            try {
                wait();
            }
            // We do not care if this happens
            catch( InterruptedException ex ) {}
        }
        m_TestThread = null; // we get to do this again
        return(m_State > 0);
    }

    public synchronized void setState(int newState) 
    {
        m_State = newState;
        if(m_State != 0)
            Assertion.doNada();
        notifyAll();
    }

    
    class PingTimer implements Runnable
    {
        public void run() 
        {
            Pinger Test = new Pinger();
            Thread PingThread = new Thread(Test,"FTP Timer");
            PingThread.setDaemon(true);
            PingThread.start();
            try {
                PingThread.join(m_Timeout);
            }
             // We do not care if this happens
           catch( InterruptedException ex ) {}
            
            if(PingThread.isAlive())
                setState(-6); // timed out
        }
    // end inner class PingTimer
    }
    
    class Pinger implements Runnable
    {
	    public void run() {
	        int TryCount = 0;
	        for(int i = 0; i < m_INet.length; i++) { 
	            if(pingINet(m_INet[i]))
	                return;
	            else
	                TryCount++;
	        }   
	    }
	
	
        @SuppressWarnings(value = "deprecated")
	    protected boolean pingINet(InetAddress testNet)
	    {
		    setState(0); // unsure
	    /** Preforms the actual ping, or poll, to the specified address. */	
		    try{													// attempt to open the connection.
			    m_Socket = new DatagramSocket();
			    m_Socket.setSoTimeout(m_Timeout);						// set the socket option timeout.
		    }catch(IOException e){
			    // System.out.println("ERROR: Could not establish connection to remote host.");
			    setState(-2); // will cause failure later
			    return(false); // fail
		    }
		    String Set = "Hello World from one lonely java program seeking a way o send data";
            m_outBuffer[0] = 0xB;//client, version 1
            m_outBuffer[1] = 0;
            m_outBuffer[2] = 0;
            m_outBuffer[3] = 0;		    
    	    /* Send the packet or catch any errors. */
		    m_InPacket = new DatagramPacket(m_inBuffer, BUFFER_SIZE);		// create the UDP.
		    m_OutPacket = new DatagramPacket(m_outBuffer, BUFFER_SIZE, testNet, IPPORT_NTP);		// create the UDP.

		    try{
			    m_Socket.send(m_OutPacket);
		    }catch(IOException e){
			    // System.out.println("ERROR: Socket connection failed sending data.");
			    setState(-3);  // will cause failure later
			    return(false); // fail
		    }
    					
	        /* Receive the packet or catch any errors. */
        	
		        try{
			        m_Socket.receive(m_InPacket);
		        }catch(InterruptedIOException e){
			        // System.out.println("ERROR: Socket connection failed receiving data.");
			        setState(-4);  // will cause failure later
		    	    return(false); // fail
		        }catch(IOException e){
			    // System.out.println("IO exception");
			        setState(-5);  // will cause failure later
    			    return(false); // fail
		        }
		        // should respond with GMT
		        Date test = makeDate(m_inBuffer);
		        int testtime = test.getDate();
		        testtime = test.getMonth();
		        testtime = test.getHours();
		        testtime = test.getMinutes();
		        testtime = test.getYear();
		        setState(1); // all OK
		        return(true); // success
	        }//end ping()
	// end inner class Pinger
    }
// end class OCTestConnection
    protected Date makeDate(byte[] buf)
    {
        int[] ubuf = new int[6];
        if (buf[0]>=0)
                ubuf[5]=buf[0];
        else
                ubuf[5]=256+buf[0];
                
        if (((buf[1]<1)||(buf[1]>15)) ||
                ((ubuf[5]&0xC0)==0xC0) || //test LI field
                ((buf[40]==0)&&(buf[41]==0)&&(buf[42]==0)&&(buf[43]==0)))
                    return(new Date());
                
        for (int i=0; i<5; i++) {
            if (buf[40+i]>=0)
                    ubuf[i]=buf[40+i];
            else
                    ubuf[i]=256+buf[40+i];
            }
            return new Date((long)((ubuf[0]*256.0*65536+ubuf[1]*65536+ubuf[2]*256+ubuf[3])-(60.0*60*24*365.2422*70+3974.4))*1000+(long)(ubuf[4]/256+.5));
            //bugbug we didn't use the seconds fraction in a standard way
       }
}

