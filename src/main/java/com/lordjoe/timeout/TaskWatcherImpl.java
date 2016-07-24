package com.lordjoe.timeout;

import com.lordjoe.utilities.*;

import java.util.*;
import java.lang.*;

/**
 * com.cbmx.timeout.TaskWatcherImpl
 *
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public class TaskWatcherImpl implements TaskWatcher
{
    public final static TaskWatcherImpl[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = TaskWatcherImpl.class;


    private final Set m_Listeners;
    private final TaskManager m_Manager;
    private final Object m_Data;

    public TaskWatcherImpl(TaskManager mgr, Object data)
    {
        m_Listeners = Collections.synchronizedSet(new HashSet());
        m_Manager = mgr;
        m_Data = data;
    }

    public TaskWatcherImpl(TaskManager mgr)
    {
        this(mgr, null);
    }

    public Object getData()
    {
        return m_Data;
    }

    public TaskManager getManager()
    {
        return m_Manager;
    }

    /**
     * return a task timer associated with this Watcher
     *
     * @return
     */
    public WatchedTaskTimer getTimer()
    {
        return new TaskTimerImpl();
    }

    /**
     * return a task timer associated with this Watcher
     *
     * @return
     */
    public WatchedTaskTimer getTimer(Object data)
    {
        return new TaskTimerImpl(data);
    }

    /**
     * add a listener to any associated timeout
     *
     * @param listener
     */
    public void addTaskTimeoutListener(TaskTimeoutListener listener)
    {
        m_Listeners.add(listener);
    }

    /**
     * remove a listener to any associated timeout
     *
     * @param listener
     */
    public void removeTaskTimeoutListener(TaskTimeoutListener listener)
    {
        m_Listeners.remove(listener);
    }

    /**
     * noitfy any listenera - note this is done is a separate thread
     *
     * @param task
     */
    public void notifyListeners(TaskTimer task)
    {
        TaskTimeoutListener[] listeners = null;
        synchronized (m_Listeners) {
            if (m_Listeners.isEmpty())
                return;
            listeners = new TaskTimeoutListener[m_Listeners.size()];
            m_Listeners.toArray(listeners);
            Runnable r = new ListenerNotifier(task, listeners);
            ThreadPoolTaskRunner.INSTANCE.runTask(r);
        }
    }

    protected class ListenerNotifier implements Runnable
    {
        private final TaskTimer m_Task;
        private final TaskTimeoutListener[] m_Listeners;

        public ListenerNotifier(TaskTimer task, TaskTimeoutListener[] listeners)
        {
            m_Listeners = listeners;
            m_Task = task;
        }

        public void run()
        {
            for (int i = 0; i < m_Listeners.length; i++) {
                TaskTimeoutListener listener = m_Listeners[i];
                listener.onTaskTimeout(m_Task);
            }
        }
    }


    public class TaskTimerImpl implements WatchedTaskTimer, java.lang.Comparable
    {

        private long m_StartTime;
        private long m_TimeoutTime;
        private long m_EndTime;
        private boolean m_Ended;
        private boolean m_TimedOut;
        private final Object m_Data;

        public TaskTimerImpl()
        {
            this(null);
        }

        public TaskTimerImpl(Object data)
        {
            m_Data = data;
        }


        /**
         * return possuby null data associates with the task
         *
         * @return
         */
        public Object getData()
        {
            return m_Data;
        }

        public TaskWatcher getWatcher()
        {
            return TaskWatcherImpl.this;
        }


        /**
         * Call to say the task is started
         *
         * @param timeoutMillisec timeout
         */
        public void taskStarted(long timeoutMillisec)
        {
            m_TimeoutTime = timeoutMillisec;
            m_StartTime = System.currentTimeMillis();
            m_EndTime = m_StartTime + timeoutMillisec;
            TaskWatcherImpl.this.getManager().startTaskWatch(this);
        }

        public String toString()
        {
            String name = "";
            if (m_Data != null)
                name = " " + m_Data.toString();
            return "Task timeout-" + m_TimeoutTime + " remaining-" + timeRemaining() + name;
        }

        /**
         * call to say the task is ended
         */
        public void taskEnded()
        {
            m_Ended = true;
            TaskWatcherImpl.this.getManager().endTaskWatch(this);
        }

        /**
         * true if the task timed out before taskEnded called
         *
         * @return
         */
        public boolean isTimedOut()
        {
            if (m_TimedOut)
                return true;
            long remaining = getRemainingTime();
            boolean timedOut = remaining < 0;
            if (!m_Ended) {
                m_TimedOut = timedOut;
            }
            return m_TimedOut;

        }

        protected long getRemainingTime()
        {
            long remaining = m_EndTime - System.currentTimeMillis();
            return remaining;
        }

        /**
         * true if the task ended or timed out
         *
         * @return
         */
        public boolean isEnded()
        {
            return m_Ended || m_TimedOut;
        }

        /**
         * millisec before timeout - if ended this returns -1
         *
         * @return as above
         */
        public long timeRemaining()
        {
            if (isEnded())
                return -1;
            else {
                long remaining = getRemainingTime();
                return Math.max(0, remaining);
            }
        }


        /**
         * allow sort in order of time remaining - completed tasks go at the end
         *
         * @param o
         * @return
         */
        public int compareTo(Object o)
        {
            if (this == o)
                return 0;
            TaskTimer realO = (TaskTimer) o;
            if (isEnded()) {
                if (realO.isEnded()) {
                    return Util.compareObjects(this, realO);
                }
                else {
                    return 1;
                }
            }
            else {
                if (realO.isEnded()) {
                    return -1;
                }
                long left = timeRemaining();
                long oleft = realO.timeRemaining();
                if (left == oleft)
                    return Util.compareObjects(this, realO);
                return left < oleft ? -1 : 1;
            }
        }
    }
}
