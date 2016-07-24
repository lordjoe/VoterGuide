package com.lordjoe.ui.propertyeditor;

import com.lordjoe.lang.*;
import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;


/**
 * com.lordjoe.ui.propertyeditor.SelectionReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class SelectionReflectionPropertyEditor<T> extends AbstractReflectionPropertyEditor<T>
      implements ItemListener, ValidityChangeListener

{
    public static SelectionReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = SelectionReflectionPropertyEditor.class;

    private DefaultComboBoxModel m_Model;
    private JSpecialComboBox m_ComboBox;

    public SelectionReflectionPropertyEditor(IPropertyWrapper<T> pw) {
        super(pw);
        T[] ts = pw.getOptions();
        if (ts == null)
            throw new IllegalStateException("cannot create a Selection editor without options");
        m_Model = new DefaultComboBoxModel(ts);
        m_ComboBox = new JSpecialComboBox(m_Model);
        m_ComboBox.addFocusListener(this);
        m_ComboBox.addItemListener(this);
        if(pw.isReadOnly())
            m_ComboBox.setEnabled(false);
        adjustFieldProperties(m_ComboBox);
    }


    public JComponent[] getStylizableComponents() {
        List<JComponent> holder = new ArrayList<JComponent>();
        holder.add(getLabel());
        holder.add(m_ComboBox);
        JComponent[] ret = new JComponent[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public JComponent getMainDisplay() {
        return m_ComboBox;
    }

    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public T getValue() {
        return (T) m_ComboBox.getSelectedItem();
    }

    public void setValue(T value) {
        Object oldValue = getValue();
        if (ObjectOps.equalsNullSafe(oldValue, value))
            return;
        m_ComboBox.setSelectedItem(value);
        adjustFieldProperties(m_ComboBox);
        notifyEditedPropertyChangeListeners(oldValue, value);
    }

    public void itemStateChanged(ItemEvent e) {
        adjustFieldProperties(m_ComboBox);
        T value = getValue();
        notifyEditedPropertyChangeListeners(null,value);

    }

    protected void onBlur() {
    }


    public void onValidityChange(Object source, boolean isValid) {
        T value = getValue();
        notifyEditedPropertyChangeListeners(null,value);
    }

}
