package com.lordjoe.ui.general;


import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.DefaultFrame;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.JSizeableLabel
 *
 * @author slewis
 * @date Mar 23, 2005
 */
public class JSizeableLabel extends JLabel implements IFeatureSizeConsumer
{
    public static final Class THIS_CLASS = JSizeableLabel.class;
    public static final JSizeableLabel EMPTY_ARRAY[] = {};

   // private  int m_XDivisions; // ignored
    private  int m_YDivisions;
    private Dimension m_TextSize;
    public JSizeableLabel(String s)
    {
        super(s);
        setOpaque(false);
        setYDivisions(2);
     }

    public Dimension getTextSize()
     {
         if(m_TextSize == null)
              m_TextSize = buildTextSize();
         return m_TextSize;
     }
    protected Dimension buildTextSize()
     {
         Graphics gr = getGraphics();
         String text = getText();
         FontMetrics fm = gr.getFontMetrics();
         Rectangle textRect
                  = fm.getStringBounds(text, gr).getBounds();
         gr.dispose();
         return new Dimension(textRect.width,textRect.height);
     }

    public void setBounds(int x, int y, int width, int height)
     {
         super.setBounds(x, y, width, height);
     }

    /**
       * This should stop vertical resizing
       */
//      public Dimension getMinimumSize() {
//         Dimension ps = super.getPreferredSize();
//         return new Dimension(ps.width / 2,ps.height / 2);
//      }

     /**
       * This should stop vertical resizing
       */
      public Dimension getPreferredSize() {
         Dimension ts = getTextSize();
         Dimension featureSize = UIUtilities.getFeatureSize(this, DefaultFrame.class);
         int height = featureSize.height * getYDivisions();
         int width = (ts.width * height) / ts.height;
         return new Dimension(width,height);
      }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();

    }


    /**
     * set the number of grid divisions we prefer
     * in the X direction
     * @param XDivisions positive number of divisions
     */
    public void setXDivisions(int XDivisions)
    {
        throw new UnsupportedOperationException("XDivisions are ignored");
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
        throw new UnsupportedOperationException("XDivisions are ignored");
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

     /**
       * This should stop vertical resizing
       */
      public Dimension getMaximumSize() {
         Dimension ps = super.getPreferredSize();
          return new Dimension(ps.width * 2,ps.height * 2);
        }

    public void paint(Graphics g)
    {
        // super.paint(g);
        Rectangle rect = UIUtilities.getActiveRectangle(this);
         Graphics2D realG = (Graphics2D) g;
        UIUtilities.improveRendering(realG);
        drawLabel(realG,rect);
    }



    public void drawLabel(Graphics2D realG, Rectangle rect)
    {
        Font oldFont = realG.getFont();

        Font labelFont = UIUtilities.drawRightTextInRect(realG, rect, getText());
        realG.setFont(labelFont);

        realG.setFont(oldFont);

    }
}
