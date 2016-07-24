/**{ file
    @name GridClickEvent.java
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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.Component;

public class GridClickEvent extends MouseEvent {
    public static final int HEADER_ROW = -1;
    private int m_row;
    private int m_col;
    public int getRow() { return(m_row); }
    public int getCol() { return(m_col); }
    public void setRow(int r) {
        m_row = r;
    }
    public void setCol(int c) {
        m_col = c;
    }

    public GridClickEvent(Component source, int id, long when, int modifiers,
                      int x, int y, int clickCount, boolean popupTrigger,
                      int row,int col) {
        super(source,id,when,modifiers,x,y,clickCount,popupTrigger);
        m_row = row;
        m_col = col;
    }
    public GridClickEvent(MouseEvent ev,int row,int col)
    {
        super((Component)ev.getSource(),ev.getID(),ev.getWhen(),ev.getModifiers(),
            ev.getX() ,ev.getY() ,ev.getClickCount(),ev.isPopupTrigger());
        m_row = row;
        m_col = col;
    }

    public int getScreenX() {
        Component c = (Component)getSource();
        Point loc = c.getLocationOnScreen();
        return(loc.x + getX());
    }
    public int getScreenY() {
        Component c = (Component)getSource();
        Point loc = c.getLocationOnScreen();
        return(loc.y + getY());
    }
// end class GridClickEvent
}
