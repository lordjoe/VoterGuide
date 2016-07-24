package com.lordjoe.utilities;


/**
 * interface implemented by a nested exception to return the original stack
 * trace
 */
public interface IWrappedException extends IStackTraceable {
    /**
     * return the cause
     */
    public Throwable getCause();
}
