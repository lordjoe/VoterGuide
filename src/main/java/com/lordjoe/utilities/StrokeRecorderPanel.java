package com.lordjoe.utilities;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.utilities.StrokeRecorderPanel
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class StrokeRecorderPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private StrokeRecord m_ActiveRecord;
    private final StrokeSet m_CompleteRecords;
    private boolean m_Animated;
    private long m_AnimatedStart;
    private StrokeRecord m_AnimatedRecord;

    public StrokeRecorderPanel()
    {
        m_CompleteRecords = new StrokeSet();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void clear()
    {
        m_CompleteRecords.clear();
        m_ActiveRecord = null;
        repaint(50);
    }

    public StrokeRecord[] getCompleteRecords()
    {
        return m_CompleteRecords.getStrokeRecords();
    }

    public StrokeRecord getActiveRecord()
    {
        return m_ActiveRecord;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e)
    {
        saveActiveRecord();
        int button = e.getButton();
        if (button == MouseEvent.BUTTON3) {
            System.out.println(getXML());
            m_AnimatedRecord = m_CompleteRecords.firstRecord();
            if (m_AnimatedRecord != null) {
                m_Animated = true;
                m_AnimatedStart = System.currentTimeMillis();
                m_AnimatedRecord = m_CompleteRecords.firstRecord();
            }
            repaint(50);
        }
    }

    public String getXML()
    {
        return m_CompleteRecords.getXML();
    }


    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        m_ActiveRecord = new StrokeRecord();

    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e)
    {
        saveActiveRecord();

    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e)
    {
        if (m_ActiveRecord == null) {
            m_ActiveRecord = new StrokeRecord();
        }

    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e)
    {
        saveActiveRecord();

    }

    private void saveActiveRecord()
    {
        if (m_ActiveRecord != null) {
            m_ActiveRecord.filterStrokes();
            if (!m_ActiveRecord.isEmpty())
                m_CompleteRecords.addRecord(m_ActiveRecord);
            m_ActiveRecord = null;
        }
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     *                                                                                          <p>
     *                                                                                          <p>
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&Drop operation.
     */
    public void mouseDragged(MouseEvent e)
    {
        if (m_ActiveRecord != null) {
            m_ActiveRecord.addStroke(e.getPoint());
            repaint(50);
        }

    }


    public boolean isAnimated()
    {
        return m_Animated;
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     */
    public void mouseMoved(MouseEvent e)
    {

    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(isAnimated()) {
            StrokeRecord oleReec =  m_AnimatedRecord;
          m_AnimatedRecord =  m_CompleteRecords.animateDraw(g,m_AnimatedStart,m_AnimatedRecord);
            if(m_AnimatedRecord == null) {
                 m_Animated = false;
             }
            if(m_AnimatedRecord != oleReec) {
                 m_AnimatedStart = System.currentTimeMillis();
             }
            repaint(50);
          }
        else {
        StrokeRecord[] recs = getCompleteRecords();
        for (int i = 0; i < recs.length; i++) {
            StrokeRecord rec = recs[i];
            drawStrokeRecord(g, rec);
        }
        StrokeRecord active = getActiveRecord();
        if (active != null)
            drawStrokeRecord(g, active);
        }

    }

    public static void drawStrokeRecord(Graphics g, StrokeRecord sr)
    {
        sr.fastDraw(g);
    }
}
