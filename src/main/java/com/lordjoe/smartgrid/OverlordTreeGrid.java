/* 
    file OverlordTreeGrid.java
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
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;


public class OverlordTreeGrid extends OverlordGrid implements
    OverlordTreeGridAgent,OverlordTreeTableModelInterface
{
    private TreeTableCellRenderer m_TreeRenderer; // renderer when grid is a tree
    private int[]              m_RowIndents;
    private TreeModel          m_RowTree;
    private int                m_MaxIndent;
    private MutableTreeNode[]  m_RowNodes;
    private TreePath[]         m_RowPaths;
    private boolean[]          m_RowIsLeaf;
    private boolean[]          m_RowIsCollapsed;
    private boolean            m_IsTree;

    // pure test code
     private JFrame m_TestTreeFrame;
     private JTree  m_TestTree;
     public static final boolean SHOW_TEST_TREE = false; // true;


    public OverlordTreeGrid() {
        super();
        /*  Code to help me debug behavior */
        if(SHOW_TEST_TREE) {
            m_TestTreeFrame = new JFrame("Test Tree Frame");
            m_TestTree = new JTree();
            m_TestTreeFrame.getContentPane().add(new JScrollPane(m_TestTree));
        }
    }


 public boolean isFirstColumnSelectable(int row)
 {
    boolean ret = super.isFirstColumnSelectable(row);
    if(isTree())
        return(!isLeaf(row));
    return(ret);
 }

    public int[]      getRowIndents()      { return(m_RowIndents); }

    public void       setRowIndents(int[] newIndents)      {
        m_RowIndents = newIndents;
        m_MaxIndent = 0;
        int MinIndent = Integer.MAX_VALUE;
        if(newIndents != null) {
            for(int i = 0; i < m_RowIndents.length; i++)
                m_MaxIndent = Math.max(m_MaxIndent,m_RowIndents[i]);
            setTree(true);
            buildTreeModel();
        }
        else {
            setTree(false);
        }
    }

    protected void buildTreeModel() {
        DefaultMutableTreeNode root    = new DefaultMutableTreeNode();
        DefaultMutableTreeNode current = root;
        DefaultMutableTreeNode next    = root;
        m_RowNodes = new MutableTreeNode[m_ShownToRealMap.length];
        int currentLevel = -1;
        Object[][] AllData = getData();
        for(int k = 0; k < AllData.length; k++) {
            Object data = AllData[k][0];
            if(m_RowIndents[k] <= 0) {
                current = new DefaultMutableTreeNode(data);
                m_RowNodes[k] = current;
                currentLevel = 0;
                root.add(current);
            }
            else {
                while(m_RowIndents[k] <= currentLevel) {
                    if(current.getParent() != null) {
                        current = (DefaultMutableTreeNode)current.getParent();
                        currentLevel--;
                    }
                }
                if(currentLevel < 0) {
                    current = new DefaultMutableTreeNode(data);
                    m_RowNodes[k] = current;
                    currentLevel = 0;
                    root.add(current);
                }
                else {
                    next = new DefaultMutableTreeNode(data);
                    m_RowNodes[k] = next;
                    current.add(next);
                    currentLevel++;
                    current = next;
                }
            }
        }
//        m_RowTree = new DefaultTreeModel(root);
        m_RowTree = new DefaultTreeModel(root);
        if(m_TreeRenderer != null) {
            m_TreeRenderer.setModel(m_RowTree);
           /* Code to aid in debugging */
           if(SHOW_TEST_TREE) {
                m_TestTree.setModel(m_RowTree); // debug code
                m_TestTreeFrame.setVisible(true);
                m_TestTreeFrame.setSize(300,300);
           }
        }

        TreePath[] allPaths = SwingHelpers.getTreePaths(m_RowTree);
        // drop invisible root
        m_RowPaths = new TreePath[allPaths.length - 1];
        System.arraycopy(allPaths,1,m_RowPaths,0,allPaths.length - 1);

        for(int i = 0; i < m_RowIsCollapsed.length; i++) {
            m_TreeRenderer.expandPath(m_RowPaths[i]);
         //  Debugging code maintains a shadow tree
            if(SHOW_TEST_TREE)
                m_TestTree.expandPath(m_RowPaths[i]);
        }
        adjustTreeRenderer();
    }

    protected void adjustTreeRenderer() {
        if(m_TreeRenderer == null)
            return;
        // reach a uniform state
        // remember root not shown - does not count
    //    for(int i = 0; i < m_RowIsCollapsed.length  + 1; i++) {
        int NumberShown = 0;
        for(int i = 0; i < m_RowIsCollapsed.length; i++) {
            if(m_TreeRenderer.isExpanded(m_RowPaths[i])) {
                NumberShown++;
            }
            else {
                if(m_TreeRenderer.isCollapsed(m_RowPaths[i])) {
                    NumberShown++;
                }
            }

        }
 //       int NumberShown = 0;
        // remember root not shown - does not count
        for(int i = 0; i < m_RowIsCollapsed.length; i++) {
            if(m_RowIsCollapsed[i]) {
         //       m_TreeRenderer.collapseRow(i);
         //       m_TreeRenderer.collapsePath(m_RowPaths[i]);
         //       m_TestTree.collapsePath(m_RowPaths[i]);

            }
            else {
 //               NumberShown++;
   //             m_TreeRenderer.expandRow(i);
           //     m_TreeRenderer.expandPath(m_RowPaths[i]);
           //     m_TestTree.expandPath(m_RowPaths[i]);
            }
        }
        Dimension size = m_TreeRenderer.getSize();
        int TableSize = getRowHeight() * (NumberShown + 1);
        TableSize += getRowHeight() / 2; // fudge it
        size.height = TableSize;
        m_TreeRenderer.setSize(size );
    }




    public TreeModel getTree() { return(m_RowTree); }

    public int getMaxIndent() { return(m_MaxIndent); }

    public boolean isCollapsed(int rowIndex)
    {
        if(!isTree())
            return(false);
        int RealRow = getRealRow(rowIndex);
        return(m_RowIsCollapsed[RealRow]);
    }

    public void setCollapsed(int rowIndex,boolean doit)
    {
        if(!isTree())
            return;
       setRealRowCollapsed(getRealRow(rowIndex),doit);
    }

    protected void setRealRowCollapsed(int RealRow,boolean doit)
    {
       m_RowIsCollapsed[RealRow] = doit;
       if(m_RowIsLeaf[RealRow])
           return;
       int startCollapse = RealRow + 1;
       int endCollapse;
       if(doit) {
           m_TreeRenderer.collapsePath(m_RowPaths[RealRow]);
         //  Debugging code maintains a shadow tree
          if(SHOW_TEST_TREE)
                m_TestTree.collapsePath(m_RowPaths[RealRow]);

           for(endCollapse = startCollapse + 1; endCollapse < m_RowIndents.length; endCollapse++) {
                if(m_RowIndents[endCollapse] <= m_RowIndents[RealRow])
                    break;
           }
           endCollapse--;
           hideCollapsedRows(startCollapse,endCollapse);
       }
       else { // uncollapse
           m_TreeRenderer.expandPath(m_RowPaths[RealRow]);
         //  Debugging code maintains a shadow tree
          if(SHOW_TEST_TREE)
             m_TestTree.expandPath(m_RowPaths[RealRow]);

           for(endCollapse = RealRow + 1; endCollapse < m_RowIndents.length; endCollapse++) {
                if(m_RowIndents[endCollapse] <= m_RowIndents[RealRow])
                    break;
                if(m_RowIndents[endCollapse] == m_RowIndents[RealRow] + 1) {
                    m_RowIsShown[endCollapse] = true;
                    if(!m_RowIsLeaf[endCollapse])
                        setRealRowCollapsed(endCollapse,true);
                }
           }
           buildShownMap();
       }
       adjustTreeRenderer();
       fireTableDataChanged();
       invalidate();
    }

    protected void hideCollapsedRows(int startCollapse,int endCollapse)
    {
        hideRows(startCollapse,endCollapse);
    }

    public void fullyExpand(int rowIndex)
    {
        if(isLeaf(rowIndex))
            return;
        setCollapsed(rowIndex,false);
        int startCollapse = rowIndex + 1;
        int endCollapse;
        for(endCollapse = startCollapse + 1; endCollapse < m_RowIndents.length; endCollapse++) {
             if(m_RowIndents[endCollapse] <= m_RowIndents[rowIndex])
                    break;
             fullyExpand(endCollapse);
        }
    }

    public boolean isLeaf(int rowIndex)
    {
        if(!isTree())
            return(true);
        int RealRow = getRealRow(rowIndex);
        return(m_RowIsLeaf[RealRow]);
    }

    public int getIndent(int rowIndex)
    {
        if(!isTree())
            return(-1);
        int RealRow = getRealRow(rowIndex);
        return(m_RowIndents[RealRow]);
    }

    public int getRealIndent(int rowIndex)
    {
        if(!isTree())
            return(-1);
        return(m_RowIndents[rowIndex]);
    }

    public void setRealIndent(int rowIndex,int value)
    {
        if(!isTree())
            return;
        m_RowIndents[rowIndex] = value;
        m_MaxIndent = Math.max(m_MaxIndent,value);
    }

    public void setAllIndentation(int[] indent)
    {
        m_MaxIndent = 0;
        if(indent != null) {
            int NRows = getRealRowCount();
            Assertion.validate(indent.length >= NRows);
            setTree(true);
            for(int i = 0; i < m_RowIndents.length; i++) {
                m_RowIndents[i] = indent[i];
                m_MaxIndent = Math.max(m_MaxIndent,m_RowIndents[i]);
            }
            rebuildTree();
        }
        else { // if(indent != null)
            if(!isTree())
                return;
            setTree(false);
        }
        rebuildTable();
    }

    protected void rebuildTree()
    {
         buildTreeModel();
         for(int i = 0; i < m_RowIndents.length - 1; i++) {
            if(m_RowIndents[i] < m_RowIndents[i + 1])
                m_RowIsLeaf[i] = false;
            else
                m_RowIsLeaf[i] = true;
         }
         m_RowIsLeaf[m_RowIndents.length - 1] = true;

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

    public boolean isTree() { return(true); }

    public void setTree(boolean doit) {
        if(m_IsTree == doit)
            return;
        m_IsTree = doit;

          if (doit) {
          Assertion.validate(getColumnModel().getColumnCount() > 0);
          TableColumn column = getColumnModel().getColumn(0);
          if(m_TreeRenderer == null) {
                if(m_RowTree != null)
                    m_TreeRenderer = new TreeTableCellRenderer(this,m_RowTree);
                else
                    m_TreeRenderer = new TreeTableCellRenderer(this);
          }
          m_TreeRenderer.setFont(getGridFont());
          m_TreeRenderer.setBackground(getBackground());
          m_TreeRenderer.setForeground(getForeground());
          m_TreeRenderer.setLeafIcon(new OverlordLeafIcon());
          m_TreeRenderer.setOpenIcon(new OverlordOpenNodeIcon());
          m_TreeRenderer.setClosedIcon(new OverlordClosedNodeIcon());


          column.setCellRenderer(m_TreeRenderer);

          if(m_Tables != null) {
              for(int i = 0; i < m_Tables.length; i++) {
                 if(m_Tables[i] != null)
                    m_Tables[i].setRowHeight(20);
              }
          }

   //   	   updateTreeSize();
        }
        else {
          setColumnCheckBox(0, false);
          repaint();
        }
        repaint();
     }


    protected void internalSetDimensions(int rowCount, int columnCount)
    {
        int OldRowCount = getRealRowCount();
        boolean isMoot = (columnCount == getColumnCount() && rowCount == OldRowCount);
        if(isMoot)
                return; // null operation
        super.internalSetDimensions(rowCount,columnCount);
        if(m_Data != null) {
             // Adding a column only
              // changing rows + columns on existing data
                int copyRows = Math.min(rowCount,OldRowCount);

                m_NumberShownRows = rowCount;
                // update all row based data
                m_RowIndents     = new int[m_NumberShownRows];
                m_RowIsLeaf      = new boolean[m_NumberShownRows];
                m_RowIsCollapsed = new boolean[m_NumberShownRows];

                for(int i = 0; i < copyRows; i++) {
                    m_RowIsShown[i] = true;
                    m_RowIndents[i] = -1; // not intented
                    m_RowIsLeaf[i]       = true;
                    m_RowIsCollapsed[i]  = false;
                }
                if(copyRows < m_NumberShownRows) {
                    for(int i = copyRows; i < m_NumberShownRows; i++) {
                        m_RowIndents[i] = -1; // not intented
                        m_RowIsLeaf[i]       = true;
                        m_RowIsCollapsed[i]  = false;
                    }
                }
        } // if(m_Data != null)
        // no preexisting data
        else {
            m_NumberShownRows = rowCount;
            m_RowIndents     = new int[m_NumberShownRows];
            m_RowIsLeaf      = new boolean[m_NumberShownRows];
            m_RowIsCollapsed = new boolean[m_NumberShownRows];

            for(int i = 0; i < rowCount; i++) {
                m_RowIndents[i] = -1; // not indented
                m_Data[i] = new Object[columnCount];
                m_RowIsLeaf[i]       = true;
                m_RowIsCollapsed[i]  = false;
            }
        }
        doClearSelection();
        fireTableDataChanged();
    }

    public int getRowIndentation(int row) {
        return(getRealIndent(row));
    }

    public void setRowIndentation(int row,int indent) {
        setRealIndent(row,indent);
    }

    /**
     *  local Cell Double click handler - call only if
     *  not consumed
     */
    public void selfCellDoubleClicked(GridClickEvent ev)
    {
        if(ev.isConsumed())
            return;
        if(ev.getRow() == GridClickEvent.HEADER_ROW)
            return;
        if(ev.getCol() == 0) {
            int RealRow = ev.getRow();
            if(RealRow == 12)
                Assertion.doNada();
            boolean WasCollapsed = m_RowIsCollapsed[RealRow];
            setRealRowCollapsed(RealRow,!WasCollapsed);
            ev.consume();
        }
    }

}

abstract class OverlordTreeIcon implements Icon {
    public final int ICON_SIZE = 16;
    public final int ICON_SYMBOL_SIZE = 1;
    public int getIconHeight() { return(ICON_SIZE) ; }
    public int getIconWidth() { return(ICON_SIZE) ; }
}

class OverlordLeafIcon extends OverlordTreeIcon {
    public void paintIcon(Component c,Graphics g,int x,int y)
    {
        int inset = ICON_SIZE / 4;
        inset++;
        Color Old = g.getColor();
        g.setColor(Color.lightGray);
        g.draw3DRect(x + inset,y + inset,ICON_SIZE - 2 * inset,ICON_SIZE - 2 * inset,true);
        g.setColor(Old);
    }
}

class OverlordOpenNodeIcon extends OverlordTreeIcon {
    public void paintIcon(Component c,Graphics g,int x,int y)
    {
        int inset = ICON_SIZE / 5;
        inset -= inset % 2; // make size even
        Color Old = g.getColor();
        g.setColor(Color.lightGray);
        g.draw3DRect(x + inset,y + inset,ICON_SIZE - 2 * inset,ICON_SIZE - 2 * inset,true);

        g.setColor(new Color(128,0,0));

        g.draw3DRect(x + inset + 2,
            y + ICON_SIZE / 2 - ICON_SYMBOL_SIZE / 2  - 1,
            ICON_SIZE - 2 * inset - 4,
            ICON_SYMBOL_SIZE,
            false);

        g.setColor(Old);
    }
}

class OverlordClosedNodeIcon extends OverlordTreeIcon {
    public void paintIcon(Component c,Graphics g,int x,int y)
    {
        int inset = ICON_SIZE / 5;
        inset -= inset % 2;
        Color Old = g.getColor();
        g.setColor(Color.lightGray);
        g.draw3DRect(x + inset,y + inset,ICON_SIZE - 2 * inset,ICON_SIZE - 2 * inset,true);

        g.setColor(new Color(128,0,0));

        g.draw3DRect(x + inset + 2,
            y + ICON_SIZE / 2 - ICON_SYMBOL_SIZE / 2 ,
            ICON_SIZE - 2 * inset - 4,
            ICON_SYMBOL_SIZE,
            false);
         g.draw3DRect(x + ICON_SIZE / 2 - ICON_SYMBOL_SIZE / 2  ,
            y + inset + 2,
            ICON_SYMBOL_SIZE,
            ICON_SIZE - 2 * inset - 4,
            false);
  //     g.draw3DRect(x + inset,y + inset,ICON_SYMBOL_SIZE,ICON_SIZE - 2 * inset,false);

        g.setColor(Old);
    }
}

