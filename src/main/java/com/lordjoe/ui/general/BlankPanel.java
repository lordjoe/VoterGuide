package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.BlankPanel
 *
 * @author Steve Lewis
 * @date Aug 8, 2006
 */
public class BlankPanel extends JPanel
{
    public final static BlankPanel[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = BlankPanel.class;

    public BlankPanel()
     {
        setOpaque(false);
     }

    public void paint(Graphics g)
    {
        // do nothing
    }
}
