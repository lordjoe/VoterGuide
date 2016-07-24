package com.lordjoe.concurrent;

import java.util.*;

/**
 * com.lordjoe.concurrent.ConflictImpl
 *
 * @author Steve Lewis
 * @date Nov 7, 2005
 */
public class ConflictImpl implements IConflict
{
    private final Map<Object, Object> m_Items;

    public ConflictImpl()
    {
        m_Items = new HashMap<Object, Object>();
    }

    /**
     * true if the
     * @param c
     * @return
     */
    public synchronized boolean hasConflict(Collection c)
    {
        Collection copy = new ArrayList(c); // consider hashing
        for (Iterator it = c.iterator(); it.hasNext();) {
            Object test = it.next();
            if (!hasConflict(test, copy))
                return false;
        }
        return true; // no conflict
    }


    public boolean hasConflict(Object test, Collection c)
    {
        if (test.getClass().isArray())
            return hasConflict((Object[]) test, c);
        else {
            if (c.contains(test)) {
                c.remove(test);
                return true;
            }
            else
                return false;
        }

    }


    public synchronized void addItem(Object added)
    {
        Object prev = m_Items.get(added);
        if (prev == null) {
            m_Items.put(added, added);
            return;
        }
        else {
            m_Items.remove(prev);
            if (prev.getClass().isArray()) {

            }
            else {
                Object[] arry = {prev, added};
                m_Items.put(added, arry);

            }
        }
    }
}
