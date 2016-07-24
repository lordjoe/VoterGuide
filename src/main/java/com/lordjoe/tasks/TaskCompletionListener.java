package com.lordjoe.tasks;

/**
 * com.lordjoe.tasks.TaskCompletionListener
 *
 * @author Steve Lewis
 * @date TaskCompletionListener
 */
public interface TaskCompletionListener
{
    /**
     * perform whatever the listener wants to do when the task is
     * finished
     * @param task
     */
    public void onTaskCompletion(Task<?> task);

    /**
     * do something when aTask completes
     * only call on successful completeion
     * @param tsk  non-null task
     * @param ex  non-null exception
     */
    public void onTaskFailure(Task<?> tsk,Exception ex);

}
