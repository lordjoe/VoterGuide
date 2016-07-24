package com.lordjoe.ui.general;


import javax.swing.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.general.JToolBarButton
 *
 * @author Steve Lewis
 * @date Jan 2, 2008
 */
public class JToolBarButton extends JButton {
    //implements PropertyChangeListener {
    public static JToolBarButton[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JToolBarButton.class;

    private Action m_Action;
    private String m_Name;
    public JToolBarButton(Action act) {
        super(act);
        m_Action = act;
        m_Name = (String) m_Action.getValue(Action.NAME);
    }

    public void setIcon(Icon icon) {
        setText(null);
        super.setIcon(icon);

    }

    public float getAlignmentY() {
        return 0.5f;
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>event</code>
     * parameter.
     *
     * @param event the <code>ActionEvent</code> object
     * @see javax.swing.event.EventListenerList
     */
    @Override
    protected void fireActionPerformed(ActionEvent event)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            Object listener = listeners[i];
            listener = null;
        }
        super.fireActionPerformed(event);

    }

    /**
     * Adds an <code>ActionListener</code> to the button.
     *
     * @param l the <code>ActionListener</code> to be added
     */
    @Override
    public void addActionListener(ActionListener l)
    {
        listenerList.remove(ActionListener.class,l);
        super.addActionListener(l);

    }
}
