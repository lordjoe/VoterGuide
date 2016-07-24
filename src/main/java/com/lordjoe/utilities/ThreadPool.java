package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;


/**
 * Collection of Utilities for dealing with threads
 * @author Steve Lewis
 */
public class ThreadPool {
    public static ThreadPool gInstance = new ThreadPool();

    public static ThreadPool instance() {
        return (gInstance);
    }

    public static void runnIt(Runnable runner) {
        PoolThread TheThread = (PoolThread) getThread();
        try {
            TheThread.setTask(runner);
        } catch (Exception ex) {
            throw new WrapperException(ex);
        } finally {
            releaseThread(TheThread);
        }

    }

    public static Thread getThread() {
        return (instance().doGetThread());
    }

    public static void releaseThread(Thread t) {
        instance().doReleaseThread(t);
    }

    private PoolThread[] m_Threads = {};
    private int m_AvailableThreads;
    private Object m_Mutex = new Object();
    private ThreadGroup m_Group;

    private ThreadPool() {
        m_Group = new ThreadGroup("ThreadPool");
    }

    protected synchronized Thread doGetThread() {
        if (m_AvailableThreads == 0) {
            addThread();
        }
        return (null);
    }

    protected synchronized void doReleaseThread(Thread t) {

    }

    protected synchronized void addThread() {
        Thread added = new PoolThread();
        m_Threads = (PoolThread[]) Util.addToArray(m_Threads, added);
        m_AvailableThreads++;
    }

    protected class PoolThread extends Thread {
        private Runnable m_Task;

        protected PoolThread() {
            // Thread(m_Group,"PoolThread" + m_Threads.length);
            new Thread(m_Group, "PoolThread" + m_Threads.length);
            setDaemon(true);
        }

        public void run() {
            try {
                while (true) {
                    waitForTask();
                    runTask();
                }
            } catch (InterruptedException ex) {
            }
        }

        protected void setTask(Runnable Task) {
            m_Task = Task;
        }

        protected void runTask() {
            if (m_Task != null) {
                try {
                    m_Task.run();
                } catch (Exception ex) {
                    StackTrace.printExceptionReport(ex);
                }
                if (isInterrupted())
                // ThreadPool.this.dropThread(this);
                    ThreadPool.releaseThread(this);
            }
        }

        protected void waitForTask() throws InterruptedException {
            synchronized (m_Mutex) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    throw new ThreadInterruptedException();
                }
            }
        }
    }

}
