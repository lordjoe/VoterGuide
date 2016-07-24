package com.lordjoe.ui;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;

/**
 * com.lordjoe.ui.FileDropHandler
 *
 * @author Steve Lewis
 * @date Oct 12, 2007
 */
public class FileDropHandler extends AbstractDropHandler

{
    public static FileDropHandler[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FileDropHandler.class;
    public static final String FILE_DROP_MIME = "application/x-java-file-list; class=java.util.List";

    private boolean m_FileOnly;
    private boolean m_DirectoryOnly;
    private boolean m_MultipleAllowed;

    public FileDropHandler(JComponent pComponent)
    {
        super(pComponent);
        register(pComponent);
    }


    public boolean isFileOnly()
    {
        return m_FileOnly;
    }

    public void setFileOnly(boolean pFileOnly)
    {
        m_FileOnly = pFileOnly;
        if(pFileOnly)
            setDirectoryOnly(false);
    }

    public boolean isDirectoryOnly()
    {
        return m_DirectoryOnly;
    }

    public void setDirectoryOnly(boolean pDirectoryOnly)
    {
        m_DirectoryOnly = pDirectoryOnly;
        if(pDirectoryOnly)
            setFileOnly(false);
    }

    public boolean isMultipleAllowed()
    {
        return m_MultipleAllowed;
    }

    public void setMultipleAllowed(boolean pMultipleAllowed)
    {
        m_MultipleAllowed = pMultipleAllowed;
    }

    /**
     * select some flavor overtide for better behavior
     *
     * @param options
     * @return
     */
    protected DataFlavor selectFlavor(DataFlavor[] options)
    {
        for (int i = 0; i < options.length; i++) {
            DataFlavor option = options[i];
            if(option.getMimeType().equals(FILE_DROP_MIME))
                return option;
        }
        return null;

    }

    public void dragEnter(DropTargetDragEvent evt)
    {
        Transferable tr = evt.getTransferable();
        DataFlavor[] flavors =  tr.getTransferDataFlavors();
        DataFlavor dataFlavor = selectFlavor(flavors);
        if(dataFlavor == null)
            evt.rejectDrag();
        try {
            Object obj = tr.getTransferData(dataFlavor);
            obj = conditionObject(obj);
            if(obj != null && isObjectAcceptableDrop(obj))
                evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);
            else
                evt.rejectDrag();
                
        }
        catch (UnsupportedFlavorException e) {
            evt.rejectDrag();
         }
        catch (IOException e) {
            evt.rejectDrag();
        }

    }   // end dragEnter

    /**
     * handle a drop - added is the object for dropping
     *
     * @param added object to add
     * @param evt   event
     * @return one of ACTION_REJECT,-1; DnDConstants.ACTION_NONE,ACTION_COPY,ACTION_MOVE
     */
    public int handleDrop(Object added, DropTargetDropEvent evt)
    {
        JComponent component = getComponent();
        if (added instanceof File) {
            File realObj = (File) added;
            if (component instanceof JTextField) {
                JTextField txt = (JTextField) component;
                txt.setText(realObj.getAbsolutePath());
                return java.awt.dnd.DnDConstants.ACTION_COPY;
            }
        }
        if (added instanceof File[]) {
            File[] realObj = (File[] )added;
            if (component instanceof JTextField) {
                JTextField txt = (JTextField) component;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < realObj.length; i++) {
                    File file = realObj[i];
                    if(sb.length() > 0)
                        sb.append(";");
                    sb.append(file.getAbsolutePath());
                }
                txt.setText(sb.toString());
                return java.awt.dnd.DnDConstants.ACTION_COPY;
            }
        }
        return java.awt.dnd.DnDConstants.ACTION_NONE;
    }
    /**
     * overtide to mutate the original object
     *
     * @return new drop object
     */
    protected Object conditionObject(Object original)
    {
        if (original instanceof File)
            return original;
        if (original instanceof File[])
            return original;
        if (original instanceof List) {
            List realObject = (List) original;
            switch (realObject.size()) {
                case 0:
                    return null;
                case 1:
                    original = realObject.get(0);
                    return original;
                default:
                    if (!isMultipleAllowed())
                        return null;
                    try {
                        original = realObject.toArray(new File[0]);
                        return original;
                    }
                    catch (ClassCastException ex) {
                        return null;
                    }
            }
        }
        return null; // not OK
    }

    /**
     * return true if added is acceptable to drop on this component
     *
     * @param added proposed drop
     * @return trua is we can handle it
     */
    public boolean isObjectAcceptableDrop(Object added)
    {
        if (added == null)
            return false;
        Class<? extends Object> aClass = added.getClass();
        if (aClass.isArray()) {
            if (!isMultipleAllowed())
                return false;
            return aClass.getComponentType() == File.class;
        }
        if (added instanceof List) {
            List realObject = (List) added;
            switch (realObject.size()) {
                case 0:
                    return false;
                case 1:
                    added = realObject.get(0);
                default:
                    if (!isMultipleAllowed())
                        return false;
                    try {
                        added = realObject.toArray(new File[0]);
                        return true;
                    }
                    catch (ClassCastException ex) {
                        return false;
                    }
            }
        }
        if (!(added instanceof File)) {
            return false;
        }
        File realAdd = (File) added;
        if (isFileOnly())
            return realAdd.isFile();
        if (isDirectoryOnly())
            return realAdd.isDirectory();
        return true;
    }
}
