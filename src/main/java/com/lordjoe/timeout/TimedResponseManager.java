package com.lordjoe.timeout;

import com.lordjoe.utilities.*;

import java.util.*;
import java.lang.*;

/**
 * com.cbmx.timeout.TimedResponseManager
 *
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public class TimedResponseManager implements Runnable
{
    public final static TimedResponseManager[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = TimedResponseManager.class;

    private static final TimedResponseManager INSTANCE = new TimedResponseManager();

    /**
     * allow a different task runner - say a thread pool or an exector
     * @param runner
     */
     public static void setTaskRunner(TaskRunner runner)
     {
         INSTANCE.setTaskRunnerUsed(runner);
     }
    /**
      * a task running on this thread must
      *
      * @param time
      */
     public static void registerTimeout(long time)
     {
         Runnable r = new ThreadInterruptingTask();
         registerTimeout(time, r);
     }

    /**
     * a task running on this thread must
     *
     * @param time
     */
    public static void registerTimeout(long time, Runnable r)
    {
        INSTANCE.doRegisterTimeout(time, r);
    }

    public static void unRegisterTimeout()
    {
        INSTANCE.doUnRegisterTimeout();
    }

    private final Map m_Tasks;
    private TaskRunner m_TaskRunnerUsed;
    private TimedResponseManager()
    {
        m_TaskRunnerUsed = new ThreadCreatingTaskRunner();
        m_Tasks = Collections.synchronizedMap(new HashMap());
        Thread thr = new Thread(this, "TimedResponseManager Thread");
        thr.setDaemon(true);
        thr.start();
    }

    public TaskRunner getTaskRunnerUsed()
    {
        return m_TaskRunnerUsed;
    }

    public void setTaskRunnerUsed(TaskRunner pTaskRunnerUsed)
    {
        m_TaskRunnerUsed = pTaskRunnerUsed;
    }

    public void run()
    {
        TimeoutRegistration waitFor = null;
        while (true) {
            waitForTask();
            waitFor = getFirstTask();
            if(waitFor != null && taskIsValid(waitFor))
            {
                waitForTaskTimeout(waitFor);
            }

        }
    }

    private boolean taskIsValid(TimeoutRegistration pWaitFor)
    {
        return m_Tasks.get(pWaitFor.getThread()) == pWaitFor;
    }

    public void waitForTaskTimeout(TimeoutRegistration waitFor)
    {
        long waitTime = 0;
        synchronized (m_Tasks) {
            while(taskIsValid(waitFor)) {
                waitTime = waitFor.getTimeoutWait();
                if(waitTime < 0)  {
                    // in a different Thread handle timeout
                    getTaskRunnerUsed().runTask(waitFor.getOnTimeout());
                    return;
                }
                try {
                    m_Tasks.wait(waitTime);
                }
                catch (InterruptedException ex) {
                    throw new IllegalStateException("Do NOT INTERRUPT TimedResponseManager Thread");
                }
                if(!taskIsValid(waitFor))
                    return; // task complete
                if(getFirstTask() != waitFor)
                    return;
            }
        }

    }


    /**
     * a task running on this thread must
     *
     * @param time
     */
    private TimeoutRegistration getFirstTask()
    {
        TimeoutRegistration[] tasks = null;
        synchronized (m_Tasks) {
            tasks = new TimeoutRegistration[m_Tasks.size()];
            m_Tasks.values().toArray(tasks);
        }
        Arrays.sort(tasks);
        if(tasks.length > 0)
            return tasks[0];
        return null;
    }

    /**
     * a task running on this thread must
     *
     * @param time
     */
    private void waitForTask()
    {
        synchronized (m_Tasks) {
            while (m_Tasks.isEmpty())
                try {
                    m_Tasks.wait();
                }
                catch (InterruptedException ex) {
                    throw new IllegalStateException("Do NOT INTERRUPT TimedResponseManager Thread");
                }
        }
    }

    /**
     * a task running on this thread must
     *
     * @param time
     */
    private void doRegisterTimeout(long time, Runnable onTimeout)
    {
        synchronized (m_Tasks) {
            Thread thread = Thread.currentThread();
            m_Tasks.put(thread, new TimeoutRegistration(time, onTimeout));
            m_Tasks.notifyAll();
        }
    }

    private void doUnRegisterTimeout()
    {
        synchronized (m_Tasks) {
            m_Tasks.remove(Thread.currentThread());
            m_Tasks.notifyAll();
        }

    }

    /**
     * interrupts the thread - this is the default
     * task
     */
    private static class TimeoutRegistration implements java.lang.Comparable
    {
        private final Thread m_Thread;
        private final long m_Timeout;
        private final Runnable m_OnTimeout;

        public TimeoutRegistration(long time, Runnable onTimeout)
        {
            m_Thread = Thread.currentThread();
            m_OnTimeout = onTimeout;
            m_Timeout = time + System.currentTimeMillis();
        }

        public long getTimeoutWait()
        {
            return getTimeout() - System.currentTimeMillis();
        }

        public boolean isTimedOut()
        {
            return getTimeoutWait() < 0;
        }


        public Thread getThread()
        {
            return m_Thread;
        }

        public long getTimeout()
        {
            return m_Timeout;
        }

        public Runnable getOnTimeout()
        {
            return m_OnTimeout;
        }

        public int compareTo(Object o)
        {
            TimeoutRegistration realO = (TimeoutRegistration) o;
            if (getTimeout() < realO.getTimeout())
                return 1;
            if (getTimeout() > realO.getTimeout())
                return -1;
            boolean dif = getThread().hashCode() < realO.getThread().hashCode();
            if (dif)
                return 1;
            else
                return -1;
        }
    }

    /**
     * interrupts the thread - this is the default
     * task
     */
    private static class ThreadInterruptingTask implements Runnable
    {
        private final Thread m_Thread;

        public ThreadInterruptingTask()
        {
            m_Thread = Thread.currentThread();
        }

        public void run()
        {
            m_Thread.interrupt();
        }

    }


}
