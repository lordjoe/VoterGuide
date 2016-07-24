package com.lordjoe.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.IDetailDispayer
 *  represents a component which can display a detail dialog
 * @author Steve Lewis
 * @date Feb 28, 2005
 */
public interface IDetailDispayer
{
    public static final Class THIS_CLASS = IDetailDispayer.class;
    public static final IDetailDispayer[] EMPTY_ARRAY = {};

    /**
     * build a dialog showing detail
     * @param parent owning component used to locate a frame
     * @param evt triggereing event
     * @return  possibly null dialog
     */
    public JPanel buildDetailPanel(Component parent,MouseEvent evt);

}
