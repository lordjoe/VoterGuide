package com.lordjoe.ui;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

/**
 * com.lordjoe.ui.AbstractDropHandler
 *
 * @author Steve Lewis
 * @date Oct 12, 2007
 */
public abstract class AbstractDropHandler implements java.awt.dnd.DropTargetListener
        //   java.awt.dnd.DragSourceListener,
        //  java.awt.dnd.DragGestureListener
{

    public static final int ACTION_REJECT = -1;

    public static AbstractDropHandler[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractDropHandler.class;
    private java.awt.dnd.DropTarget m_DropTarget;
    protected final JComponent m_Component;

    public AbstractDropHandler(
            JComponent pComponent)
    {
        m_Component = pComponent;
    }

    protected void register(JComponent pComponent)
    {
        m_DropTarget = new java.awt.dnd.DropTarget(pComponent, this);
    }   // end initComponents

    public JComponent getComponent()
    {
        return m_Component;
    }

/* ********  D R O P   T A R G E T   L I S T E N E R   M E T H O D S  ******** */

    public void dropActionChanged(java.awt.dnd.DragSourceDragEvent evt)
    {   //System.out.println( "DragSourceListener.dropActionChanged" );
    }   // end dropActionChanged

    public void dragEnter(java.awt.dnd.DropTargetDragEvent evt)
    {   //System.out.println( "DropTargetListener.dragEnter" );
        DataFlavor[] flavors = evt.getCurrentDataFlavors();
        if(selectFlavor(flavors) != null)
            evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);
    }   // end dragEnter

    public void dragExit(java.awt.dnd.DropTargetEvent evt)
    {   //System.out.println( "DropTargetListener.dragExit" );
    }   // end dragExit

    public void dragOver(java.awt.dnd.DropTargetDragEvent evt)
    {   //System.out.println( "DropTargetListener.dragOver" );
    }   // end dragOver

    public void dropActionChanged(java.awt.dnd.DropTargetDragEvent evt)
    {   //System.out.println( "DropTargetListener.dropActionChanged" );
        evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);
    }   // end dropActionChanged


    public void drop(java.awt.dnd.DropTargetDropEvent evt)
    {   //System.out.println( "DropTargetListener.drop" );
        Object obj = getTransferObject(evt);
        if (obj != null && isObjectAcceptableDrop(obj)) {
            int action = handleDrop(obj, evt);
//            if (action != ACTION_REJECT) {
//                evt.acceptDrop(action);
//            }
//            else {
//                evt.rejectDrop();
//
//            }
//        }   // end if: we got the object
//
//        // Else there was a problem getting the object
//        else {
//            evt.rejectDrop();
        }   // end else: can't get the object
    }   // end drop


    protected Object getTransferObject(java.awt.dnd.DropTargetDropEvent evt)
    {
        java.awt.datatransfer.Transferable transferable = evt.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        DataFlavor flavor = null;
        switch (flavors.length) {
            case 0:
                break;
            case 1:
                flavor = flavors[0];
                break;
            default:
                flavor = selectFlavor(flavors);
        }
        if(flavor == null)
            return null;
        try {
            int dropAction = getDropAction();
            evt.acceptDrop(dropAction);
            Object obj = transferable.getTransferData(flavor);
            obj = conditionObject(obj);

            return obj;
        }   // end try
        catch (java.awt.datatransfer.UnsupportedFlavorException e) {
            return null;
        }   // end catch
        catch (java.io.IOException e) {
            return null;
        }   // end catch
    }


    /**
     * overtide to mutate the original object
     * @return  new drop object
     */
    protected Object conditionObject(Object original)
    {
        return original;
    }
    

    /**
     * overtide to change the action on drop
     * @return
     */
    protected int getDropAction()
    {
        return java.awt.dnd.DnDConstants.ACTION_COPY;
    }

    /**
     * select some flavor overtide for better behavior
     *
     * @param options
     * @return
     */
    protected DataFlavor selectFlavor(DataFlavor[] options)
    {
        return options[0];
    }


    /**
     * handle a drop - added is the object for dropping
     *
     * @param added object to add
     * @param evt   event
     * @return one of ACTION_REJECT,-1; DnDConstants.ACTION_NONE,ACTION_COPY,ACTION_MOVE
     */
    public abstract int handleDrop(Object added, java.awt.dnd.DropTargetDropEvent evt);

    /**
     * return true if added is acceptable to drop on this component
     *
     * @param added proposed drop
     * @return trua is we can handle it
     */
    public abstract boolean isObjectAcceptableDrop(Object added);
}
