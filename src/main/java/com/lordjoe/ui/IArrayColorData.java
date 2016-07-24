package com.lordjoe.ui;

import java.awt.*;

/**
 * com.lordjoe.ui.IArrayColorData
 * this is a general interface for representing the state of a
 * number of locations on a 2d surface - the most obvious
 * use is for draging chips
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IArrayColorData
{
    public static final Class THIS_CLASS = IArrayColorData.class;
    public static final IArrayColorData EMPTY_ARRAY[] = {};

    /**
     * called before drawing is started
     */
    public void initializeData();


    /**
     * return text to display for the location
     * @param location  loaciton
     * @return possibly null text
     */
    public String getLocationText(int location);

    /**
     * return true if the location is selected
     * @param location location location of an element in the array
     * @return as above
     */
    public boolean isLocationSelected(int location);

    /**
     * return the color for a specific element
     * @param location location of an element in the array
     * @return possibly null Color - null says don't draw
     */
    public Color getLocationColor(int location);

    /**
     * return the length of one row
     * @return
     */
    public int getWidthInElements();
    /**
      * return the number of elements
      * @return
      */
     public int getTotalElements();

    /**
     * add listener
     * @param listener non-null listener
     */
    public void addDataChangedListener(IDataChangedListener listener);

    /**
     * remove listener
     * @param listener non-null listener
     */
     public void removeDataChangedListener(IDataChangedListener listener);



}