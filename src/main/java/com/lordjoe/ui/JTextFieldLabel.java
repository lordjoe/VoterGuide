package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JTextFieldLabel
 *    looks like a disabled text field acts like a label
 *   Used when the user thinks of it as a text field but cammot edit
 * @author Steve Lewis
 * @date Oct 15, 2007
 */
public class JTextFieldLabel extends JLabel
{
    public static JTextFieldLabel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JTextFieldLabel.class;

    public JTextFieldLabel()
    {
        super(" ");
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }
    public JTextFieldLabel(String s)
    {
        super(s);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }
}
