package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.IAdapter
 *  implement the adabter pattern - not typically transform an object to a dispay
 *  form
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public interface IAdapter
{
    public static IAdapter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IAdapter.class;

    /**
     * transform an object to a different form
     * @param in non-null input object
     * @return non-null return
     */
    public Object transform(Object in);
}
