package com.lordjoe.exceptions;

import com.lordjoe.lang.*;
import org.apache.log4j.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.exceptions.ExceptionHandlingManager
 * exception manager - allows applications to plug logging strategies
 *
 * @author Steve Lewis
 * @date Jan 26, 2006
 */
public class ExceptionHandlingManager implements IExceptionLogger
{
    private static final Logger logger = Logger.getLogger(ExceptionHandlingManager.class);

    public static final ExceptionHandlingManager INSTANCE = new ExceptionHandlingManager();

    /**
     * turn the task into an ExceptionHandlingRunner
     *
     * @param task
     * @return
     */
    public static ExceptionHandlingRunner getExceptionHandlingRunner(Runnable task)
    {
        if (task instanceof ExceptionHandlingRunner)
            return (ExceptionHandlingRunner) task;
        else
            return new WrapperRunnable(task);

    }

    public static void addExceptionLogger(IExceptionLogger logger)
    {
        synchronized (INSTANCE) {
            INSTANCE.m_Handlers.add(logger);
        }
    }

    public static void removeExceptionLogger(IExceptionLogger logger)
    {
        synchronized (INSTANCE) {
            INSTANCE.m_Handlers.remove(logger);
        }
    }


    public static void log(Object o, Throwable ex,Thread thr)
    {
        rethrowMessagingException(ex);
        synchronized (INSTANCE) {
            INSTANCE.logException(o, ex,thr);
        }
    }

    public static void log(Object o, Throwable ex)
    {
        rethrowMessagingException(ex);
        synchronized (INSTANCE) {
            INSTANCE.logException(o, ex,null);
        }
    }

    /**
     * if ex is a messaging exception then rethrow
     *
     * @param ex
     */
    private static void rethrowMessagingException(Throwable ex)
    {
            if (ex instanceof RuntimeException)
                throw (RuntimeException) ex;
            else
                throw new RuntimeException(ex);
    }

    public static void log(Throwable ex)
     {
         rethrowMessagingException(ex);
          synchronized (INSTANCE) {
             INSTANCE.logException(null,ex,null);
         }
      }
    public static void log(Throwable ex,Thread thr)
     {
         rethrowMessagingException(ex);
         synchronized (INSTANCE) {
             INSTANCE.logException(null,ex,thr);
         }
     }

    private final List m_Handlers;
    private final Set m_AlreadyLogged;

    private ExceptionHandlingManager()
    {
        m_Handlers = Collections.synchronizedList(new ArrayList());
        m_AlreadyLogged = Collections.synchronizedSet(new HashSet());
    }

    /**
     * perform some exception logging
     *
     * @param ex mom-mull exception
     */
    public synchronized void logException(Throwable ex)
    {
        if (m_Handlers.isEmpty()) {
            logger.error(this, ex);
        }
        else {
            for (Iterator iterator = m_Handlers.iterator(); iterator.hasNext();) {
                IExceptionLogger log = (IExceptionLogger) iterator.next();
                log.logException(ex);
            }

        }
    }

    /**
     * perform some exception logging
     *
     * @param ex mom-mull exception
     */
    public synchronized void logException(Object data, Throwable ex)
    {
        // Make sure same exception logged only once
        if (m_AlreadyLogged.contains(ex))
            return;
        m_AlreadyLogged.add(ex);
        if (m_Handlers.isEmpty()) {
            logger.error(data, ex);
        }
        else {
            for (Iterator iterator = m_Handlers.iterator(); iterator.hasNext();) {
                IExceptionLogger log = (IExceptionLogger) iterator.next();
                log.logException(data, ex);
            }

        }
    }


        /**
     * perform some exception logging
     *
     * @param ex mom-mull exception
     */
    public synchronized void logException(Object data, Throwable ex,Thread thr)
    {
        String error = buildErrorString(null, ex);
        GeneralUtilities.printString(error);
         logException(data,ex);
    }
    /**
     * pop uo a dialog with a message and
     * @param message message to display
     * @param ex the error
     */
    public static void displayException(String message, Throwable ex)
    {
        displayException(null,message,ex);
    }
    /**
     * pop uo a dialog with a message and
     * @param message message to display
     * @param ex the error
     */
    public static void displayException(Component parentComponent,String message, Throwable ex)
    {
        String error = buildUserErrorString(message, ex);
        String title = message.replace('\n',' ');
        if(title.length() > 64) {
           title = message.substring(0,61) + "...";
        }
        JOptionPane.showMessageDialog(parentComponent, error, title, JOptionPane.ERROR_MESSAGE);
    }

    public static String buildUserErrorString(String message, Throwable ex)
    {
        StringBuffer sb = new StringBuffer();
        if (message != null) {
            sb.append(message);
            sb.append("\n");
        }
        sb.append(ex.getClass().getName());
        sb.append("\n");
        String errormessage = StringOps.insertNeededReturns(ex.getMessage(),80);
        sb.append(errormessage);
        sb.append("\n");
//        sb.append("Stack Trace\n");
//        StackTraceElement[] trace = ex.getStackTrace();
//        for (int i = 0; i < trace.length; i++) {
//            StackTraceElement st = trace[i];
//            sb.append("   ");
//            sb.append(st.toString());
//            sb.append("\n");
//         }
        return sb.toString();
    }


    public static String buildErrorString(String message, Throwable ex)
    {
        StringBuffer sb = new StringBuffer();
        if (message != null) {
            sb.append(message);
            sb.append("\n");
        }
        sb.append(ex.getClass().getName());
        sb.append("\n");
        String errormessage = StringOps.insertNeededReturns(ex.getMessage(),80);
        sb.append(errormessage);
        sb.append("\n");
        sb.append("Stack Trace\n");
        StackTraceElement[] trace = ex.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement st = trace[i];
            sb.append("   ");
            sb.append(st.toString());
            sb.append("\n");
         }
        return sb.toString();
    }


    public static class WrapperRunnable implements ExceptionHandlingRunner
    {
        private final Runnable m_RealTask;

        public WrapperRunnable(Runnable task)
        {
            m_RealTask = task;
        }

        public void run()
        {
            try {
                m_RealTask.run();
            }
            catch (RuntimeException ex) {
                ExceptionHandlingManager.log(ex);
                throw ex;
            }
            catch (Throwable ex) {
                ExceptionHandlingManager.log(ex);
                throw new WrappingException(ex);
            }
        }
    }


}
