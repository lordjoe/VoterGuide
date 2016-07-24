package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.RequiredObjectEditor
 *
 * @author Steve Lewis
 * @date Dec 15, 2007
 */
public interface RequiredObjectEditor {
    public static RequiredObjectEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RequiredObjectEditor.class;

    /**
     * return the edited Object
     * @return possibly null Object
     */
    public Object getEditedObject();

    /**
     * set the control as editable
     * @param editable true if editable
     */
    public void setEditable(boolean editable);

    /**
     * set the control as reguired contition
     * @param editable true if editable
     */
    public void setRequiredPresent(boolean editable);

    /**
     * set the control as reguired contition
     * @param editable true if editable
     */
    public void setRequired(boolean editable);


}
