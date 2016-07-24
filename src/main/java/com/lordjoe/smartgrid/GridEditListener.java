/**{ file
    @name GridEditListener.java
    @function - interface for listening to cell edit events - these are
    sent when the user has edited a cell in the grid. The code has the option of
    altering the new value or comsuming the event - canceling the edit
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

import java.util.EventListener;

public interface GridEditListener extends EventListener {

  void cellEdited(GridEditEvent ev);
}
