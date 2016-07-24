package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.TimedResponder
 *  This is a class which is guaranteed to respond to a string command
 *  within a specified interval
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public interface TimedResponder
{
    public static final TimedResponder[] EMPTY_ARRAY = {};

    /**
     *
     * @param command non-null command
     * @param timeoutMillisec  if > 0 a timeout - <= 0 no timeout
     * @return  non-null response String
     * @throws com.cbmx.timeout.ResponseTimeoutException if no response recieved within the timeout interval
     */
    public String getResponseWithTimeout(String command,long timeoutMillisec) throws ResponseTimeoutException;
}
