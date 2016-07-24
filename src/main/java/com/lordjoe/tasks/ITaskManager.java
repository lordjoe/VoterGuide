package com.lordjoe.tasks;

/**
 * com.lordjoe.tasks.ITaskManager
 *
 * @author SLewis
 * @date Nov 28, 2005
 */
public interface ITaskManager extends TaskCompletionListener
{
    public static final Class THIS_CLASS = ITaskManager.class;
    public static final ITaskManager[] EMPTY_ARRAY = {};
}
