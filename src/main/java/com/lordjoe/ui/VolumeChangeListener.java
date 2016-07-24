package com.lordjoe.ui;

/**
 * com.lordjoe.ui.AudioVolumeChangeListener
 *   this is usually implemented by a volume meter
 * 
 * @author Steve Lewis
 * @date May 20, 2008
 */
public interface VolumeChangeListener
{
    public static AudioVolumeChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AudioVolumeChangeListener.class;

    /**
     * act when the audio volume changed
     * @param value  -1 off other values 0..1
     */
    public void onVolumeChange(double value);
}