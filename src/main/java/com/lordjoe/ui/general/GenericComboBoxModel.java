/*
 * @(#)DefaultComboBoxModel.java	1.19 04/05/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.lordjoe.ui.general;

import java.util.*;
import java.util.List;

import java.io.Serializable;

import javax.swing.*;

/**
 * Genericized Model for Combo boxes
 *
 * @author Steve Lewis
 * @version 1.19 05/05/04
 */

public class GenericComboBoxModel <T> extends AbstractListModel implements MutableComboBoxModel, Serializable
{
    private final List<T> objects;
    private T selectedObject;

    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public GenericComboBoxModel()
    {
        objects = new ArrayList<T>();
    }

    /**
     * Constructs a DefaultComboBoxModel object initialized with
     * an array of objects.
     *
     * @param items an array of Object objects
     */
    public GenericComboBoxModel(final T[] items)
    {
        objects = new ArrayList<T>(Arrays.asList(items));

        if (getSize() > 0)
        {
            selectedObject = getElementAt(0);
        }
    }

    /**
     * Constructs a DefaultComboBoxModel object initialized with
     * a vector.
     *
     * @param v a Vector object ...
     */
    public GenericComboBoxModel(List<? extends T> v)
    {
        objects = new ArrayList(v);

        if (getSize() > 0)
        {
            selectedObject = getElementAt(0);
        }
    }

    // implements javax.swing.ComboBoxModel
    /**
     * Set the value of the selected item. The selected item may be null.
     *                                                                                          <p>
     *
     * @param anObject The combo box value or null for no selection.
     */
    public void setSelectedItem(Object anObject)
    {
        if ((selectedObject != null && !selectedObject.equals(anObject)) ||
                selectedObject == null && anObject != null)
        {
            selectedObject = (T) anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem()
    {
        return selectedObject;
    }

    // implements javax.swing.ListModel
    public int getSize()
    {
        return objects.size();
    }

    // implements javax.swing.ListModel
    public T getElementAt(int index)
    {
        if (index >= 0 && index < objects.size())
            return objects.get(index);
        else
            return null;
    }

    /**
     * Returns the index-position of the specified object in the list.
     *
     * @param anObject
     * @return an int representing the index position, where 0 is
     *         the first position
     */
    public int getIndexOf(Object anObject)
    {
        return objects.indexOf(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void addElement(Object anObject)
    {
        objects.add((T) anObject);
        fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
        if (objects.size() == 1 && selectedObject == null && anObject != null)
        {
            setSelectedItem(anObject);
        }
    }

    // implements javax.swing.MutableComboBoxModel
    public void insertElementAt(Object anObject, int index)
    {
        objects.set(index, (T) anObject);
        fireIntervalAdded(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElementAt(int index)
    {
        if (getElementAt(index) == selectedObject)
        {
            if (index == 0)
            {
                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
            }
            else
            {
                setSelectedItem(getElementAt(index - 1));
            }
        }

        objects.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElement(Object anObject)
    {
        int index = objects.indexOf(anObject);
        if (index != -1)
        {
            removeElementAt(index);
        }
    }

    /**
     * Empties the list.
     */
    public void removeAllElements()
    {
        if (objects.size() > 0)
        {
            int firstIndex = 0;
            int lastIndex = objects.size() - 1;
            objects.clear();
            selectedObject = null;
            fireIntervalRemoved(this, firstIndex, lastIndex);
        }
        else
        {
            selectedObject = null;
        }
    }
}
