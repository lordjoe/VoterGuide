package com.lordjoe.propertyeditor;

import com.lordjoe.lib.xml.*;
import com.lordjoe.utilities.*;
import com.lordjoe.exceptions.*;

import java.lang.reflect.*;

/**
 * com.lordjoe.propertyeditor.ReflectionCollectionWrapper
 *   uses reflection to return a propertyWarpper for a specified property
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class ReflectionCollectionWrapper<T> implements ICollectionWrapper<T>
{
    public static ReflectionPropertyWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectionPropertyWrapper.class;

    private final T[] m_Type;
    private final Object m_Owner;
    private final ClassCollection m_Collection;
    private String m_DisplayName;

    public ReflectionCollectionWrapper(Object pOwner, ClassCollection pProperty,T[] type)
    {
        m_Type = type;
        m_Owner = pOwner;
        m_Collection = pProperty;
        Class cls = m_Collection.getType();
    }

    public ClassCollection getCollection() {
        return m_Collection;
    }



    /**
     * return the class of the property
     *
     * @return
     */
    public Class getPropertyClass()
    {
        return m_Collection.getType();
    }


    public T[] getType()
    {
        return m_Type;
    }

    public Object getOwner()
    {
        return m_Owner;
    }

    /**
     * return the name of the property - usually as a java bean name
     *
     * @return non-null String
     */
    public String getCollectionName()
    {
        return m_Collection.getName();
    }


    /**
     * return the name of the property as seen by the user -
     *
     * @return non-null String
     */
    public String getDisplayName()
    {
        if (m_DisplayName != null)
            return m_DisplayName;
        String s = getCollectionName();
        return Util.displayString(s);
    }

    public void setDisplayName(String pDisplayName)
    {
        m_DisplayName = pDisplayName;
    }


    public T[] getCurrentItems() {
        try {
            return (T[]) m_Collection.getItemsMethod().invoke(m_Owner);
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }
    }

    public void addItem(T value) {
        if (m_Collection.getAddMethod() == null)
            throw new UnsupportedOperationException("Add not Supported");
        try {
            m_Collection.getAddMethod().invoke(m_Owner,value);
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        } catch (Exception ex) {
        	/*
        	 * SAM note: debugging added
        	 */
        	ex.printStackTrace();
        	System.err.println( "exception in addItem with value type " + value.getClass() + " method called: " + m_Collection.getAddMethod() );
        	throw new WrappingException(ex);
        }
    }

    public boolean isRemoveSupported() {
        return m_Collection.getRemoveMethod() != null;
    }

    public boolean isAddSupported() {
        return m_Collection.getAddMethod() != null;
    }

    public void removeItem(T value) {
        if (m_Collection.getRemoveMethod() == null)
            throw new UnsupportedOperationException("Remove not Supported");
        try {
            m_Collection.getRemoveMethod().invoke(m_Owner,value);
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }

    }
}