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
public class DoubleReflectionPropertyEditor extends  AbstractReflectionPropertyEditor<Double>
    implements ActionListener
{
    public static DoubleReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DoubleReflectionPropertyEditor.class;

    private JDoubleTextField m_TextField;
    public DoubleReflectionPropertyEditor(IPropertyWrapper<Double> pw)
    {
        super(pw);
        m_TextField = new JDoubleTextField(30);
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
        Double value = getValue();
        notifyEditedPropertyChangeListeners(null,value);
    }


    public void actionPerformed(ActionEvent e) {
        adjustFieldProperties(m_TextField);
        Double value = getValue();
        notifyEditedPropertyChangeListeners(null,value);
    }

    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public Double getValue()
    {
        return m_TextField.getDoubleValue();
    }

    public void setValue(Double value)
    {
        Double oldValue = getValue();
        if(ObjectOps.equalsNullSafe(oldValue,value))
            return;
        m_TextField.setDoubleValue(value);
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue,value);

    }

}
