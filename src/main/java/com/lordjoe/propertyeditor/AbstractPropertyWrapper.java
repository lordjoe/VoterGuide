package com.lordjoe.propertyeditor;

import com.lordjoe.lib.xml.*;

/**
 * com.lordjoe.propertyeditor.AbstractPropertyWrapper
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public abstract  class AbstractPropertyWrapper<T> implements IPropertyWrapper<T>
{
    public static AbstractPropertyWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractPropertyWrapper.class;

    private final ClassProperty m_Property;
    private final Object m_Target;

    public AbstractPropertyWrapper(ClassProperty pProperty, Object pTarget)
    {
        m_Property = pProperty;
        m_Target = pTarget;
    }

    public ClassProperty getProperty()
    {
        return m_Property;
    }


    public Class getOwningClass()
    {
        return getProperty().getOwningClass();
    }

    public Object getTarget()
    {
        return m_Target;
    }


}
