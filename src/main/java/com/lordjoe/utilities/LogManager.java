package com.lordjoe.utilities;

import org.apache.log4j.*;

import java.net.*;
import java.util.*;

/**
 *
 */
public abstract class LogManager
{
    public static final Priority ERROR = Priority.ERROR;
    public static final Priority WARN = Priority.WARN;
    public static final Priority INFO = Priority.INFO;
    public static final Priority DEBUG = Priority.DEBUG;

    private static ILogSystem gDefaultLogger;
    private static ILogSystem[] gRegisteredLoggers = ILogSystem.EMPTY_ARRAY;

    public static final String DEFAULT_CATEGORY = "default";

    /**
     * replace the default logger
     * @param newLogger non-null logger
     */
    public static synchronized void setDefaultLogger(ILogSystem newLogger)
    {
        gDefaultLogger = newLogger;
    }

    /**
     * add a logger which will log in addition to the default logger
     * @param newLogger
     */
    public static synchronized void addLogger(ILogSystem newLogger)
    {
        ILogSystem[] replace = new ILogSystem[gRegisteredLoggers.length + 1];
        System.arraycopy(gRegisteredLoggers, 0, replace, 0, gRegisteredLoggers.length);
        replace[gRegisteredLoggers.length] = newLogger;
        gRegisteredLoggers = replace;
    }

    /**
     * log a debug message - in release mode these messages may not be logged
     *
     * @param Message non-null message
     */
    public static void logDebug(String Message)
    {
        gDefaultLogger.logDebug(Message);
        for (int i = 0; i < gRegisteredLoggers.length; i++) {
            ILogSystem registeredLogger = gRegisteredLoggers[i];
            registeredLogger.logDebug(Message);
        }
    }

    /**
     * log a  message
     *
     * @param Message non-null message
     */
    public static void logMessage(String Message)
    {
        gDefaultLogger.logMessage(Message);
        for (int i = 0; i < gRegisteredLoggers.length; i++) {
            ILogSystem registeredLogger = gRegisteredLoggers[i];
            registeredLogger.logMessage(Message);
        }
    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public static void logWarn(String Message)
    {
        gDefaultLogger.logWarn(Message);
        for (int i = 0; i < gRegisteredLoggers.length; i++) {
            ILogSystem registeredLogger = gRegisteredLoggers[i];
            registeredLogger.logWarn(Message);
        }
    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public static void logError(String Message)
    {
        gDefaultLogger.logError(Message);
        for (int i = 0; i < gRegisteredLoggers.length; i++) {
            ILogSystem registeredLogger = gRegisteredLoggers[i];
            registeredLogger.logError(Message);
        }
    }

    /**
     * log a waqrning message
     *
     * @param Message non-null message
     */
    public static void logError(String Message, Throwable error)
    {
        gDefaultLogger.logError(Message, error);
        for (int i = 0; i < gRegisteredLoggers.length; i++) {
            ILogSystem registeredLogger = gRegisteredLoggers[i];
            registeredLogger.logError(Message, error);
        }
    }

    /**
     * should use system properties to set up a default logger but
     * there is an expectation that this will be replaced
     */
    private static class DefaultLogger extends Category implements ILogSystem
    {
        public DefaultLogger()
        {
            super(DEFAULT_CATEGORY);
        }

        /**
         * log a debug message - in release mode these messages may not be logged
         *
         * @param Message non-null message
         */
        public void logDebug(String Message)
        {
            log(DEBUG, Message);
        }

        /**
         * log a  message
         *
         * @param Message non-null message
         */
        public void logMessage(String Message)
        {
            log(INFO, Message);
        }

        /**
         * log a waqrning message
         *
         * @param Message non-null message
         */
        public void logWarn(String Message)
        {
            log(WARN, Message);
        }

        /**
         * log a waqrning message
         *
         * @param Message non-null message
         */
        public void logError(String Message)
        {
            log(ERROR, Message);
        }

        /**
         * log a waqrning message
         *
         * @param Message non-null message
         */
        public void logError(String Message, Throwable error)
        {
            log(ERROR, Message, error);
        }


    }

}
