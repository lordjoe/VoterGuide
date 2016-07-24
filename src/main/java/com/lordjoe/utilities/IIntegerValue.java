package com.lordjoe.utilities;

/**
 * An interface whichj allows an int to be
 * obtained without regard to whether it is fixed
 * or computed and how it is obtained Not any subclass of Integer
 * may implement this
 * @author Steve Lewis
 * @see IObjectDescructor
 */
public interface IIntegerValue {
    /**
     * return the value
     * @return as above
     */
    public int intValue();
}
