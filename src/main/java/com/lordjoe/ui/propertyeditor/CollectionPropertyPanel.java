package com.lordjoe.ui.propertyeditor;

import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.ObjectChangeEventGenerator;
import com.lordjoe.lang.ObjectChangeListener;
import com.lordjoe.lib.xml.ClassAnalyzer;
import com.lordjoe.lib.xml.ClassCollection;
import com.lordjoe.propertyeditor.AddMemberDialog;
import com.lordjoe.propertyeditor.ICollectionWrapper;
import com.lordjoe.propertyeditor.OptionMethod;
import com.lordjoe.propertyeditor.OptionSource;
import com.lordjoe.ui.general.SubjectDisplayPanel;
import com.lordjoe.utilities.ClassUtilities;
import com.lordjoe.utilities.INamedObject;
import com.lordjoe.utilities.NonReplaceListModel;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.lordjoe.ui.propertyeditor.CollectionPropertyPanel
 *
 * @author Steve Lewis
 * @date Dec 17, 2007
 */
public abstract class CollectionPropertyPanel<T> extends SubjectDisplayPanel
        implements ICollectionPropertyPanel<T>, ObjectChangeEventGenerator, ObjectChangeListener {
    public static CollectionPropertyPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CollectionPropertyPanel.class;
    public static final int DEFAULT_LIST_WIDTH = 400;
    public static final int DEFAULT_LIST_HEIGHT = 120;

    private ICollectionWrapper<T> m_Wrapper;
    private final String m_CollectionName;
    private final NonReplaceListModel m_Elements;
    private final JList m_List;
    private final ClassCollection m_Collection;
    private final Set m_RemovedItems;
    private final Set m_CreatedItems;
    private final List<ObjectChangeListener> m_ObjectChangeListeners;

    public CollectionPropertyPanel(Object TheObject, ICollectionWrapper<T> wrapper) {
        String className = ClassUtilities.shortClassName(wrapper.getPropertyClass());

        m_ObjectChangeListeners = new CopyOnWriteArrayList<ObjectChangeListener>();
        m_CollectionName = wrapper.getCollectionName();
        m_Collection = ClassAnalyzer.getCollection(TheObject, m_CollectionName);
        m_RemovedItems = new HashSet();
        m_CreatedItems = new HashSet();
        m_Elements = new NonReplaceListModel();
        m_List = new JList(m_Elements);
        setSubject(TheObject);
        setLayout(new BorderLayout());
        setBorder(UIUtilities.buildLabeledBorder(ResourceString.parameterToText(className + "." + m_CollectionName)));
        add(new JScrollPane(m_List), BorderLayout.CENTER);
        JPanel acctions = buildActions(wrapper);
        add(acctions, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(getDefaultListWidth(), getDefaultListHeight()));
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
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyObjectChangeListeners(Object changed) {
        if (m_ObjectChangeListeners.isEmpty())
            return;
        for (ObjectChangeListener listener : m_ObjectChangeListeners) {
            listener.onObjectChange(changed, null);
        }
    }


    public int getDefaultListWidth() {
        return DEFAULT_LIST_WIDTH;
    }

    public int getDefaultListHeight() {
        return DEFAULT_LIST_HEIGHT;
    }


    protected JPanel buildActions(ICollectionWrapper<T> wrapper) {
        JPanel acctions = new JPanel();
        EditAction ea = new EditAction();
        Action addAction;
        if (hasOptionsMethod()) {
            addAction = new AddAction();
            m_List.addListSelectionListener((ListSelectionListener) addAction);
        } else {
            addAction = new CreateAction();
        }
        acctions.add(new JButton(addAction));
        acctions.add(new JButton(ea));

        if (wrapper.isRemoveSupported()) {
            RemoveAction ra = new RemoveAction();
            acctions.add(new JButton(ra));
            m_List.addListSelectionListener(ra);
        }

        m_List.addListSelectionListener(ea);
        m_List.addMouseListener(new DoubleClickListener());
        return acctions;
    }

    public String getCollectionName() {
        return m_CollectionName;
    }

    public Class getType() {
        return m_Collection.getType();
    }

    public void setSubject(Object subject) //throws IllegalSubjectException
    {
        super.setSubject(subject);
        try {
            Method method = m_Collection.getItemsMethod();
            Object[] items = (Object[]) method.invoke(getSubjectObject());
            m_Elements.clear();
            if (INamedObject.class.isAssignableFrom(getType())) {
                Arrays.sort(items, INamedObject.COMPARATOR);
            }
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];
                m_Elements.addElement(item);
                if (item instanceof ObjectChangeEventGenerator)
                    ((ObjectChangeEventGenerator) item).addObjectChangeListener(this);
            }
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }

    }

    public void removeAll() {
        super.removeAll();

    }

    public Object[] getCurrentItems() {
        Object[] ret = new Object[m_Elements.size()];
        m_Elements.copyInto(ret);
        return ret;
    }

    public void setSelectedValue(T item) {
        m_List.setSelectedValue(item, true);
    }

    public void addItem(T item) {
        m_Elements.addElement(item);
        if (item instanceof ObjectChangeEventGenerator)
            ((ObjectChangeEventGenerator) item).addObjectChangeListener(this);
        setSelectedValue(item);
    }

    public void removeItem(T item) {
        m_Elements.addElement(item);
        if (item instanceof ObjectChangeEventGenerator)
            ((ObjectChangeEventGenerator) item).removeObjectChangeListener(this);
    }

    public void clearItems() {
        Object[] items = new Object[m_Elements.size()];
        m_Elements.copyInto(items);
        for (int i = 0; i < items.length; i++) {
            Object item = items[i];
            if (item instanceof ObjectChangeEventGenerator)
                ((ObjectChangeEventGenerator) item).removeObjectChangeListener(this);
        }
        m_Elements.clear();
    }

    public void setCurrentItems(T[] items) {
        m_Elements.clear();
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            m_Elements.addElement(item);
        }
    }

    public void onObjectChange(Object changed, Object addedData) {
        onItemChanged((T) changed);

    }

    public void onItemChanged(T item) {
        m_List.repaint(500);
    }


    protected Object[] getSelections() {
        return m_List.getSelectedValues();
    }

    protected T[] callMethod(String methodName) {
        return callMethod(getClass(), methodName, this);
    }

    protected T[] callMethod(Class cls, String methodName, Object target) {
        try {
            Method m = cls.getMethod(methodName);
            return (T[]) m.invoke(target);
        } catch (NoSuchMethodException e) {
            throw new WrappingException(e);
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }
    }

    protected boolean hasOptionsMethod() {
        Method method = m_Collection.getAddMethod();
        OptionMethod om = method.getAnnotation(OptionMethod.class);
        if (om != null)
            return true;
        OptionSource os = method.getAnnotation(OptionSource.class);
        if (os != null)
            return true;
        return false;
    }

    protected T doAdd() {
        Method method = m_Collection.getAddMethod();
        OptionMethod om = method.getAnnotation(OptionMethod.class);
        T[] options;
        if (om != null) {
            String name = om.MethodName();
            options = callMethod(name);
        } else {
            OptionSource os = method.getAnnotation(OptionSource.class);
            String name = os.MethodName();
            Class sourceClass = os.SourceClass();
            options = callMethod(sourceClass, name, null); // methd must be static

        }
        List<Object> holder = new ArrayList<Object>();
        for (int i = 0; i < options.length; i++) {
            T option = options[i];
            if (!m_Elements.contains(option))
                holder.add(option);
        }
        if (holder.size() == 0)
            return null;

        options = (T[]) Array.newInstance(options.getClass().getComponentType(), holder.size());
        holder.toArray(options);
        AddMemberDialog<T> dlg = new AddMemberDialog<T>(options, true, this);
        T[] items = dlg.selectAddedItems();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];
                if (!m_Elements.contains(item))
                    m_Elements.addElement(item);
            }
            return items[0];
        }
        return null;
    }

    protected abstract T doCreate();

    protected abstract void doEdit();

    private class RemoveAction extends AbstractAction implements ListSelectionListener {
        private RemoveAction() {
            putValue(Action.NAME, ResourceString.parameterToText("Remove"));
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            Object[] selkections = m_List.getSelectedValues();
            for (int i = 0; i < selkections.length; i++) {
                Object selkection = selkections[i];
                m_RemovedItems.add(selkection);
                m_CreatedItems.remove(selkection);
                m_Elements.removeElement(selkection);
            }

        }

        public void valueChanged(ListSelectionEvent e) {
            Object[] selkections = getSelections();
            setEnabled(selkections.length > 0);
        }
    }

    private class DoubleClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getClickCount() == 2 && m_List.getSelectedValues().length == 1) {
                doEdit();
            }
        }
    }

    protected class EditAction extends AbstractAction implements ListSelectionListener {
        private EditAction() {
            putValue(Action.NAME, ResourceString.parameterToText("Edit") + "...");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            doEdit();
        }

        public void valueChanged(ListSelectionEvent e) {
            Object[] selkections = m_List.getSelectedValues();
            setEnabled(selkections.length == 1);
        }
    }

    protected class CreateAction extends AbstractAction {
        private CreateAction() {
            putValue(Action.NAME, ResourceString.parameterToText(m_CollectionName + " Create") + "...");
        }

        public void actionPerformed(ActionEvent e) {
            Object created = doCreate();
            if (created != null) {
                m_CreatedItems.add(created);
                m_Elements.addElement(created);
            }
        }

    }

    protected class AddAction extends AbstractAction implements ListSelectionListener {
        private AddAction() {
            putValue(Action.NAME, ResourceString.parameterToText("Add") + "...");
        }

        public void actionPerformed(ActionEvent e) {
            Object created = doAdd();
            if (created != null) {
                m_Elements.addElement(created);

            }
        }

        public void valueChanged(ListSelectionEvent e) {
            Object[] selkections = m_List.getSelectedValues();
            setEnabled(selkections.length > 0);
        }

    }

    public JComponent[] getStylizableComponents() {
        JComponent[] ret = {this};
        return ret;
    }

    public JComponent getStylizableSelf() {
        return this;
    }
}
