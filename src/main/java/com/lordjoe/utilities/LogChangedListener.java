package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.LogChangedListener
 * interface to changed it an ILogger
 * @author slewis
 * @date Jan 17, 2005
 */
public interface LogChangedListener
{
    public static final Class THIS_CLASS = LogChangedListener.class;
    public static final LogChangedListener EMPTY_ARRAY[] = {};

    /**
     * do whatever is called for when a command is sent
     * @param evt non-null event
     */
    public void onLogChanged(LogChangedEvent evt);
}