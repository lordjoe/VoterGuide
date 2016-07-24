package com.lordjoe.runner;

/**
 * com.lordjoe.runner.IRestriction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IRestriction
{
    public static IRestriction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IRestriction.class;

    public boolean isSatisfied(Object test);
}
