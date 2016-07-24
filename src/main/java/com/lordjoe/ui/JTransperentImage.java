package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JTransperentImage
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class JTransperentImage extends JLabel
{
    public final static JTransperentImage[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = JTransperentImage.class;


    private double m_Transperency;
    private AlphaComposite m_Composite;
    public JTransperentImage(ImageIcon img)
    {
        this(img,1.0);
    }
    public JTransperentImage(ImageIcon img,double transperancy)
    {
        super(img);
        setTransperency(transperancy);
    }

    public double getTransperancy()
    {
        return m_Transperency;
    }

    public synchronized void setTransperency(double pTransperency)
    {
        pTransperency = Math.max(0.0,Math.min(1.0,pTransperency));
        if(m_Transperency == pTransperency && m_Composite != null)
            return;

        m_Transperency = pTransperency;
        m_Composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)m_Transperency);
        repaint(50);
    }

    public synchronized void paint(Graphics gr)
    {
        Graphics2D realgr = (Graphics2D)gr;
        Composite originalComposite = realgr.getComposite();
        realgr.setComposite(m_Composite);
        Dimension size = getSize();
        Image icon = ((ImageIcon)getIcon()).getImage();
        realgr.drawImage(icon,0,0,size.width,size.height,this);
        realgr.setComposite(originalComposite);
    }


}
