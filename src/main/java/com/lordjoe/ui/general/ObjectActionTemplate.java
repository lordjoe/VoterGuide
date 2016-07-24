package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.general.ObjectActionTemplate
 *    template for
 * @author Steve Lewis
 * @date Jul 21, 2008
 */
public abstract class ObjectActionTemplate<T> implements INameable
{
    public static ObjectActionTemplate[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectActionTemplate.class;

    private final String m_Name;
    public ObjectActionTemplate(String name)
    {
        m_Name = name;
    }

    /**
     * return a human readable name
     *
     * @return non-null name
     */
    public String getName()
    {
        return m_Name;
    }



    public abstract ObjectAction<T> buildAction(T value);

}