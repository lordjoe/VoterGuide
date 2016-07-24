package com.lordjoe.tasks;

/**
 * com.lordjoe.tasks.AbstractRunnableTask
 * task itsels is overridden to implement Runnable
 * @author Steve Lewis
 * @date AbstractRunnableTask
 */
public abstract class AbstractRunnableTask extends RunTask implements Runnable
{
    /**
     * here the object IS a runnable
     * @return
     */
   // @Override
    protected Runnable getRunnable()
    {
        return this;
    }

    
}
