package com.lordjoe.utilities;

import java.io.*;

/**
 * Exception to throw when a task times out without ompletion
 * @author Steve Lewis
 */
public class TaskFailException extends RuntimeException {
    private Throwable m_Exception;

    public TaskFailException(Throwable ex) {
        m_Exception = ex;
    }

    public Throwable getCause() {
        return (m_Exception);
    }

    public String getLocalizedMessage() {
        return (getCause().getLocalizedMessage());
    }

    public String getMessage() {
        return (getCause().getMessage());
    }

    public void printStackTrace() {
        getCause().printStackTrace();
    }

    public void printStackTrace(PrintWriter s) {
        getCause().printStackTrace(s);
    }

    public void printStackTrace(PrintStream s) {
        getCause().printStackTrace(s);
    }

}
