package com.lordjoe.ui;

import java.awt.datatransfer.*;
import java.lang.reflect.*;
import java.io.*;

/**
 * com.lordjoe.ui.ObjectDataFlavor
 *    Uses to drag and drop a col;lection of objects -
 *   Type must be known to reciever and sender -
 *    maybe in the future I can be a little more general
 * @author Steve Lewis
 * @date Jul 23, 2008
 */
public class ObjectDataFlavor<T> implements Transferable
{
    public static ObjectDataFlavor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectDataFlavor.class;

    private final DataFlavor m_Flavor;
    private final T[] m_Data;
    public ObjectDataFlavor(T[] data) {

        Class type = data.getClass().getComponentType();
        String colorType = DataFlavor.javaJVMLocalObjectMimeType +
                           ";class=" + data.getClass().getComponentType().getName();
        try {
            m_Flavor = new DataFlavor(colorType);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("should never get here",e);
        }
         m_Data = (T[])Array.newInstance(type,data.length);
        System.arraycopy( data, 0, m_Data, 0, data.length );
    }

    /**
     * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.  The array should be ordered according to preference
     * for providing the data (from most richly descriptive to least descriptive).
     *
     * @return an array of data flavors in which this data can be transferred
     */
    public DataFlavor[] getTransferDataFlavors()
    {
        DataFlavor[] ret = {  m_Flavor };
        return ret;
    }

    /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     *
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        return m_Flavor.equals(flavor);
    }

    /**
     * Returns an object which represents the data to be transferred.  The class
     * of the object returned is defined by the representation class of the flavor.
     *
     * @param flavor the requested flavor for the data
     * @throws java.io.IOException if the data is no longer available
     *                             in the requested flavor.
     * @throws java.awt.datatransfer.UnsupportedFlavorException
     *                             if the requested data flavor is
     *                             not supported.
     * @see java.awt.datatransfer.DataFlavor#getRepresentationClass
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
    {
        if(m_Flavor.equals(flavor))
            return m_Data;
        return null;
    }
}
