package com.lordjoe.logging;

import com.lordjoe.ui.general.*;

/**
 * com.lordjoe.logging.ILoggedObjectFilter
 *
 * @author Steve Lewis
 * @date Jun 30, 2008
 */
public interface ILoggedObjectFilter<T> extends IObjectFilter<LoggedObject<T>>
{
    public static ILoggedObjectFilter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ILoggedObjectFilter.class;

    /**
     * return true if the data is acceptable
     * @param test non-null test object
     * @return true if acceptable
     */
    public boolean acceptable(LoggedObject<T> test);

}
