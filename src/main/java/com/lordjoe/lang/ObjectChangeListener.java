package com.lordjoe.lang;

/**
 * com.lordjoe.lang.ObjectChangeListener
 *   more general than a property change this is
 *   send by an object which has or may have changed
 * @author Steve Lewis
 * @date Feb 13, 2008
 */
public interface ObjectChangeListener {
    public static ObjectChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectChangeListener.class;

    /**
     * what to do when a   subecribed object changes
     * @param changed   non-null object which is changed
     * @param addedData  possibly  null data about the change
     *    must be agreed between sender and recipient
     */
    public void onObjectChange(Object changed,Object addedData) ;
}
