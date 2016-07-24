package com.lordjoe.ui;

import javax.swing.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.CancelDialogAction
 *
 * @author Steve Lewis
 * @date Nov 13, 2007
 */
public class CancelDialogAction extends AbstractAction
{
    public static CancelDialogAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CancelDialogAction.class;

    private JDialog m_Dialog;
    /**
     * Defines an <code>Action</code> object with a default
     * description string and default icon.
     */
    public CancelDialogAction(JDialog dlg)
    {
        m_Dialog = dlg;
        putValue(Action.NAME, "Cancel");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        m_Dialog.setVisible(false);

    }
}
