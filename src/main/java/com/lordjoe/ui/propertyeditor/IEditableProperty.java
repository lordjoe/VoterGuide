package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.IEditableProperty
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public interface IEditableProperty extends IObjectProperty
{
    public static final Class THIS_CLASS = IEditableProperty.class;
    public static final IEditableProperty EMPTY_ARRAY[] = {};

    /**
     * return the current value - possibly null
     * @return possibly null value
     */
    public Object getValue();

    /**
     * possibly null value to set
     * @param value
     */
    public void setValue(Object value);
}