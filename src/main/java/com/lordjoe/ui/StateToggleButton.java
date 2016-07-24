package com.lordjoe.ui;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * com.lordjoe.ui.StateToggleButton
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public class StateToggleButton<T> extends JToggleButton
{
    public static StateToggleButton[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = StateToggleButton.class;

    private final T m_Subject;
    private Icon m_SelectedUnpressedIcon;
    private Icon m_SelectedPressedIcon;

    protected StateToggleButton(T subject, Map<ImageState, Icon> icons)
    {
        m_Subject = subject;
        setIcons(icons);
    }

    public T getSubject()
    {
        return m_Subject;
    }


    protected void setIcons(Map<ImageState, Icon> icons)
    {
       Icon icn = null;
        icn = icons.get(ImageState.Disabled);
        if(icn != null)
            setDisabledIcon(icn);
        icn = icons.get(ImageState.Normal);
        if(icn != null)
            setIcon(icn);
        icn = icons.get(ImageState.Pressed);
        if(icn != null)  {
            setPressedIcon(icn);
            m_SelectedUnpressedIcon = icn;
        }
        icn = icons.get(ImageState.Selected);
        if(icn != null)
            setSelectedIcon(icn);
        icn = icons.get(ImageState.SelectedPressed);
        if(icn != null)
            m_SelectedPressedIcon = icn;

    }

    protected Dimension getDefaultSize()
    {
        return new Dimension(20,20);
    }

    /**
     * Sets the button's default icon. This icon is
     * also used as the "pressed" and "disabled" icon if
     * there is no explicitly set pressed icon.
     *
     * @param defaultIcon the icon used as the default image
     * @beaninfo bound: true
     * attribute: visualUpdate true
     * description: The button's default icon
     * @see #getIcon
     * @see #setPressedIcon
     */
    public void setIcon(Icon defaultIcon)
    {
        if(getIcon() == defaultIcon)
                return;
        super.setIcon(defaultIcon);
        invalidate();
        Container parent = getParent();
        if(parent != null)
            parent.invalidate();
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
        Icon icon = getIcon();
        if(icon == null)
            return getDefaultSize();
        int width = icon.getIconWidth();
        int heitht = icon.getIconHeight();
        return new Dimension(width,heitht);
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
        return getPreferredSize();

    }


}
