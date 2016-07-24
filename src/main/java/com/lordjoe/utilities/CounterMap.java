package com.lordjoe.utilities;

import java.util.*;


/**
 * Map designed to count the number of times a counter is added or dropped
 * @author Steve Lewis
 */
public class CounterMap
 {
    private final Map m_Properties;

    public CounterMap() {
        m_Properties = new HashMap();
    }

    public synchronized void clear() {
        m_Properties.clear();
    }

    public synchronized void add(String name) {
        add(name,1);
    }

    public synchronized void add(String name,int added ) {
        CountHolder value = (CountHolder) m_Properties.get(name);
        if (value == null) {
            value = new CountHolder(name);
            m_Properties.put(name,value);
        }
        value.addCount(added);
    }

    public synchronized void drop(String name) {
        CountHolder value = (CountHolder) m_Properties.get(name);
        if (value == null) {
             value = new CountHolder(name);
             m_Properties.put(name,value);
         }
         add(name,-1);
    }

    public synchronized void clear(String name) {
        CountHolder value = (CountHolder) m_Properties.get(name);
        if (value == null) {
             value = new CountHolder(name);
             m_Properties.put(name,value);
         }
         value.clearCount();
    }


    public synchronized String[] keys() {
        return (Util.getKeyStrings(m_Properties));
    }

    public synchronized int value(String s) {
        CountHolder val = (CountHolder) m_Properties.get(s);
        if (val != null)
            return (val.getCount());
        return (0);
    }

    public synchronized Set keySet() {
        return (m_Properties.keySet());
    }

    public synchronized boolean containsKey(Object in) {
        return (m_Properties.containsKey(in));
    }


    public synchronized int[] values() {
        String[] keys = keys();
        int[] ret = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            ret[i] = value(keys[i]);
        }
        return (ret);
    }



}