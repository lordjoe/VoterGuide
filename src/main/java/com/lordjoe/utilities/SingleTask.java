package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

/**
 * Implements a task which is usually run in a queue
 */
public abstract class SingleTask implements Runnable {
    private static ApacheThreadPool gPool;

    protected synchronized ApacheThreadPool getPool() {
        if (gPool == null) {
            ApacheThreadPool thePool = new ApacheThreadPool("SingleTask");
            thePool.start();
            gPool = thePool;
        }
        return (gPool);
    }

    public static final int BASE_INTERVAL = 250;
    private boolean m_Finished;
    private Throwable m_Exception;
    private Object m_Return;

    public void run() {
        doRun();
    }


    public Object runWithTimeout(int Timeout) throws ThreadCrossingException {
        ApacheThreadPool ThePool = getPool();
        ITaskWatcher Watcher = ThePool.runItWithWatch(this);
        Watcher.waitCompletion(this, Timeout);
        if (getException() != null)
            throw new ThreadCrossingException(getException());
        return (m_Return);
    }

    public Object runInQueue(ITaskQueue TheQueue, int Timeout)
            throws ThreadCrossingException, TaskTimeoutException {
        TheQueue.runIt(this);
        if (waitRun(Timeout)) {
            if (getException() != null)
                throw new ThreadCrossingException(getException());
            return (getReturn());
        }
        throw new TaskTimeoutException();
    }

    protected synchronized void doRun() {
        try {
            setReturn(runTask());
        } catch (Throwable ex) {
            // ex.printStackTrace(); // - MWR
            setException(ex);
        }
        setFinished();
        notifyAll();
    }

    protected abstract Object runTask();

    /**
     * wait for completion
     * @param Timeout positive number of milisec to wait
     *
     */
    public synchronized boolean waitRun(int Timeout) {
        int Times = 0;
        while (Timeout > Times) {
            try {
                wait(BASE_INTERVAL);
            } catch (InterruptedException ex) {
                throw new ThreadInterruptedException();
            }
            if (isFinished())
                return (true);
            Times += BASE_INTERVAL;
        }
        return (isFinished());
    }

    public Object getReturn() {
        if (!isFinished())
            throw new IllegalStateException("Cannot get return until finished");
        return (m_Return);
    }

    public Throwable getException() {
        //      if(!isFinished())
        //         throw new IllegalStateException("Cannot get exception until finished");
        if (m_Exception == null) // easier for debugging
            return (null);
        return (m_Exception);
    }

    protected void setException(Throwable t) {
        m_Exception = t;
    }

    public boolean isFinished() {
        return (m_Finished);
    }

    protected void setFinished() {
        m_Finished = true;
    }

    protected void setReturn(Object ret) {
        m_Return = ret;
    }

}
