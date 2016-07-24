package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.StrokeDrawListener
 *
 * @author Steve Lewis
 * @date Apr 13, 2006
 */
public interface StrokeDrawListener
{
    public static final StrokeDrawListener[] EMPTY_ARRAY = {};

    public void onStrokeDrawStart();

    public void onStrokeDrawEnd();
}
