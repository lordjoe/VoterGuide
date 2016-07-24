package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.IObjectEditorDialog
 *  offer the user a modal dialog to edit an existing object
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IObjectEditorDialog<T>
{
    public static IObjectEditorDialog[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IObjectEditorDialog.class;

    /**
     * set the style of the panel
     * @param style
     */
    public void applyStyle(IStylizer style);
   
    /**
     * offer the user a modal dialog to edit an existing object
     * @return possibly null created object -
     */
    public T editObject();

    /**
     * return the component used for editing
     * @return non-nul;l component
     */
    public JComponent getEditorComponent();
}
