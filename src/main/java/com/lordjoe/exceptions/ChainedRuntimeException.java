/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.exceptions;

/**
 * Title:        ChainedException
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:      Lordjoe

 */

/***************************************************************************************************
 *
 *  <!-- ChainedRuntimeException -->
 *
 *  A chained, unchecked exception class.
 *                                                                                              <p>
 *  See ChainedException class prologue comment for information regarding
 *  chained exceptions.
 *
 ***************************************************************************************************
 */
public class ChainedRuntimeException extends RuntimeException
{
    private static final String LINE_SEP = System.getProperty("line.separator");
    private Throwable cause = null;

    public ChainedRuntimeException()
    {
        super();
    }

    public ChainedRuntimeException(final Throwable cause)
    {
        super();
        this.cause = cause;
    }

    public ChainedRuntimeException(String message)
    {
        super(message);
    }

    public ChainedRuntimeException(String message, final Throwable cause)
    {
        super(message);
        this.cause = cause;
    }

    public Throwable getCause()
    {
        return cause;
    }

    public String getMessage()
    {
        if (cause == null)
        {
            return super.getMessage();
        }
        else
        {
            return super.getMessage() + " ; nested exception is " + LINE_SEP + "\t" +
                    cause.toString();
        }
    }

    public void printStackTrace()
    {
        this.printStackTrace(System.err);
    }

    public void printStackTrace(java.io.PrintStream ps)
    {
        if(cause == null)
        {
            super.printStackTrace(ps);
        }
        else
        {
            synchronized (ps)
            {
                ps.println(this);
                cause.printStackTrace(ps);
            }
        }
    }

    public void printStackTrace(java.io.PrintWriter pw)
    {
        if(cause == null)
        {
            super.printStackTrace(pw);
        }
        else
        {
            synchronized(pw)
            {
                pw.println(this);
                cause.printStackTrace(pw);
            }
        }
    }

} // end ChainedRuntimeException
