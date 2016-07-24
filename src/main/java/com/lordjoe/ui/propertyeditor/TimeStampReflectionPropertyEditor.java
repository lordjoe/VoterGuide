package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;
import com.lordjoe.lang.*;
import com.lordjoe.ui.general.*;
import com.lordjoe.ui.timeeditor.*;

import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.beans.*;

/**
 * com.lordjoe.ui.propertyeditor.TimeStampReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class TimeStampReflectionPropertyEditor extends  AbstractReflectionPropertyEditor<Timestamp>
    implements PropertyChangeListener
{
    public static DateReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DateReflectionPropertyEditor.class;
    public static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm";

    private JTimeStampChooser m_TextField;
    public TimeStampReflectionPropertyEditor(IPropertyWrapper<Timestamp> pw)
    {
        super(pw);
        m_TextField = new JTimeStampChooser();
        m_TextField.addPropertyChangeListener(this);
        m_TextField.addFocusListener(this);
        m_TextField.setDateFormatString(DATE_FORMAT);
        adjustFieldProperties(m_TextField);
        if(pw.isReadOnly())
               m_TextField.setEnabled(false);
        if(pw.getCurrentValue() == null) {
           ObjectOps.breakHere();
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
    public Timestamp getValue()
    {
        Date date = m_TextField.getDate();
        if(date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    public void setValue(Timestamp value)
    {
        Date oldValue = getValue();
        if(ObjectOps.equalsNullSafe(oldValue,value))
            return;
        m_TextField.setTime(value);
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue,value);

    }


    public void propertyChange(PropertyChangeEvent evt) {
        if("date".equals(evt.getPropertyName())) {
           adjustFieldProperties(m_TextField);
            Date oldValue = getValue();
            notifyEditedPropertyChangeListeners(oldValue,oldValue);
         }
    }

    protected void onBlur() {
        Date value = getValue();
        adjustFieldProperties(m_TextField);
        notifyEditedPropertyChangeListeners(null, value);
    }


}