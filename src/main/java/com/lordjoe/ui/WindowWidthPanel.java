package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * com.lordjoe.ui.WindowWidthPanel
 *  panel prefers its frames width
 * @author Steve Lewis
 * @date Feb 11, 2008
 */
public class WindowWidthPanel extends JPanel implements ComponentListener {
    public static ParentWidthPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ParentWidthPanel.class;
    public static final int DEFAULT_WIDTH = 100;
    public static final int INSET_SIZE = 40;

    private JFrame m_ParentFrame;
    private int m_PreferredWidth;

    public WindowWidthPanel() {
        m_PreferredWidth = DEFAULT_WIDTH;
    }


    public int getPreferredWidth() {
        return Math.max(DEFAULT_WIDTH, m_PreferredWidth);
    }

    public void setPreferredWidth(int pPreferredWidth) {
        m_PreferredWidth = pPreferredWidth;
    }

    public synchronized JFrame getParentFrame() {
        if(m_ParentFrame == null)   {
            JFrame fr = (JFrame) UIUtilities.getTopAncestor(this);
            if(fr != null)
                setParentFrame(fr);
        }
        return m_ParentFrame;
    }

    public synchronized void setParentFrame(JFrame pParentFrame) {
        if(m_ParentFrame != null)
            throw new IllegalStateException("cannot reset parent frame"); // ToDo change
        m_ParentFrame = pParentFrame;
        m_ParentFrame.addComponentListener(this);
        sizeFromComponent(m_ParentFrame);
    }


    public void componentResized(ComponentEvent e) {
        Component component1 = e.getComponent();
        sizeFromComponent(component1);

    }

    private void sizeFromComponent(Component pComponent1) {
        Dimension dimension = pComponent1.getSize();
        int preferedWidth = Math.max(DEFAULT_WIDTH,dimension.width - INSET_SIZE);
        setPreferredWidth(preferedWidth);
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    protected int getPreferredHeight()
    {
        Dimension preferredSize = super.getPreferredSize();
        return  preferredSize.height;
    }



    public Dimension getPreferredSize() {
      return new Dimension(getPreferredWidth(),getPreferredHeight());
   }

}