package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JSizedButton
 *  this is a JButton supporting a minimum size
 * @author Steve Lewis
 * @date Nov 30, 2006
 */
public class JSizedButton extends JButton
{
    public static JSizedButton[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JSizedButton.class;

    private Dimension m_SetSize;
    public JSizedButton(Action act, Dimension size)
    {
        super(act);
        m_SetSize = size;
    }


    public Dimension getSetSize()
    {
        return m_SetSize;
    }

    public void setSetSize(Dimension pSize)
    {
        m_SetSize = pSize;
    }


    /**
     * If the minimum size has been set to a non-<code>null</code> value
     * just returns it.  If the UI delegate's <code>getMinimumSize</code>
     * method returns a non-<code>null</code> value then return that; otherwise
     * defer to the component's layout manager.
     *
     * @return the value of the <code>minimumSize</code> property
     * @see #setMinimumSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMinimumSize()
    {
        Dimension ret = super.getMinimumSize();
        Dimension newRet = new Dimension(Math.max(ret.width,m_SetSize.width),
               Math.max(ret.height,m_SetSize.height) );
        return newRet;

    }


    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getPreferredSize()
    {
        Dimension ret = super.getPreferredSize();
        Dimension newRet = new Dimension(Math.max(ret.width,m_SetSize.width),
               Math.max(ret.height,m_SetSize.height) );
        return newRet;
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
        Dimension ret = super.getMaximumSize();
        Dimension newRet = new Dimension(Math.max(ret.width,m_SetSize.width),
               Math.max(ret.height,m_SetSize.height) );
        return newRet;

    }
}
