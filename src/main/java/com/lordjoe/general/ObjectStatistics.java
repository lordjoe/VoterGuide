package com.lordjoe.general;

/**
 * com.lordjoe.general.ObjectStatistics
 *
 * @author slewis
 * @date May 26, 2005
 */
public interface ObjectStatistics
{
    public static final Class THIS_CLASS = ObjectStatistics.class;
    public static final ObjectStatistics EMPTY_ARRAY[] = {};

    public int getNumberStatistics();

    public String[] getStatisticNames();

    public int getStatisticIndex(String staticticName);

    public Float getStatistic(String staticticName);

    public Float getStatistic(int index);


}