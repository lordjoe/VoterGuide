package com.lordjoe.general;



/**
 * com.lordjoe.general.ExceptionUtilities
 *
 * @author slewis
 * @date May 26, 2005
 */
public class ExceptionUtilities
{
    public static final Class THIS_CLASS = ExceptionUtilities.class;
    public static final ExceptionUtilities EMPTY_ARRAY[] = {};

    /**
     * return the original cause of an exception
     * @param ex caught exception
     * @return
     */
    public static Throwable getUltimateCause(Throwable ex)
    {
        if(ex.getCause() == null)
            return ex;
        if(ex.getCause() == ex)
            return ex;
        return getUltimateCause(ex.getCause());
    }

    public static RuntimeException getRuntimeCause(Throwable ex)
    {
         Throwable cause = getUltimateCause( ex);
         if(cause instanceof RuntimeException)
             return (RuntimeException)cause;
        return
            new  RuntimeException(cause);
    }

    public static void printCauseStack(Throwable ex)
    {
        Throwable ultimateCause = getUltimateCause(ex);
             ultimateCause.printStackTrace();
    }

}
