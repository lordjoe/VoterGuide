package com.lordjoe.general;

/**
 * com.lordjoe.general.ObjectStatisticsCollection
 *
 * @author slewis
 * @date May 26, 2005
 */
public interface ObjectStatisticsCollection
{
    public static final Class THIS_CLASS = ObjectStatisticsCollection.class;
    public static final ObjectStatisticsCollection EMPTY_ARRAY[] = {};

    public int getNumberStatistics();

    public String[] getStatisticNames();

    public int getStatisticIndex(String staticticName);

    public int getNumberElements();

    public ObjectStatistics getElement(int index);
}