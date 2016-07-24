package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * com.lordjoe.ui.propertyeditor.JLongPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JNetAddressPropertyEditor extends JAbstractValidatingPropertyEditor<InetAddress> 
{
    public static final Class THIS_CLASS = JNetAddressPropertyEditor.class;
    public static final JNetAddressPropertyEditor EMPTY_ARRAY[] = {};

    private InetAddress m_OriginalValue;
    private InetAddress m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JNetAddressPropertyEditor(IEditableProperty prop)
    {
        m_Property = prop;
        Object value = prop.getValue();
        if (value != null) {
            if (value instanceof InetAddress) {
                m_OriginalValue = (InetAddress) value;
            }
            else {
                if (value instanceof String) {
                    m_OriginalValue = PropertyEditorUtilities.buildINetAddress(value.toString());
                }
            }
            setAddressValue(m_OriginalValue);
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
        setAddressValue((InetAddress) m_Property.getValue());
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        setAddressValue(m_OriginalValue);

    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public InetAddress getOriginalValue()
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
    public InetAddress getCurrentValue() throws IllegalStateException
    {
        return getAddressValue();
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
    public Boolean isValueValid(InetAddress b)
    {
        return null;
    }


    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(InetAddress value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public InetAddress getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        InetAddress defaultValue = (InetAddress) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setAddressValue(defaultValue);
    }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setAddressValue(null);
        else
            try {
                setAddressValue(InetAddress.getByName(s));
            }
            catch (UnknownHostException e) {
                setAddressValue(null);
            }

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
        InetAddress ivalue = getAddressValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }


    public boolean isValid()
    {
        String s = getText();
        if(s == null || s.length() == 0)
            return false;
        return PropertyEditorUtilities.buildINetAddress(s) != null;
    }



    public InetAddress getAddressValue()
    {
        String text = getText();
        if (text.length() == 0)
            return null;
        return PropertyEditorUtilities.buildINetAddress(text);
    }


    public void setAddressValue(InetAddress in)
    {
        if (in == null)
            setText("");
         else
            setText(in.getHostName());
    }

}