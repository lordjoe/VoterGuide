package com.lordjoe.ui;
import java.awt.*;

/**
 * com.lordjoe.ui.VerticalFlowLayout
 *
 * @author Steve Lewis
 * @date Feb 24, 2006
 */
public class VerticalFlowLayout implements LayoutManager2 
{
  private final int vgap;

  /**
   * VerticalFlowLayout constructor comment.
   */
  public VerticalFlowLayout() {
    this(0);
  }

  /**
   * VerticalFlowLayout constructor comment.
   */
  public VerticalFlowLayout(int vgap) {
    if (vgap < 0) {
      this.vgap = 0;
    } else {
      this.vgap = vgap;
    }
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
        return 0;
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
        return 0;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    public void invalidateLayout(Container target)
    {
    }

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @see java.awt.Component#getMaximumSize
     * @see java.awt.LayoutManager
     */
    public Dimension maximumLayoutSize(Container parent) {
      Insets insets = parent.getInsets();
      int maxWidth = 0;
      int totalHeight = 0;
      int numComponents = parent.getComponentCount();
      for (int i = 0; i < numComponents; ++i) {
        Component c = parent.getComponent(i);
        if (c.isVisible()) {
          Dimension cd = c.getMaximumSize();
          maxWidth = Math.max(maxWidth, cd.width);
          totalHeight += cd.height;
        }
      }
      Dimension td = new Dimension(maxWidth + insets.left + insets.right,
                                   totalHeight + insets.top + insets.bottom
                                   + vgap * numComponents);
      return td;
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
    }

    /**
   * addLayoutComponent method comment.
   */
  public void addLayoutComponent(String name, Component comp) {
  }

  /**
   * layoutContainer method comment.
   */
  public void layoutContainer(Container parent) {
    Insets insets = parent.getInsets();
    int w = parent.getSize().width - insets.left - insets.right;
    // int h = parent.size().height - insets.top - insets.bottom;
    int numComponents = parent.getComponentCount();
    if (numComponents == 0) {
      return;
    }
    int y = insets.top;
    int x = insets.left;
    for (int i = 0; i < numComponents; ++i) {
      Component c = parent.getComponent(i);
      if (c.isVisible()) {
        Dimension d = c.getPreferredSize();
        c.setBounds(x, y, w, d.height);
        y += d.height + vgap;
      }
    }
  }

  /**
   * minimumLayoutSize method comment.
   */
  public Dimension minimumLayoutSize(Container parent) {
    Insets insets = parent.getInsets();
    int maxWidth = 0;
    int totalHeight = 0;
    int numComponents = parent.getComponentCount();
    for (int i = 0; i < numComponents; ++i) {
      Component c = parent.getComponent(i);
      if (c.isVisible()) {
        Dimension cd = c.getMinimumSize();
        maxWidth = Math.max(maxWidth, cd.width);
        totalHeight += cd.height;
      }
    }
    Dimension td = new Dimension(maxWidth + insets.left + insets.right,
                                 totalHeight + insets.top + insets.bottom
                                 + vgap * numComponents);
    return td;
  }

  /**
   * preferredLayoutSize method comment.
   */
  public Dimension preferredLayoutSize(Container parent) {
      Insets insets = parent.getInsets();
    int maxWidth = 0;
    int totalHeight = 0;
    int numComponents = parent.getComponentCount();
    for (int i = 0; i < numComponents; ++i) {
      Component c = parent.getComponent(i);
      if (c.isVisible()) {
        Dimension cd = c.getPreferredSize();
        maxWidth = Math.max(maxWidth, cd.width);
        totalHeight += cd.height;
      }
    }
    Dimension td = new Dimension(maxWidth + insets.left + insets.right,
                                 totalHeight + insets.top + insets.bottom
                                 + vgap * numComponents);
    return td;
  }


  /**
   * removeLayoutComponent method comment.
   */
  public void removeLayoutComponent(Component comp) {
  }

}