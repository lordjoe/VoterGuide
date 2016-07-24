package com.lordjoe.utilities;

import java.util.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.utilities.EntryOrderedMap
 * this is a structure supporting lookup which is
 * guaranteed to return items in the order requested
 * @author Steve Lewis
 * @date Jun 2, 2006 
 */
public class EntryOrderedMap<K,V>
{
    public final static EntryOrderedMap[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = EntryOrderedMap.class;

    private final Map<K, V> m_LookUp;
    private final List<V> m_Items;
    private final Class m_ComponentType;

    public EntryOrderedMap(V[] sample)
    {
        m_Items = new ArrayList<V>(Arrays.asList(sample));
        m_LookUp = new HashMap<K, V>();
        m_ComponentType = sample.getClass().getComponentType();
        if (sample.length > 0) {
            for (int i = 0; i < sample.length; i++) {
                V v = sample[i];
                addSampleEntry(v);
            }
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    public Iterator<V> iterator() {
       return new EntryOrderedMapIterator<V>();
    }

    public class EntryOrderedMapIterator<V> implements Iterator<V>
    {
         private int m_Index;
         private final V[] m_Items;
         public EntryOrderedMapIterator() {
             m_Items = (V[])EntryOrderedMap.this.getItems();
         }
        public boolean hasNext()
        {
            return m_Index < m_Items.length;
        }

       public V next()
        {
            return m_Items[m_Index++];
        }

         public void remove()
        {
            throw new UnsupportedOperationException("This iterator does not remove");

        }
    }


    protected K keyFromEntry(V sample)
    {
        throw new UnsupportedOperationException("Override to provide a way to map sample to a key");
    }


    protected void addSampleEntry(V sample)
    {
        K key = keyFromEntry(sample);
        m_LookUp.put(key, sample);
    }


    public int size()
    {
        return m_LookUp.size();
    }


    public boolean contains(V sample)
    {
        K key = keyFromEntry(sample);
        return m_LookUp.containsKey(key);
    }


    protected void removeSampleEntry(V sample)
    {
        K key = keyFromEntry(sample);
        m_LookUp.remove(key);
    }

    public void add(V sample)
    {
        validateComponentType(sample);
        synchronized (m_Items) {
            addSampleEntry(sample);
            m_Items.add(sample);
        }
    }

    protected void validateComponentType(V sample)
    {
        if (!m_ComponentType.isInstance(sample))
            throw new IllegalArgumentException("Attempting to add item of class " +
                    sample.getClass().getName() +
                    " not " + m_ComponentType.getName());
    }


    public boolean remove(V sample)
    {
        synchronized (m_Items) {
            if (m_Items.contains(sample)) {
                removeSampleEntry(sample);
                m_Items.remove(sample);
                return true;
            }
            else {
                return false; // not present
            }
        }
    }

    public V[] getItems()
    {
        synchronized (m_Items) {
            int size = m_Items.size();
            V[] ret = (V[]) Array.newInstance(m_ComponentType, size);
            m_Items.toArray(ret);
            return ret;
        }
    }

    public V get(K key)
    {
        return m_LookUp.get(key);
    }
}
