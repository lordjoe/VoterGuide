package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.JInelasticLabel
 *  like a regular label but sets MaxSize to PreferredSize
 * @author Steve Lewis
 * @date Feb 7, 2006
 */
public class JInelasticLabel extends JLabel
{
    public final static JInelasticLabel[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = JInelasticLabel.class;

    public JInelasticLabel(String text)
    {
        super(text);
    }

    public JInelasticLabel(Icon text)
    {
        super(text);
    }

    /**
     * If the maximum size has been set to a non-<code>null</code> value
     * just returns it.  If the UI delegate's <code>getMaximumSize</code>
     * method returns a non-<code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>maximumSize</code> property
     * @see #setMaximumSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMaximumSize()
    {
        return super.getPreferredSize();

    }

}
