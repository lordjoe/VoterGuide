/*
 * @(#)OverlordListSelectionModel.java	1.28 97/12/16
 *
 * Specialized selection model for working with OverlordGrids
 *
 */

package com.lordjoe.smartgrid;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import com.lordjoe.utilities.*;


/**
 * @version 1.28 12/16/97
 * @author Hans Muller
 * @see ListSelectionModel
 */

public class OverlordListSelectionModel implements ListSelectionModel
{
    private OverlordGrid m_Grid;
    private int selectionMode = MULTIPLE_INTERVAL_SELECTION;
    private int minIndex = -1;
    private int maxIndex = -1;
    private int anchorIndex = -1;
    private int leadIndex = -1;
    private int firstChangedIndex = -1;
    private int lastChangedIndex = -1;
    private boolean isAdjusting = false;

    protected BitSet value = new BitSet(32);
    protected EventListenerList listenerList = new EventListenerList();

    protected boolean leadAnchorNotificationEnabled = true;
    
    public OverlordListSelectionModel(OverlordGrid thegrid)
    {
        OverlordGrid m_Grid = thegrid;
    }
    
    public int getAnchorSelectionIndex() { return anchorIndex; }
    public int getLeadSelectionIndex() { return leadIndex; }

    public int getMinSelectionIndex() { return minIndex; }
    public int getMaxSelectionIndex() { return maxIndex; }

    public boolean getValueIsAdjusting() { return isAdjusting; }


    public int getSelectionMode() { return selectionMode; }

    public void setSelectionMode(int selectionMode) {
	switch (selectionMode) {
	case SINGLE_SELECTION:
	case SINGLE_INTERVAL_SELECTION:
	case MULTIPLE_INTERVAL_SELECTION:
	    this.selectionMode = selectionMode;
	    break;
	default:
	    throw new IllegalArgumentException("invalid selectionMode");
	}
    }


    public boolean isSelectedIndex(int index) {
	    return ((index == -1) || (index < minIndex) || (index > maxIndex)) ? false : value.get(index);
    }

    public boolean isSelectionEmpty() {
	    return (minIndex == -1) && (maxIndex == -1);
    }

    public void addListSelectionListener(ListSelectionListener l) {
 	    listenerList.add(ListSelectionListener.class, l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
 	    listenerList.remove(ListSelectionListener.class, l);
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
     * @param firstIndex The first index in the interval.
     * @param index1 The last index in the interval.
     * @param isAdjusting True if this is the final change in a series of them.
     * @see EventListenerList
     */
    protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting)
    {
    	Object[] listeners = listenerList.getListenerList();
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


    public void clearSelection()
    {
    	if ((minIndex == -1) || (maxIndex == -1)) {
    	    return;
    	}

    	boolean selectionChanged = false;

    	for(int r = minIndex; r <= maxIndex; r++) {
    	    if (value.get(r)) {
    		selectionChanged = true;
    	    }
    	    value.clear(r);
    	}

    	minIndex = maxIndex = anchorIndex = leadIndex = -1;

    	if (selectionChanged) {
    	    fireValueChanged(minIndex, maxIndex);
    	}
    }

    /**
     * Sets the value of the leadAnchorNotificationEnabled flag.
     * @see		#isLeadAnchorNotificationEnabled()
     */
    public void setLeadAnchorNotificationEnabled(boolean flag) {
        leadAnchorNotificationEnabled = flag;
    }

    /**
     * Returns the value of the leadAnchorNotificationEnabled flag.
     * When leadAnchorNotificationEnabled is true the model
     * generates notification events with bounds that cover all the changes to
     * the selection plus the changes to the lead and anchor indices.
     * Setting the flag to false causes a norrowing of the event's bounds to
     * include only the elements that have been selected or deselected since
     * the last change. Either way, the model continues to maintain the lead
     * and anchor variables internally. The default is true.
     * @return 		the value of the leadAnchorNotificationEnabled flag
     * @see		#setLeadAnchorNotificationEnabled(boolean)
     */
    /* The JTable uses this to optimise row selection on mouse dragged.
     * Setting the flag to false causes the repainting code to only redraw
     * those rows that have been selected (or deselected) since the last event.
     */
    public boolean isLeadAnchorNotificationEnabled() {
        return leadAnchorNotificationEnabled;
    }

    /* If the lead or anchor indices have changed, add them to
     * the firstChanged/lastChanged interval.
     */
    private void updateLeadAnchorIndices(int index0, int index1)
    {
        if (leadAnchorNotificationEnabled) {
            if ((anchorIndex != -1) && (anchorIndex != index0)) {
                int minAnchorIndex = Math.min(anchorIndex, index0);
                int maxAnchorIndex = Math.max(anchorIndex, index0);

        	firstChangedIndex = (firstChangedIndex == -1)
        	    ? maxAnchorIndex
                    : Math.min(minAnchorIndex, firstChangedIndex);
        	lastChangedIndex = Math.max(maxAnchorIndex, lastChangedIndex);
            }

            if ((leadIndex != -1) && (leadIndex != index1)) {
        	int minLeadIndex = Math.min(leadIndex, index1);
        	int maxLeadIndex = Math.max(leadIndex, index1);

        	firstChangedIndex = (firstChangedIndex == -1)
        	    ? minLeadIndex
                    : Math.min(minLeadIndex, firstChangedIndex);
        	lastChangedIndex = Math.max(maxLeadIndex, lastChangedIndex);
            }
        }

    	anchorIndex = index0;
    	leadIndex = index1;
    }


    public void setSelectionInterval(int index0, int index1)
    {
    	if (getSelectionMode() == SINGLE_SELECTION) {
    	    index0 = index1;
    	}

    	int newMinIndex, newMaxIndex;

    	/* Special case, clear selection.
    	 */

    	if ((index0 == -1) && (index1 == -1)) {
    	    clearSelection();
    	    return;
    	}

    	newMinIndex = Math.min(index0, index1);
    	newMaxIndex = Math.max(index0, index1);

	/* Union newMinIndex,newMaxIndex with minIndex,maxIndex.
	 */

	if ((minIndex == -1) || (newMinIndex < minIndex)) {
	    minIndex = newMinIndex;
	}

	if ((maxIndex == -1) || (newMaxIndex > maxIndex)) {
	    maxIndex = newMaxIndex;
	}

	/* Update the selection value bitset and keep track of the
	 * first and last indices that were actually changed.
	 */

	firstChangedIndex = -1;
	lastChangedIndex = -1;

	for(int r = minIndex; r < newMinIndex; r++) {
	    if (value.get(r)) {
		if (firstChangedIndex == -1) {
		    firstChangedIndex = lastChangedIndex = r;
		}
		else if (r > lastChangedIndex) {
		    lastChangedIndex = r;
		}
	    }
	    value.clear(r);
	}

	for(int r = newMinIndex; r <= newMaxIndex; r++) {

	    if (!value.get(r)) {
		if (firstChangedIndex == -1) {
		    firstChangedIndex = lastChangedIndex = r;
		}
		else if (r > lastChangedIndex) {
		    lastChangedIndex = r;
		}
	    }
	    value.set(r);
	}

	for(int r = newMaxIndex + 1; r <= maxIndex; r++) {
	    if (value.get(r)) {
		if (firstChangedIndex == -1) {
		    firstChangedIndex = lastChangedIndex = r;
		}
		else if (r > lastChangedIndex) {
		    lastChangedIndex = r;
		}
	    }
	    value.clear(r);
	}

	updateLeadAnchorIndices(index0, index1);

	minIndex = newMinIndex;
	maxIndex = newMaxIndex;

	if ((firstChangedIndex != -1) && (lastChangedIndex != -1)) {
	    fireValueChanged(firstChangedIndex, lastChangedIndex);
	}
    }


    public void addSelectionInterval(int index0, int index1)
    {
	int mode = getSelectionMode();
	if ((mode == SINGLE_SELECTION) || (mode == SINGLE_INTERVAL_SELECTION)) {
	    setSelectionInterval(index0, index1);
	}

	if ((index0 == -1) && (index1 == -1)) {
	    return;
	}

	int addMinIndex = Math.min(index0, index1);
	int addMaxIndex = Math.max(index0, index1);

	/* Update minIndex, maxIndex.
	 */

	if ((minIndex == -1) || (addMinIndex  < minIndex)) {
	    minIndex = addMinIndex;
	}

	if ((maxIndex == -1) || (addMaxIndex > maxIndex)) {
	    maxIndex = addMaxIndex;
	}

	/* Update the selection value bitset, and keep track of the
	 * first and last indices that were actually changed.
	 */

	firstChangedIndex = -1;
	lastChangedIndex = -1;

	for(int r = addMinIndex; r <= addMaxIndex; r++) {
	    if (!value.get(r)) {
		if (firstChangedIndex == -1) {
		    firstChangedIndex = lastChangedIndex = r;
		}
		else if (r > lastChangedIndex) {
		    lastChangedIndex = r;
		}
		value.set(r);
	    }
	}

	updateLeadAnchorIndices(index0, index1);

	if ((firstChangedIndex != -1) && (lastChangedIndex != -1)) {
	    fireValueChanged(firstChangedIndex, lastChangedIndex);
	}

    }


    public void removeSelectionInterval(int index0, int index1)
    {
	/* No-op special case, or selection is already empty.
	 */

	if ((index0 == -1) && (index1 == -1)) {
	    return;
	}

	/* Current selection is empty
	 */

	if ((minIndex == -1) && (maxIndex == -1)) {
	    return;
	}

	/* Update the selection value bitset, and keep track of the
	 * first and last indices that were actually changed.
	 */

	int remMinIndex = Math.min(index0, index1);
	int remMaxIndex = Math.max(index0, index1);

	int firstChangedIndex = -1;
	int lastChangedIndex = -1;

	for(int r = remMinIndex; r <= remMaxIndex; r++) {
	    if (value.get(r)) {
		if (firstChangedIndex == -1) {
		    firstChangedIndex = lastChangedIndex = r;
		}
		else if (r > lastChangedIndex) {
		    lastChangedIndex = r;
		}
		value.clear(r);
	    }
	}

	/* Find the new min and max index values.
	 */

	int newMinIndex = -1;
	int newMaxIndex = -1;

	for(int r = minIndex; r < maxIndex; r++) {
	    if (value.get(r)) {
		if (newMinIndex == -1) {
		    newMinIndex = newMaxIndex = r;
		}
		else if (r > newMaxIndex) {
		    newMaxIndex = r;
		}
	    }
	}

	if ((firstChangedIndex != -1) && (lastChangedIndex != -1)) {
	    fireValueChanged(firstChangedIndex, lastChangedIndex);
	}
    }


    /**
     * Insert length indices beginning before/after index.  This is typically
     * called to sync the selection model with a corresponding change
     * in the data model.
     */

    public void insertIndexInterval(int index, int length, boolean before)
    {
	/* The first new index will appear at insMinIndex and the last
	 * one will appear at insMaxIndex
	 */
	int insMinIndex = (before) ? Math.max(0, index - 1) : index + 1;
	int insMaxIndex = (insMinIndex + length) - 1;

	/* If both of the indices next to the insertion point were
	 * selected then select all of the new indices.  Special case:
	 * if you're inserting before index 0 then new indices will
	 * be selected if the index 0 is selected.
	 */
	boolean selected = value.get(index) && value.get(insMinIndex);

	/* Right shift the entire bitset by length, beginning with
	 * index-1 if before is true, index+1 if it's false (i.e. with
	 * insMinIndex).
	 */
	for(int i = maxIndex; i >= insMinIndex; i--) {
	    if (value.get(i)) {
		value.set(i + length);
	    }
	    else {
		value.clear(i + length);
	    }
	}

	/* Initialize the newly inserted indices.
	 */
	for(int i = insMinIndex; i <= insMaxIndex; i++) {
	    if (selected) {
		value.set(i);
	    }
	    else {
		value.clear(i);
	    }
	}

	if (minIndex != -1 && (minIndex > insMinIndex || (minIndex == insMinIndex && !selected))) {
	    minIndex += length;
	}

	if (maxIndex != -1 && maxIndex >= insMinIndex) {
	    maxIndex += length;
	}

	fireValueChanged(insMinIndex, maxIndex);
    }


    /**
     * Remove the indices in the interval index0,index1 (inclusive) from
     * the selection model.  This is typically called to sync the selection
     * model width a corresponding change in the data model.  Note
     * that (as always) index0 need not be <= index1.
     */
    public void removeIndexInterval(int index0, int index1)
    {
	/* Special case - empty interval.
	 */
	if ((index0 < 0) || (index1 < 0)) {
	    return;
	}

	/* Special case - nothing's selected.
	 */
	if ((minIndex == -1) && (maxIndex == -1)) {
	    return;
	}

	int rmMinIndex = Math.min(index0, index1);
	int rmMaxIndex = Math.max(index0, index1);
	int gapLength = (rmMaxIndex - rmMinIndex) + 1;

	/* Special case - we're not removing any selected indices.
	 */
	if ((rmMinIndex > maxIndex) || (rmMaxIndex < minIndex)) {
	    return;
	}

	/* Special case - we're removing all of the selected indices.
	 */
	if ((rmMinIndex <= minIndex) && (rmMaxIndex >= maxIndex)) {
	    clearSelection();
	    return;
	}

	/* Shift the entire bitset to the left to close the index0, index1
	 * gap. Clear the bits to the right of the old maxIndex.
	 */
	for(int i = rmMinIndex; i <= rmMaxIndex; i++) {
	    if (value.get(i + gapLength)) {
		value.set(i);
	    }
	    else {
		value.clear(i);
	    }
	}

	for(int i = maxIndex; i > maxIndex - gapLength; i--) {
	    value.clear(i);
	}

	/* Compute the new min and max selection indices.
	 */

	int oldMinIndex = minIndex;
	int oldMaxIndex = maxIndex;

	if (oldMinIndex >= rmMinIndex) {
	    minIndex = -1;
	    for(int i = rmMinIndex; i <= oldMaxIndex; i++) {
		if (value.get(i)) {
		    minIndex = i;
		    break;
		}
	    }
	}

	if (minIndex == -1) {
	    maxIndex = -1;
	}
	else if (oldMaxIndex > rmMaxIndex) {
	    maxIndex = oldMaxIndex - gapLength;
	}
	else {
	    for(int i = oldMaxIndex; i >= minIndex; i--) {
		if (value.get(i)) {
		    maxIndex = i;
		    break;
		}
	    }
	}

	if ((maxIndex == -1) && (minIndex == -1)) {
	    fireValueChanged(rmMinIndex, rmMaxIndex);
	}
	else {
	    firstChangedIndex = Math.min(rmMinIndex, minIndex);
	    lastChangedIndex = Math.max(rmMaxIndex, maxIndex);
	    fireValueChanged(firstChangedIndex, lastChangedIndex);
	}
    }


    public void setValueIsAdjusting(boolean b) {
    	if (b != this.isAdjusting) {
    	    this.isAdjusting = b;
    	    this.fireValueChanged(b);
    	}
    }


    public String toString() {
    	String s =  ((getValueIsAdjusting()) ? "~" : "=") + value.toString();
    	return getClass().getName() + " " + Integer.toString(hashCode()) + " " + s;
    }

    /**
     * Returns a clone of the reciever with the same selection.
     * listenerLists are not duplicated.
     *
     * @exception CloneNotSupportedException if the receiver does not
     *    both (a) implement the Cloneable interface and (b) define a
     *    <code>clone</code> method.
     */
    public Object clone() throws CloneNotSupportedException {
    	OverlordListSelectionModel clone = (OverlordListSelectionModel)super.clone();
    	clone.value = (BitSet)value.clone();
    	clone.listenerList = new EventListenerList();
    	return clone;
    }
    
   public void setAnchorSelectionIndex(int ignore) {
        Assertion.fixThis();
   }
   
   public void setLeadSelectionIndex(int ignore)
   {
        Assertion.fixThis();
   }
 }

