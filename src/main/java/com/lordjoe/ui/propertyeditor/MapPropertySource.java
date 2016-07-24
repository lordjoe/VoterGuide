package com.lordjoe.ui.propertyeditor;

import com.lordjoe.utilities.*;

import java.util.*;


/**
 * com.lordjoe.ui.propertyeditor.MapPropertySource
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class MapPropertySource implements IPropertySource
{
    public static final Class THIS_CLASS = MapPropertySource.class;
    public static final MapPropertySource EMPTY_ARRAY[] = {};

    private final Map<String,Object> m_Source;

    public MapPropertySource()
    {
        m_Source = new HashMap<String,Object>();
    }
    public  MapPropertySource(Map<String,? extends Object> source)
    {
        m_Source = ( Map<String,Object>)source;
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
        return m_Source.get(name);
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
        m_Source.put(name,value);
    }

    /**
     * return the names of all properties
     *
     * @return
     */
    public String[] getPropertyNames()
    {
        return Util.collectionToStringArray(m_Source.keySet());
    }

}
