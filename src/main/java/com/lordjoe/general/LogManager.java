package com.lordjoe.general;

import com.lordjoe.lang.*;

import java.util.*;


/**
 * com.lordjoe.general.LogManager
 * Hold a collection of LOgs - Sinthesis has simi;ar capabilities but
 * this works better for testing
 * @author slewis
 * @date Jun 13, 2005
 */
public class LogManager
{
    public static final Class THIS_CLASS = LogManager.class;
    public static final LogManager EMPTY_ARRAY[] = {};

    public static final Map<String,ILoggerObject> gLoggers =
            new HashMap<String, ILoggerObject>();

    public static ILoggerObject getLog(String id)
    {
       synchronized (gLoggers)
        {
            ILoggerObject ret = gLoggers.get(id);
            if (ret == null)
            {
                ret = new StringLoggerObject(id);
                gLoggers.put(id, ret);
            }
            return ret;
        }
    }
    /**
     * add a log for a specific ID
     * @param id
     * @param log
     */
    public static void setLog(String id,ILoggerObject log)
    {
       synchronized (gLoggers)
        {
            ILoggerObject prev = gLoggers.get(id);
            if (prev != null)
            {
                log.appendText(prev.getText());
            }
            gLoggers.put(id,log);
        }
    }

}
