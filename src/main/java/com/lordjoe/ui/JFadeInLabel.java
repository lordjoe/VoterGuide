package com.lordjoe.ui;

import com.lordjoe.lang.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JFadeInLabel
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class JFadeInLabel extends JTransperentImage implements Fadeable
{
    public final static JFadeInLabel[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = JFadeInLabel.class;

    public static final double DEFAULT_DEL = 0.02;
    public static final int DEFAULT_FADE_IN_TIME = 3000;
    public static final int DEFAULT_FADE_IN_STEPS = 30;

    private long m_FadeStartTime;
    private int m_FadeInSteps;
    private int m_FadeInTime;
    private boolean m_Fadeing;
    private double m_Del;
    private int m_FadeRepaint;
    private Set m_FadeListeners;

    public JFadeInLabel(ImageIcon img)
    {
        this(img, 1.0);
    }

    public JFadeInLabel(ImageIcon img, double transperancy)
    {
        super(img, transperancy);
        m_FadeInSteps = 30;
        m_FadeInTime = DEFAULT_FADE_IN_TIME;
        m_FadeRepaint = m_FadeInTime / m_FadeInSteps;
        m_FadeListeners = new HashSet();
    }

    public int getFadeInSteps()
    {
        return m_FadeInSteps;
    }

    public void setFadeInSteps(int pFadeInSteps)
    {
        m_FadeInSteps = pFadeInSteps;
        m_FadeRepaint = m_FadeInTime / m_FadeInSteps;
    }

    public int getFadeInTime()
    {
        return m_FadeInTime;
    }

    public void setFadeInTime(int pFadeInTime)
    {
        m_FadeInTime = pFadeInTime;
        m_FadeRepaint = m_FadeInTime / m_FadeInSteps;
    }

    public boolean isFadeing()
    {
        return m_Fadeing;
    }

    public void setFadeing(boolean pFadeing)
    {
        if(pFadeing)
            m_FadeStartTime = System.currentTimeMillis();

        if (m_Fadeing == pFadeing) {
            return;
        }
        m_Fadeing = pFadeing;
        notifyFadeListeners(!m_Fadeing);

    }

    public double getDel()
    {
        return m_Del;
    }

    public void setDel(double pDel)
    {
        m_Del = pDel;
    }

    public int getFadeRepaint()
    {
        return m_FadeRepaint;
    }

    public void setFadeRepaint(int pFadeRepaint)
    {
        m_FadeRepaint = pFadeRepaint;
    }

    public void startFadeIn()
    {
        setTransperency(0);
        setDel(DEFAULT_DEL);
        setFadeing(true);
        repaint(m_FadeRepaint);
    }

    public void startFadeOut()
    {
        setTransperency(1.0);
        setDel(-DEFAULT_DEL);
        setFadeing(true);
        repaint(m_FadeRepaint);
    }

    private double getNextTransperance()
    {
        int del = (int)(System.currentTimeMillis() - m_FadeStartTime);
        double fraction = (double)del / (double)getFadeInTime();
        fraction = Math.min(fraction,1.0);
        fraction = Math.max(fraction,0.0);
        if(m_Del < 0)
            fraction = 1.0 - fraction;
        return fraction;

    }

    public synchronized void paint(Graphics gr)
    {
        if (isFadeing()) {
            double transperance = getNextTransperance();
            if (m_Del > 0 && transperance >= 1.0) {
                setTransperency(1.0);
                setDel(0);
                setFadeing(false);
            }
            if (m_Del < 0 && transperance <= 0.0) {
                setTransperency(0.0);
                setDel(0);
                setFadeing(false);
            }
            if (getDel() != 0)
                setTransperency(transperance);

        }
        super.paint(gr);
        if (isFadeing())
            repaint(m_FadeRepaint);

    }


    protected synchronized void notifyFadeListeners(boolean ended)
    {
        if (m_FadeListeners.isEmpty())
            return;
        FadeListener[] ret = new FadeListener[m_FadeListeners.size()];
        m_FadeListeners.toArray(ret);
        for (int i = 0; i < ret.length; i++) {
            FadeListener fadeListener = ret[i];
            if (ended)
                fadeListener.onFadeEnded(this);
            else
                fadeListener.onFadeStarted(this);

        }
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

    }

    public void setIcon(Icon icon) {
        if(ObjectOps.equalsNullSafe(icon,getIcon()))
            return;
        super.setIcon(icon);

    }


    public synchronized void addFadeListener(FadeListener added)
    {
        m_FadeListeners.add(added);
    }

    public synchronized void removeFadeListener(FadeListener removed)
    {
        m_FadeListeners.remove(removed);
    }


}
