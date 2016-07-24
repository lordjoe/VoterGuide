package com.lordjoe.utilities;

import java.util.*;

/**
 * com.lordjoe.utilities.AbstractAccumulatingVisitor
 *
 * @author Steve Lewis
 * @date Dec 29, 2007
 */
public abstract class AbstractAccumulatingVisitor<T, V> implements IVisitor<T> {
    public static AbstractAccumulatingVisitor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractAccumulatingVisitor.class;

    private final Set<V> m_Accumulated;

    public AbstractAccumulatingVisitor() {
        m_Accumulated = new HashSet();
    }

    public void addItem(V added) {
        synchronized (m_Accumulated) {
            m_Accumulated.add(added);
        }
    }

    public V[] getItems() {
        synchronized (m_Accumulated) {
            V[] ret = buildHolderArray(m_Accumulated.size());
            m_Accumulated.toArray(ret);
            return ret;
        }
    }

    public void visit(T target) {
        V[] items = gatherItems(target);
        for (int i = 0; i < items.length; i++) {
            V item = items[i];
            addItem(item);
        }

    }

    /**
     * abstract because we do not know how to build an array
     * @param size
     * @return
     */
    protected abstract V[] buildHolderArray(int size);

    /**
     * get the items we are accumulating from the target
     * @param target
     * @return
     */
    public abstract V[] gatherItems(T target);
}
