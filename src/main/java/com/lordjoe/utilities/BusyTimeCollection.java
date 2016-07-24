package com.lordjoe.utilities;



/**
 * Object representing a collection of times when
 * a person is busy
 * @author Steve Lewis
 */
public class BusyTimeCollection {
/*    public static final int DEFAULT_SCHEDULING_INTERVAL = 15 * 60 * 1000; // 15 minutes
    private Date m_start;
    private Date m_end;
    private int m_interval;
    private boolean[] m_busy;

    public BusyTimeCollection() {
        m_interval = DEFAULT_SCHEDULING_INTERVAL;
    }

    public BusyTimeCollection(Date start,Date end) {
        this();
        setStart(start);
        setEnd(end);
    }

    protected void buildBusyList() {
        if(getStart() == null || getEnd() == null)
            return;
        long startTime = roundDownToInterval(getStartTime());
        long endTime = roundUpToInterval(getEndTime());
        long diff = (endTime - startTime) / getInterval();
        m_busy = new boolean[(int)diff];
    }


    public long roundDownToInterval(Date date)
    {
        return(roundDownToInterval(date.getTime()));
    }

    public long roundDownToInterval(long date)
    {
        long answer = date / getInterval();
        return(answer * getInterval());
    }

    public long roundUpToInterval(Date date)
    {
        return(roundUpToInterval(date.getTime()));
    }

    public long roundUpToInterval(long date)
    {
        long answer = date / getInterval();
        if((date % interval ) != 0)
            answer++;
        return(answer * getInterval());
    }


    public void addInterval(Date start,Date end)
    {
        long StartTime = start.getTime();
        long EndTime = end.getTime();
        if(EndTime < StartTime)
            throw new IllegalArgumentException("end must be >= start ");
        if(end.getTime() <= getStartTime())
            return; // outside the interval
        if(start.getTime() >= getEndTime())
            return; // outside the interval
        if(StartTime < getStartTime())
            start = getStart();
        if(EndTime > getEndTime())
            end = getEnd();
        validatedAddInterval(start,end);
    }

    public void addInterval(TimeInterval added)
    {
        addInterval(added.m_start,added.m_end);
    }

    public int getNumberIntervals()
    {
        return(m_NumberIntervals);
    }

    public int getNumberPoints()
    {
        return(m_NumberIntervals * 2);
    }

    public Date getStart()
    {
        return(m_start);
    }

    public Date getEnd()
    {
        return(m_end);
    }

    public long getStartTime()
    {
        return(m_start.getTime());
    }

    public long getEndTime()
    {
        return(m_end.getTime());
    }

    protected void validatedAddInterval(Date start,Date end)
    {
        int startIndex  = findTimeIndex(start);
        int endIndex  = findTimeIndex(end);
       // if(startIndex == -1)
    }

    protected void findTimeIndex(Date start)
    {
        int n  = getNumberIntervals();
        if(n == 0)
            return(-1); // we are the first
        findTimeIndex(start.getTime(),n, 2 * n);
    }


    protected void findTimeIndex(long start,int testIndex,int SearchSize)
    {
        if(SearchSize == 0)
            return(testIndex);
        if(start == m_intervals[testIndex])
            return(testIndex);
        if(start < m_intervals[testIndex])
            return(findTimeIndex(start,testIndex - SearchSize / 2, SearchSize / 2));
        else
            return(findTimeIndex(start,testIndex + SearchSize / 2, SearchSize / 2));
    }
    */
}
