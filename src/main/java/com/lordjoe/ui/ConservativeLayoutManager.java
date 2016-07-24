package com.lordjoe.ui;

import java.awt.*;

/**
 * com.lordjoe.ui.ConservativeLayoutManager
 * wrapper around a layout manager
 * @author Steve Lewis
 * @date Dec 14, 2005
 */
public class ConservativeLayoutManager implements LayoutManager2
{
    private final LayoutManager m_Manager;
    private final LayoutManager2 m_Manager2;
    private Dimension m_LaidOutSize;
    private Container m_LaidContainer;

    public ConservativeLayoutManager(LayoutManager mgr)
    {
        m_Manager = mgr;
        m_Manager2 = (mgr instanceof LayoutManager2) ?
               (LayoutManager2)mgr :  null;
    }
    public ConservativeLayoutManager(LayoutManager2 mgr)
    {
        m_Manager = mgr;
        m_Manager2 = mgr;
    }

    protected synchronized void forceLayout()
    {
        m_LaidOutSize = null;
        m_LaidContainer = null;
     }

    protected boolean isLayoutNeeded(Container target,Dimension targetSize)
    {
        if(target != m_LaidContainer)
            return true;
        if(m_LaidOutSize == null ||
                !targetSize.equals(m_LaidOutSize))
            return true;
        return false;
    }

    public LayoutManager getManager()
    {
        return m_Manager;
    }

    public LayoutManager2 getManager2()
    {
        return m_Manager2;
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
        forceLayout();
        LayoutManager2 manager2 = getManager2();
        manager2.addLayoutComponent( comp,  constraints);
    }

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @see java.awt.Component#getMaximumSize
     * @see java.awt.LayoutManager
     */
    public Dimension maximumLayoutSize(Container target)
    {
        LayoutManager2 manager2 = getManager2();
        return manager2.maximumLayoutSize(target);
     }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    public float getLayoutAlignmentX(Container target)
    {
        LayoutManager2 manager2 = getManager2();
        return manager2.getLayoutAlignmentX(target);
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    public float getLayoutAlignmentY(Container target)
    {
        LayoutManager2 manager2 = getManager2();
        return manager2.getLayoutAlignmentX(target);
      }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    public void invalidateLayout(Container target)
    {
//        forceLayout();
        LayoutManager2 manager2 = getManager2();
        manager2.invalidateLayout(target);
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
        forceLayout();
        getManager().addLayoutComponent(name,comp);
     }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp)
    {
        forceLayout();
        getManager().removeLayoutComponent(comp);
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
        return getManager().preferredLayoutSize(parent);
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
        return getManager().minimumLayoutSize(parent);
     }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    public synchronized void layoutContainer(Container parent)
    {
        Dimension targetSize = parent.getSize();
        if(!isLayoutNeeded(parent,targetSize))
            return;
        getManager().layoutContainer(parent);
        m_LaidContainer = parent;
        m_LaidOutSize = targetSize;
    }

}
