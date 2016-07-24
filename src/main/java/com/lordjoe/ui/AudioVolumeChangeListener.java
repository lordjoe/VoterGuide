package com.lordjoe.ui;

/**
 * com.lordjoe.ui.AudioVolumeChangeListener
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public interface AudioVolumeChangeListener
{
    public static AudioVolumeChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AudioVolumeChangeListener.class;

    /**
     * act when the audio volume changed
     * @param value  -1 off other values 0..1
     */
    public void onAudioVolumeChange(double value);
}
