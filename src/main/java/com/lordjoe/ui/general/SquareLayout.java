package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.SquareLayout
 *   holds a simgle component which is quaranteed to me square
 *  height == width
 * @author Steve Lewis
 * @date Aug 8, 2006
 */
public class SquareLayout extends AspectLayout
{
    public final static SquareLayout[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = SquareLayout.class;


    public SquareLayout()
    {
       super(1.0);
    }
//    /**
//     * If the layout manager uses a per-component string,
//     * adds the component <code>comp</code> to the layout,
//     * associating it
//     * with the string specified by <code>name</code>.
//     *
//     * @param name the string to be associated with the component
//     * @param comp the component to be added
//     */
//    public void addLayoutComponent(String name, Component comp)
//    {
//    }
//
//    /**
//     * Removes the specified component from the layout.
//     *
//     * @param comp the component to be removed
//     */
//    public void removeLayoutComponent(Component comp)
//    {
//     }
//
//    /**
//     * Calculates the preferred size dimensions for the specified
//     * container, given the components it contains.
//     *
//     * @param parent the container to be laid out
//     * @see #minimumLayoutSize
//     */
//    public Dimension preferredLayoutSize(Container parent)
//    {
//        Dimension dimension = getDesiredDimension(parent);
//        return dimension;
//    }
//
//    /**
//     * Calculates the minimum size dimensions for the specified
//     * container, given the components it contains.
//     *
//     * @param parent the component to be laid out
//     * @see #preferredLayoutSize
//     */
//    public Dimension minimumLayoutSize(Container parent)
//    {
//        Dimension dimension = getDesiredDimension(parent);
//        return dimension;
//    }
//
//    private Dimension getDesiredDimension(Container parent)
//    {
//        Rectangle r = parent.getBounds();
//        Insets ins = parent.getInsets();
//        int componentWidth = r.width - ins.left - ins.right;
//        int componentHeight = r.height - ins.top - ins.bottom;
//        int dim = Math.min(componentWidth,
//                componentHeight);
//
//        int width = componentWidth;
//        int height = componentWidth;
//        int left = ins.left;
//        int top = ins.top;
//        if(width > dim) {
//            left = (width - dim) / 2;
//            width = dim;
//        }
//        if(height > dim) {
//            top = (height - dim) / 2;
//            height = dim;
//        }
//        Dimension dimension = new Dimension(width, height);
//        return dimension;
//    }
//
//    /**
//     * Lays out the specified container.
//     *
//     * @param parent the container to be laid out
//     */
//    public void layoutContainer(Container parent)
//    {
//        Component[] components = parent.getComponents();
//        if(components.length == 0)
//            return;
//         Rectangle r = parent.getBounds();
//        Insets ins = parent.getInsets();
//        int componentWidth = r.width - ins.left - ins.right;
//        int componentHeight = r.height - ins.top - ins.bottom;
//        int dim = Math.min(componentWidth,
//                componentHeight);
//
//        int width = componentWidth;
//        int height = componentHeight;
//        int left = ins.left;
//        int top = ins.top;
//        if(width > dim) {
//            left = (width - dim) / 2;
//            width = dim;
//        }
//        if(height > dim) {
//            top = (height - dim) / 2;
//            height = dim;
//        }
//        Component component = components[0];
//        component.setSize(width, height);
//        component.setLocation(left, top);
//    }
}
