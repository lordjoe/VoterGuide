package com.lordjoe.utilities;

import com.lordjoe.lang.*;


/**
 * com.lordjoe.utilities.LogChangedEvent
 *     ToDO add something about the change
 * @author slewis
 * @date Jan 17, 2005
 */
public class LogChangedEvent
{
    public static final Class THIS_CLASS = LogChangedEvent.class;
    public static final LogChangedEvent EMPTY_ARRAY[] = {};

    public static final int AJILE_PORT = -2;

    private final ILoggerObject m_Logger;
    private final int m_Type;
    private final String m_Text;

    public LogChangedEvent(ILoggerObject logger,int type,String text)
    {
       m_Logger = logger;
        m_Type = type;
        m_Text = text;
    }

    public ILoggerObject getLogger()
    {
        return m_Logger;
    }

    public int getType()
    {
        return m_Type;
    }

    public String getText()
    {
        return m_Text;
    }

}
