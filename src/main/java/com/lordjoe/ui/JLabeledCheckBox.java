package com.lordjoe.ui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.JLabeledCheckBox
 *  simple NDCnation of a label and a checkbox - acts like a checkbox
 * @author Steve Lewis
 * @date Feb 10, 2006
 */
public class JLabeledCheckBox extends JPanel implements ItemSelectable,
        ItemListener,ActionListener
{
    public final static JLabeledCheckBox[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = JLabeledCheckBox.class;

    private final JLabel m_Label;
    private final JCheckBox m_Checkbox;
    private boolean m_CheckboxLeftSide;
    /**
     * A list of event listeners for this component.
     */
    private final EventListenerList m_ItemListenerList;
    private final EventListenerList m_ActionListenerList;

    public JLabeledCheckBox()
    {
        this("");
    }

    public boolean isCheckboxLeftSide()
    {
        return m_CheckboxLeftSide;
    }

    public void setCheckboxLeftSide(boolean pCheckboxLeftSide)
    {
        m_CheckboxLeftSide = pCheckboxLeftSide;
    }

    public void setFont(Font f)
    {
        super.setFont(f);
        if(m_Label != null)
             m_Label.setFont(f);

    }

    public JLabeledCheckBox(String s)
    {
        m_Label = buildLabel(s);
        m_Checkbox = new JCheckBox();
        m_Checkbox.addItemListener(this);
        m_Checkbox.addActionListener(this);
         m_Label.setLabelFor(m_Checkbox);
        m_ItemListenerList = new EventListenerList();
        m_ActionListenerList = new EventListenerList();
        setLayout(null);
        add(m_Label);
        add(m_Checkbox);
    }


    /**
     * Causes this container to lay out its components.  Most programs
     * should not call this method directly, but should invoke
     * the <code>validate</code> method instead.
     *
     * @see java.awt.LayoutManager#layoutContainer
     * @see #setLayout
     * @see #validate
     * @since JDK1.1
     */
    public void doLayout()
    {
        Dimension size = getSize();
        Insets insets = getInsets();
        Dimension cbSize = m_Checkbox.getPreferredSize();
        Dimension lbSize = m_Label.getPreferredSize();
        int commonHeight = Math.max(lbSize.height,cbSize.height);
        int cbVStart =  insets.top ;
        int lbVStart =  insets.top ;
        if(commonHeight > cbSize.height)
            cbVStart =  insets.top + (commonHeight - cbSize.height) / 2;
        if(commonHeight > lbSize.height)
            lbVStart =   insets.top + (commonHeight - lbSize.height) / 2;

        if(isCheckboxLeftSide())  {
            int cbHStart = insets.left;
            int cbEnd = cbHStart + cbSize.width;
            m_Checkbox.setBounds(cbHStart,cbVStart,cbSize.width,cbSize.height);
            m_Label.setBounds(cbEnd,lbVStart,lbSize.width,lbSize.height);
        }
        else {
            int cbHStart = size.width - cbSize.width - insets.right;
            m_Checkbox.setBounds(cbHStart,cbVStart,size.width,cbSize.height);
            int llblx = cbHStart - lbSize.width;
            m_Label.setBounds(llblx,lbVStart,lbSize.width,lbSize.height);
         }

    }

    protected JLabel buildLabel(String s)
    {
       return new JLabel(s);
    }

    public JLabel getLabel()
    {
        return m_Label;
    }

    public JCheckBox getCheckbox()
    {
        return m_Checkbox;
    }


    public void setOpaque(boolean doit)
    {
        super.setOpaque(doit);
        if(m_Label != null)
            m_Label.setOpaque(doit);
        if(m_Checkbox != null)
            m_Checkbox.setOpaque(doit);
    }


    /**
     * If the maximum size has been set to a non-<code>null</code> value
     * just returns it.  If the UI delegate's <code>getMaximumSize</code>
     * method returns a non-<code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>maximumSize</code> property
     * @see #setMaximumSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMaximumSize()
    {
        Dimension cb = m_Checkbox.getMaximumSize();
        Dimension lb = m_Label.getMaximumSize();
        return new Dimension(cb.width + lb.width,cb.height + lb.height);
    }

    /**
     * If the minimum size has been set to a non-<code>null</code> value
     * just returns it.  If the UI delegate's <code>getMinimumSize</code>
     * method returns a non-<code>null</code> value then return that; otherwise
     * defer to the component's layout manager.
     *
     * @return the value of the <code>minimumSize</code> property
     * @see #setMinimumSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMinimumSize()
    {
        Dimension cb = m_Checkbox.getMinimumSize();
        Dimension lb = m_Label.getMinimumSize();
        return new Dimension(cb.width + lb.width,cb.height + lb.height);

    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getPreferredSize()
    {
        Insets insets = getInsets();
        Dimension cb = m_Checkbox.getPreferredSize();
        Dimension lb = m_Label.getPreferredSize();
        return new Dimension(insets.left + insets.right + cb.width + lb.width,
                insets.top + insets.bottom + cb.height + lb.height);
    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent oldEvent)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = m_ActionListenerList.getListenerList();
        if (listeners.length == 0)
            return;
        ActionEvent e = new ActionEvent(this,oldEvent.getID(),oldEvent.getActionCommand(),oldEvent.getModifiers());
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = 0; i < listeners.length; i++) {
            Object listener = listeners[i];
            if(listener instanceof ActionListener)
                ((ActionListener) listener).actionPerformed(e);
        }
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent oldEvent)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = m_ItemListenerList.getListenerList();
        if (listeners.length == 0)
            return;
        ItemEvent e = new ItemEvent(this,
                ItemEvent.ITEM_STATE_CHANGED,
                this,
                oldEvent.getStateChange());
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = 0; i < listeners.length; i++) {
            Object listener = listeners[i];
            if(listener instanceof ItemListener)
              ((ItemListener) listener).itemStateChanged(e);
        }
    }

    /**
     * Adds a listener to receive item events when the state of an item is
     * changed by the user. Item events are not sent when an item's
     * state is set programmatically.  If <code>l</code> is
     * <code>null</code>, no exception is thrown and no action is performed.
     *
     * @param l the listener to receive events
     * @see java.awt.event.ItemEvent
     */
    public void addItemListener(ItemListener l)
    {
        m_ItemListenerList.add(ItemListener.class,l);
    }

    /**
     * Removes an item listener.
     * If <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param l the listener being removed
     * @see java.awt.event.ItemEvent
     */
    public void removeItemListener(ItemListener l)
    {
        m_ItemListenerList.remove(ItemListener.class,l);
    }

    /**
     * Adds a listener to receive item events when the state of an item is
     * changed by the user. Item events are not sent when an item's
     * state is set programmatically.  If <code>l</code> is
     * <code>null</code>, no exception is thrown and no action is performed.
     *
     * @param l the listener to receive events
     * @see java.awt.event.ItemEvent
     */
    public void addActionListener(ActionListener l)
    {
        m_ActionListenerList.add(ActionListener.class,l);
    }

    /**
     * Removes an Action listener.
     * If <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param l the listener being removed
     * @see java.awt.event.ActionEvent
     */
    public void removeActionListener(ActionListener l)
    {
        m_ActionListenerList.remove(ActionListener.class,l);
    }

    public String getText()
    {
        return m_Label.getText();
    }

    public boolean isSelected()
    {
        return m_Checkbox.isSelected();
    }

    public boolean isEnabled()
    {
        return m_Checkbox.isEnabled();
    }
    public void setEnabled(boolean doit)
    {
        m_Checkbox.setEnabled(doit);
    }


        /**
      * Sets the state of the button. Note that this method does not
      * trigger an <code>actionEvent</code>.
      * Call <code>doClick</code> to perform a programatic action change.
      *
      * @param b  true if the button is selected, otherwise false
      */
     public void setSelected(boolean b) {
          m_Checkbox.setSelected(b);
     }

     /**
      * Programmatically perform a "click". This does the same
      * thing as if the user had pressed and released the button.
      */
     public void doClick() {
         m_Checkbox.doClick();
     }

     /**
      * Programmatically perform a "click". This does the same
      * thing as if the user had pressed and released the button.
      * The button stays visually "pressed" for <code>pressTime</code>
      *  milliseconds.
      *
      * @param pressTime the time to "hold down" the button, in milliseconds
      */
     public void doClick(int pressTime) {
         m_Checkbox.doClick(pressTime);
     }

    /**
     * Sets the action command for this button.
     * @param actionCommand the action command for this button
     */
    public void setActionCommand(String actionCommand) {
        m_Checkbox.setActionCommand(actionCommand);
    }

    /**
     * Returns the action command for this button.
     * @return the action command for this button
     */
    public String getActionCommand() {
       return  m_Checkbox.getActionCommand();
     }

    public void setAction(Action a) {
      m_Checkbox.setAction(a);
    }

    public Action getAction() {
      return m_Checkbox.getAction();
    }
    /**
     * Returns the selected items or <code>null</code> if no
     * items are selected.
     */
    public Object[] getSelectedObjects()
    {
        if (isSelected() == false) {
            return null;
        }
        Object[] selectedObjects = new Object[1];
        selectedObjects[0] = getText();
        return selectedObjects;
    }


}
