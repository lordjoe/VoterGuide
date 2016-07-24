package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;

/**
 * com.lordjoe.ui.propertyeditor.IPropertyEditorGenerator
 *
 * @author Steve Lewis
 * @date Dec 15, 2007
 */
public interface IPropertyEditorGenerator {
    public static IPropertyEditorGenerator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IPropertyEditorGenerator.class;

    /**
     * make a suitable property edotir
     * @param p  non-null property
     * @return  non-null editor
     */
    public IComponentPropertyEditor buildEditor(IPropertyWrapper p);
}
