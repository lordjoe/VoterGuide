package com.lordjoe.timeout;


import com.lordjoe.utilities.*;

import java.util.*;

/**
 * com.cbmx.timeout.TaskManager
 *
 * @author Steve Lewis
 * @date Apr 26, 2006
 */
public class TaskManager implements Runnable
{
    public final static TaskManager[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = TaskManager.class;

    public static final int DEFAULT_TIMEOUT = 100000;

    public static final TaskManager INSTANCE = new TaskManager();


    private final Set m_Tasks;
    private TaskTimer m_NextTask;

    private TaskManager()
    {
        m_Tasks = new HashSet();
        Thread thr = new Thread(this, "Task Manager Thread");
        thr.setDaemon(true);
        thr.start();
    }

    public void run()
    {
        while (true) {
            waitNextTask();
        }
    }


    public TaskWatcher buildTaskWatcher()
    {
        return new TaskWatcherImpl(this);
    }

    public TaskWatcher buildTaskWatcher(Object data)
    {
        return new TaskWatcherImpl(this, data);
    }


    protected void waitNextTask()
    {
        synchronized (m_Tasks) {
            while (true) {
                TaskTimer nextTask = m_NextTask;
                long timeout = DEFAULT_TIMEOUT;
                if (nextTask != null) {
                    timeout = Math.min(nextTask.timeRemaining(), timeout);
                }
                try {
                    m_Tasks.wait(timeout);
                }
                catch (InterruptedException ex) {
                    throw new ThreadInterruptedException();
                }
                if (nextTask != null && nextTask == m_NextTask) {
                    if (nextTask.isTimedOut()) {
                        reorderTasks();
                    }
                }
            }

        }
    }

    /**
     * should only be called by a task watcher
     * @param task
     */
    public void startTaskWatch(TaskTimer task)
    {
        synchronized (m_Tasks) {
            m_Tasks.add(task);
            reorderTasks();
        }
    }

    /**
      * should only be called by a task watcher
      * @param task
      */
     public void endTaskWatch(TaskTimer task)
    {
        synchronized (m_Tasks) {
            m_Tasks.remove(task);
            reorderTasks();
        }
    }

    protected void reorderTasks()
    {
        synchronized (m_Tasks) {
            if (m_Tasks.isEmpty()) {
                m_NextTask = null;
                m_Tasks.notifyAll();
                return;
            }
            TaskTimer[] tasks = getAllTasks();

            for (int i = 0; i < tasks.length; i++) {
                TaskTimer task = tasks[i];
                if (task.isEnded()) {
                    m_Tasks.remove(task);
                }
                if (task.isTimedOut()) {
                    m_Tasks.remove(task);
                    if (task instanceof WatchedTaskTimer) {
                        ((WatchedTaskTimer) task).getWatcher().notifyListeners(task);
                    }
                }
            }
            if (m_Tasks.isEmpty()) {
                m_NextTask = null;
            }
            else {
                tasks = getAllTasks();

//                    for (int i = 0; i < tasks.length; i++) {
//                        TaskTimer task = tasks[i];
//                        System.out.println(task);
//                    }
//                    System.out.println("============================");
//
                m_NextTask = tasks[0];
            }
            m_Tasks.notifyAll();
        }

    }

    protected TaskTimer[] getAllTasks()
    {
        TaskTimer[] tasks = new TaskTimer[m_Tasks.size()];
        m_Tasks.toArray(tasks);
        Arrays.sort(tasks);
        return tasks;
    }


}
