/* 
    file OffsetTableModel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import java.awt.*;
import javax.swing.event.*;
import com.lordjoe.utilities.*;

/**
    This class is used by SubTable - a class derived from JTable which represents 
    only a portion of the columns held by the full table model. The Proxy model is
    an adapter which maps from local columns to global columns
*/

public class OffsetTableModel implements OverlordTableModelInterface
{
   private OverlordGrid m_RealModel;
   private int m_StartCol;
   private int m_EndCol;

    public OffsetTableModel()
    {
        Assertion.doNada();
}

    public OffsetTableModel(OverlordGrid realModel,int startCol, int endCol) 
    {
        this();
        m_RealModel = realModel;
        m_StartCol = startCol;
        m_EndCol   = endCol;
    }
    
    public OverlordGrid getRealModel() { return(m_RealModel); }
    public int getStartCol() { return(m_StartCol); }
    public int getEndCol()   { return(m_EndCol); }

    public void setStartCol(int newStart)
    {
        if(m_StartCol == newStart) 
            return;
         m_StartCol = newStart;
         m_RealModel.fireTableDataChanged();
    }
    
    public void setEndCol(int newEnd)
    {
        if(m_EndCol == newEnd) 
            return;
         m_EndCol = newEnd;
         m_RealModel.fireTableDataChanged();
    }
   
    protected int toRealColumn(int column)
    {
        return(column); // seems the column model does all the work
        // return(m_StartCol + column);
    }
    
    protected int fromRealColumn(int column)
    {
        return(column); // seems the column model does all the work
        // return(column - m_StartCol);
    }

//
// Default Implementation of the Interface
//

    /**
     *  Return a default name for the column using spreadsheet conventions:
     *  A, B, C, ... Z, AA, AB, etc.
     */
    public String getColumnName(int column) {
        return(m_RealModel.getColumnName(toRealColumn(column)));
    }

    public void setColumnName(int column,String name) {
        m_RealModel.setColumnName(toRealColumn(column),name);
    }
    public boolean isColumnSelectionSupported() {
        return(m_RealModel.isColumnSelectionSupported());
    }
    
    public void setColumnSelectionSupported(boolean b){
        m_RealModel.setColumnSortSupported(b);
    }
    
    public boolean isColumnSortSupported() {
        return(m_RealModel.isColumnSortSupported());
    }
    
    public void    setColumnSortSupported(boolean b){
        m_RealModel.setColumnSortSupported(b);
    }
    
    public void setSortColumn(int col) {
        m_RealModel.setSortColumn(col + m_StartCol);
    }
    
    public int getSortColumn()
    {
        return(m_RealModel.getSortColumn() - m_StartCol);
    }
    
    // Row Headers - only first table can do this
    public boolean isUsingRowHeaders() {
        if(m_StartCol == 0)
            return(m_RealModel.isUsingRowHeaders());
        return(false);
    }
    
    public void setUsingRowHeaders(boolean doit) {
        if(m_StartCol == 0)
            m_RealModel.setUsingRowHeaders(doit);
    }
    
    
    
      /**{ method
          @name isCellSelected
          @param Row - row
          @param Col  - col
          @return - true if a cell is selected
          @function - tests is a cell is selected -
      }*/
      public boolean isCellSelected(int Row,int Col) {
         int realCol = toRealColumn(Col);
      	 return(m_RealModel.isCellSelected(Row,realCol));
      }

    /**
     * Convenience method for locating columns by name.
     * Implementation is naive so this should be overridden if
     * this method is to be called often. This method is not
     * in the TableModel interface and is not used by the JTable.
     */
    public int findColumn(String columnName) {
        return(fromRealColumn(m_RealModel.findColumn(columnName)));
    }

    /**
     *  Returns Object.class by default
     */
    public Class getColumnClass(int columnIndex) {
        return(m_RealModel.getColumnClass(toRealColumn(columnIndex)));
    }

    /**
     *  This default implementation returns false for all cells
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return(m_RealModel.isCellEditable(rowIndex,toRealColumn(columnIndex)));
    }

    /**
     *  Used to set data in the model - viewed data is different
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        m_RealModel.setValueAt(aValue,rowIndex,toRealColumn(columnIndex));
    }


    /**
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return(m_RealModel.getValueAt(rowIndex,toRealColumn(columnIndex)));
    }
        
    public boolean isTree() 
    {
        return(m_RealModel.isTree());
    }
    
    public boolean isLabReport() 
    {
        return(m_RealModel.isLabReport());
    }
    
    public void setLabReport(boolean doit) 
    {
        m_RealModel.setLabReport(doit);
    }
    
    
    
    public int getRowCount()
    {
        return(m_RealModel.getRowCount());
    }

    public int getColumnCount()
    {
        // m_EndCol == -1 says run to end of model
        if(m_EndCol >= 0)
            return(m_EndCol - m_StartCol + 1);
        else
            return(m_RealModel.getColumnCount() - m_StartCol);
    }


    public Color getForeColor(int rowIndex, int columnIndex) {
        return(m_RealModel.getForeColor(rowIndex,columnIndex + m_StartCol));
    }
    
    public Color getBackColor(int rowIndex, int columnIndex) {
        return(m_RealModel.getBackColor(rowIndex,columnIndex + m_StartCol));
    }

  public boolean isHeader3D() {
     return(m_RealModel.isHeader3D());
  }
    
    public Color getHeaderBackground(){
  	    return(m_RealModel.getHeaderBackground()); 
    }
    
    
    public Color getHeaderForeground(){
  	    return(m_RealModel.getHeaderForeground()); 
    }

    public Color getBackground(){
  	    return(m_RealModel.getBackground()); 
    }
    
    
    public Color getForeground(){
  	    return(m_RealModel.getForeground()); 
    }


//
//  Managing Listeners
//

    public void addTableModelListener(TableModelListener l) {
	    m_RealModel.addTableModelListener(l);
    }

    public void removeTableModelListener(TableModelListener l) {
	    m_RealModel.removeTableModelListener(l);
    }

//
//  Fire methods
//

    /**
     * Notify all listeners that all cell values in the table's rows may have changed.
     * The number of rows may also have changed and the JTable should redraw the
     * table from scratch. The structure of the table, ie. the order of the
     * columns is assumed to be the same.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableDataChanged() {
        Assertion.fatalError(); // this is done by real model
    }

    /**
     * Notify all listeners that the table's structure has changed.
     * The number of columns in the table, and the names and types of
     * the new columns may be different from the previous state.
     * If the JTable recieves this event and its <I>autoCreateColumnsFromModel</I>
     * flag is set it discards any TableColumns that it had and reallocates
     * default ones in the order they appear in the model. This is the
     * same as calling <code>setModel(TableModel)</code> on the JTable.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableStructureChanged() {
        Assertion.fatalError(); // this is done by real model
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been inserted.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsInserted(int firstRow, int lastRow) {
        Assertion.fatalError(); // this is done by real model
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been updated.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        Assertion.fatalError(); // this is done by real model
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been deleted.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        Assertion.fatalError(); // this is done by real model
    }

    /**
     * Notify all listeners that the value of the cell at (row, column)
     * has been updated.
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableCellUpdated(int row, int column) {
      //  fireTableChanged(new TableModelEvent(this, row, row, column));
    }

    /**
     * Forward the given notification event to all TableModelListeners that registered
     * themselves as listeners for this table model.
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableChanged(TableModelEvent e) {
        Assertion.fatalError(); // this is done by real model
    }

/*
    public boolean isLeaf(int rowIndex) 
    {
        return(m_RealModel.isLeaf(rowIndex));
    }
    
    public int getIndent(int rowIndex) 
    {
        return(m_RealModel.getIndent(rowIndex));
    }

    public int getMaxIndent()
    {
        return(m_RealModel.getMaxIndent());
    }

    public boolean isCollapsed(int rowIndex) 
    {
        return(m_RealModel.isCollapsed(rowIndex));
    }
    
    public void setCollapsed(int rowIndex,boolean doit) 
    {
        m_RealModel.setCollapsed(rowIndex,doit);
    }
*/
} // End of class OffsetTableModel
