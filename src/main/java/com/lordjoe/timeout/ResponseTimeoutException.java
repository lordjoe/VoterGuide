package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.ResponseTimeoutException
 *
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public class ResponseTimeoutException extends RuntimeException
{
    public final static ResponseTimeoutException[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = ResponseTimeoutException.class;

    private final long m_Timeout;
    public ResponseTimeoutException(String message,long timeout)
    {
        super(message);
        m_Timeout = timeout;
    }

    public long getTimeout()
    {
        return m_Timeout;
    }
}
