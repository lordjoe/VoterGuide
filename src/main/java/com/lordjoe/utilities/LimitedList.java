package com.lordjoe.utilities;

import java.util.*;


/*
* Workd like a queue as things are added entries are removed
 * com.lordjoe.Utilities.LimitedList
 * Developed for testing garbage collection
 * @author smlewis
 * Date: May 1, 2003
 */

public class LimitedList extends ArrayList
{
    public static final Class THIS_CLASS = LimitedList.class;
    public static final LimitedList[] EMPTY_ARRAY = {};

    private final int m_MaxSize;

    /**
     * constrictor
     * @param max macimum size
     */
    public LimitedList(int max)
    {
        super();
        m_MaxSize = max;
    }

    /**
     * return the maximum size of the list
     * @return
     */
    public int getMaxSize()
    {
        return m_MaxSize;
    }

    /**
      * override makes sure size does not exceed x
      * @param added non-null object to add
      * @return see super
      */
     public boolean addAll(Collection added)
     {
         boolean ret = super.addAll(added);
         normalize();
         return(ret);
     }

    /**
     * override makes sure size does not exceed x
     * @param added non-null object to add
     * @return see super
     */
    public boolean add(Object added)
    {
        boolean ret = super.add(added);
        normalize();
        return(ret);
    }

    protected void normalize()
    {
        while(size() > m_MaxSize)
            remove(0);
    }
}