package com.lordjoe.utilities;

import org.apache.log4j.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.MissingResourceException;


public abstract class LogUtilities {
    public static final Priority ERROR = Priority.ERROR;
    public static final Priority WARN = Priority.WARN;
    public static final Priority INFO = Priority.INFO;
    public static final Priority DEBUG = Priority.DEBUG;

    private static final String LOG_PROPERTIES_FILE_NAME = "WEB-INF/log.properties";
    private static final String LOG_PROPERTIES_FILE_CONFIG_NAME = "log4j.properties.file";
    private static URL propURL;
    private static ILogController gController;

    public static ILogController getController()
    {
        return gController;
    }

    public static void setController(ILogController controller)
    {
        gController = controller;
    }


    public static boolean logDebugMessages(Class in)
    {
        if(gController == null)
            return(true);
        return(gController.logDebugMessages(in));
    }

    /**
     * global to turn on more intense logging
     */
    protected static boolean gDeveloperMode = false;


    public static void init()
    {

        try
        {
            String logFile = null; //DefinedSystemProperties.getInstance().getProperty( LOG_PROPERTIES_FILE_CONFIG_NAME);
            //String logFile = context.getInitParameter(LOG_PROPERTIES_FILE_CONFIG_NAME);
             if(!FileUtilities.fileExists(logFile))
                throw new IllegalStateException("problem - File " + logFile + " does not exist");
       //     propURL = context.getResource(LOG_PROPERTIES_FILE_NAME);
            propURL = new URL("file","localhost",logFile);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        if (propURL == null)
        {
            throw new MissingResourceException("Log configuration resource missing",
                    LogUtilities.class.getName(), LOG_PROPERTIES_FILE_NAME);
        }
        PropertyConfigurator.configure(propURL);
    }

    public static void setDeveloperMode(boolean in) {
        gDeveloperMode = in;
    }

    public static boolean getDeveloperMode() {
        return (gDeveloperMode);
    }

    public static void logMessage(Object in, String Message) {
        logMessage(in.getClass(), Message);
    }

    public static void logMessage(Object in, String Message, Priority level) {
        logMessage(in.getClass(), Message, level);
    }

    public static void logDebug(Class in, String Message) {
         logMessage(in, Message, DEBUG);
     }
   public static void logDebug(Object in, String Message) {
        logDebug(in.getClass(), Message);
    }
    public static void logMessage(Class in, String Message) {
        logMessage(in, Message, INFO);
    }
    public static void logInfo(Class in, String Message) {
         logMessage(in, Message, INFO);
     }
    public static void logInfo(Object in, String Message) {
         logMessage(in.getClass(), Message, INFO);
     }

    public static void logMessage(Class in, String Message, Throwable ex, Priority level) {
        getCatgory(in).log(level, Message, ex);
    }

    public static void logMessage(Class in, String Message, Priority level) {
       // if(level == DEBUG && !logDebugMessages(in))
         //   return;
        getCatgory(in).log(level, Message);
    }

    public static void logError(Object in, Throwable ex) {
        logError(in.getClass(), "", ex);
    }

    public static void logError(Object in, String Message, Throwable ex) {
        logError(in.getClass(), Message, ex);
    }

    public static void logError(Class in, String Message, Throwable ex) {
          logMessage(in, Message, ex, ERROR);
    }
    public static void logError(Object in, String Message) {
        logMessage(in, Message,ERROR);
    }

    public static void logError(Class in, String Message) {
        logMessage(in, Message,ERROR);
    }

    public static Category getCatgory(Object in) {
        return (getCatgory(in.getClass()));
    }

    public static Category getCatgory(Class in) {
        return (Category.getInstance(in));
    }

}