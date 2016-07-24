package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * com.lordjoe.ui.ExitDialogAction
 *
 * @author Steve Lewis
 * @date Nov 13, 2007
 */
public class ExitDialogAction extends AbstractAction
{
    public static ExitDialogAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ExitDialogAction.class;

    private JComponent m_Dialog;
    /**
     * Defines an <code>Action</code> object with a default
     * description string and default icon.
     */
    public ExitDialogAction(JComponent dlg)
    {
        m_Dialog = dlg;
        putValue(Action.NAME, "Exit");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        UIUtilities.getTopAncestor(m_Dialog).setVisible(false);

    }
}