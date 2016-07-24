package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.CheckBoxCollection
 *
 * @author Steve Lewis
 * @date Mar 1, 2005
 */
public class RadioButtonCollection extends JPanel implements ItemSelectable, ActionListener
{
    public static final Class THIS_CLASS = RadioButtonCollection.class;
    public static final RadioButtonCollection[] EMPTY_ARRAY = {};

    protected static String[] buildObjectNames(Object[] items)
    {
        String[] ret = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            Object item = items[i];
            if (item instanceof INamedObject) {
                ret[i] = ((INamedObject) item).getName();
            }
            if (ret[i] == null)
                ret[i] = items[i].toString();
        }
        return ret;
    }

    private final Object[] m_Items;
    private final String[] m_ItemNames;
    private final JRadioButton[] m_RadioButtons;
    private final ButtonGroup m_Group;
    private final List<ItemListener> m_Listeners;

    public RadioButtonCollection(Object[] items)
    {
        this(items, buildObjectNames(items));
    }

    public RadioButtonCollection(Object[] items, String[] itemNames)
    {
        setLayout(new SpringLayout());
        m_Listeners = new ArrayList<ItemListener>();
        m_Items = items;
        m_ItemNames = itemNames;
        m_Group = new ButtonGroup();
        m_RadioButtons = new JRadioButton[items.length];

        for (int i = 0; i < m_Items.length; i++) {
            m_RadioButtons[i] = buildRadioButton(m_Items[i], m_ItemNames[i]);
            m_RadioButtons[i].setOpaque(false);
            m_RadioButtons[i].setForeground(Color.white);
            m_Group.add(m_RadioButtons[i]);
            m_RadioButtons[i].addActionListener(this);
        }

        SpringUtilities.makeCompactGrid(this,
                1, getComponentCount(),
                0, 0,
                5, 5);
        setOpaque(false);

    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        Object item = getSelectedItem();
        notifyItemListeners();
    }

    /**
     * Returns the selected items or <code>null</code> if no
     * items are selected.
     */
    public Object[] getSelectedObjects()
    {
        Object selection = getSelectedItem();
        if (selection == null)
            return Util.EMPTY_OBJECT_ARRAY;
        Object[] ret = {selection};
        return ret;
    }

    public Object getSelectedItem()
    {
        List holder = new ArrayList();
        for (int i = 0; i < m_RadioButtons.length; i++) {
            if (m_RadioButtons[i].isSelected())
                return m_Items[i];
        }
        return (null);
    }

    public void setSelectedIindex(int index)
    {
        for (int i = 0; i < m_RadioButtons.length; i++) {
            if (i == index)
                m_RadioButtons[i].setSelected(true);
            else
                m_RadioButtons[i].setSelected(false);
        }
    }


    public Object[] getItems()
    {
        return m_Items;
    }

    public JRadioButton[] getRadioButtons()
    {
        return m_RadioButtons;
    }

    public String[] getItemNames()
    {
        return m_ItemNames;
    }

    public JRadioButton buildRadioButton(Object item, String name)
    {
        JRadioButton cb = new JRadioButton(name);
        add(cb);
        return cb;
    }

    protected void notifyItemListeners()
    {
        if (m_Listeners.isEmpty())
            return;
        ItemListener[] listeners = null;
        synchronized (m_Listeners) {
            listeners = new ItemListener[m_Listeners.size()];
            m_Listeners.toArray(listeners);
        }
        ItemEvent evt = new ItemEvent(this, 0, getSelectedItem(), ItemEvent.SELECTED);
        for (int i = 0; i < listeners.length; i++) {
            ItemListener listener = listeners[i];
            listener.itemStateChanged(evt);
        }

    }

    public void addItemListener(ItemListener added)
    {
        synchronized (m_Listeners) {
            m_Listeners.add(added);
        }
    }

    public void removeItemListener(ItemListener removed)
    {
        synchronized (m_Listeners) {
            m_Listeners.remove(removed);
        }

    }
}

