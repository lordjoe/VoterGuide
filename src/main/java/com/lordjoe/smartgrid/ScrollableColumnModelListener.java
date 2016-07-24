/**{ file
    @name ScrollableColumnModelListener.java
    @function - interface for listening to changes in scrollable columns
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	************************

    @date> Wed May 28 17:44:29  1997
    @version> 1.0
}*/

package com.lordjoe.smartgrid;
import javax.swing.event.*;

public interface ScrollableColumnModelListener extends java.util.EventListener {

  void scrollableColumnAdded(TableColumnModelEvent evt);

  void scrollableColumnRemoved(TableColumnModelEvent evt);

}
