package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.PlaceHolderPanel
 *
 * @author slewis
 * @date Apr 22, 2005
 */
public class PlaceHolderPanel extends JPanel
{
    public static final Class THIS_CLASS = PlaceHolderPanel.class;
    public static final PlaceHolderPanel EMPTY_ARRAY[] = {};
    public static final Color BG_COLOR = Color.red;
    public PlaceHolderPanel()
    {
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());
        JLabel lbl = new JSizeableLabel("This is a place holder");
        add(lbl,BorderLayout.CENTER);
    }

}
