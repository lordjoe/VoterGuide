/**{ file
    @name OverlordGridAgent.java
    @function <Add Comment Here>
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97
	*  Steven M. Lewis
	************************

    @date> Wed May 28 17:44:44  1997
    @version> 1.0
}*/
package com.lordjoe.smartgrid;

import java.awt.*;
/**{ interface
    @name OverlordGridAgent
    @function <Add Comment Here>
}*/
public interface OverlordGridAgent {


    //- *******************
    //- Methods

    //- *******************
    //- Basics

     /**{ method
        @name getComponent
        @function - implementer IS a component - return it
        @return - 
    }*/
   Component getComponent();

      /**{ method
        @name setBackColor
        @function - set the backgrouns color of a cell
        @param row  - cell row
        @param col  - cell column
        @param color  - background
    }*/
  public void setBackColor(int row,int col,Color color);

    /**{ method
        @name setGridDimensions
        @param rows - number rows
        @param cols - number columns
        @function - set the dimensions of the grid. Rows beyond rows
            are deleted as are columns beyons cols. added cells contain
            blanks
    }*/
    public void setGridDimensions(int rows,int cols);

    /**{ method
        @name getGridText
        @param r - row
        @param c - column
        @return - the text cells with checkboxes may return "t" or "f"
        @function - return the text in the requested cell
    }*/
    public String getGridText(int r,int c);

    /**{ method
        @name setGridText
        @param row - row
        @param col - column
        @param text - new text
        @function - set the text in the cell. A GridAccessException
            (RunTime) can be thrown if the cell is out of bounds. Use
            "t" and "f" for checkboxes

    }*/
    public void setGridText(int row,int col,String text);


    /**{ method
        @name setColumnHeader
        @function - set the header text for the given colummn -
            ideally column headers are not data and do not scroll
        @param c - column
        @param text - new text

    }*/
    public void setColumnHeader(int c,Object text);

    /**{ method
        @name setRowHeader
        @function - set the header text for the given row -
            ideally row headers are not data and do not scroll
            note row headers may be interpreted as column 0
        @param r - row
        @param text - new text

    }*/
    public void setRowHeader(int r,String text);

    /**
        After the grid is loaded this computes the maximum width of the 
        column and its data and sets the width of each column to that 
        width. In the interface so an external loader can czause this 
        to happen after the grid is loaded.
     */
    public void setColumnsToDesiredWidths();


    //- *******************
    //- Selection

    /**{ method
        @name setCellSelectionAllowed
        @function - allow individual cells to be selected. If false only
            rows can be selected
        @param doit - true allows
    }*/
    public void setCellSelectionAllowed(boolean doit);

    /**{ method
        @name isCellSelectionAllowed
        @function - true if cells can be selected otherwise only rows can be selcted
        @return - the sytate
    }*/
    public boolean isCellSelectionAllowed();

    /**{ method
        @name setMultipleSelectionAllowed
        @param doit - true allows
        @function - allow multiple items to be selected. If false only
            one item can be selected
    }*/
    public void setMultipleSelectionAllowed(boolean doit);

    /**{ method
        @name isMultipleSelectionAllowed
        @return - the state
        @function - true if multiple items can be selected
    }*/
    public boolean isMultipleSelectionAllowed();


    /**{ method
        @name clearSelection
        @function - clear all selections
    }*/
    public void clearSelection();

    /**{ method
        @name selectAll
        @function - select the entire contents - if multiple selections
            not allowed select the first selectable row
    }*/
    public void selectAll();

    /**{ method
        @name hasSelection
        @function - return true if there is one or more items selected
    }*/
    public boolean hasSelection();



    /**{ method
        @name rowIsSelected
        @param NRow - row to test
        @return - true if selected
        @function - return true if the row is selected - if cell selection
            id allowed return true if the entire row is selected
    }*/
    public boolean rowIsSelected(int NRow);

    /**{ method
        @name numberSelectedRows
        @return - the number
        @function - return the of selected rows
    }*/
    public int numberSelectedRows();




    /**{ method
        @name setCellTextColor
        @param c - column
        @param r - row
        @function - set the Text color in the cell to color
    }*/
    public void setCellTextColor(int r,int c,Color color);

    /**{ method
        @name setCellColor
        @param c - column
        @param r - row
        @function - set the background color in the cell to color
    }*/
    public void setCellColor(int r,int c,Color color);


    /**{ method
        @name getGridFont
        @return - Font
        @function - get the Font used by all cells except column headers
    }*/
    public Font getGridFont();

    /**{ method
        @name setGridFont
        @param newFont - Font to use
        @function - set the Font used by all cells except column headers
    }*/
    public void setGridFont(Font newFont);

    /**{ method
        @name getHeaderFont
        @return - Font
        @function - get the Font used by column headers
    }*/
    public Font getHeaderFont();

    /**{ method
        @name setHeaderFont
        @param newFont - Font to use
        @function - set the Font used by  column headers
    }*/
    public void setHeaderFont(Font newFont);


    /**{ method
        @name lastGridRow
        @return - last row number
        @function - get the last row
    }*/
    public int lastGridRow();

    /**{ method
        @name lastGridCol
        @return - last col number
        @function - get the last column
    }*/
    public int lastGridCol();

    /**{ method
        @name firstNonHeaderRow
        @return - first row which is not a header - resolves whether
            header is 0 or 1 ...
        @function - the row
    }*/
 //   public int firstNonHeaderRow();

    /**{ method
        @name firstNonHeaderCol
        @return - first col which is not a header - resolves whether
            header is 0 or 1 ...
        @function - the col
    }*/
 //   public int firstNonHeaderCol();

    /**{ method
        @name numberHeaderRows
        @return - number or rows used for headers - may be 0
        @function - see return
    }*/
  //  public int numberHeaderRows();

    /**{ method
        @name getNumberHeaderCols
        @return - number or cols used for headers - may be 0
        @function - see return
    }*/
    public int getNumberHeaderCols();

    /**{ method
        @name setFixedColumns
        @param n - number fixed cols
        @function - Fixed columns do not scroll with when the grid is scrolled
            horizitally it is ok if only 0 and 1 work here
    }*/
    public void setFixedColumns(int n);

    /**{ method
        @name getFixedColumns
        @function - the number of fixed columns
    }*/
    public int getFixedColumns();


    /**{ method
        @name setAutoWidth
        @param doit   - set if true
        @function - when autowidth is set columns will size to hold the
            widest element. when false columns sizes are set with
            setColumnWidth true is default.
        @see #setColumnWidth
    }*/
 //   public void setAutoWidth(boolean doit);

    /**{ method
        @name setAutoHeight
        @param doit   - set if true
        @function - when autoheight is set rows will size to hold the
            tallest element. When false row sizes are set with
            setRowHeight true is default.
        @see #setRowHeight
    }*/
 //   public void setAutoHeight(boolean doit);

    /**{ method
        @name setRowHeight
        @param row   - affected row
        @param height - height in pixels
        @function - set the height of the given row
    }*/
    public void setRowHeight(int row,int height);


    /**{ method
        @name clear
        @function - set all text to blanks
    }*/
    public void clear();



    /**{ method
        @name rebuildTable
         @function - force a table to rebuild - this allows
            operations which modify many cells to only rebuild once
    }*/
    public void rebuildTable();

    /**
        set the background color of the header
        @param newColor - color to use
    */
    public void  setHeaderBackground(Color newColor);


    /**
        get the background color of the header
        @return - header back color
    */
    public Color getHeaderBackground();

    /**
        set the foreground color of the header
        @param newColor - color to use
    */
    public void  setHeaderForeground(Color newColor);

    /**
        get the background color of the header
        @return - header fore color
    */
   public Color getHeaderForeground();

    /**
        get whether header is drawn with a raised border
        @return - true is 3d
    */
    public boolean isHeader3D();

   /**
        set whether header is drawn with a raised border
        @param doit - true is 3d
   */
   public void setHeader3D(boolean doit);

    /**
        return true of the header is displayed 
        for the viewer this can always return true
        @return - as above
    */
    public boolean isHeaderShown();
    /**
        set the header shown
        for the viewer this can do nothing as headers are always shown
        @param doit - true says show header
    */
    public void setHeaderShown(boolean doit);

    /**
        return whether the 
        @return - true says gris shown
    */
    public boolean isShowGrid();
    /**
        set the table gris shown
        for the viewer this can do nothing as headers are always shown
        @param doit - true says show grid
    */
    public void setShowGrid(boolean doit);


    // Grid listeners 
    /**
        register as a GridClickListener
        @param - l the listener
    */
    public void addGridClickListener(GridClickListener l);
    /**
        deregister as a GridClickListener
        @param - l the listener
    */
    public void removeGridClickListener(GridClickListener l);

    /* ========================================================
        These are not needed in the public interface for the
        viewer but may be used by other applications 
        i.e. they are not needed
    */

    /**{ method
        @name setGridObject
        @param row - row
        @param col - column
        @param text - new text
        @function - set the text in the cell. A GridAccessException
            (RunTime) can be thrown if the cell is out of bounds. Use
            "t" and "f" for checkboxes

    }*/
   public void setGridObject(int row,int col,Object text);
    /**{ method
        @name setColumnEditable
        @param Col - the column
        @param doit - true if  so otherwise readonly (default)
        @function - set a column to check boxes
    }*/
    public void setColumnEditable(int Col,boolean doit);

    /**{ method
        @name isCellModified
        @param Row - row
        @param Col  - col
        @return - true the user has modified the cell or the cellis
            readonly
        @function - tests is a cell is modified by the user
    }*/
    public boolean isCellModified(int Col,int Row);
     // Sort methods
    public boolean isColumnSortSupported();
    public void setColumnSortSupported(boolean doit);

//    public int getSortColumn();
//    public void setSortColumn(int col);
    /**{ method
        @name setColumnCheckBox
        @param Col - the column
        @param doit - true if so otherwise text
        @function - set a column to check boxes
    }*/
//    public void setColumnCheckBox(int Col,boolean doit);
//    public void setUsingRowHeaders(boolean doit);

//    public boolean isUsingRowHeaders();
    /**{ method
        @name selectRow
        @param NRow - row to select
        @function - select a row in the grid. Do not clear any other selections.
    }*/
 //   public void selectRow(int NRow);


    /**{ method
        @name deSelectRow
        @param NRow - row to select
        @function - unselect a row in the grid. If multiple selections
            are allowed this does not clear other selections
    }*/
    public void deSelectRow(int NRow);

    /**{ method
        @name selectCell
        @param Row - row
        @param Col  - col
        @function - select a cell in the grid. Only acts if cell selection
            is allowed
    }*/
 //   public void selectCell(int Row,int Col);

    /**{ method
        @name deSelectCell
        @param Row - row
        @param Col  - col
        @function - unselect a cell in the grid. Only acts if cell selection
            is allowed
    }*/
 //   public void deSelectCell(int Row,int Col);

    /**{ method
        @name isCellSelected
        @param Row - row
        @param Col  - col
        @return - true if a cell is selected
        @function - tests is a cell is selected -
    }*/
  //  public boolean isCellSelected(int Row,int Col);
    /**{ method
        @name xyToCell
        @return - x = column y = row
        @function - convert x,y pixels into a cell
    }*/
 //   public Point xyToCell(int x,int y);
    /**
        register as a GridEditListener
        @param - l the listener
    */
    public void addGridEditListener(GridEditListener l);
    /**
        deregister as a GridEditListener
        @param - l the listener
    */
    public void removeGridEditListener(GridEditListener l);

    /**{ method
        @name setColumnWidth
        @param col   - affected col
        @param width - height in pixels
        @function - set the width of the given column
        @see #setAutoWidth
    }*/
//    public void setColumnWidth(int col,int width);
    /**{ method
        @name setFixedRows
        @function <Add Comment Here>
    }*/
 //   public void setFixedRows(int n);

    /**{ method
        @name getFixedRows
        @function <Add Comment Here>
    }*/
 //   public int getFixedRows();
    /**{ method
        @name getScroll
        @return - true if selected
        @function <Add Comment Here>
    }*/
 //   public Point getScroll();

     //- *******************
    //- Hide and Show

   /**{ method
        @name hideRow
        @param NRow - row to hide
        @function - drop the row from the current display
    }*/
  //  public void hideRow(int row);

    /**{ method
        @name showRow
        @param NRow - row to show
        @function - show the indicated row
    }*/
  //  public void showRow(int row);
//- *******************
//- End Class OverlordGridAgent
}
