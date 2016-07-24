package com.lordjoe.general;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: slewis
 * Date: Dec 8, 2004
 * Time: 9:29:44 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * com.lordjoe.general.BitArray2D
 *
 * @author slewis
 * @date Dec 8, 2004
 */
public class NipArray2D extends NipArray
{
    public static final Class THIS_CLASS = NipArray2D.class;
    public static final NipArray2D EMPTY_ARRAY[] = {};

    private final int m_RowSize;
    public NipArray2D(int size,int rows) {
        super(size);
        m_RowSize = rows;
    }

    public int getRowSize()
    {
        return m_RowSize;
    }

    public int rowColToIndex(int row,int col)
  {
      if(row < 0 || col < 0)
          return -1;
      if(col >= getRowSize())
          return -1;
      int ret = getRowSize() * row + col;
      if(ret > getLength())
          return -1;
      return ret;
  }

    public Point indexToRowColumn(int index)
    {
        int row = index / getRowSize();
        int col = index % getRowSize();
        Point ret = new Point(row,col);
        return ret;
    }

    public int getValue(int row, int column)
    {
        return getValue(rowColToIndex(row, column));
    }
    public void setValue(int row, int column,int value)
    {
        setValue(rowColToIndex(row, column),value);
    }
    public boolean equivalent(NipArray2D test)
       {
           if(!super.equivalent(test))
               return false;
           return getRowSize() == test.getRowSize();
       }

}
