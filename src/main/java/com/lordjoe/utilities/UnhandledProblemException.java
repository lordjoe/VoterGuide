package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

/**
 * com.lordjoe.Utilities.UnhandledProblemException
 * @2uthor Steve Lewis
 * @date Dec 7, 2003
 */
public class UnhandledProblemException extends WrapperException
{
    public static Class THIS_CLASS = UnhandledProblemException.class;
    public static final UnhandledProblemException[] EMPTY_ARRAY = {};
    public UnhandledProblemException(Throwable ex)
    {
        super(ex);
    }

    public UnhandledProblemException(Throwable ex,boolean logException)
    {
        super(ex);
        if(logException)
            LogUtilities.logError(this,ex);
    }
    public UnhandledProblemException(String message,Throwable ex,boolean logException)
    {
        super(message,ex);
        if(logException)
            LogUtilities.logError(this,ex);
    }
}

