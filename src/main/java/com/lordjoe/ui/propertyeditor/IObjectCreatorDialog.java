package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.IObjectCreatorDialog
 *  offer the user a modal dialog to create s new object
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IObjectCreatorDialog<T>
{
    public static IObjectCreatorDialog[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IObjectCreatorDialog.class;


    /**
     * set the style of the panel
     * @param style
     */
    public void applyStyle(IStylizer style);
    
    /**
     * offer the user a modal dialog to create s new object
     * @return possibly null created object -
     */
    public T createObject();
}
