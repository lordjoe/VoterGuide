package com.lordjoe.runner;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.runner.RunParameter
 *
 * @author Steve Lewis
 * @date May 30, 2008
 */
public class RunParameter  implements INamedObject
{
    public static RunParameter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunParameter.class;

    private final String m_Name;
    private final String m_Type;
    private final String m_Value;
    private final String m_Default;
    public RunParameter(String name,String cls,String defaultVal,String value)
    {
        m_Name = name;
        m_Type = cls;
        m_Value = value;
        m_Default = defaultVal;
    }

    public Object getObjectValue()
    {
       return getValue();
    }
    public String getValue()
    {
        return m_Value;
    }

    public String getDefault()
    {
        return m_Default;
    }

    public String getName()
    {
        return m_Name;
    }

    public String getType()
    {
        return m_Type;
    }

    public String toString()
    {
        return "Parameter " + getName() + " of Type " + getClass();
    }
    public int hashCode()
    {
        return getName().hashCode() ^ getClass().hashCode();
    }

    public boolean equals(Object o)
    {
        if(getClass() != o.getClass())
            return false;
        RunParameter realO = (RunParameter)o;
        return getName().equals(realO.getName()) &&
                getType() == realO.getType();
    }
}
