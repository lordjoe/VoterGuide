package com.lordjoe.ui.general;


import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.ui.ResourceImages;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.ImageProcessingTester
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class ImageProcessingTester
{
    public static final Class THIS_CLASS = ImageProcessingTester.class;
    public static final ImageProcessingTester EMPTY_ARRAY[] = {};

    public static final String BACKGROUND_IMAGE = "BrushedSteel2.gif";

    public static class ImagePanel extends JPanel
    {
        private final Image m_Image;
        ImagePanel(String imageName) {
            m_Image = ResourceImages.getImage(imageName).getImage();
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D realGraphics = (Graphics2D)g;
            UIUtilities.improveRendering(realGraphics);
            Rectangle r = UIUtilities.getActiveRectangle(this);
       //     r.height = Math.min(r.height,m_Image.getHeightInCells(this) / 2);
        //    r.width = Math.min(r.width,m_Image.getWidthInCells(this) / 2);
            UIUtilities.drawImageInOval(realGraphics,m_Image,r,true);
          }
    }

    public static void main(String[] args)
    {
        try
        {
            DefaultFrame test = new DefaultFrame();

            ImagePanel cmd = new ImagePanel(BACKGROUND_IMAGE);
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
