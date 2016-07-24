package com.lordjoe.ui.general;

import java.awt.*;

/**
 * com.lordjoe.ui.general.AspectLayout
 *   holds a simgle component which is quaranteed to keep an aspect ratio
 * @author Steve Lewis
 * @date Aug 8, 2006
 */
public class AspectLayout implements LayoutManager {
    public final static AspectLayout[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = AspectLayout.class;

    private final double m_RatioXOverY;
    public AspectLayout(double ratio)
    {
       m_RatioXOverY = ratio;
    }
    /**
     * If the layout manager uses a per-component string,
     * adds the component <code>comp</code> to the layout,
     * associating it
     * with the string specified by <code>name</code>.
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp)
    {
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp)
    {
     }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        Dimension dimension = getDesiredDimension(parent);
        return dimension;
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        Dimension dimension = getDesiredDimension(parent);
        return dimension;
    }


    public double getRatioXOverY() {
        return m_RatioXOverY;
    }

    private Dimension getDesiredDimension(Container parent)
    {
        Rectangle r = parent.getBounds();
        Insets ins = parent.getInsets();
        int componentWidth = r.width - ins.left - ins.right;
        int componentHeight = r.height - ins.top - ins.bottom;
        int width = componentWidth;
        int height = componentHeight;
        int left = ins.left;
        int top = ins.top;
        if(componentWidth * getRatioXOverY() > componentHeight) {
            width =  (int)(componentWidth * getRatioXOverY());
        }
        else {
            height =  (int)(componentHeight / getRatioXOverY());
        }
        Dimension dimension = new Dimension(width, height);
        return dimension;
    }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    public void layoutContainer(Container parent)
    {
        Component[] components = parent.getComponents();
        if(components.length == 0)
            return;
         Rectangle r = parent.getBounds();
        Insets ins = parent.getInsets();
        int componentWidth = r.width - ins.left - ins.right;
        int componentHeight = r.height - ins.top - ins.bottom;
        int dim = Math.min(componentWidth,
                componentHeight);

        int width = componentWidth;
        int height = componentHeight;
        int left = ins.left;
        int top = ins.top;
        if(componentWidth * getRatioXOverY() > componentHeight) {
            width =  (int)(componentWidth * getRatioXOverY());
            left = (componentWidth - width) / 2;
        }
        else {
            height =  (int)(componentHeight / getRatioXOverY());
            height = (componentHeight - height) / 2;
        }
        Component component = components[0];
        component.setSize(width, height);
        component.setLocation(left, top);
    }
}
