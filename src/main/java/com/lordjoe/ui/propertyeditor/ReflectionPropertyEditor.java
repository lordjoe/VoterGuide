package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.ReflectionPropertyCreator
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class ReflectionPropertyEditor<T> implements IObjectEditorDialog<T>
{
    public static ReflectionPropertyCreator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectionPropertyCreator.class;


    private final ReflectionPropertyEditorPanel<T>  m_Panel;
    public ReflectionPropertyEditor(T target)
    {
         m_Panel = new ReflectionPropertyEditorPanel(target);

    }
    public ReflectionPropertyEditor(T target,String... properties)
    {
         m_Panel = new ReflectionPropertyEditorPanel(target,properties);

    }

    /**
     * set the style of the panel
     * @param style
     */
    public void applyStyle(IStylizer style)
    {
         style.stylize(m_Panel);
    }


    /**
     * offer the user a modal dialog to edit an existing object
     * @return possibly null edited object -
     */
    public T editObject()
    {
        return m_Panel.doEditTarget();
    }

    public JComponent getEditorComponent() {
        return m_Panel;
    }
}