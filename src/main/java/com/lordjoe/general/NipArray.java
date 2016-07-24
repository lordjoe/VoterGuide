package com.lordjoe.general;

/**
 * com.lordjoe.general.NipArray
 *  this is a compact array of 2 bit values - useful for
 * storing and sending values with 4 states
 * @author slewis
 * @date Dec 8, 2004
 */
public class NipArray
{
    public static final Class THIS_CLASS = NipArray.class;
    public static final NipArray EMPTY_ARRAY[] = {};

    public static final int NIP_SIZE = 4;

    // values of successive 2 byte chunks
    public static final byte[] NIP_BITS = { (byte)3,(byte)12,(byte)48,(byte)192 };

    private byte[] m_data;

    public NipArray(int size)
    {
        m_data = new byte[(size + NIP_SIZE - 1)/ NIP_SIZE];
    }
    public int getLength()
     {
         return  getData().length * NIP_SIZE;
     }

    /**
     * return the underlying byte array
     * @return non-null byte array
     */
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

    public int getValue(int location)
    {
        int index = location >> 2;
        int bit = location & 3;
        byte mask = NIP_BITS[bit];
        byte[] values = getData();
        int maskValue = values[index] & mask;
        maskValue >>= 2 * bit;
        return  maskValue & NIP_BITS[0];
    }
    
    
    public void setValue(int location,int value)
    {
        int index = location >> 2;
        int bit = location & 3;
        byte[] values = getData();
        byte oldVal = values[index];
        values[index] = (byte)((oldVal & (255 ^ NIP_BITS[bit])) |
              (value << 2 * bit));

    }

    public boolean equivalent(NipArray test)
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
}


