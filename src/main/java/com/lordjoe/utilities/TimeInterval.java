package com.lordjoe.utilities;

import java.util.*;
import java.util.Date;

/**
 * Object representing an interval in time
 * @author Steve Lewis
 */
public class TimeInterval implements java.io.Serializable, java.lang.Comparable {
    private Calendar m_start;
    private Calendar m_end;

    public TimeInterval() {
    }

    public TimeInterval(Calendar start, Calendar end) {
        this();
        m_start = start;
        m_end = end;
    }

    public TimeInterval(Date start, Date end) {
        this();
        m_start = new GregorianCalendar();
        m_start.setTime(start);
        m_end = new GregorianCalendar();
        m_end.setTime(end);
    }

    public Calendar getStart() {
        return (m_start);
    }

    public Date getStartDate() {
        return (m_start.getTime());
    }

    public long getStartTime() {
        return (m_start.getTime().getTime());
    }

    public Calendar getEnd() {
        return (m_end);
    }

    public Date getEndDate() {
        return (m_end.getTime());
    }

    public long getEndTime() {
        return (m_end.getTime().getTime());
    }

    public TimeDuration getDuration() {
        return (new TimeDuration(getDurationMillis()));
    }

    public long getDurationMillis() {
        long end = getEndTime();
        long start = getStartTime();
        long ret = end - start;
        return (ret);
    }

    public boolean equals(Object Test) {
        if (Test == null) return (false);
        if (Test == this) return (true);
        if (Test.getClass() != this.getClass()) return (false);
        TimeInterval realTest = (TimeInterval) Test;
        if (realTest.getStartTime() != getStartTime())
            return (false);
        return (realTest.getEndTime() == getEndTime());
    }

    public int hashCode() {
        return (m_start.hashCode() ^ m_end.hashCode());
    }

    /**
     * Compares two TimeInterval numerically.
     */
    public int compareTo(TimeInterval him) {
        long myTest = getStartTime();
        long hisTest = him.getStartTime();
        if (myTest != hisTest)
            return (myTest < hisTest ? -1 : 1);
        myTest = getEndTime();
        hisTest = him.getEndTime();
        if (myTest != hisTest)
            return (myTest < hisTest ? -1 : 1);
        return (0);
    }

    public int compareTo(Object o) {
        return compareTo((TimeInterval) o);
    }
}
