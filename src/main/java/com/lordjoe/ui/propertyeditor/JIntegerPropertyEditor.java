package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.JIntegerTextField;
import com.lordjoe.utilities.Util;

import java.awt.*;


/**
 * com.lordjoe.ui.propertyeditor.JIntegerPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JIntegerPropertyEditor extends JIntegerTextField implements IPropertyEditor<Integer> 
{
    public static final Class THIS_CLASS = JIntegerPropertyEditor.class;
    public static final JIntegerPropertyEditor EMPTY_ARRAY[] = {};
    public static final Integer ZERO = new Integer(0);

    private Integer m_OriginalValue;
    private Integer m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JIntegerPropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();
        if (value == null) {
            setOriginalValue(null);
        }
        else {
            Integer realValue = null;
            if (value instanceof Integer)
                realValue = (Integer) value;
            else
                realValue = new Integer(value.toString());
            setOriginalValue(realValue);
        }
        setIntegerValue(m_OriginalValue);
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


    public void setOriginalValue(Integer pOriginalValue)
    {
        m_OriginalValue = pOriginalValue;
    }

    /**
     * return the value represented by the cuttent state of the editor
     *
     * @return possibly null value as above
     * @throws IllegalStateException when the currrent state of the
     *                               editor is not a legal value
     */
    public Integer getCurrentValue() throws IllegalStateException
    {
        return getIntegerValue();
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
    public Boolean isValueValid(Integer in)
    {
        Integer min = getMinimumValue();
         if (min != null) {
             if (in < min)
                 return Boolean.FALSE;
         }
         Integer max = getMaximumValue();
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
    public void setValueOnEntry(Integer value)
    {
        m_ValueOnEntry = value;

    }

    public void setBackground(Color bg)
    {
        if(bg.equals(Color.white))
            Util.breakHere();
        else
            Util.breakHere();

        super.setBackground(bg);

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Integer getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    public Integer getOriginalValue()
    {
        return m_OriginalValue;
    }

    public IEditableProperty getProperty()
    {
        return m_Property;
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        Integer val = (Integer) m_Property.getValue();
        setIntegerValue(val);
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        setIntegerValue(m_OriginalValue);
    }


    @Override
    protected void setRequiredColor()
    {
         super.setRequiredColor();

    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Integer defaultValue = (Integer) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setIntegerValue(defaultValue);
    }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setIntegerValue(null);
        else
            setIntegerValue(Integer.valueOf(s));

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
     * apply current changes
     */
    public void apply()
    {
        Integer ivalue = getIntegerValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
        
        String s = getText();
        s = null;
    }

    /**
     * debug over
     * @param t
     */
    @Override
    public void setText(String t)
    {
        super.setText(t);

    }
}
