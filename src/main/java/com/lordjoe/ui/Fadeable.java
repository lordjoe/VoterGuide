package com.lordjoe.ui;

/**
 * com.lordjoe.ui.Fadeable
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public interface Fadeable
{
    public static final Fadeable[] EMPTY_ARRAY = {};

    public void startFadeIn();

    public void startFadeOut();

    public double getTransperancy();

    public void addFadeListener(FadeListener added);

    public void removeFadeListener(FadeListener removed);

}
