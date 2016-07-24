package com.lordjoe.exceptions;

import java.util.*;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * com.lordjoe.exceptions.WrappingException
 *  an exception which MUST wrap another exception
 * it behaves as an extension of the original exception
 * with a mreged stack trace - these can be nested and
 * should allow thread crossing
 * @author Steve Lewis
 * @date WrappingException
 */
public class WrappingException extends RuntimeException
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
        for (int i = framesInCommon; i < Math.min(excTrace.length,causeTrace.length); i++) {
            StackTraceElement st = excTrace[i];
            holder.add(st);
        }

        StackTraceElement[] ret = new StackTraceElement[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public WrappingException(Throwable ex)
    {
        super(ex);
    }
    public WrappingException()
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
     * Initializes the <i>cause</i> of this throwable to the specified value.
     * (The cause is the throwable that caused this throwable to get thrown.)
     *                                                                                          <p>
     * <p>This method can be called at most once.  It is generally called from
     * within the constructor, or immediately after creating the
     * throwable.  If this throwable was created
     * with {@link #Throwable(Throwable)} or
     * {@link #Throwable(String,Throwable)}, this method cannot be called
     * even once.
     *
     * @param cause the cause (which is saved for later element by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @return a reference to this <code>Throwable</code> instance.
     * @throws IllegalArgumentException if <code>cause</code> is this
     *                                  throwable.  (A throwable cannot be its own cause.)
     * @throws IllegalStateException    if this throwable was
     *                                  created with {@link #Throwable(Throwable)} or
     *                                  {@link #Throwable(String,Throwable)}, or this method has already
     *                                  been called on this throwable.
     * @since 1.4
     */
    public synchronized Throwable initCause(Throwable cause)
    {
        Throwable oldcause = getCause();
        if(oldcause != null && oldcause != this)
            throw new IllegalStateException("Cause can only be set once"); 

        return super.initCause(cause);

    }

    /**
     * return the top cause in the hierarchy
     * @return
     */
    public Throwable getUtimateCause()
    {
       Throwable cause = (Throwable)getCause();
       Throwable nextCause = (Exception)cause.getCause();
       while(nextCause != null && cause != nextCause) {
            cause = nextCause;
            nextCause = (Throwable)cause.getCause();
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


    /**
     * Returns a short description of this throwable.
     * If this <code>Throwable</code> object was created with a non-null detail
     * message string, then the result is the concatenation of three strings:
     * <ul>
     * <li>The name of the actual class of this object
     * <li>": " (a colon and a space)
     * <li>The result of the {@link #getMessage} method for this object
     * </ul>
     * If this <code>Throwable</code> object was created with a <tt>null</tt>
     * detail message string, then the name of the actual class of this object
     * is returned.
     *
     * @return a string representation of this throwable.
     */
    public String toString()
    {
        return super.toString();

    }

    public String getMessage()
    {
        Throwable utimateCause = getUtimateCause();
        return utimateCause.getMessage();

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
