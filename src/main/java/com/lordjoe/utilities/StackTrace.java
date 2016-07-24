package com.lordjoe.utilities;

import java.util.*;
import java.io.*;
import java.rmi.*;


/**
 * represents one item on the stack
 */
public class StackTrace {
    private Class m_Exception;
    private StackElement[] m_Elements;

    public StackTrace() {
        this(getStackTrace());
    }

    public StackTrace(Throwable ex) {
        this(getStackTrace(ex));
        m_Exception = ex.getClass();
    }

    public StackTrace(String[] items) {
        List holder = new ArrayList();
        int i = 0;
        if (items.length > 0 && items[i].indexOf("(") == -1)
            m_Exception = buildException(items[i++]);

        for (; i < items.length; i++) {
            holder.add(new StackElement(items[i]));
        }
        m_Elements = new StackElement[holder.size()];
        holder.toArray(m_Elements);

    }

    protected StackTrace(Class TheClass, StackElement[] items) {
        m_Exception = TheClass;
        m_Elements = items;

    }

    public StackTrace merge(StackTrace source) {
        List Holder = new ArrayList();
        StackElement[] SourceItems = source.m_Elements;
        int index = 0;
        for (int i = 0; i < SourceItems.length; i++)
            Holder.add(SourceItems[i]);

        StackElement[] MergedItems = new StackElement[Holder.size()];
        Holder.toArray(MergedItems);
        return (new StackTrace(m_Exception, MergedItems));
    }

    /**
     * custom string
     * @return non-null string similar to stack dump
     */
    public String toString() {
        StringBuffer st = new StringBuffer();
        if (m_Exception != null) {
            st.append(m_Exception.getName());
            st.append("\n");
        }
        for (int i = 0; i < m_Elements.length; i++) {
            st.append("at ");
            st.append(m_Elements[i].toString());
            st.append("\n");
        }
        return (st.toString());

    }

    public StackTrace(String TheStack) {
        this(TheStack != null ? Util.parseString(TheStack, "\n"): new String[0]);
    }

    /**
     * return the current
     trace
     * @return exception - will be null if we generated the trace
     */
    public static Class buildException(String s) {
        try {
            Class ret = Class.forName(s);
            if (ret == TraceException.class)
                return (null); // ignore artificial exceptions
            return (ret);
        } catch (ClassNotFoundException ex) {
            return (null);
        }
    }

    /**
     * return the current
     trace
     * @return non-null string representing the stack trace
     */
    public static String getStackTrace() {
        return (getStackTrace(new TraceException()));
    }

    public static String mergeStackTrace(String Enclosing, String source) {
        StackTrace EnclosingTrace = new StackTrace(Enclosing);
        StackTrace SourceTrace = new StackTrace(source);
        StackTrace merge = EnclosingTrace.merge(SourceTrace);
        return (merge.toString());
    }


    public static String buildStackTrace(Throwable in) {
        Throwable[] exceptions = ExceptionReporter.getCauses(in);
        String[] trace = getStackTraceItems(exceptions);
        return (Util.buildListString(trace, 0, 1));
    }


    /**
     * special class so we cal read a stack trace
     */
    private static class TraceException extends Throwable {
    }

    /**
     * return the stack trace in the throwable
     * @param non-null Throwable holding the stack
     * @return non-null string representing the stack trace
     */
    public static String getStackTrace(Throwable ex) {
        if (ex instanceof IStackTraceable) {

            String tracestr = (((IStackTraceable) ex).getStackTraceString());
            if (tracestr != null) {  // might be null -- MWR
                return tracestr;
            }
        }
        return (getExceptionTrace(ex));
    }

    public static String getOriginalMessage(Throwable ex) {
        Throwable[] causes = ExceptionReporter.getCauses(ex);
        StringBuffer sb = new StringBuffer();
        for (int i = causes.length - 1; i >= 0; i--) {
            String Message = causes[i].getMessage();
            if (!Util.isEmptyString(Message)) {
                return (Message);
            }
        }
        return ("");
    }

    public static Throwable getOriginalException(Throwable ex) {
        Throwable[] causes = ExceptionReporter.getCauses(ex);
        return (causes[causes.length - 1]);
    }

    public static String getCummulativeMessages(Throwable ex) {
        Throwable[] causes = ExceptionReporter.getCauses(ex);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < causes.length; i++) {
            /*  if(causes[i] instanceof IStackTraceable) {
                  String Messages = (((IStackTraceable)causes[i]).getCummulativeMessages());
                  if(sb.length() > 0)
                      sb.append("Caused by " + causes[i].getClass().getUrl() + "\n");
                  sb.append(Messages);
                  return(sb.toString());
              }
              else {
              */
            appendMessage(sb, causes[i]);
            /* } */
        }
        return (sb.toString());
    }

    public static void appendMessage(StringBuffer sb, Throwable ex) {
        sb.append("Caused by " + ex.getClass().getName() + "\n");
        String Message = ex.getMessage();
        if (!Util.isEmptyString(Message)) {
            sb.append("Message: ");
            sb.append(Message);
            sb.append("\n");
        }
    }

    /**
     * return the stack trace in the throwable
     * @param non-null Throwable holding the stack
     * @return non-null string representing the stack trace
     */
    public static String getExceptionTrace(Throwable ex) {
        String[] items = getStackTraceItems(ex);
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < items.length - 1; i++) {
            out.append(items[i]);
            if (i < items.length - 1)
                out.append("\n");
        }
        return (out.toString());
    }

    /**
     * return the current stack trace
     * @retur non-null string representing the stack trace
     */
    public static String[] getStackTraceItems() {
        return (getStackTraceItems(new TraceException()));
    }

    /**
     * drop at at the beginning and any nonprinting chars at the end
     * @param in non-null string input
     * @return non-null output
     */
    protected static String filterStackTrace(String in) {
        StringBuffer t = new StringBuffer(in);
        for (int i = t.length() - 1; i >= 0; i--) {
            if (Character.isLetterOrDigit(t.charAt(i)))
                return (t.toString());
        }
        return (t.toString().trim());
    }

    /**
     * drop at at the beginning and any nonprinting chars at the end
     * @param in non-null string input
     * @return non-null output
     */
    protected static String filterExceptionClass(String in) {
        int ColonIndex = in.indexOf(":");
        if (ColonIndex == -1)
            return (in.trim());
        return (in.substring(0, ColonIndex).trim());
    }


    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] getStackTraceItems(Throwable[] items) {
        if (items.length == 0)
            return (Util.EMPTY_STRING_ARRAY);
        if (items.length == 1)
            return (getThrowableStackTraceItems(items[0]));
        //  String[] FirstTrace = getThrowableStackTraceItems(items[0]);
        return (getStackTraceItems(Util.EMPTY_STRING_ARRAY, items, 0));
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] getStackTraceItems(Throwable[] items, Throwable Start) {
        if (items.length == 0)
            return (Util.EMPTY_STRING_ARRAY);
        String[] FirstTrace = null;
        if (Start == items[0]) {
            FirstTrace = getThrowableStackTraceItems(items[0]);
            return (getStackTraceItems(FirstTrace, items, 1));
        } else {
            return (getStackTraceItems(Util.EMPTY_STRING_ARRAY, items, 0));
        }
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] getStackTraceItems(Throwable in) {
        return (getStackTraceItems(ExceptionReporter.getCauses(in)));
    }


    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] getStackTraceItems(String[] CurrentItems, Throwable[] items, int index) {
        if (index >= items.length)
            return (CurrentItems);
        Throwable ThisItem = items[index];
        // Wrapper exceptions already have entire trace
        if (ThisItem instanceof IStackTraceable) {
            String[] FullTrace = ((IStackTraceable) ThisItem).getStackTraceElements();
            return (mergeCausalTraces(CurrentItems, FullTrace));
        }
        String[] FirstTrace = getThrowableStackTraceItems(ThisItem);
        String[] ThisTrace = null;
        if (ThisItem instanceof ServerException) {
            ThisTrace = mergeThreadTraces(CurrentItems, FirstTrace, "Crossed to a new Process");
        } else {
            ThisTrace = mergeCausalTraces(CurrentItems, FirstTrace);
        }
        if (ThisItem instanceof ThreadCrossingException) {
            String[] OtherTrace = getStackTraceItems(Util.EMPTY_STRING_ARRAY, items, index + 1);
            return (mergeCrossingTraces(CurrentItems, OtherTrace));
        } else {
            return (getStackTraceItems(ThisTrace, items, index + 1));
        }
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    protected static String[] mergeCrossingTraces(String[] items, String[] added) {
        return (mergeThreadTraces(items, added, "Crossed to a new Thread"));
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    protected static String[] mergeThreadTraces(String[] items, String[] added, String Message) {
        List holder = new ArrayList();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                holder.add(items[i]);
            }
        }
        if (Message != null)
            holder.add(Message);
        if (added != null) {
            for (int k = 0; k < added.length; k++) {
                holder.add(added[k]);
            }
        }
        String[] ret = Util.collectionToStringArray(holder);
        return (ret);
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] mergeCausalTraces(String[] items, String[] added) {
        List holder = new ArrayList();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                holder.add(items[i]);
            }
        }
        if (added != null) {
            holder.add("Caused by");

            for (int k = 0; k < added.length; k++) {
                holder.add(added[k]);
            }
        }
        String[] ret = Util.collectionToStringArray(holder);
        return (ret);
    }

    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    protected static String[] mergeTraces(String[] items, String[] added) {
        List holder = new ArrayList();
        for (int i = 0; i < items.length; i++) {
            holder.add(items);
        }
        int startDups = -1;

        for (int i = Math.max(items.length - added.length, 0); i < items.length; i++) {
            if (items[i].equals(added[0])) {
                startDups = i;
                for (int j = i; j < items.length; j++) {
                    if (!items[j].equals(added[j - i])) {
                        startDups = -1;
                        break;
                    }
                }
                if (startDups == i)
                    break;
            }
        }
        if (startDups == -1) { // nooverlap
            for (int k = 0; k < added.length; k++) {
                holder.add(added[k]);
            }
        } else {
            for (int k = items.length - startDups; k < added.length; k++) {
                holder.add(added[k]);
            }
        }
        String[] ret = Util.collectionToStringArray(holder);
        return (ret);
    }


    /**
     * return the  stack trace of the throwable
     * @return non-null string representing the stack trace
     */
    public static String[] getThrowableStackTraceItems(Throwable ex) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream(1000);
        PrintStream p = new PrintStream(bo);
        ex.printStackTrace(p);
        String s = bo.toString();
        String[] items = Util.parseString(s, "\n");
        List holder = new ArrayList(items.length);
        int i = 0;
        holder.add(filterExceptionClass(items[i++]));
        int start = items.length - 1;
        // find stack start
        for (; start >= 0; start--) {
            if (!StackElement.isStackElement(items[start])) {
                start++;
                break;
            }
        }
        // ignore the exceptions we generated
        if (ex instanceof TraceException)
            start++;

        for (i = start; i < items.length; i++) {
            if (StackElement.isStackElement(items[i]))
                holder.add(filterStackTrace(items[i].substring(3)));
        }

        String[] ret = Util.collectionToStringArray(holder);
        return (ret);
    }

    /**
     * code to get parameter Exception
     * @return <Add Comment Here>
     * @see getException
     */
    public Class getException() {
        return (m_Exception);
    }

    /**
     * code to set parameter Exception
     * @param in <Add Comment Here>
     * @see getException
     */
    public void setException(Class in) {
        m_Exception = in;
    }

    /**
     * code to get parameter Elements
     * @return <Add Comment Here>
     * @see getElements
     */
    public StackElement[] getElements() {
        return (m_Elements);
    }

    /**
     * code to set parameter Elements
     * @param in <Add Comment Here>
     * @see getElements
     */
    public void setElements(StackElement[] in) {
        m_Elements = in;
    }


    public static String buildExceptionReport(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("Exception Report\n");
        sb.append("Base Cause: ");
        sb.append(StackTrace.getOriginalException(ex).getClass().getName());
        sb.append("\nBase Message: ");
        sb.append(StackTrace.getOriginalMessage(ex));

        sb.append("\nFull Messages\n");
        sb.append(StackTrace.getCummulativeMessages(ex));
        sb.append("\n");

        sb.append("\nFull Stack Trace\n");
        String trace = StackTrace.getStackTrace(ex);
        if (Util.isEmptyString(trace))
            trace = "<<No Stack Trace Available>>";
        sb.append(trace);
        sb.append("\n");
        return (sb.toString());
    }

    public static void printExceptionReport(Throwable ex) {
        System.out.println(buildExceptionReport(ex));
    }
}
