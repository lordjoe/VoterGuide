package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.IStylizer
 *   this functions like a style sheet to set look and feel of
 *   components - allows much look and feel to be externalised
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IStylizer
{
    public static IStylizer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IStylizer.class;

    /**
     * set the stype of the component to the current rules
     * @param jc
     */
    public void stylize(JComponent jc);
}
