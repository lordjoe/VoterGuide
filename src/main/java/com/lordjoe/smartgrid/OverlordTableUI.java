/* 
    file OverlordTableUI.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.plaf.*;

import com.lordjoe.utilities.*;

/**
 * OverlordTableUI implementation
 * <p>
 */
public class OverlordTableUI  extends BasicTableUI
{
//
// Install/Deinstall ui
//
    
    protected SubTable getRealTable()
    {
        return((SubTable)table);
    }
    protected OverlordGrid getOverlordGrid()
    {
        return(getRealTable().getGrid());
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new OverlordTableUI();
    }

    /**
     * Creates the mouse listener for the JTable.
     */
    protected MouseInputListener createMouseInputListener() {
        return new MyMouseInputHandler();
    }
    
    public class MyMouseInputHandler extends MouseInputHandler 
    {
 
        public void mousePressed(MouseEvent e) {
            int RealColumn;
            if( (e.getModifiers() & InputEvent.BUTTON1_MASK) == 0) {
                    return;
            }
         /*   if(e.getClickCount() > 1) {
                    performDoubleClick(e);
                    return;
            } */
            SubTable RealTable = getRealTable();
            Point p = e.getPoint();
                int row = RealTable.rowAtPoint(p);
                int column = RealTable.columnAtPoint(p);
                RealColumn = column + RealTable.getModelOffset();
    	    // The autoscroller can generate drag events outside the Table's range. 
                if ((column == -1) || (row == -1)) {
                    return;
            }
            
            OverlordGrid RealGrid = getOverlordGrid();
            if((e.getModifiers() & Event.SHIFT_MASK) != 0 ||
                e.getClickCount() > 2) 
            {
                if(RealGrid.isColumnEditable(RealColumn)) {
                    if(RealTable.editCellAt(row, column, e));
                    return;
                }
            }
            if(RealColumn == 0 && RealGrid.isMultipleSelectionAllowed()) {
                if(RealGrid.isSelectedIndex(row))
                    RealGrid.deSelectRow(row); 
                else 
                    RealGrid.selectRow(row); 
                return;
            }
            else {
                boolean CellSelect = RealGrid.isCellSelectionAllowed();
                boolean HeaderSelect = RealGrid.isSelectOnHeadersOnly();
                if(!CellSelect || HeaderSelect) {
                    GridClickEvent ev = new GridClickEvent(e,row,column);
                    RealGrid.fireCellClicked(ev); 
                    return;
                }
            }
            super.mousePressed(e);
        }
        public void mouseReleased(MouseEvent e) {
           if( (e.getModifiers() & InputEvent.BUTTON1_MASK) == 0) {
                    return;
            }
            if(e.getClickCount() > 1) {
                    performDoubleClick(e);
                    return;
            }
            
            OverlordGrid RealGrid = getOverlordGrid();
            GridClickEvent ev = new GridClickEvent(e,0,0);
            RealGrid.fireClickReleased(ev); 
            super.mouseReleased(e);
     }
  }


    

//
// Size Methods
//


  /**  If the table is autoresizing its columns
    *  <code>(getAutoResizeMode() != JTable.AUTO_RESIZE_OFF)</code>
    *  and the JTable is also inside a scrollpane, the preferred width
    *  of the JTable is the width of the viewport that contains it.
    *  Otherwise, the preferred width is the total width of all
    *  of the columns. The preferred height it the number of rows
    *  times the preferred height (including the intercell spacing).
    *  <p>
    *  The result is that when the mode of the JTable is AUTO_RESIZE_OFF
    *  columns are not autoresized and a horizontal scrollbar appears
    *  when the total width of the columns excedes the width of the
    *  JScrollPane. When the mode is not AUTO_RESIZE_OFF the JTable
    *  autoresizes its columns continually to ensure that they fill
    *  the full width of the JScrollPane.
    */
    public Dimension getPreferredSize(JComponent c) {
        return(c.getPreferredSize());
 /*       JTable table = (JTable)c;
        Dimension size = new Dimension();
        int mode = table.getAutoResizeMode();

        try {
            Component parent = table.getParent();
            if (mode != JTable.AUTO_RESIZE_OFF && parent instanceof JViewport) {
                size.width = parent.getBounds().width;
                table.sizeColumnsToFit(true );
            }
            else {
                size.width = table.getColumnModel().getTotalColumnWidth();
            }
            size.height = table.getRowCount() * (table.getRowHeight() +
                          table.getIntercellSpacing().height);
        }
        catch(ArrayIndexOutOfBoundsException ex) {
             Assertion.fatalException(ex);
            }
        return size;
        */
    }
    
    public Dimension getMinimumSize(JComponent c) {
        return(c.getPreferredSize());
    }
    

//
// Protected & Private Methods
//
    protected void performDoubleClick(MouseEvent e) {
        Point p = e.getPoint();
        int columnIndex = table.columnAtPoint(p);
        int rowIndex    = table.rowAtPoint(p);
        OffsetTableModel TheModel = (OffsetTableModel)table.getModel();
        OverlordGrid RealModel = TheModel.getRealModel();
        GridClickEvent ev = new GridClickEvent(e,rowIndex,columnIndex);
        RealModel.fireCellDoubleClicked(ev);
    }

 /*  protected void updateSelection(MouseEvent e) {
        // ignore right mouse click
        if( (e.getModifiers() & InputEvent.BUTTON1_MASK) == 0) {
                return;
        }
        if(table.getCellSelectionEnabled()&&
            ((e.getModifiers()& InputEvent.BUTTON1_MASK) != 0)) // only left button
        {
            if(e.getClickCount() > 1) {
                performDoubleClick(e);
                return;
            }
            int columnIndex = table.columnAtPoint(e.getPoint());
            int rowIndex    = table.rowAtPoint(e.getPoint());
            OffsetTableModel TheModel = (OffsetTableModel)table.getModel();
            OverlordGrid RealModel = TheModel.getRealModel() ;
            if(rowIndex >= 0 &&
               columnIndex > 0 ||
               (columnIndex == 0 && RealModel.isFirstColumnSelectable(rowIndex)))
            {
                updateCellSelection(e);
                return;
            }
        }
    	int rowIndex = -1;
    	int columnIndex = -1;
    	// dont actually need these clauses but they make selection faster.
    	boolean shouldChangeRowSelection = table.getRowSelectionAllowed();
    	boolean shouldChangeColumnSelection = table.getColumnSelectionAllowed() &&
    	        (table.getCellSelectionEnabled() || !table.getRowSelectionAllowed());

    	int TestColumn = table.columnAtPoint(e.getPoint());

    	TestColumn = 0;
    	// Find which column was hit
    	if (shouldChangeColumnSelection &&
    	        (columnIndex = table.columnAtPoint(e.getPoint())) != -1) {
    	    if (controlKeyDown) {
    		if (isSelecting) {
    		    table.addColumnSelectionInterval(anchorColumnIndex, columnIndex);
    		}
    		else {
    		    // we will deselect
    		    table.removeColumnSelectionInterval(anchorColumnIndex, columnIndex);
    		}
    	    }
    	    else {
    		table.setColumnSelectionInterval(anchorColumnIndex, columnIndex);
    	    }
    	}
    	// Find which row was hit
    	if (shouldChangeRowSelection &&
    	        (rowIndex = table.rowAtPoint(e.getPoint())) != -1) {
    	    if (controlKeyDown) {
        		if (isSelecting) {
        		    table.addRowSelectionInterval(anchorRowIndex, rowIndex);
        		}
        		else {
        		    // we will deselect
        		    table.removeRowSelectionInterval(anchorRowIndex, rowIndex);
        		}
    	    }
    	    else {
    	        if(anchorRowIndex == rowIndex && table.isRowSelected(rowIndex))
    	            table.removeRowSelectionInterval(anchorRowIndex, rowIndex);
    	        else
      		        table.setRowSelectionInterval(anchorRowIndex, rowIndex);
    	    }
    	}
    }
    */
    

 
    protected void updateCellSelection(MouseEvent e) {
    	int rowIndex    = table.rowAtPoint(e.getPoint());
    	int columnIndex = table.columnAtPoint(e.getPoint());
        OffsetTableModel TheModel = (OffsetTableModel)table.getModel();
        columnIndex = TheModel.toRealColumn(columnIndex);
        OverlordGrid RealModel = TheModel.getRealModel() ;
    	Assertion.assume(rowIndex >= 0 && columnIndex >= 0);

    	boolean WasSelected = RealModel.isCellSelected(rowIndex,columnIndex);
    	RealModel.clearSelection();
    	if(WasSelected) {
    	    RealModel.deSelectShownCell(columnIndex,rowIndex);
    	}
    	else {
    	    RealModel.selectShownCell(columnIndex,rowIndex);
    	}
    }

 
}  // End of Class BasicTableUI

