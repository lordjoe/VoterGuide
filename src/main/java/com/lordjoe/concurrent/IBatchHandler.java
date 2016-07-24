package com.lordjoe.concurrent;

/**
 * com.lordjoe.concurrent.IBatchHandler
 *
 * @author Steve Lewis
 * @date Jul 5, 2009
 */
public interface IBatchHandler<T>
{
    public static IBatchHandler[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IBatchHandler.class;

    public void handleBatch(T[] added);
}
