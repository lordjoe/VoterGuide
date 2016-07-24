package com.lordjoe.runner;

import java.util.*;

/**
 * com.lordjoe.runner.DispatchedCommand
 *
 * @author Steve Lewis
 * @date Mar 11, 2007
 */
public class RecievedResult
{
    public static RecievedResult[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RecievedResult.class;

    private final String m_Result;
    private final Date m_Date;
    public RecievedResult(String result)
    {
        if(result == null)
            throw new IllegalArgumentException("result cannot be null");
        m_Result = result;
        m_Date = new Date();
    }


    public String getResult()
    {
        return m_Result;
    }

    public Date getDate()
    {
        return m_Date;
    }
}
