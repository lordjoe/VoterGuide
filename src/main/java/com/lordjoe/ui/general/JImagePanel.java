package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.JImagePanel
 * a panel where the background is an image
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public class JImagePanel extends JPanel {
    public static JImagePanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JImagePanel.class;

    private ImageIcon m_Image;

    public JImagePanel(ImageIcon icn) {
        setImage(icn);
    }

    public ImageIcon getImage() {
        return m_Image;
    }

    public void setImage(ImageIcon pImage) {
        m_Image = pImage;
        if(m_Image != null)
            setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
         super.paintComponent(g);
        ImageIcon icn = getImage();
        if (icn != null) {
            Dimension d = getSize();
            Image img = icn.getImage();
            g.drawImage(img,0,0, d.width, d.height, this);
        }
    }

    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
    }
}
