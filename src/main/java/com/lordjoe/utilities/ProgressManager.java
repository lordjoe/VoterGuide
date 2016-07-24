/**{ file
 @name ProgressManager.java
 @function ProgressManager shows the user how a task is progressing
 @author> Steven Lewis
 @copyright>
  ************************
  *  Copyright (c) 1996,97
  *  Steven M. Lewis
 ************************

 @date> Fri May 30 11:05:43  1997
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import java.util.*;
import java.awt.Frame;

/**
 * { class
 *
 * @name ProgressManager
 * @function ProgressManager shows the user how a task is progressing
 * note this class is a Nulliton
 * }
 */
public class ProgressManager
{


    //- ******************* 
    //- Fields 
    /**
     * { field
     *
     * @name gMyframe
     * @function Frame dirplaying data
     * }
     */
    private static Hashtable gThreadToHandler = new Hashtable();

    private static Frame gActiveFrame;

    public static void setActiveFrame(Frame AFrame) {
        gActiveFrame = AFrame;
    }

    public static Frame getActiveFrame() {
        return (gActiveFrame);
    }


    private ProgressManager(Thread CallingThread) {
        gThreadToHandler.put(CallingThread, this);
    }

    private void dispose() {
        gThreadToHandler.remove(m_AssociatedThread);
    }


    /**
     * { field
     *
     * @name gMyframe
     * @function Frame dirplaying data
     * }
     */
    protected static ProgressDialog gMyDialog = null;

    public static ProgressDialog getProgressDialog() {
        return (gMyDialog);
    }


    /**
     * { field
     *
     * @name gCurrentTasks
     * @function collection of tasks
     * }
     */
    private Vector m_CurrentTasks = new Vector();

    // all registered tasks
    /**
     * { field
     *
     * @name m_AssociatedThread
     * @function Thread associated with this progress manager
     * }
     */
    private Thread m_AssociatedThread;


    //- ******************* 
    //- Methods 
    /**
     * { method
     *
     * @name updateDisplay
     * @function set the display to show current data
     * Causes appropriate changes to be shown to the user
     * }
     */
    public synchronized void updateDisplay() {
        if (gMyDialog == null) {
            gMyDialog = new ProgressDialog();
        }
        TaskProgress[] tasks = new TaskProgress[m_CurrentTasks.size()];
        m_CurrentTasks.copyInto(tasks);
        gMyDialog.updateDisplay(tasks);
    }

//
// report that the given task has changed

    /**
     * { method
     *
     * @param target task which has changed
     *               }
     * @name update
     * @function resigter changes in me and the display
     */
    public static void update() {
        ProgressManager realManager = getCurrentManager();
        realManager.updateDisplay();
    }

    /**
     * { method
     *
     * @param target task which has changed
     *               }
     * @name update
     * @function resigter changes in me and the display
     */
    public static void update(ITaskProgress tp) {
        ProgressManager realManager = getCurrentManager();
        //  realManager.doUpdate(tp);
    }

//
// report that the given task has changed

    /**
     * { method
     *
     * @name clearTasks
     * @function drop all tasks
     * }
     */
    public static void clearTasks() {
        ProgressManager realManager = getCurrentManager();
        realManager.doClearTasks();
    }

    /**
     * { method
     *
     * @name clearTasks
     * @function drop all tasks
     * }
     */
    public synchronized void doClearTasks() {
        while (m_CurrentTasks.size() > 0) {
            done(((ITaskProgress) m_CurrentTasks.elementAt(m_CurrentTasks.size() - 1)));
        }
    }

    /**
     * { method
     *
     * @param target task to register
     *               }
     * @name register
     * @function add a task to the list of active tasks
     * this will usually add it to the display
     */
    public static void register(TaskProgress target) {
        ProgressManager realManager = getCurrentManager();
        realManager.doRegister(target);
    }

    /**
     * { method
     *
     * @param target task to register
     *               }
     * @name register
     * @function add a task to the list of active tasks
     * this will usually add it to the display
     */
    public synchronized void doRegister(TaskProgress target) {

        m_CurrentTasks.addElement(target);
        updateDisplay();
    }

    /**
     * { method
     *
     * @param target completed task
     *               }
     * @name done
     * @function mark a task as being completed
     */
    public static void done(ITaskProgress target) {
        ProgressManager realManager = getCurrentManager();
        realManager.doDone(target);
    }

    public synchronized void doDone(ITaskProgress target) {
        synchronized (m_CurrentTasks) {
            m_CurrentTasks.removeElement(target);
        }
        updateDisplay();
    }

    protected static ProgressManager getCurrentManager() {
        Thread currentThread = Thread.currentThread();
        ProgressManager currentManager = (ProgressManager) gThreadToHandler.get(currentThread);
        if (currentManager == null) {
            currentManager = new ProgressManager(currentThread);
        }
        return (currentManager);
    }

    protected static void validateThreads() {
        Vector DeadThreads = new Vector();
        // find dead threads
        Enumeration threads = gThreadToHandler.keys();
        while (threads.hasMoreElements()) {
            Thread test = (Thread) threads.nextElement();
            if (!test.isAlive())
                DeadThreads.addElement(test);

        }
        // drop dead threads
        for (int i = 0; i < DeadThreads.size(); i++)
            gThreadToHandler.remove(DeadThreads.elementAt(i));
    }

//- *******************
//- End Class ProgressManager
}
