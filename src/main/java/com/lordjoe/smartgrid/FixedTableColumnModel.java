/* 
    file FixedTableColumnModel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import javax.swing.event.*;


public class FixedTableColumnModel extends ProxyTableColumnModel implements FixedColumnModelListener {

  public FixedTableColumnModel(OverlordTableColumnModel model,int offset,int ncols) {
    super(model,offset,ncols);
    m_ColumnModel.addFixedColumnModelListener(this);
}

  protected void finalize() throws java.lang.Throwable {
      m_ColumnModel.removeFixedColumnModelListener(this);
    super.finalize();
}

  public int getRealColumnIndex(int columnIndex) {
    return m_ColumnModel.getFixedColumnIndex(columnIndex);
  }

  public int getProxyColumnIndex(int columnIndex) {
    return m_ColumnModel.getFixedColumnIndex(columnIndex);
  }

  public int getColumnCount() {
    return m_ColumnModel.getFixedColumnCount();
  }

  /* Interface FixedColumnModelListener */

  public void fixedColumnAdded(TableColumnModelEvent evt) {
      //Developer.report("fixedColumnAdded");
      columnAdded((TableColumnModelEvent)evt);
  }

  public void fixedColumnRemoved(TableColumnModelEvent evt) {
      //Developer.report("fixedColumnRemoved");
      columnRemoved((TableColumnModelEvent)evt);
  }

  /* End of interface FixedColumnModelListener */
}

