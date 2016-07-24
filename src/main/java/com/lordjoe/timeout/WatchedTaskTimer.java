package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.WatchedTaskTimer
 *    Task timer associated with a watcher
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public interface WatchedTaskTimer extends TaskTimer
{
    public static final WatchedTaskTimer[] EMPTY_ARRAY = {};

    /**
     * return nun-null associated watcher
     * @return as above
     */
    public TaskWatcher getWatcher();

    /**
     * return possuby null data associates with the task
     * @return
     */
    public Object getData();
}
