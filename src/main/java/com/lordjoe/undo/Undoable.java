package com.lordjoe.undo;

/**
 * com.lordjoe.undo.Undoable
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public interface Undoable
{
    public static Undoable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = Undoable.class;

    /**
     * get the action type
     * @return non-null type
     */
    public UndoableActionType getType();

    /**
     * undo the action
     */
    public void undo();

    /**
     * redo the action
     */
    public void redo();

}
