
//
// This interface will be implemented by a subclass  of OverlordGrid which
// behaves like a tree.
//  It will use a custom component in the first column supporting
// 1) Indentation
// 2) An Icon representing either a leaf, an open node or a closed node
// 3) A name
// The presentation shoule be similar to the display of a single element
// of a tree

package com.lordjoe.smartgrid;

public interface OverlordGridTreeAgent extends OverlordGridAgent
{
	// sets the indenataion level of a row
	// indentation must be 0 for the first row
	// for subsequent rows indentation at row n  must <= indentation
	// of row n - 1  + 1 - i.e. you can be a child of the previous
	// row at level n + 1, a sibling at level n or unrelated at < n
	public void setRowIndentation(int row,int indent);

	// return a row's indent level ( default is 0)
	public int getRowIndentation(int row);

	// return true if a row has no children
	public boolean isLeaf(int row);

	// hide all children of the given row (doCollapse = true)- this
	// causes all childrows and all their descendents to be hidden
	// doCollapse = false shows all children but not their descendents
	// if the row is a leaf ( no children) this has no effect.
	public void setCollapsed(int row, boolean doCollapse);

	// return whether a row is in a collapsed state
	public boolean isCollapsed(int row);

	// this calls setCollapse(n,false) for all descendents of row
	// causing all items in that level of the tree to show
	public void fullyExpand(int row);

	// pass in an array which sets the indent level for all rows in the
	// grid. The length of indents must be >= the number of rows
	public void setAllIndentation(int[]  indents);


}