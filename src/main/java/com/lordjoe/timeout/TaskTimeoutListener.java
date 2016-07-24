package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.TaskTimeoutListener
 *   Object is interested in task timeouts
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public interface TaskTimeoutListener
{
    public static final TaskTimeoutListener[] EMPTY_ARRAY = {};

    /**
     * do something when a task times out
     * @return
     */
    public void onTaskTimeout(TaskTimer timer);
}
