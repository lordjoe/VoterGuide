package com.lordjoe.utilities;

/**
 * Wraps another exception in a runtime exception
 * @author Steve Lewis
 */
public class WrapperException extends RuntimeException implements IWrappedException {

    private Throwable m_cause;
    private String[] m_StackTraceElements; // marks where the exception was thrown
    private boolean m_Initialized; // used to stop multiple init calls
    private String m_AppName; // used to stop multiple init calls
    private String m_AppStart; // used to stop multiple init calls

    public WrapperException() {
        super();
        init();
    }

    public WrapperException(Throwable ex) {
        super();
        init();
        setCause(ex);
    }

    public WrapperException(String s, Throwable ex) {
        super(s);
        init();
        setCause(ex);
    }

    public WrapperException(String s) {
        super(s);
        init();
    }

    /*
    * Something that every version should do
    */
    protected void init() {
        if (m_Initialized)
            throw new IllegalStateException("multiple init calls");
        m_Initialized = true;
        m_AppName = Util.getApplicationName();
        m_AppStart = Util.getStartTimeString();
    }

    public String getAppName() {
        return (m_AppName);
    }

    public String getAppStart() {
        return (m_AppStart);
    }

    public void setCause(Throwable ex) {
        if (ex == null)
            throw new IllegalArgumentException("WrapperException does not take null");
        if (ex.equals(this))
            throw new IllegalArgumentException("WrapperException does not take itself");

        if (m_cause != null)
            throw new IllegalStateException("WrapperException does allow resetting cause");
        m_cause = ex;
        buildStackTraceElements();
    }

    public String getStackTraceString() {
       String[] lines = getStackTraceElements();
        return (Util.concatLines(lines));
    }

    public String[] getStackTraceElements() {
        if (m_StackTraceElements == null)
            buildStackTraceElements();
        return (m_StackTraceElements);
    }

    /**
     * return first message representing teh original cause
     * @return non-null possibly empty string
     */
    public String getOriginalMessage() {
        Throwable[] causes = ExceptionReporter.getCauses(this);
        StringBuffer sb = new StringBuffer();
        for (int i = causes.length - 1; i >= 0; i--) {
            String Message = causes[i].getMessage();
            if (!Util.isEmptyString(Message)) {
                return (Message);
            }
        }
        return ("");
    }


    public String getCummulativeMessages() {
        Throwable[] causes = ExceptionReporter.getCauses(this);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < causes.length; i++) {
            StackTrace.appendMessage(sb, causes[i]);
        }
        return (sb.toString());
    }

    protected void buildStackTraceElements() {
        String[] myTrace = StackTrace.getThrowableStackTraceItems(this);
        Throwable[] causes = ExceptionReporter.getCauses(this);
        if (causes.length <= 1) {
            setStackTraceElements(myTrace);
            return;
        }

        Throwable[] causalExceptions = new Throwable[causes.length - 1];
        System.arraycopy(causes, 1, causalExceptions, 0, causes.length - 1);

        String[] causalTrace = StackTrace.getStackTraceItems(causalExceptions);
        String[] trace = StackTrace.mergeCausalTraces(myTrace, causalTrace);
        setStackTraceElements(trace);
    }

    public void setStackTraceElements(String[] in) {
        m_StackTraceElements = in;
    }

    /*       Throwable cause = getCause();
           if(cause != null && cause != this) {
               if(cause instanceof IWrappedException) {
                   return(((IWrappedException)cause).getStackTrace());
               }
               else {
                   return(StackTrace.getStackTrace(getCause()));
               }
           }
           else {
               // Note we need to call super.printStackTrace
               StringWriter strWrite = new StringWriter();
               PrintWriter prtWrite = new PrintWriter(strWrite);
               super.printStackTrace(prtWrite);
               StringBuffer temp = strWrite.getBuffer();
               String str = temp.toString();
               return str;
           }
       }
       */

    public Throwable getCause() {
        return (m_cause);
    }

    public Throwable getWrapped() {
        return (m_cause);
    }

    public Throwable getRealWrapped() {
        Throwable wrapped = getWrapped();
        while (wrapped instanceof java.lang.reflect.InvocationTargetException)
            wrapped = ((java.lang.reflect.InvocationTargetException) wrapped).getTargetException();
        while (wrapped instanceof org.xml.sax.SAXException)
            wrapped = ((org.xml.sax.SAXException) wrapped).getException();
        while (wrapped instanceof java.rmi.RemoteException) {
            Throwable detail = ((java.rmi.RemoteException) wrapped).detail;
            if (detail == null || detail == wrapped)
                break;
            wrapped = detail;
        }

        return (wrapped);
    }

    /*   public String getMessage() {
           String outerMessage = super.getMessage();
           String innerMsg = getRealWrapped().getMessage();
           if(Util.isEmptyString(outerMessage)) {
               return(innerMsg);
           }
           else {
               if(Util.isEmptyString(innerMsg)) {
                   return(outerMessage);
               }
               else {
                   return(outerMessage + "\n     Root cause: " + innerMsg);
               }
           }
       }
       */

    /*
    public void printStackTrace(PrintWriter s)
    {
        //s.println("Wrapped exception - " + getWrapped().getMessage()); - MWR
	    s.println(getMessage());
        s.println(getStackTrace());
    }

    public void printStackTrace(PrintStream s)
    {
        //s.println("Wrapped exception - " + getWrapped().getMessage()); - MWR
	    s.println(getMessage());
        s.println(getStackTrace());
    }

    public void printStackTrace()
    {
	    printStackTrace(System.out);
    }
    */
}
