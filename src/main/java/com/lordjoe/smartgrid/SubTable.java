/* 
    file SubTable.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

abstract public class SubTable extends JTable
{

    private OverlordGrid m_Grid;

    public SubTable()
    {
    }

    public SubTable(OverlordGrid grid, int offset, int ncols)
    {
        super();
        m_Grid = grid;
        setAutoCreateColumnsFromModel(false);
        //      setAutoResizeMode(AUTO_RESIZE_OFF);
        setDoubleBuffered(true);
        setModel(new OffsetTableModel(m_Grid, offset, ncols));
        setColumnModel(createColumnModel(m_Grid.getColumnModel(), offset, ncols));
        setSelectionModel(m_Grid.getSelectionModel());

        getTableHeader().setColumnModel(getColumnModel());
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(true);
        if (!m_Grid.isHeaderShown())
            getTableHeader().setVisible(false);

        setShowGrid(grid.isShowGrid());
        if (isBorderNeeded())
            setBorder(LineBorder.createGrayLineBorder());
        // Add a custom ui
        ComponentUI newUI = OverlordTableUI.createUI(this);
        setUI(newUI);
    }

    /**
     * Debugging code
     */
    public void setSelectionModel(ListSelectionModel m)
    {
        super.setSelectionModel(m);
    }

    public boolean isBorderNeeded()
    {
        return (true);
    }

    public OverlordGrid getGrid()
    {
        return (m_Grid);
    }

    abstract TableColumnModel createColumnModel(OverlordTableColumnModel basemodel, int offset,
                                                int ncols);

    //
    // Overide Steve Lewis - nevcer ever do this
    public boolean getScrollableTracksViewportWidth()
    {
        return (false);
    }

    public void setModelOffset(int newOffset)
    {
        OffsetTableModel realModel = (OffsetTableModel) getModel();
        if (realModel.getStartCol() == newOffset)
            return;
        realModel.setStartCol(newOffset);
    }

    public int getModelOffset()
    {
        OffsetTableModel realModel = (OffsetTableModel) getModel();
        return (realModel.getStartCol());
    }

    /**
     * { method
     *
     * @param Row - row
     * @param Col - col
     * @return - true if a cell is selected
     * @name isCellSelected
     * @function - tests is a cell is selected -
     * }
     */
    public boolean isCellSelected(int Row, int Col)
    {
        OffsetTableModel realModel = (OffsetTableModel) getModel();
        try {
            return (realModel.isCellSelected(Row, Col));
        }
        catch (Exception ex) {
            boolean test = realModel.isCellSelected(Row, Col);
            ex = null;
            return (false);
        }
    }


    // This really simply sets the sort column
    //
    public void setColumnSelectionInterval(int i, int j)
    {
        if (i == j) {
            OverlordTableModelInterface realModel = (OverlordTableModelInterface) getModel();
            // It is not a good idea to sort trees
            if (realModel.isTree())
                return;
            if (!realModel.isColumnSelectionSupported())
                return;
            if (!realModel.isColumnSortSupported())
                return;

            //    clearSelection();
            if (realModel.getSortColumn() == i)
                realModel.setSortColumn(-1); // no sort
            else
                realModel.setSortColumn(i);
        }
    }

    /**
     * Returns the default table header object which is
     * a JTableHeader.  Subclass can override this
     * method to return a different table header object
     *
     * @return the default table header object
     */
    protected JTableHeader createDefaultTableHeader()
    {
        return new OverlordTableHeader(columnModel, this);
    }


    protected TableColumnModel createDefaultColumnModel()
    {
        TableColumnModel ret = new DefaultTableColumnModel();
        return (ret);
    }

    public TableCellRenderer getDefaultRenderer(Class columnClass)
    {
        if (columnClass == java.lang.String.class) {
            //  return(new OverlordCellRenderer(new JLabel()));
            return (new OverlordCellRenderer());
            //   return(new OverlordCellRenderer(new JExtendedLabel()));
        }
        TableCellRenderer ret = super.getDefaultRenderer(columnClass);
        return (ret);
    }

    public boolean isHeader3d()
    {
        return (((OffsetTableModel) getModel()).isHeader3D());
    }

    public void setHeaderShown(boolean doit)
    {
        JTableHeader TheHeader = getTableHeader();
        if (TheHeader.isVisible() == doit)
            return;
        if (TheHeader != null)
            TheHeader.setVisible(doit);
        if (!doit) {
            Container parent = TheHeader.getParent();
            if (parent != null)
                parent.remove(TheHeader);
        }
        else {
            // put it back
            addHeaderToParent();
        }
    }

    // This version will place a header at the top of the document
    // See FixedTable to see alternative which adds the header to the
    // corner
    protected void addHeaderToParent()
    {
        getGrid().setColumnHeaderView(getTableHeader());
    }

    public void setColumnsToDesiredWidths()
    {
        TableColumnModel TCM = this.getColumnModel();
        Dimension size = getSize();
        int width = 0;
        for (int col = 0; col < TCM.getColumnCount(); col++) {
            if (col == 0 && !(this instanceof FixedTable))
                Assertion.doNada();
            int colWidth = getWidthToLargestCell(col);
            TableColumn TheColumn = TCM.getColumn(col);
            TheColumn.setWidth(colWidth);
            width += colWidth;
        }
        size.width = width;
        setSize(size);
    }


    /**
     * Programmatically starts editing the cell at <I>row</I> and
     * <I>column</I>, if the cell is editable.
     * To prevent the JTable from editing a particular table, column or
     * cell value, return false from the isCellEditable() method in the
     * TableModel interface.
     *
     * @param row    the row to be edited
     * @param column the column to be edited
     * @param e      event to pass into
     *               shouldSelectCell
     * @return false if for any reason the cell cannot be edited.
     * @throws IllegalArgumentException If <I>row</I> or <I>column</I>
     *                                  are not in the valid range
     */
    public boolean editCellAt(int row, int column, EventObject e)
    {
        boolean ret = super.editCellAt(row, column, e);
        if (ret)
            Assertion.doNada(); // this is a go
        return (ret);
    }

    /**
     * This tells the listeners the editor has ended editing
     */
    public void editingStopped(ChangeEvent e)
    {
        // Take in the new value
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            Object value = editor.getCellEditorValue();
            Object oldValue = getValueAt(editingRow, editingColumn);

            if (oldValue != null && oldValue.equals(value))
                return;
            // the event must be in terms of absolute column number
            GridEditEvent ev = new GridEditEvent(getGrid(), 0, editingRow,
                    editingColumn + getModelOffset(), oldValue, value);
            getGrid().fireCellEdited(ev);
            if (!ev.isConsumed() && ev.getNewValue() != null)
                setValueAt(ev.getNewValue(), editingRow, editingColumn);

            removeEditor();
        }
    }

    /**
     * This tells the listeners the editor has canceled editing
     */
    public void editingCanceled(ChangeEvent e)
    {
        super.editingCanceled(e);
    }


    public void sizeColumnsToFit(int resizingColumn)
    {
        // setColumnsToDesiredWidths();
    }

    /**
     * sets the column's width to the largest cell in the column.
     *
     * @param col which column to size
     */
    public int getWidthToLargestCell(int col)
    {
        TableColumnModel TCM = getColumnModel();
        TableModel TModel = getModel();

        if (col > TCM.getColumnCount())
            return (0);

        TableColumn aColumn = columnModel.getColumn(col);
        // get the header size to start
        TableCellRenderer tableCellRenderer = aColumn.getHeaderRenderer();

        // value for this header cell. Renderer needs it to compute size
        Object value = aColumn.getHeaderValue();

        int largest = 0;
        Component renderer = null;
        if (tableCellRenderer != null) {
            // get the header cell renderer for the column
            renderer = tableCellRenderer.getTableCellRendererComponent(this, value,
                    false, false, 0, col);

            // take this value as the starting largest value
            largest = renderer.getPreferredSize().width;
        }

        TableCellRenderer TheCellRenderer = aColumn.getCellRenderer();
        if (TheCellRenderer != null) {
            int nrows = this.getModel().getRowCount();
            // now walk the cells
            for (int i = 0; i < nrows; i++) {

                // value for this cell. Renderer needs it to compute size
                value = TModel.getValueAt(i, col + getModelOffset()); // !!! Hack - Hack Hack

                // get the cell renderer for the column
                renderer = TheCellRenderer.getTableCellRendererComponent(this, value,
                        false, false, i, col);

                // keep the widest one
                int currentWidth = renderer.getPreferredSize().width;
                largest = Math.max(largest, currentWidth);
            }

            // add margin width - margin is on both sides
            largest += (TCM.getColumnMargin() * 2);
        }
        return (largest);
    }

    /**
     * sets the column's width to the largest cell in the column.
     *
     * @param col which column to size
     */
    public void setWidthToLargestCell(int col)
    {
        int largest = getWidthToLargestCell(col);
        TableColumnModel TCM = this.getColumnModel();
        // finally, set the column width
        TCM.getColumn(col).setWidth(largest);
    }

    // We want the space needed for columns
    public Dimension getPreferredSize()
    {
        Dimension spacing = getIntercellSpacing();
        Dimension size = new Dimension(); // super.getPreferredSize();
        TableColumnModel TCM = this.getColumnModel();
        int width = 0;
        for (int col = 0; col < TCM.getColumnCount(); col++) {
            int colWidth = getWidthToLargestCell(col);
            TableColumn column = TCM.getColumn(col);
            if (column != null) {
                column.setWidth(colWidth);
                width += colWidth + spacing.width;
            }
        }
        size.height = (getRowCount()) * (getRowHeight() + spacing.height);
        size.width = width;
        return (size);
    }

    public Dimension getPreferredScrollableViewportSize()
    {
        Dimension d = getPreferredSize();
        return (d);
        // return getUI().getMinimumSize(this);
    }


    // debugging override
    public void setBounds(int left, int top, int width, int height)
    {
        if (width > 0 && height > 0)
            Assertion.doNada();
        super.setBounds(left, top, width, height);
    }

}

