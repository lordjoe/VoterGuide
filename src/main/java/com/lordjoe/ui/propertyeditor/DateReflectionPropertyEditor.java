package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;
import com.lordjoe.lang.*;
import com.lordjoe.ui.general.*;

import javax.swing.*;
import java.util.*;
import java.beans.*;

/**
 * com.lordjoe.ui.propertyeditor.DateReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class DateReflectionPropertyEditor extends  AbstractReflectionPropertyEditor<java.sql.Date> implements PropertyChangeListener
{
    public static DateReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DateReflectionPropertyEditor.class;
    public static final String DATE_FORMAT = "dd-MMM-yyyy";

    private JDateValueEditor m_TextField;
    public DateReflectionPropertyEditor(IPropertyWrapper<java.sql.Date> pw)
    {
        super(pw);
        m_TextField = new JDateValueEditor();
        m_TextField.addFocusListener(this);
        m_TextField.addPropertyChangeListener(this);
        m_TextField.setDateFormat(DATE_FORMAT);
        if(pw.isReadOnly())
              m_TextField.setEnabled(false);
        adjustFieldProperties(m_TextField);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if("date".equals(evt.getPropertyName())) {
           adjustFieldProperties(m_TextField);
            Date oldValue = getValue();
            notifyEditedPropertyChangeListeners(oldValue,oldValue);
         }
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

    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public java.sql.Date getValue()
    {
        Date date = m_TextField.getTime();
        if(date == null)
            return null;
        
        return new java.sql.Date(date.getTime());
    }

    public void setValue(java.sql.Date value)
    {
        Date oldValue = getValue();
        if(ObjectOps.equalsNullSafe(oldValue,value))  {            
            return;
        }
        m_TextField.setTime(value);
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue,value);
    }




    protected void onBlur() {
        Date value = getValue();
        notifyEditedPropertyChangeListeners(null, value);
    }


}