package com.lordjoe.ui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.datatransfer.*;

/**
 * com.lordjoe.ui.CopyTextAction
*    supports a button to place the contents of a text field on the clipboard
* @author Steve Lewis
* @date Oct 15, 2007
*/ //
// Copy the contents of a text pane to the clipboard
//
public class CopyTextAction extends AbstractAction
{
    private final JTextField m_TextHolder;

    public CopyTextAction(JTextField th)
    {
        this.putValue(Action.NAME,  "Copy");
        m_TextHolder = th;
    }

    public void actionPerformed(ActionEvent ev)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        String text = m_TextHolder.getText();
        StringSelection t = new StringSelection(text);
        cb.setContents(t,t);
    }

}
