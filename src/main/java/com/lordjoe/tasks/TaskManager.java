package com.lordjoe.tasks;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.tasks.TaskManager
 *
 * @author Steve Lewis
 * @date TaskManager
 */
public class TaskManager implements ITaskManager
{

    public static final int NUMBER_POOL_THREADS = 20;

    private static final TaskManager gInstance = new TaskManager();
    /**
      * create an exception to mark
      */
     public static TaskManager getInstance()
      {
          return gInstance;
      }

     /**
      * run a task synchronously but with a timeout
      * @param tsk non-null task
      * @param timeout  millisec
      * @return  possibly null return
      * @throws StackingException
      */
     public static <T> T callTask(Task<T> tsk,long timeout) throws StackingException
     {
          try {
               Future<Task> future = getInstance().doExecute(tsk);
              if(timeout == 0)
                     future.get();
              else
                   future.get(timeout,TimeUnit.MILLISECONDS);
               if(tsk.getException() != null) {
                   throw new StackingException(tsk.getException());
               }
               return tsk.getReturn();
          }
          catch(InterruptedException ex)  {
               throw new StackingException(ex);
            }
          catch(ExecutionException ex)  {
              throw new StackingException(ex);
            }
          catch(TimeoutException ex)  {
                throw new StackingException(ex);
            }
     }
    /**
     * run a task asynchronously
     * @param tsk non-null task
     */
    public static void launchTask(Task tsk)
    {
         getInstance().doExecute(tsk);
    }

      /**
      * run a task asynchronously
      * @param tsk non-null task
      * @param listeners  anny completion listeners already in the task
      */
     public static void launchTask(Task tsk,TaskCompletionListener... listeners)
     {
          for (int i = 0; i < listeners.length; i++) {
              TaskCompletionListener listener = listeners[i];
              tsk.addTaskCompletionListener(listener);
          }
          launchTask(tsk);
     }

    /**
     * run a task asynchronously
     * @param tsk non-null task
     * @param listeners  anny completion listeners already in the task
     */
    public static void launchTask(Task tsk,IExceptionHandler... listeners)
    {
         for (int i = 0; i < listeners.length; i++) {
             IExceptionHandler listener = listeners[i];
             tsk.addExceptionHandler(listener);
         }
         launchTask(tsk);
    }


    /**
      * special method  to run a runable - usually a listener
      * notififction task
      * @param runner non-null runnable
      */
      public static void runNotification(Runnable runner)
      {
          getInstance().doRunNotification(runner);
      }

    /**
     * create an exception to mark
     */
    public static MarkerException markLocation()
     {
         return getInstance().doMarkLocation();
     }

    public static boolean isRootThread()
       {
           // swing dispatch is always roog
           if(SwingUtilities.isEventDispatchThread())
                return true;
          return isRootThread(Thread.currentThread());
       }

    public static boolean isRootThread(Thread thr)
       {

          return getInstance().doIsRootThread(thr);
       }


    public static MarkerException getLastMark()
      {
         return getLastMark(Thread.currentThread());
      }


    public static MarkerException getLastMark(Thread thr)
     {
        return getInstance().doGetLastMark(thr);
     }


    /**
     * root threads are not searched for added causes
     * @param thr
     */
     public static void registerRootThread(Thread thr)
      {
         gInstance.doRegisterRootThread(thr);
      }


    private final Set<Thread> m_RootThreads;
    private final Map<Thread,MarkerException> m_Markers;
    private final ExecutorService m_Executor;
    private final List<Future<Task>> m_RunningTasks;

    private TaskManager()
    {
        this(NUMBER_POOL_THREADS);
    }

    private TaskManager(int numberThreads)
    {
        m_Executor = Executors.newFixedThreadPool(numberThreads);
        m_RunningTasks = Collections.synchronizedList(new ArrayList<Future<Task>>());
        m_Markers =  Collections.synchronizedMap(new HashMap<Thread,MarkerException>());
        m_RootThreads =  new CopyOnWriteArraySet<Thread>();
     }

    public Future<Task> doExecute(Task tsk)
     {
         Future<Task> future = m_Executor.submit(tsk);
         m_RunningTasks.add(future);
         return future;
     }


    /**
     * perform whatever the listener wants to do when the task is
     * finished
     *
     * @param task
     */
    public void onTaskCompletion(Task<?> task)
    {
        m_RunningTasks.remove(task);
    }

    /**
     * do something when aTask completes
     * only call on successful completeion
     *
     * @param tsk non-null task
     * @param ex  non-null exception
     */
    public void onTaskFailure(Task<?> tsk, Exception ex)
    {
        m_RunningTasks.remove(tsk);
    }


    /**
     * special method  to run a runable - usually a listener
     * notififction task
     * @param runner non-null runnable
     */
       public void doRunNotification(Runnable runner)
        {
            m_Executor.submit(runner);
        }


    private boolean doIsRootThread(Thread thr)
        {

           return m_RootThreads.contains(thr);
        }


    private void doRegisterRootThread(Thread thr)
     {
        m_RootThreads.add(thr);
     }

    private MarkerException doMarkLocation()
    {
        MarkerException value = new MarkerException(null);
        m_Markers.put(Thread.currentThread(),value);
        return value;
    }

    private MarkerException doGetLastMark(Thread thr)
    {
       return m_Markers.get(thr);
    }
    /**
     * return true if there are tasks left to complete
     *
     * @return
     */
    public boolean isTaskRunning()
    {
        if (m_RunningTasks.size() == 0)
            return false;
        List<Future<Task>> completed = new ArrayList<Future<Task>>();
        boolean stillRunning = false;
        for (Future<Task> task : m_RunningTasks)
        {
            if (!task.isDone())
            {
                stillRunning = true;
            }
            else
            {
                completed.add(task); // done
            }
        }
        for (Future<Task> task : completed)
        {
            handleCompletedTask(task);
        }
        return stillRunning;
    }

    /**
     * drop any completed task from the list of running tasks
     *
     * @param task
     */
    protected void handleCompletedTask(Future<Task> task)
    {
        m_RunningTasks.remove(task);

    }
}
