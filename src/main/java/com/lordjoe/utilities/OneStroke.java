package com.lordjoe.utilities;

import java.awt.*;

/**
 * com.lordjoe.utilities.OneStroke
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class OneStroke
{

    public static final String TAG_NAME = "OneStroke";
    private final long m_AfterStart;
    private final Point m_XY;
    private StrokeRecord m_strokeRecord;

    public OneStroke(StrokeRecord pStrokeRecord, Point xy)
      {
          m_strokeRecord = pStrokeRecord;
          m_XY = xy;
          m_AfterStart = System.currentTimeMillis() - m_strokeRecord.getStartTime();
      }
    public OneStroke(StrokeRecord pStrokeRecord, long strart,int x,int y)
      {
          m_strokeRecord = pStrokeRecord;
          m_XY = new Point(x,y);
          m_AfterStart = strart;
      }

    public long getAfterStart()
    {
        return m_AfterStart;
    }

    protected void normalize(StrokeNormalization norm)
    {
        norm.normalize(m_XY);
        
    }

    public Point getXY()
    {
        return m_XY;
    }

    public void appendXML(StringBuffer sb, int indent)
    {
        sb.append(Util.indentString(indent));
        Point p = getXY();
        sb.append(
                "<" + TAG_NAME + " time=\"" + getAfterStart() + "\" x=\"" + p.x + "\" y=\"" + p.y + "\" />\n");
    }

}
