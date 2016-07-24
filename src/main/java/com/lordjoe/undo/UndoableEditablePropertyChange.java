package com.lordjoe.undo;

import com.lordjoe.lib.xml.*;
import com.lordjoe.ui.propertyeditor.*;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.undo.UndoablePropertyChange
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public class UndoableEditablePropertyChange<T> extends AbstractUndoable
{
    public static UndoableEditablePropertyChange[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UndoableEditablePropertyChange.class;

    private final JPanel m_Pane;
    private final IPropertyEditor m_Target;
    private final String m_OldValue;
    private final String m_NewValue;

    public UndoableEditablePropertyChange(JPanel editorPane,
                                          IPropertyEditor target,Object oldValue,Object newValue )
    {
        super(UndoableActionType.ChangeValue);
        m_Pane =  editorPane;
        m_Target = target;
        if(oldValue != null)
            m_OldValue = oldValue.toString();
        else
            m_OldValue = null;
        if(newValue != null)
            m_NewValue = newValue.toString();
        else
            m_NewValue = null;

    }

    public IPropertyEditor getTarget()
    {
        return m_Target;
    }


    public String getOldValue()
    {
        return m_OldValue;
    }

    public String getNewValue()
    {
        return m_NewValue;
    }

    public JPanel getPane()
    {
        return m_Pane;
    }

    /**
     * undo the action
     */
    public void undo()
    {
        String temp = null;
        IPropertyEditor propertyEditor = getTarget();
        String s = getOldValue();
        if(propertyEditor instanceof JTextField) {
            temp = ((JTextField)propertyEditor).getText();
        }
        propertyEditor.setStringValue(s);
        JPanel editorPane = getPane();
        editorPane.repaint(100);

        editorPane.repaint(100);
        if(propertyEditor instanceof JTextField) {
            temp = ((JTextField)propertyEditor).getText();
        }
        propertyEditor.apply();
        if(propertyEditor instanceof JTextField) {
            temp = ((JTextField)propertyEditor).getText();
        }
    //    editorPane.getParentPanel().getParentConfiguration().update();
        notifyUndoChangeListeners(propertyEditor);
    }

    /**
     * redo the action
     */
    public void redo()
    {
        IPropertyEditor propertyEditor = getTarget();
        String s = getNewValue();
        propertyEditor.setStringValue(s);
        JPanel editorPane = getPane();
        editorPane.repaint(100);
        propertyEditor.apply();
 //       editorPane.getParentPanel().getParentConfiguration().update();
        notifyUndoChangeListeners(propertyEditor);

    }
}