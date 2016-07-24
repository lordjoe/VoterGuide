package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.DataAvailableListener
 *  general purpose notification that some data
 *   is not available - frequently used when data is
 *  read through a web service or another thread
 * @author Steve Lewis
 * @date Sep 13, 2007
 */
public interface DataAvailableListener
{
    public static DataAvailableListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DataAvailableListener.class;

    /**
     * respond to availability of new data
     *   normally you will uery the source for data
     * @param source non-null source
     */
    public void onDataAvailable(Object source);
}