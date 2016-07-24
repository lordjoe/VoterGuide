package com.lordjoe.propertyeditor;

import java.beans.*;

/**
 * com.lordjoe.propertyeditor.ICollectionWrapper
 *   this represents a general way to handle a collection
 *   in a UI component
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface ICollectionWrapper<T>  extends IGenericWrapper
{
    public static IPropertyWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IPropertyWrapper.class;


    /**
     * return the name of the property - usually as a java bean name
     * @return non-null String
     */
    public String getCollectionName();

    /**
     * return the value represented by the editor
     * @return
     */
    public T[] getCurrentItems();

    /**
     * add an item to the collection
     * @param value
     */
    public void addItem(T value) ;

    /**
     * remove an item from the collection
     * @param value
     */
    public void removeItem(T value) ;


    public boolean isRemoveSupported();

    public boolean isAddSupported();

}