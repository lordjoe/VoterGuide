package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JMyViewport
 *
 * @author Steve Lewis
 * @date Nov 4, 2008
 */
public class JMyViewport extends JViewport
{
    public static JMyViewport[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JMyViewport.class;

    private final JMyScrollPane m_ScrollPane;
    public JMyViewport(JMyScrollPane sp)
    {
        m_ScrollPane = sp;
    }

    public JMyScrollPane getScrollPane()
    {
        return m_ScrollPane;
    }

    /**
     * If the view's size hasn't been explicitly set, return the
     * preferred size, otherwise return the view's current size.
     * If there is no view, return 0,0.
     *
     * @return a <code>Dimension</code> object specifying the size of the view
     */
    public Dimension getViewSize()
    {
        Dimension viewSize = super.getViewSize();
        return viewSize;

    }
}
