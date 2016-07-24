package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.util.*;
import java.util.List;
import java.awt.*;

/**
 * com.lordjoe.utilities.StrokeRecord
 *
 * @author Steve Lewis
 * @date Feb 27, 2006
 */
public class StrokeRecord implements ITagHandler
{
    public static final String TAG_NAME = "StrokeRecord";
    private final long m_StartTime;
    private final List m_Entries;

    public StrokeRecord()
      {
          this(System.currentTimeMillis());
      }
    public StrokeRecord(long start)
      {
          m_Entries = new ArrayList();
          m_StartTime = start;
      }

    public boolean isEmpty()
    {
        return m_Entries.isEmpty();
    }

    public void filterStrokes()
    {
        if (m_Entries.isEmpty())
            return;
        OneStroke[] Strokes = getStrokes();
        m_Entries.clear();
        OneStroke lastStroke = Strokes[0];
        m_Entries.add(lastStroke);
        for (int i = 1; i < Strokes.length; i++) {
            OneStroke stroke = Strokes[i];
            if (distance(lastStroke, stroke)) {
                lastStroke = stroke;
                m_Entries.add(lastStroke);
            }
        }
    }

    public static boolean distance(OneStroke s1, OneStroke s2)
    {
        Point p1 = s1.getXY();
        Point p2 = s2.getXY();
        int delx = Math.abs(p1.x - p2.x);
        int dely = Math.abs(p1.y - p2.y);
        return delx + dely > 6;
    }

    public void fastDraw(Graphics g)
    {
        OneStroke[] Strokes = getStrokes();
        if (Strokes.length == 0)
            return;
        Point start = Strokes[0].getXY();
        for (int i = 1; i < Strokes.length; i++) {
            OneStroke stroke = Strokes[i];
            Point next = Strokes[i].getXY();
            g.drawLine(start.x, start.y, next.x, next.y);
            start = next;
        }
    }

    public boolean animateDraw(Graphics g,long startTime,double timeFactor)
    {
        OneStroke[] Strokes = getStrokes();
        if (Strokes.length == 0)
            return true;
        long now = System.currentTimeMillis();
        long del = (long)(timeFactor * (now - startTime));
        Point start = Strokes[0].getXY();
        for (int i = 1; i < Strokes.length; i++) {
            OneStroke stroke = Strokes[i];
            if(stroke.getAfterStart() > del)
                return false;
            Point next = stroke.getXY();
            g.drawLine(start.x, start.y, next.x, next.y);
            start = next;
        }
        return true;
    }

 
    public void draw(Graphics g)
    {
        OneStroke[] Strokes = getStrokes();
        if (Strokes.length == 0)
            return;
        long now = System.currentTimeMillis();
        Point start = Strokes[0].getXY();
        for (int i = 1; i < Strokes.length; i++) {
            OneStroke stroke = Strokes[i];
             Point next = stroke.getXY();
            g.drawLine(start.x, start.y, next.x, next.y);
            start = next;
        }
    }

    public long getStartTime()
    {
        return m_StartTime;
    }

    public void addStroke(Point loc)
    {
        m_Entries.add(new OneStroke(this, loc));
    }

    public void addStroke(long start, int x, int y)
    {
        m_Entries.add(new OneStroke(this, start, x, y));
    }

    public OneStroke[] getStrokes()
    {
        OneStroke[] ret = new OneStroke[m_Entries.size()];
        m_Entries.toArray(ret);
        return ret;
    }

    public String getXML()
    {
        StringBuffer sb = new StringBuffer();
        appendXML(sb, 0);
        return sb.toString();
    }

    public void appendXML(StringBuffer sb, int indent)
    {
        sb.append(Util.indentString(indent));
        sb.append("<" + TAG_NAME + " start=\"" + getStartTime() + "\" />\n");
        OneStroke[] strokes = getStrokes();
        for (int i = 0; i < strokes.length; i++) {
            OneStroke stroke = strokes[i];
            stroke.appendXML(sb, indent + 1);
        }
        sb.append(Util.indentString(indent));
        sb.append("</" + TAG_NAME + ">\n");
    }

    /**
     * This returns the object which will handle the tag - the handler
     * may return itself or create a sub-object to manage the tag
     *
     * @param TagName    non-null name of the tag
     * @param attributes non-null array of name-value pairs
     * @return possibly null handler
     */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        if (TagName.equals(OneStroke.TAG_NAME)) {
            int time = XMLUtil.handleRequiredNameValueInt("time", attributes);
            int x = XMLUtil.handleRequiredNameValueInt("x", attributes);
            int y = XMLUtil.handleRequiredNameValueInt("y", attributes);
            addStroke(time, x, y);
        }
        return this;
    }

}
