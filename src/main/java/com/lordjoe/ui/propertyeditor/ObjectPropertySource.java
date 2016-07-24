package com.lordjoe.ui.propertyeditor;

import com.lordjoe.lib.xml.*;


/**
 * com.lordjoe.ui.propertyeditor.ObjectPropertySource
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class ObjectPropertySource
{
    public static final Class THIS_CLASS = ObjectPropertySource.class;
    public static final ObjectPropertySource EMPTY_ARRAY[] = {};
    private final Object m_Source;

     public  ObjectPropertySource(Object source)
    {
        m_Source = source;
    }
    /**
     * return the property value
     *
     * @param name name non-null name of an existing or creatable
     *             property
     * @return possibly null value
     */
    public Object getProperty(String name)
    {
        return ClassAnalyzer.getProperty(m_Source,name);
    }

    /**
     * set the value of one property
     *
     * @param name  non-null name of an existing or creatable
     *              property
     * @param value possibly null value
     */
    public void setProperty(String name,Object value)
    {
        ClassAnalyzer.setProperty(m_Source,name,value);
    }

    /**
     * return the names of all properties
     *
     * @return
     */
    public String[] getPropertyNames()
    {
        return ClassAnalyzer.getPropertyNames(m_Source.getClass());
    }

}
