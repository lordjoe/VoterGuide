package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.IPropertySource
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public interface IPropertySource
{
    public static final Class THIS_CLASS = IPropertySource.class;
    public static final IPropertySource EMPTY_ARRAY[] = {};

    /**
     * return the property value
     * @param name name non-null name of an existing or creatable
     *   property
     * @return possibly null value
     */
    public Object getProperty(String name);

    /**
     * set the value of one property
     * @param name non-null name of an existing or creatable
     *   property
     * @param value possibly null value
     */
    public void setProperty(String name,Object value);

    /**
     * return the names of all properties
     * @return
     */
    public String[] getPropertyNames();


}