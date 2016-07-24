package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.EditedPropertyChangeListener
 *
 * @author Steve Lewis
 * @date Dec 15, 2007
 */
public interface EditedPropertyChangeListener {
    public static EditedPropertyChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = EditedPropertyChangeListener.class;

    public void onEditedPropertyChange(Object oldValue,Object value);
}
