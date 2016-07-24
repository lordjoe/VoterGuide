package com.lordjoe.ui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.lordjoe.ui.JObjectTextLabel
 *
 * @author Steve Lewis
 * @date Oct 16, 2007
 */
public class JObjectTextLabel<T> extends JTextFieldLabel {
    public static JObjectTextLabel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JObjectTextLabel.class;

    private T m_Object;
    private final List<ActionListener> m_ActionListeners;


    public JObjectTextLabel() {
        super();
        m_ActionListeners = new CopyOnWriteArrayList<ActionListener>();
    }


    public JObjectTextLabel(T s) {
        super();
        m_ActionListeners = new CopyOnWriteArrayList<ActionListener>();
        setObject(s);
    }




    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addActionListener(ActionListener added) {
        if (!m_ActionListeners.contains(added))
            m_ActionListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeActionListener(ActionListener removed) {
        while (m_ActionListeners.contains(removed))
            m_ActionListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyActionListeners() {
        if (m_ActionListeners.isEmpty())
            return;
        ActionEvent evt = new ActionEvent(this,0,null);
        for (ActionListener listener : m_ActionListeners) {
            listener.actionPerformed(evt);
        }
    }
    public T getObject() {
        return m_Object;
    }

    public void setObject(T pObject) {
        if (m_Object == pObject)
            return;
        m_Object = pObject;
        final String text = pObject == null ? " " : pObject.toString();
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        setText(text);
                        notifyActionListeners();
                    }
                }
        );
    }
}
