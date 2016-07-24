package com.lordjoe.utilities;

import java.util.*;
import java.lang.ref.WeakReference;


/*
 * Map using WeakObjectReferences as Values
 * com.lordjoe.Utilities.WeakValueMap
 * @author smlewis
 * Date: May 1, 2003
 */

public class WeakValueMap extends HashMap
{
    public static final Class THIS_CLASS = WeakValueMap.class;
    public static final WeakValueMap[] EMPTY_ARRAY = {};
    private static  int CLEANUP_INTERVAL = (int)TimeDuration.ONE_MINUTE * 5;

    public static void setCleanUpInterval(int millisec)
    {
        CLEANUP_INTERVAL = millisec;
    }

    private static Map gRegisteredItems = null;

    /**
     * drop any collected objects
     */
    protected static void cleanUp()
    {
        Collection keys = gRegisteredItems.keySet();
        WeakValueMap[] realRefs = new WeakValueMap[keys.size()];
        keys.toArray(realRefs);

        for (int i = 0; i < realRefs.length; i++)
        {
            WeakValueMap realRef = realRefs[i];
            realRef.doCleanUp();
        }
    }

    private static class Cleaner implements Runnable
    {
        public void run()
        {
            ThreadUtilities.waitFor(CLEANUP_INTERVAL);
            try
            {
                cleanUp();
            }
                    // Log and ignore exceptions to keep running
            catch (Exception ex)
            {
                LogUtilities.logError(this, ex);
            }
        }
    }

    private static synchronized void register(WeakValueMap added)
    {
        if (gRegisteredItems == null)
        {
            gRegisteredItems = new WeakHashMap();
            Thread runner = new Thread(new Cleaner(), "WeakValueMap Cleaner");
            runner.start();
        }
        gRegisteredItems.put(added, EMPTY_ARRAY); // use likea set

    }


    public WeakValueMap()
    {
        register(this);
    }
    private WeakValueMap(boolean dontRegister)
    {
    }

    public Object get(Object key)
    {
        WeakReference test = null;
        Object ret = null;
        synchronized (this)
        {
            test = (WeakReference) super.get(key);
        }
        if (test != null)
        {
            ret = test.get();
            if (ret == null)
            {
                synchronized (this)
                {
                    remove(key);
                }
            }
        }
        return (ret);
    }

    public Object put(Object key, Object value)
    {
        WeakReference valref = new WeakReference(value);
        synchronized (this)
        {
            WeakReference oldRef = (WeakReference) super.put(key, valref);
            if (oldRef != null)
                return (oldRef.get());
            return (null);
        }
    }


    public  Object[] getKeys()
    {
        synchronized (this)
        {
            Collection keys = keySet();
            Object[] realRefs = new Object[keys.size()];
            keys.toArray(realRefs);
            return (realRefs);
        }
    }

    /**
     * drop any collected objects
     */
    public void doCleanUp()
    {
        synchronized (this)
        {
            System.out.println("before size " + size());
            Object[] realRefs = getKeys();
            for (int i = 0; i < realRefs.length; i++)
            {
                Object realRef = realRefs[i];
                if (get(realRef) == null) // must be out of scope
                    remove(realRef);
            }
            System.out.println("size " + size());
        }
    }

    //
    // Too hard to make these work cleanly
    public boolean containsValue(Object value)
    {
        throw new UnsupportedOperationException("WeakValue map does not support");
    }

    public Collection values()
    {
        throw new UnsupportedOperationException("WeakValue map does not support");
    }

    public void putAll(Map t)
    {
        throw new UnsupportedOperationException("WeakValue map does not support");
    }


}