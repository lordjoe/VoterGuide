package com.lordjoe.ui;

import java.awt.*;

/**
 * com.lordjoe.ui.FastDrawUtilities
 *
 * @author Steve Lewis
 * @date Jan 5, 2006
 */
public abstract class FastDrawUtilities
{
    private FastDrawUtilities()
    {
    }

    public static boolean isAncestorFastDraw(Component c)
    {
        return isAncestorOfClass(c, IFastDrawComponent.class);
    }


    public static boolean isAncestorOfClass(Component c, Class test)
    {
        Container parent = c.getParent();
        while (parent != null) {
            if (test.isInstance(parent))
                return true;
            parent = parent.getParent();
        }
        return false;
    }

    /**
     * create an image of the component
     *
     * @param c non-null component
     * @return
     */
    public static Image buildImage(Component c)
    {
        Dimension size = c.getSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();
        Image ret = c.createImage(width, height);
        if (ret != null) {
            Graphics g = ret.getGraphics();
            Color background = c.getBackground();
            if(background == null)
                background = Color.white;
            g.setColor(background);
            g.fillRect(0,0,(int)size.getWidth(),(int)size.getHeight());
            c.paint(g);
        }
        return ret;
    }


}
