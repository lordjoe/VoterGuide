package com.lordjoe.lang;

import com.lordjoe.general.*;
import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// import org.wcs.gain.WCSUtilities;

/**
 * org.wcs.gain.AbstractWSCObject
 *
 * @author Steve Lewis
 * @date Dec 15, 2007
 */
public abstract class AbstractDataObject extends IdentifiedObject implements PropertyChangeListener,
        CollectionChangeListener, DirtyChangeListener, ISavable, IChangeValidator, ObjectChangeEventGenerator {
    public static AbstractDataObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractDataObject.class;

    private boolean m_Dirty;
    private boolean m_Provisional;  // provisional
    private final List<DirtyChangeListener> m_DirtyChangeListeners;
    private final List<ObjectChangeListener> m_ObjectChangeListeners;

    protected AbstractDataObject() {
        m_DirtyChangeListeners = new CopyOnWriteArrayList<DirtyChangeListener>();
        m_ObjectChangeListeners = new CopyOnWriteArrayList<ObjectChangeListener>();
    }

    // Add to constructor


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addObjectChangeListener(ObjectChangeListener added) {
        if (!m_ObjectChangeListeners.contains(added))
            m_ObjectChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeObjectChangeListener(ObjectChangeListener removed) {
        while (m_ObjectChangeListeners.contains(removed))
            m_ObjectChangeListeners.remove(removed);
    }

    /**
     * provisional objects will be deleted by cancel
     * @return
     */
    public boolean isProvisional() {
        return m_Provisional;
    }

    public void setProvisional(boolean pProvisional) {
        m_Provisional = pProvisional;
    }

    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyObjectChangeListeners(Object added) {
        if (m_ObjectChangeListeners.isEmpty())
            return;
        for (ObjectChangeListener listener : m_ObjectChangeListeners) {
            listener.onObjectChange(this, added);
        }
    }


    /**
     * if true the create editor has all fields and after create there is no need
     * for further editing
     *
     * @return as above
     */
    public boolean isCreateComplete() {
        return false;
    }

    /**
     * final to force overriding of  appendSelfToXHTML
     * and  appendChildrenToXHTML
     *
     * @param ap     non-null appendable
     * @param indent indent level
     */
    public final void appendToXHTML(Appendable ap, int indent) throws IOException {
        appendSelfToXHTML(ap, indent);
        appendChildrenToXHTML(ap, indent);
        appendSelfEndToXHTML(ap, indent);
    }

    /**
     * add a representation of this object but not children to xhtml
     *
     * @param ap     non-null appendable
     * @param indent indent level
     */
    protected void appendSelfToXHTML(Appendable ap, int indent) throws IOException {

    }

    /**
     * add a representation of this object but not children to xhtml
     *
     * @param ap     non-null appendable
     * @param indent indent level
     */
    protected void appendSelfEndToXHTML(Appendable ap, int indent) throws IOException {

    }

    /**
     * add a representation of child objects but not children to xhtml
     *
     * @param ap     non-null appendable
     * @param indent indent level
     */
    protected void appendChildrenToXHTML(Appendable ap, int indent) throws IOException {

    }


    /**
     * if the object is shared by multiple data sets and edited at the base
     *
     * @return as above
     */
    public boolean isCommonObject() {
        return false;
    }


    public void save() {
        setProvisional(false);
        // do nothing - some classes override
        notifyObjectChangeListeners(this);
    }


    public void rollback() {
        setProvisional(true);
        // do nothing - some classes override
        notifyObjectChangeListeners(this);
    }

    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addDirtyChangeListener(DirtyChangeListener added) {
        if (!m_DirtyChangeListeners.contains(added))
            m_DirtyChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeDirtyChangeListener(DirtyChangeListener removed) {
        while (m_DirtyChangeListeners.contains(removed))
            m_DirtyChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyDirtyChangeListeners() {
        if (m_DirtyChangeListeners.isEmpty())
            return;
        for (DirtyChangeListener listener : m_DirtyChangeListeners) {
            boolean dirty = isDirty();
            listener.onDirtyChange(this, dirty);
        }
    }


    public synchronized void guaranteeId() {
        if (getId() != null)
            return;
        setId((String)generateId());
    }

    protected abstract Object generateId();

    protected abstract void registerId(Object o);

    /**
     * override registers the id as read in do we track ids
     *
     * @param pId
     */
    public void setId(String pId) {
        super.setId(pId);
        Long l = null;
        try {
            l = new Long(pId);
        } catch (NumberFormatException e) {
            return;
        }
        registerId(l);
       // WCSUtilities.registerId(getClass(), l);
    }

    /**
     * is this object dirty
     *
     * @return
     */
    public boolean isDirty() {
        return m_Dirty;
    }

    /**
     * dirty is altered and not saved
     *
     * @param pDirty
     */
    public void setDirty(boolean pDirty) {
        if (m_Dirty == pDirty)
            return;
        m_Dirty = pDirty;
        notifyDirtyChangeListeners();
    }

    /**
     * true if this or any descendent is dirty
     *
     * @return as above
     */
    public boolean hasDirtyChild() {
        return isDirty();
    }

    /**
     * clear dirty and all dirty children
     */
    public void clearDirty() {
        setDirty(false);
    }

    public void applyVisitor(IVisitor<AbstractDataObject> visitor) {
        visitor.visit(this);
    }

    public void onMemberAdded(String collection, Object added) {
        setDirty(true);
        if (added instanceof AbstractDataObject) {
            ((AbstractDataObject) added).addDirtyChangeListener(this);
        }

    }

    public void onMemberRemoved(String collection, Object removed) {
        setDirty(true);
        if (removed instanceof AbstractDataObject) {
            ((AbstractDataObject) removed).removeDirtyChangeListener(this);
        }
    }

    public void onCollectionCleared(String collection) {
        setDirty(true);

    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("dirty".equals(evt.getPropertyName()))
            return;
        setDirty(true);
    }

    public void onDirtyChange(Object target, boolean dirty) {
        if (target != this && dirty)
            setDirty(true);
    }

    /**
     * method used for group validation
     *
     * @param proposed non-null list of proposed changes
     * @return if null all op otherwise a list of properties which
     *         are problematic
     */
    public IValidationReason[] validateProposedChange(Map<String, Object> proposed) {
        return null;  // all OK
    }
}
