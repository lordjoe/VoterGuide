package com.lordjoe.concurrent;

/**
 * com.lordjoe.concurrent.IConflictSet
 *  interface representing a conflict set
 * @author Steve Lewis
 * @date Nov 8, 2005
 */
public interface IConflictSet
{
    void addConflict(IConflict cf);

    void awaitNoConflicts(Object item);

    /**
     * @param item    non-null item
     * @param timeout millisec timeout
     * @return true if there are no conflicts
     */
    boolean awaitNoConflicts(Object item, long timeout);

    boolean itemConflicts(Object item);

    /**
     * add an item to the current set
     * waiting until no conflicts
     * @param item non-null item
     */
    void addItem(Object item);

    /**
     * remove an item from the current set
     *
     * @param item non-null item
     */
    void releaseItem(Object item);
}
