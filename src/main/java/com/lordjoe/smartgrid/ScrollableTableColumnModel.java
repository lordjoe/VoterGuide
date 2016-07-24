/* 
    file ScrollableTableColumnModel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import javax.swing.event.*;

/**
    implemtation of ProxyTableColumnModel for the 
*/
public class ScrollableTableColumnModel extends ProxyTableColumnModel implements ScrollableColumnModelListener {

  public ScrollableTableColumnModel(OverlordTableColumnModel model,int offset,int ncols) {
    super(model,offset,ncols);
    m_ColumnModel.addScrollableColumnModelListener(this);
}

  protected void finalize() throws java.lang.Throwable {
  	m_ColumnModel.removeScrollableColumnModelListener(this);
    super.finalize();
}

  public int getRealColumnIndex(int columnIndex) {
    int ret = m_ColumnModel.getScrollableColumnIndex(columnIndex);
    return(ret);
  }
  
  public int getProxyColumnIndex(int columnIndex)
  {
      int NFixed = m_ColumnModel.getFixedColumnCount();
      if(columnIndex >= NFixed)
         return(columnIndex - NFixed);
      return(-1);
  }
  

  public int getColumnCount() {
    //Developer.report("scrollable column count = " + m_ColumnModel.getScrollableColumnCount());
    return m_ColumnModel.getScrollableColumnCount();
  }

  /* Interface ScrollableColumnModelListener */

  public void scrollableColumnAdded(TableColumnModelEvent evt) {
  	//Developer.report("scrollableColumnAdded");
  	columnAdded((TableColumnModelEvent)evt);
  }

  public void scrollableColumnRemoved(TableColumnModelEvent evt) {
  	//Developer.report("scrollableColumnRemoved");
  	columnRemoved((TableColumnModelEvent)evt);
  }

  /* End of interface ScrollableColumnModelListener */

}

