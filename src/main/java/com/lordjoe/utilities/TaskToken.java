package com.lordjoe.utilities;

/**
 * com.lordjoe.Utilities.TaskToken
 * Wrapper for the token returned by a ClockDaemon
 * allows better type safety
 * @2uthor Steve Lewis
 * @date Jul 25, 2003
 */
public class TaskToken
{
    public static Class THIS_CLASS = TaskToken.class;
    public static final TaskToken[] EMPTY_ARRAY = {};

    private final Object m_realToken;

    public TaskToken(Object tok)
    {
        m_realToken = tok;
    }

    public Object getToken() { return m_realToken; }
}

