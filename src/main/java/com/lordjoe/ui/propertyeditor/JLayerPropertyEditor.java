package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.JIntegerPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class JLayerPropertyEditor extends JIntegerPropertyEditor
{
    public static final Class THIS_CLASS = JLayerPropertyEditor.class;
    public static final JLayerPropertyEditor EMPTY_ARRAY[] = {};


    public JLayerPropertyEditor(IEditableProperty prop)
    {
        super(prop);
    }

     /**
     * set to the default value
     */
    public void setDefault()
    {
        Object value = getProperty().getDefaultValue();
        Integer realValue = null;
        if(value instanceof Integer)
            realValue = (Integer)value;
        else
            realValue = new Integer(value.toString());
        getProperty().setValue(realValue);
        setIntegerValue(realValue);
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        Integer originalValue = getOriginalValue();
        getProperty().setValue(originalValue.toString());
        setIntegerValue(originalValue);
    }


    /**
     * apply current changes
     */
    public void apply()
    {
        Integer ivalue = getIntegerValue();
        getProperty().setValue(ivalue.toString());
        setOriginalValue(ivalue);
    }

}
