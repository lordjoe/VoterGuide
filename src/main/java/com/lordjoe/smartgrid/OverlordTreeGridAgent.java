/**{ file
    @name OverlordGridAgent.java
    @function <Add Comment Here>
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************

    @date> Wed May 28 17:44:44  1997
    @version> 1.0
}*/
package com.lordjoe.smartgrid;

/**{ interface
    @name OverlordGridAgent
    @function <Add Comment Here>
}*/
public interface OverlordTreeGridAgent extends OverlordGridAgent{

    /**
        pass in an array which sets the indent level for all rows in the
        grid. The length of indents must be >= the number of rows
        indentation must be 0 for the first row
        for subsequent rows indentation at row n  must <= indentation
        of row n - 1  + 1 - i.e. you can be a child of the previous
        row at level n + 1, a sibling at level n or unrelated at < n

        @param indents - array of indents where 0 is a top level node  and
         each child is one more than its parent
    */
    public void setAllIndentation(int[]  indents);

    /* ========================================================
        These are not needed in the public interface for the
        viewer but may be used by other applications 
        i.e. they are not needed
    */
  //  public boolean isTree();

  //  public void setTree(boolean doit);


    // sets the indenataion level of a row
    // indentation must be 0 for the first row
    // for subsequent rows indentation at row n  must <= indentation
    // of row n - 1  + 1 - i.e. you can be a child of the previous
    // row at level n + 1, a sibling at level n or unrelated at < n
//	public void setRowIndentation(int row,int indent);

    // return a row's indent level ( default is 0)
//	public int getRowIndentation(int row);

    // return true if a row has no children
//	public boolean isLeaf(int row);

    // hide all children of the given row (doCollapse = true)- this
    // causes all childrows and all their descendents to be hidden
    // doCollapse = false shows all children but not their descendents
    // if the row is a leaf ( no children) this has no effect.
//	public void setCollapsed(int row, boolean doCollapse);

    // return whether a row is in a collapsed state
//	public boolean isCollapsed(int row);

    // this calls setCollapse(n,false) for all descendents of row
    // causing all items in that level of the tree to show
//	public void fullyExpand(int row);

//- *******************
//- End Class OverlordTreeGridAgent
}
