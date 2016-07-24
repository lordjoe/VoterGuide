package com.lordjoe.ui.propertyeditor;

import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.AbstractDataObject;
import com.lordjoe.lang.CompositeIdentifiedObject;
import com.lordjoe.lang.ObjectChangeEventGenerator;
import com.lordjoe.lang.ObjectChangeListener;
import com.lordjoe.lib.xml.ClassAnalyzer;
import com.lordjoe.lib.xml.ClassCollection;
import com.lordjoe.propertyeditor.AddMemberDialog;
import com.lordjoe.propertyeditor.ICollectionWrapper;
import com.lordjoe.propertyeditor.OptionMethod;
import com.lordjoe.propertyeditor.OptionSource;
import com.lordjoe.ui.ClassAnalyzerUI;
import com.lordjoe.ui.general.PropertyTable;
import com.lordjoe.ui.general.SubjectDisplayPanel;
import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.ui.propertyeditor.PropertyTableCollectionPanel
 *
 * @author Steve Lewis
 * @date Feb 19, 2008
 */
public abstract class PropertyTableCollectionPanel<T> extends SubjectDisplayPanel implements
        ICollectionPropertyPanel<T>, ObjectChangeListener, CollectionChangeListener
{
    public static PropertyTableCollectionPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = PropertyTableCollectionPanel.class;

    private final T[] m_Template;
    private final String m_CollectionName;
    private final NonReplaceListModel m_Elements;
    private final PropertyTable<T> m_List;
    private final ClassCollection m_Collection;
    private final Set m_RemovedItems;
    private final Set m_CreatedItems;

    public PropertyTableCollectionPanel(Object TheObject, ICollectionWrapper<T> wrapper,T[] template)
    {
        m_Template = template;
        m_CollectionName = wrapper.getCollectionName();
        String className = ClassUtilities.shortClassName(wrapper.getPropertyClass());
        m_Collection = ClassAnalyzer.getCollection(TheObject, m_CollectionName);
        m_RemovedItems = new HashSet();
        m_CreatedItems = new HashSet();
        m_Elements = new NonReplaceListModel();
        m_List = new PropertyTable<T>(getDisplayedProperties(),m_Template);
        setSubject(TheObject);
        setLayout(new BorderLayout());
        setBorder(UIUtilities.buildLabeledBorder(ResourceString.parameterToText(className + "." + m_CollectionName)));
        m_List.setBackground(getPanelBackground());
        add(new JScrollPane(m_List), BorderLayout.CENTER);
        JPanel acctions = buildActions();
        acctions.setBackground(getPanelBackground());
        add(acctions, BorderLayout.SOUTH);
        setBackground(getPanelBackground());
    }

    public String[] getDisplayedProperties()
     {
         throw new UnsupportedOperationException("Fix This"); // ToDo
     }

    public Color getPanelBackground()
     {
         return Color.white;
     }

     protected abstract JPanel buildActions();

    public String getCollectionName() {
        return m_CollectionName;
    }


    /**
     * handle member added to a collection
     *
     * @param collection possibly null collection name
     * @param added      non-null added member
     */
    public void onMemberAdded(String collection, Object added)
    {
        if (isMyCollection(collection)) {
            addItem((T)added);
             m_List.addMember((T)added);
         }

    }

    protected void addCreatedItem(T added)
    {
        addItem( added);
        m_CreatedItems.add(added);
        if(!m_Elements.contains(added))
            m_List.addMember(added);
    }

    protected boolean isMyCollection(String collection) {
        return collection.equals(getCollectionName());
    }

    /**
     * handle member removed from a collection
     *
     * @param collection possibly null collection name
     * @param added      non-null added member
     */
    public void onMemberRemoved(String collection, Object removed)
    {
        if (isMyCollection(collection)) {
            removeItem((T)removed);
              m_List.removeMember((T)removed);
         }
    }

    /**
     * handle all members removed
     *
     * @param collection possibly null collection name
     */
    public void onCollectionCleared(String collection)
    {
        if (isMyCollection(collection)) {
            throw new UnsupportedOperationException("Fix This"); // ToDo
         }

    }

    public Class getType() {
        return m_Collection.getType();
    }

    protected T[] getSelectedValues()
    {
        return m_List.getSelectedValues();
    }

    public void setSubject(Object subject) //throws IllegalSubjectException
    {
        super.setSubject(subject);
        try {
            Method method = m_Collection.getItemsMethod();
            Object[] items = (Object[]) method.invoke(getSubjectObject());
            clearItems();
            m_List.clear();
            if (INamedObject.class.isAssignableFrom(getType())) {
                Arrays.sort(items, INamedObject.COMPARATOR);
            }
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];
                T su = (T)item;
                addItem(su);
                m_List.addMember(su);
              }
              if(subject instanceof ICollectionHolder)
                  ((ICollectionHolder)subject).addCollectionChangeListener(this);
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

    public void addItem(T item) {
        m_Elements.addElement(item);
        if(item instanceof ObjectChangeEventGenerator)
             ((ObjectChangeEventGenerator)item).addObjectChangeListener(this);
    }

    public void removeItem(T item) {
        m_Elements.removeElement(item);
        if(item instanceof ObjectChangeEventGenerator)
             ((ObjectChangeEventGenerator)item).removeObjectChangeListener(this);
    }

    public void setCurrentItems(T[] items) {
        clearItems();
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            addItem(item);
        }
    }


    public void clearItems() {
        Object[] items = new Object[m_Elements.size()];
         m_Elements.copyInto(items);
        for (int i = 0; i < items.length; i++) {
            Object item = items[i];
            removeItem((T)item);
        }
         m_Elements.clear();
    }



    protected void doEdit() {
        Object[] selkections = getSelections();
        ClassAnalyzerUI.editObject((AbstractDataObject) selkections[0]);
        updateMember((T)selkections[0]);
    }



    protected void updateMember(T member) {
        m_List.updateMember(member);
    }


    protected Object[] getSelections() {
        return m_List.getSelectedValues();
    }

    protected PropertyTable<T> getPropertyTable()
    {
        return m_List;
    }

    protected T[]callMethod(String methodName) {
        return callMethod(getClass(), methodName, this);
    }

    public void onObjectChange(Object item, Object addedData) {
        onItemChanged((T) item);
    }

    public void onItemChanged(T item)
    {
        m_List.onObjectChange(item,null);
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
                T item = items[i];
                if (!m_Elements.contains(item))
                    addItem(item);
            }
            return items[0];
        }
        return null;
    }

    protected T doCreate() {
        return (T) ClassAnalyzerUI.createSubObject(getCollectionName(), (CompositeIdentifiedObject) getSubjectObject(), getType());
    }

    public JComponent[] getStylizableComponents() {
        JComponent[] ret = {this};
        return ret;
    }

    public JComponent getStylizableSelf() {
        return this;
    }

    protected class RemoveAction extends AbstractAction implements ListSelectionListener
    {
        public RemoveAction() {
            putValue(Action.NAME, ResourceString.parameterToText("Remove"));
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            Object[] selkections = m_List.getSelectedValues();
            for (int i = 0; i < selkections.length; i++) {
                Object selkection = selkections[i];
                T spCount = (T)selkection;
                m_RemovedItems.add(spCount);
                m_CreatedItems.remove(spCount);
                removeItem((T)spCount);
               m_List.removeMember(spCount);
            }

        }

        public void valueChanged(ListSelectionEvent e) {
            Object[] selkections = getSelections();
            setEnabled(selkections.length > 0);
        }
    }

}
