package com.lordjoe.ui;

import com.lordjoe.utilities.*;
import com.lordjoe.lib.xml.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;

/**
 * com.lordjoe.ui.JCollectionTable
 *
 * @author Steve Lewis
 * @date Nov 14, 2007
 */
public class JCollectionTable<T> extends JTable
{
    public static JCollectionTable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JCollectionTable.class;

    private CollectionTableModel<T> m_Model;
    private TableRemoveRenderer<T> m_DeleteButton;
    private TableEditRenderer<T> m_EditButton;  // todo make this an edit button

    public JCollectionTable(CollectionTableModel<T> model)
    {
        super(model);
        m_Model = model;
        if (model.isDeleteButtonShown()) {
            m_DeleteButton = new TableRemoveRenderer(this, model.getDeleteButtonColumn());
        }
        if (model.isEditButtonShown()) {   // todo make this an edit button
            m_EditButton = new TableEditRenderer(this, model.getEditButtonColumn());
        }
    }

    @Override
    public TableModel getModel()
    {
       TableModel ret = super.getModel();
        return ret;
    }

    /**
     * Invoked when this table's <code>TableModel</code> generates
     * a <code>TableModelEvent</code>.
     * The <code>TableModelEvent</code> should be constructed in the
     * coordinate system of the model; the appropriate mapping to the
     * view coordinate system is performed by this <code>JTable</code>
     * when it receives the event.
     * <p/>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     * <p/>
     * Note that as of 1.3, this method clears the selection, if any.
     */
    public void tableChanged(TableModelEvent e)
    {
        super.tableChanged(e);
        if(m_Model == null)
            return;
        if (m_DeleteButton != null && m_Model.isDeleteButtonShown()) {
            int deleteCol = m_Model.getDeleteButtonColumn();
            TableColumnModel columnModel = getColumnModel();
            TableColumn colx = columnModel.getColumn(deleteCol);
            colx.setCellRenderer(m_DeleteButton);
            colx.setCellEditor(m_DeleteButton);
        }
        if (m_EditButton != null && m_Model.isEditButtonShown()) {
            int deleteCol = m_Model.getEditButtonColumn();
            TableColumnModel columnModel = getColumnModel();
            TableColumn colx = columnModel.getColumn(deleteCol);
            colx.setCellRenderer(m_EditButton);
            colx.setCellEditor(m_EditButton);
        }

    }




    public static void main(String[] args)
    {
        CollectionHolderImpl<NameValue> data = new CollectionHolderImpl<NameValue>(
                NameValue.EMPTY_ARRAY);
        data.addCollectionMember("MyCollection",new NameValue("Fee", "Fie"));
        data.addCollectionMember("MyCollection",new NameValue("Foe", "Fum"));
        data.addCollectionMember("MyCollection",new NameValue("I", "Smell"));
        CollectionTableModel<NameValue> model = new CollectionTableModel<NameValue>(data);
        model.setDeleteButtonShown(true);
        model.addDisplayedProperty("name", "Name");
        model.addDisplayedProperty("value", "Value");

        JCollectionTable<NameValue> table = new JCollectionTable<NameValue>(model);
        JDialog dlg = new JDialog();
        dlg.setTitle("Test Table");
        dlg.setLayout(new GridLayout(1, 1));
        dlg.add(new JScrollPane(table));
        dlg.setSize(400, 300);
        dlg.setVisible(true);


    }

}
