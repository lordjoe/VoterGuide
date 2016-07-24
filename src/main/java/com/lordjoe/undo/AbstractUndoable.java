package com.lordjoe.undo;


/**
 * com.lordjoe.undo.AbstractUndoable
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public abstract class AbstractUndoable implements Undoable
{
    public static AbstractUndoable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractUndoable.class;

    private final UndoableActionType m_Type;

    public AbstractUndoable(UndoableActionType pType)
    {
        m_Type = pType;
    }

    protected void notifyUndoChangeListeners(Object  target)
    {
        if(target instanceof UndoObjectChangeListener)  {
            ((UndoObjectChangeListener)target).onUndoStateChange(target);
        }

    }


    public UndoableActionType getType()
    {
        return m_Type;
    }
}
