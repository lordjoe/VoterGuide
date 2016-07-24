package com.lordjoe.ui.propertyeditor;

import com.lordjoe.utilities.*;
import com.lordjoe.propertyeditor.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.awt.*;

/**
 * com.lordjoe.ui.propertyeditor.AbstractCollectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Jan 28, 2008
 */
public abstract class AbstractCollectionPropertyEditor<T> implements ICollectionPropertyEditor, IStylizableComponent, CollectionChangeListener {
    public static AbstractCollectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractCollectionPropertyEditor.class;
    public static final Color DIASABLED_BACKGROUND = new Color(245, 245, 245);

    private ICollectionWrapper<T> m_Wrapper;
    private T[] m_OriginalValue;
    private final List<EditedPropertyChangeListener> m_EditedPropertyChangeListeners;

    public AbstractCollectionPropertyEditor(ICollectionWrapper<T> pWrapper) {
        m_Wrapper = pWrapper;
        m_OriginalValue = pWrapper.getCurrentItems();
        m_EditedPropertyChangeListeners = new CopyOnWriteArrayList<EditedPropertyChangeListener>();
    }

    protected boolean isMyCollection(String s)
    {
        return m_Wrapper.getCollectionName().equals(s);
    }

    public abstract void onMemberAdded(String collection, Object added);

    public abstract void onMemberRemoved(String collection, Object removed);

    public abstract void onCollectionCleared(String collection);

    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addEditedPropertyChangeListener(EditedPropertyChangeListener added)
    {
        if (!m_EditedPropertyChangeListeners.contains(added))
            m_EditedPropertyChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeEditedPropertyChangeListener(EditedPropertyChangeListener removed)
    {
        while (m_EditedPropertyChangeListeners.contains(removed))
            m_EditedPropertyChangeListeners.remove(removed);
    }

    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyEditedPropertyChangeListeners(Object oldValue, Object value)
    {
        if (m_EditedPropertyChangeListeners.isEmpty())
            return;
        for (EditedPropertyChangeListener listener : m_EditedPropertyChangeListeners) {
            listener.onEditedPropertyChange(oldValue, value);
        }
    }

    public abstract JComponent[] getStylizableComponents();

    public abstract JComponent getStylizableSelf();

    public abstract JComponent getMainDisplay();

    public abstract void rebuildPanel();

    public abstract JComponent getComponent();

    public ICollectionWrapper<T> getCollectionWrapper()
    {
        return m_Wrapper;
    }

    /**
     * set the value to that represented by the data source
     */
    public void reconcile()
    {
        if (SwingUtilities.isEventDispatchThread()) {
            doReconcile();
        }
        else {
            SwingUtilities.invokeLater(
                    new Runnable()
                    {  // start anonymous inner class

                        public void run()
                        {
                            doReconcile();
                        }
                    }  // end anonymous inner class
            );
        }
    }

    protected void doReconcile()
    {
//        T[] oldItems = getCollectionWrapper().getCurrentItems();
//        m_Panel.setCurrentItems(oldItems);
    }

    public abstract void commit();

    /**
     * roll changes back to creation or lar commit
     */
    public void rollback()
    {
        T[] items = m_OriginalValue;
        setCollectionContents(items);
    }

    protected void setCollectionContents(Object[] pItems)
    {
        ICollectionWrapper<T> cw = getCollectionWrapper();
        T[] oldItems = cw.getCurrentItems();
        Set<T> current = new HashSet<T>(Arrays.asList(oldItems));
        Set<T> adds = new HashSet<T>((Collection<T>) Arrays.asList(pItems));
        Set<T> removes = new HashSet<T>(Arrays.asList(oldItems));
        for (int i = 0; i < pItems.length; i++) {
            T item = (T) pItems[i];
            if (current.contains(item)) {
                removes.remove(item);
                adds.remove(item);
                continue;
            }
        }
//        for (int i = 0; i < oldItems.length; i++) {
//            T item = (T) oldItems[i];
//            if(current.contains(item)) {
//                continue;
//            }
//            else {
//                adds.add(item);
//            }
//        }
        for (T x : adds) {
            getCollectionWrapper().addItem(x);
        }
        for (T x : removes) {
            getCollectionWrapper().removeItem(x);
        }
    }
}
