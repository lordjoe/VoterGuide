package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.TaskRunner
 *  Abstracts the idea of a thread pool
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public interface TaskRunner
{
    public static final TaskRunner[] EMPTY_ARRAY = {};

    

    /**
     * run the runnable - usually in a different thread
     * @param r non-null Runnable
     */
    public void runTask(Runnable r);
}
