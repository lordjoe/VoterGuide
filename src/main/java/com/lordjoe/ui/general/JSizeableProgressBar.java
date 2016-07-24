package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.JSizeableProgressBar
 *
 * @author slewis
 * @date Apr 8, 2005
 */
public class JSizeableProgressBar extends JProgressBar implements IFeatureSizeConsumer
{
    public static final Class THIS_CLASS = JSizeableProgressBar.class;
    public static final JSizeableProgressBar EMPTY_ARRAY[] = {};

    private  int m_XDivisions;
    private  int m_YDivisions;
    public JSizeableProgressBar()
    {
    }
    public JSizeableProgressBar(int min, int max)
     {
          super( min,  max);
     }

    public JSizeableProgressBar(int orient, int min, int max)
    {
         super(orient,  min,  max);
    }

    /**
     * set the number of grid divisions we prefer
     * in the X direction
     * @param XDivisions positive number of divisions
     */
    public void setXDivisions(int XDivisions)
    {
        m_XDivisions = XDivisions;
    }

    /**
     * set the number of grid divisions we prefer
     * in the Y direction
     * @param XDivisions positive number of divisions
     */
    public void setYDivisions(int YDivisions)
    {
        m_YDivisions = YDivisions;
    }

    /**
     * get the number of grid divisions we prefer
     * in the X direction
     * @return non-negative number
     */
    public int getXDivisions()
    {
        return m_XDivisions;
    }

    /**
      * get the number of grid divisions we prefer
      * in the Y direction
      * @return non-negative number
      */
    public int getYDivisions()
    {
        return m_YDivisions;
    }

    public Dimension getPreferredSize()
    {
        Dimension ret = super.getPreferredSize();
        int xdiv = getXDivisions();
        int ydiv = getYDivisions();
         if(xdiv == 0 || ydiv == 0)
             return ret;
        Dimension fsize = UIUtilities.getFeatureSize(this);
        ret = new Dimension(fsize.width * xdiv,fsize.height * ydiv);
        return ret;
    }


    public void setBounds(int x, int y, int width, int height)
     {
         super.setBounds(x, y, width, height);
     }
    
    public Dimension getMinimumSize()
     {
         return getPreferredSize();

     }
    public Dimension getMaximumSize()
     {
         return getPreferredSize();

     }
 }
