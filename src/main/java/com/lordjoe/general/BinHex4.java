package com.lordjoe.general;


/**
 * com.lordjoe.general.BinHex4
 *
 * @author slewis
 * @date May 19, 2005
 */
public class BinHex4
{
    public static final Class THIS_CLASS = BinHex4.class;
    public static final BinHex4 EMPTY_ARRAY[] = {};

    public static final int VIRTUAL_LINE_LENGTH = 64;
    public static final int ACTUAL_LINE_LENGTH = 56;
    public static final int MAX_LINES = 224;
    public static final byte DEFAULT_DATA = 0;
/*
* this takes a string which is
* formatted as MAX_LINES lines of
* length  ACTUAL_LINE_LENGTH and
* returns elements assuming it is
* formatted as MAX_LINES lines of
* length  VIRTUAL_LINE_LENGTH
* parameters
* in - a string of lenght ACTUAL_LINE_LENGTH * 	MAX_LINES
* index desired element
* return element of the virtual array
*/
    byte virtualElement(byte[] inp, int index)
    {
        if (index < 0)
            return DEFAULT_DATA;
        int row = index / VIRTUAL_LINE_LENGTH;
        if (row >= MAX_LINES)
            return DEFAULT_DATA;
        int col = index % VIRTUAL_LINE_LENGTH;
        if (col >= ACTUAL_LINE_LENGTH)
            return DEFAULT_DATA;
        int realIndex = row * ACTUAL_LINE_LENGTH + col;
        return inp[realIndex];
    }

//    /**
//    * this tests the virtual element routine
//    */
//    void testVirualElement()
//    {
//        int i = 0;
//        int row,col;
//        byte[] table = new byte[4];
//        table[0] = 0;
//        table[1] = 6;
//        table[2] = 7;
//        table[3] = 4;
//
//
//        char input[ACTUAL_LINE_LENGTH * MAX_LINES];
//        char output[VIRTUAL_LINE_LENGTH * MAX_LINES];
//        for(i = 0; i < 	ACTUAL_LINE_LENGTH * MAX_LINES; i++)
//           input[i] = table[(rand() & 3)];
//        for(i = 0; i < 	VIRTUAL_LINE_LENGTH * MAX_LINES; i++)
//           output[i] = virtualElement(input,i);
//        for(row = 0; row < MAX_LINES; row++) {
//            // all corresponding elements should be equal
//            for(col = 0; col < ACTUAL_LINE_LENGTH; col++) {
//                char correct = input[ ACTUAL_LINE_LENGTH * row + col];
//                char test = output[ VIRTUAL_LINE_LENGTH * row + col];
//                if(correct != test)
//                    printf("Problem at %d , %d\n",row,col);
//            }
//            // all non-matching elements should be default
//            for(; col < VIRTUAL_LINE_LENGTH; col++) {
//                char test = output[ VIRTUAL_LINE_LENGTH * row + col];
//                if(DEFAULT_DATA != test)
//                    printf("Problem at %d , %d\n",row,col);
//
//            }
//        }
//
//    }
//


    /**
     * build a remap from 4 in chars to
     * 256 out chars
     */
    protected static byte[] buildRecode(byte[] in)
    {
        byte[] ret = new byte[256];
        int i = 0;
        for (i = 0; i < 256; i++)
            ret[i] = 0;
        for (i = 0; i < 4; i++)
            ret[in[i]] = (byte) i;
        return ret;
    }

    /**
     * given an array of bytes holding onnly the values in table
     * encode a string with the data and a decode table
     * @param data data as above
     * @param table an array of 4 legal values
     * @return an encoded string
     */
    public static String encode2(byte[] data, byte[] table)
    {
        byte[] lookup = buildRecode(table);
        byte[] temp = new byte[data.length];
        int i = 0;
        // convert to nips
        for (i = 0; i < data.length; i++)
            temp[i] = lookup[data[i]];

        // pack
        byte[] nipsHolder = new byte[data.length / 4];
        for (int j = 0; j < nipsHolder.length; j++)
        {
            byte packed = packNips(temp, 4 * j);
            nipsHolder[j] = packed;
        }

        StringBuffer sb = new StringBuffer();
        for (i = 0; i < 4; i++) {
            char tableValue = (char)(table[i] + '0');
            sb.append(tableValue); // start with the lookup table
        }

        String ret = Base64.encodeBytesAsString(nipsHolder);
        sb.append(ret);
        return sb.toString();

    }

    /**
     * Decode routine for 'binhex4' encodeing
     * data is the passed string set up as follows
     * chars 0-3 are theallowed 4 values
     * the rest in the string   12544 / 4 values is
     * binhex packed data 4 2 bit nips per char
     * these are decoded by unpacking and looking of the value in
     * the table enerated by the first 4 chars
     * inputs
     * data - the original string usually of length  4 + (12544 / 4)
     * out output of length 12544 + 1 - 0 terminated string
     */
    public static byte[] decode2(byte[] data)
    {
        int len = data.length;
        byte[] table = new byte[4];
        int i = 0;
        // first 4 chars are mapping
        for (i = 0; i < 4; i++)
        {
            byte c = (byte) (data[i] - '0');
            table[i] = c;
        }
        byte[] packed = Base64.decode(data, 4, data.length - 4);
        byte[] ret = new byte[4 * packed.length];
        int j = 0;
        byte[] unpack = new byte[4];
        // convert to nips
        for (i = 0; i < packed.length; i++)
        {
            byte c = packed[i];
            unpackNips(c, unpack);
            for (int k = 0; k < 4; k++)
                ret[j++] = table[unpack[k]];
        }
        return ret;


    }

    /**
     * unpack one char into 4 2 bit nips
     * c - the input character
     * out - an array of 4 chars to recieve the nip
     */
    public static void unpackNips(byte c, byte[] out)
    {
        out[0] = (byte) (c & 3);
        out[1] = (byte) ((c >> 2) & 3);
        out[2] = (byte) ((c >> 4) & 3);
        out[3] = (byte) ((c >> 6) & 3);
    }

    /**
     * pack an array of 4 2 bit nips into
     * one char
     * out - 4 char arrsy
     * return - packed data
     */
    public static byte packNips(byte[] out, int offset)
    {
        byte c = (byte) ((out[0 + offset] & 3) + ((out[1 + offset] & 3) << 2) +
                ((out[2 + offset] & 3) << 4) + ((out[3 + offset] & 3) << 6));
        return c;
    }
}
