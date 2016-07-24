package com.lordjoe.utilities;

import java.io.Serializable;
import java.util.*;

/**
 * com.lordjoe.utilities.DualMap
 * make a one to one mapping of two objects
 * User: Steve
 * Date: 6/28/2016
 */
public class DualList<K,T> implements  Serializable {

    private final Map<K,Set<T>> forward = new HashMap<K, Set<T>>() ;
    private final Map<T,K> reverse = new HashMap<T, K>() ;
    private final Class kClass;
    private final Class tClass;

    public DualList(Class kClass, Class tClass) {
        this.kClass = kClass;
        this.tClass = tClass;
    }

    public synchronized void register(K k,T t)  {
        if(!kClass.isInstance(k))
            throw new IllegalArgumentException("bad type " + k.getClass());
        if(!tClass.isInstance(t))
            throw new IllegalArgumentException("bad type " + t.getClass());
        Set<T> existing = forward.get(k);
        if(existing == null)   {
             existing = new HashSet<T>();
            forward.put(k,existing);
        }
        existing.add(t);
        reverse.put(t,k) ;
    }

    public boolean isEmpty() {
        return  size() == 0;
    }

    public int size() {
        return forward.size();
    }

    public Object lookup(Object o)   {
        if(kClass.isInstance(o))
            return forwardLookup((K)o) ;
        if(tClass.isInstance(o))
            return reverseLookup((T)o) ;
        throw new IllegalArgumentException("Unknown class" + o.getClass());
    }

    public K reverseLookup(T t)   {
        if(!tClass.isInstance(t))
            throw new IllegalArgumentException("bad type " + t.getClass());
        return reverse.get(t);
    }
    public synchronized Set<T> forwardLookup(K k)   {
        if(!kClass.isInstance(k))
            throw new IllegalArgumentException("bad type " + k.getClass());
        Set<T> ts = forward.get(k);
        if(ts == null)   {
            ts = new HashSet<T>();
            forward.put(k,ts);
        }
        return ts;
    }

    public Set<T> forwardValues()   {
           return reverse.keySet();
    }

    public Set<K> reverseValues()   {
        return forward.keySet();
    }

}
