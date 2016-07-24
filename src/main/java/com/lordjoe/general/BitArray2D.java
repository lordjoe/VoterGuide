package com.lordjoe.general;

import java.awt.*;


/**
 * com.lordjoe.general.BitArray2D
 *
 * @author slewis
 * @date Dec 8, 2004
 */
public class BitArray2D extends BitArray
{
    public static final Class THIS_CLASS = BitArray2D.class;
    public static final BitArray2D EMPTY_ARRAY[] = {};
    
    public static final int BAD_INDEX = -1;

    private final int m_RowSize;
    public BitArray2D(int size,int rows) {
        super(size);
        m_RowSize = rows;
    }
    /**
      * copy constructor
      * @param src non-null array to make a deep copy
      */
     public BitArray2D(BitArray2D src)
      {
          super(src);
          m_RowSize = src.getRowSize();
      }


    public int getRowSize()
     {
         return m_RowSize;
     }

    public boolean[] getRowData(int row) {
       boolean[] ret = new boolean[getRowSize()];
        for (int i = 0; i < ret.length; i++)
        {
           ret[i] = getValue(row,i);            
        }
        return ret;
    }


      public int rowColToIndex(int row,int col)
    {
        if(row < 0 || col < 0)
            return BAD_INDEX;
        if(col >= getRowSize())
            return BAD_INDEX;
        int ret = getRowSize() * row + col;
        if(ret >= getLength())
            return BAD_INDEX;
        return ret;
    }

    public Point indexToRowColumn(int index)
    {
        int row = index / getRowSize();
        int col = index % getRowSize();
        Point ret = new Point(row,col);
        return ret;
    }

    public boolean getValue(int row, int column)
    {
        return getValue(rowColToIndex(row, column));
    }

    public void setValue(int row, int column,boolean value)
    {
        setValue(rowColToIndex(row, column),value);
    }

    public boolean equivalent(BitArray2D test)
      {
          if(!super.equivalent(test))
              return false;
          return getRowSize() == test.getRowSize();
      }

}
