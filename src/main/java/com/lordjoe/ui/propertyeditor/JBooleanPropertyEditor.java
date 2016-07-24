package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.propertyeditor.JIntegerPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JBooleanPropertyEditor extends JCheckBox implements IPropertyEditor<Boolean>
{
    public static final Class THIS_CLASS = JBooleanPropertyEditor.class;
    public static final JBooleanPropertyEditor EMPTY_ARRAY[] = {};

    private Boolean m_OriginalValue;
    private Boolean m_Value;
    private Boolean m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JBooleanPropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();

        m_OriginalValue = convertToBoolean(value);
        setBooleanValue(m_OriginalValue);
        setOpaque(false);
     }


    private Boolean convertToBoolean(Object o)
    {
       if(o instanceof Boolean)  {
           return (Boolean)o;
       }
        if(o instanceof String)   {
            return "true".equalsIgnoreCase((String)o) ||
                    "ok".equalsIgnoreCase((String)o);
        }
        return false;
    }

    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        setSelected("true".equals(s));

    }



    /**
     * return current value as a String or null if no
     * value
     * @return
     */
    public String getStringValue( )
    {
        Object val = getCurrentValue();
        if(val == null)
            return null;
        return val.toString();
    }


    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(Boolean value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Boolean getValueOnEntry()
    {
        return m_ValueOnEntry;
    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Boolean getOriginalValue()
    {
        return m_OriginalValue;
    }


    /**
     * make the  current value is either valid or invalid
     *
     * @param b as ab0ve
     */
    public void setValueValid(boolean valid)
    {
        if(valid)
            setBackground(Color.white);
        else
            setBackground(UIUtilities.INVALID_COLOR);


    }

    /**
     * test whether the value is valid
     * return null if unable to decide
     *
     * @param b possibly null value
     * @return as above null is no opinion
     */
    public Boolean isValueValid(Boolean b)
    {
        return null;
    }

    /**
     * return the value represented by the cuttent state of the editor
     *
     * @return possibly null value as above
     * @throws IllegalStateException when the currrent state of the
     *                               editor is not a legal value
     */
    public Boolean getCurrentValue() throws IllegalStateException
    {
       return getBooleanValue();
    }

    public void setBooleanValue(Boolean b)
    {
        m_Value = b;
        boolean value = false;
        if(b != null)
             value = b.booleanValue();
        setSelected(value);
    }
    public Boolean getBooleanValue()
    {
        if(m_Value == null)
            return Boolean.FALSE;
        return m_Value;
    }



    /**
     * allow user editing
     *
     * @param enabled
     */
    public void setEditable(boolean enabled)
    {
        setEnabled(enabled);

    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        setBooleanValue((Boolean)m_Property.getValue());
      }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        setBooleanValue(m_OriginalValue);

    }

    /**
     * set to the default value
     */
    public void setDefault()
    {
        Boolean defaultValue = (Boolean)m_Property.getDefaultValue();
        setBooleanValue(defaultValue);
     }

    /**
     * apply current changes
     */
    public void apply()
    {
        Boolean ivalue = isSelected();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }

}
