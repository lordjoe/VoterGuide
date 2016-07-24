package com.lordjoe.ui;

//package com.rickyclarkson.java.awt.layout;
/**
 * com.lordjoe.ui.PercentLayout
 *  Taken and mpdified from http://lavender.cime.net/~ricky/percentlayout.html
 *
 * @author Steve Lewis
 * @date Jan 6, 2006
 */

import java.awt.*;
import java.util.*;


public final class PercentLayout implements LayoutManager2
{
    public static final int FACTOR = 1;
    public static final int MIN_WIDTH = 2;
    public static final int MIN_HEIGHT = 2;

    private final Map m_Constraints;
    private final Map m_NormalizedConstraints;
    private boolean m_Normalized;
    private final PercentConstraint m_MaximumConstraint;
     private Dimension m_LastSize;

    /**
     * used when the limits are indeterminate
     */
    public PercentLayout()
     {
         m_Constraints = new HashMap();
         m_NormalizedConstraints = new HashMap();
         m_MaximumConstraint = null;
     }

    /**
     * used when the limits are known
     * @param maxX
     * @param maxy
     */
    public PercentLayout(int maxX,int maxy)
     {
         m_Constraints = new HashMap();
         m_NormalizedConstraints = new HashMap();
         m_MaximumConstraint = new PercentConstraint(0,0,maxX,maxy);

     }

    public boolean isNormalized()
    {
        return m_Normalized;
    }

    public void setNormalized(boolean pNormalized)
    {
        m_Normalized = pNormalized;
        if(!pNormalized)
            m_LastSize = null;

    }

    protected synchronized void guaranteeNormalized()
    {
        if (isNormalized())
            return;
        buildNormalizedConstraints();
        setNormalized(true);
    }

    protected void buildNormalizedConstraints()
    {
        PercentConstraint[] items = new PercentConstraint[m_Constraints.size()];
        m_Constraints.values().toArray(items);
        PercentConstraint norm = getMaximumConstraint(items);
        Component[] components = new Component[m_Constraints.size()];
        m_Constraints.keySet().toArray(components);
        m_NormalizedConstraints.clear();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            PercentConstraint raw = (PercentConstraint)m_Constraints.get(component);
            PercentConstraint constraint = PercentConstraint.normalize(raw,norm);
            m_NormalizedConstraints.put(component,constraint);
        }

    }

    protected PercentConstraint getMaximumConstraint(PercentConstraint[] pItems)
    {
        if(m_MaximumConstraint != null)
            return  m_MaximumConstraint;
        PercentConstraint norm = PercentConstraint.buildMaximum(pItems);
        return norm;
    }

    public synchronized void addLayoutComponent(Component component, Object constraint)
    {
        if (!(constraint instanceof PercentConstraint))
            throw new IllegalArgumentException
                    (
                            "constraint must be of type " +
                                    "PercentLayout.PercentConstraint"
                    );

        m_Constraints.put(component, constraint);
        setNormalized(false);
    }

    public void addLayoutComponent(String string, Component component)
    {
        throw new UnsupportedOperationException
                ("use addLayoutComponent(Component,Object)");
    }

    public float getLayoutAlignmentX(Container container)
    {
        return 0.5f;
    }

    public float getLayoutAlignmentY(Container container)
    {
        return 0.5f;
    }

    public void invalidateLayout(Container container)
    {
    }

    public synchronized void layoutContainer(Container container)
    {
        Dimension size = container.getSize();
        Insets ins = container.getInsets();
//        if(m_LastSize != null && m_LastSize.equals(size))
//            return;
        m_LastSize = size;
        guaranteeNormalized();
        Component[] components = container.getComponents();

        int containerWidth = container.getWidth() - ins.left - ins.right ;
        int containerHeight = container.getHeight() - ins.top - ins.bottom;

        for (int a = 0; a < components.length; a++) {
            Component component = components[a];
            PercentConstraint constraint =
                    (PercentConstraint) m_NormalizedConstraints.get(component);

            if(component.getClass().getName().equals("com.lordjoe.ui.general.JSizeableProgressBar"))
                 component = components[a]; // break here


            if (constraint == null)
                continue;

            double left = constraint.getLeft();
            int x = (int) (containerWidth * left / FACTOR);
            double top = constraint.getTop();
            int y = (int) (containerHeight * top / FACTOR);
            double cwidth = constraint.getWidth();
            int width =
                    (int)
                            (containerWidth * cwidth / FACTOR);

            double cheight = constraint.getHeight();
            int height =(int)(containerHeight * cheight / FACTOR);

            String name = component.getClass().getName();
            if(name.endsWith("ChipTestMonitorPanel"))
                height =(int)(containerHeight * cheight / FACTOR); // break here
            if(name.endsWith("ChipStatusSummaryPanel"))
                height =(int)(containerHeight * cheight / FACTOR); // break here

            component.setLocation(x + ins.left, y + ins.top);
            width = Math.max(width,MIN_WIDTH);
            height = Math.max(height,MIN_HEIGHT);
            component.setSize(width, height);
        }
    }

    public synchronized Dimension maximumLayoutSize(Container container)
    {
        guaranteeNormalized();
        Component[] components = container.getComponents();

        double maxWidth = 0,maxHeight = 0;

        for (int a = 0; a < components.length; a++) {
            PercentConstraint constraint =
                    (PercentConstraint) m_NormalizedConstraints.get(components[a]);

            if (constraint == null)
                continue;

            Dimension currentMax = components[a].getMaximumSize();

            /*
                * A component 50 pixels wide, taking up 5% of the
                * screen would need 50*20=1000 pixels.
                * Or 50*100/5.*/
            double currentMaxWidth =
                    currentMax.width * FACTOR / constraint.getWidth();

            double currentMaxHeight =
                    currentMax.height * FACTOR / constraint.getHeight();

            maxWidth = Math.max(maxWidth, currentMaxWidth);
            maxHeight = Math.max(maxHeight, currentMaxHeight);
        }

        return new Dimension((int) maxWidth, (int) maxHeight);
    }

    public synchronized Dimension minimumLayoutSize(Container container)
    {
        guaranteeNormalized();
        Component[] components = container.getComponents();

        double maxWidth = 0,maxHeight = 0;

        for (int a = 0; a < components.length; a++) {
            PercentConstraint constraint =
                    (PercentConstraint) m_NormalizedConstraints.get(components[a]);

            if (constraint == null)
                continue;

            Dimension currentMax = components[a].getMinimumSize();

            double currentMaxWidth =
                    currentMax.width * FACTOR / constraint.getWidth();

            double currentMaxHeight =
                    currentMax.height * FACTOR / constraint.getHeight();

            maxWidth = Math.max(maxWidth, currentMaxWidth);
            maxHeight = Math.max(maxHeight, currentMaxHeight);
        }

        return new Dimension((int) maxWidth, (int) maxHeight);
    }

    public synchronized Dimension preferredLayoutSize(Container container)
    {
        guaranteeNormalized();
        Component[] components = container.getComponents();

        double maxWidth = 0,maxHeight = 0;

        for (int a = 0; a < components.length; a++) {
            PercentConstraint constraint =
                    (PercentConstraint) m_NormalizedConstraints.get(components[a]);

            if (constraint == null)
                continue;

            Dimension currentMax = components[a].getPreferredSize();

            double currentMaxWidth =
                    currentMax.width * FACTOR / constraint.getWidth();

            double currentMaxHeight =
                    currentMax.height * FACTOR / constraint.getHeight();

            maxWidth = Math.max(maxWidth, currentMaxWidth);
            maxHeight = Math.max(maxHeight, currentMaxHeight);
        }

        return new Dimension((int) maxWidth, (int) maxHeight);
    }

    public synchronized void removeLayoutComponent(Component component)
    {
        m_Constraints.remove(component);
        setNormalized(false);
     }
}
