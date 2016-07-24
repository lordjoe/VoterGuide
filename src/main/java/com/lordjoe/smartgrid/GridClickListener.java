/**{ file
    @name GridClickListener.java
    @function - interface for listening to changes in row selection - unlike
       other listeners this simply takes the array of selections - saving time
       in sending individual items
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

public interface GridClickListener extends EventListener {

  void cellDoubleClicked(GridClickEvent ev);
  void cellClicked(GridClickEvent ev);
  void clickReleased(GridClickEvent ev);
}
