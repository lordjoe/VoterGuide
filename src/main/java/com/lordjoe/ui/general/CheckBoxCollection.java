package com.lordjoe.ui.general;

import javax.swing.*;
import java.util.*;

/**
 * com.lordjoe.ui.general.CheckBoxCollection
 *
 * @author Steve Lewis
 * @date Mar 1, 2005
 */
public class CheckBoxCollection extends JPanel
{
    public static final Class THIS_CLASS = CheckBoxCollection.class;
    public static final CheckBoxCollection[] EMPTY_ARRAY = {};

    protected static String[] buildObjectNames(Object[] items)
    {
        String[] ret = new String[items.length] ;
        for(int i = 0; i < items.length; i++)
            ret[i] = items[i].toString();
        return ret;
    }

    private final Object[] m_Items;
    private final String[] m_ItemNames;
    private final JCheckBox[] m_CheckBoxes;
    public CheckBoxCollection(Object[] items)
    {
          this(items,buildObjectNames(items));
    }
    public CheckBoxCollection(Object[] items,String[] itemNames)
    {
        setLayout(new SpringLayout());
         m_Items = items;
        m_ItemNames = itemNames;
        m_CheckBoxes = new JCheckBox[items.length];

        for(int i = 0; i < m_Items.length; i++)
             m_CheckBoxes[i] = buildCheckBox(m_Items[i],m_ItemNames[i]);

        SpringUtilities.makeCompactGrid(this,
                1,getComponentCount(),
                0,0,
                5,5);

    }

    public Object[] getSelectedItems()
    {
        List holder = new ArrayList();
         for(int i = 0; i < m_CheckBoxes.length; i++) {
            if(m_CheckBoxes[i].isSelected())
                holder.add(m_Items[i]);
        }
        Object[] ret = new Object[holder.size()];
        holder.toArray(ret);
        return(ret);
    }

    public Object[] getItems()
    {
        return m_Items;
    }

    public JCheckBox[] getCheckBoxes()
    {
        return m_CheckBoxes;
    }

    public String[] getItemNames()
    {
        return m_ItemNames;
    }

    public JCheckBox buildCheckBox(Object item,String name)
    {
        JLabel label = new JLabel(name);
        JCheckBox cb = new JCheckBox();
        label.setLabelFor(cb);
        add(label);
        add(cb);
         return cb;
    }
}

