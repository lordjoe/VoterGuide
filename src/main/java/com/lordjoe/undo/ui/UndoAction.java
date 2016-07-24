package com.lordjoe.undo.ui;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.undo.UndoStack;
import com.lordjoe.undo.UndoStateListener;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * com.lordjoe.ui.UndoAction
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public class UndoAction extends AbstractAction implements UndoStateListener
{
    public static UndoAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UndoAction.class;

    /**
     * Defines an <code>Action</code> object with a default
     * description string and default icon.
     */
    public UndoAction()
    {
        String value = ResourceString.getStringFromText("Undo");
        putValue(Action.NAME, value);
        putValue(Action.SMALL_ICON, UIUtilities.getImageIcon("icons/edit-undo"));
        putValue(Action.SHORT_DESCRIPTION, value);
        UndoStack.getInstance().addUndoStateListener(this);
        checkEnabled();
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        UndoStack undo = UndoStack.getInstance();
        undo.undo();
    }

    protected void checkEnabled()
    {
        UndoStack undo = UndoStack.getInstance();
        boolean b = undo.getUndoCount() > 0;
        setEnabled(b);
        String value = ResourceString.getStringFromText("Undo");
        if (!b) {
            value += " " + ResourceString.getStringFromText("no actions to undo");
        }
        putValue(Action.SHORT_DESCRIPTION, value);
    }

    /**
     * undo action added
     */
    public void onUndoStateChange()
    {
        checkEnabled();

    }
}
