package com.lordjoe.lang;

import com.lordjoe.utilities.*;
import com.lordjoe.general.*;

import java.util.*;
import java.util.concurrent.*;
import java.beans.*;

/**
 * com.lordjoe.lang.CompositeIdentifiedObject
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public abstract class CompositeIdentifiedObject<T> extends AbstractDataObject implements IComposite,
        ICollectionHolder<T>
{
    public static CompositeIdentifiedObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CompositeIdentifiedObject.class;
    private final List<CompositeChangeListener> m_CompositeChangeListeners;
    private final List<PropertyChangeListener> m_PropertyChangeListeners;
    private final T[] m_Type;  // array possibly empty of the type of child
    private final List<T> m_Members;
    private final List<CollectionChangeListener<T>> m_CollectionListeners;
    private String m_CollectionName;

    public CompositeIdentifiedObject(T[] type)
    {
        m_CompositeChangeListeners = new CopyOnWriteArrayList<CompositeChangeListener>();
        m_PropertyChangeListeners = new CopyOnWriteArrayList<PropertyChangeListener>();
        m_Type = type;
        m_Members = new ArrayList<T>();
        m_CollectionListeners = new ArrayList<CollectionChangeListener<T>>();
        addCollectionChangeListener(this);
    }

    /**
     * add a change listener
     * final to make sure this is not duplicated at multiple levels
     *
     * @param added non-null change listener
     */
    public final void addPropertyChangeListener(PropertyChangeListener added)
    {
        if (!m_PropertyChangeListeners.contains(added))
            m_PropertyChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public final void removePropertyChangeListener(PropertyChangeListener removed)
    {
        while (m_PropertyChangeListeners.contains(removed))
            m_PropertyChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyPropertyChangeListeners(String name,Object oldValue,Object newValue)
    {
        if (m_PropertyChangeListeners.isEmpty())
            return;
        PropertyChangeEvent evt = new PropertyChangeEvent(this,name,oldValue, newValue);
        for (PropertyChangeListener listener : m_PropertyChangeListeners) {
            listener.propertyChange(evt);
        }
    }


    public String getCollectionName()
    {
        return m_CollectionName;
    }

    public void setCollectionName(String pCollectionName)
    {
        m_CollectionName = pCollectionName;
    }

    public void rollback()
    {
        boolean provisional = isProvisional();
        super.rollback();
    }

    /**
     * true if this or any descendent is dirty
     *
     * @return as above
     */
    @Override
    public boolean hasDirtyChild()
    {
        if (isDirty())
            return true;
        T[] children = getCollectionMembers();
        for (int i = 0; i < children.length; i++) {
            T child = children[i];
            if (child instanceof AbstractDataObject) {
                if (((AbstractDataObject) child).hasDirtyChild())
                    return true;
            }
        }
        return false;
    }


    /**
     * clear dirty and all dirty children
     */
    @Override
    public void clearDirty()
    {
        T[] children = getCollectionMembers();
        for (int i = 0; i < children.length; i++) {
            T child = children[i];
            if (child instanceof AbstractDataObject) {
                ((AbstractDataObject) child).clearDirty();
            }
        }
        super.clearDirty();
    }


    /**
     * add a collection change listener
     *
     * @param l non-null collection change listener
     */
    public void addCollectionChangeListener(CollectionChangeListener<T> l)
    {
        if (!m_CollectionListeners.contains(l))
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

    protected void notifyCollectionListeners(String collectionName, Object member,
                                             CollectionChangeListener.CollectionChangeEvent evt)
    {
        if (m_CollectionListeners.isEmpty())
            return;
        for (CollectionChangeListener listener : m_CollectionListeners) {
            switch (evt) {
                case add:
                    listener.onMemberAdded(collectionName, member);
                    break;
                case remove:
                    listener.onMemberRemoved(collectionName, member);
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
    public final T[] getCollectionMembers()
    {
        return m_Members.toArray(m_Type);
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
    public void removeCollectionMember(String collectionName, T member)
            throws UnsupportedOperationException
    {
        m_Members.remove(member);
        if (member instanceof ISavable)
            ((ISavable) member).removeDirtyChangeListener(this);
        setDirty(true); // objeect has changed
        notifyCollectionListeners(getCollectionName(), member,
                CollectionChangeListener.CollectionChangeEvent.remove);

    }

    public int getCollectionMemberCount()
    {
        return m_Members.size();
    }

    /**
     * add  a member of the collection
     *
     * @param member non-null member
     * @throws UnsupportedOperationException
     */
    public void addCollectionMember(String collectionName, T member)
            throws UnsupportedOperationException
    {
        m_Members.add(member);
        if (member instanceof ISavable)
            ((ISavable) member).addDirtyChangeListener(this);
        setDirty(true); // objeect has changed
        notifyCollectionListeners(collectionName, member,
                CollectionChangeListener.CollectionChangeEvent.add);

    }


    public int getChildObjectCount()
    {
        return getMemberCount();
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

    /**
     * visit this and all children
     *
     * @param visitor
     */
    public void applyVisitor(IVisitor<AbstractDataObject> visitor)
    {
        super.applyVisitor(visitor);
        T[] children = getCollectionMembers();
        for (int i = 0; i < children.length; i++) {
            T child = children[i];
            if (child instanceof AbstractDataObject) {
                visitor.visit((AbstractDataObject) child);
            }
        }

    }


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addCompositeChangeListener(CompositeChangeListener added)
    {
        if (!m_CompositeChangeListeners.contains(added))
            m_CompositeChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeCompositeChangeListener(CompositeChangeListener removed)
    {
        while (m_CompositeChangeListeners.contains(removed))
            m_CompositeChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyCompositeChangeListeners(Object affected, String collection,
                                               CompositeChangeListener.ChangeTypes type)
    {
        if (m_CompositeChangeListeners.isEmpty())
            return;
        for (CompositeChangeListener listener : m_CompositeChangeListeners) {
            switch (type) {
                case add:
                    listener.onAddChild(this, (IComposite) affected);
                    break;
                case remove:
                    listener.onRemoveChild(this, (IComposite) affected);
                    break;
                case clear:
                    listener.onClearChildren(this);
                    break;
                case bulkAdd:
                    listener.onAddChildren(this, (IComposite[]) affected);
                    break;
            }
        }
    }

}
