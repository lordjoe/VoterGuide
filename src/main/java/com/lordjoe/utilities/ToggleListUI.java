/**
 * JToggleList - create a list with a di
 */

package com.lordjoe.utilities;
import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class ToggleListUI extends BasicListUI
{
    /**
     * Returns a new instance of BasicListUI.  BasicListUI delegates are
     * allocated one per JList.
     *
     * @return A new ListUI implementation for the Windows look and feel.
     */
    public static ComponentUI createUI(JComponent list) {
        return new ToggleListUI();
    }
    
    //
    // *%$#@@!&&^%$ needed to expose list to inner class
    protected JList getList() { return(list); }
    
    protected int convertYToRow(int y0) {
        return(super.convertYToRow(y0));
    }

    /**
     * Creates a delegate that implements MouseInputListener.
     * The delegate is added to the corresponding java.awt.Component listener
     * lists at installUI() time. Subclasses can override this method to return
     * a custom MouseInputListener, e.g.
     * <pre>
     * class MyListUI extends BasicListUI {
     *    protected MouseInputListener <b>createMouseInputListener</b>() {
     *        return new MyMouseInputHandler();
     *    }
     *    public class MyMouseInputHandler extends MouseInputHandler {
     *        public void mouseMoved(MouseEvent e) {
     *            // do some extra work when the mouse moves
     *            super.mouseMoved(e);
     *        }
     *    }
     * }
     * Override creates a different mouse handler
     * </pre>
     *
     * @see MouseInputHandler
     * @see #installUI
     */
    protected MouseInputListener createMouseInputListener() {
        return new ToggleMouseInputHandler();
    }
    /**
     * Mouse input, and focus handling for JList.  An instance of this
     * class is added to the appropriate java.awt.Component lists
     * at installUI() time.  Note keyboard input is handled with JComponent
     * KeyboardActions, see installKeyboardActions().
     * <p>
     * Override in this class toggles selection on click
     *
     * @see #createMouseInputListener
     * @see #installKeyboardActions
     * @see #installUI
     */
    public class ToggleMouseInputHandler extends BasicListUI.MouseInputHandler
    {
        public void mousePressed(MouseEvent e)
        {
	    if (!SwingUtilities.isLeftMouseButton(e)) {
	        return;
	    }

	    if (!getList().isEnabled()) {
		return;
	    }

	    /* Request focus before updating the list selection.  This implies
	     * that the current focus owner will see a focusLost() event
	     * before the lists selection is updated IF requestFocus() is
	     * synchronous (it is on Windows).  See bug 4122345
	     */
            if (!getList().hasFocus()) {
                getList().requestFocus();
            }

            int row = convertYToRow(e.getY());
            if (row != -1) {
                getList().setValueIsAdjusting(true);
                int anchorIndex = getList().getAnchorSelectionIndex();
                if (e.isShiftDown() && (anchorIndex != -1)) {
                    getList().setSelectionInterval(anchorIndex, row);
                }
                else {
                    if (getList().isSelectedIndex(row)) {
                        getList().removeSelectionInterval(row, row);
                    }
                    else {
                        getList().addSelectionInterval(row, row);
                    }
                }
            }
        }
        /**
           Override does nothing simulating the
           behavior when control is down
           */
        public void mouseDragged(MouseEvent e) {
        }
    // end inner class ToggleMouseInputHandler
    }
// end class JToggleList
}



