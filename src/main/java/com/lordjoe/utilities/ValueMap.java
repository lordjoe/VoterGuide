package com.lordjoe.utilities;

import java.util.*;

/**
 * Holds a value which makes whether the value has been changed or
 * nulled
 * @see ValueMap
 * @author Steve Lewis
 */
public class ValueMap extends MapWrapper {

    public ValueMap() {
    }

    /**
     * Altered to unwrap value from a ValueHolder
     */
    public boolean containsValue(Object value) {
        return (super.containsValue(new ValueHolder(value)));
    }

    /**
     * Altered to unwrap value from a ValueHolder
     */
    public Object get(Object key) {
        ValueHolder holder = (ValueHolder) super.get(key);
        if (holder == null)
            return (null);
        return (holder.getValue());
    }

    /**
     * Altered to wrap value in a ValueHolder
     */
    public Object put(Object key, Object value) {
        ValueHolder holder = (ValueHolder) super.get(key);
        if (holder == null) {
            super.put(key, new ValueHolder(value));
            return (null);
        } else {
            Object ret = holder.getValue();
            holder.setValue(value);
            return (ret);
        }
    }


    /**
     * true if the data pointed by the key is altered
     * @param key non-null key
     * @return see above
     */
    public boolean isAltered(Object key) {
        ValueHolder holder = (ValueHolder) super.get(key);
        if (holder == null)
            return (false);
        return (holder.isAltered());
    }

    /**
     * Altered to unwrap value from a ValueHolder
     */
    public Collection values() {
        Collection temp = super.values();
        ValueHolder[] items = new ValueHolder[temp.size()];
        temp.toArray(items);
        List holder = new ArrayList();
        for (int i = 0; i < items.length; i++) {
            Object item = items[i].getValue();
            if (item != null)
                holder.add(item);
        }
        return (holder);
    }

    /**
     * mark all values as unaltered
     */
    public void clearAltered() {
        Collection temp = super.values();
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            ValueHolder item = (ValueHolder) it.next();
            item.clearAltered();
        }
    }

    public Set getAlteredKeys() {
        Set temp = super.keySet();
        Set holder = new HashSet();
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            ValueHolder item = (ValueHolder) super.get(key);
            if (item.isAltered())
                holder.add(key);
        }
        return (holder);
    }


    public void putAll(Map t) {
        throw new UnsupportedOperationException("Value Maps do not support putAll");
    }


}
