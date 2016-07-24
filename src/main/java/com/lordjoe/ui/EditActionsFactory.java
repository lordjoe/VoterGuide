package com.lordjoe.ui;

import com.lordjoe.utilities.*;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import javax.swing.text.*;
import java.beans.*;
import java.awt.event.*;
import java.awt.*;


/**
 * com.lordjoe.ui.EditActionsFactory
 * Useful set of Actrions for an edit menu
 * @author Steve Lewis
 * @date May 10, 2006 
 */
public abstract class EditActionsFactory
{
    public final static EditActionsFactory[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = EditActionsFactory.class;

    private static Action COPY_ACTION;
    private static Action CUT_ACTION;
    private static Action PASTE_ACTION;
    private static Action SELECT_ALL_ACTION;

    public synchronized static Action getCopyAction()
    {
        if(COPY_ACTION == null) {
            COPY_ACTION = new CopyAction();
        }
        return COPY_ACTION;
    }
    public synchronized  static Action getCutAction()
    {
        if(CUT_ACTION == null) {
            CUT_ACTION = new CutAction();
        }
        return CUT_ACTION;
    }
    public synchronized  static Action getPasteAction()
    {
        if(PASTE_ACTION == null) {
            PASTE_ACTION = new PasteAction();
        }
        return PASTE_ACTION;
    }
    public synchronized  static Action getSelectAllAction()
    {
        if(SELECT_ALL_ACTION == null) {
            SELECT_ALL_ACTION = new SelectAllAction();
        }
        return SELECT_ALL_ACTION;
    }

    private EditActionsFactory()
    {
    }

    public  static abstract class EditAction extends AbstractAction implements PropertyChangeListener
    {
        protected EditAction()
        {
            FocusManager.getCurrentManager().addPropertyChangeListener(this);
        }

        /**
         * This method gets called when a bound property is changed.
         * @param evt A PropertyChangeEvent object describing the event source
         *   	and the property that has changed.
         */

        public void propertyChange(PropertyChangeEvent evt)
        {
            String propertyName = evt.getPropertyName();
             if(!"focusOwner".equals(propertyName))
                return;
            Object o1 = evt.getOldValue();
            Object o2 = evt.getNewValue();
//            if(!(o1 instanceof JTextComponent) &&
//                    !(o2 instanceof JTextComponent))
//                return;
//             GeneralUtilities.printString(propertyName);
            boolean enabled = o2 != null && o2 instanceof JTextComponent;
            setEnabled(enabled);

        }

        public JTextComponent getTextComponent(ActionEvent e)
        {
            if (e != null) {
                Object o = e.getSource();
                if (o instanceof JTextComponent) {
                    return (JTextComponent) o;
                }
            }
            return null;
        }

        public JTextComponent getTextComponent()
        {
            Component e = FocusManager.getCurrentManager().getFocusOwner();
            if (e != null) {
                if (e instanceof JTextComponent) {
                    return (JTextComponent) e;
                }
            }
            return null;
        }
    }

    public  static class CopyAction extends EditAction
    {
        public CopyAction()
        {
            putValue(Action.NAME, ResourceString.parameterToText("Copy"));
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        }
        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
                target.copy();
            }
        }
    }

    public  static class PasteAction extends EditAction
    {
        public PasteAction()
        {
            putValue(Action.NAME,ResourceString.getStringFromText("Paste"));
             putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        }
        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
                target.paste();
            }
        }
    }

    public  static class CutAction extends EditAction
    {
        public CutAction()
        {
            putValue(Action.NAME, ResourceString.parameterToText("Cut"));
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        }
         /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
                target.cut();
            }
        }
    }
    public  static class SelectAllAction extends EditAction
    {
        public SelectAllAction()
        {
            putValue(Action.NAME,ResourceString.getStringFromText("Select All"));
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        }
        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
                target.setSelectionStart(0);
                int selectionEnd = target.getText().length();
                target.setSelectionEnd(selectionEnd);
            }
        }
    }
}
