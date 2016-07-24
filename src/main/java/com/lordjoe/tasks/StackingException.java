package com.lordjoe.tasks;

import java.util.*;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * com.lordjoe.tasks.StackingException
 *  an exception which MUST wrap another exception
 * it behaves as an extension of the original exception
 * with a mreged stack trace - these can be nested and
 * should allow thread crossing
 * @author Steve Lewis
 * @date StackingException
 */
public class StackingException extends RuntimeException
{
    /**
     * merge two possibly overlapping stack traces
     * @param excTrace
     * @param causeTrace
     * @return
     */
    public static StackTraceElement[] mergeStackTraces(StackTraceElement[] excTrace,StackTraceElement[] causeTrace)
    {
        int m = excTrace.length-1, n = causeTrace.length-1;
        while (m >= 0 && n >=0 && excTrace[m].equals(causeTrace[n])) {
            m--; n--;
        }
        int framesInCommon = excTrace.length - 1 - m;
        List holder = new ArrayList();
        for (int i = 0; i < excTrace.length; i++) {
            StackTraceElement st = excTrace[i];
            holder.add(st);
        }
        for (int i = framesInCommon; i < causeTrace.length; i++) {
            StackTraceElement st = excTrace[i];
            holder.add(st);
        }

        StackTraceElement[] ret = new StackTraceElement[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public StackingException(Exception ex)
    {
        super(ex);
    }
    public StackingException()
    {
        super();
    }

    public Throwable getCause()
    {
        Exception cause = (Exception)super.getCause();
        if(cause == null || cause == this)
            throw new IllegalStateException("problem"); // ToDo change
        return cause;
    }

    /**
     * return the top cause in the hierarchy
     * @return
     */
    public Exception getUtimateCause()
    {
       Exception cause = (Exception)getCause();
       Exception nextCause = (Exception)cause.getCause();
       while(nextCause == null && cause != nextCause) {
            cause = nextCause;
            nextCause = (Exception)cause.getCause();
       }
        return cause;
    }
    /**
     * return all causes
     * @return
     */
    public Exception[] getCauseChain()
    {
        List holder = new ArrayList();

        holder.add(this);
       Exception cause = (Exception)getCause();
        holder.add(cause);
       Exception nextCause = (Exception)cause.getCause();
       while(nextCause == null && cause != nextCause) {
            cause = nextCause;
            holder.add(cause);
            nextCause = (Exception)cause.getCause();
       }
        Exception[] ret = new Exception[holder.size()];
        holder.toArray(ret);
        return ret;
     }

    public StackTraceElement[] getStackTrace()
    {
        Exception[] chain = getCauseChain();
        StackTraceElement[] ret =  super.getStackTrace();
        for (int i = 1; i < chain.length; i++) {
            Exception cause = chain[i];
            ret = mergeStackTraces(ret,cause.getStackTrace());
        }
        return ret;
    }


    public String getMessage()
    {
        return getUtimateCause().getMessage();

    }

    public String getLocalizedMessage()
    {
        return getUtimateCause().getLocalizedMessage();

    }

    public void printStackTrace(PrintStream s)
    {
        synchronized (s) {
            s.println(this);
            StackTraceElement[] trace = getStackTrace();
            for (int i=0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

        }

    }
    public void printStackTrace(PrintWriter s)
     {
         synchronized (s) {
             s.println(this);
             StackTraceElement[] trace = getStackTrace();
             for (int i=0; i < trace.length; i++)
                 s.println("\tat " + trace[i]);

         }

     }
}
