package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.NDCTooltip
 *  custom tooltip - currently used to track bugs in tootip rendering
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class NDCTooltip extends JToolTip
{
    public final static NDCTooltip[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = NDCTooltip.class;


    public NDCTooltip()
    {
        super();
    }
    /**
     * Invoked by Swing to draw components.
     * Applications should not invoke <code>paint</code> directly,
     * but should instead use the <code>repaint</code> method to
     * schedule the component for redrawing.
     *                                                                                          <p>
     * This method actually delegates the work of painting to three
     * protected methods: <code>paintComponent</code>,
     * <code>paintBorder</code>,
     * and <code>paintChildren</code>.  They're called in the order
     * listed to ensure that children appear on top of component itself.
     * Generally speaking, the component and its children should not
     * paint in the insets area allocated to the border. Subclasses can
     * just override this method, as always.  A subclass that just
     * wants to specialize the UI (look and feel) delegate's
     * <code>paint</code> method should just override
     * <code>paintComponent</code>.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @see #paintComponent
     * @see #paintBorder
     * @see #paintChildren
     * @see #getComponentGraphics
     * @see #repaint
     */
    public void paint(Graphics g)
    {
        super.paint(g);

    }

    /**
     * Paints this component's children.
     * If <code>shouldUseBuffer</code> is true,
     * no component ancestor has a buffer and
     * the component children can use a buffer if they have one.
     * Otherwise, one ancestor has a buffer currently in use and children
     * should not use a buffer to paint.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @see #paint
     * @see java.awt.Container#paint
     */
    protected void paintChildren(Graphics g)
    {
        super.paintChildren(g);

    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     *                                                                                          <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoker super's implementation you must honor the opaque property,
     * that is
     * if this component is opaque, you must completely fill in the background
     * in a non-opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see javax.swing.plaf.ComponentUI
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

    }
}
