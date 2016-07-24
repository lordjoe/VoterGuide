package com.lordjoe.general;

import com.lordjoe.exceptions.*;
import com.lordjoe.tasks.*;
import com.lordjoe.utilities.*;

import java.util.*;
import java.util.concurrent.*;


/**
 * com.lordjoe.general.FutureCollection
 * A collection of futures with methods to help
 * say when all tasks are complete
 *
 * @author slewis
 * @date Jun 13, 2005
 */
public class FutureCollection
{
    public static final Class THIS_CLASS = FutureCollection.class;
    public static final FutureCollection EMPTY_ARRAY[] = {};

    private final Set<Future<Task>> m_Futures;

    public FutureCollection(Future<Task>[] tasks)
    {
        m_Futures = new HashSet<Future<Task>>(Arrays.asList(tasks));
    }

    public boolean isDone()
    {
        for (Iterator<Future<Task>> iterator = m_Futures.iterator(); iterator.hasNext();)
        {
            Future<Task> future = iterator.next();
            if (!future.isDone())
                return false;
        }
        return true;
    }

    public void waitUntilDone()
    {
        waitUntilDone(Long.MAX_VALUE);
    }

    public void waitUntilDone(long timeout)
    {
        if(isDone())
            return;
        Future<Task>[] tasks = new Future[m_Futures.size()];
        m_Futures.toArray(tasks);
        for (int i = 0; i < tasks.length; i++)
        {
            try
            {
                Future<Task> task = tasks[i];
                Task tsk = task.get(timeout, TimeUnit.MILLISECONDS);
                Exception ex = tsk.getException();
                if (ex != null)
                    throw DeferredException.deferHandling(ex);
            }
            catch (TimeoutException ex)
               {
                   throw new TaskTimeoutException(timeout);
               }
            catch (ExecutionException ex)
               {
                   throw DeferredException.deferHandling(ex);
               }
                 catch (InterruptedException ex)
            {
                throw new ThreadInterruptedException();
            }
        }
    }

}
