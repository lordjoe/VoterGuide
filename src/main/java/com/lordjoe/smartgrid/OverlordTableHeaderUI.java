/* 
    file OverlordTableHeaderUI.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/


package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
    Our version of BasicTableHeaderUI - this does not
    support column rearrangement or resizing but does
    generate a GridClickEvent when the header is
    clicked
*/
public class OverlordTableHeaderUI extends BasicTableHeaderUI
{
    public OverlordTableHeaderUI() {
        super();
    }
    
    public void installUI(JComponent c) {
        super.installUI(c);
    }
    public SubTable getRealTable() {
       return(getRealTableHeader().getSubTable());
    }
    
    protected OverlordGrid getOverlordGrid()
    {
        return(getRealTable().getGrid());
    }
    
    protected OverlordTableHeader getRealTableHeader() {
       return((OverlordTableHeader)header);
    }
    /**
     * Creates the mouse listener for the JTable.
     */
    protected MouseInputListener createMouseInputListener() {
        return new OverlordTableMouseInputHandler();
    }
    
    public static ComponentUI createUI(JComponent h) {
        return new OverlordTableHeaderUI();
    }
    /**
        innerclass for handling mouse events
    */
    public class OverlordTableMouseInputHandler extends BasicTableHeaderUI.MouseInputHandler
    {
        public void mouseClicked(MouseEvent e) {
            Assertion.doNada();
        }

        public void mousePressed(MouseEvent e) {
            if(e.getClickCount() > 1) {
                performDoubleClick(e);
                return;
            }
        	TableColumnModel columnModel = getRealTableHeader().getColumnModel();
        	Point p = e.getPoint();
        	int index = columnModel.getColumnIndexAtX(p.x);
        	if(index == -1)
        	    return;
        	SubTable RealTable = getRealTable();
        	int column = index + RealTable.getModelOffset();
        	GridClickEvent ev = new GridClickEvent(e,GridClickEvent.HEADER_ROW,column);
        	getOverlordGrid().fireCellClicked(ev); 
        }
        
        public void mouseReleased(MouseEvent e) {
        	TableColumnModel columnModel = getRealTableHeader().getColumnModel();
        	Point p = e.getPoint();
        	int index = columnModel.getColumnIndexAtX(p.x);
        	if(index == -1)
        	    return;
        	SubTable RealTable = getRealTable();
        }

        protected void performDoubleClick(MouseEvent e) {
            Point p = e.getPoint();
        	SubTable table = getRealTable();
            int columnIndex = table.columnAtPoint(p);
            OffsetTableModel TheModel = (OffsetTableModel)table.getModel();
            OverlordGrid RealModel = TheModel.getRealModel();
            GridClickEvent ev = new GridClickEvent(e,GridClickEvent.HEADER_ROW,columnIndex);
            RealModel.fireCellDoubleClicked(ev);
        }

        // Override to ignore these
        public void mouseMoved(MouseEvent e) {
        }

        // Override to ignore these
        public void mouseDragged(MouseEvent e) {
        }
    // end inner class OverlordTableMouseInputHandler
    }
// end class OverlordTableUI
}
