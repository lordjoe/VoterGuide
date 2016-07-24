package com.lordjoe.ui.general;

import javax.swing.*;

/**
 * com.lordjoe.ui.general.ObjectAction
 *
 * @author Steve Lewis
 * @date Jul 21, 2008
 */
public abstract class ObjectAction<T>  extends AbstractAction
{
    public static ObjectAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectAction.class;

    private T m_Subject;
    public ObjectAction(T subj)
    {
        m_Subject = subj;
    }

    public T getSubject()
    {
        return m_Subject;
    }

    public void setSubject(T value)
    {
        m_Subject = value;
    }
}
