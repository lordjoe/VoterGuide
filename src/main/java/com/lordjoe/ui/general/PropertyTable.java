package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.ObjectChangeListener;
import com.lordjoe.lib.xml.ClassAnalyzer;
import com.lordjoe.lib.xml.ClassProperty;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.utilities.ClassUtilities;
import com.lordjoe.utilities.ResourceString;
import com.lordjoe.utilities.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * com.lordjoe.ui.general.PropertyTable
 *
 * @author Steve Lewis
 * @date Nov 15, 2006
 */
public class PropertyTable<T> extends JTable
        implements ObjectChangeListener {


    private String[] m_Properties;
    private DefaultTableModel m_Model;
    private DefaultListSelectionModel m_SelectModel;
    private final List<T> m_Items;
    private final List<T> m_DisplayedItems;
    private final T[] m_EmptyArray;

    public PropertyTable(String[] props, T[] data) {
        this(props,Util.displayStrings(props),data);
    }
    public PropertyTable(String[] props,String[] userProps, T[] data,ObjectActionTemplate<T>... actions) {
        m_Properties = props;
        Class<? extends Object[]> aClass = data.getClass();
        Class<?> type = aClass.getComponentType();
        m_EmptyArray = (T[])Array.newInstance(type,0);
        m_Model = new PropertyTableModel(type, props,actions);
        m_SelectModel = new DefaultListSelectionModel();
        m_Items = new ArrayList<T>();
        m_DisplayedItems = new ArrayList<T>();
        setModel(m_Model);
        setSelectionModel(m_SelectModel);
        m_SelectModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setData(data);
        // set column names
        TableColumnModel tableColumnModel = getColumnModel();
        for (int i = 0; i < userProps.length; i++) {
            String rop = userProps[i];
            TableColumn column = tableColumnModel.getColumn(i);
            column.setHeaderValue(rop);
        }
        //TODO sorting not integrated in jdk 1.5:
        //setAutoCreateRowSorter(true);
    }

    public void onObjectChange(Object changed, Object addedData) {
        updateMember((T)changed);

    }

    public void clear() {
        m_SelectModel.setAnchorSelectionIndex(-1);
        m_SelectModel.setLeadSelectionIndex(-1);
        m_Model.setRowCount(0);
        m_DisplayedItems.clear();
     }

    protected List<T> getDisplayedItems()
    {
        return m_DisplayedItems;
    }

    /**
     * return the item represented in a row
     * @param row
     * @return possibly null item
     */
    public T getRowItem(int row)
    {
        return m_DisplayedItems.get(row);
    }
    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addListSelectionListener(ListSelectionListener added) {
        m_SelectModel.addListSelectionListener(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeListSelectionListener(ListSelectionListener removed) {
        m_SelectModel.addListSelectionListener(removed);
    }


    public T[] getSelectedValues() {
        int selectionIndex = m_SelectModel.getLeadSelectionIndex();
        if (selectionIndex == -1)
            return m_EmptyArray;
        if (selectionIndex >= m_Items.size())
            return m_EmptyArray;

        T[] ret = (T[])Array.newInstance(m_EmptyArray.getClass().getComponentType(),1);
        ret[0] =   m_Items.get(selectionIndex) ;
        return ret;
    }


    public String[] getProperties() {
        return m_Properties;
    }

    public void addMember(T added) {
        getDisplayedItems().add(added);
        m_Items.add(added);
        String[] cols = getProperties();
        Object[] values = new Object[cols.length];
        for (int col = 0; col < cols.length; col++) {
            String prop = cols[col];
            Object value = ClassAnalyzer.getProperty(added, prop);
            values[col] = value;
        }
        m_Model.addRow(values);
    }

    public void removeMember(T removed) {
        int index = m_Items.indexOf(removed);
        if (index > -1) {
            m_Model.removeRow(index);
            m_Items.remove(removed);
            getDisplayedItems().remove(removed);
        }
    }

    public void updateMember(T changed) {
         int row = m_Items.indexOf(changed);
        if (row == -1)
            return;
        String[] cols = getProperties();
        for (int col = 0; col < cols.length; col++) {
            String prop = cols[col];
            Object value = ClassAnalyzer.getProperty(changed, prop);
            m_Model.setValueAt(value, row, col);
        }
    }

    public void setData(Object[] data) {
        for (int row = 0; row < data.length; row++) {
            T rowObj = (T) data[row];
            addMember(rowObj);
        }
    }

    public DefaultTableModel getTableModel()
    {
        return m_Model;
    }

    protected DefaultListSelectionModel getSelectModel()
    {
        return m_SelectModel;
    }

    public static class TestData     {
        private String m_Fee;
        private Boolean m_Fie;
  //      private AgeClass m_Foe;

//        static {
//            AgeClass.populate(AgeClass.THIS_CLASS, "0;1;2;3;4;5");
//        }

        public TestData(int i) {
            m_Fee = "Fee" + i;
            m_Fie = (0 == i % 2 ? new Boolean(true) : new Boolean(false));
 //           m_Foe = (AgeClass) AgeClass.valueOf(AgeClass.THIS_CLASS, String.valueOf(i % 6));
        }


        public String getFee() {
            return m_Fee;
        }

//        public AgeClass getFoe() {
//            return m_Foe;
//        }
//
        public Boolean getFie() {
            return m_Fie;
        }


    }

    public static void main(String[] args) {
        DefaultFrame frame = new DefaultFrame();
        String[] props = {"fee", "fie"};
        TestData[] data = new TestData[3];
        for (int i = 0; i < data.length; i++) {
            data[i] = new TestData(i);
        }
        PropertyTable pt = new PropertyTable(props, data);
        JScrollPane js = UIUtilities.buildLabeledScroller("Fee Fie Fum", pt);
        frame.setMainDisplay(js);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    /**
     * internal table model for a PropertyTable.
     *
     * @author Scott
     */
    public class PropertyTableModel extends DefaultTableModel {

        private Class[] m_tablePropertyClasses;

        public PropertyTableModel(Class dataClass, String[] properties,ObjectActionTemplate<T>... actions) {
            super();
            m_tablePropertyClasses = new Class[properties.length + actions.length];
            int col = 0;
            for (; col < properties.length; col++) {
                String prop = properties[col];
                ClassProperty classProperty = ClassAnalyzer.getClassProperty(dataClass, prop);
                if (classProperty != null) {
                    Class type = classProperty.getType();
                    // we cannot handle primitives - make the property a Wrapper
                    if (type.isPrimitive())
                        type = ClassUtilities.primitiveToWrapper(type);
                    m_tablePropertyClasses[col] = type;
                } else {
                    classProperty = ClassAnalyzer.getClassProperty(dataClass, prop); // debug repeat
                    m_tablePropertyClasses[col] = Object.class;
                }
                String s = ResourceString.parameterToText(dataClass.getName() + "." + prop);
                addColumn(s);
            }
            for (int action = 0; action < actions.length; action++) {
                ObjectActionTemplate<T> theAction =  actions[action];
                String prop = theAction.getName();
                addColumn(prop);
                m_tablePropertyClasses[col++] = Action.class;
            }
        }

        /*
        * Provide class by lookup
        */
        public Class getColumnClass(int c) {
            return m_tablePropertyClasses[c];
        }

        /*
         * Don't allow editing.
         */
        public boolean isCellEditable(int row, int col) {
            return false;
        }

    }
}
