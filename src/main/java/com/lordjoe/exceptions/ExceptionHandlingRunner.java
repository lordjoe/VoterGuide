package com.lordjoe.exceptions;

/**
 * com.lordjoe.exceptions.ExceptionHandlingRunner
 * Objects implementing this interface will handle any exceptions
 * thrown in thr run method.
 * @author Steve Lewis
 * @date Feb 13, 2006
 * @see ExceptionHandlingManager
 */
public interface ExceptionHandlingRunner extends Runnable
{
    public static final ExceptionHandlingRunner[] EMPTY_ARRAY = {};
}
