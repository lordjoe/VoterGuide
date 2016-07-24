package com.lordjoe.undo;

/**
 * com.lordjoe.undo.UndoObjectChangeListener
 *
 * @author Steve Lewis
 * @date Nov 13, 2008
 */
public interface UndoObjectChangeListener
{
    public static UndoObjectChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UndoObjectChangeListener.class;

    /**
     * notify the target of a state change made by the
     * Undo manager
     * @param target non-null target
     */
    public void onUndoStateChange(Object target);
}
