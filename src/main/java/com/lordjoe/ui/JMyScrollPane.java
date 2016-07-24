package com.lordjoe.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * com.lordjoe.ui.JMyScrollPane
 *   handles background better than a standard JScroll Pane
 *   also supports debugging
 * @author Steve Lewis
 * @date Sep 26, 2008
 */
public class JMyScrollPane extends JScrollPane implements PropertyChangeListener
{
    public static JMyScrollPane[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JMyScrollPane.class;


    private JMyViewport m_ViewPort;
    /**
     * Creates a <code>JScrollPane</code> that displays the
     * contents of the specified
     * component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     *
     * @param view the component to display in the scrollpane's viewport
     * @see #setViewportView
     */
    public JMyScrollPane(Component view)
    {
        super(view);
        view.addPropertyChangeListener("rebuild",this);
    }

    /**
     * Creates a viewport if necessary and then sets its view.  Applications
     * that don't provide the view directly to the <code>JScrollPane</code>
     * constructor
     * should use this method to specify the scrollable child that's going
     * to be displayed in the scrollpane. For example:
     * <pre>
     * JScrollPane scrollpane = new JScrollPane();
     * scrollpane.setViewportView(myBigComponentToScroll);
     * </pre>
     * Applications should not add children directly to the scrollpane.
     *
     * @param view the component to add to the viewport
     * @see #setViewport
     * @see javax.swing.JViewport#setView
     */
    public void setViewportView(Component view)
    {
        super.setViewportView(view);

    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */

    public void propertyChange(PropertyChangeEvent evt)
    {
        if("rebuild".equals(evt.getPropertyName()))
            setViewportView((Component)evt.getSource());

    }

    /**
     * Returns a new <code>JViewport</code> by default.
     * Used to create the
     * viewport (as needed) in <code>setViewportView</code>,
     * <code>setRowHeaderView</code>, and <code>setColumnHeaderView</code>.
     * Subclasses may override this method to return a subclass of
     * <code>JViewport</code>.
     *
     * @return a new <code>JViewport</code>
     */
    @Override
    protected JViewport createViewport()
    {
        m_ViewPort= new JMyViewport(this);
        return m_ViewPort;

    }

    public void setBackground(Color bg)
    {
        super.setBackground(bg);
        getViewport().setBackground(bg);

    }

    /**
     * Moves and resizes this component. The new location of the top-left
     * corner is specified by <code>x</code> and <code>y</code>, and the
     * new size is specified by <code>width</code> and <code>height</code>.
     *
     * @param x      the new <i>x</i>-coordinate of this component
     * @param y      the new <i>y</i>-coordinate of this component
     * @param width  the new <code>width</code> of this component
     * @param height the new <code>height</code> of this
     *               component
     * @see #getBounds
     * @see #setLocation(int, int)
     * @see #setLocation(java.awt.Point)
     * @see #setSize(int, int)
     * @see #setSize(java.awt.Dimension)
     * @since JDK1.1
     */
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);

    }

    /**
     * Resizes this component so that it has width <code>width</code>
     * and height <code>height</code>.
     *
     * @param width  the new width of this component in pixels
     * @param height the new height of this component in pixels
     * @see #getSize
     * @see #setBounds
     * @since JDK1.1
     */
    @Override
    public void setSize(int width, int height)
    {
        super.setSize(width, height);

    }
}
