package com.lordjoe.lang;

/**
 * com.lordjoe.lang.NanoTimer
 *
 * @author Steve Lewis
 * @date Nov 21, 2007
 */
public class NanoTimer {
    public static Class THIS_CLASS = NanoTimer.class;
    public static final NanoTimer[] EMPTY_ARRAY = {};

    private long m_Start;
    public NanoTimer()
    {
       reset();
    }

    public void reset()
    {
       m_Start = System.nanoTime();
    }

    public long getStart()
    {
        return m_Start;
    }


    public long getElapsedNanoSec()
    {
       return  System.nanoTime() - getStart();
    }

    public long getElapsedMilliSec()
    {
       return  getElapsedNanoSec() / 1000000;
    }

}
