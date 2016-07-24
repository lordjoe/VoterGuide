package com.lordjoe.tasks;

import javax.swing.*;
import java.util.concurrent.*;
import java.util.*;

/**
 * com.lordjoe.tasks.Task
 * this encapsulates a task -  a callable which can be
 * called in a thread and holds launch information and
 * location information
 * Use RunTask to do the same with a Runnable
 *  Uses either Subclass or create a Task with a Callable<T>
 *  Task<MyType>  tsk = new Task(new MyCallable());
 *  tsk.execute();
 *  tsk.waitForCompletion();
 *  if(tsk.getException() != null)
 *      throw tsk.getException();
 *  MyType ret = tsk.getReturn();
 * @see com.lordjoe.tasks.RunTask
 * @author Steve Lewis
 * @date Task
 */
public class  Task<T> implements Callable<Task> //ITask
{
    private static final Set<IExceptionHandler> gExceptionListeners =
           new CopyOnWriteArraySet<IExceptionHandler>();

    public static void addGlobalExceptionHandler(IExceptionHandler ex)
    {
       gExceptionListeners.add(ex);
    }
    public static void removeGlobalExceptionHandler(IExceptionHandler ex)
    {
       gExceptionListeners.remove(ex);
    }
    public static void notifyExceptionHandlers(Exception ex)
    {
       synchronized(gExceptionListeners) {
            if(gExceptionListeners.isEmpty())
                return;
           for(IExceptionHandler handler : gExceptionListeners)
              handler.onException(ex);
       }
    }

    private MarkerException m_LaunchStack;
    private Exception m_Exception;
    private T m_Return;
    private Future<Task> m_Future;
    private Callable<T> m_Callable;
    private Thread m_CalledFrom;
    private List<TaskCompletionListener> m_Listeners;
    private Set<IExceptionHandler> m_ExceptionHandlers;

    /**
     * constructor used by subclasses which mat supply a
     * a callable or runnable in a different way
     */
    protected Task()
    {
        m_Callable = null;
        m_ExceptionHandlers = new CopyOnWriteArraySet<IExceptionHandler>();
       }

    public Task(Callable<T> action)
     {
        m_Callable = action;
         m_ExceptionHandlers = new CopyOnWriteArraySet<IExceptionHandler>();
        if(action == null)
              throw new TaskException("action cannot be null");
      }


    public void addExceptionHandler(IExceptionHandler ex)
     {
        m_ExceptionHandlers.add(ex);
     }

     public void removeExceptionHandler(IExceptionHandler ex)
     {
        m_ExceptionHandlers.remove(ex);
     }


    protected void setCallable(Callable<T> pCallable)
    {
        if(m_Callable != null)
            throw new TaskException("Callable cab be set only once");
        m_Callable = pCallable;
    }

    public void addTaskCompletionListener(TaskCompletionListener added)
    {
        synchronized(m_Listeners) {
        if(m_Listeners == null)  {
           m_Listeners = new ArrayList<TaskCompletionListener>();
        }
        m_Listeners.add(added);
        }
    }

    public synchronized void removeTaskCompletionListener(TaskCompletionListener added)
    {
        synchronized(m_Listeners) {
        if(m_Listeners == null)  {
           return;
        }
        m_Listeners.remove(added);
        }
    }

    /**
     * actual code to perform the action of the task
     * this returns the object
     * @return the task
     */
    public Task<T> execute()
     {
         try {
             TaskManager exe = TaskManager.getInstance();
             setLaunchStack(new MarkerException(null));
             Future<Task> future = exe.doExecute(this);
             setFuture(future);
         }
         catch(RuntimeException ex) {
             ex.printStackTrace();
             setException(ex);
         }
         return this;
     }

    /**
     * run the task in the swing thread
     */
    public void runInSwingThread()
     {
         if(SwingUtilities.isEventDispatchThread())
            execute();
         else
            SwingUtilities.invokeLater(new SwingRunner());
     }

    /**
     * special runner for swing
     */
    protected class SwingRunner implements Runnable
    {
       public void run()
       {
           execute();
       }
    }

    /**
     * wait for a running task to finish
     * @throws MarkerException  on any problem
     */
    public void waitForCompletion() throws MarkerException
    {
       Future<Task> future = getFuture();
        if(future == null)
             return; // not running
        if(future.isDone()) {
             RuntimeException ex = getException();
             if(ex != null)
                throw ex;
            return; // done
        }
        try {
            future.get();
        }
        catch (Exception e) {
            e.printStackTrace();
            setException(e);
            MarkerException ex2 = getLaunchStack();
            throw ex2;
        }
    }

    /**
     * if the task is started wait until it has completed
     * @param timeout millisec to wait
     */
    public synchronized void waitForCompletion(long timeout)
      {
          Future<Task> future = getFuture();
           if(future == null)
                return; // not running
           if(future.isDone())
                return; // done
           try {
               future.get(timeout, TimeUnit.MILLISECONDS);
           }
           catch (Exception e) {
                e.printStackTrace();
                setException(e);
                MarkerException ex2 = getLaunchStack();
                throw ex2;
            }
      }

    public boolean isStarted()
    {
        Future<Task> task = getFuture();
        if(task == null)
            return false; // not started
        return true;
    }

    public boolean isFinished()
    {
        if(!isStarted())
            return false; // not started
        Future<Task> future = getFuture();
        if(future.isCancelled())
            return true;
        return future.isDone();
    }

    public boolean isRunning()
    {
        if(!isStarted())
            return false; // not started
        return !isFinished();
    }

    public Future<Task> getFuture()
    {
        return m_Future;
    }

    public void setFuture(Future<Task> pFuture)
    {
        m_Future = pFuture;
    }



    public Task call()
    {
        try {
           setLaunchStack(new MarkerException(null));
           performAction();
        }
        catch(Exception ex) {
           setException(ex);
           notifyExceptionHandlers( ex);
        }
        finally {
            TaskManager.getInstance().onTaskCompletion(this); // cleanup
        }
        if(getException() != null)
            notifyListeners();
        return this;
    }

    protected synchronized void notifyListeners()
    {
        if(m_Listeners == null)
            return;
        synchronized(m_Listeners) {
             if(m_Listeners.isEmpty())
                 return;
            TaskManager.runNotification(new ListenerNotifier());
        }

    }

    /**
     * runnable to notify any listenera of completion
     */
    protected class ListenerNotifier implements Runnable
    {
        public void run() {
             synchronized(m_Listeners) {
                 for (Iterator<TaskCompletionListener> iterator = m_Listeners.iterator(); iterator.hasNext();) {
                     TaskCompletionListener taskCompletionListener =  iterator.next();
                     taskCompletionListener.onTaskCompletion(Task.this);
                 }
             }
        }
    }

    public Thread getCalledFrom()
    {
        return m_CalledFrom;
    }

    public void setCalledFrom(Thread pCalledFrom)
    {
        m_CalledFrom = pCalledFrom;
    }

    protected MarkerException getLaunchStack()
    {
        return m_LaunchStack;
    }

    public void setLaunchStack(MarkerException pLaunchStack)
    {
        m_LaunchStack = pLaunchStack;
    }

    public String getErrorString()
    {
        if(getException() == null)
            return null;
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }


    public RuntimeException getException()
    {
        if(m_Exception == null)
            return null;
        return getLaunchStack();
    }

    protected void setException(Exception pException)
    {
        if(m_Exception != null)
            throw new TaskException("Exception can only be set once");
        MarkerException launchStack = getLaunchStack();
         m_Exception = pException;
        launchStack.setCause(pException);
    }

    public T getReturn()
    {
        return m_Return;
    }

    public void setReturn(T pReturn)
    {
        m_Return = pReturn;
    }

    protected Callable<T> getCallable()
    {
        return m_Callable;
    }


    protected void performAction() throws Exception
    {
            T ret = getCallable().call();
            setReturn(ret);
            return;

    }


}
