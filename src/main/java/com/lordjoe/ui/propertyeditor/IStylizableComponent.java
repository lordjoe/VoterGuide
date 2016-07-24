package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.IStylizer
 *   a component designed to work withj s stylizer
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IStylizableComponent
{
    public static IStylizer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IStylizer.class;

    /**
     * return sub components to a stylizer
     * @return
     */
    public JComponent[] getStylizableComponents();

    /**
     * return self as Jcomponent to a stylizer
     * @return
     */
    public JComponent getStylizableSelf();

}