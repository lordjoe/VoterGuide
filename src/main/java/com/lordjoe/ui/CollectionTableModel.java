package com.lordjoe.ui;

import com.lordjoe.utilities.*;
import com.lordjoe.lib.xml.*;

import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;

/**
 * com.lordjoe.ui.CollectionTableModel
 *
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public class CollectionTableModel<T> extends AbstractTableModel implements
        CollectionChangeListener<T>
{
    public static CollectionTableModel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CollectionTableModel.class;

    private ICollectionHolder<T> m_Subject;
    private final List<String> m_DisplayedProperties;
    private final Map<String, String> m_DisplayedPropertyNames;
    private final Map<String, IAdapter> m_Adapters;
    private boolean m_DeleteButtonShown;
    private boolean m_EditButtonShown;
    private IObjectEditor<T> m_Editor;

    /**
     * Constructs a default <code>DefaultTableModel</code>
     * which is a m_Table of zero columns and zero rows.
     */
    public CollectionTableModel(ICollectionHolder<T> pSubject)
    {
        m_DisplayedProperties = new ArrayList<String>();
        m_DisplayedPropertyNames = new HashMap<String, String>();
        m_Adapters = new HashMap<String, IAdapter>();
        setSubject(pSubject);
        pSubject.addCollectionChangeListener(this);
    }

    /**
     * add a property to the list of properties dispayed
     *
     * @param propertyName
     * @param displayedName
     */
    public void addDisplayedProperty(String propertyName, String displayedName)
    {
        if (m_DisplayedProperties.contains(propertyName))
            return;
        m_DisplayedProperties.add(propertyName);
        if (displayedName != null)
            m_DisplayedPropertyNames.put(propertyName, displayedName);
        fireTableChanged(new TableModelEvent(this));
    }

    /**
     * add an adapter to handle a specific property
     *
     * @param propertyName
     * @param adapter
     */
    public void addPropertyAdapter(String propertyName, IAdapter adapter)
    {
        m_Adapters.put(propertyName, adapter);
        fireTableChanged(new TableModelEvent(this));
    }

    /**
     * remove an adapter
     *
     * @param propertyName
     */
    public void removePropertyAdapter(String propertyName)
    {
        m_Adapters.remove(propertyName);
        fireTableChanged(new TableModelEvent(this));
    }

    public void removeDisplayedProperty(String propertyName)
    {
        m_DisplayedProperties.remove(propertyName);
        m_DisplayedPropertyNames.remove(propertyName);
        fireTableChanged(new TableModelEvent(this));
    }

    public ICollectionHolder<T> getSubject()
    {
        return m_Subject;
    }

    public void setSubject(ICollectionHolder<T> pSubject)
    {
        if (m_Subject == pSubject)
            return;
//        if (m_Subject != null)
//            m_Subject.removeCompositeFilterChangeListener(this);
        m_Subject = pSubject;
//        m_Subject.addCompositeFilterChangeListener(this);
        rePopulate();
    }

    public boolean isDeleteButtonShown()
    {
        return m_DeleteButtonShown;
    }

    public int getDeleteButtonColumn()
    {
        return m_DisplayedProperties.size();
    }

    public IObjectEditor<T> getEditor()
    {
        return m_Editor;
    }

    public void setEditor(IObjectEditor<T> pEditor)
    {
        m_Editor = pEditor;
    }

    public void setDeleteButtonShown(boolean pDeleteButtonShown)
    {
        m_DeleteButtonShown = pDeleteButtonShown;
    }

    public boolean isEditButtonShown()
    {
        return m_EditButtonShown;
    }

    public void setEditButtonShown(boolean pEditButtonShown)
    {
        m_EditButtonShown = pEditButtonShown;
    }


    public int getEditButtonColumn()
    {
        int ret =  m_DisplayedProperties.size();
        if(isDeleteButtonShown())
            ret++;
        return ret;
    }


    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount()
    {
        return m_Subject.getMemberCount();
    }



    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */

    public int getColumnCount()
    {
        int ret = m_DisplayedProperties.size();
        if(isDeleteButtonShown())
            ret++;
        if(isEditButtonShown())
            ret++;
        return ret;
    }

    public boolean isCellEditable(int row, int column)
    {
        return column >= m_DisplayedProperties.size();
    }

    public String getColumnName(int column)
    {
        if (column < m_DisplayedProperties.size()) {
            String prop = m_DisplayedProperties.get(column);
            String display = m_DisplayedPropertyNames.get(prop);
            if (display != null)
                return display;
            return Util.capitalize(prop);
        }
        return "";
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (rowIndex >= m_Subject.getMemberCount())
            return "";
        T item = m_Subject.getCollectionMember(rowIndex);
        if (columnIndex < m_DisplayedProperties.size()) {
            String propName = m_DisplayedProperties.get(columnIndex);
            Object o = ClassAnalyzer.getProperty(item, propName);
            return o;
        }
        else return item;

    }


    /**
     * handle member added to a collection
     *
     * @param added non-null added member
     */
    public void onMemberAdded(String collection,T added)
    {
        rePopulate();
    }

    /**
     * handle member removed from a collection
     *
     * @param added non-null added member
     */
    public void onMemberRemoved(String collection,T added)
    {
        rePopulate();

    }

    /**
     * handle all members removed
     */
    public void onCollectionCleared(String collection)
    {
        rePopulate();

    }


    protected void rePopulate()
    {
        ICollectionHolder<T> subj = getSubject();
        T[] filters = subj.getCollectionMembers();
        fireTableStructureChanged();
    }



}
