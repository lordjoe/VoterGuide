package com.lordjoe.lang;

/**
 * com.lordjoe.lang.ObjectChangeListener
 *   more general than a property change this is
 *   send by an object which has or may have changed
 * @author Steve Lewis
 * @date Feb 13, 2008
 */
public interface ObjectChangeEventGenerator {
    public static ObjectChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectChangeListener.class;

    /**
      * add a change listener
      *
      * @param added non-null change listener
      */
     public void addObjectChangeListener(ObjectChangeListener added);

     /**
      * remove a change listener
      *
      * @param removed non-null change listener
      */
     public void removeObjectChangeListener(ObjectChangeListener removed);
 }