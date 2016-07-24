package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.TaskTimer
 *   Object used to set a timeout for a task
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public interface TaskTimer
{
    public static final TaskTimer[] EMPTY_ARRAY = {};

    /**
     * Call to say the task is started
     * @param timeoutMillisec  timeout
     */
    public void taskStarted(long timeoutMillisec);

    /**
     * call to say the task is ended
     */
    public void taskEnded();

    /**
     * true if the task timed out before taskEnded called
     * @return
     */
    public boolean isTimedOut();


    /**
     * true if the task ended or timed out
     * @return
     */
    public boolean isEnded();


    /**
     * millisec before timeout - if ended this returns -1
     * @return  as above
     */
    public long timeRemaining();
}
