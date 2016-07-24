package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.JLongTextField;

import java.awt.*;


/**
 * com.lordjoe.ui.propertyeditor.JLongPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JLongPropertyEditor extends JLongTextField implements IPropertyEditor<Long>
{
    public static final Class THIS_CLASS = JLongPropertyEditor.class;
    public static final JLongPropertyEditor EMPTY_ARRAY[] = {};

    private Long m_OriginalValue;
    private Long m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JLongPropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();
        if (value != null) {
            if (value instanceof Long) {
                m_OriginalValue = (Long) value;
            }
            else {
                if (value instanceof Number) {
                    m_OriginalValue = new Long(((Number) value).longValue());
                }
                else {
                    m_OriginalValue = new Long(value.toString());
                }
            }

            setLongValue(m_OriginalValue);
        }
    }

    /**
     * Sets the specified boolean to indicate whether or not this
     * <code>TextComponent</code> should be editable.
     * A PropertyChange event ("editable") is fired when the
     * state is changed.
     *
     * @param b the boolean to be set
     * @beaninfo description: specifies if the text can be edited
     * bound: true
     * @see #isEditable
     */
    public void setEditable(boolean b)
    {
        super.setEditable(b);
        setEnabled(b);
        setDisabledTextColor(Color.black);
        setOpaque(b);
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        setLongValue((Long) m_Property.getValue());
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        setLongValue(m_OriginalValue);

    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Long getOriginalValue()
    {
       return m_OriginalValue;
    }

    /**
     * return the value represented by the cuttent state of the editor
     *
     * @return possibly null value as above
     * @throws IllegalStateException when the currrent state of the
     *                               editor is not a legal value
     */
    public Long getCurrentValue() throws IllegalStateException
    {
        return getLongValue();
    }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setLongValue(null);
        else
            setLongValue(Long.valueOf(s));

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
    public Boolean isValueValid(Long in)
    {
        Long min = getMinimumValue();
         if (min != null) {
             if (in < min)
                 return Boolean.FALSE;
         }
         Long max = getMaximumValue();
         if (max != null) {
             if (in > max)
                 return Boolean.FALSE;
         }
        if(!isZeroAllowed() && 0 == in)
            return Boolean.FALSE;
        return Boolean.TRUE;
      }

    
    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(Long value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Long getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Long defaultValue = (Long) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setLongValue(defaultValue);
    }

    /**
     * apply current changes
     */
    public void apply()
    {
        Long ivalue = getLongValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }

}
