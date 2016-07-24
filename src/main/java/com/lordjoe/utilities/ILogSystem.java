package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.ILogSystem
 *  interface for logging 
 * @author Steve Lewis
 * @date Oct 9, 2006
 */
public interface ILogSystem
{
    public static final ILogSystem[] EMPTY_ARRAY = {};
    public static final Class THIS_CLASS = ILogSystem.class;


    /**
     * log a debug message - in release mode these messages may not be logged
     * @param Message non-null message
     */
    public void logDebug(String Message);
    /**
     * log a  message
     * @param Message non-null message
     */
    public void logMessage(String Message);
    /**
      * log a waqrning message
      * @param Message non-null message
      */
     public void logWarn(String Message);
    /**
      * log a waqrning message
      * @param Message non-null message
      */
     public void logError(String Message);
    /**
      * log a waqrning message
      * @param Message non-null message
      */
     public void logError(String Message,Throwable error);

    
}
