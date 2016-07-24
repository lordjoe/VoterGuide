package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.IGenericPropertyEditor
 *
 * @author Steve Lewis
 * @date Dec 17, 2007
 */
public interface IGenericPropertyEditor {
    public static IGenericPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IGenericPropertyEditor.class;

    /**
     * called when the display is about ready to come up
     */
    public void rebuildPanel();

    /**
     * this neeed not Be a component but is better manage one
     * @return
     */
    public JComponent getComponent();

    /**
     * set the value to that represented by the data source
     */
    public void reconcile();

    /**
     * set the value of the source to that represented by the editor
     */
    public void commit();

    /**
     * roll changes back to creation or lar commit
     */
    public void rollback();
}
