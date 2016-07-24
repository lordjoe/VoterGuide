package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.NullLogger
 * logger that does nothing
 * @author Steve Lewis
 * @date Oct 9, 2006
 */
public class NullLogger implements ILogSystem
{
    public static final NullLogger[] EMPTY_ARRAY = {};
    public static final Class THIS_CLASS = NullLogger.class;

    /**
     * log a debug message - in release mode these messages may not be logged
     *
     * @param Message non-null message
     */
    public void logDebug(String Message)
    {
       // Do nothing

    }

    /**
     * log a  message
     *
     * @param Message non-null message
     */
    public void logMessage(String Message)
    {
        // Do nothing

    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public void logWarn(String Message)
    {
        // Do nothing

    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public void logError(String Message)
    {
        // Do nothing

    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public void logError(String Message, Throwable error)
    {
        // Do nothing

    }

}
