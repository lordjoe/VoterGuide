package com.lordjoe.utilities;

import java.util.*;

/**
 * Implemented by an object wanting to do cleanup
 * on an object going out of scope
 * @author Steve Lewis
 * @see WatchedMap
 */
public interface IObjectDestructor {
    /**
     * this is called when target is about to go out
     * of scope - i.e. timed out by a WatchedMap
     * It allows cleanup well in advance of garbage collection
     * @param Target on-null Object going out of scope
     * @see com.lordjoe.utilities.WatchedMap
     */
    public void onObjectClose(Object Target);
}
