/* 
    file OverlordGrid.java
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
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 * Notes: <UL>
 *    <LI> The index of the first row is 0
 *    <LI> The index of the first column is 0
 * </UL>
 *
 * Implemented methods: <UL>
 *    <LI>setGridDimensions()
 *    <LI>getGridText()
 *    <LI>setGridText()
 *    <LI>setColumnHeader
 *    <LI>lastGridRow()
 *    <LI>lastGridCol()
 *    <LI>clear()
 *    <LI>setColumnCheckBox
 * </UL>
 *
 * Code example: <PRE>
 *		Grid grid = new Grid();
 *		grid.setGridDimensions(6, 4);
 *		JScrollPane scrollPane = grid.createScrollPaneForTable(grid);
 *		scrollPane.setDoubleBuffered(true);
 *		frame.add("Center", scrollPane);
 * </PRE>
 *
 * See <CODE>GridTest</CODE> for a complete example. This class creates
 * a grid that can be modified by using the popup menu that appears
 * when clicking anywhere beneath the table. The parameters of the
 * methods called are set to be random.
 */
public class OverlordGrid extends JScrollPane implements 
    OverlordGridAgent,
    OverlordTableModelInterface,
    TableColumnModelListener,ListSelectionModel
{

  public final static int DEFAULT_COLUMN_WIDTH			= 75;
  public static final String gEmptyString = ""; // default return object
  private int        m_NumberFixedColumns;  // these columns to not scroll
  // NOTE this is protected to simplify code in the derived class OverlordTreeGrid
  protected SubTable[] m_Tables;            // Holds read tables
  private OverlordTableColumnModel m_ColumnModel;
  
  // Flags
  private boolean m_CellSelectionAllowed = false; // allow cells to be selected -
  private boolean m_AutoWidth = false;
  private boolean m_UsingRowHeaders = false;  // treat column 0 as row headers
  private boolean m_SelectOnHeadersOnly = true; // allow row selection only when header is clicked
  protected boolean    m_ColumnSortSupported;   // when true clicking on the column header sorts by the
                                                // data in that column
  
    /**
        constructor for OverlordGrid
    */
	public OverlordGrid() {
		super();
		m_SelectModel = this;
		m_ColumnModel = new OverlordTableColumnModel(this);
        createTables();
		this.getViewport().setBackingStoreEnabled(true);
		setDoubleBuffered(true);

    //this.setDoubleBuffered(true);
	}

// ===================================================================
// Start Table model code
// ===================================================================

    /** List of listeners */
    protected EventListenerList m_listenerList = new EventListenerList();

    protected boolean    m_InhibitFirstColumnSelect;
    protected boolean    m_InhibitTableEvents;   // for optimization if a number of operations alter
                                                 // the table this flag can block notification until 
                                                 // all operations are completed
                                                 
    protected boolean    m_PendingTableEvent;    // if events inhibited this remembers the fact that there is an
                                                 // event to send
                                                 
    // Table Data                                             
    protected Object[]   m_ColumnNames; // names of columns
    protected Object[][] m_Data;        // Data for the table
    protected boolean[]  m_ColumnIsEditable; // true of we permit column editing

    // Selection Data
    protected boolean[][]  m_CellSelections; // NOTE these are col,row
                                           // to allow passing to col models
    protected int        m_NumberCellSelections;
    protected boolean[]  m_RowSelections;
    protected boolean    m_SelectionEmpty = true;
    protected int        m_SelectionMode = MULTIPLE_INTERVAL_SELECTION;
    protected int        m_AnchorIndex;
    protected int        m_LeadIndex;
    protected boolean    m_IsAdjusting;
    protected boolean    m_ColumnSelectionSupported;

    // Colors - Fonts  and presentation
    protected Color   m_FixedForeground = Color.black;     // if non-null use as default foreground color for fixed columns
    protected Color   m_FixedBackground = Color.lightGray; // if non-null use as default background color foreground color for fixed columns
    protected Color   m_HeaderForeground = Color.black;    // if non-null use as default foreground color
    protected Color   m_DefaultBackground = Color.white;    // if non-null use as default foreground color
    protected Color   m_HeaderBackground;                  // if non-null use as default background color
    protected boolean m_isHeader3D = true;      // is header shown as raised border
    protected boolean m_HeaderShown = false;    // display grid header
    protected boolean m_ShowGrid;    // display lines on the grid
    protected Color[][]  m_CellForeground; // if non-null use as foreground color
    protected Color[][]  m_CellBackground; // if non-null use as background color
    private Font    m_GridFont = new Font("Courier", Font.PLAIN, 12);   // font used in the grid
    private Font    m_HeaderFont = new Font("Courier", Font.PLAIN, 12); // font used in the header
    
    // Mapping show to actual rows
    protected int        m_NumberShownRows; // number or rows dislayed - others are hidden
    protected int[]      m_RealToShownMap;  // for each real row has index of displayed row - 
                                            // -1 says row is hidden
    protected int[]      m_ShownToRealMap;  // for each displayed row has index of corresponding real row
    protected boolean[]  m_RowIsShown;      // true if the row is being displayed
    
    protected ListSelectionModel m_SelectModel;
    
    
    private boolean    m_SortReversed;     // if true reverse the normal sort
    private int        m_SortColumn = -1;  // column on which data is sorted - -1 says use 
                                           // creation order 
    private boolean    m_IsLabReport;      // = true; // stripe green and Cyan
                                           // used to test custom coloring

    /**
        This class is not a tree but OverlordTreeGrid is and returns true
    */
    public boolean isTree() {
        return(false);
    }
    
     /**{ method
        @name getComponent
        @function - implementer IS a component - return it
        @return - 
    }*/
   public Component getComponent() { return(this); }
    

 
 /**
    This sets the first column selectable. Frequently this column is 
    used for row headers and selection in this column selects the 
    entire row.
    @param doit = make the firat column selectable
 */
 public void setFirstColumnSelectable(boolean doit)
 {
    m_InhibitFirstColumnSelect = !doit;
    // make sure we do not already do this
    if(m_InhibitFirstColumnSelect && isCellSelectionAllowed())
        clearSelection();
 }

 /**
    is the first column selectable. Frequently this column is 
    used for row headers and selection in this column selects the 
    entire row.
    @return - true if  firat column selectable
 */
 public boolean isFirstColumnSelectable(int row)
 {
    return(m_InhibitFirstColumnSelect);
 }

 public boolean isSortReversed() { return(m_SortReversed); }
 
 public void setSortReversed(boolean doit) { 
    m_SortReversed = doit; 
 }
 

 /**
    This does not say if the row is scrolled into view but rather 
    if it is included in the display - i.e not hidden
    @param - index in real coordinates
    @return - true if  visible
 */
  public boolean isRowVisible(int rowIndex) {
    return(m_RowIsShown[rowIndex]);
  }

 /**
    This sets a row hidden or shown
    @param - index in real coordinates
    @param - value - true if  visible
 */
  public void setRowVisible(int rowIndex, boolean value) {
  	if (value != isRowVisible(rowIndex)) {
      if(value)
         showRow(rowIndex);
      else
         hideRow(rowIndex);
    }
  }

 /**
    @return - number of visible rows
 */
  public int getVisibleRowCount() {
  	int result = 0;
    for (int i = 0; i < getRowCount(); i++) {
    	if (isRowVisible(i))
      	result++;
    }
  	return result;
  }

 /**
    This function converts from visible to real rows 
    @return - the real index of the row
 */
  public int getRealRowIndex(int visibleRowIndex) {
  	int visibleIndex = -1;
    for (int realIndex = 0; realIndex < getRowCount(); realIndex++) {
    	if (isRowVisible(realIndex)) {
      	visibleIndex++;
        if (visibleIndex == visibleRowIndex)
        	return realIndex;
      }
    }
  	return -1;
  }

  public int getVisibleRowIndex(int realRowIndex) {
  	int visibleIndex = -1;
		for (int realIndex = 0; realIndex < getRowCount(); realIndex++) {
    	if (isRowVisible(realIndex))
      	visibleIndex++;
      if (realIndex == realRowIndex)
        return visibleIndex;
    }
    return -1;
  }

  public void clearData() {
  	clearModel(this);
  }

  public void repaintHeaders()
  {
      for(int i = 0; i < m_Tables.length; i++) {
         if(m_Tables[i] != null) {
          	    JTableHeader TheHeader = m_Tables[i].getTableHeader();
          	    if(TheHeader != null)
          	        TheHeader.repaint(50); 
         }
      }
  }

  /**
    return the Table column - the function is needed since there may be 
    multiple tables to be searched for the appropriate data
    @param n - column number 
    @return - the column
  */
  public TableColumn getColumn(int n)
  {
      for(int i = 0; i < m_Tables.length; i++) {
         if(m_Tables[i] != null) {
             int NCurrent = m_Tables[i].getColumnCount();
             if(NCurrent < n) {
                TableColumnModel cm = m_Tables[i].getColumnModel();
                return(cm.getColumn(n));
             }
             n -= NCurrent;
         }
      }
      return(null);
  }
  
  /**
    return the Table column - the function is needed since there may be 
    multiple tables to be searched for the appropriate data
    @param n - column number 
    @return - the column
  */
  public void sizeColumnsToFitData() {
        for(int i = 0; i < m_Tables.length; i++) {
         if(m_Tables[i] != null) {
             int NCurrent = m_Tables[i].getColumnCount();
             for(int j = 0; j < NCurrent; j++) {
                TableColumnModel cm = m_Tables[i].getColumnModel();
                TableColumn tc = cm.getColumn(j);
                
             }
         }
      }
    }
    
  /* =====================================================
    The following operations are needed by TableColumnModelListener 
    This control really does not support them - i.e setGridDimensions
    is the only way to add a column
  */

  public void columnAdded(TableColumnModelEvent evt) {}
  public void columnRemoved(TableColumnModelEvent evt) {}
  public void columnMoved(TableColumnModelEvent e) { }
  public void columnMarginChanged(ChangeEvent e) {}
  public void columnSelectionChanged(ListSelectionEvent e) {}
  /* End of interface TableColumnModelListener */

    public Object[]   getColumnNames()     { return(m_ColumnNames); }
    public Object[][] getData()            { return(m_Data); }
    public int        getNumberShownRows() { return(m_NumberShownRows); }
    public int[]      getRealToShownMap()  { return(m_RealToShownMap); }
    public int[]      getShownToRealMap()  { return(m_ShownToRealMap); }
    public boolean[]  getRowIsShown()      { return(m_RowIsShown); }
    public boolean    isRowShown(int i)    { return(m_RowIsShown[i]); }
    
    public boolean isColumnEditable(int col) {
        return(m_ColumnIsEditable[col]);
    }
    
    public void setColumnEditable(int col,boolean doit) {
        m_ColumnIsEditable[col] = doit;
    }
    
    public int        getRealRow(int shownRow) {
        // may only happen during drawing after teardown
        if(m_ShownToRealMap == null || m_ShownToRealMap.length <= shownRow)
            return(0);
        return(m_ShownToRealMap[shownRow]);
    }
    
    public boolean isTableEventsInhibited() { return(m_InhibitTableEvents); }

    public void    setTableEventsInhibited(boolean doit) {
        if(m_InhibitTableEvents == doit)
            return;
        m_InhibitTableEvents = doit;
        if(m_InhibitTableEvents){
            m_PendingTableEvent = false;
        }
        else {
            if(m_PendingTableEvent) {
                m_PendingTableEvent = false;
                fireTableDataChanged(); // now redo all
            }
        }
    }


    /**
     * return the name of the requested column - if no name is stored
     * make one up.
     *  Return a default name for the column using spreadsheet conventions:
     *  A, B, C, ... Z, AA, AB, etc.
     * @return - the name 
     */
    public String getColumnName(int column) {
        if(m_ColumnNames != null && m_ColumnNames[column] != null)
            return(m_ColumnNames[column].toString());
        // make a default name
    	String result = "";
    	for (; column >= 0; column = column / 26 - 1) {
    	    result = (char)((char)(column%26)+'A') + result;
    	}
        return result;
    }

    public void setColumnName(int column,String name) {
        m_ColumnNames[column] = name;
    }

    /**
     * Convenience method for locating columns by name.
     * Implementation is naive so this should be overridden if
     * this method is to be called often. This method is not
     * in the TableModel interface and is not used by the JTable.
     */
    public int findColumn(String columnName) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (columnName.equals(getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     *  Returns Object.class by default
     */
    public Class getColumnClass(int columnIndex) {
	    return String.class;
    }

    /**
     *  This default implementation returns false for all cells
     */
    public boolean isCellEditable(int rowIndex, int columnIndex ) {
	    return(m_ColumnIsEditable[columnIndex]);
    }

    /**
        set the foreground color for the column headers of all tables
        @param newColor - color to set
     */
    public void setHeaderForeground(Color newColor) {
        if((m_HeaderForeground != null && m_HeaderForeground.equals(newColor)) ||
            (m_HeaderForeground == null && newColor == null))
            return;
        m_HeaderForeground = newColor;
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null) {
          	    JTableHeader TheHeader = m_Tables[i].getTableHeader();
          	    if(TheHeader != null)
          	        TheHeader.setForeground(m_HeaderForeground);
      	    }
      	}
    }
    public Color getHeaderForeground() {
        return(m_HeaderForeground);
    }
    
    public void setHeaderBackground(Color newColor) {
        if((m_HeaderBackground != null && m_HeaderBackground.equals(newColor)) ||
            (m_HeaderBackground == null && newColor == null))
            return;
        m_HeaderBackground = newColor;
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null) {
          	    JTableHeader TheHeader = m_Tables[i].getTableHeader();
          	    if(TheHeader != null)
          	        TheHeader.setBackground(m_HeaderBackground);
      	    }
      	}
    }

    public Color getHeaderBackground() {
        return(m_HeaderBackground);
    }
    
    public boolean isHeader3D()
    {
        return(m_isHeader3D);
    }

    public void setHeader3D(boolean doit)
    {
        m_isHeader3D = doit;
        // !!! notify
    }

    public boolean isHeaderShown()
    {
        return(m_HeaderShown);
    }

    public void setHeaderShown(boolean doit)
    {
        if(m_HeaderShown == doit)
            return;
        m_HeaderShown = doit;
        for(int i = 0; i < m_Tables.length; i++) {
            if(m_Tables[i] != null)
                m_Tables[i].setHeaderShown(doit);
        }
    }

    /** 
        The Fixed columns can have a different default background from the 
        scrollable columns - this is useful to distinguish the two
        @param newColor - default fixed columns color
     */
    public void setFixedForeground(Color newColor) {
        if((m_FixedForeground != null && m_FixedForeground.equals(newColor)) ||
            (m_FixedForeground == null && newColor == null))
            return;
        m_FixedForeground = newColor;
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null && m_Tables[i] instanceof FixedTable) {
      	        m_Tables[i].repaint(50);
       	    }
      	}
    }
    
    
    public Color getFixedForeground() {
        return(m_FixedForeground);
    }
    
    
    /** 
        The Fixed columns can have a different default background from the 
        scrollable columns - this is useful to distinguish the two
        @param newColor - default fixed columns color
     */
    public void setFixedBackground(Color newColor) {
        if((m_FixedBackground != null && m_FixedBackground.equals(newColor)) ||
            (m_FixedBackground == null && newColor == null))
            return;
        m_FixedBackground = newColor;
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null && m_Tables[i] instanceof FixedTable) {
      	        m_Tables[i].repaint(50);
       	    }
      	}
    }

    public Color getFixedBackground() {
        return(m_FixedBackground);
    }
    
    public boolean isShowGrid()
    {
        return(m_ShowGrid);
    }

    public void setShowGrid(boolean doit)
    {
        if(m_ShowGrid == doit)
            return;
        m_ShowGrid = doit;
        for(int i = 0; i < m_Tables.length; i++) 
           if(m_Tables[i] != null)
               m_Tables[i].setShowGrid(m_ShowGrid);
    }

    public void setForeColor(int rowIndex, int columnIndex ,Color newColor) {
        Color OldColor = m_CellForeground[rowIndex][columnIndex];
        if((OldColor != null && OldColor.equals(newColor)) ||
            (OldColor == null && newColor == null))
            return;
        m_CellForeground[rowIndex][columnIndex] = newColor;
        // !!! should fire changed
    }

    public void setBackColor(int rowIndex, int columnIndex, Color newColor) 
    {
        try {
            // having trouble with array out of bounds early
            int NRows = m_CellBackground.length;
            if(rowIndex >= NRows)
                return;
            Color[] myRow =  m_CellBackground[rowIndex];
            int Cols = myRow.length;
            if(rowIndex >= NRows || myRow == null ||
                columnIndex >= Cols) {
                Assertion.doNada();
                return;
                }

            // real code starts here
            Color OldColor = m_CellBackground[rowIndex][columnIndex];
            if((OldColor != null && OldColor.equals(newColor)) ||
                (OldColor == null && newColor == null))
                return;
            m_CellBackground[rowIndex][columnIndex] = newColor;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
           // throw new WrapperException(e);
        }
        // !!! should fire changed
    }

    public Color getForeColor(int rowIndex, int columnIndex) {
        int RealRow = getRealRow(rowIndex);
        if(m_CellForeground == null) {
            if(columnIndex >= m_NumberFixedColumns) {
                return(getForeground());
            }
            else {
                return(getFixedForeground());
            }
        }
        return(m_CellForeground[RealRow][columnIndex]);
    }

    public Color getBackColor(int rowIndex, int columnIndex ) {
        
       if(m_IsLabReport) {
            if(((rowIndex / 3) % 2) == 1) {
                return(Color.cyan);
            }
            else {
                return(Color.lightGray);
            }
        }
        Color ret = null;
        if(columnIndex < m_NumberFixedColumns)
            return(getFixedBackground());
            
        if(m_CellBackground != null) {
             int RealRow = getRealRow(rowIndex);
             // should only fail during draw during update
             if(m_CellBackground.length > RealRow && 
                m_CellBackground[RealRow].length > columnIndex) {
                 ret = m_CellBackground[RealRow][columnIndex];
                 if(ret != null)
                    return(ret);
               }
        }
        
        return(getBackground());
    }

    public Color getRealForeColor(int rowIndex, int columnIndex ) {
         // should only fail during draw during update
         if(m_CellForeground != null && m_CellForeground.length > rowIndex && 
            m_CellForeground[rowIndex].length > columnIndex)
             return(m_CellForeground[rowIndex][columnIndex]);
        if(columnIndex >= m_NumberFixedColumns) {
            return(getForeground());
        }
        else {
            return(getFixedForeground());
        }
    }
    public Color getRealBackColor(int rowIndex, int columnIndex ) {
         if(m_CellBackground != null && m_CellBackground.length > rowIndex && 
            m_CellBackground[rowIndex].length > columnIndex)
             return(m_CellBackground[rowIndex][columnIndex]);
        if(columnIndex >= m_NumberFixedColumns) {
            return(getBackground());
        }
        else {
            return(getFixedBackground());
        }
    }

    public void setRealForeColor(int rowIndex, int columnIndex ,Color newColor) {
        m_CellForeground[rowIndex][columnIndex] = newColor;
    }

    public void setRealBackColor(int rowIndex, int columnIndex ,Color newColor) {
        m_CellBackground[rowIndex][columnIndex] = newColor;
    }

    /**
     *  Used to set data in the model - viewed data is different
     */
    public void setRealValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(rowIndex < m_Data.length && m_Data[rowIndex] != null && columnIndex < m_Data[rowIndex].length)
            m_Data[rowIndex][columnIndex] = aValue;
        else
           Assertion.doNada();      
    }

    /**
     *  This empty implementation is provided so users dont have to implement
     *  this method if their data model is not editable.
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        int RealRow = getRealRow(rowIndex);
        if(RealRow >= m_Data.length || columnIndex >= m_Data[RealRow].length) {
            if(RealRow > m_Data.length)
                Assertion.doNada(); // these are bad - forgive off by one for now
            if(columnIndex > m_Data[RealRow].length)
                Assertion.doNada(); // these are bad - forgive off by one for now
            return;
        }

        m_Data[RealRow][columnIndex] = aValue;
    }

    /**
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            int RealRow = getRealRow(rowIndex);
            return(m_Data[RealRow][columnIndex]);
        }
        // These cases happen when the control draws during
        // teardown
        catch(NullPointerException ex) {
            return(gEmptyString);
        }
        catch(IndexOutOfBoundsException ex) {
            return(gEmptyString);
        }
    }


    public int getRealRowCount()
    {
        if(m_Data != null)
            return(m_Data.length);
         return(0);
    }

    public int getRowCount()
    {
        return(m_NumberShownRows);
    }

    public int getColumnCount()
    {
        if(m_Data != null && m_Data.length > 0 && m_Data[0] != null)
            return(m_Data[0].length);
         return(0);
    }

    /** 
        This method does most of the work of setGridDimensions and is
        used to allow OverlordTreeGrid to perform added housekeeping
    */
    protected void internalSetDimensions(int rowCount, int columnCount)
    {
        if(m_UsingRowHeaders)
            columnCount++;
        if(m_Data != null) {
            int OldRowCount = getRealRowCount();
            if(columnCount == getColumnCount() && rowCount == OldRowCount)
                return; // null operation

            Object[][] oldData = m_Data;
            Color[][] oldFore = m_CellForeground;
            Color[][] oldBack = m_CellBackground;
            Object[] oldColumnNames = m_ColumnNames;
            boolean[] OldColumnIsEditable = m_ColumnIsEditable;
            
            m_Data = new Object[rowCount][columnCount];
            m_CellForeground = new Color[rowCount][columnCount];
            m_CellBackground = new Color[rowCount][columnCount];
            m_ColumnNames = new Object[columnCount];
            m_ColumnIsEditable = new boolean[columnCount];
            if(isCellSelectionAllowed()) { // note revrese of row col to 
                                            // support column selection as ListSelection
                m_CellSelections = new boolean[columnCount][rowCount];
                m_NumberCellSelections = 0;
            }

            copyData(m_ColumnNames,oldColumnNames);
            copyData(m_ColumnIsEditable,OldColumnIsEditable);

             // Adding a column only
             if(rowCount == OldRowCount) {
                for(int i = 0; i < rowCount; i++) {
                    copyData(m_Data[i],oldData[i]);
                    copyData(m_CellForeground[i],oldFore[i]);
                    copyData(m_CellBackground[i],oldBack[i]);
                }
             }
             // changing rows + columns on existing data
             else {
                int copyRows = Math.min(rowCount,OldRowCount);

                m_NumberShownRows = rowCount;
                // update all row based data
                m_RealToShownMap = new int[m_NumberShownRows];
                m_ShownToRealMap = new int[m_NumberShownRows];
                m_RowIsShown     = new boolean[m_NumberShownRows];
                m_RowSelections  = new boolean[m_NumberShownRows];

                for(int i = 0; i < copyRows; i++) {
                    m_RealToShownMap[i] = i;
                    m_ShownToRealMap[i] = i;
                    m_RowIsShown[i] = true;
                    copyData(m_Data[i],oldData[i]);
                    copyData(m_CellForeground[i],oldFore[i]);
                    copyData(m_CellBackground[i],oldBack[i]);
                }
                if(copyRows < m_NumberShownRows) {
                    for(int i = copyRows; i < m_NumberShownRows; i++) {
                        m_RealToShownMap[i] = i;
                        m_ShownToRealMap[i] = i;
                        m_RowIsShown[i] = true;
                    }
                }
             }
        } // if(m_Data != null)
        // no preexisting data
        else {
            m_Data = new Object[rowCount][columnCount];
            m_CellForeground = new Color[rowCount][columnCount];
            m_CellBackground = new Color[rowCount][columnCount];
            m_ColumnNames = new Object[columnCount];
            m_ColumnIsEditable = new boolean[columnCount];
            m_NumberShownRows = rowCount;
            m_RealToShownMap = new int[m_NumberShownRows];
            m_ShownToRealMap = new int[m_NumberShownRows];
            m_RowIsShown     = new boolean[m_NumberShownRows];
            m_RowSelections  = new boolean[m_NumberShownRows];
            if(isCellSelectionAllowed()) { // note revrese of row col to 
                                            // support column selection as ListSelection
                m_CellSelections = new boolean[columnCount][rowCount];
                m_NumberCellSelections = 0;
            }

            for(int i = 0; i < rowCount; i++) {
                m_RealToShownMap[i] = i;
                m_ShownToRealMap[i] = i;
                m_RowIsShown[i] = true;
                m_Data[i] = new Object[columnCount];
            }
        }
        doClearSelection();
        fireTableDataChanged();
    }

    
    protected void copyData(Object[] dst,Object[] src) {
         int copyColumns = Math.min(dst.length,src.length);
         System.arraycopy(src,0,dst,0,copyColumns);
    }

    protected void copyData(boolean[] dst,boolean[] src) {
         int copyColumns = Math.min(dst.length,src.length);
         System.arraycopy(src,0,dst,0,copyColumns);
    }

    public void hideRows(int rowStart,int RowEnd)
    {
        boolean actionNeeded = false;
        for(int i = rowStart; i <= RowEnd; i++) {
            if(!rowIsHidden(i)) {
                actionNeeded = true;
                m_RowIsShown[i] = false;
                m_RowSelections[i] = false; // unselect row
            }
        }
        if(actionNeeded) {
            buildShownMap();
            fireTableDataChanged();
        }
    }

    public void showRows(int rowStart,int RowEnd)
    {
        boolean actionNeeded = false;
        for(int i = rowStart; i <= RowEnd; i++) {
            if(rowIsHidden(i)) {
                actionNeeded = true;
                m_RowIsShown[i] = false;
            }
        }
        if(actionNeeded) {
            buildShownMap();
            fireTableDataChanged();
        }
    }

    public void hideRow(int row)
    {
        if(rowIsHidden(row))
            return;
        m_RowIsShown[row] = false;
        buildShownMap();
        fireTableRowsDeleted(row,row);
    }

    public void showRow(int row)
    {
        if(!rowIsHidden(row))
            return;
        m_RowIsShown[row] = true;
        buildShownMap();
        fireTableRowsInserted(row,row);
    }

    public boolean rowIsHidden(int row)
    {
        return(!m_RowIsShown[row]);
    }

    protected void buildShownMap()
    {
        m_NumberShownRows = 0;
        for(int i = 0; i < getRealRowCount(); i++) {
            if(m_RowIsShown[i]) {
                m_RealToShownMap[i] = m_NumberShownRows;
                m_ShownToRealMap[m_NumberShownRows] = i;
                m_NumberShownRows++;
            }
            else {
                m_RealToShownMap[i] = -1; // not shown
            }
        }
    }

//
//  Managing Listeners
//
    public void addTableModelListener(TableModelListener l) {
	    m_listenerList.add(TableModelListener.class, l);
    }

    public void removeTableModelListener(TableModelListener l) {
	    m_listenerList.remove(TableModelListener.class, l);
    }

    public void addGridClickListener(GridClickListener l) {
	    m_listenerList.add(GridClickListener.class, l);
    }

    public void removeGridClickListener(GridClickListener l) {
	    m_listenerList.remove(GridClickListener.class, l);
    }
     public void addGridEditListener(GridEditListener l) {
	    m_listenerList.add(GridEditListener.class, l);
    }

    public void removeGridEditListener(GridEditListener l) {
	    m_listenerList.remove(GridEditListener.class, l);
    }
   
    

//
//  Fire methods
//

    /**
     * Notify all listeners that all cell values in the table's rows may have changed.
     * The number of rows may also have changed and the JTable should redraw the
     * table from scratch. The structure of the table, ie. the order of the
     * columns is assumed to be the same.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableDataChanged() {
        if(m_InhibitTableEvents) {
           m_PendingTableEvent = true;
           return;
        }
        fireTableChanged(new TableModelEvent(this));
    }

    /**
     * Notify all listeners that the table's structure has changed.
     * The number of columns in the table, and the names and types of
     * the new columns may be different from the previous state.
     * If the JTable recieves this event and its <I>autoCreateColumnsFromModel</I>
     * flag is set it discards any TableColumns that it had and reallocates
     * default ones in the order they appear in the model. This is the
     * same as calling <code>setModel(TableModel)</code> on the JTable.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableStructureChanged() {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been inserted.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableRowsInserted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been updated.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableRowsUpdated(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }

    /**
     * Notify all listeners that rows in the (inclusive) range
     * [<I>firstRow</I>, <I>lastRow</I>] have been deleted.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableRowsDeleted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }

    /**
     * Notify all listeners that the value of the cell at (row, column)
     * has been updated.
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableCellUpdated(int row, int column) {
        fireTableChanged(new TableModelEvent(this, row, row, column));
    }

    /**
     * Forward the given notification event to all TableModelListeners that registered
     * themselves as listeners for this table model.
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see EventListenerList
     */
    protected void fireTableChanged(TableModelEvent e) {
        // redo whole table later but do not fire now
        if(m_InhibitTableEvents) {
           m_PendingTableEvent = true;
           return;
        }
       	// Guaranteed to return a non-null array
    	Object[] listeners = m_listenerList.getListenerList();
    	// Process the listeners last to first, notifying
    	// those that are interested in this event
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==TableModelListener.class) {
    		((TableModelListener)listeners[i+1]).tableChanged(e);
    	    }
    	}
    }

    public boolean isColumnSelectionSupported() {
        return(m_ColumnSelectionSupported);
    }
    
    public void setColumnSelectionSupported(boolean b){
        m_ColumnSelectionSupported = b;
    }
    
    /* =========================================
        Sorting by columns
    */
    
    
    public boolean isColumnSortSupported() {
        return(m_ColumnSortSupported);
    }
    
    public void    setColumnSortSupported(boolean b){
        m_ColumnSortSupported = b;
    }

    public int getSortColumn() { return(m_SortColumn); }

    public void setSortColumn(int col)
    {
        if(getSortColumn() == col)
            return;

        // remember selections
        boolean[] oldSelect = null;
        Point[]   oldCellSelect = null;
        boolean hadSelection = hasSelection();
        if(hadSelection) {
           if(isCellSelectionAllowed()) {
               oldCellSelect = getSelectedCells();
           }
           else {
               oldSelect = Util.clone(m_RowSelections);
               clearSelection(); // now clear
           }
        }

        m_SortColumn = col;
        buildShownMap(); // effectively unsorting

        if(m_NumberShownRows < 2) {
            return;
        }
        // done - we have unsorted
        if(m_SortColumn < 0) {
            // restore any selection
    		// restore Selections
    		if(hadSelection) {
               if(isCellSelectionAllowed()) {
    		       setSelectedCells(oldCellSelect);
               }
               else {
    		       setSelectedRows(oldSelect);
    		   }
    		}
    		rebuildTable();
    		repaintHeaders();
            return;
      }

        int[] ra = new int[m_NumberShownRows + 1];

        for(int ix = 0; ix < m_NumberShownRows; ix ++)
            ra[ix + 1] = m_ShownToRealMap[ix];
            
        sortStringData(ra,col);
        if(m_SortReversed)
          invertSort(ra);
        
		int NValid = 0;
		// build shown maps
        for(int ix = 0; ix < m_NumberShownRows; ix ++)   {
            m_ShownToRealMap[ix] = ra[ix + 1];
		}

		// now build inverse map
		// mark all as not shown
        for(int ix = 0; ix < m_RealToShownMap.length; ix ++)   {
            m_RealToShownMap[ix] = -1; // not shown
        }
        // map all shown rows
        for(int ix = 0; ix < m_NumberShownRows; ix ++)   {
            m_RealToShownMap[m_ShownToRealMap[ix]] = ix;
		}

		// restore Selections
		if(hadSelection) {
           if(isCellSelectionAllowed()) {
		       setSelectedCells(oldCellSelect);
           }
           else {
		       setSelectedRows(oldSelect);
		   }
		}
 		rebuildTable();
		repaintHeaders();
   }

    // reverse the order of RA with an offset of 1
    // should this be a utility
    protected void invertSort(int[] ra)
    {
        int[] temp = new int[ra.length];
        for(int i = 0; i < ra.length; i++)
            temp[i] = ra[i];
        int n = 1;
        for(int k = ra.length - 1; k > 0; k--)
            ra[k] = temp[n++];
    }
    
    protected void sortStringData(int[] ra,int col)
    {
        int l,j,ir,i;
        int rra;
        int nrows = getRealRowCount();
        String[] realData = new String[nrows];
        for(int k = 0; k < nrows; k++)
            realData[k] = m_Data[k][col].toString().toUpperCase();
        
        // now run the heap	sort
        l =(m_NumberShownRows >> 1) + 1;
        ir = m_NumberShownRows;

        // Look here
        for(;;) {
            if(l > 1) {
                rra = ra[-- l];
            }
            else {
                rra = ra[ir];
                ra[ir] = ra[1];
                if(-- ir == 1) {
                    ra[1] = rra;
                    break;
                }
            }
            i = l;
            j = l << 1;
            while(j <= ir) {
                if(j < ir && compareStringData(ra[j],ra[j + 1],realData) ) {
                    ++ j;
                }
                if(compareStringData(rra,ra[j],realData)) {
                    ra[i] = ra[j];
                    j +=(i = j);
                }
                else {
                    j = ir + 1;
                }
            }
            ra[i] = rra;
        }
    }
    
    /**
        return array of points holding all selected cells - only useful if
        cell selection is permitted
        @return - points holding column and row of all selected cells
          may be 0 length but not null
    */
    public Point[] getSelectedCells()
    {
        Point[] ret = new Point[m_NumberCellSelections];
        if(m_CellSelections == null || m_NumberCellSelections <= 0)
            return(ret);
            
        int NSelections = 0;
         if(m_CellSelections != null && m_NumberCellSelections > 0 ) {
            for(int col = 0; col < m_CellSelections.length; col++) {
                for(int row = 0; row < m_CellSelections[0].length; row++) {
                    // note revrese of row col to 
                    // support column selection as ListSelection
                    if(m_CellSelections[col][row]) {
                        ret[NSelections++] = new Point(col,row);
                        if(m_NumberCellSelections <= NSelections)
                            return(ret);
                    }
                }
            }
         }
         Assertion.fatalError(); // should not get here
         return(ret);
    }

    /**
        programatically select the cells to me selected
        @param cells - points holding column and row of all selected cells
          may be 0 length but not null
    */
    public void setSelectedCells(Point[] cells)
    {
        clearSelection();
        if(cells != null) {
            for(int i = 0; i < cells.length; i++) {
                selectCell(cells[i].x,cells[i].y);
            }
        }

    }

    
    protected boolean compareColumnData(int row1,int row2,int col)
    {
        if(m_Data[row1][col] == null)
            return(m_Data[row2][col] != null);
        if(m_Data[row1][col].equals(m_Data[row2][col]))
            return(row1 < row2);
        return(m_Data[row1][col].toString().compareTo(m_Data[row2][col].toString()) < 0);
    }
    
    protected boolean compareStringData(int row1,int row2,String[] data)
    {
        if(data[row1] == null)
            return(data[row2] != null);
        if(data[row1].equals(data[row2]))
            return(row1 < row2);
        return(data[row1].compareTo(data[row2]) < 0);
    }
    
    protected boolean compareIntData(int row1,int row2,int[] data)
    {
        if(data[row1] == data[row2])
            return(row1 < row2);
        return(data[row1] < data[row2]);
    }


    /** 
        remove all data setting grid to hold empty strings
    */
  protected void clearModel(TableModel model) {
    	for (int col = 0; col < model.getColumnCount(); col++) {
          	for (int row = 0; row < model.getRowCount(); row++) {
            	model.setValueAt("", row, col); // Warning: change with next Swing version
            }
        }
    }


    public boolean isLabReport()
    {
        return(m_IsLabReport);
    }

    public void setLabReport(boolean doit)
    {
        if(m_IsLabReport == doit)
            return;
        m_IsLabReport = doit;
        // not really needed but it will force a repaint
        // and we should rarely call this interface
        rebuildTable();
    }


    public void rebuildTable()
    {
        for(int i = 0; i < m_Tables.length; i++) {
            if(m_Tables[i] != null)
                m_Tables[i].doLayout();
        }
            
        int UpdateRows = getRowCount() + 1000;
        TableModelEvent rebuildEvent = new TableModelEvent(this, TableModelEvent.HEADER_ROW,UpdateRows, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        fireTableChanged(rebuildEvent);
    }

    // =============================
    // Selection Code
    // =============================

    public boolean[] getCellSelections(int Col)
    {
        if(isCellSelectionAllowed()) // note col row not row col
            return(m_CellSelections[Col]);
        return(null);
    }

    public void clearSelection()
    {
        if(m_SelectionEmpty)
            return;
        boolean[] oldSelect = Util.clone(m_RowSelections);
        doClearSelection();
        m_AnchorIndex = m_LeadIndex = -1;
        notifySelectionChanges(oldSelect);
    }
    //
    // isSomeCellSelected
    // true if there is a cell selection
    protected boolean isSomeCellSelected()
    {
        return(m_NumberCellSelections > 0);
    }

    //
    // clear - do not notify
    // return says selection changed
    protected boolean doClearSelection()
    {
         if(m_SelectionEmpty)
            return(false);
         boolean selectionChanged = false;
         if(m_CellSelections != null && m_NumberCellSelections > 0 ) {
            for(int col = 0; col < m_CellSelections.length; col++) {
                for(int row = 0; row < m_CellSelections[0].length; row++) {
                    // note revrese of row col to 
                    // support column selection as ListSelection
                    if(m_CellSelections[col][row]) {
                        deSelectCell(row,col);
                        selectionChanged = true;
                        if(m_NumberCellSelections == 0)
                            return(selectionChanged);
                    }
                }
            }
         }
        for(int i = 0; i < m_RowSelections.length; i++) {
            if(m_RowSelections[i]) {
                selectionChanged = true;

                m_RowSelections[i] = false;
            }
            m_SelectionEmpty = true;
        }
        return(selectionChanged);
    }

    public boolean isSelectedIndex(int index) {
        int realRow = getRealRow(index);
        if(realRow < 0)
            return(false);
	    return (m_RowSelections[realRow]);
    }
    


    public boolean isSelectionEmpty() {
	    return (m_SelectionEmpty);
    }

    public int getAnchorSelectionIndex() {
        return m_AnchorIndex;
    }
    public int getLeadSelectionIndex() { return m_LeadIndex; }

    public void removeSelectionInterval(int index0, int index1)
    {
        if(m_SelectionEmpty)
            return;
        boolean[] oldSelect = Util.clone(m_RowSelections);
        boolean selectionChanged = false;
        for(int i = index0; i <= index1; i++) {
            int realRow = getRealRow(i);
            if(realRow >= 0) {
                if(m_RowSelections[realRow]) {
                    selectionChanged = true;
                    m_RowSelections[realRow] = false;
                }
            }
        }
    	m_AnchorIndex = index0;
    	m_LeadIndex = index1;
    	notifySelectionChanges(oldSelect);
    }

    protected void setSelectedRows(boolean[] oldSelect)
    {
        boolean[] emptySelect = new boolean[oldSelect.length]; // set empty for notifications
        // clear selections
        clearSelection();
        // copy new selections
        for(int row = 0; row < m_RowSelections.length; row++) {
            m_RowSelections[row] = oldSelect[row];
        }
        // notify
        notifySelectionChanges(emptySelect);
    }

    protected void notifySelectionChanges(boolean[] oldSelect)
    {
        int i = 0;
        boolean inValueChanged = false;
        m_SelectionEmpty = true;
        int StartValueChanged = 0;

        while(i < m_ShownToRealMap.length) {
            int realRow = getRealRow(i);
            if(oldSelect[realRow] != m_RowSelections[realRow]) {
                // start interval if not started
                if(!inValueChanged) {
                    inValueChanged = true;
                    StartValueChanged = i;
                }
            }
            else {
                // end interval if  started
                if(inValueChanged) {
                    fireValueChanged(StartValueChanged, i - 1);
                    inValueChanged = false;
                }
            }
            if(m_RowSelections[realRow])
                m_SelectionEmpty = false;
            i++;
        }
        // finish last interval
        if(inValueChanged) {
            fireValueChanged(StartValueChanged, i - 1);
        }
    }

    public void selectAll()
    {
        setSelectionInterval(0,m_RowSelections.length - 1);
    }

   public void setAnchorSelectionIndex(int ignore) {
   //     Assertion.fixThis();
   }
   
   public void setLeadSelectionIndex(int ignore)
   {
        // Assertion.fixThis();
   }
   
   public void setSelectionInterval(int index0, int index1)
    {
        if(index0 != index1 && !isMultipleSelectionAllowed())
            throw new IllegalArgumentException("Only one selection allowed");
            
        boolean[] oldSelect = Util.clone(m_RowSelections);
        boolean selectionChanged = doClearSelection();
        doAddSelectionInterval(index0,index1);
    	notifySelectionChanges(oldSelect); // tell any selection changes
    }

    public void addSelectionInterval(int index0, int index1)
    {
        boolean[] oldSelect = Util.clone(m_RowSelections);
    	int mode = getSelectionMode();
    	if ((mode == SINGLE_SELECTION) || (mode == SINGLE_INTERVAL_SELECTION)) {
    	    setSelectionInterval(index0, index1);
    	    return;
    	}
    	doAddSelectionInterval(index0,index1);
    	notifySelectionChanges(oldSelect); // tell any selection changes
    }

    // Internal add selection - do no notifications - handled
    // by calling program
    // Note coordinated are visible not model
    protected boolean doAddSelectionInterval(int index0, int index1)
    {
        boolean selectionChanged = false;
        m_SelectionEmpty = true;
        for(int i = index0; i <= index1; i++) {
            int realRow = getRealRow(i);
            if(realRow >= 0) {
                if(!m_RowSelections[realRow]) {
                    selectionChanged = true;
                    m_RowSelections[realRow] = true;
                }
            }
        }
    	m_AnchorIndex = index0;
    	m_LeadIndex = index1;
    	m_SelectionEmpty = false;
    	return(selectionChanged);
    }

    public int getMinSelectionIndex()
    {
        if(m_SelectionEmpty)
            return(-1);
        for(int i = 0; i < m_RowSelections.length; i++) {
            if(m_RowSelections[i])
                return(i);
        }
        Assertion.fatalError(); // should not get here
        return(-1);
    }

    public int getMaxSelectionIndex()
    {
        if(m_SelectionEmpty)
            return(-1);
        for(int i = m_RowSelections.length - 1; i >= 0; i--) {
            if(m_RowSelections[i])
                return(i);
        }
        Assertion.fatalError(); // should not get here
        return(-1);
    }

    
    public int getSelectionMode() { return(m_SelectionMode); }

    public void setSelectionMode(int newMode) {
        if(m_SelectionMode == newMode)
            return;
    	switch (m_SelectionMode) {
        	case SINGLE_SELECTION:
        	case SINGLE_INTERVAL_SELECTION:
        	case MULTIPLE_INTERVAL_SELECTION:
        	    m_SelectionMode = newMode;
        	    clearSelection();
        	    break;
        	default:
        	    throw new IllegalArgumentException("invalid selectionMode");
    	}
     }

    public void setValueIsAdjusting(boolean b) {
    	if (b != m_IsAdjusting) {
    	    this.m_IsAdjusting = b;
    	    this.fireValueChanged(b);
    	}
    }
    public boolean getValueIsAdjusting() { return(m_IsAdjusting); }

    /**
     * Insert length indices beginning before/after index.  This is typically
     * called to sync the selection model with a corresponding change
     * in the data model.
     */

    public void insertIndexInterval(int index, int length, boolean before)
    {
        // ignore the model handles this
    }
    /**
     * Remove the indices in the interval index0,index1 (inclusive) from
     * the selection model.  This is typically called to sync the selection
     * model width a corresponding change in the data model.  Note
     * that (as always) index0 need not be <= index1.
     */
    public void removeIndexInterval(int index0, int index1)
    {
        // ignore the model handles this
    }

    public void addListSelectionListener(ListSelectionListener l) {
 	    m_listenerList.add(ListSelectionListener.class, l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
 	    m_listenerList.remove(ListSelectionListener.class, l);
    }
    /**
     * Notify listeners that we are beginning or ending a
     * series of value changes
     */
    protected void fireValueChanged(boolean isAdjusting) {
	    fireValueChanged(getMinSelectionIndex(), getMaxSelectionIndex(), isAdjusting);
    }


    /**
     * Notify ListSelectionListeners that the value of the selection,
     * in the closed interval firstIndex,lastIndex, has changed.
     */
    protected void fireValueChanged(int firstIndex, int lastIndex) {
	    fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
    }

    /**
     * @param ev - the event holding Old and new values
     * @see EventListenerList
     */
    public void fireCellEdited(GridEditEvent ev)
    {
       	// Guaranteed to return a non-null array
    	Object[] listeners = m_listenerList.getListenerList();
    	// Process the listeners last to first, notifying
    	// those that are interested in this event
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==GridEditListener.class) {
    		((GridEditListener)listeners[i+1]).cellEdited(ev);
    	    }
    	}
    }

    /**
     * @param firstIndex The first index in the interval.
     * @param index1 The last index in the interval.
     * @param isAdjusting True if this is the final change in a series of them.
     * @see EventListenerList
     */
    public void fireCellDoubleClicked(GridClickEvent ev)
    {
        int realRow = getRealRow(ev.getRow());
        ev.setRow(realRow);
       	// Guaranteed to return a non-null array
    	Object[] listeners = m_listenerList.getListenerList();
    	// Process the listeners last to first, notifying
    	// those that are interested in this event
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==GridClickListener.class) {
    		((GridClickListener)listeners[i+1]).cellDoubleClicked(ev);
    	    }
    	}
    	if(!ev.isConsumed())    
    	    selfCellDoubleClicked(ev);

    }
    /**
     *  local Cell Double click handler - call only if 
     *  not consumed
     */
    public void selfCellDoubleClicked(GridClickEvent ev)
    {
    }

    /**
     *  high level Cell click handler
     */
    public void fireCellClicked(GridClickEvent ev)
    {
        if(ev.getRow() != GridClickEvent.HEADER_ROW) {
            int realRow = getRealRow(ev.getRow());
            ev.setRow(realRow);
        }
       	// Guaranteed to return a non-null array
    	Object[] listeners = m_listenerList.getListenerList();
    	// Process the listeners last to first, notifying
    	// those that are interested in this event
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==GridClickListener.class) {
    		((GridClickListener)listeners[i+1]).cellClicked(ev);
    	    }
    	}
    	if(!ev.isConsumed())    
    	    selfCellClicked(ev);
    }
    /**
     *  local Cell click handler - call only if 
     *  not consumed
     */
    public void selfCellClicked(GridClickEvent ev)
    {
        if(ev.getRow() == GridClickEvent.HEADER_ROW) {
            if(isColumnSortSupported()) {
                if((ev.getModifiers() & Event.CTRL_MASK) != 0) {
                    setSortReversed(!isSortReversed());
                    setSortColumn(-1); // this forces a new sort
                }
                int SortCol = getSortColumn();
                if(SortCol != ev.getCol()) {
                    setSortColumn(ev.getCol());
                }
                else {
                    setSortColumn(-1);
                }
            }
        }
    }
 
    
    /**
     * @param firstIndex The first index in the interval.
     * @param index1 The last index in the interval.
     * @param isAdjusting True if this is the final change in a series of them.
     * @see EventListenerList
     */
    public void fireClickReleased(GridClickEvent ev)
    {
        if(ev.getRow() != GridClickEvent.HEADER_ROW) {
            int realRow = getRealRow(ev.getRow());
            ev.setRow(realRow);
        }
        // Guaranteed to return a non-null array
    	Object[] listeners = m_listenerList.getListenerList();
    	// Process the listeners last to first, notifying
    	// those that are interested in this event
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==GridClickListener.class) {
    		((GridClickListener)listeners[i+1]).clickReleased(ev);
    	    }
    	}
     	if(!ev.isConsumed())    
    	    selfClickReleased(ev);
   }
 
    /**
     *  local Cell click handler - call only if 
     *  not consumed
     */
    public void selfClickReleased(GridClickEvent ev)
    {
    }
 
 
    public void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting)
    {
    	Object[] listeners = m_listenerList.getListenerList();
    	ListSelectionEvent e = null;

    	for (int i = listeners.length - 2; i >= 0; i -= 2) {
    	    if (listeners[i] == ListSelectionListener.class) {
    		if (e == null) {
    		    e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
    		}
    		((ListSelectionListener)listeners[i+1]).valueChanged(e);
    	    }
    	}
    }
    
    
// ===================================================================
// End Table model code
// ===================================================================




	public SubTable getFixedTable() {
      	return(m_Tables[0]);
     }

	public SubTable getScrollableTable() {
      	return(m_Tables[1]);
     }

	public OverlordTableColumnModel getColumnModel() {
      	return m_ColumnModel;
      }

    public OverlordTableModelInterface getModel() {
        return(this);
    }

    public ListSelectionModel getSelectionModel() {
        return(m_SelectModel);
    }

  public void setSelectionModel(ListSelectionModel value) {
  //  if(m_SelectModel == value)
   //     return;
  	for(int i = 0; i < m_Tables.length; i++)
  	    if(m_Tables[i] != null)
  	        m_Tables[i].setSelectionModel(m_SelectModel);
  }

  public void setBackground(Color background) {
  	super.setBackground(background);
  	if(m_Tables == null)
  	    return;
  	for(int i = 0; i < m_Tables.length; i++)
  	    if(m_Tables[i] != null)
  	        m_Tables[i].setBackground(background);
  }

  public void setForeground(Color color) {
  	super.setForeground(color);
  	if(m_Tables == null)
  	    return;
  	for(int i = 0; i < m_Tables.length; i++)
  	    if(m_Tables[i] != null)
  	        m_Tables[i].setForeground(color);
  }


	/* Interface OverlordGridAgent */

  /**{ method
      @name setGridDimensions
      @param rows - number rows
      @param cols - number columns
      @function - set the dimensions of the grid. Rows beyond rows
          are deleted as are columns beyons cols. added cells contain
          blanks
  }*/
  public void setGridDimensions(int rows,int cols) {
    int oldCount = m_ColumnModel.getColumnCount();
   	m_ColumnModel.setColumnCount(cols);
  /*  for(int i = oldCount; i  < cols; i++) {
        TableColumn TheColumn = new TableColumn(i);
        TheColumn.setWidth(100);
        m_ColumnModel.addColumn(TheColumn);
    }
    */
    internalSetDimensions(rows,cols);
  }

	/**{ method
	    @name getGridText
	    @param c - column
	    @param r - row
	    @return - the text cells with checkboxes may return "t" or "f"
	    @function - return the text in the requested cell
	}*/
	public String getGridText(int c,int r) {
		Object value = getValueAt(r, c);
		String stringValue = (value == null) ? "" : value.toString();

		// If the column c contains components of type JCheckBox,
		// return "t" or "f"
		Component columnComponent = getColumnComponent(c);
		if (columnComponent instanceof JCheckBox) {
			return stringValue.equalsIgnoreCase("true") ? "t" : "f";
		}
		return stringValue;
	}

  /**{ method
      @name setGridText
      @param c - column
      @param r - row
      @param text - new text
      @function - set the text in the cell. A GridAccessException
          (RunTime) can be thrown if the cell is out of bounds. Use
          "t" and "f" for checkboxes

  }*/
  public void setGridText(int row,int col,String text) {
  /*  Component columnComponent = getColumnComponent(c);
    if (columnComponent instanceof JCheckBox) {
      text = text.equals("t") ? "true" : "false";
    }
    */
    if(text.equals("25.0"))
        Assertion.doNada();
    if(text.equals("1.1"))
        Assertion.doNada();
    if(text.equals("0.125"))
        Assertion.doNada();
        
    setValueAt(text, row, col);
  }
  /**{ method
      @name setGridObject
      @param c - column
      @param r - row
      @param text - new text
      @function - set the text in the cell. A GridAccessException
          (RunTime) can be thrown if the cell is out of bounds. Use
          "t" and "f" for checkboxes

  }*/
  public void setGridObject(int row,int col,Object text) {
    setValueAt(text, row, col);
  }

  /**{ method
      @name setColumnHeader
      @param c - column
      @param text - new text
      @function - set the header text for the given colummn -
          ideally column headers are not data and do not scroll.
          If this function is not called, dont show headers.
          If the header is in the form "Foo \n bbb", show the header
          with two lines.

  }*/
	public void setColumnHeader(int c,Object text) {
	        
        if (m_ColumnModel == null || c >= m_ColumnModel.getColumnCount()) {
          return;
        }
        if(!isHeaderShown())
            setHeaderShown(true);
        m_ColumnModel.getColumn(c).setHeaderValue(text);
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null) {
          	    JComponent HeaderItem = m_Tables[i].getTableHeader();
          	    HeaderItem.repaint();
      	    }
      	}
    }
    
   
   /**{ method
      @name getRowHeight
      @function - get the height of a row
  }*/
  public int getRowHeight() {
      if(m_Tables != null && m_Tables[0] != null)
        return(m_Tables[0].getRowHeight());
      return(16); // guess
   }
   /**{ method
      @name getRowHeight
      @function - get the height of a row
  }*/
  public int getTotalRowHeight() {
      if(m_Tables != null && m_Tables[0] != null)
        return(m_Tables[0].getRowHeight() + m_Tables[0].getIntercellSpacing().height);
      return(18); // guess
   }
   
   /**{ method
      @name getIntercellSpacing
      @function - get the height of a row
  }*/
  public Dimension getIntercellSpacing() {
      if(m_Tables != null && m_Tables[0] != null)
        return(m_Tables[0].getIntercellSpacing());
      return(new Dimension(3,2)); // guess
   }

  /**{ method
      @name setRowHeader
      @param r - row
      @param text - new text
      @function - set the header text for the given row -
          ideally row headers are not data and do not scroll

  }*/
  public void setRowHeader(int r,String text) {
      setValueAt(text, r, 0);      
  }
  
    /**{ method
        @name setUsingRowHeaders
        @function <Add Comment Here>
        @param doit <Add Comment Here>
        @policy <Add Comment Here>
        @primary
    }*/
    public void setUsingRowHeaders(boolean doit) {
        if(m_UsingRowHeaders == doit)
            return;
        m_UsingRowHeaders = doit;
        // should rebuilt table
    }

    public boolean isUsingRowHeaders() {
        return(m_UsingRowHeaders);
    }
  
    public void setSelectOnHeadersOnly(boolean doit) {
        if(m_SelectOnHeadersOnly == doit)
            return;
        m_SelectOnHeadersOnly = doit;
    }

    public boolean isSelectOnHeadersOnly() {
        return(m_SelectOnHeadersOnly);
    }
  
  

  //- *******************
  //- Selection

  /**{ method
      @name setCellSelectionAllowed
      @param doit - true allows
      @function - allow individual cells to be selected. If false only
          rows can be selected
  }*/
  public void setCellSelectionAllowed(boolean doit) {
    if(m_CellSelectionAllowed == doit)
        return;
  	 m_CellSelectionAllowed = doit;
  	 m_NumberCellSelections = 0;
  	 // tell managed tables
  	 if(m_Tables != null) {
      	 for(int i = 0; i < m_Tables.length; i++)
      	    if(m_Tables[i] != null)
      	        m_Tables[i].setCellSelectionEnabled(doit);
  	 }

  	 if(doit) {
      	 int NRows = getRowCount();
      	 int NCols = getColumnCount();
      	 if(m_CellSelections == null && NRows > 0 && NCols  > 0 ) {
      	    // Note m_CellSelections is Col, row
      	    m_CellSelections = new boolean[NCols][NRows];
      	 }
  	 }
  	 else {
  	    m_CellSelections = null;
  	 }
  }

  /**{ method
      @name isCellSelectionAllowed
      @return - the sytate
      @function - true if cells can be selected otherwise only rows can be selcted
  }*/
  public boolean isCellSelectionAllowed() {
    return m_CellSelectionAllowed;
  }

  /**{ method
      @name setMultipleSelectionAllowed
      @param doit - true allows
      @function - allow multiple items to be selected. If false only
          one item can be selected
  }*/
  // Multiple selection for what ? row, columns, cells, all of them
  public void setMultipleSelectionAllowed(boolean doit) {
    if(doit) 
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    else
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    
  }

  /**{ method
      @name isMultipleSelectionAllowed
      @return - the state
      @function - true if multiple items can be selected
  }*/
  public boolean isMultipleSelectionAllowed() {
    return(getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  }

  /**{ method
      @name selectRow
      @param NRow - row to select
      @function - select a row in the grid. Do not clear any other selections.
  }*/
  public void selectRow(int NRow) {
    if(isMultipleSelectionAllowed())
        addSelectionInterval(NRow, NRow);
    else
        setSelectionInterval(NRow, NRow);
  }


  /**{ method
      @name deSelectRow
      @param NRow - row to select
      @function - unselect a row in the grid. If multiple selections
          are allowed this does not clear other selections
  }*/
  public void deSelectRow(int NRow) {
    removeSelectionInterval(NRow, NRow);
  }

  /**{ method
      @name selectShownCell
      @param Row - row
      @param Col  - col
      @function - select a cell in the grid. Only acts if cell selection
          is allowed
  }*/
  public void selectShownCell(int Col,int Row) {
  	 int RealRow = m_ShownToRealMap[Row];
  	 if(RealRow >= 0)
  	    selectCell(Col,RealRow);
  }
  /**{ method
      @name deSelectShownCell
      @param Row - row
      @param Col  - col
      @function - select a cell in the grid. Only acts if cell selection
          is allowed
  }*/
  public void deSelectShownCell(int Col,int Row) {
  	 int RealRow = m_ShownToRealMap[Row];
  	 if(RealRow >= 0)
  	    deSelectCell(RealRow,Col);
  }

  /**{ method
      @name selectCell
      @param Row - row
      @param Col  - col
      @function - select a cell in the grid. Only acts if cell selection
          is allowed
  }*/
  public void selectCell(int Row,int Col ) {
  	 if(!isCellSelectionAllowed())
  	    return;
  	 // Note reversel or Row and Col
  	 if(m_CellSelections[Col][Row])
  	    return;
  	// Note reversel or Row and Col
  	 m_CellSelections[Col][Row] = true;
  	 m_NumberCellSelections++;
  	 m_SelectionEmpty = false;
  	 // now notify - need to map to shown coordinates
  	 int ShownRow = m_RealToShownMap[Row];
  	 if(ShownRow >= 0)
  	    fireValueChanged(ShownRow,ShownRow);
  }

  /**{ method
      @name deSelectCell
      @param Row - row
      @param Col  - col
      @function - unselect a cell in the grid. Only acts if cell selection
          is allowed
  }*/
  public void deSelectCell(int Row,int Col) {
  	 if(!isCellSelectionAllowed())
  	    return;
  	 // Note reversel or Row and Col
  	 if(!m_CellSelections[Col][Row])
  	    return;
  	 // Note reversel or Row and Col
  	 m_CellSelections[Col][Row] = false;
  	 m_NumberCellSelections--;
  	 m_SelectionEmpty = m_NumberCellSelections > 0;
  	 // now notify - need to map to shown coordinates
  	 int ShownRow = m_RealToShownMap[Row];
  	 if(ShownRow >= 0)
  	    fireValueChanged(ShownRow,ShownRow);
  }

  /**{ method
      @name isCellSelected
      @param Row - row
      @param Col  - col
      @return - true if a cell is selected
      @function - tests is a cell is selected -
  }*/
  public boolean isCellSelected(int Row,int Col) {
  	 if(isCellSelectionAllowed()) {
  	     int realRow = getRealRow(Row);
  	     // note selections reverses col and row
           if(Col >= m_CellSelections.length || realRow >= m_CellSelections[Col].length )
              return false;
           return(m_CellSelections[Col][realRow]);
  	 }
  	 else {
  	    return(isSelectedIndex(Row));
  	 }
  }

  /**{ method
      @name hasSelection
      @function - return true if there is one or more items selected
  }*/
  // Any kind of item ? cell, row, column
  public boolean hasSelection() {
    return(!isSelectionEmpty());
  }

  /**{ method
      @name rowIsSelected
      @param NRow - row to test
      @return - true if selected
      @function - return true if the row is selected - if cell selection
          id allowed return true if the entire row is selected
  }*/
  public boolean rowIsSelected(int NRow) {
  	if (isCellSelectionAllowed()) {
	    // add something for cell selection
	    return false;
    }
    else
      return(isSelectedIndex(NRow));
  }

  /**{ method
      @name numberSelectedRows
      @return - the number
      @function - return the of selected rows
  }*/
  public int numberSelectedRows() {
  	if (isCellSelectionAllowed()) {
	    // add something for cell selection
	    return 0;
    }
    else {
    	int result = 0;
      for (int i = 0; i < getRowCount(); i++) {
      	if (isSelectedIndex(i))
        	result++;
      }
      return result;
    }
  }

  /**{ method
      @name getScroll
      @return - true if selected
      @function <Add Comment Here>
  }*/
  // ????
  public Point getScroll() {
    return new Point();
  }

   //- *******************
  //- Hide and Show


  /**{ method
      @name setCellTextColor
      @param c - column
      @param r - row
      @function - set the Text color in the cell to color
  }*/
  public void setCellTextColor(int r,int c,Color color) {
      ((OverlordGrid)getModel()).setForeColor(r,c,color);
  }

  /**{ method
      @name setCellColor
      @param c - column
      @param r - row
      @function - set the background color in the cell to color
  }*/
  public void setCellColor(int r,int c,Color color) {
      ((OverlordGrid)getModel()).setBackColor(r,c,color);
  }

  /**{ method
      @name getGridFont
      @return - Font
      @function - get the Font used by all cells except column headers
  }*/
  public Font getGridFont() {
    return m_GridFont;
  }

  /**{ method
      @name setGridFont
      @param newFont - Font to use
      @function - set the Font used by all cells except column headers
  }*/
  public void setGridFont(Font newFont) {
  	m_GridFont = newFont;
    for (Enumeration e = m_ColumnModel.getColumns(); e.hasMoreElements(); ) {
    	TableColumn column = (TableColumn)e.nextElement();
      if (column instanceof OverlordTableColumn)
      	((OverlordTableColumn)column).setFont(m_GridFont);
    }
  }

  /**{ method
      @name getHeaderFont
      @return - Font
      @function - get the Font used by column headers
  }*/
  public Font getHeaderFont() {
    return m_HeaderFont;
  }

  /**{ method
      @name setHeaderFont
      @param newFont - Font to use
      @function - set the Font used by  column headers
  }*/
  public void setHeaderFont(Font newFont) {
  	m_HeaderFont = newFont;
  	for(int i = 0; i < m_Tables.length; i++) {
  	    if(m_Tables[i] != null)
  	        m_Tables[i].setFont(m_HeaderFont);
  	}
  }

  /**{ method
      @name xyToCell
      @return - x = column y = row
      @function - convert x,y pixels into a cell
  }*/
  public Point xyToCell(int x,int y) {
  	return new Point();
  }

  /**{ method
      @name lastGridRow
      @return - last row number
      @function - get the last row.
      note: The first row number is 0. Thus, a grid with one row will
      return the same number (0) as a grid with no rows.
  }*/
  public int lastGridRow() {
    return Math.max(0, getRowCount() - 1);
  }

  /**{ method
      @name lastGridCol
      @return - last col number
      @function - get the last column
  }*/
  public int lastGridCol() {
    return Math.max(0, getColumnCount() - 1);
  }

  /**{ method
      @name firstNonHeaderRow
      @return - first row which is not a header - resolves whether
          header is 0 or 1 ...
      @function - the row
  }*/
  // Header = fixed row ???? else always 0
  public int firstNonHeaderRow() {
    return 0;
  }

  /**{ method
      @name firstNonHeaderCol
      @return - first col which is not a header - resolves whether
          header is 0 or 1 ...
      @function - the col
  }*/
  // Header = fixed col ???? else always 0
  public int firstNonHeaderCol() {
    if(isUsingRowHeaders())
        return(1);
    else
        return(0);
  }

  /**{ method
      @name numberHeaderRows
      @return - number or rows used for headers - may be 0
      @function - see return
  }*/
  public int numberHeaderRows() {
    return 0;
  }

  /**{ method
      @name getNumberHeaderCols
      @return - number or cols used for headers - may be 0
      @function - see return
  }*/
  public int getNumberHeaderCols() {
    if(isUsingRowHeaders())
        return(1);
    else
        return(0);
  }

  /**{ method
      @name setFixedColumns
      @param n - number fixed cols
      @function - Fixed columns do not scroll with when the grid is scrolled
          horizontally it is ok if only 0 and 1 work here
  }*/
  public void setFixedColumns(int n) {
    if(m_NumberFixedColumns == n)
        return;

     if(m_Data == null)
        return; // grid not initialized - save for future reference
        
     m_NumberFixedColumns = n;
     createTables();
  }

  /**{ method
      @name getFixedColumns
      @function - the number of fixed columns
  }*/
  public int getFixedColumns() {
     return(m_NumberFixedColumns);
  }

  /**{ method
      @name setFixedRows
      @function <Add Comment Here>
  }*/
  public void setFixedRows(int n) {
      Assertion.fixThis(); // !!! not implemented
  }

  /**{ method
      @name getFixedRows
      @function <Add Comment Here>
  }*/
  public int getFixedRows() {
    return 0;
  }
  
  

  /**{ method
      @name setAutoWidth
      @param doit   - set if true
      @function - when autowidth is set columns will size to hold the
          widest element. when false columns sizes are set with
          setColumnWidth true is default.
      @see #setColumnWidth
  }*/
  public void setAutoWidth(boolean doit) {
    if(m_AutoWidth == doit)
        return;

        
  	for(int i = 0; i < m_Tables.length; i++) {
  	    if(m_Tables[i] != null)
	        m_Tables[i].setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
  	}
        
/*  !!! Tryoing hard to fix sizing bug
    m_AutoWidth = doit;
   if (m_AutoWidth) {
      	for(int i = 0; i < m_Tables.length; i++) {
      	    if(m_Tables[i] != null)
    	        m_Tables[i].setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      	}
    }
    else {
      	for(int i = 0; i < m_Tables.length; i++) {
    	    if(m_Tables[i] != null)
      	        m_Tables[i].setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      	}
    }
    setColumnsToDesiredWidths();
    doLayout();  // TODO: do something for automatically resize columns
    */
  }
  
  public void setColumnsToDesiredWidths() {
  	for(int i = 0; i < m_Tables.length; i++) {
	    if(m_Tables[i] != null)
  	        m_Tables[i].setColumnsToDesiredWidths();
  	}
  }

  /**{ method
      @name setAutoHeight
      @param doit   - set if true
      @function - when autoheight is set rows will size to hold the
          tallest element. When false row sizes are set with
          setRowHeight true is default.
      @see #setRowHeight
  }*/
  public void setAutoHeight(boolean doit) {
    Assertion.fixThis(); // !!! not implemented
  }

  /**{ method
      @name setRowHeight
      @param row   - affected row
      @param height - height in pixels
      @function - set the height of the given row
  }*/
  public void setRowHeight(int row,int height) {
    Assertion.fixThis(); // !!! not implemented
  }

  /**{ method
      @name setColumnWidth
      @param col   - affected col
      @param width - height in pixels
      @function - set the width of the given column
      @see #setAutoWidth
  }*/
  public void setColumnWidth(int col,int width) {
    TableColumn column = m_ColumnModel.getColumn(col);
    column.setWidth(width);
  }

  /**{ method
      @name clear
      @function - set all text to blanks
  }*/
  // TODO: support Fixed cols.
  public void clear() {
  	clearData();
  }

  /**{ method
      @name setColumnCheckBox
      @param Col - the column
      @param doit - true if so otherwise text
      @function - set a column to check boxes
  }*/
  public void setColumnCheckBox(int c,boolean isCheckBox) {
    if (c < 0 || c >= m_ColumnModel.getColumnCount())
      return;

    TableColumn tableColumn = m_ColumnModel.getColumn(c);
    if (isCheckBox) {
 /*   	JCheckBox checkBox = new JCheckBox();
      checkBox.setFont(m_GridFont);
      tableColumn.setCellRenderer(OverlordTableColumn.createCellRenderer(checkBox));
      tableColumn.setCellEditor(OverlordTableColumn.createCellEditor(checkBox));
      */
    }
    else {
    	JLabel label = new JLabel();
    	JTextField textField = new JTextField("");
      label.setFont(m_GridFont);
      textField.setFont(m_GridFont);
      tableColumn.setCellRenderer(OverlordTableColumn.createCellRenderer(label));
      TableCellEditor cellEditor = OverlordTableColumn.createCellEditor(textField);
      if (cellEditor instanceof DefaultCellEditor)
      	((DefaultCellEditor)cellEditor).setClickCountToStart(1);
      tableColumn.setCellEditor(cellEditor);
    }
    repaint();
  }


  /**{ method
      @name isCellModified
      @param Row - row
      @param Col  - col
      @return - true the user has modified the cell or the cell is
          readonly // ??? Why ???
      @function - tests is a cell is modified by the user
  }*/
  public boolean isCellModified(int c,int Row) {
    Assertion.fixThis(); // !!! not implemented
    return false;
  }

	/* End of interface OverlordGridAgent */

  protected void createTables()
  {
     m_Tables = new SubTable[2];
    // clearTables();
     if(m_NumberFixedColumns > 0) {
        createFixedTable(m_NumberFixedColumns);
        createScrollableTable(m_NumberFixedColumns);
     }
     else {
        createScrollableTable(0);
     }
  }
  
  protected void clearTables()
  {
     if(m_Tables == null)
        return;
     switch(m_Tables.length) {
        case 2:
            this.setRowHeader(null);
            this.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, null);
        case 1:
            this.getViewport().setView(null);
        	this.setColumnHeaderView(null);
            break;
        default:
            Assertion.fatalError();
     }
     m_Tables = null;
  }
  
  protected SubTable createFixedTable(int n) {
  	m_Tables[0] = new FixedTable(this,0,n);
    reapplyProperties();
    m_Tables[0].setForeground(m_FixedForeground);
    m_Tables[0].setBackground(m_FixedBackground);   

	this.setRowHeaderView(m_Tables[0]);
    this.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, m_Tables[0].getTableHeader());
      JTableHeader header = m_Tables[0].getTableHeader();
   //   header.setSize(header.getPreferredSize());
      invalidate();
      validate();
    return(m_Tables[0]);
  }

  protected SubTable createScrollableTable(int offset) {
    if(m_Tables == null) {
        if(offset == 0)
            m_Tables = new SubTable[1];
        else
            m_Tables = new SubTable[2]; // make room for fixed table
    }
    JViewport myPort = getViewport();
    if(myPort != null &&  myPort.getView() != null && 
            myPort.getView() == m_Tables[1]) {
        OffsetTableModel TheModel = (OffsetTableModel)m_Tables[1].getModel();
        TheModel.setStartCol(offset);
        return(m_Tables[1]);
    }
    
  	m_Tables[1] = new ScrollableTable(this,offset,-1);
    reapplyProperties();

    if(myPort != null)
        getViewport().setView(m_Tables[1]);
    else
        this.setViewportView(m_Tables[1]);
	this.setColumnHeaderView(m_Tables[1].getTableHeader());
    return(m_Tables[1]);
  }


  protected void reapplyProperties() {
    setHeaderFont(m_HeaderFont);
    setAutoWidth(m_AutoWidth);
    setBackground(getBackground());
    setForeground(getForeground());
    setSelectionModel(this);
   // m_ColumnModel.setColumnSelectionAllowed(true); // temp .. debug
  }

	/**
	 * Return the component used to render the cells of the given column.
	 */
	protected Component getColumnComponent(int c) {
		TableCellRenderer tableCellRenderer =
			m_ColumnModel.getColumn(c).getCellRenderer();
		if (tableCellRenderer instanceof DefaultTableCellRenderer) {
			return ((DefaultTableCellRenderer)tableCellRenderer);
		}
		return null;
	}




    /**{ method
       // debugging override
    }*/
    public  void paint(Graphics g) {
        super.paint(g);
    }


    /**{ method
       // debugging override
    }*/
    public void setBounds(int x,int y,int width,int height) {
        if(width > 0 && height > 0)
           Assertion.doNada();
        super.setBounds(x,y,width,height);
    }
    // debugging override
    public  void setLayout(LayoutManager m) {
        super.setLayout(m);
    }

    // debugging override
    public  void doLayout() {
        try {
            super.doLayout();
        }
        catch(NullPointerException ex) {
            
        }
    }
    

// end class OverlordGrid
}


