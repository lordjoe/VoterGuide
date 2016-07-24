package com.lordjoe.utilities;

import java.util.TimerTask;
import java.util.Timer;

/**
 * encapsulated some details of running a periodic task
 * com.lordjoe.Utilities.PeriodicTask
 * @2uthor Steve Lewis
 * @date Jul 24, 2003
 */
public abstract class PeriodicTask extends TimerTask
{
    public static Class THIS_CLASS = PeriodicTask.class;
    public static final PeriodicTask[] EMPTY_ARRAY = {};

    private Timer m_Timer;
    private long m_Period;
    private long m_StartDelay = 0;
    private boolean m_Started;

    public PeriodicTask()
     {
         this(new Timer());
     }
    public PeriodicTask(Timer theTimer)
     {
         setTimer(theTimer);
     }

    public Timer getTimer()
    {
        return m_Timer;
    }

    public void setTimer(Timer timer)
    {
        m_Timer = timer;
    }

    public long getPeriod()
    {
        return m_Period;
    }

    public void setPeriod(long period)
    {
        m_Period = period;
    }

    public long getStartDelay()
    {
        return m_StartDelay;
    }

    public void setStartDelay(long startDelay)
    {
        m_StartDelay = startDelay;
    }

    public synchronized boolean isStarted()
    {
        return m_Started;
    }

    public synchronized void setStarted(boolean started)
    {
        m_Started = started;
    }

    public void start()
    {
         getTimer().scheduleAtFixedRate(this, getStartDelay(), getPeriod());
    }

}

