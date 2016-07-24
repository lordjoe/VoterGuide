package com.lordjoe.concurrent;

import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.concurrent.ConflictSetImpl
 *
 * @author Steve Lewis
 * @date Nov 7, 2005
 */

/**
 * this class defines a conflict set
 */
public class ConflictSetImpl implements IConflictSet
{
    private final Set<Object> m_Current;
    private final Set<IConflict> m_Conflicts;

    public ConflictSetImpl()
    {
        m_Current = new CopyOnWriteArraySet<Object>();
        m_Conflicts = new CopyOnWriteArraySet<IConflict>();
    }

    public synchronized void addConflict(IConflict cf)
    {
        m_Conflicts.add(cf);
    }


    public synchronized void awaitNoConflicts(Object item)
    {
        if (m_Conflicts.isEmpty())
            return;
        synchronized (this) {
            try {
                while (itemConflicts(item)) wait();
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * @param item    non-null item
     * @param timeout millisec timeout
     * @return true if there are no conflicts
     */
    public synchronized boolean awaitNoConflicts(Object item, long timeout)
    {
        if (m_Conflicts.isEmpty())
            return true;
        synchronized (this) {
            try {
                long start = System.currentTimeMillis();
                while (itemConflicts(item)) {
                    wait(timeout / 10);
                    if (start + timeout < System.currentTimeMillis())
                        return !itemConflicts(item); // timeout
                }
                return true; // no conflicts
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean itemConflicts(Object item)
    {
        IConflict[] conflicts = new IConflict[m_Conflicts.size()];
        for (int i = 0; i < conflicts.length; i++) {
            IConflict conflict = conflicts[i];
            if (conflict.hasConflict(m_Current)) {
                return true;
            }
        }
        return false;
    }

    /**
     * add an item to the current set
     * @param item  non-null item
     * @param timeout max time to wait for no conflicts
     * @return  true if add succeeded
     */
    public synchronized boolean addItem(Object item, long timeout)
    {
        if (awaitNoConflicts(item, timeout)) {
            m_Current.add(item);
            return true;
        }
        return false;
    }

    /**
     * add an item to the current set
     * waiting until no conflicts
     * @param item non-null item
     */
    public synchronized void addItem(Object item)
    {
        awaitNoConflicts(item);
        m_Current.add(item);
    }

    /**
     * remove an item from the current set
     *
     * @param item non-null item
     */
    public synchronized void releaseItem(Object item)
    {
        m_Current.remove(item);
        notifyAll();
    }
}
