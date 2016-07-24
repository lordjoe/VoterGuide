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
public class IntegerReflectionPropertyEditor extends  AbstractReflectionPropertyEditor<Integer> 
    implements ActionListener
{
    public static IntegerReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IntegerReflectionPropertyEditor.class;

    private JIntegerTextField m_TextField;
    public IntegerReflectionPropertyEditor(IPropertyWrapper<Integer> pw)
    {
        super(pw);
        m_TextField = new JIntegerTextField(30);
        m_TextField.addFocusListener(this);
        m_TextField.addActionListener(this);
         adjustFieldProperties(m_TextField);
        if(pw.isReadOnly())
              m_TextField.setEnabled(false);
        m_TextField.setRequired(pw.isRequired());
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

    protected void onBlur() {
        adjustFieldProperties(m_TextField);
        Integer value = getValue();
        notifyEditedPropertyChangeListeners(null,value);
    }


    public void actionPerformed(ActionEvent e) {
        adjustFieldProperties(m_TextField);
        Integer value = getValue();
        notifyEditedPropertyChangeListeners(null,value);
    }


    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public Integer getValue()
    {
        return m_TextField.getIntegerValue();
    }

    public void setValue(Integer value)
    {
        Integer oldValue = getValue();
        if(ObjectOps.equalsNullSafe(oldValue,value))
            return;
        m_TextField.setIntegerValue(value);
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue,value);

    }

}