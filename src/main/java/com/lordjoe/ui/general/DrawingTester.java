package com.lordjoe.ui.general;


import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.utilities.ThreadUtilities;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.DrawingTester
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DrawingTester
{
    public static final Class THIS_CLASS = DrawingTester.class;
    public static final DrawingTester EMPTY_ARRAY[] = {};
    public static final long REVERSE_INTERVAL = 2000;

    public static class Animate implements Runnable
    {

        private final ImagePanel m_Panel;
         Animate(ImagePanel panel) {
             m_Panel = panel;
          }

        public void run()
        {
            long time = System.currentTimeMillis();
            long reversetime = time + REVERSE_INTERVAL;
            boolean forward = true;
            while(!m_Panel.getTopLevelAncestor().isVisible()) {
                ThreadUtilities.waitFor(500);
            }
            while(m_Panel.getTopLevelAncestor().isVisible()) {
                time = System.currentTimeMillis();
                if(time < reversetime) {
                    reversetime = time + REVERSE_INTERVAL;
                    forward = !forward;
                }
                repaintPanel(forward);
                ThreadUtilities.waitFor(250);
            }
         }

        protected void repaintPanel(boolean dir)
        {
            m_Panel.incrementPhase(0.1);
            m_Panel.repaint(50);
        }
    }

    public static class ImagePanel extends JPanel
    {
        private double phase = 0;
         ImagePanel() {
          }

        public double getPhase()
        {
            return phase;
        }

        public void setPhase(double phase)
         {
             this.phase = phase;
         }

        
        public void incrementPhase(double added)
         {
             this.phase  += added;
             while(phase > 1)
                  phase -= 1;
             while(phase < 0)
                  phase += 1;
          }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D realGraphics = (Graphics2D)g;
            UIUtilities.improveRendering(realGraphics);
            Rectangle r = UIUtilities.getActiveRectangle(this);
       //     r.height = Math.min(r.height,m_Image.getHeightInCells(this) / 2);
        //    r.width = Math.min(r.width,m_Image.getWidthInCells(this) / 2);
         }
    }

    public static void main(String[] args)
    {
        try
        {
            DefaultFrame test = new DefaultFrame();

            ImagePanel cmd = new ImagePanel();
            test.setMainDisplay(cmd);

            test.setSize(500, 400);
            UIUtilities.becomeVisible(test);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
