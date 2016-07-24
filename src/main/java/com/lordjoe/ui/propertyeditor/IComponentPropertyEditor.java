package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.IComponentPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IComponentPropertyEditor<T> extends IGenericPropertyEditor {
    public static IComponentPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IComponentPropertyEditor.class;

    /**
     * add a listener
     * @param lstn
     */
    public void addEditedPropertyChangeListener(EditedPropertyChangeListener lstn);

    /**
     * remove a listener
     * @param lstn
     */
    public void removeEditedPropertyChangeListener(EditedPropertyChangeListener lstn);
    


    public IPropertyWrapper<T> getPropertyWrapper();

    /**
     * return the current value when the editor was created or last committed
     * @return
     */
    public T getOriginalValue();
    /**
     * return the current value as represented by the editor
     * @return
     */
    public T getValue();

    public void setValue(T value);


}
