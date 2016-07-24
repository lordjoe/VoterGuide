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
public class StrokePlayerPanel extends JPanel
{
    private final StrokeSet m_CompleteRecords;
    private long m_AnimatedStart;
    private StrokeRecord m_AnimatedRecord;
    private Set<StrokeDrawListener> m_StrokeDrawListeners;

    public StrokePlayerPanel(StrokeSet s)
    {
        m_CompleteRecords = s;
        setOpaque(false);
        setBackground(Color.white);
        m_StrokeDrawListeners = new HashSet<StrokeDrawListener>();
    }


    public void addStrokeDrawListener(StrokeDrawListener added)
    {
        m_StrokeDrawListeners.add(added);
    }
    public void removeStrokeDrawListener(StrokeDrawListener removed)
    {
        m_StrokeDrawListeners.remove(removed);
    }

    public void notifyStrokeDrawListener(boolean isEnd)
    {
       if(m_StrokeDrawListeners.isEmpty())
         return;
        StrokeDrawListener[] listeners = new StrokeDrawListener[m_StrokeDrawListeners.size()];
        m_StrokeDrawListeners.toArray(listeners);
        for (int i = 0; i < listeners.length; i++) {
            StrokeDrawListener listener = listeners[i];
            if(isEnd)
                listener.onStrokeDrawEnd();
            else
                listener.onStrokeDrawStart();
        }
    }

    public StrokePlayerPanel()
    {
        m_CompleteRecords = new StrokeSet();
    }

    public void clear()
    {
        m_CompleteRecords.clear();
        repaint(50);
    }

    public StrokeRecord[] getCompleteRecords()
    {
        return m_CompleteRecords.getStrokeRecords();
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
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
        if(m_CompleteRecords != null)
         m_CompleteRecords.normalize(0,0,width,height);
    }

    public void paint(Graphics gr)
    {
        Graphics2D g = (Graphics2D)gr;
        if (m_AnimatedRecord == null) {
            m_AnimatedRecord = m_CompleteRecords.getStrokeRecords()[0];
            m_AnimatedStart = System.currentTimeMillis();
            notifyStrokeDrawListener(false);
        }
        super.paint(g);
        Dimension size = getSize();
        Color oldColor = g.getColor();
//        g.setColor(Color.white);
//        g.fillRect(0,0,size.width,size.height);
        g.setColor(Color.black);
        StrokeRecord oleReec = m_AnimatedRecord;
        m_AnimatedRecord = m_CompleteRecords.animateDraw(g, m_AnimatedStart, m_AnimatedRecord);
        if (m_AnimatedRecord != oleReec) {
            m_AnimatedStart = System.currentTimeMillis();
        }
        if(m_AnimatedRecord != null)
            repaint(50);
        else
            notifyStrokeDrawListener(true);
        g.setColor(oldColor);
    }

    public static void drawStrokeRecord(Graphics g, StrokeRecord sr)
    {
        sr.fastDraw(g);
    }
}
