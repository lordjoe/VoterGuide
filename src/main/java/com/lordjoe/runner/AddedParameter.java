package com.lordjoe.runner;

/**
 * com.lordjoe.runner.AddedParameter
 *
 * @author Steve Lewis
 * @date Mar 13, 2007
 */
public class AddedParameter
{
    public static AddedParameter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AddedParameter.class;

    private final String m_Name;
    private final String m_Type;

    public AddedParameter(String name,String type)
    {
        m_Name = name;
        m_Type  = type;
    }


    public String getType()
    {
        return m_Type;
    }

    public String getName()
    {
        return m_Name;
    }
}
