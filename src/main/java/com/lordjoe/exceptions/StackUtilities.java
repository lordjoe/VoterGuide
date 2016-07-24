package com.lordjoe.exceptions;

import java.io.*;
import java.util.*;

/**
 * com.cbmx.exceptions.StackUtilities
 *
 * @author Steve Lewis
 * @date Oct 28, 2005
 */
public abstract class StackUtilities
{
    private StackUtilities()
    {
    }

    public static final String[] DEFAULT_PACKAGES =
            {"com.cbmx", "com.lordjoe"};
    private static final Set gOurPackages =
            new HashSet(Arrays.asList(DEFAULT_PACKAGES));

    public static void addApprovedPackage(String s)
    {
        gOurPackages.add(s);
    }

    public static String[] getApprovedPackages()
    {
        String[] ret = new String[gOurPackages.size()];
        gOurPackages.toArray(ret);
        return ret;
    }

    /**
     * return the current stack trace
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] filterStackTrace(StackTraceElement[] in)
    {
        String[] approved = getApprovedPackages();
        List holder = new ArrayList();
        for (int i = 0; i < in.length; i++) {
            StackTraceElement elt = in[i];
            if (stackTraceApproved(elt, approved))
                holder.add(elt);
        }

        StackTraceElement[] ret = new StackTraceElement[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected static boolean stackTraceApproved(StackTraceElement elt, String[] approved)
    {
        String className = elt.getClassName();
        for (int i = 0; i < approved.length; i++) {
            String s = approved[i];
            if (className.startsWith(s))
                return true;
        }
        return false;
    }


    /**
     * return the current stack trace
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] getStackTrace()
    {
        StackTraceElement[] ret = getStackTrace(1);
        return ret;
    }

    /**
     * return the current stack trace
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] getStackTrace(int drop)
    {
        Exception temp = new Exception();
        StackTraceElement[] all = temp.getStackTrace();
        int ndropped = 1 + drop;
        StackTraceElement[] ret = new StackTraceElement[all.length - ndropped];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = all[i + ndropped];
        }
        // printStackTrace(ret);
        return ret;
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printStackTrace()
    {
        printStackTrace(System.err);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printStackTrace(PrintStream s)
    {
        StackTraceElement[] trace = getStackTrace(2);
        synchronized (s) {
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);
        }
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printStackTrace(StackTraceElement[] trace, PrintStream s)
    {
        synchronized (s) {
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);
        }
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printStackTrace(StackTraceElement[] trace)
    {
        printStackTrace(trace, System.err);
    }

    /**
     * return the current stack trace
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] getFilteredStackTrace(Throwable ex)
    {
        StackTraceElement[] trace = ex.getStackTrace();
        trace = filterStackTrace(trace);
        return trace;
    }

    /**
     * return the current stack trace
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] getFilteredStackTrace()
    {
        StackTraceElement[] ret = getFilteredStackTrace(1);
        return ret;
    }

    /**
     * return the current stack trace dropping packages
     * which are not ours
     *
     * @return non-null array of StackTraceElement
     */
    public static StackTraceElement[] getFilteredStackTrace(int drop)
    {
        StackTraceElement[] ret = getStackTrace(drop + 1);
        ret = filterStackTrace(ret);
        printStackTrace(ret);
        return ret;
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printFilteredStackTrace()
    {
        printFilteredStackTrace(System.err);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printFilteredStackTrace(PrintStream s)
    {
        StackTraceElement[] trace = getFilteredStackTrace(2);
        synchronized (s) {
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);
        }
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printFilteredStackTrace(Throwable ex)
    {
        printFilteredStackTrace(ex, System.err);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printFilteredStackTrace(Throwable ex, PrintStream s)
    {

        StackTraceElement[] trace = getFilteredStackTrace(ex);
        synchronized (s) {
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);
        }
    }

}
