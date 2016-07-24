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
public class JDoublePropertyEditor extends JDoubleTextField implements IPropertyEditor<Double>
{
    public static final Class THIS_CLASS = JDoublePropertyEditor.class;
    public static final JDoublePropertyEditor EMPTY_ARRAY[] = {};

    private Double m_OriginalValue;
    private Double m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JDoublePropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();
        if (value != null && value instanceof String)
            value = new Double((String) value);
        m_OriginalValue = (Double) value;
        setDoubleValue(m_OriginalValue);
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
        if(b)
            setBackground(DISABLED_BACKGROUND);
        else
            setBackground(DISABLED_BACKGROUND);
    }
    

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        setDoubleValue((Double) m_Property.getValue());
    }


    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Double getOriginalValue()
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
    public Double getCurrentValue() throws IllegalStateException
    {
        return getDoubleValue();
    }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setDoubleValue(null);
        else
            setDoubleValue(Double.valueOf(s));

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
    public Boolean isValueValid(Double b)
    {
        return null;
    }


    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(Double value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Double getValueOnEntry()
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
        setDoubleValue(m_OriginalValue);
    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Double defaultValue = (Double) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setDoubleValue(defaultValue);
    }

    /**
     * apply current changes
     */
    public void apply()
    {
        Double ivalue = getDoubleValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }

}
