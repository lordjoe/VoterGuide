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
public class RedoAction extends AbstractAction implements UndoStateListener
{
    public static RedoAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RedoAction.class;

    /**
     * Defines an <code>Action</code> object with a default
     * description string and default icon.
     */
    public RedoAction()
    {
        String fromText = ResourceString.getStringFromText("Redo");
        putValue(Action.NAME, fromText);
        putValue(Action.SMALL_ICON, UIUtilities.getImageIcon("icons/edit-redo"));
        putValue(Action.SHORT_DESCRIPTION, fromText);
        UndoStack.getInstance().addUndoStateListener(this);
        checkEnabled();
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        UndoStack undo = UndoStack.getInstance();
        undo.redo();

    }

    protected void checkEnabled()
    {
        UndoStack undo = UndoStack.getInstance();
        boolean b = undo.getRedoCount() > 0;
        setEnabled(b);
        String value = ResourceString.getStringFromText("Redo");
        if (!b) {
            value += " " + ResourceString.getStringFromText("no actions to redo");
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