package com.lordjoe.utilities;

import java.awt.*;

/**
 * com.lordjoe.utilities.StrokeNormalization
 *
 * @author Steve Lewis
 * @date Apr 14, 2006
 */
public class StrokeNormalization
{
    public final static StrokeNormalization[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = StrokeNormalization.class;

    public static final int MIN_X = 0;
    public static final int MIN_Y = 1;
    public static final int MAX_X = 2;
    public static final int MAX_Y = 3;

    private final int m_XOffset;
    private final int m_YOffset;
    private final double m_XFactor;
    private final double m_YFactor;

    public StrokeNormalization(int left, int top, int right, int bottom,int[] limits)
    {
        m_XOffset = left - limits[MIN_X];
        m_YOffset = top - limits[MIN_Y];
        m_XFactor = (double)(right - left) / (double)( limits[MAX_X] - limits[MIN_X]);
        m_YFactor = (double)(bottom - top) / (double)( limits[MAX_Y] - limits[MIN_Y]);
    }

    public void normalize(Point p)
    {
        p.x = doNormalize(p.x,m_XOffset,m_XFactor);
        p.y = doNormalize(p.y,m_YOffset,m_YFactor);
    }

    protected int doNormalize(int value, int offset,double factor)
    {
        value += offset;
        return (int)(value * factor);
    }


}
