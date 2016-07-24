package com.lordjoe.lang;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.lang.NamedObject
 * base class for NamedObects
 * @author slewis
 * @date Jan 7, 2005
 */
public class NamedObject implements INamedObject,Comparable
{
    public static final Class THIS_CLASS = NamedObject.class;
    public static final NamedObject EMPTY_ARRAY[] = {};

    private final String m_Name;

    public NamedObject(String name)
    {
        m_Name = name;
    }

    public String getName()
    {
        return m_Name;
    }


    public int compareTo(Object o)
    {
        if (!(o instanceof NamedObject))
            throw new IllegalArgumentException("wrong class");
        NamedObject realTest = (NamedObject) o;
        int ret = getName().compareTo(realTest.getName());
        return ret;
    }

    public boolean equals(Object test)
    {
        if (test == null)
            return false;
        if (test.getClass() != getClass())
            return false;
        NamedObject realTest = (NamedObject) test;
        return
                getName().equals(realTest.getName());

    }

    public int hashCode()
    {
        return getName().hashCode();
    }

    public String toString()
    {
        return getName();
    }

}
