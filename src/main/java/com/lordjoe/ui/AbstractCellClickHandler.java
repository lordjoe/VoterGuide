package com.lordjoe.ui;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * com.lordjoe.ui.AbstractCellClickHandler
 *   handler for clicks in a JTable
 * @author Steve Lewis
 * @date Jul 28, 2008
 */
public abstract class AbstractCellClickHandler extends MouseInputAdapter
{
    public static AbstractCellClickHandler[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractCellClickHandler.class;


    private final JTable m_Table;

    protected AbstractCellClickHandler(JTable pTable)
    {
        m_Table = pTable;
        m_Table.addMouseListener(this);
    }

    public JTable getTable()
    {
        return m_Table;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        JTable table = getTable();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);
        int count = e.getClickCount();
        switch(count)  {
            case 1 :
                handleSingleClick(row,col);
                return;
            case 2 :
                handleDoubleClick(row,col);
                return;
            default :
                handleMultipleClick(count,row,col);

        }
    }

    /**
     * handle a click on a cell
     * @param row row clicked or -1 for none
     * @param col col clicked or -1 for none
     */
    public abstract void handleSingleClick(int row ,int col);

    /**
     * handle a double click on a cell
     * @param row row clicked or -1 for none
     * @param col col clicked or -1 for none
     */
    public  void handleDoubleClick(int row ,int col)
    {
        // default to multiple click behavior
        handleMultipleClick(2,  row , col);
    }
    /**
     * handle a multiple clicks on a cell
     * @param row row clicked or -1 for none
     * @param col col clicked or -1 for none
     */
    public  void handleMultipleClick(int multiple,int row ,int col)
    {
        // default to single click behavior
        handleSingleClick(  row , col);
    }
}
