package com.lordjoe.utilities;

import java.lang.reflect.*;
import java.util.*;

/**
 * com.lordjoe.utilities.CollectionHolderImpl
 *   collection of a specific type
 * @author Steve Lewis
 * @date  Nov 15, 2007
 */
public class CollectionHolderImpl<T> implements ICollectionHolder<T>
{
    public static CollectionHolderImpl[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CollectionHolderImpl.class;

    private final T[] m_Type;
    private final List<T> m_Members;
    private final List<CollectionChangeListener<T>> m_CollectionListeners;
    private String m_CollectionName;

    public CollectionHolderImpl(T[] type)  // type is needed to set T
    {
        m_Type = type;
        m_Members = new ArrayList<T>();
        m_CollectionListeners = new ArrayList<CollectionChangeListener<T>>();
     }

    public String getCollectionName() {
        return m_CollectionName;
    }

    public void setCollectionName(String pCollectionName) {
        m_CollectionName = pCollectionName;
    }

    /**
     * add a collection change listener
     *
     * @param l non-null collection change listener
     */
    public void addCollectionChangeListener(CollectionChangeListener<T> l)
    {
       m_CollectionListeners.add(l);

    }

    /**
     * remove a collection change listener
     *
     * @param l non-null collection change listener
     */
    public void removeCollectionChangeListener(CollectionChangeListener<T> l)
    {
        m_CollectionListeners.remove(l);

    }

    protected void notifyCollectionListeners(String collectionName,T member,CollectionChangeListener.CollectionChangeEvent evt)
    {
        if (m_CollectionListeners.isEmpty())
            return;
        for (CollectionChangeListener listener : m_CollectionListeners) {
            switch(evt) {
                case add:
                    listener.onMemberAdded(collectionName,member);
                    break;
                case remove:
                    listener.onMemberRemoved(collectionName,member);
                    break;
                case clear:
                    listener.onCollectionCleared(collectionName);
                    break;
            }
        }
    }


    /**
     * return all members of the collection
     *
     * @return as above
     */
    public T[] getCollectionMembers()
    {
        return getTs();
    }

    /**
     * return an indexed member of a collection
     *
     * @param index index of the member between 0 and getMemberCount() - 1;
     * @return non-null collection member
     * @throws RuntimeException of some form if index is out of bounde
     */
    public T getCollectionMember(int index) throws RuntimeException
    {
        return m_Members.get(index);
    }

    /**
     * remove  a member of the collection
     *
     * @param member non-null member
     * @throws UnsupportedOperationException
     */
    public void removeCollectionMember(String collectionName,T member)
            throws UnsupportedOperationException
    {
        m_Members.remove(member);
        notifyCollectionListeners(getCollectionName(),member, CollectionChangeListener.CollectionChangeEvent.remove);

    }

    /**
     * add  a member of the collection
     *
     * @param member non-null member
     * @throws UnsupportedOperationException
     */
    public void addCollectionMember(String collectionName,T member) throws UnsupportedOperationException
    {
        m_Members.add(member);
        notifyCollectionListeners(getCollectionName(),member, CollectionChangeListener.CollectionChangeEvent.add);

    }

    /**
     * return size of the collection
     *
     * @return as above
     */
    public int getMemberCount()
    {
         return m_Members.size();
    }

    public Class getMemberType()
    {
        return m_Type.getClass().getComponentType();
    }

    public T[] getTs()
    {

        T[] ret = (T[])Array.newInstance(getMemberType(),m_Members.size());
        m_Members.toArray(ret);
        return ret;
    }



}