package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.ICollectionHolder
 *
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public interface IObjectEditor<T>
{
    public static IObjectEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IObjectEditor.class;

    /**
     * do whatever it takes to edit an object
     * @param l non-null object to edit
     */
    public T performEdit(T  l);

}