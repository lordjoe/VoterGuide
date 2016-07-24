package com.lordjoe.ui.general;

import com.toedter.calendar.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.awt.*;
import java.beans.*;

/**
 * com.lordjoe.ui.general.JDateValueEditor
 *
 * @author Steve Lewis
 * @date Dec 27, 2007
 */
public class JDateValueEditor extends JDateChooser  implements PropertyChangeListener
{
    public static JDateValueEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JDateValueEditor.class;

    private final List<ValidityChangeListener> m_ValidityChangeListeners;

    public JDateValueEditor() {
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        if(dateEditor instanceof JComponent) {
            ((JComponent)dateEditor).addPropertyChangeListener("text", this);
            ((JComponent)dateEditor).addPropertyChangeListener("date", this);
        }
    }


    /**
     * This method gets called when a bound property is changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *   	and the property that has changed.
     */

    public void propertyChange(PropertyChangeEvent evt)
    {
        super.propertyChange(evt);
        if("date".equals(evt.getPropertyName())) {
           notifyValidityChangeListeners(getDate() != null);
        }
    }

    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addValidityChangeListener(ValidityChangeListener added)
    {
        if (!m_ValidityChangeListeners.contains(added))
            m_ValidityChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeValidityChangeListener(ValidityChangeListener removed)
    {
        while (m_ValidityChangeListeners.contains(removed))
            m_ValidityChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyValidityChangeListeners(boolean valid)
    {
        if (m_ValidityChangeListeners == null) // happens durinf construction
            return;
        if (m_ValidityChangeListeners.isEmpty())
            return;
        for (ValidityChangeListener listener : m_ValidityChangeListeners) {
            listener.onValidityChange(this, valid);
        }
    }

    public void setDate(Date date) {
        super.setDate(date);
        notifyValidityChangeListeners(true);
    }

    public void setDateFormat(String s)
    {
        setDateFormatString(s);
    }

    public void setTime(Date d)
    {
        setDate(d);
    }
    
    public Date getTime( )
    {
        return getDate( );
    }


    public void setBackground(Color bg) {
        super.setBackground(bg);
        if(dateEditor instanceof JComponent)
            ((JComponent)dateEditor).setBackground(bg);

    }
}
