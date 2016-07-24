package com.lordjoe.ui;

import javax.swing.table.*;

/**
 * com.lordjoe.ui.NoneditableTableModel
 * @see http://rickproctor.blogspot.com/2007/02/making-jtable-non-editable_21.html
 * Table model that does not support editing
 * @author Steve Lewis
 * @date Oct 27, 2008
 */
public class NoneditableTableModel extends DefaultTableModel
{
    public static NoneditableTableModel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = NoneditableTableModel.class;

    public NoneditableTableModel()
    {
    }


    public boolean isCellEditable(int row, int column)
     {
         return false;
     }

}
