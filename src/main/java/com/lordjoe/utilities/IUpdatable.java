package com.lordjoe.utilities;

/**
 * since reading the System time is frequently inefficient we use one
 * instance to keep a record good to the second or so
 * @author Steve Lewis
 */
public interface IUpdatable {
    /**
     * true if the object is current relative ti its source
     */
    public boolean isCurrent();

    /**
     * update the object with its source if  needed
     * @return current version of the object
     */
    public Object update();
}
