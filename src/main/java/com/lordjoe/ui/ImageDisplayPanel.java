package com.lordjoe.ui;


import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.ImageDisplayPanel
 * A panel for displaying a zoomable image or imagesource
 *
 * @author Steve Lewis
 * @date Sep 22, 2005
 */
public class ImageDisplayPanel extends JPanel
{
    private final IImageSource m_Holder;
    private Dimension m_PreferredSize;

    public ImageDisplayPanel(Image img)
    {
        this(new ImageHolder(img));
     }

    public ImageDisplayPanel(IImageSource img)
    {
        m_Holder = img;
        int ht = getImage().getHeight(this);
         int wd = getImage().getWidth(this);
         m_PreferredSize = new Dimension(wd, ht);
         super.setPreferredSize(m_PreferredSize);
    }

    public void setPreferredSize(int wd, int ht)
    {
        m_PreferredSize = new Dimension(wd, ht);
        super.setPreferredSize(m_PreferredSize);

    }

    public Dimension getPreferredSize()
    {
        if (m_PreferredSize == null)
            return new Dimension(100, 100);
        return m_PreferredSize;
    }

    public Image getImage()
    {
        return getHolder().getImage();
    }

    public IImageSource getHolder()
    {
        return m_Holder;
    }

    public void paint(Graphics g)
    {
        Dimension size = getSize();
        g.drawImage(getImage(), 0, 0, size.width, size.height, this);
    }

    /**
     * return trur if the component is larger than the image
     *
     * @return as above
     */
    public boolean isZoomInSensible()
    {
        Image image = getImage();
        int ht = image.getHeight(this);
        int wd = image.getWidth(this);
        Dimension size = getSize();
        if (size.width > wd)
            return true;
        if (size.height > ht)
            return true;
        return false;
    }
}
