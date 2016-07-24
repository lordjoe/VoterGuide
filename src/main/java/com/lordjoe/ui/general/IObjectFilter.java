package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.IObjectFilter
 *    genaral filter for objects
 * @author Steve Lewis
 * @date Jul 4, 2008
 */
public interface IObjectFilter<T>
{
    public static IObjectFilter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IObjectFilter.class;

    /**
     * return true if test is acceptable
     * @param test non-null test object
     * @return as above
     */
    public boolean acceptable(T test);
}
