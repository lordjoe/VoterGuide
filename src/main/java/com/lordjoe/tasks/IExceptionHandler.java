package com.lordjoe.tasks;

/**
 * com.lordjoe.tasks.IExceptionHandler
 * this handled an exception - frequently in a task
 * @author Steve Lewis
 * @date Aug 19, 2005
 */
public interface IExceptionHandler
{
    /**
     * do something when an exception occurs
     * logging is th eusual action
     * @param ex  non-null exception
     */
    public void onException(Exception ex);
}
