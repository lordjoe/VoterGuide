package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.JDoubleTextField;

import java.awt.*;


/**
 * com.lordjoe.ui.propertyeditor.JDoublePropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JFloatPropertyEditor extends JDoubleTextField implements IPropertyEditor<Float> 
{
    public static final Class THIS_CLASS = JFloatPropertyEditor.class;
    public static final JFloatPropertyEditor EMPTY_ARRAY[] = {};

    private Float m_OriginalValue;
    private Float m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JFloatPropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();
        if(value == null)
            return;
        if(value instanceof String)
           value = new Float((String)value);
        if(value instanceof Float) {
            m_OriginalValue = (Float)value;
            setFloatValue(m_OriginalValue);
        }
        else {
            if(value instanceof Number) {
                m_OriginalValue = new Float(((Number)value).floatValue());
                setFloatValue(m_OriginalValue);
            }
            else {
                m_OriginalValue = new Float((value.toString()));
                setFloatValue(m_OriginalValue);
             }
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
        setFloatValue((Float)m_Property.getValue());
      }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Float getOriginalValue()
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
    public Float getCurrentValue() throws IllegalStateException
    {
        return getFloatValue();
    }

    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setFloatValue(null);
        else
            setFloatValue(Float.valueOf(s));

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
    public Boolean isValueValid(Float in)
    {
        Double min = getMinimumValue();
         if (min != null) {
             if (in < min)
                 return Boolean.FALSE;
         }
         Double max = getMaximumValue();
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
    public void setValueOnEntry(Float value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Float getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        setFloatValue(m_OriginalValue);

    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Float defaultValue = (Float)m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setFloatValue(defaultValue);
     }

    /**
     * apply current changes
     */
    public void apply()
    {
        Float ivalue = getFloatValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }

}
