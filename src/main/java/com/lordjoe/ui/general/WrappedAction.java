package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.*;


/**
 * com.lordjoe.ui.general.WrappedAction
 * Class to wrap an Action allowing a button's action to be
 * changed
 *
 * @author slewis
 * @date Mar 9, 2005
 */
public class WrappedAction extends AbstractAction implements PropertyChangeListener
{
    public static final Class THIS_CLASS = WrappedAction.class;
    public static final WrappedAction EMPTY_ARRAY[] = {};
    public static final String REAL_NAME = "RealName"; // name before internationalization

    public static AbstractAction getNullAction(String name) {
        return new NullAction(name);
    }

    public static WrappedAction getWrappedNullAction(String name) {
        return new WrappedAction(getNullAction(name));
    }

    private String m_Name;
    private AbstractAction m_RealAction;

    public WrappedAction() {
    }


    /**
     * Gets the <code>Object</code> associated with the specified key.
     *
     * @param key a string containing the specified <code>key</code>
     * @return the binding <code>Object</code> stored with this key; if there
     *         are no keys, it will return <code>null</code>
     * @see javax.swing.Action#getValue
     */
    public Object getValue(String key)
    {
        if(getRealAction() != null)
            return getRealAction().getValue(key);
        return super.getValue(key);

    }

    public WrappedAction(String s) {
        this();
        m_Name = s;
    }

    public WrappedAction(AbstractAction act) {
        this();
        setRealAction(act);
    }

    public Action getRealAction() {
        return m_RealAction;
    }

    public void setRealAction(AbstractAction realAction) {
         String newName = null;
        if (realAction != null) {
             newName = (String) realAction.getValue(Action.NAME);
        }
        if (realAction.equals(m_RealAction))
            return;
        Action oldAction = m_RealAction;
        Object oldName = null;
        Object oldIcon = null;
        PropertyChangeListener[] listeners = getPropertyChangeListeners();
        boolean oldEnabled = false;
        if (m_RealAction != null) {
            oldName = m_RealAction.getValue(Action.NAME);
            oldIcon = m_RealAction.getValue(Action.SMALL_ICON);
            oldEnabled = m_RealAction.isEnabled();
            m_RealAction.removePropertyChangeListener(this);
            for (int i = 0; i < listeners.length; i++) {
                PropertyChangeListener listener = listeners[i];
                m_RealAction.removePropertyChangeListener(listener);
            }
        }
        if (m_RealAction != null)
            m_RealAction.removePropertyChangeListener(this);
        m_RealAction = realAction;
        boolean b = realAction.isEnabled();
        m_RealAction.addPropertyChangeListener(this);
        for (int i = 0; i < listeners.length; i++) {
            PropertyChangeListener listener = listeners[i];
            m_RealAction.addPropertyChangeListener(listener);
        }
        setEnabled(b);
        Object value = m_RealAction.getValue(Action.NAME);
        putValue(Action.NAME, value);
        value = m_RealAction.getValue(Action.SMALL_ICON);
        putValue(Action.SMALL_ICON,value);
        value = m_RealAction.getValue(REAL_NAME);
        putValue(REAL_NAME,value);
        value = m_RealAction.getValue(Action.SHORT_DESCRIPTION);
        putValue(Action.SHORT_DESCRIPTION,value);

        if (newName != null)
            m_Name = newName.toString();
        firePropertyChange("action",
                oldAction,
                m_RealAction);
        firePropertyChange(Action.NAME, oldName, newName);
        firePropertyChange("enabled",
                Boolean.valueOf(oldEnabled),
                Boolean.valueOf(b));
        firePropertyChange(Action.SMALL_ICON,
                oldIcon,
                m_RealAction.getValue(Action.SMALL_ICON));
    }

    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     * <p/>
     * A <code>PropertyChangeEvent</code> will get fired in response to setting
     * a bound property, e.g. <code>setFont</code>, <code>setBackground</code>,
     * or <code>setForeground</code>.
     * Note that if the current component is inheriting its foreground,
     * background, or font from its container, then no event will be
     * fired in response to a change in the inherited property.
     *
     * @param listener The <code>PropertyChangeListener</code> to be added
     * @see javax.swing.Action#addPropertyChangeListener
     */
    @Override
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener)
    {
        super.addPropertyChangeListener(listener);
        if(m_RealAction != null)
            m_RealAction.addPropertyChangeListener(listener);

    }

    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * This removes a <code>PropertyChangeListener</code> that was registered
     * for all properties.
     *
     * @param listener the <code>PropertyChangeListener</code> to be removed
     * @see javax.swing.Action#removePropertyChangeListener
     */
    @Override
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener)
    {
        super.removePropertyChangeListener(listener);
        if(m_RealAction != null)
            m_RealAction.removePropertyChangeListener(listener);

    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == m_RealAction)  {
            String propertyName = evt.getPropertyName();
            if("enabled".equals(propertyName)) {
                 boolean b = (Boolean)evt.getNewValue();
                if(b != isEnabled())
                    setEnabled(b);
                firePropertyChange(propertyName, evt.getOldValue(),
                        evt.getNewValue());
                return;
            }
            if(Action.SMALL_ICON.equals(propertyName)) {
                 Object b =  evt.getNewValue();
                putValue(Action.SMALL_ICON,b);
                return;
            }
            if(Action.SHORT_DESCRIPTION.equals(propertyName)) {
                 Object b =  evt.getNewValue();
                putValue(Action.SHORT_DESCRIPTION,b);
                return;
            }
            if(Action.LONG_DESCRIPTION.equals(propertyName)) {
                 Object b =  evt.getNewValue();
                putValue(Action.LONG_DESCRIPTION,b);
                return;
            }
            if(Action.NAME.equals(propertyName)) {
                 Object b =  evt.getNewValue();
                putValue(Action.NAME,b);
                return;
            }
            if(REAL_NAME.equals(propertyName)) {
                 Object b =  evt.getNewValue();
                putValue(REAL_NAME,b);
                return;
            }
            firePropertyChange(propertyName, evt.getOldValue(),
                    evt.getNewValue());
        }
        else {
            return;
        }
    }


    /**
     * Sets the enabled state of the <code>Action</code>.  When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     * If the value has changed, a <code>PropertyChangeEvent</code> is sent
     * to listeners.
     *
     * @param b true to enable this <code>Action</code>, false to disable it
     */
    public void setEnabled(boolean b) {

        if (m_RealAction == null) {
            if (b)
                throw new IllegalStateException("no enclosed action");
            return;
        }
        boolean oldValue = m_RealAction.isEnabled();
        if (oldValue != b)
            m_RealAction.setEnabled(b);
        super.setEnabled(b);
        firePropertyChange("enabled",
               Boolean.valueOf(oldValue), Boolean.valueOf(b));
    }

    /**
     * Returns the enabled state of the <code>Action</code>. When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     *
     * @return true if this <code>Action</code> is enabled
     */
    public boolean isEnabled() {
        if (m_RealAction == null)
            return false;
        return m_RealAction.isEnabled();
    }

//
//    /**
//     * Supports reporting bound property changes.  This method can be called
//     * when a bound property has changed and it will send the appropriate
//     * <code>PropertyChangeEvent</code> to any registered
//     * <code>PropertyChangeListeners</code>.
//     */
//    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
//        if (changeSupport == null ||
//                (oldValue != null && newValue != null && oldValue.equals(newValue))) {
//            return;
//        }
//        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
//    }
//
//
//    /**
//     * Adds a <code>PropertyChangeListener</code> to the listener list.
//     * The listener is registered for all properties.
//     * <p/>
//     * A <code>PropertyChangeEvent</code> will get fired in response to setting
//     * a bound property, e.g. <code>setFont</code>, <code>setBackground</code>,
//     * or <code>setForeground</code>.
//     * Note that if the current component is inheriting its foreground,
//     * background, or font from its container, then no event will be
//     * fired in response to a change in the inherited property.
//     *
//     * @param listener The <code>PropertyChangeListener</code> to be added
//     * @see Action#addPropertyChangeListener
//     */
//    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
//        if (changeSupport == null) {
//            changeSupport = new SwingPropertyChangeSupport(this);
//        }
//        changeSupport.addPropertyChangeListener(listener);
//        if (m_RealAction != null && m_RealAction != this)
//            m_RealAction.addPropertyChangeListener(listener);
//    }
//
//
//    /**
//     * Removes a <code>PropertyChangeListener</code> from the listener list.
//     * This removes a <code>PropertyChangeListener</code> that was registered
//     * for all properties.
//     *
//     * @param listener the <code>PropertyChangeListener</code> to be removed
//     * @see Action#removePropertyChangeListener
//     */
//    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
//        if (changeSupport == null) {
//            return;
//        }
//        changeSupport.removePropertyChangeListener(listener);
//        if (m_RealAction != null)
//            m_RealAction.removePropertyChangeListener(listener);
//    }
//
//
//    /**
//     * Returns an array of all the <code>PropertyChangeListener</code>s added
//     * to this AbstractAction with addPropertyChangeListener().
//     *
//     * @return all of the <code>PropertyChangeListener</code>s added or an empty
//     *         array if no listeners have been added
//     * @since 1.4
//     */
//    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
//        if (changeSupport == null) {
//            return new PropertyChangeListener[0];
//        }
//        return changeSupport.getPropertyChangeListeners();
//    }

    /**
     * Supports reporting bound property changes.  This method can be called
     * when a bound property has changed and it will send the appropriate
     * <code>PropertyChangeEvent</code> to any registered
     * <code>PropertyChangeListeners</code>.
     */
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
    {
        PropertyChangeListener[] changeListeners = getPropertyChangeListeners();
        for (int i = 0; i < changeListeners.length; i++) {
            PropertyChangeListener changeListener = changeListeners[i];
             changeListener = null;
        }
        super.firePropertyChange(propertyName, oldValue, newValue);

    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        if (m_RealAction != null)
            m_RealAction.actionPerformed(e);
    }

    /**
     * Action that does nothing and is disabled
     */
    private static class NullAction extends AbstractAction {
        public NullAction(String name) {
            putValue(Action.NAME, name);
        }

        public boolean isEnabled() {
            return false;
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            // do nothing
        }
    }

}
