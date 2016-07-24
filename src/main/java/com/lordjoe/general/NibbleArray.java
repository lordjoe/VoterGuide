package com.lordjoe.general;

/**
 * com.lordjoe.general.NibbleArray
 *  this is a compact array of 2 bit values - useful for
 * storing and sending values with 4 states
 * @author slewis
 * @date Dec 8, 2004
 */
public class NibbleArray
{
    public static final Class THIS_CLASS = NibbleArray.class;
    public static final NibbleArray EMPTY_ARRAY[] = {};

    public static final int NIBBLE_SIZE = 2; // 2 nibbles per byte

    // values of successive 2 byte chunks
    public static final byte[] NIBBLE_BITS = { (byte)0xf,(byte)0xf0

    };

    private byte[] m_data;

    public NibbleArray(int size)
    {
        m_data = new byte[(size + NIBBLE_SIZE - 1)/ NIBBLE_SIZE];
    }
    public int getLength()
     {
         return  getData().length * NIBBLE_SIZE;
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
        int index = location >> 1;
        int bit = location & 1;
        byte mask = NIBBLE_BITS[bit];
        byte[] values = getData();
        int maskValue = values[index] & mask;
        maskValue >>= 4 * bit;
        return  maskValue & NIBBLE_BITS[0];
    }
    
    
    public void setValue(int location,int value)
    {
        int index = location >> 1;
        int bit = location & 1;
        byte[] values = getData();
        byte oldVal = values[index];
        values[index] = (byte)((oldVal & (255 ^ NIBBLE_BITS[bit])) |
              (value << 4 * bit));

    }

    public boolean equivalent(NibbleArray test)
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


