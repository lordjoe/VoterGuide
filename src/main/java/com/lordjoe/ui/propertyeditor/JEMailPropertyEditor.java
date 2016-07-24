package com.lordjoe.ui.propertyeditor;

import com.lordjoe.ui.general.*;

import java.awt.*;
import java.net.*;

/**
 * com.lordjoe.ui.propertyeditor.JLongPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JEMailPropertyEditor extends JAbstractValidatingPropertyEditor<String>
{
    public static final Class THIS_CLASS = JEMailPropertyEditor.class;
    public static final JEMailPropertyEditor EMPTY_ARRAY[] = {};

    private String m_OriginalValue;
    private String m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JEMailPropertyEditor(IEditableProperty prop) {
        m_Property = prop;
        Object value = prop.getValue();
        if (value != null) {
            if (value instanceof String) {
                m_OriginalValue = (String) value;
            } else {
                m_OriginalValue = value.toString();
            }
            setEMailValue(m_OriginalValue);
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
    public void setEditable(boolean b) {
        super.setEditable(b);
        setEnabled(b);
        setDisabledTextColor(Color.black);
        setOpaque(b);
    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public String getOriginalValue()
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
    public String getCurrentValue() throws IllegalStateException
    {
        return getText();
    }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        if(s == null)
            setText(null);
        else
            setText(s);

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
    public void setValueOnEntry(String value)
    {
        m_ValueOnEntry = value;

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public String getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert() {
        setEMailValue((String) m_Property.getValue());
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal() {
        m_Property.setValue(m_OriginalValue);
        setEMailValue(m_OriginalValue);

    }

    /**
     * set to the default value
     */
    public void setDefault() {
        String defaultValue = (String) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setEMailValue(defaultValue);
    }

    /**
     * apply current changes
     */
    public void apply() {
        String ivalue = getEMailValue();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }


    public boolean isValid() {
        String s = getText();
        if (s == null || s.length() == 0)
            return false;
        return isLegalEMail(s);
    }

    public static boolean isLegalEMail(String s) {
        if (!s.contains("@"))
            return false;
        String[] parts = s.split("@");
        if (parts.length != 2)
            return false;
        InetAddress addr = PropertyEditorUtilities.buildINetAddress(parts[1]);
        return addr != null;
    }

    public String getEMailValue() {
        String text = getText();
        if (text.length() == 0)
            return null;
        return text;
    }


    public void setEMailValue(String in) {
        if (in == null)
            setText("");
        else
            setText(in.toString());
    }

}