package com.lordjoe.exceptions;

/**
 * com.lordjoe.exceptions.UnexpectedFailureException
 *
 * @author Steve Lewis
 * @date Nov 21, 2007
 */
public class UnexpectedFailureException extends RuntimeException{
    public static UnexpectedFailureException[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UnexpectedFailureException.class;

    public UnexpectedFailureException() {
    }

    public UnexpectedFailureException(String message) {
        super(message);
    }

    public UnexpectedFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedFailureException(Throwable cause) {
        super(cause);
    }
}
