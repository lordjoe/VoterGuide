package com.lordjoe.utilities;

import java.util.*;
import java.io.*;

/**
 * enumarated type for comparisons used by VB
 */
public abstract class EnumeratedType  implements INameable,java.lang.Comparable
{
    private static Map gClassExamples = new HashMap();

    public static EnumeratedType stringToValue(Class type,String val)
    {
        EnumeratedType exemplar = null;
        synchronized(gClassExamples) {
             exemplar = (EnumeratedType)gClassExamples.get(type);
         }
         if(exemplar == null)
             throw new IllegalArgumentException("class " + type.getName() + " is not a registered enum");
        return(exemplar.getEnumeratedTypeByValue(val));
    }
    public static EnumeratedType[] getClassOptions(Class type)
    {
        EnumeratedType exemplar = null;
        synchronized(gClassExamples) {
             exemplar = (EnumeratedType)gClassExamples.get(type);
         }
         if(exemplar == null)
             throw new IllegalArgumentException("class " + type.getName() + " is not a registered enum");
        String[] names = exemplar.getOptionNames();
        EnumeratedType[] ret = new EnumeratedType[names.length];
        for (int i = 0; i < names.length; i++)
        {
            String name = names[i];
            ret[i] = stringToValue(type,name);
        }
        return ret;
    }

    private final String m_Name;
    protected EnumeratedType(String name) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        m_Name = name;
        rememberName();
        synchronized(gClassExamples) {
            if(gClassExamples.get(getClass()) != null)
                return;
            gClassExamples.put(getClass(),this);
        }
    }

    protected void rememberName() {
        getOptionMap().put(getName(), this);
    }

    protected void rememberValue() {
        getValueMap().put(getEnumeratedData(), this);
    }

    /**
     * return the name of the object
     * @return non-null name
     */
    public String getName() {
        return (m_Name);
    }


    /**
     * return a Map of possible values
     * @return non-null name
     */
    protected abstract Map getOptionMap();

    /**
     * return a Map of possible values
     * wheer the key is the value
     * @return non-null name
     */
    protected abstract Map getValueMap();


    /**
     * return the class of the object held by the
     * type
     * @return non-null name
     */
    public String[] getOptionNames() {
        return (Util.getKeyStrings(getOptionMap()));
    }


    /**
     * allows the objects to be serialized
     * and returns the proper enumerated element
     * @return non-null object
     * @throws NullPointerException on error
     */
    protected Object readResolve() throws ObjectStreamException {
        return (getEnumeratedTypeByValue(getEnumeratedData()));
    }

    /**
     * return the class of the object held by the
     * type
     * @return non-null name
     */
    public EnumeratedType getEnumeratedTypeByValue(Object value) {
        Map valueMap = getValueMap();
        EnumeratedType enumeratedType = (EnumeratedType) valueMap.get(value);
        if(enumeratedType != null)
            return enumeratedType;
        else
            return null; // break here
    }

    /**
     * return the class of the object held by the
     * type
     * @return non-null name
     */
    public EnumeratedType getEnumeratedTypeByName(Object value) {
        return ((EnumeratedType) getOptionMap().get(value));
    }

    public EnumeratedType randomType() {
        return ((EnumeratedType) Util.randomValue(getOptionMap()));
    }

    public abstract String getStringValue();


    /**
     * return the class of the object held by the
     * type
     * @return non-null class
     */
    public abstract Class getEnumeratedClass();

    /**
     * return the data underlying the enumeration
     * @return non-null class
     */
    public abstract Object getEnumeratedData();

    /**
     * I gues the object should be the toString
     * @return non-null String
     */
    public String toString() {
  //      return (getEnumeratedData().toString());
        return (getName());
    }

    /**
     * compare names
     * @param test non-null Enumerated Type to compare
     * @return as woti compertto
     */
    public int compareTo(Object test)
    {
        return getName().compareTo(((EnumeratedType)test).getName());
    }


}
