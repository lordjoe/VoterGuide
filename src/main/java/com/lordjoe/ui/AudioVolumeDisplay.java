package com.lordjoe.ui;

import com.lordjoe.utilities.*;
//import com.nationalinterop.radios.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * com.lordjoe.ui.AudioVolumeDisplay
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public class AudioVolumeDisplay extends JPanel implements AudioVolumeChangeListener
{
    public static AudioVolumeDisplay[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AudioVolumeDisplay.class;
    public static final String RECEIVE_BASE_IMAGE = "Channel/base_audio_volume_display.png";
    public static final String RECEIVE_ACTVE_IMAGE = "Channel/gain-vu.png";

    public static final String TRANSMIT_BASE_IMAGE = "Channel/transmit_audio_volume_display.png";
    public static final String TRANSMIT_ACTVE_IMAGE = "Channel/transmit_active.png";


    public static final int NUMBER_LEVELS = 6;
    public static final int OFFSET = 1;


    private final AudioControllerDisplay m_Parent;
    private boolean m_Transmit;
    private ImageIcon m_BaseImage;
    private ImageIcon m_ActiveImage;
    private int m_VolumeLevel;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public AudioVolumeDisplay(AudioControllerDisplay parent)
    {
        m_BaseImage = ResourceImages.getImageResource(RECEIVE_BASE_IMAGE);
        m_ActiveImage = ResourceImages.getImageResource(RECEIVE_ACTVE_IMAGE);
        m_Parent = parent;
    }

    public AudioControllerDisplay getParent()
    {
        return m_Parent;
    }


    public boolean isTransmit()
    {
        return m_Transmit;
    }

    public void setTransmit(boolean pTransmit)
    {
        if (m_Transmit == pTransmit)
            return;
        m_Transmit = pTransmit;
        if (m_Transmit) {
            setBaseImage(ResourceImages.getImageResource(TRANSMIT_BASE_IMAGE));
            setActiveImage(ResourceImages.getImageResource(TRANSMIT_ACTVE_IMAGE));
        }
        else {
            setBaseImage(ResourceImages.getImageResource(RECEIVE_BASE_IMAGE));
            setActiveImage(ResourceImages.getImageResource(RECEIVE_ACTVE_IMAGE));
        }
    }

    protected ImageIcon getBaseImage()
    {
        return m_BaseImage;
    }

    protected void setBaseImage(ImageIcon pBaseImage)
    {
        m_BaseImage = pBaseImage;
    }

    protected ImageIcon getActiveImage()
    {
        return m_ActiveImage;
    }

    protected void setActiveImage(ImageIcon pActiveImage)
    {
        m_ActiveImage = pActiveImage;
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
        getParent().repaint(100);
    }


    /**
     * act when the audio volume changed
     *
     * @param value -1 off other values 0..1
     */
    public void onAudioVolumeChange(double value)
    {
        int level = (int) (value * NUMBER_LEVELS);
//        AbstractRadioChannel chan = (AbstractRadioChannel) getParent().getSubject();
//        TransmitState state = chan.getTransmitState();
//        RecieveState rstate = chan.getRecieveState();
//        // only show transmit or recieve volume
//        if (level > 0) {
//            if (isTransmit() && !chan.isPlaying() && state != TransmitState.transmitting)
//                level = 0;
//            if (!isTransmit() && rstate != RecieveState.recieving)
//                level = 0;
//        }
        setVolumeLevel(level);

    }

    protected void paintComponent(Graphics g)
    {
        Rectangle bounds = getBounds();
        Insets insets = getInsets();
        Dimension dimension = getSize();
        int width = dimension.width;
        int height = dimension.height;

        Image baseImg = getBaseImage().getImage();
        Image activeImg = getActiveImage().getImage();
        int drawLeft = insets.left;
        int drawTop = insets.top;
        g.drawImage(baseImg, drawLeft, drawTop, this);
        int level = getVolumeLevel();
        if (level > 0) {
            double frac = level / (float) (NUMBER_LEVELS - 1);
            frac = Math.min(1, Math.max(frac, 0));
            int top = height - (int) (frac * height - 2 * OFFSET) - OFFSET;
            Shape clip = g.getClip();
            g.clipRect(0, top, width, (height - top));
            g.drawImage(activeImg, drawLeft + OFFSET, drawTop + OFFSET, this);
            g.setClip(clip);

        }
    }

    public void setSize(int x, int y)
    {
        super.setSize(x, y);

    }

    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);

    }

    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);

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
        ImageIcon image = getBaseImage();
        return new Dimension(image.getIconWidth(),
                image.getIconHeight());

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


    /*
   * TEST CODE
    */
    public static final Random RND = new Random();

    protected void startTest()
    {
        Thread setter = new Thread(new VolumeVarier(), "VolumeVarier");
        setter.setDaemon(true);
        setter.start();
    }

    protected void nextVolume()
    {
        onAudioVolumeChange(RND.nextDouble());
    }

    public class VolumeVarier implements Runnable
    {
        public void run()
        {
            while (true) {
                nextVolume();
                ThreadUtilities.waitFor(300);
            }
        }
    }


}
