package com.lordjoe.utilities;

/**
 * DefaultLoanedObject - token used to represent a loan
 * @see ILoanedObject
 * @author Steve Lewis
 */
public class DefaultLoanedObject extends Thread implements ILoanedObject {
    private Thread m_BorrowingThread;
    private int m_Timeout;
    private Runnable m_TimeoutAction;

    public DefaultLoanedObject() {
        m_BorrowingThread = null;
        m_TimeoutAction = null;
        setDaemon(true);

    }

    /**
     * Constructor specifying timeout action
     * @param TimeoutAction non-null runnable to run if the loan expires
     */
    public DefaultLoanedObject(Runnable TimeoutAction) {
        this();
        m_TimeoutAction = TimeoutAction;

    }

    /**
     * This is called upon timeout - i.e the loan has expired
     * The action is to run any timeout action
     */
    public void onTimeout() {
        if (m_BorrowingThread == null)
            return;
        if (!m_BorrowingThread.isAlive()) {
            setBorrowingThread(null); // cannot punish the dead
            return;
        }

        if (m_TimeoutAction != null)
            m_TimeoutAction.run();
    }

    /**
     * call at the start of a loan to get the clock ticking
     * @param millisec number millisec to wait non-negative
     * @param action non-null runnable to run if the loan expires
     */
    public void startTimeout(int millisec, Runnable action) {
        m_Timeout = millisec;
        m_TimeoutAction = action;
        setBorrowingThread(Thread.currentThread());
        notifyAll();
    }

    /**
     * call at the start of a loan to get the clock ticking
     * @param millisec number millisec to wait non-negative
     */
    public synchronized void startTimeout(int millisec) {
        startTimeout(millisec, m_TimeoutAction);
    }

    /**
     * this is called to stop the cloak i.e. the loan
     * is returned
     */
    public synchronized void stopTimeout() {
        m_BorrowingThread = null;
        notifyAll();
    }

    /**
     * call to set the borrowing thread
     * @param in non-null thread doing the borrowing
     */
    public void setBorrowingThread(Thread in) {
        m_BorrowingThread = in;
    }

    /**
     * call to get the borrowing thread
     * @return   non-null thread doing the borrowing
     */
    public Thread getBorrowingThread() {
        return (m_BorrowingThread);
    }

    protected synchronized void waitForLoan() throws InterruptedException {
        while (getBorrowingThread() == null) {
            wait();
        }
    }

    protected synchronized void waitForReturn() throws InterruptedException {
        if (m_Timeout > 0)
            wait(m_Timeout);
        else
            wait();
    }


    public void run() {
        try {
            while (true) {
                waitForLoan();
                waitForReturn();
                if (getBorrowingThread() != null)
                    onTimeout();
                setBorrowingThread(null);
            }
        } catch (InterruptedException ex) {
        }
    }

}
