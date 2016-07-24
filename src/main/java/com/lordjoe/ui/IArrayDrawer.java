package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.IArrayDrawer
 *
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IArrayDrawer
{
    public static final Class THIS_CLASS = IArrayDrawer.class;
    public static final IArrayDrawer EMPTY_ARRAY[] = {};

    /**
     * draw on the Graphics
     * @param g
     */
    public void drawArray(Graphics g);

    /**
     * set the size for the drawing - this is hard to get from a Graphics
     * @param size non-null Size in pixels
     */
    public void setDrawingSize(Dimension size);

    /**
     * return the size of one square cell
     * @return
     */
    public int getCellSize();


    public void setHoldingComponent(JComponent cmp);

}