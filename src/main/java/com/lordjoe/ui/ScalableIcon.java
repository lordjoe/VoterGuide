package com.lordjoe.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;

/**
 *    See http://forum.java.sun.com/thread.jspa?threadID=5214259&tstart=75
 */
public class ScalableIcon implements Icon {

    private final int w,h;
    private final double scalex,scaley;
    private final Icon base;

    public ScalableIcon(Icon icon,int width,int height) {
        if (icon instanceof ScalableIcon)
            base=((ScalableIcon) icon).base;
        else
            base=icon;
        w=width;
        h=height;
        scalex=w/(double)base.getIconWidth();
        scaley=h/(double)base.getIconHeight();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2=(Graphics2D)g.create(x,y,w,h);
        g2.scale(scalex,scaley);
        base.paintIcon(c,g2,0,0);
        g2.dispose();
    }

    public int getIconWidth() {
        return w;
    }

    public int getIconHeight() {
        return h;
    }
}
