package com.lordjoe.utilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * com.lordjoe.utilities.DualMap
 * make a one to one mapping of two objects
 * User: Steve
 * Date: 6/28/2016
 */
public class DualMap<K,T> implements  Serializable {

    private final Map<K,T> forward = new HashMap<>() ;
    private final Map<T,K> reverse = new HashMap<>() ;
    private final Class kClass;
    private final Class tClass;

    public DualMap(Class kClass, Class tClass) {
        this.kClass = kClass;
        this.tClass = tClass;
    }

    public void register(K k,T t)  {
        if(!kClass.isInstance(k))
            throw new IllegalArgumentException("bad type " + k.getClass());
        if(!tClass.isInstance(t))
            throw new IllegalArgumentException("bad type " + t.getClass());
        T existing = forward.get(k);
        if(existing != null)   {
            if (!existing.equals(t))
                throw new IllegalArgumentException("cannot replace an item with a different one");
            return;
        }
        forward.put(k,t);
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
    public T forwardLookup(K k)   {
        if(!kClass.isInstance(k))
            throw new IllegalArgumentException("bad type " + k.getClass());
        return forward.get(k);
    }

    public Set<T> forwardValues()   {
           return reverse.keySet();
    }

    public Set<K> reverseValues()   {
        return forward.keySet();
    }

}
