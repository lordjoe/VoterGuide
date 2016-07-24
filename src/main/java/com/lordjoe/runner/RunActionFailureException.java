package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunActionFailureException
 *
 * @author Steve Lewis
 * @date Mar 6, 2007
 */
public class RunActionFailureException extends RuntimeException
{
    public static RunActionFailureException[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionFailureException.class;


    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RunActionFailureException(String message)
    {
        super(message);
    }
}
