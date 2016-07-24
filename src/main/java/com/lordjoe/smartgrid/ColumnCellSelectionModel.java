
package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColumnCellSelectionModel  implements  ListSelectionModel
{
    private OverlordGrid m_Grid;
    private int          m_Column;
    private boolean    m_SelectionEmpty = true;
    private int        m_SelectionMode = MULTIPLE_INTERVAL_SELECTION;
    private int        m_AnchorIndex;
    private int        m_LeadIndex;
    private boolean    m_IsAdjusting;
    protected EventListenerList m_SelectionListeners = new EventListenerList();

    public ColumnCellSelectionModel(OverlordGrid Grid,int Column)
    {
        m_Grid = Grid;
        m_Column = Column;
}

    public void clearSelection()
    {
        if(m_SelectionEmpty)
            return;
        boolean[] oldSelect = Util.clone(getSelections());
        doClearSelection();
        m_AnchorIndex = m_LeadIndex = -1;
        notifySelectionChanges(oldSelect);  
    }
 
    //
    // clear - do not notify
    // return says selection changed
    protected boolean doClearSelection()
    {
         if(m_SelectionEmpty)
            return(false);
       boolean[] CellSelections = getSelections();
       boolean selectionChanged = false;
         m_SelectionEmpty = true;
        for(int i = 0; i < CellSelections.length; i++) {
            if(CellSelections[i]) {
                selectionChanged = true;
                
                CellSelections[i] = false;
            }
        }
        return(selectionChanged);
    }
    
    public boolean isSelectedIndex(int index) {
        int realRow = m_Grid.getRealRow(index);
        if(realRow < 0)
            return(false);
	    return (getSelections()[realRow]);
    }
    
    
 
 
    public void setValueIsAdjusting(boolean b) {
    	if (b != m_IsAdjusting) {
    	    this.m_IsAdjusting = b;
    	    this.fireValueChanged(b);
    	}
    }
    public boolean getValueIsAdjusting() { return(m_IsAdjusting); }
    
    protected boolean[] getSelections() {
        return(m_Grid.getCellSelections(m_Column));
    }

     public void setSelectionInterval(int index0, int index1)
    {
        boolean[] oldSelect = Util.clone(getSelections());
        boolean selectionChanged = doClearSelection();
        doAddSelectionInterval(index0,index1);
    	notifySelectionChanges(oldSelect); // tell any selection changes
    }

   public void addSelectionInterval(int index0, int index1)
    {
        boolean[] oldSelect = Util.clone(getSelections());
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
        boolean[] CellSelections = getSelections();
        boolean selectionChanged = false;
        m_SelectionEmpty = true;
        for(int i = index0; i <= index1; i++) {
            int realRow = m_Grid.getRealRow(i);
            if(realRow >= 0) {
                if(!CellSelections[realRow]) {
                    selectionChanged = true;
                    CellSelections[realRow] = true;
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
        boolean[] CellSelections = getSelections();
        if(m_SelectionEmpty)
            return(-1);
        for(int i = 0; i < CellSelections.length; i++) {
            if(CellSelections[i])
                return(i);
        }
        Assertion.fatalError();
        return(-1);
    }
    
    public int getMaxSelectionIndex()
    {
        boolean[] CellSelections = getSelections();
       if(m_SelectionEmpty)
            return(-1);
        for(int i = CellSelections.length - 1; i >= 0; i--) {
            if(CellSelections[i])
                return(i);
        }
        Assertion.fatalError();
        return(-1);
    }
    
    public int getSelectionMode() { return(m_SelectionMode); }
 
   public void setAnchorSelectionIndex(int ignore) {
        Assertion.fixThis();
   }
   
   public void setLeadSelectionIndex(int ignore)
   {
        Assertion.fixThis();
   }

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
        boolean[] CellSelections = getSelections();
        boolean[] oldSelect = Util.clone(CellSelections);
        boolean selectionChanged = false;
        for(int i = index0; i <= index1; i++) {
            int realRow = m_Grid.getRealRow(i);
            if(realRow >= 0) {
                if(CellSelections[realRow]) {
                    selectionChanged = true;
                    CellSelections[realRow] = false;
                }
            }
        }
    	m_AnchorIndex = index0;
    	m_LeadIndex = index1;
    	notifySelectionChanges(oldSelect);  
    }
    protected void notifySelectionChanges(boolean[] oldSelect)
    {
        int i = 0;
        boolean inValueChanged = false;
        m_SelectionEmpty = true;
        int StartValueChanged = 0;
        boolean[] CellSelections = getSelections();
        int NVisible = m_Grid.getVisibleRowCount();
        
        while(i < NVisible) {
            int realRow = m_Grid.getRealRow(i);
            if(oldSelect[realRow] != CellSelections[realRow]) {
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
            if(CellSelections[realRow])
                m_SelectionEmpty = false;
            i++;
        }
        // finish last interval
        if(inValueChanged) {
            fireValueChanged(StartValueChanged, i - 1);
        }
    }

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
 	    m_SelectionListeners.add(ListSelectionListener.class, l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
 	    m_SelectionListeners.remove(ListSelectionListener.class, l);
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
    public void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting)
    {
    	Object[] listeners = m_SelectionListeners.getListenerList();
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

}
