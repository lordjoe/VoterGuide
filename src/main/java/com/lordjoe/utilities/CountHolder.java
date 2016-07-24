package com.lordjoe.utilities;

import java.io.Serializable;
import java.util.*;

/**
 * com.lordjoe.Utilities.CountHolder
 * associated a name with count - used for reporting
 * @Author Steve Lewis smlewis@lordjoe.com
 */
public class CountHolder   implements INameable,Serializable {
    public static final CountHolder[] EMPTY_ARRAY = {};

    private String m_Name;
    private int m_Count;

    public  CountHolder(String name)
    {
        setName(name);
    }



    public String getName() {
        return m_Name;
    }

    public void setName(String aName) {
        this.m_Name = aName;
    }

    public int getCount() {
        return m_Count;
    }

    protected void setCount(int n)
    {
         m_Count = n;
    }

    public synchronized void clearCount() {
        this.m_Count = 0;
    }

    public synchronized void addCount(int added) {
        m_Count += added;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getName());
        sb.append(" ") ;
        sb.append(getCount());
        return(sb.toString());
    }

    public synchronized void combine(  CountHolder added)
    {
        addCount(added.getCount());
    }

    public synchronized CountHolder cloneMe()
    {
        CountHolder ret = new CountHolder(getName());
        ret.combine(this);
        return(ret);
    }
}
