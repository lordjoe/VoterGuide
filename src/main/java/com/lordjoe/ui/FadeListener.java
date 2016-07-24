package com.lordjoe.ui;

/**
 * com.lordjoe.ui.FadeListener
 * managed a Fadeable object
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public interface FadeListener
{
    public static final FadeListener[] EMPTY_ARRAY = {};

    public void onFadeStarted(Fadeable fader);

    public void onFadeEnded(Fadeable fader);
}
