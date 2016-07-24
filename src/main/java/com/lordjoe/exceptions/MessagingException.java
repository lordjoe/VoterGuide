package com.lordjoe.exceptions;

/**
 * com.cbmx.exceptions.MessagingException
 *  an exception as not
 * an error but a control parameter This will not be logged or handled by
 * the ExceptionHandlingManager
 * @author Steve Lewis
 * @date Feb 15, 2006
 */
public class MessagingException extends RuntimeException implements ErrorForMessaging
{
    /**
     * Constructs a new runtime exception with <code>null</code> as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public MessagingException()
    {
        super();

    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later element by the {@link #getMessage()} method.
     */
    public MessagingException(String message)
    {
        super(message);

    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later element by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public MessagingException(Throwable cause)
    {
        super(cause);

    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later element
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later element by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public MessagingException(String message, Throwable cause)
    {
        super(message, cause);

    }
}
