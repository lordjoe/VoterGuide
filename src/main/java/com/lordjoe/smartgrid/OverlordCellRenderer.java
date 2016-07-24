/* 
    file OffsetTableModel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;


import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
 * @version 1.16 12/17/97
 * @author Hans Muller
 * @author Alan Chung
 * @see JTable
 */

public class OverlordCellRenderer extends DefaultTableCellRenderer {
//
// Constructors
//

 //   public OverlordCellRenderer(JTextArea x) {
 //      super(x);
 //   }
    public OverlordCellRenderer() {
       super();
    }

/*    public OverlordCellRenderer(JButton x) {
        super(x);
    }

    public OverlordCellRenderer(JCheckBox x) {
        super(x);
    }
*/
//
// Implementing TableCellRenderer
//

    public Component getTableCellRendererComponent(JTable table, Object value,
						   boolean isSelected,boolean hasFocus,
						   int row, int column) {
		    Component ret = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
		    // possibly reset background
            OverlordTableModelInterface realModel = (OverlordTableModelInterface)table.getModel();
            
 		    Color tableBack = realModel.getBackColor(row,column);
		    if(tableBack != null) {
		        if(isSelected)
		           if(tableBack.equals(Color.white)) {
		               tableBack = UIManager.getColor("TextField.selectionBackground");
		           }
		           else {
            	        tableBack = DisplayUtil.toSelectionBackground(tableBack);
            	   }
		            
		        ret.setBackground(tableBack);
		    }
		    if(column == 0 && realModel.isUsingRowHeaders()) {
		        ((JComponent)ret).setBorder(new BevelBorder(isSelected ? BevelBorder.LOWERED : BevelBorder.RAISED));
		    }
		    return(ret);
	 }
	
}


