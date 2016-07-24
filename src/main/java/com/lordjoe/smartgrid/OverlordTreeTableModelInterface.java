/* 
    file OverlordTreeTableModelInterface.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

/*
    Added methods promised by a TreeTableModel
*/
public interface OverlordTreeTableModelInterface extends OverlordTableModelInterface 
{
    public boolean isCollapsed(int rowIndex);
    public void    setCollapsed(int rowIndex,boolean doit);
    public boolean isLeaf(int rowIndex);
    public int getIndent(int rowIndex);
    public int getMaxIndent();
 }
