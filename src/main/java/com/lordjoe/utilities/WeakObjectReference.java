package com.lordjoe.utilities;

import java.lang.ref.WeakReference;


/*
 * com.lordjoe.Utilities.WeakObjectReference
 * Weak reference that works asa key in a hash table
 * @author smlewis
 * Date: May 1, 2003
 */

public class WeakObjectReference extends WeakReference
{
    public static final Class THIS_CLASS = WeakObjectReference.class;
    public static final WeakObjectReference[] EMPTY_ARRAY = {};

    private final int m_Hash;
    public WeakObjectReference(Object ref)
    {
        super(ref);
        m_Hash = System.identityHashCode(ref);
    }

    public int hashCode()
    {
        return(m_Hash);
    }

    /**
     * Equality is when the same object is references
     * @param test
     * @return
     */
    public boolean equals(Object test)
    {
        if(test == null) return(false);
        if(!(test instanceof WeakObjectReference))
             return(false);
        WeakObjectReference realTest = (WeakObjectReference)test;
        if(realTest.hashCode() != hashCode())
            return(false);
        return(realTest.get() == get());
     }
}