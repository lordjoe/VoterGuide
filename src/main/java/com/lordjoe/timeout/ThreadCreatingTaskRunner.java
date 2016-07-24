package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.ThreadCreatingTaskRunner
 *
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public class ThreadCreatingTaskRunner implements TaskRunner
{
    public final static ThreadCreatingTaskRunner[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = ThreadCreatingTaskRunner.class;

    /**
     * run the runnable - usually in a different thread
     *
     * @param r non-null Runnable
     */
    public void runTask(Runnable r)
    {
        new Thread(r).start();

    }
}
