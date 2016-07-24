package com.lordjoe.undo;

/**
 * com.lordjoe.undo.UndoableActionType
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public enum UndoableActionType
{
    AddChild,
    RemoveChild,
    RemoveState,
    ChangeValue;

    public static UndoableActionType[]EMPTY_ARRAY={};
    public static Class THIS_CLASS = UndoableActionType.class;
}
