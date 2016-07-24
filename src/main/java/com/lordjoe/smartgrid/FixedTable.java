/* 
    file FixedTable.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import javax.swing.*;
import javax.swing.table.*;

/**
    FixedTable - this class is used for the portion
    of 
    */
public class FixedTable extends SubTable {

    public FixedTable(OverlordGrid grid,int offset,int colWidth) {
          super(grid,offset,colWidth);
    }

    protected TableColumnModel createColumnModel(OverlordTableColumnModel basemodel,int offset,int ncols)
    {
        return(new FixedTableColumnModel(basemodel,offset,ncols));
    }


    protected void addHeaderToParent()
    {
      getGrid().setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, getTableHeader());
    }

    /**
        Fixed tables do not need borders as they are not the last
        Table
    */
    public boolean isBorderNeeded() { return(false); }

}

