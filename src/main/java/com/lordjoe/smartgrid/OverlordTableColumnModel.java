
package com.lordjoe.smartgrid;

import java.util.*;
import java.beans.*;
import javax.swing.table.*;
import javax.swing.event.*;
import com.lordjoe.utilities.*;

public class OverlordTableColumnModel extends DefaultTableColumnModel implements PropertyChangeListener {

  private OverlordGrid      m_Grid = null;
  private EventListenerList m_ListenerList = new EventListenerList();
  private boolean           m_MultipleSelectionAllowed;
  private boolean           m_ColumnSelectionAllowed;
  private boolean           m_EmptySelectionAllowed;

  public OverlordTableColumnModel(OverlordGrid grid) {
  	super();
    m_Grid = grid;
//    setSelectionModel(createSelectionModel());
    setSelectionModel(m_Grid);
    setMultipleSelectionAllowed(true);
    setEmptySelectionAllowed(true);
    setColumnSelectionAllowed(true);
  }

  public void refresh() {
    updateFixedColumns(getFixedColumnCount() > 0);
 //   updateScrollableColumns(getScrollableColumnCount() > 0);
    //Developer.report("refresh");
    m_Grid.doLayout();
  }

  public OverlordGrid getGrid() {  return(m_Grid); } 
    
  public int getFixedColumnCount() {
    return(m_Grid.getFixedColumns());
  }
  
  public int getFixedColumnIndex(int columnIndex) {
    int NFixed = getFixedColumnCount();
    return(columnIndex);
  }
  
  public int getScrollableColumnIndex(int columnIndex) {
    int NFixed = getFixedColumnCount();
    return(columnIndex + NFixed);
  }
  
  public int getScrollableColumnCount()
  {
    int NFixed = getFixedColumnCount();
    return(getColumnCount() - NFixed);
    
  }
  

  public void setFixedColumnCount(int value) {
    int numCols = getFixedColumnCount();
    if(value == numCols)
        return;
        
    TableColumnModelEvent notice = new TableColumnModelEvent(this, getColumnCount() - 1, 0);
    // Add or remove columns as necessary.
    if (value > numCols) {
      // Remove the columns from the scrollable columns and add them to the fixed columns
      for (int i = numCols; i < value; i++) {
        fireScrollableColumnRemoved(notice);
        updateFixedColumns(true);
        fireFixedColumnAdded(notice);
      }
    }
    else  {
      // Remove the columns from the fixed columns and add them to the scrollable columns
      for (int i = numCols-1; i >= value; i--) {
        fireFixedColumnRemoved(notice);
        updateScrollableColumns(true);
        fireScrollableColumnAdded(notice);
      }
    }
    refresh();
  }



  public void setColumnCount(int value) {
    int numCols = getColumnCount();
    if (value == numCols)
      return;

    // Add or remove columns as necessary.
    if (value > numCols) {
      if (m_Grid.getScrollableTable() == null)
				m_Grid.createScrollableTable(m_Grid.getFixedColumns());

      // Add columns
      for (int i = numCols; i < value; i++) {
      	OverlordTableColumn column = new OverlordTableColumn(i);
        column.setFont(m_Grid.getGridFont());
		column.addPropertyChangeListener(this);
        column.setWidth(OverlordGrid.DEFAULT_COLUMN_WIDTH);
        column.setHeaderValue("Column " + i);
        addColumn(column);
      }
    }
    else {
        while(value < numCols) {
            OverlordTableColumn column = (OverlordTableColumn)getColumn(numCols - 1);
            numCols--;
            removeColumn(column);
        }
    }
    refresh();
  }

  /* Interface TableColumnModel */

  public void addColumn(TableColumn column) {
    if(isFixed(column)) {
	    updateFixedColumns(true);
    }
    else {
      updateScrollableColumns(true);
    }
    column.addPropertyChangeListener(this);
    super.addColumn(column);
  }

  public void removeColumn(TableColumn column) {
    super.removeColumn(column);
  }

  public void moveColumn(int columnIndex, int newIndex) {
  }

  public void setColumnMargin(int index) {
  	super.setColumnMargin(index);
  }

  public int getColumnCount() {
    if(m_Grid == null || m_Grid.getColumnCount() <= 0) 
        return(0);
  	return super.getColumnCount();  // Warning: return also the status column
  }

  public Enumeration getColumns() {
    if(m_Grid == null || m_Grid.getColumnCount() <= 0) 
        return(NullEnumeration.getInstance());
        
  	return super.getColumns();  // Warning: return also the status column
  }

  public int getColumnIndex(Object identifier) {
  	return super.getColumnIndex(identifier);
  }

  public TableColumn getColumn(int identifier) 
  {
    try {
  	    return(super.getColumn(identifier));
    } 
    // Obviously I am having trouble here 
    catch(ArrayIndexOutOfBoundsException ex) {
        // repeat call for debugging
        TableColumn ret = super.getColumn(identifier);
      //  Assertion.fatalException(ex);
        return(null);
    }
  }

  public int getColumnMargin() {
  	return super.getColumnMargin();
  }

  public int getColumnIndexAtX(int index) {
  	return super.getColumnIndexAtX(index);
  }

  public int getTotalColumnWidth() {
    if(m_Grid == null || m_Grid.getColumnCount() <= 0) 
        return(0);
  	return super.getTotalColumnWidth();
  }
  

  public void setMultipleSelectionAllowed(boolean value) {
  	 m_MultipleSelectionAllowed = value;
  }

  public boolean getMultipleSelectionAllowed() {
  	return m_MultipleSelectionAllowed;
  }

  public void setEmptySelectionAllowed(boolean value) {
  	m_EmptySelectionAllowed = value;
  }

  public boolean getEmptySelectionAllowed() {
  	return(m_EmptySelectionAllowed);
  }

  public void setColumnSelectionAllowed(boolean value) {
  	m_ColumnSelectionAllowed = value;
  }

  public boolean getColumnSelectionAllowed() {
  	return(m_ColumnSelectionAllowed);
  }

  public void selectAll() {
//  	super.selectAll();
  }

  public void clearSelection() {
 // 	super.clearSelection();
  }

  public void makeSelectionEmpty() {
 // 	super.makeSelectionEmpty();
    /*m_SelectionModel.clearSelection();
    if (getFixedColumnModel() != null && getFixedColumnModel().getSelectedColumnCount() > 0)
			getFixedColumnModel().makeSelectionEmpty();
    if (getScrollableColumnModel() != null && getScrollableColumnModel().getSelectedColumnCount() > 0)
			getScrollableColumnModel().makeSelectionEmpty();*/
  }




  public void addColumnModelListener(TableColumnModelListener listener) {
  	super.addColumnModelListener(listener);
  }

  public void removeColumnModelListener(TableColumnModelListener listener) {
  	super.removeColumnModelListener(listener);
  }

  /* End of interface TableColumnModel */

  /* Interface PropertyChangeListener */

  public void propertyChange(PropertyChangeEvent evt) {
  	super.propertyChange(evt);
    String string = evt.getPropertyName();
    if (OverlordTableColumn.VISIBLE_PROPERTY.equals(string)) {
    	OverlordTableColumn column = (OverlordTableColumn)evt.getSource();
      
      if (column.isVisible()) {
        if(isFixed(column)) 
        	fireFixedColumnAdded(new TableColumnModelEvent(this, getColumnCount() , 0));
        else
        	fireScrollableColumnAdded(new TableColumnModelEvent(this, getColumnCount() , 0));
      }
      else {
      	if(isFixed(column)) 
        	fireFixedColumnRemoved(new TableColumnModelEvent(this, getColumnCount() , 0));
        else
        	fireScrollableColumnRemoved(new TableColumnModelEvent(this, getColumnCount() , 0));
      }
    	refresh();
    }
  }

  /* End of interface PropertyChangeListener */

  public void addFixedColumnModelListener(FixedColumnModelListener x) {
	  m_ListenerList.add(FixedColumnModelListener.class, x);
  }

  public void removeFixedColumnModelListener(FixedColumnModelListener x) {
	  m_ListenerList.remove(FixedColumnModelListener.class, x);
  }

  public void addScrollableColumnModelListener(ScrollableColumnModelListener x) {
	  m_ListenerList.add(ScrollableColumnModelListener.class, x);
  }

  public void removeScrollableColumnModelListener(ScrollableColumnModelListener x) {
	  m_ListenerList.remove(ScrollableColumnModelListener.class, x);
  }

  protected boolean isVisible(TableColumn column) {
  	boolean result = true;
    if (column instanceof OverlordTableColumn)
    	result = ((OverlordTableColumn)column).isVisible();
    return result;
  }

  protected boolean isFixed(TableColumn column) {
    int TheIndex = column.getModelIndex();
    return(TheIndex < m_Grid.getFixedColumns());
  }

  protected void updateFixedColumns(boolean fixedTableVisible) {
  	if (fixedTableVisible) {
      if (m_Grid.getFixedTable() == null && m_Grid.getColumnModel() != null)
				m_Grid.createFixedTable(m_Grid.getFixedColumns());
    }
    // else delete fixed table
  }

  protected void updateScrollableColumns(boolean scrollableTableVisible) {
  	if (scrollableTableVisible) {
      if (m_Grid.getScrollableTable() == null && m_Grid.getColumnModel() != null)
				m_Grid.createScrollableTable(getFixedColumnCount());
    }
    // else delete scrollable table
  }

  protected void fireFixedColumnAdded(TableColumnModelEvent e) {
  	//Developer.report("fireFixedColumnAdded");
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==FixedColumnModelListener.class) {
    		((FixedColumnModelListener)listeners[i+1]).fixedColumnAdded(e);
      }
    }
  }

  protected void fireFixedColumnRemoved(TableColumnModelEvent e) {
  	//Developer.report("fireFixedColumnRemoved");
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==FixedColumnModelListener.class) {
        ((FixedColumnModelListener)listeners[i+1]).fixedColumnRemoved(e);
      }
    }
  }

  protected void fireScrollableColumnAdded(TableColumnModelEvent e) {
  	//Developer.report("fireScrollableColumnAdded");
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==ScrollableColumnModelListener.class) {
    		((ScrollableColumnModelListener)listeners[i+1]).scrollableColumnAdded(e);
      }
    }
  }

  protected void fireScrollableColumnRemoved(TableColumnModelEvent e) {
  	//Developer.report("fireScrollableColumnRemoved");
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==ScrollableColumnModelListener.class) {
        ((ScrollableColumnModelListener)listeners[i+1]).scrollableColumnRemoved(e);
      }
    }
  }
  
}


