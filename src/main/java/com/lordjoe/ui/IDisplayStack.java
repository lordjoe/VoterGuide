package com.lordjoe.ui;

import com.lordjoe.ui.general.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.IDisplayStack
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public interface IDisplayStack {
    public static IDisplayStack[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IDisplayStack.class;

    /**
     * set what to show if there is nothing in the display statc
     * @param name  component name
     * @param c   component
     */
    public void setDefaultComponent(String name,JComponent c);

    /**
     * show the previously displayed comonent
     */
    public void popComponent();

    /**
     * push the component onto a component stack
     * @param name
     * @param c
     */
    public void pushComponent(String name, JComponent c);

    /**
     * return the number of elements on the stack
     * @return  as above
     */
    public int getStackSize();
    /**
     * return the names of the stacked items
     * @return  as above
     */
    public String[] getStackItems();


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addIDisplayStackChangeListener(IDisplayStackChangeListener added) ;

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeIDisplayStackChangeListener(IDisplayStackChangeListener removed);

}
