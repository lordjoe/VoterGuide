package com.lordjoe.utilities;

/**
 * com.lordjoe.Utilities.ObjectCount
 * A value object for pairong soemthing with a count
 * @2uthor Steve Lewis
 * @date Jul 29, 2003
 */
public class ObjectCount
{
    public static Class THIS_CLASS = ObjectCount.class;
    public static final ObjectCount[] EMPTY_ARRAY = {};

    public final int count;
    public final Object target;

    /**
     * condtructor
     * @param aTarget non-null target
     * @param aCount   count
     */
    public ObjectCount(Object aTarget,int aCount) {
        count = aCount;
        if(aTarget == null)
           throw new IllegalArgumentException("Target must be non-null");
        target = aTarget;
    }

}

