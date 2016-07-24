/**{ file
    @name FixedColumnModelListener.java
    @function - interface for listening to changes in fixed columns
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

public interface FixedColumnModelListener extends java.util.EventListener {

  void fixedColumnAdded(TableColumnModelEvent evt);

  void fixedColumnRemoved(TableColumnModelEvent evt);

}

