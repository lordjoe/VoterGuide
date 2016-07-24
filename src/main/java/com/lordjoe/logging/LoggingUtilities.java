package com.lordjoe.logging;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.logging.LoggingUtilities
 *
 * @author Steve Lewis
 * @date Jun 30, 2008
 */
public abstract class LoggingUtilities
{
    public static LoggingUtilities[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = LoggingUtilities.class;

    private static Map<Class, ListCellRenderer> gCustomRenderers =
            Collections.synchronizedMap(new HashMap<Class, ListCellRenderer>());

    private LoggingUtilities()
    {
    }

    public static ListCellRenderer getCellRenderer(Class type)
    {
        return gCustomRenderers.get(type);
    }

    public static void registerCellRenderer(Class type, ListCellRenderer renderer)
    {
        gCustomRenderers.put(type, renderer);
    }

    /**
     * filter out eveents before time
     *
     * @param after non-null time
     * @return non-null filter
     */
    public static LogDateFilter buildAfterFilter(Calendar after)
    {
        return new LogDateFilter(after, null);
    }

    /**
     * filter out eveents after time
     *
     * @param before non-null time
     * @return non-null filter
     */
    public static LogDateFilter buildBeforeFilter(Calendar before)
    {
        return new LogDateFilter(null, before);
    }

    /**
     * filter only events between times
     *
     * @param after  non-null time
     * @param before non-null time
     * @return non-null filter
     */
    public static LogDateFilter buildTimeFilter(Calendar after, Calendar before)
    {
        return new LogDateFilter(after, before);
    }

    /**
     * Filter based only on time
     */
    private static class LogDateFilter implements ILoggedObjectFilter<Object>
    {
        private final Calendar m_StartDate;
        private final Calendar m_EndDate;

        private LogDateFilter(Calendar pStartDate, Calendar pEndDate)
        {
            m_StartDate = pStartDate;
            m_EndDate = pEndDate;
        }

        /**
         * return true if the data is acceptable
         *
         * @param test non-null test object
         * @return true if acceptable
         */
        public boolean acceptable(LoggedObject<Object> test)
        {
            long testTime = test.getTime().getTimeInMillis();
            if (m_StartDate != null) {
                if (m_StartDate.getTimeInMillis() > testTime)
                    return false;
            }
            if (m_EndDate != null) {
                if (m_EndDate.getTimeInMillis() > testTime)
                    return false;
            }
            return true;
        }
    }

    /**
     * turn a method into a Method into a string
     * @param m  non-null method
     * @return  non-null string
     */
    public static String buildMethodString(Method m)
    {
        if(m == null)
            return null;
        StringBuilder sb = new StringBuilder();
        String classname = ClassUtilities.shortClassName(m.getDeclaringClass());
        sb.append(classname);
        sb.append(":");
        sb.append(m.getName());
        return sb.toString();
    }
}
