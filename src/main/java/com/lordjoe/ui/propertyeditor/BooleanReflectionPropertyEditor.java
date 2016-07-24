package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.ui.general.*;
import com.lordjoe.lang.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.propertyeditor.DoubleReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class BooleanReflectionPropertyEditor extends  AbstractReflectionPropertyEditor<Boolean>
    implements ItemListener
{
    public static BooleanReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = BooleanReflectionPropertyEditor.class;

    private JCheckBox m_TextField;
    public BooleanReflectionPropertyEditor(IPropertyWrapper<Boolean> pw)
    {
        super(pw);
        m_TextField = new JCheckBox( );
        m_TextField.addFocusListener(this);
        m_TextField.addItemListener(this);
        if(pw.isReadOnly())
             m_TextField.setEnabled(false);
        adjustFieldProperties(m_TextField);
   }


    public JComponent getMainDisplay()
    {
        return m_TextField;
    }


    public JComponent[] getStylizableComponents()
    {
        List<JComponent> holder = new ArrayList<JComponent>();
        holder.add(getLabel());
        holder.add(m_TextField);
        JComponent[] ret = new JComponent[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    public void itemStateChanged(ItemEvent e) {
         adjustFieldProperties(m_TextField);
         Boolean value = getValue();
         notifyEditedPropertyChangeListeners(null,value);

     }

     protected void onBlur() {
     }

    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public Boolean getValue()
    {
        return m_TextField.isSelected();
    }

    public void setValue(Boolean value)
    {
        Boolean oldValue = getValue();
        if(ObjectOps.equalsNullSafe(oldValue,value))
            return;
        m_TextField.setSelected(value);
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue,value);

    }

}