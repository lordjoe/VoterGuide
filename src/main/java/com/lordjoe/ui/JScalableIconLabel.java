package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: stevel
 * Date: Oct 1, 2007
 * Time: 3:17:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class JScalableIconLabel extends JLabel
{
    private Icon m_Icon;
    public JScalableIconLabel()
    {

    }
    public JScalableIconLabel(Icon icn)
    {
        this();
        m_Icon = icn;
    }

    public void setRealIcon(Icon icn)
    {
        m_Icon = icn;
        setIcon(new ScalableIcon(m_Icon, getWidth(), getHeight()));
    }

    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if(m_Icon != null)
            setIcon(new ScalableIcon(m_Icon, width, height));
    }

    public void setBounds(Rectangle r) {
        setBounds(r.x,r.y,r.width,r.height);
    }
}
