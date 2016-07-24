package com.lordjoe.general;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.general.NamedObjectEntryOrderedMap
 * Behaves like a map if INamedObject with unique names
 * @author Steve Lewis
 * @date Jun 2, 2006 
 */
public class NamedObjectEntryOrderedMap<V extends INamedObject> extends EntryOrderedMap<String,V>
{
    public final static NamedObjectEntryOrderedMap[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = NamedObjectEntryOrderedMap.class;

    public NamedObjectEntryOrderedMap(V[] sample)
    {
        super(sample);
    }

    protected String keyFromEntry(V sample)
    {
        return sample.getName();
    }
}
