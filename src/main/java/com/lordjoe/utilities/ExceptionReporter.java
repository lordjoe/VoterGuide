package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.util.*;
import java.rmi.RemoteException;
import java.lang.reflect.InvocationTargetException;

/**
 * com.onewebsystems.eagle.core.ExceptionReporter
 * handles unnesting exceptions ot generate reports
 */

public abstract class ExceptionReporter {
    private ExceptionReporter() {
    } // do not think of building one


    /*  public static String buildRemoteExceptionReport(String Message,RemoteException ex)
      {
          StringBuffer sb = new StringBuffer();
          ExceptionReporter.appendExceptionHeader(sb);
          ExceptionReporter.appendExceptionString(Message,sb,ex);
          return(sb.toString());
      }
      */

    public static RemoteException buildRemoteException(Throwable ex) {
        String RemoteMessage = "An error occured in a remote call to '" + Util.getApplicationName() +
                "' started at " + Util.getStartTimeString() + "\n";
        return (new RemoteException(RemoteMessage, ex));
    }

    public static RemoteException buildAnnotatedRemoteException(String Message, Map args) {
        return (buildAnnotatedRemoteException(Message, null, args));
    }

    public static RemoteException buildAnnotatedRemoteException(String Message, Exception ex, Map args) {
        String Annotation = buildAnnotation(Message, args);
        if (ex != null) {
            if (!(ex instanceof IStackTraceable))
                ex = new WrapperException(ex); // needed to pass stack tract across processes
            return (new RemoteException(Annotation, ex));
        } else {
            return (new RemoteException(Annotation));
        }
    }

    protected static String buildAnnotation(String Message, Map args) {
        StringBuffer sb = new StringBuffer();
        if (Message != null)
            sb.append(Message + "\n");

        appendArguments(sb, args);
        return (sb.toString());
    }


    public static RemoteException buildAnnotatedRemoteException(Exception Annotation, Map args) {
        return (buildAnnotatedRemoteException(null, Annotation, args));
    }


    public static void appendExceptionString(StringBuffer sb, Throwable ex) {
        appendExceptionString(null, sb, ex);
    }

    public static void appendExceptionString(String Message, StringBuffer sb, Throwable ex) {
        StringBuffer messages = new StringBuffer();
        Throwable cause = buildMessages(messages, ex);
        if (Message != null)
            sb.append("Message: " + Message + "\n" + messages.toString());
        else
            sb.append("Message: " + messages.toString());
        sb.append("StackTrace:\n");
        sb.append(StackTrace.getStackTrace(cause));
        sb.append("\n");

    }

    public static Throwable buildMessages(StringBuffer sb, Throwable ex) {
        Throwable ThisCause = ex;
        Throwable LastCause = getCause(ex);
        String Message = ex.getMessage();
        if (!Util.isEmptyString(Message)) {
            sb.append(Message);
            sb.append("\n");
        }
        if (ThisCause != LastCause) {
            ThisCause = LastCause;
            LastCause = buildMessages(sb, LastCause);
        }
        return (LastCause);
    }


    public static void appendArguments(StringBuffer sb, Map values) {
        if (values.size() == 0)
            return;
        appendMap(sb, "Arguments", values, 1);
    }


    protected static void appendMap(StringBuffer sb, String Name, Map values, int indent) {
        sb.append(Util.indentString(indent));
        sb.append(Name + "\n");
        SortedSet keys = new TreeSet(values.keySet());
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            String keyStr = key.toString();
            Object value = values.get(key);
            appendObject(sb, keyStr, value, indent + 1);
        }
    }

    protected static void appendObject(StringBuffer sb, String keyStr, Object value, int indent) {
        if (value instanceof Map) {
            appendMap(sb, keyStr, (Map) value, indent + 1);
        } else {
            sb.append(Util.indentString(indent) + keyStr + " = " + value + "\n");
        }
    }

    public static String showCallingLocation() {
        String[] items = StackTrace.getStackTraceItems();
        return (items[5]);
    }

    /**
     * Return the cause Exception
     * Possibly the original exception
     */
    public static Throwable[] getCauses(Throwable ex) {
        List holder = new ArrayList();
        holder.add(ex);
        accumulateCauses(holder, ex);
        Throwable[] ret = new Throwable[holder.size()];
        holder.toArray(ret);
        return (ret);
    }

    /**
     * gather a list of causes for ex
     */
    protected static void accumulateCauses(List holder, Throwable ex) {
        Throwable cause = getCause(ex);
        if (cause == null || cause == ex)
            return; // no cause
        holder.add(cause); // keep accumulating
        accumulateCauses(holder, cause);
    }

    /**
     * Return the cause Exception
     * Possibly the original exception
     */
    public static Throwable getCause(Throwable ex) {
        Throwable cause = ex;
        if (ex instanceof InvocationTargetException) {
            Throwable possibleCause = ((InvocationTargetException) ex).getTargetException();
            if (possibleCause != null && !(possibleCause == ex)) {
                cause = possibleCause;
            }
        }
        if (ex instanceof RemoteException) {
            Throwable possibleCause = ((RemoteException) ex).detail;
            if (possibleCause != null && !(possibleCause == ex)) {
                cause = possibleCause;
            }
        }
        if (ex instanceof IWrappedException) {
            Throwable possibleCause = ((IWrappedException) ex).getCause();
            if (possibleCause != null) {
                cause = possibleCause;
            }
        }
        return (cause);
    }

    /**
     * Return the cause Exception keep unwrapping
     * Possibly the original exception
     */
    public static Throwable getOriginalCause(Throwable ex) {
        Throwable[] causes = getCauses(ex);
        return (causes[causes.length - 1]);
    }
}
