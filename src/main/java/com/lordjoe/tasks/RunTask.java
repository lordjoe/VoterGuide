package com.lordjoe.tasks;

import java.util.concurrent.*;

/**
 * com.lordjoe.tasks.Task
 * this encapsulates a task - runnable  which can be
 * be launched as a task
 *
 * @author Steve Lewis
 * @date Task
 */
public class RunTask extends Task<Void>
{
    private final Runnable m_Runnable;

    public RunTask(Runnable action)
    {

        m_Runnable = action;
        if (action == null)
            throw new TaskException("action cannot be null");
    }


    protected RunTask()
    {
        m_Runnable = null;
        if (!(this instanceof Runnable))
            throw new TaskException("Null constructor cab only be a called by Runnable");
    }


    protected Runnable getRunnable()
    {
        return m_Runnable;
    }


    protected void performAction() throws Exception
    {
        if (getRunnable() != null) {
            try {
                getRunnable().run();
                return;
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        throw new TaskException("Exactly one of Callable and Runnable must be set");

    }


}
