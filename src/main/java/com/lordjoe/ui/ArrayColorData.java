package com.lordjoe.ui;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * com.lordjoe.ui.ArrayColorData
 *
 * @author Steve Lewis
 * @date Sep 15, 2005
 */
public class ArrayColorData implements IArrayColorData
{
    private int[] m_Data;
    private Color[] m_Colors;
    private int m_Width;

    private final List m_Listeners;

    public ArrayColorData()
    {
        m_Listeners = new ArrayList();
    }

    public ArrayColorData(int[] data, int width, Color[] colors)
    {
        this();
        if(data == null)
            throw new IllegalArgumentException("data cannot be null");
        m_Data = data;
        m_Width = width;
        m_Colors = colors;
        initializeData();
    }

    public int[] getData()
    {
        return m_Data;
    }

    public int getWidth()
    {
        return m_Width;
    }

    public Color[] getColors()
    {
        return m_Colors;
    }

    public void setData(int[] pData)
    {
        m_Data = pData;
    }

    public void setWidth(int pWidth)
    {
        m_Width = pWidth;
    }

    public void setColors(Color[] pColors)
    {
        m_Colors = pColors;
    }


    /**
     * called before drawing is started
     */
    public void initializeData()
    {
    }

    /**
     * return text to display for the location
     *
     * @param location loaciton
     * @return possibly null text
     */
    public String getLocationText(int location)
    {
        return null;
    }

    /**
     * return true if the location is selected
     *
     * @param location location location of an element in the array
     * @return as above
     */
    public boolean isLocationSelected(int location)
    {
        return false;
    }

    /**
     * return the color for a specific element
     *
     * @param location location of an element in the array
     * @return possibly null Color - null says don't draw
     */
    public Color getLocationColor(int location)
    {
        if (location < getTotalElements()) {
            int ColorIndex = m_Data[location];
            if (ColorIndex < 0 || ColorIndex >= m_Colors.length)
                return null;
            return m_Colors[ColorIndex];
        }
        return null;
    }

    /**
     * return the length of one row
     *
     * @return
     */
    public int getWidthInElements()
    {
        return m_Width;
    }

    /**
     * return the number of elements
     *
     * @return
     */
    public int getTotalElements()
    {
        if(m_Data == null)
            return 0;
        return m_Data.length;
    }

    /**
     * add listener
     *
     * @param listener non-null listener
     */
    public synchronized void addDataChangedListener(IDataChangedListener listener)
    {
        m_Listeners.add(listener);
    }

    /**
     * remove listener
     *
     * @param listener non-null listener
     */
    public synchronized void removeDataChangedListener(IDataChangedListener listener)
    {
        m_Listeners.remove(listener);
     }

    public void notifyDataChangedListeners()
    {
        synchronized(m_Listeners)   {
            if(m_Listeners.isEmpty())
               return;
            IDataChangedListener[] listeners = new IDataChangedListener[m_Listeners.size()];
            m_Listeners.toArray(listeners);
            for (int i = 0; i < listeners.length; i++) {
                IDataChangedListener listener = listeners[i];
                 listener.onDataChanged(this);
            }
        }
    }

}
