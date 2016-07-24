package com.lordjoe.ui.propertyeditor;

import java.awt.*;

/**
 * com.lordjoe.ui.propertyeditor.IPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public interface IPropertyEditor<T>
{
    public static final Class THIS_CLASS = IPropertyEditor.class;
    public static final IPropertyEditor EMPTY_ARRAY[] = {};

    public static final Color DISABLED_BACKGROUND = Color.lightGray;
    public static final Color ENABLED_BACKGROUND = Color.white;
    public static final Color ERROR_COLOR = new Color(255, 200, 200);
    public static final Color DEFAULT_COLOR = Color.white;

    /**
     * return current value as a String or null if no
     * value
     * @return
     */
    public String getStringValue();

    /**
     * set the value as a string
     * @param s possibly null String
     */
    public void setStringValue(String s);
    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     * @return possibly null value
     */
    public T getOriginalValue();

    /**
     * return the value represented by the cuttent state of the editor
     * @return possibly null value as above
     * @throws IllegalStateException  when the currrent state of the
     * editor is not a legal value
     */
    public T getCurrentValue() throws IllegalStateException;

    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     * @param value
     */
    public void setValueOnEntry(T value);

    /**
     * get the value when the focus was gained
     * @return  possible null value
     * @throws IllegalStateException
     */
    public T getValueOnEntry();

    /**
     * make the  current value is either valid or invalid
     *
     * @param b as ab0ve
     */
    public void setValueValid(boolean valid);


    /**
     * test whether the value is valid
     * return null if unable to decide
     * @param b possibly null value
     * @return as above null is no opinion
     */
    public Boolean isValueValid(T b);

    /**
     * allow user editing
     * @param enabled
     */
    public void setEditable(boolean enabled);

    /**
      * restore the property to the original value
      * or value set at last apply
      */
     public void revert();

    /**
      * restore the property to the original value
      * or value set at last apply
      */
     public void revertToOriginal();

    /**
     * set to the default value
     */
    public void setDefault();

    /**
     * apply current changes
     */
    public void apply();


}