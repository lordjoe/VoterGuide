package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.util.*;

/**
 * com.lordjoe.Utilities.ThreadCrossingException
 * Wraps another exception in a runtime exception
 * @author Steve Lewis
 */
public class ThreadCrossingException extends WrapperException {
    // Needed to get Stack Trace this thread
    private Throwable m_InternalTrace;

    public ThreadCrossingException(Throwable ex) {
        super(ex);
    }

    public ThreadCrossingException(String s, Throwable ex) {
        super(s, ex);
    }

    protected void init() {
        super.init();
        m_InternalTrace = new Throwable();
    }


    protected void buildStackTraceElements() {
        String[] MyTrace = StackTrace.getThrowableStackTraceItems(this);
        Throwable Cause = getCause();
        String[] CauseTrace = StackTrace.getStackTraceItems(Cause);
        List holder = new ArrayList();
        for (int i = 0; i < MyTrace.length; i++) {
            holder.add(MyTrace[i]);
        }
        holder.add("Called In Separate Thread From:");
        for (int k = 0; k < CauseTrace.length; k++) {
            holder.add(CauseTrace[k]);
        }
        String[] Fulltrace = Util.collectionToStringArray(holder);
        setStackTraceElements(Fulltrace);
    }


    public Throwable getInternalTrace() {
        return (m_InternalTrace);
    }

    // =======================================
    // Test Section
    // =======================================
    public static void main(String[] args) {
        try {
            SingleTask TheTask = new ErrorTask1(5);
            TheTask.runWithTimeout(500000);
        } catch (WrapperException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTraceString());
        }
    }

    protected static class ErrorTask1 extends SingleTask {
        private int m_Level;

        protected ErrorTask1(int level) {
            m_Level = level;
        }

        protected Object runTask() {
            return (runFinder(Util.randomInt(5) + 1));
        }

        protected Object runFinder(int n) {
            if (n == 0) {
                if (m_Level > 0) {
                    SingleTask TheTask = new ErrorTask1(m_Level - 1);
                    return (TheTask.runWithTimeout(500000));
                } else {
                    throw new IllegalStateException("Reached the end");
                    //return("Done");
                }
            } else {
                return (runFinder(n - 1));
            }
        }

    }

}
