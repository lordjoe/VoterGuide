package com.lordjoe.utilities;

import java.io.PrintStream;

public class StreamLogger implements ILogger {
    private static final ILogger gInstance = new StreamLogger();

    public static ILogger getConsole() {
        return (gInstance);
    }

    private PrintStream m_Out = System.out;
    private boolean m_debug = false;

    private StreamLogger() {
    }

    public StreamLogger(PrintStream out) {
        m_Out = out;
    }


    public StreamLogger(PrintStream out, boolean debug) {
        this(out);
        setDebug(debug);
    }

    public void setDebug(boolean bool) {
        m_debug = bool;
    }


    public void error(Object message) {
        showMessage("ERROR", message);
    }

    public void error(Object message, Throwable t) {
        showMessage("ERROR", message, t);
    }

    public void showMessage(String Level, Object message) {
        m_Out.println(Level + ": " + message);
    }

    public void showMessage(String Level, Object message, Throwable t) {
        showMessage(Level, message);
        t.printStackTrace(m_Out);
    }

    public boolean isDebugEnabled() {
        return m_debug;
    }

    public void debug(Object message) {
        showMessage("DEBUG", message);
    }

    public void debug(Object message, Throwable t) {
        showMessage("DEBUG", message, t);
    }

    public void warn(Object message) {
        showMessage("WARN", message);
    }

    public void warn(Object message, Throwable t) {
        showMessage("WARN", message, t);
    }

    public void info(Object message) {
        showMessage("INFO", message);
    }

    public void info(Object message, Throwable t) {
        showMessage("INFO", message, t);
    }

}
