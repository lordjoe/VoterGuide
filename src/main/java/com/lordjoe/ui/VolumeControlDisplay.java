package com.lordjoe.ui;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * com.lordjoe.ui.VolumeControlDisplay
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public class VolumeControlDisplay extends JPanel implements VolumeChangeListener,
        MouseListener,MouseMotionListener
{
    public static VolumeControlDisplay[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = VolumeControlDisplay.class;
    public static final String RECIEVE_BASE_IMAGE = "Channel/volume_bar.png";
    public static final String  RECIEVE_ACTVE_IMAGE = "Channel/gain-indicator.png";
     
    public static final int OFFSET = 1;
    public static final int REPAINT_DELAY = 30;
    public static final int NOT_DRAGGING = -1;

    private int m_DragStart;
    private int m_VolumeStart;
    private  ImageIcon m_BaseImage;
    private  ImageIcon m_ActiveImage;
    private int m_VolumeLevel;
    private final int m_NumberLevels;
    private final int m_IconWidth;
    private final int m_BaseOffset;
    private final int m_BaseHeightOffset;
    private final AudioControllerDisplay m_ParentDisplay;
    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public VolumeControlDisplay(AudioControllerDisplay parentDisplay)
    {
       this(ResourceImages.getImageResource(RECIEVE_BASE_IMAGE),
        ResourceImages.getImageResource(RECIEVE_ACTVE_IMAGE),parentDisplay);
        m_DragStart = NOT_DRAGGING;
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    protected VolumeControlDisplay(ImageIcon base,ImageIcon active,AudioControllerDisplay parentDisplay)
    {
        m_BaseImage = base;
        m_ActiveImage = active;
        int iconHt = m_ActiveImage.getIconHeight();
        int BarHt = m_BaseImage.getIconHeight();
         m_NumberLevels = (BarHt * iconHt / 2) / iconHt;
        setOpaque(false);
        int iconWidth = m_BaseImage.getIconWidth();
        m_IconWidth = Math.max(iconWidth,m_ActiveImage.getIconWidth());
        m_BaseOffset = (m_IconWidth -  iconWidth) / 2;
        m_BaseHeightOffset = (m_ActiveImage.getIconHeight()) / 2;
        setVolumeLevel(0);
        addMouseListener(this);
        addMouseMotionListener(this);
        m_ParentDisplay = parentDisplay;
    }

    public AudioControllerDisplay getParentDisplay()
    {
        return m_ParentDisplay;
    }

    public int getNumberLevels()
    {
        return m_NumberLevels;
    }

    public int getVolumeLevel()
    {
        return m_VolumeLevel;
    }

    public void setVolumeLevel(int pVolumeLevel)
    {
        if (m_VolumeLevel == pVolumeLevel)
            return;
        m_VolumeLevel = pVolumeLevel;
        repaint(REPAINT_DELAY);
    }

    /**
     * act when the audio volume changed
     *
     * @param value -1 off other values 0..1
     */
    public void onVolumeChange(double value)
    {
        int level = (int) (value * getNumberLevels());
        setVolumeLevel(level);

    }

    protected void paintComponent(Graphics g)
    {
        Dimension dimension = getSize();
        int width = dimension.width;
        int height = dimension.height;
        int activeImageHeight = m_ActiveImage.getIconHeight();
        int imageHeight =  m_BaseImage.getIconHeight() - activeImageHeight;
        g.drawImage(m_BaseImage.getImage(), m_BaseOffset + ((width - m_BaseImage.getIconWidth()) / 2), m_BaseHeightOffset, this);
        int level = getVolumeLevel();
        if (level >= 0) {
            double frac = level / (float) (getNumberLevels() - 1);
            frac = Math.min(1,Math.max(frac,0));
            int top =  (height - 2 * m_BaseOffset) - (int) (frac * imageHeight) - activeImageHeight ;
            g.drawImage(m_ActiveImage.getImage(), 0, top, this);

        }
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(m_IconWidth,
                m_BaseImage.getIconHeight() + m_ActiveImage.getIconHeight());

    }

    /**
     * If the maximum size has been set to a non-<code>null</code> value
     * just returns it.  If the UI delegate's <code>getMaximumSize</code>
     * method returns a non-<code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>maximumSize</code> property
     * @see #setMaximumSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMaximumSize()
    {
        return getPreferredSize();

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e)
    {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        m_DragStart = e.getPoint().y;
        m_VolumeStart = getVolumeLevel();

    }

    protected void resetVolumeLevel(int location)
    {
        int del = m_DragStart - location;
        int height = getSize().height;
        double frac = (double)del / (double)height;
        int newVolume = m_VolumeStart + (int)(getNumberLevels() * frac);
        newVolume = Math.max(0,Math.min(newVolume,getNumberLevels()));
        if(newVolume != getVolumeLevel())  {
           // getParentDisplay().setVolume(newVolume);
            setVolumeLevel(newVolume);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e)
    {
        if(m_DragStart != NOT_DRAGGING)  {
            resetVolumeLevel(e.getPoint().y);
        }
        m_DragStart = NOT_DRAGGING;

    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e)
    {

    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e)
    {
      //  m_DragStart = NOT_DRAGGING;

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p/>
     * Due to platform-dependent Drag&Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&Drop operation.
     */
    public void mouseDragged(MouseEvent e)
    {
        resetVolumeLevel(e.getPoint().y);

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     */
    public void mouseMoved(MouseEvent e)
    {

    }

    /*
   * TEST CODE
    */
//    public static final Random RND = new Random();
//
//    protected void startTest()
//    {
//        Thread setter = new Thread(new VolumeVarier(), "VolumeVarier");
//        setter.setDaemon(true);
//        setter.start();
//    }
//
//    protected void nextVolume()
//    {
//       onVolumeChange(RND.nextDouble());
//    }
//
//    public class VolumeVarier implements Runnable
//    {
//        public void run()
//        {
//            while (true) {
//                nextVolume();
//                ThreadUtilities.waitFor(300);
//            }
//        }
//    }
//
//    public static void main(String[] args)
//    {
//        JFrame jf = new JFrame();
//        JPanel jp = new  JPanel();
//        VolumeControlDisplay vd = new VolumeControlDisplay();
//        jp.add(vd) ;
//        jf.add(jp);
//        jf.setSize(200,300);
//        jf.setVisible(true);
//        vd.startTest();
//    }

}