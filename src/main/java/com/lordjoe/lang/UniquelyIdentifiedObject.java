package com.lordjoe.lang;

import com.lordjoe.utilities.*;

import java.util.*;

/**
 * com.lordjoe.lang.UniquelyIdentifiedObject
 *
 * @author merdahl
 *         created Jun 12, 2007
 */
public class UniquelyIdentifiedObject implements IIdentifiedObject, Comparable<Object>
{
    public static UniquelyIdentifiedObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UniquelyIdentifiedObject.class;

    /**
     * holds all objevts - yes I know this is a memory leak
     * todo make into a WeakMap
     */
    public static final Map<Class, Map<String, UniquelyIdentifiedObject>> gRegisteredInstances =
            new HashMap<Class, Map<String, UniquelyIdentifiedObject>>();



    private static Map<String, UniquelyIdentifiedObject> getRegistrationMap(Class cls)
    {
        synchronized (gRegisteredInstances) {
            Map<String, UniquelyIdentifiedObject> registeredObjects =
                    gRegisteredInstances.get(cls);
            if(registeredObjects == null) {
                registeredObjects = new HashMap<String, UniquelyIdentifiedObject>();
                gRegisteredInstances.put(cls,registeredObjects);
            }
            return registeredObjects;
        }
    }

    private String m_Name;
    private String m_Id;

    public UniquelyIdentifiedObject(String name)
    {
        m_Name = name;
    }

    /**
     * make sure there are no other obh=jects with this id
     */
    protected  void registerInstance()
     {
         Map<String, UniquelyIdentifiedObject> registeredObjects =
                 getRegistrationMap(this.getClass());
         String key = getId();
         if(registeredObjects.containsKey(key)) {
             throw new IllegalStateException("Duplicate Unigue Object with key " + key);
         }
         registeredObjects.put(key,this);
       }


    public String getName()
    {
        return m_Name;
    }

    public int compareTo(Object o)
    {
        if (o == this)
            return 0;
        return getName().compareTo(((UniquelyIdentifiedObject) o).getName());
    }

    public void setName(String pName)
    {
        if (pName.contains("null"))
            m_Name = pName; // break here
        m_Name = pName;
    }

    public void setId(String id)
    {
        if (m_Id != null)
            throw new IllegalStateException("Id only set once");
        m_Id = id;
        registerInstance(); // make sure the key is unique
    }

    public String getId()
    {
        return m_Id;
    }


    public String toString()
    {
        return getName();
    }


    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (getClass().equals(obj.getClass())) {
            return getId().equals(((UniquelyIdentifiedObject) obj).getId());
        }
        return false;
    }

    public int hashCode()
    {
        String id = getId();
        int i = getClass().hashCode();
        return i ^ id.hashCode();
    }
}
