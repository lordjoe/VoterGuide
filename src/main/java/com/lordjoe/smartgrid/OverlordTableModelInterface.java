/* 
    file OverlordTableModelInterface.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/


package com.lordjoe.smartgrid;
import java.awt.*;
import javax.swing.table.*;


public interface OverlordTableModelInterface extends TableModel /*  TreeModel */
{
    public void setColumnName(int column,String name);
    // Tree Methods
    public boolean isTree();
  /*  public boolean isCollapsed(int rowIndex);
    public void    setCollapsed(int rowIndex,boolean doit);
    public boolean isLeaf(int rowIndex);
    public int getIndent(int rowIndex);
    public int getMaxIndent();
    */
    public boolean isColumnSelectionSupported();
    public void setColumnSelectionSupported(boolean b);
    
    // Row Headers
    public boolean isUsingRowHeaders();
    public void setUsingRowHeaders(boolean doit);
    
    // Sort Methods
    public int getSortColumn();
    public void setSortColumn(int col); 
    public boolean isColumnSortSupported();
    public void    setColumnSortSupported(boolean b);
    
    // Color Methods
    public Color getForeColor(int rowIndex, int columnIndex);
    public Color getBackColor(int rowIndex, int columnIndex);
    
    public boolean isHeader3D();
    public Color getHeaderBackground();
    public Color getHeaderForeground();
    
    public Color getBackground();
    public Color getForeground();

    
    // Special mode to stripe data - may only be used by LCJ
    public void setLabReport(boolean doit);
    public boolean isLabReport();

  }
