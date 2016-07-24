package com.lordjoe.timeout;

/**
 * com.cbmx.timeout.TaskWatcher
 *   Object used to watch a set of tasks
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public interface TaskWatcher
{
    public static final TaskWatcher[] EMPTY_ARRAY = {};

    /**
      * return a task timer associated with this Watcher
      * @return
      */
     public WatchedTaskTimer getTimer();
    /**
      * return a task timer associated with this Watcher
      * @return
      */
     public WatchedTaskTimer getTimer(Object data);

    /**
     * return a possibly null piece of data associated with the task
     * @return
     */
     public Object getData();

    /**
     * add a listener to any associated timeout
     * @param listener
     */
    public void addTaskTimeoutListener(TaskTimeoutListener listener);

    /**
     * remove a listener to any associated timeout
     * @param listener
     */
    public void removeTaskTimeoutListener(TaskTimeoutListener listener);

    /**
     * notify any listenera - note this is done is a separate thread
     * @param task
     */
    public void notifyListeners(TaskTimer task);

}


