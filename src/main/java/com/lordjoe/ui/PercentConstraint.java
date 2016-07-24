package com.lordjoe.ui;

/**
 * com.lordjoe.ui.PercentConstraint
 *
 * @see PercentLayout
 * @author Steve Lewis
 * @date Jan 6, 2006
 */
public class PercentConstraint
{
    public static PercentConstraint buildMaximum(PercentConstraint[] items)
    {
        double offsetWidth = 0;
        double offsetHeight = 0;
        double width = 0;
        double height = 0;
        for (int i = 0; i < items.length; i++) {
            PercentConstraint item = items[i];
            double right = item.getRight();
            double bottom = item.getBottom();
            if (right > 0)
                width = Math.max(width, right);
            else
                offsetWidth = Math.max(offsetWidth, -right);
            if (bottom > 0)
                height = Math.max(height, bottom);
            else
                offsetHeight = Math.max(offsetHeight, -bottom);

        }
        if(width > 0)
            width += offsetWidth;
        else
            width = 1.0;
        if(height > 0)
            height += offsetHeight;
        else
            height = 1.0;
        return new PercentConstraint(0, 0, width, height);
    }

    public static PercentConstraint normalize(PercentConstraint raw, PercentConstraint norms)
    {
        double widthFactor = norms.getWidth();
        double heightFactor = norms.getHeight();

        double left = raw.getLeft() / widthFactor;

        double top = raw.getTop() / heightFactor;

        double right = raw.getRight() / widthFactor;
        if (right < 0)
            right = (1.0 + raw.getRight()) / widthFactor;
        
        double bottom = raw.getBottom() / heightFactor;
        if (bottom < 0)
            bottom = (1.0 + raw.getBottom()) / heightFactor;

        return new PercentConstraint(left, top, right, bottom);
    }

    private final double m_Left;
    private final double m_Top;
    private final double m_Right;
    private final double m_Bottom;

    public PercentConstraint
            (double left, double top, double right, double bottom)
    {
        m_Left = left;
        m_Top = top;
        m_Right = right;
        m_Bottom = bottom;
    }


    public double getLeft()
    {
        return m_Left;
    }

    public double getTop()
    {
        return m_Top;
    }

    public double getRight()
    {
        return m_Right;
    }

    public double getBottom()
    {
        return m_Bottom;
    }

    public double getWidth()
    {
        return m_Right - m_Left;
    }

    public double getHeight()
    {
        return m_Bottom - m_Top;
    }
}

