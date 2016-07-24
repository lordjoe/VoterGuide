package com.lordjoe.tasks;

import java.util.*;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * com.lordjoe.tasks.MarkerException
 *  an exception which marks the stack
 * If is is associated with a cause it behaves as a stack to
 * the ultimate cause - even crossing threads
 * it behaves as an extension of the original exception
 * with a merged stack trace - these can be nested and
 * should allow thread crossing
  * @author Steve Lewis
 * @date MarkerException
 */

public class MarkerException extends RuntimeException
{

    /**
     * return the top cause in the hierarchy
     * @return
     */
    public static Exception getUtimateCause( Exception start)
    {
       Exception cause = (Exception)start.getCause();
       Exception nextCause = (Exception)cause.getCause();
       while(nextCause != null && cause != nextCause) {
            cause = nextCause;
            nextCause = (Exception)cause.getCause();
       }
        return cause;
    }

    public MarkerException(Throwable ex)
    {
         super(ex);
        if(ex != null)
            ex.printStackTrace();
    }

    public MarkerException()
    {
        super();
    }

    public void setCause(Throwable newCause)
    {
        Exception oldcause = (Exception)super.getCause();
        if(oldcause != null && oldcause != this)   {            
             throw new IllegalStateException("cannot reset cause");
        }
        try {
           this.initCause(newCause);
        }
        catch(IllegalStateException ex) {
            throw new MarkerException(newCause);
        }
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
        return getUtimateCause(this);
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
       while(nextCause != null && cause != nextCause) {
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
            ret = TaskUtilities.mergeStackTraces(ret,cause.getStackTrace());
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
            s.println(getUtimateCause());
            StackTraceElement[] trace = getStackTrace();
            for (int i=0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

        }

    }
    public void printStackTrace(PrintWriter s)
     {
         synchronized (s) {
             s.println(getUtimateCause());
             StackTraceElement[] trace = getStackTrace();
             for (int i=0; i < trace.length; i++)
                 s.println("\tat " + trace[i]);

         }

     }
}
