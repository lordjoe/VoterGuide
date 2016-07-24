package com.lordjoe.undo;

import com.lordjoe.lib.xml.*;

/**
 * com.lordjoe.undo.UndoablePropertyChange
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public class UndoablePropertyChange<T> extends AbstractUndoable
{
    public static UndoablePropertyChange[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UndoablePropertyChange.class;

    private final Object m_Target;
    private final String m_PropertyName;
    private final T m_OldValue;
    private final T m_NewValue;

    public UndoablePropertyChange(Object target,String propName,T oldValue,T newValue )
    {
        super(UndoableActionType.ChangeValue);
        m_Target = target;
        m_PropertyName = propName;
        m_OldValue = oldValue;
        m_NewValue = newValue;
    }

    public Object getTarget()
    {
        return m_Target;
    }

    public String getPropertyName()
    {
        return m_PropertyName;
    }

    public T getOldValue()
    {
        return m_OldValue;
    }

    public T getNewValue()
    {
        return m_NewValue;
    }

    /**
     * undo the action
     */
    public void undo()
    {
        Object target = getTarget();
        ClassAnalyzer.setProperty(target,getPropertyName(),getOldValue());
        notifyUndoChangeListeners(target);
    }

    /**
     * redo the action
     */
    public void redo()
    {
        Object target = getTarget();
        ClassAnalyzer.setProperty(target,getPropertyName(),getNewValue());
        notifyUndoChangeListeners(target);

    }
}
