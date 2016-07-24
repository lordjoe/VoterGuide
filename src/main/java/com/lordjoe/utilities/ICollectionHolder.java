package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.ICollectionHolder
 *
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public interface ICollectionHolder<T>
{
    public static ICollectionHolder[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ICollectionHolder.class;


    public String getCollectionName();

    /**
     * add a collection change listener
     * @param l non-null collection change listener
     */
    public void addCollectionChangeListener(CollectionChangeListener<T> l);

    /**
     * remove a collection change listener
     * @param l non-null collection change listener
     */
    public void removeCollectionChangeListener(CollectionChangeListener<T> l);

    /**
     *  return all members of the collection
     * @return as above
     */
    public T[] getCollectionMembers();

    /**
     *  return an indexed member of a collection
     * @param index index of the member between 0 and getMemberCount() - 1;
     * @return non-null collection member
     * @throws RuntimeException  of some form if index is out of bounde
     */
    public T getCollectionMember(int index) throws RuntimeException;

    /**
     *  return size of the collection
     * @return as above
     */
    public int getMemberCount();


    /**
     * remove  a member of the collection
     * @param member  non-null member
     * @throws UnsupportedOperationException
     */
    public void removeCollectionMember(String collectionName,T member) throws UnsupportedOperationException;

    /**
     * add  a member of the collection
     * @param member  non-null member
     * @throws UnsupportedOperationException
     */
    public void addCollectionMember(String collectionName,T member) throws UnsupportedOperationException;
}
