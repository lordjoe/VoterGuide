package com.lordjoe.utilities;

import com.lordjoe.utilities.*;
import com.lordjoe.lib.xml.*;

import java.io.Serializable;
import java.util.*;

/**
 * Data class for name count pairs
 * system
 */
public class NamedCounter  implements INameable,Serializable,Struct {
    public static final NamedCounter[] EMPTY_ARRAY = {};
    public static final Class THIS_CLASS = NamedCounter.class;


    public static Map buildMap(NamedCounter[] items)
    {
        Map ret = new HashMap();
        for(int i = 0; i < items.length; i++)
            ret.put(items[i].m_Name,new Integer(items[i].m_Value));
        return(ret);
    }
    
    public static NamedCounter[] fromMap(Map Values)
    {
        NamedCounter[] items = new NamedCounter[Values.size()];
        String[] keys = Util.getKeyStrings(Values);
        for(int i = 0; i < keys.length; i++) {
            items[i] = new NamedCounter(keys[i],((Integer)Values.get(keys[i])).intValue());
        }
        return(items);
    }
    
    public NamedCounter() {}
    public NamedCounter(String name,int value)
    {
        m_Name = name;
        m_Value = value;
    }
    
    
	public static final NamedCounter[] NULL_ARRAY = {};
	public String					m_Name;
	public int					m_Value;
	
	public String getName() { return(m_Name); }
	public int getValue() { return(m_Value); }
	public void setName(String in) { m_Name = in; }
	public void setValue(int in) { m_Value = in; }
    public void increment() {  m_Value++; }
    public void decrement() {  m_Value--; }
    public int compareTo(Object in) {
        return(getName().compareTo(((NamedCounter)in).getName())) ;
    }
}
