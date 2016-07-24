package com.lordjoe.general;

import com.lordjoe.utilities.*;


/**
 * com.lordjoe.general.UniqueElapsedTime
 * provides a unigue time basd identifier
 * also can be used as a slightly imperfect timer
 * @author slewis
 * @date Jan 11, 2005
 */
public abstract class UniqueElapsedTime
{
    public static final Class THIS_CLASS = UniqueElapsedTime.class;
    public static final UniqueElapsedTime EMPTY_ARRAY[] = {};

    private static long gStartTime;
    private static long gCurrentValue;

    /**
     * reset the counter - use this cautiously
     */
    public static synchronized void reset()
    {
        gStartTime = TimeOps.now();
        gCurrentValue = gStartTime;
    }

    /**
     * return a number which guaranteed
     * until reset is called to be
     * 1) unique
     * 2) ascending
     * 3) approximatly the time in millisec since
     * reset was called or the first request was made
     * This makes the return good for constucting identifiers
     *
     * @return as above
     */
    public static synchronized long getUniqueElapsedTime()
    {
        if (gStartTime == 0)
            reset();
        long now = TimeOps.now();
        if (now > gCurrentValue)
        {
            gCurrentValue = now;
        }
        else
        {
            gCurrentValue++;
        }
        return gCurrentValue - gStartTime;
    }


    private UniqueElapsedTime()
    {
    }
}
