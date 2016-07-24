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
public class JComboBoxPropertyEditor extends JComboBox implements IPropertyEditor<Object>
{
    public static final Class THIS_CLASS = JComboBoxPropertyEditor.class;
    public static final JComboBoxPropertyEditor EMPTY_ARRAY[] = {};

    private Object m_OriginalValue;
    private Object m_ValueOnEntry;
    private final IEditableProperty m_Property;

    public JComboBoxPropertyEditor(IEditableProperty prop)
    {
        this(prop,prop.getOptions());

     }
    public JComboBoxPropertyEditor(IEditableProperty prop,Object[] options)
    {
        m_Property = prop;
        m_OriginalValue = prop.getValue();
       for (int i = 0; i < options.length; i++)
        {
            Object option = options[i];
            this.addItem(option);
        }
        setSelectedItem(m_OriginalValue);

     }


    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
        setSelectedItem(s); // todo make this work for non-strings

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
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public Object getOriginalValue()
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
    public Object getCurrentValue() throws IllegalStateException
    {
        return getSelectedItem();
    }

    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(Object value)
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
            setBackground( UIUtilities.INVALID_COLOR);


    }

    /**
     * test whether the value is valid
     * return null if unable to decide
     *
     * @param b possibly null value
     * @return as above null is no opinion
     */
    public Boolean isValueValid(Object b)
    {
        return null;
    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public Object getValueOnEntry()
    {
        return m_ValueOnEntry;
    }


    public Dimension getPreferredSize() {
        return new Dimension(300,20);
     //   return super.getPreferredSize();    //To change body of overridden methods use File | Settings | File Templates.
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
        setForeground(Color.black);
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        setSelectedItem(m_Property.getValue());
      }

    /**
      * restore the property to the original value
      * or value set at last apply
      */
     public void revertToOriginal()
     {
         m_Property.setValue(m_OriginalValue);
           setSelectedItem(m_OriginalValue);
      }


    /**
     * set to the default value
     */
    public void setDefault()
    {
        Object defaultValue = (Object)m_Property.getDefaultValue();
        m_Property.setValue(defaultValue);
        setSelectedItem(defaultValue);
     }

    /**
     * apply current changes
     */
    public void apply()
    {
        Object ivalue = getSelectedItem();
        m_Property.setValue(ivalue);
        m_OriginalValue = ivalue;
    }

}
