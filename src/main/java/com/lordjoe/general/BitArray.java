package com.lordjoe.general;

import java.util.*;

/**
 * com.lordjoe.general.BitArray
 *  a class holding a memory efficient array of booleans with the ability
 * to convert to a string usable in an xml file
 * @author slewis
 * @date Dec 8, 2004
 */
public class BitArray
{
    public static final Class THIS_CLASS = BitArray.class;
    public static final BitArray EMPTY_ARRAY[] = {};

    public static final byte[] BYTE_BITS = { (byte)1,(byte)2,(byte)4,
                                             (byte)8,(byte)16,(byte)32,(byte)64,(byte)128 };

    private byte[] m_data;

    public BitArray(int size)
     {
         m_data = new byte[(size + Byte.SIZE - 1)/ Byte.SIZE];
      }

    /**
     * copy constructor
     * @param src non-null array to make a deep copy
     */
    public BitArray(BitArray src)
     {
         this(src.getLength());
         byte[] srcData = src.getData();
         System.arraycopy(srcData,0,m_data,0,srcData.length);
     }


    public BitArray(boolean[] in)
     {
         m_data = new byte[(in.length + Byte.SIZE - 1)/ Byte.SIZE];
         setData(in);
     }

    public void setData(boolean[] in)
    {
        for (int i = 0; i < in.length; i++)
        {
            setValue(i,in[i]);
        }
    }

    public boolean[] getBooleanData()
    {
        boolean[] ret = new boolean[getLength()];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = getValue(i);
        }
        return ret;
    }

    /**
     * put all data to false
     */
    public void clear()
    {
        Arrays.fill(m_data,(byte)0);
    }


    public int getLength()
    {
        return  getData().length * Byte.SIZE;
    }
    public byte[] getData()
    {
        return m_data;
    }

    public void setData(byte[] data)
    {
        m_data = data;
    }

    /**
     * get the data as a string suitable for use in passing to
     *   setDataAsString uses base 64 encoding
     * @return non-null string
     */
     public String getDataAsString() {
        return Base64.encodeBytesAsString(getData());
    }

    /**
     * get the data as a string suitable for use in passing to
     *   setDataAsString uses base 64 encoding
     * @param inp non-null string almost always gotten from  getDataAsString
     */
    public void setDataAsString(String inp)
    {
        setData(Base64.decode(inp));
    }
    public boolean getValue(int location)
    {
        int index = location >> 3;
        int bit = location & 7;
        byte[] values = getData();
        if(index >= values.length)
            return false;  // ?? huh
        return (values[index] & BYTE_BITS[bit]) != 0;
    }

    public int getTrueValueCount()
    {
        int ret = 0;
        byte[] bytes = getData();
        for (int i = 0; i < bytes.length; i++)
        {
            byte aByte = bytes[i];
            ret += bitsOn(aByte);
        }
        return ret;
    }

    public static int bitsOn(byte aByte)
    {
        int ret = 0;
        while(aByte != 0) {
           if((aByte & 1) == 1) {
               ret ++;
           }
            aByte = (byte)(0x7f & (aByte >> 1));
        }
        return ret;
    }


    public void setValue(int location,boolean value)
    {
        int index = location >> 3;
        int bit = location & 7;
        byte[] values = getData();
        if(index >= values.length)
            throw new IllegalArgumentException("index out of bounds " +
                    " was " + location+ " bounds " + values.length);
        if(value)
            values[index] |= BYTE_BITS[bit];
        else
           values[index] &= (255 ^ BYTE_BITS[bit]);

    }


    public boolean equivalent(BitArray test)
     {
         if(getLength() != test.getLength())
             return false;
         byte[] data = getData();
         byte[] testdata = test.getData();
          for (int i = 0; i < data.length; i++)
         {
              if(data[i] != testdata[i])
                  return false;
         }
         return true;
     }

    /**
     * true is any element is not zero
     * @return as above
     */
    public boolean isNonZero()
     {
         if(getLength() == 0)
             return false;
         byte[] data = getData();
           for (int i = 0; i < data.length; i++)
         {
              if(data[i] != 0)
                  return true;
         }
         return false;
     }

}
