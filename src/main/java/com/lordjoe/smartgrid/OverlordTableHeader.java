
package com.lordjoe.smartgrid;

import javax.swing.*;
import javax.swing.table.*;

public class OverlordTableHeader extends JTableHeader
{
    static {
        UIDefaults defaults = UIManager.getDefaults();
        defaults.put("OverlordTableHeader","com.lordjoe.smartgrid.OverlordTableHeaderUI");
    }
    
    private SubTable m_SubTable; 

    public OverlordTableHeader() {
        super();
    }
    public OverlordTableHeader(TableColumnModel cm,SubTable tb) {
        super(cm);
        m_SubTable = tb;        
    }
    
    public SubTable getSubTable() { return(m_SubTable); }

    /**
     * @return "TableHeaderUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
	    return "OverlordTableHeader";
    }
}
