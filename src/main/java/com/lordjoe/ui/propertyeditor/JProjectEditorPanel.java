package com.lordjoe.ui.propertyeditor;

import javax.swing.*;

/**
 * com.lordjoe.ui.propertyeditor.JProjectEditorPanel
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public class JProjectEditorPanel extends JPanel implements IStylizableComponent
{
    public static JProjectEditorPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JProjectEditorPanel.class;

    private final AbstractReflectionPropertyEditor m_Editor;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public JProjectEditorPanel(AbstractReflectionPropertyEditor pEditor)
    {
        m_Editor = pEditor;
    }

    /**
     * return sub components to a stylizer
     *
     * @return
     */
    public JComponent[] getStylizableComponents()
    {
       return  m_Editor.getStylizableComponents();
    }

    /**
     * return self as Jcomponent to a stylizer
     *
     * @return
     */
    public JComponent getStylizableSelf()
    {
        return null;
    }
}
