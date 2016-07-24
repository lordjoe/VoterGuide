/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 15, 2002
 * Time: 10:54:46 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.io.*;
import java.util.*;

/**
 *  Properties which are based one a file and will update when the file does
 * @author Steve Lewis
 */
public class WatchedProperties {
    public static final WatchedProperties[] EMPTY_ARRAY = {};
    private static IIntegerValue DEFAULT_INTERVAL = IntegerValueFactory.buildValue((int)(5 * TimeDuration.ONE_MINUTE));
    private static List gValues = new ArrayList();
    private static Thread gThread;
    protected static synchronized  void addWatch(WatchedProperties me) 
    {
        if(gThread  == null) {
            gThread = new Thread(new PropertyWatcher(),"WatchedProperties Checker");
            gThread.setDaemon(true);
            gThread.start();
        }
        gValues.add(me);
    }
    protected static synchronized  void dropWatch(WatchedProperties me) 
    {
        gValues.remove(me);
    }

 
    
    private WatchedFile m_source;
    private Properties m_props;
    private List m_Listeners;

    public WatchedProperties() {
        addWatch(this);
        m_Listeners = new ArrayList();
    }
    

    public void addWatchedPropertiesListener(WatchedPropertiesListener in)
    {
        synchronized(m_Listeners)    {
             m_Listeners.add(in);
        }
    }

    public void removeWatchedPropertiesListener(WatchedPropertiesListener in)
     {
        synchronized(m_Listeners)    {
             m_Listeners.remove(in);
        }
     }



    public void setSource(String in) {
        File theFile = new File(in);
        if (!theFile.exists() || !theFile.canRead())
            throw new IllegalArgumentException("File '" + in + "' does not exits or cannot be read");
        WatchedFile source = new WatchedFile(theFile);
        source.setIntervalTime(DEFAULT_INTERVAL);
        setSource(source);
    }

    public void setSource(WatchedFile in) {
        m_source = in;
        load();
    }


    public WatchedFile getSource() {
        return(m_source);
    }

    public int size()
    {
        if(m_props == null)
            return(0);
        return(m_props.size());
    }
    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings.
     */
    public boolean isEmpty()
    {
        if(m_props == null)
            return(true);
        return(m_props.isEmpty());
    }
    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *
     * @throws ClassCastException if the key is of an inappropriate type for
     * 		  this map.
     * @throws NullPointerException if the key is <tt>null</tt> and this map
     *            does not not permit <tt>null</tt> keys.
     */
    public boolean containsKey(Object key)
     {
        if(m_props == null)
            return(false);
        return(m_props.containsKey( key));
    }
    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * @param value value whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value.
     */
    public boolean containsValue(Object value)
    {
        if(m_props == null)
            return(false);
        return(m_props.containsValue( value));
    }


    // Views

    /**
     * Returns a set view of the keys contained in this map.  The set is
     * backed by the map, so changes to the map are reflected in the set, and
     * vice-versa.  If the map is modified while an iteration over the set is
     * in progress, the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding mapping from
     * the map, via the <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt> <tt>retainAll</tt>, and <tt>clear</tt> operations.
     * It does not support the add or <tt>addAll</tt> operations.
     *
     * @return a set view of the keys contained in this map.
     */
    public Set keySet()
    {
        if(m_props == null)
            return(Util.EMPTY_SET);
        return (m_props.keySet());
    }
    /**
     * Returns a collection view of the values contained in this map.  The
     * collection is backed by the map, so changes to the map are reflected in
     * the collection, and vice-versa.  If the map is modified while an
     * iteration over the collection is in progress, the results of the
     * iteration are undefined.  The collection supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt> and <tt>clear</tt> operations.
     * It does not support the add or <tt>addAll</tt> operations.
     *
     * @return a collection view of the values contained in this map.
     */
    public Collection values()
    {
        if(m_props == null)
            return(Util.EMPTY_LIST);
        return (m_props.values());
    }

    public String getProperty(String key) {
        if(m_props == null)
            return(null);
        return (m_props.getProperty(key));
    }

    /*
    * bad behavior for read-only class
    public void setProperty(String key, String value) {
        m_props.getProperty(key, value);
    }
    */

    protected void load() {
        Properties newVals = new Properties();
        try {
            newVals.load(new FileInputStream(m_source.getFile()));
            m_props = newVals;
            notifyListeners();
        }
        catch(Exception ex) {
            throw new WrapperException("load WatchedProperties failed ",ex);
        }
    }

    protected void notifyListeners()
      {
         WatchedPropertiesListener[] items = null;
         synchronized(m_Listeners)    {
              items = (WatchedPropertiesListener[])m_Listeners.toArray(WatchedPropertiesListener.EMPTY_ARRAY);
         }
        for (int i = 0; i < items.length; i++) {
            WatchedPropertiesListener item = items[i];
            item.propertiesAreReloaded(this);
        }
      }

    protected void guaranteeCurrent()
    {
        if(m_source != null && !m_source.isCurrent()) {
            load();
            m_source.setCurrent();
         }
    }

    protected void finalize() throws Throwable {
        dropWatch(this);
        super.finalize();
    }

    protected static class PropertyWatcher implements Runnable {
        public void run() {
            WatchedProperties[] values = null;
            while(true) {
                ThreadUtilities.waitFor(2 * TimeDuration.ONE_MINUTE);    // check every 2 minutes
                synchronized (gValues) {
                    values = (WatchedProperties[]) gValues.toArray(EMPTY_ARRAY);
                }
                for (int i = 0; i < values.length; i++) {
                    WatchedProperties value = values[i];
                    value.guaranteeCurrent();
                }
            }

        }
    }

}
