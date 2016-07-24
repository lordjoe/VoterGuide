package com.lordjoe.lang;

import com.lordjoe.utilities.*;

import java.util.*;


/**
 * com.nanomaterials.lang.FileLoggerObject
 * Logger based on a file
 * @author slewis
 * @date May 2, 2005
 */
public abstract class AbstractLoggerObject  implements ILoggerObject,Comparable
{
    public static final Class THIS_CLASS = AbstractLoggerObject.class;
    public static final AbstractLoggerObject EMPTY_ARRAY[] = {};

    private final String m_Name;
    private final List m_Listeners;
    public AbstractLoggerObject(String name)
    {
        m_Name = name;
        m_Listeners = new ArrayList();
    }


    public int compareTo(Object o)
    {
        if(this == o)
            return 0;
        ILoggerObject realTest = (ILoggerObject)o;
        return getName().compareTo(realTest.getName());
    }

    public String getName()
    {
        return m_Name;
    }

    protected synchronized void notifyChangeListeners(int ChangeType)
     {
        if(m_Listeners.size() == 0)
            return;
         notifyChangeListeners(ChangeType,"");
     }

    protected synchronized void notifyChangeListeners(int ChangeType,String text)
    {
       if(m_Listeners.size() == 0)
           return;
        LogChangedEvent change = new LogChangedEvent(this,ChangeType,text);
        LogChangedListener[] ret = new LogChangedListener[m_Listeners.size()];
        m_Listeners.toArray(ret);
        for (int i = 0; i < ret.length; i++)
        {
            LogChangedListener logChangedListener = ret[i];
            logChangedListener.onLogChanged(change);
        }
    }

    /**
     * add a listener for changes
     *
     * @param added
     */
    public synchronized void addLogChangedListener(LogChangedListener added)
    {
        m_Listeners.add(added);
    }

    /**
     * remove a listener for changes
     *
     * @param removed
     */
    public synchronized void removeLogChangedListener(LogChangedListener removed)
    {
         m_Listeners.remove(removed);
    }
}
