package com.lordjoe.ui;

import com.lordjoe.utilities.*;
//import com.nationalinterop.radios.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.awt.event.*;
import java.awt.*;

/**
 * com.lordjoe.ui.AudioControllerDisplay
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public class AudioControllerDisplay<T> extends JPanel implements AudioVolumeChangeListener,
        VolumeChangeListener, MouseListener, Runnable
{
    public static AudioControllerDisplay[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AudioControllerDisplay.class;
    public static final int DEL_VOLUME = 5;
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;
    public static final int INTERVAL_MSEC = 100;
    public static final int DELAY_MSEC = 30;
    public static final String[] POSITIVE_IMAGES = {"Channel/plus-unpressed.png", "Channel/plus-unpressed.png","Channel/plus-pressed.png"};
    public static final String[] NEGATIVE_IMAGES = {  "Channel/minus-unpressed.png", "Channel/minus-unpressed.png","Channel/minus-pressed.png"};

    private final T m_Subject;
    private final AudioVolumeDisplay m_VolumeIndicator;
    private final JIconButton m_RaiseVolume;
    private final JIconButton m_LowerVolume;
    private final VolumeControlDisplay m_VolumeControl;
    private final List<VolumeChangeListener> m_VolumeChangeListeners;
    private int m_Volume;
    private int m_VolumeChange;

    public AudioControllerDisplay(T subject)
    {
        m_VolumeChangeListeners = new CopyOnWriteArrayList<VolumeChangeListener>();
        m_Subject = subject;
        m_VolumeIndicator = new AudioVolumeDisplay(this);
//        if(subject instanceof AbstractRadioChannel)
//            ((AbstractRadioChannel)subject).addAudioVolumeChangeListener(m_VolumeIndicator);
        m_VolumeControl = new VolumeControlDisplay(this);
        addVolumeChangeListener(m_VolumeControl);
        m_RaiseVolume = new JIconButton(subject, IconUtilities.buildIconMap(POSITIVE_IMAGES));
        m_RaiseVolume.addMouseListener(this);
        m_LowerVolume = new JIconButton(subject, IconUtilities.buildIconMap(NEGATIVE_IMAGES));
        m_LowerVolume.addMouseListener(this);
        Thread thr = new Thread(this, "VolumeChangeThread");
        thr.setDaemon(true);
        thr.start();
        initComponents();

    }

    public T getSubject()
    {
        return m_Subject;
    }

    protected void initComponents()
    {
        setLayout(new BorderLayout());
        add(m_RaiseVolume,BorderLayout.NORTH);
        add(m_LowerVolume,BorderLayout.SOUTH);
        JPanel jp  = new JPanel();
        add(m_VolumeControl,BorderLayout.WEST);
        jp.add(m_VolumeIndicator);
        jp.setOpaque(false);
        add(jp,BorderLayout.EAST);
            setOpaque(false);
     }

    public AudioVolumeDisplay getVolumeIndicator()
    {
        return m_VolumeIndicator;
    }

    public VolumeControlDisplay getVolumeControl()
    {
        return m_VolumeControl;
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
        int ButtonWidth = m_RaiseVolume.getPreferredSize().width;
        int controlWidth = m_VolumeControl.getPreferredSize().width +
               m_VolumeIndicator.getPreferredSize().width ;

        return new Dimension(Math.max(ButtonWidth,controlWidth),
                2 * m_RaiseVolume.getPreferredSize().height +
                Math.max( m_VolumeControl.getPreferredSize().height ,
                        m_VolumeIndicator.getPreferredSize().height));
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



    public int getVolume()
    {
        return m_Volume;
    }

    public synchronized void setVolume(int pVolume)
    {
        pVolume = Math.min(MAX_VOLUME,Math.max(pVolume,0));
        if (m_Volume == pVolume)
            return;
        m_Volume = pVolume;
        notifyAll();
        notifyVolumeChangeListeners(m_Volume / (float)MAX_VOLUME);
    }

    public int getVolumeChange()
    {
        return m_VolumeChange;
    }

    public synchronized void setVolumeChange(int pVolumeChange)
    {
        if (m_VolumeChange == pVolumeChange)
            return;
        m_VolumeChange = pVolumeChange;
        notifyAll();
    }

    public void run()
    {
        try {
            while (true) {
                int vol = getVolume();
                int del = getVolumeChange();
                synchronized (this) {
                    while (del == 0)  {
                        wait(INTERVAL_MSEC);
                        del = getVolumeChange();
                    }
                    if (vol <= MIN_VOLUME && del < 0 ||
                            vol >= MAX_VOLUME && del > 0) {
                        wait(INTERVAL_MSEC);
                    }
                }
                setVolume(vol + del);
                ThreadUtilities.waitFor(DELAY_MSEC);
            }
        }
        catch (InterruptedException e) {
            // interrupt means exit
        }
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
        JIconButton source = (JIconButton)e.getSource();
         if (source == m_RaiseVolume)
            setVolumeChange(DEL_VOLUME);
        if (e.getSource() == m_LowerVolume)
            setVolumeChange(-DEL_VOLUME);
        source.setSelected(true);
      }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e)
    {
        JIconButton source = (JIconButton)e.getSource();
         setVolumeChange(0);
         source.setSelected(false);
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
  
    }

    // Add to constructor


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addVolumeChangeListener(VolumeChangeListener added)
    {
        if (!m_VolumeChangeListeners.contains(added))
            m_VolumeChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeVolumeChangeListener(VolumeChangeListener removed)
    {
        while (m_VolumeChangeListeners.contains(removed))
            m_VolumeChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyVolumeChangeListeners(double vol)
    {
        if (m_VolumeChangeListeners.isEmpty())
            return;
        for (VolumeChangeListener listener : m_VolumeChangeListeners) {
            listener.onVolumeChange(vol);
        }
    }


    /**
     * act when the audio volume changed
     *
     * @param value -1 off other values 0..1
     */
    public void onAudioVolumeChange(double value)
    {
        m_VolumeIndicator.onAudioVolumeChange(value);

    }

    /**
     * act when the audio volume changed
     *
     * @param value -1 off other values 0..1
     */
    public void onVolumeChange(double value)
    {
        m_VolumeControl.onVolumeChange(value);
    }

    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        JPanel jp = new  JPanel();
        AudioControllerDisplay vd = new AudioControllerDisplay(null);
        jp.add(vd) ;
        jf.add(jp);
        jf.setSize(200,300);
        jf.setVisible(true);
    }

}
