/* 
    file TreeTableCellRenderer.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/
package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
    //
    // The renderer used to display the tree nodes, a JTree.
    //

public class TreeTableCellRenderer extends JTree implements TableCellRenderer
{
    private  OverlordGrid m_Table;
	private int visibleRow;
    private Color m_OverrideBackground;
    private Color m_OverrideForeground;
    private boolean m_IsSelected;

	public TreeTableCellRenderer(OverlordGrid aTable) {
	    super();
	    setRootVisible(false);
	    m_Table = aTable;
	    setCellRenderer(new RealTableTreeCellRenderer());
	}
	
	public TreeTableCellRenderer(OverlordGrid aTable,TreeModel model) {
	    super(model);
	   // setRootVisible(false);
	    m_Table = aTable;
	}
	
	public Dimension getPreferredSize() {
	    Dimension ret = super.getPreferredSize();
	    return(ret);
	}
	
    /**
      * Sets the color to use for the background if node is m_selected.
      */
    public Color getBackgroundSelectionColor() {
     //   if(m_OverrideBackground == null)
			return UIManager.getColor("Tree.selectionBackground");
	 //   return(m_OverrideBackground);
	  //  return(DisplayUtil.toSelectionBackground(m_OverrideBackground));
    }

    /**
      * Returns the background color to be used for non m_selected nodes.
      */
    public Color getBackgroundNonSelectionColor() {
        if(m_OverrideBackground == null)
			return getBackground();
    	return(m_OverrideBackground);
     }

	public void setBounds(int x, int y, int w, int h) {
	    // unlike sun sample Table geight does not work - use this form
	    int TestTableHeight = m_Table.getTotalRowHeight() * m_Table.getRowCount();
	    super.setBounds(x, 0, w, TestTableHeight);
	}

	public void paint(Graphics g) {
	    Rectangle r = getRowBounds(visibleRow);
	    if(r != null)
	        g.translate(0, -r.y);
	    else 
	        Assertion.doNada();
	        
	    if(m_IsSelected) {
	        Assertion.doNada();
	    }
	    super.paint(g);
	}

	public Component getTableCellRendererComponent(JTable table,
						       Object value,
						       boolean isSelected,
						       boolean hasFocus,
						       int row, int column)
	{
	  //  visibleRow =  m_Table.getRealRow(row);
	    visibleRow =  row;
	    m_IsSelected = isSelected;
	        
						     
	      // Default to the tables foreground

      OverlordTableModelInterface TheModel =
            (OverlordTableModelInterface)table.getModel();
      // The model can override color choices
      m_OverrideBackground = TheModel.getBackColor(row,column);
      if(m_OverrideBackground == null)
        m_OverrideBackground = table.getBackground();
        

      m_OverrideForeground = TheModel.getForeColor(row,column);
      if(m_OverrideForeground == null)
        m_OverrideForeground = table.getForeground();
        

      if(isSelected) {
           m_OverrideBackground = getBackgroundSelectionColor();
           m_OverrideForeground = UIManager.getColor("Tree.selectionForeground");
      }
		
      setForeground(m_OverrideForeground);
	  setBackground(m_OverrideBackground);

   /*     
        TreePath ThePath =	getPathForRow(row); 
        if(ThePath != null) {
               MutableTreeNode last = (MutableTreeNode)ThePath.getLastPathComponent(); 
               last.setUserObject(value);
        }
        */
       DefaultTreeCellRenderer TheRenderer = (DefaultTreeCellRenderer)getCellRenderer();
            
        if(isSelected) {
            // Note the Table thinks the row is selected - not the renderer so 
            // need to se tthe renderers nonSelected colors
            TheRenderer.setBackgroundSelectionColor(m_OverrideBackground);
            TheRenderer.setBackgroundNonSelectionColor(m_OverrideBackground);
        }
        else {
            TheRenderer.setBackgroundNonSelectionColor(m_OverrideBackground);
        }
      
	    return this;

	}
	
	public void setModel(TreeModel n) {
	    super.setModel(n);
	 //   SwingHelpers.setFullyExpanded(this);
	 //   TreeNode root = (TreeNode)n.getRoot();
	 //   writeTree(System.out,root);
	}
	
	public static void writeTree(PrintStream out,TreeNode root) {
	    writeTree(out,root,0);
	}
	
	public static void writeTree(PrintStream out,TreeNode node,int indent) {
	    for(int i = 0; i < indent; i++)
	        out.print("    ");
	    Object o = ((DefaultMutableTreeNode)node).getUserObject();
	    if(o != null)
	        out.println(o);
	    else 
	        out.println("<null>");
	    for(int i = 0; i < node.getChildCount(); i++)
	        writeTree(out,node.getChildAt(i),indent + 1);
	        
	}
	
	public void setLeafIcon(Icon in) {
	    if(getCellRenderer() instanceof DefaultTreeCellRenderer) {
	        ((DefaultTreeCellRenderer)getCellRenderer()).setLeafIcon(in);
	    }
	}
	public void setClosedIcon(Icon in) {
	    if(getCellRenderer() instanceof DefaultTreeCellRenderer) {
	        ((DefaultTreeCellRenderer)getCellRenderer()).setClosedIcon(in);
	    }
	}
	public void setOpenIcon(Icon in) {
	    if(getCellRenderer() instanceof DefaultTreeCellRenderer) {
	        ((DefaultTreeCellRenderer)getCellRenderer()).setOpenIcon(in);
	    }
	}
	
	public class RealTableTreeCellRenderer extends DefaultTreeCellRenderer {
	    public RealTableTreeCellRenderer() {
	    }
	    
	    // make sure we have the same height as the table row
	    public Dimension getPreferredSize() {
	        Dimension ret = super.getPreferredSize();
	        ret.height = m_Table.getRowHeight();
	        return(ret);
	    }
	}

// end Class
}


