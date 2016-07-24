/**
 * JActionButton - Swing Style
 */
package com.lordjoe.utilities;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.*;


public class JActionButton extends JButton implements PropertyChangeListener
{
    private Action m_Action;
    
    public JActionButton() {
        setEnabled(false); // no action
    }
    
    public JActionButton(Icon icon) {
        super(icon);
        setEnabled(false); // no action
    }
    public JActionButton(String text) {
        super(text);
        setEnabled(false); // no action
    }
    
    public JActionButton(String text,Action act) {
        super(text);
        setAction(act); 
    }
    
    public JActionButton(String text,Icon icon) {
        super(text,icon);
        setEnabled(false); // no action
    }
    
    
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("enabled")) {
            setEnabled(((Boolean)evt.getNewValue()).booleanValue());
        }
    }
    
    public void setAction(Action doThis) {
        if(m_Action != null) {
            m_Action.removePropertyChangeListener(this);
            removeActionListener(m_Action);
        }
        m_Action = doThis;
        if(doThis == null) {
            setEnabled(false);
            return;
        }
        m_Action.addPropertyChangeListener(this);
        addActionListener(m_Action);
        setEnabled(m_Action.isEnabled());
    }

// end class JActionButton
}



