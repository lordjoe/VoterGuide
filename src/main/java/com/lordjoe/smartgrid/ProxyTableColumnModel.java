/* 
    file ProxyTableColumnModel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;

/**
    This class is used by SubTable - a class derived from JTable which represents 
    only a portion of the columns held by the full table model. The Proxy model is
    an adapter which maps from local columns to global columns
*/
    
public  class ProxyTableColumnModel implements TableColumnModel, TableColumnModelListener {

  protected OverlordTableColumnModel m_ColumnModel;
  protected EventListenerList        m_ListenerList = new EventListenerList();
  private ListSelectionModel         m_selectionModel; 
  transient protected ChangeEvent    m_ChangeEvent;
  private int                        m_Offset;  // index of first column
  private int                        m_NCols;   // number of columns


  public ProxyTableColumnModel(OverlordTableColumnModel columnModel,int offset,int ncols) {
  	super();
    m_ColumnModel = columnModel;
  //  m_selectionModel = createListSelectionModel();
    m_selectionModel = columnModel.getGrid();
    setSelectionModel(m_selectionModel);
    m_ColumnModel.addColumnModelListener(this);
    m_NCols = ncols;
    setColumnOffset(offset);
}

  protected ListSelectionModel createListSelectionModel()
  {
    OverlordGrid TheGrid = m_ColumnModel.getGrid();
    return(new DefaultListSelectionModel());
  }
  
  public void setColumnOffset(int newOffset) {
    if(m_Offset == newOffset)
        return;
    m_Offset = newOffset;
  }

  // map local to real index
  public  int getRealColumnIndex(int columnIndex)
  {
    return(columnIndex + m_Offset);
  }

  // map real to local index
  public  int getProxyColumnIndex(int columnIndex)
  {
     return(columnIndex - m_Offset);
  }

  /* Interface TableColumnModel */

  public void addColumn(TableColumn column) {
  	m_ColumnModel.addColumn(column);
  }

  public void removeColumn(TableColumn column) {
  	m_ColumnModel.removeColumn(column);
  }

  public void moveColumn(int columnIndex, int newIndex) {
    if (columnIndex < 0 || columnIndex >= getColumnCount() || newIndex < 0 || newIndex >= getColumnCount())
			throw new IllegalArgumentException("moveColumn() - Index out of range");
  	m_ColumnModel.moveColumn(getRealColumnIndex(columnIndex), getRealColumnIndex(newIndex));
  }

  public void setColumnMargin(int index) {
  	m_ColumnModel.setColumnMargin(getRealColumnIndex(index));
  }

  public int getColumnCount()
  {
     if(m_NCols > 0)
        return(m_NCols);
     return(m_ColumnModel.getColumnCount() - m_Offset);

  }

  public Enumeration getColumns() {
    if(m_ColumnModel == null || m_ColumnModel.getColumnCount() <= 0)
        return(NullEnumeration.getInstance());
        
     //Developer.report("columns: ");
  	return new ProxyTableColumnModelEnumeration(this);
  }

  // note this is an inner class
  /**
    implement an enumeration over the contained columns
  */
  class ProxyTableColumnModelEnumeration implements Enumeration {
      ProxyTableColumnModel m_TheModel;
	  int i = 0;
      ProxyTableColumnModelEnumeration(ProxyTableColumnModel m) {
          m_TheModel = m;
      }
      public boolean hasMoreElements() {
        int ncols = m_TheModel.getColumnCount();
      	return (i < ncols);
      }
      public Object nextElement() {
      	//Developer.report("column " + getColumn(i).getIdentifier());
      	return m_TheModel.getColumn(i++);
      }
    };
    // end inner class


  public int getColumnIndex(Object identifier) {
  	return getProxyColumnIndex(m_ColumnModel.getColumnIndex(identifier));
  }


  /**
    return the real column given the local index
  */
  public TableColumn getColumn(int index) {
    int realIndex = getRealColumnIndex(index);
    if(realIndex < 0) { // debug this step
        realIndex = getRealColumnIndex(index);
    }
  	return m_ColumnModel.getColumn(realIndex);
  }

  public int getColumnMargin() {
  	return m_ColumnModel.getColumnMargin();
  }

  public int getColumnIndexAtX(int xPosition) {
    int index = 0;
    Point aPoint = new Point(xPosition, 1);
    Rectangle columnRect = new Rectangle(0,0,0,3);
    Enumeration enumeration = getColumns();

    while (enumeration.hasMoreElements()) {
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      columnRect.width = aColumn.getWidth() + getColumnMargin();

      if (columnRect.contains(aPoint))
		    return index;

      columnRect.x += columnRect.width;
      index++;
    }
    return -1;
	}

  public int getTotalColumnWidth() {
  	int result = 0;
  	if(getColumnCount() <= 0)
  	    return(0);
  	    
    for (Enumeration e = getColumns(); e.hasMoreElements(); ) {
        TableColumn TheColumn = (TableColumn)e.nextElement();
        result += TheColumn.getWidth() + getColumnMargin();
    }
  	return result;
  }

  public void setMultipleSelectionAllowed(boolean value) {
  	m_ColumnModel.setMultipleSelectionAllowed(value);
  }

  public boolean getMultipleSelectionAllowed() {
  	return m_ColumnModel.getMultipleSelectionAllowed();
  }

  public void setEmptySelectionAllowed(boolean value) {
  	m_ColumnModel.setEmptySelectionAllowed(value);
  }

  public boolean getEmptySelectionAllowed() {
  	return m_ColumnModel.getEmptySelectionAllowed();
  }

  public void setColumnSelectionAllowed(boolean value) {
  	m_ColumnModel.setColumnSelectionAllowed(value);
  }

  public boolean getColumnSelectionAllowed() {
  	return m_ColumnModel.getColumnSelectionAllowed();
  }


  public void makeSelectionEmpty() {
  	m_ColumnModel.makeSelectionEmpty();
  }

  public void selectAll() {
  	setColumnSelectionInterval(0, getColumnCount()-1);
  }

  public void clearSelection() {
  	m_ColumnModel.clearSelection();
  }

  public void setColumnSelectionInterval(int startIndex, int endIndex) {
  	int realIndex = getRealColumnIndex(startIndex);
 // 	m_ColumnModel.setColumnSelectionInterval(realIndex, realIndex);
//    for (int i = startIndex + 1; i <= endIndex; i++)
//    	addColumnSelectionInterval(i,i);
  }

  public void addColumnSelectionInterval(int startIndex, int endIndex) {
  	for (int i = startIndex; i <= endIndex; i++) {
    	int realIndex = getRealColumnIndex(i);
    //	m_ColumnModel.addColumnSelectionInterval(realIndex, realIndex);
    }
  }

  public void removeColumnSelectionInterval(int startIndex, int endIndex) {
  	for (int i = startIndex; i <= endIndex; i++) {
    	int realIndex = getRealColumnIndex(i);
//    	m_ColumnModel.removeColumnSelectionInterval(realIndex, realIndex);
    }
  }

  public int getSelectedColumn() {
  	return(-1);
  }


  public boolean isColumnSelected(int index) {
  	return false;
  }


  public int getSelectedColumnCount() {
  	int[] columns = m_ColumnModel.getSelectedColumns();
    int len = 0;
    for (int i = 0; i < columns.length; i++) {
    	int proxyIndex = getProxyColumnIndex(columns[i]);
    	if (proxyIndex > -1)
	    	len++;
    }
    return len;
  }

  public int[] getSelectedColumns() {
  	int[] columns = m_ColumnModel.getSelectedColumns();
    int len = 0;
    for (int i = 0; i < columns.length; i++) {
    	int proxyIndex = getProxyColumnIndex(columns[i]);
    	if (proxyIndex > -1) {
	    	columns[len++] = proxyIndex;
      }
    }
    int[] result = new int[len];
    System.arraycopy(columns, 0, result, 0, len);
    return result;
  }

  public void addColumnModelListener(TableColumnModelListener listener) {
  	Class listenerClass = javax.swing.event.TableColumnModelListener.class;
    m_ListenerList.add(listenerClass, listener);
  }

  public void removeColumnModelListener(TableColumnModelListener listener) {
  	Class listenerClass = javax.swing.event.TableColumnModelListener.class;
    m_ListenerList.remove(listenerClass, listener);
  }

  /* End of interface TableColumnModel */

  /* Interface TableColumnModelListener */
  // Here we listen to the modification made on the common OverlordTableColumnModel

  public void columnAdded(TableColumnModelEvent evt) {
  	fireColumnAdded(new TableColumnModelEvent(this, getProxyColumnIndex(evt.getFromIndex()), getProxyColumnIndex(evt.getToIndex())));
  }

  public void columnRemoved(TableColumnModelEvent evt) {
  	fireColumnRemoved(new TableColumnModelEvent(this, getProxyColumnIndex(evt.getFromIndex()), getProxyColumnIndex(evt.getToIndex())));
  }

  public void columnMoved(TableColumnModelEvent evt) {
  	fireColumnMoved(new TableColumnModelEvent(this, getProxyColumnIndex(evt.getFromIndex()), getProxyColumnIndex(evt.getToIndex())));
  }

  public void columnMarginChanged(ChangeEvent evt) {
  	fireColumnMarginChanged();
  }

  public void columnSelectionChanged(ListSelectionEvent evt) {
  	// TODO: tune
  	fireColumnSelectionChanged(new ListSelectionEvent(this, 0, getColumnCount()-1, false));
  }

  /* End of interface TableColumnModelListener */

  protected void fireColumnAdded(TableColumnModelEvent e) {
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
		    ((TableColumnModelListener)listeners[i+1]).columnAdded(e);
      }
    }
  }

  protected void fireColumnRemoved(TableColumnModelEvent e) {
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
        ((TableColumnModelListener)listeners[i+1]).columnRemoved(e);
      }
    }
  }

  protected void fireColumnMoved(TableColumnModelEvent e) {
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
        ((TableColumnModelListener)listeners[i+1]).columnMoved(e);
      }
    }
  }

  protected void fireColumnSelectionChanged(ListSelectionEvent e) {
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
        ((TableColumnModelListener)listeners[i+1]).columnSelectionChanged(e);
      }
    }
  }

  protected void fireColumnMarginChanged() {
    Object[] listeners = m_ListenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
        if (m_ChangeEvent == null)
            m_ChangeEvent = new ChangeEvent(this);
        ((TableColumnModelListener)listeners[i+1]).columnMarginChanged(m_ChangeEvent);
      }
    }
  }


//
// Selection model
//

    /**
     *  Sets the selection model for this TableColumnModel to <I>newModel</I>
     *  and registers with for listner notifications from the new selection
     *  model.  If <I>newModel</I> is null, it means columns are not
     *  selectable.
     *
     * @param	newModel	the new selection model
     * @see	#getSelectionModel()
     */
    public void setSelectionModel(ListSelectionModel newModel) {
    /*	ListSelectionModel oldModel = m_selectionModel;

    	if (newModel != oldModel) {
    	    if (oldModel != null) {
        		oldModel.removeListSelectionListener(this);
    	    }
        */
    	    m_selectionModel = newModel;
        /*
    	    if (newModel != null) {
         		newModel.addListSelectionListener(this);
    	    }
    	}
    	*/
    }

    /**
     * Returns the <B>ListSelectionModel</B> that is used to maintain column
     * selection state.
     *
     * @return	the object that provides column selection state.  Or
     *		<B>null</B> if row selection is not allowed.
     * @see	#setSelectionModel()
     */
    public ListSelectionModel getSelectionModel() {
    	return m_selectionModel;
    }

}


