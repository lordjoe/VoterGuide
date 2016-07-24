package com.lordjoe.ui.propertyeditor;

import com.lordjoe.ui.general.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.ICollectionPropertyPanel
 *
 * @author Steve Lewis
 * @date Jan 28, 2008
 */
public interface ICollectionPropertyPanel<T> extends IDisplay, IStylizableComponent {
    public static ICollectionPropertyPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ICollectionPropertyPanel.class;

    public String getCollectionName();

    public Class getType();

    public void setSubject(Object subject); //throws IllegalSubjectException


    public void removeAll();

    public Object[] getCurrentItems();

    public void addItem(T item);

    public void removeItem(T item);

    public void clearItems();

    public void setCurrentItems(T[] items);

    public JComponent[] getStylizableComponents();

    public JComponent getStylizableSelf();

    public void onItemChanged(T item);
}
