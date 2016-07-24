package com.lordjoe.ui;

import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * com.lordjoe.ui.TableRemoveRenderer
 *
 * @author Steve Lewis
 * @date Oct 5, 2007
 */
public class TableEditRenderer<T> extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener
{
    private JTable m_Table;
    private JButton renderButton;
    private JButton editButton;
    private Object m_Filter;

    public TableEditRenderer(JTable table, int column)
    {
        super();
        this.m_Table = table;
        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn colx = columnModel.getColumn(column);
        colx.setCellRenderer(this);
        colx.setCellEditor(this);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText("Edit");
        CollectionTableModel<T> mdl = (CollectionTableModel<T>)m_Table.getModel();

        return renderButton;
    }

    /**
     * Returns true.
     *
     * @param e an event object
     * @return true
     */
    public boolean isCellEditable(EventObject e)
    {
        return true;

    }

    /**
     * Returns true.
     *
     * @param anEvent an event object
     * @return true
     */
    public boolean shouldSelectCell(EventObject anEvent)
    {
        return super.shouldSelectCell(anEvent);

    }

    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        m_Filter = value;
        editButton.setText("Remove");
        return editButton;
    }

    public Object getCellEditorValue()
    {
        return m_Filter;
    }

    public void actionPerformed(ActionEvent e)
    {
        fireEditingStopped();
        CollectionTableModel<T> mdl = (CollectionTableModel<T>)m_Table.getModel();
        ICollectionHolder<T> collection = mdl.getSubject();
        collection.removeCollectionMember((String)null,(T)m_Filter);
    }
}