package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.CollectionChangeListener
 *     interface to respond to collection changes
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public interface CollectionChangeListener <T>
{
    public static CollectionChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CollectionChangeListener.class;

    public enum  CollectionChangeEvent {
        add,remove,clear
    }
    /**
     * handle member added to a collection
     * @param collection  possibly null collection name
     * @param added  non-null added member
     */
    public void onMemberAdded(String collection,T added);

    /**
     * handle member removed from a collection
     * @param collection  possibly null collection name
     * @param added  non-null added member
     */
    public void onMemberRemoved(String collection,T added);

    /**
     * handle all members removed
     * @param collection  possibly null collection name
     */
    public void onCollectionCleared(String collection);


}
