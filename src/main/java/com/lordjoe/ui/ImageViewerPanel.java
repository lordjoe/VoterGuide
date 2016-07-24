package com.lordjoe.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * com.lordjoe.ui.ImageViewerPanel
 * A panel for displaying a zoomable image or imagesource
 * @author Steve Lewis
 * @date Sep 22, 2005
 */
public class ImageViewerPanel extends JPanel
{
    public static final double ZOOM_IN_FACTOR = 0.6;

    private final ImageDisplayPanel m_ImageDisplay;
    private final JScrollPane m_ViewSurface;
    private final ZoomIn m_ZoomIn;
    private final ZoomOut m_ZoomOut;


    public ImageViewerPanel(ImageIcon img)
    {
        this(new ImageHolder(img.getImage()));
    }
    public ImageViewerPanel(Image img)
    {
        this(new ImageHolder(img));
    }
    public ImageViewerPanel(IImageSource img)
    {
        m_ImageDisplay = new ImageDisplayPanel(img);
        m_ZoomIn = new ZoomIn();
        m_ZoomOut = new ZoomOut();
        m_ImageDisplay.addComponentListener(m_ZoomOut);
        m_ViewSurface = buildDisplay();

    }

    protected JScrollPane buildDisplay()
    {
        setLayout(new BorderLayout());
        buildActions();
        JScrollPane sp = new JScrollPane(m_ImageDisplay);
        add(sp, BorderLayout.CENTER);
        return sp;

    }

    private void buildActions()
    {
        JPanel actions = new JPanel();
        actions.add(new JButton(m_ZoomIn));
        actions.add(new JButton(m_ZoomOut));
        add(actions, BorderLayout.NORTH);
    }


    public class ZoomIn extends AbstractAction
    {
        public ZoomIn()
        {
            String newValue = "Zoom In";
            // Shoould use for internationalization
        //    String newValue = ResourceUtilities.fromEnglishString("Zoom In");
             putValue(Action.NAME, newValue);
        }

        public void actionPerformed(ActionEvent e)
        {
            double factor = 1.0 / ZOOM_IN_FACTOR;
             Dimension d = m_ImageDisplay.getSize();
             int newWidth = (int)(factor * d.width);
             int newHeight = (int)(factor * d.height);

            m_ImageDisplay.setPreferredSize(newWidth,newHeight);
            m_ImageDisplay.setSize(newWidth,newHeight);
         }
    }

    public class ZoomOut extends AbstractAction implements ComponentListener
    {
        public ZoomOut()
        {
            String newValue = "Zoom Out";
          //  String newValue = ResourceUtilities.fromEnglishString("Zoom Out");
            putValue(Action.NAME, newValue);
        }

        public void actionPerformed(ActionEvent e)
        {
            double factor = ZOOM_IN_FACTOR;
            Dimension d = m_ImageDisplay.getSize();
            int newWidth = (int)(factor * d.width);
            int newHeight = (int)(factor * d.height);

            m_ImageDisplay.setPreferredSize(newWidth,newHeight);
            m_ImageDisplay.setSize(newWidth,newHeight);
         }

        public void componentResized(ComponentEvent e)
        {

             setEnabled(isZoomInSensible());
        }
        /**
         * return trur if the component is larger than the image
         *
         * @return as above
         */
        public boolean isZoomInSensible()
        {
            Dimension dim = m_ImageDisplay.getPreferredSize();
            int ht = dim.height;
            int wd = dim.width;
            Dimension size = m_ViewSurface.getSize();
            if (size.width > wd)
                return false;
            if (size.height > ht)
                return false;
            return true;
        }

        public void componentMoved(ComponentEvent e)
        {
         }

        public void componentShown(ComponentEvent e)
        {
        }

        public void componentHidden(ComponentEvent e)
        {
        }
    }
}
