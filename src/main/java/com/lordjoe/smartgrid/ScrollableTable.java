/* 
    file ScrollableTable.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import javax.swing.event.*;
import javax.swing.table.*;

/**
    virtually the only reason for a separate subclass is to allow
    separation of events in the scrolling and fixed tables 
*/
public class ScrollableTable extends SubTable {


	public ScrollableTable(OverlordGrid grid,int offset,int colWidth) {
      	super(grid,offset,colWidth);
	}
	
    protected TableColumnModel createColumnModel(OverlordTableColumnModel basemodel,int offset,int ncols)
    {
        return(new ScrollableTableColumnModel(basemodel,offset,ncols));
    }
	

    // debugging override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
    }
}

