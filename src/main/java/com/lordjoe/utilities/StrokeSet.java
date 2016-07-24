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
public class StrokeSet implements ITagHandler
{
    public static final String TAG_NAME = "StrokeSet";


    private final List m_Entries;
    private double m_TimeFactor;

    public StrokeSet()
    {
        m_Entries = new ArrayList();
        m_TimeFactor = 1;
    }


    public double getTimeFactor()
    {
        return m_TimeFactor;
    }

    public void setTimeFactor(double pTimeFactor)
    {
        m_TimeFactor = pTimeFactor;
    }

    public void clear()
    {
        m_Entries.clear();
    }

    public void addRecord(StrokeRecord loc)
    {
        m_Entries.add(loc);
    }

    public StrokeRecord firstRecord()
    {
        return (StrokeRecord) m_Entries.get(0);
    }


    public StrokeRecord[] getStrokeRecords()
    {
        StrokeRecord[] ret = new StrokeRecord[m_Entries.size()];
        m_Entries.toArray(ret);
        return ret;
    }

    public void normalize(int left, int top, int right, int bottom)
    {
        StrokeNormalization norm = buildNormalization(left,  top,  right,  bottom);
        normalizeStrokeLimits(norm);

    }
    protected StrokeNormalization buildNormalization(int left, int top, int right, int bottom)
    {
        int[] limits = getStrokeLimits();
        return new StrokeNormalization( left,  top,  right,  bottom,limits);
    }

    protected void normalizeStrokeLimits(StrokeNormalization norm)
    {
        StrokeRecord[] records = getStrokeRecords();
       for (int i = 0; i < records.length; i++) {
            StrokeRecord record = records[i];
            OneStroke[] sss = record.getStrokes();
            for (int j = 0; j < sss.length; j++) {
                OneStroke ss = sss[j];
                ss.normalize(norm);
              }
        }
     }

    public int[] getStrokeLimits()
    {
        StrokeRecord[] records = getStrokeRecords();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int i = 0; i < records.length; i++) {
            StrokeRecord record = records[i];
            OneStroke[] sss = record.getStrokes();
            for (int j = 0; j < sss.length; j++) {
                OneStroke ss = sss[j];
                Point xy = ss.getXY();
                int x = xy.x;
                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                int y = xy.y;
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        }
        int[] ret = {minX, minY, maxX, maxY};
        return ret;
    }


    public StrokeRecord animateDraw(Graphics g, long startTime, StrokeRecord sr)
    {
        StrokeRecord[] Strokes = getStrokeRecords();
        if (Strokes.length < 2)
            return null;
        long now = System.currentTimeMillis();
        for (int i = 0; i < Strokes.length; i++) {
            StrokeRecord stroke = Strokes[i];
            if (stroke == sr)
                break;
            stroke.draw(g);
        }
        long del = now - startTime;
        for (int i = 0; i < Strokes.length; i++) {
            if (sr != Strokes[i])
                continue;
            if (sr.animateDraw(g, startTime, getTimeFactor())) {
                if (i < Strokes.length - 1)
                    return Strokes[i + 1];
                else
                    return null;
            }
        }
        return sr;
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
        sb.append("<" + TAG_NAME + ">\n");
        StrokeRecord[] strokes = getStrokeRecords();
        for (int i = 0; i < strokes.length; i++) {
            StrokeRecord stroke = strokes[i];
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
        if (TagName.equals(StrokeRecord.TAG_NAME)) {
            long start = XMLUtil.handleRequiredNameValueLong("start", attributes);
            StrokeRecord sr = new StrokeRecord(start);
            addRecord(sr);
            return sr;
        }
        return this;
    }

}
