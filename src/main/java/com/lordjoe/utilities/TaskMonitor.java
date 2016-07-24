package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

/**
 *  TaskMonitor
 * NOTE this requires org.apache.tomcat.util.ThreadPool
 * get from the Apache Tomcat distrubution
 * @author Steve Lewis
 */
public class TaskMonitor  implements INameable,Runnable {
    public static int TEST_TIME = 1000; // check each second;
    public static int DEFAULT_TIMEOUT = 5000; // 5 sec
    public static String DEFAULT_NAME = "Some Task";
    private static ILogger gLogger = StreamLogger.getConsole();

    public static ILogger getLogger() {
        return (gLogger);
    }

    public static void setLogger(ILogger in) {
        gLogger = in;
    }

    private static ApacheThreadPool gThePool = null;


    private boolean m_Completed;
    private String m_Name = DEFAULT_NAME;
    private Runnable m_OnTimeOut;
    private int m_TimeOut = DEFAULT_TIMEOUT;

    public TaskMonitor() {
        synchronized (TaskMonitor.class) {
            if (gThePool == null) {
                ApacheThreadPool ThePool = new ApacheThreadPool("TaskMonitor");
                ThePool.start();
                gThePool = ThePool;
            }
        }

    }

    public TaskMonitor(String Name, int Timeout) {
        this();
        setName(Name);
        setTimeOut(Timeout);
    }

    public TaskMonitor(String Name, int Timeout, Runnable OnTimeOut) {
        this(Name, Timeout);
        setOnTimeOut(OnTimeOut);
    }

    public void run() {
        waitCompleted(getTimeOut());
    }

    public void taskStarted() {
        gThePool.runIt(this);
    }

    public synchronized void waitCompleted(int Time) {
        long Started = System.currentTimeMillis();
        int Elapsed = 0;
        try {
            while (!isCompleted()) {
                wait(TEST_TIME);
                Elapsed = (int) (System.currentTimeMillis() - Started);
                if (Elapsed > Time)
                    break;
            }
            if (!isCompleted()) {
                if (getOnTimeOut() != null) {
                    gThePool.runIt(getOnTimeOut());
                } else {
                    getLogger().info("Task '" + getName() + "' Timed out");
                }

            }
        } catch (InterruptedException ex) {
            throw new ThreadInterruptedException("Unexpected Interrupt");
        }
    }

    public synchronized void taskCompleted() {
        m_Completed = true;
        notifyAll();
    }

    public void setCompleted(boolean doit) {
        m_Completed = doit;
    }

    public boolean isCompleted() {
        return (m_Completed);
    }

    public void setTimeOut(int doit) {
        m_TimeOut = doit;
    }

    public int getTimeOut() {
        return (m_TimeOut);
    }

    public void setOnTimeOut(Runnable doit) {
        m_OnTimeOut = doit;
    }

    public Runnable getOnTimeOut() {
        return (m_OnTimeOut);
    }

    /**
     * code to get parameter Name
     * @return <Add Comment Here>
     * @see setName
     */
    public String getName() {
        return (m_Name);
    }


    /**
     * code to set parameter Name
     * @param in <Add Comment Here>
     * @see getName
     */
    public void setName(String in) {
        m_Name = in;
    }
//- *******************
//- End Class TaskMonitor
}
