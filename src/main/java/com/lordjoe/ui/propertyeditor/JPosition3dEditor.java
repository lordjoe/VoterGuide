package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.Position3d;
import com.lordjoe.ui.general.JSingleLineTextField;

import java.awt.*;

/**
 * com.lordjoe.ui.propertyeditor.JStringPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JPosition3dEditor extends JSingleLineTextField implements IPropertyEditor<Position3d> 
{
    public static final Class THIS_CLASS = JPosition3dEditor.class;
    public static final JPosition3dEditor EMPTY_ARRAY[] = {};

    private Position3d m_OriginalValue;
    private Position3d m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JPosition3dEditor(IEditableProperty prop)
    {
        m_Property = prop;
        m_OriginalValue = (Position3d) prop.getValue();
        if (m_OriginalValue != null)
            setText(m_OriginalValue.toString());
        else
            setText("");
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
        setText((String) m_Property.getValue());
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        m_Property.setValue(m_OriginalValue);
        if (m_OriginalValue != null)
            setText(m_OriginalValue.toString());
        else
            setText("");
    }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Position3d defaultValue = (Position3d) m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        if (defaultValue != null)
            setText(defaultValue.toString());
        else
            setText("");
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
     * apply current changes
     */
    public void apply()
    {
        String value = getText();
        Position3d pos = new Position3d(value);
        m_Property.setValue(pos);
        m_OriginalValue = pos;
    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Position3d getOriginalValue()
    {
        return m_OriginalValue;
    }

    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(Position3d value)
    {
        m_ValueOnEntry = value;

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
    public Boolean isValueValid(Position3d b)
    {
        return null;
    }


    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Position3d getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    /**
     * return the value represented by the cuttent state of the editor
     *
     * @return possibly null value as above
     * @throws IllegalStateException when the currrent state of the
     *                               editor is not a legal value
     */
    public Position3d getCurrentValue() throws IllegalStateException
    {
        String value = getText();
        Position3d pos = new Position3d(value);
        return pos;
    }
}
