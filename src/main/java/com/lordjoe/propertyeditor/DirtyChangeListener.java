package com.lordjoe.propertyeditor;

/**
 * com.lordjoe.propertyeditor.DirtyChangeListener
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public interface DirtyChangeListener {
    public static DirtyChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DirtyChangeListener.class;

    /**
     * respond to a change to the dirty state of an object
     * @param target
     * @param dirty
     */
    public void onDirtyChange(Object target, boolean dirty);
}
