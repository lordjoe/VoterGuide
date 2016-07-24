package com.lordjoe.tasks;

import java.util.concurrent.*;

/**
 * com.lordjoe.tasks.ITask
 *
 * @author Steve Lewis
 * @date Sep 18, 2006
 */
public interface ITask<T> extends Callable<Task>
{
    void addExceptionHandler(IExceptionHandler ex);

    void removeExceptionHandler(IExceptionHandler ex);

    void addTaskCompletionListener(TaskCompletionListener added);

    void removeTaskCompletionListener(TaskCompletionListener added);

    /**
     * actual code to perform the action of the task
     * this returns the object
     * @return the task
     */
    Task<T> execute();

    /**
     * wait for a running task to finish
     * @throws com.lordjoe.tasks.MarkerException  on any problem
     */
    void waitForCompletion() throws MarkerException;

    /**
     * if the task is started wait until it has completed
     * @param timeout millisec to wait
     */
    void waitForCompletion(long timeout);

    boolean isStarted();

    boolean isFinished();

    boolean isRunning();

    Future<Task> getFuture();

    Task call();

    Thread getCalledFrom();

    RuntimeException getException();

    T getReturn();
}
