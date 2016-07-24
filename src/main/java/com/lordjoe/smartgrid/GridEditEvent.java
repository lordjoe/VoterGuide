/**{ file
    @name GridEditEvent.java
    @function -
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

import java.awt.*;

public class GridEditEvent extends AWTEvent {
    private int m_row;
    private int m_col;
    private Object m_OldValue;
    private Object m_NewValue;
    private boolean m_editIsConsumed;

    // &*^%%$$#@!@###$$ - java only allows specified events 
    // to be consumed - forcing the rest of us to roll their own
    public void consume() {
          m_editIsConsumed = true;
    }
    // &*^%%$$#@!@###$$ - java only allows specified events 
    // to be consumed - forcing the rest of us to roll their own
    public boolean isConsumed() {
        return(m_editIsConsumed);
    }
    


    public int getRow() { return(m_row); }
    public int getCol() { return(m_col); }
    public Object getOldValue() { return(m_OldValue); }
    public Object getNewValue() { return(m_NewValue); }

    public void setNewValue(Object r) {
        m_NewValue = r;
    }

    public void setRow(int r) {
        m_row = r;
    }
    public void setCol(int c) {
        m_col = c;
    }

    public GridEditEvent(Component source, int id,
                      int row,int col,Object oldValue,Object newValue) {
        super(source,id);
        m_row = row;
        m_col = col;
        m_OldValue = oldValue;
        m_NewValue = newValue;
    }

// end class GridEditEvent
}
