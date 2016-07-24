package com.lordjoe.lang;

/**
 * com.lordjoe.lang.TimedTask
 *
 * @author Steve Lewis
 * @date Feb 13, 2006
 */
public interface TimedTask extends Runnable
{
    public static final TimedTask[] EMPTY_ARRAY = {};

    public static final long DEFAULT_TASK_TIMEOUT = 1000; // 1 sec
    /**
     * when in system time did the taskstart
     * @return
     */
    public long getStartTime();

    /**
      * how long before task is timed out
      * @return
      */
    public long getTimeoutTime();

    /**
     * true if the task has timed out
     * @return
     */
    public boolean isTimedOut();

    /**
        * true if the taskis complete
        *
        * @return
        */
       public boolean isFinished();

}
