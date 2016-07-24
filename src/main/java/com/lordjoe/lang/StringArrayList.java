/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.lang;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.text.*;
import java.util.*;


/***************************************************************************************************
 *
 *  <!-- StringArrayList -->
 *
 *  This class is an array of Strings based on the ArrayList class.  This class exposes a
 *  String-oriented API not possible with a generic ArrayList.
 *
 ***************************************************************************************************
 */
public class StringArrayList
{
    private static final String LINE_SEP = System.getProperty("line.separator");

    private ArrayList list;

    public StringArrayList()
    {
        super();

        this.list = new ArrayList();
    }

    public StringArrayList(final int initialCapacity)
    {
        super();

        this.list = new ArrayList(initialCapacity);
    }

    public StringArrayList(String[] array)
    {
        this(array.length);
        for (int i = 0; i < array.length; i++)
        {
            addString(array[i]);
        }
    }


    /***********************************************************************************************************************
     *
     *  <!-- StringArrayList() -->
     *
     *  This method assumes that the collection only contains Strings.
     *
     ***********************************************************************************************************************
     */
    public StringArrayList(final Collection collection)
    {
        this(collection.size());
        Iterator iterator = collection.iterator();
        while ( iterator.hasNext() )
        {
            final String element = (String) iterator.next();
            addString(element);
        }
    }


    public StringArrayList(final int size, final String value)
    {
        this(size);
        for (int i = 0; i < size; i++)
        {
            addString(value);
        }
    }

    public int size()
    {
        return this.list.size();
    }

    /**
     *  Sort the list alphabetically.
     */
    public void sort()
    {
        Collections.sort(this.list);
    }

    public String getString(int i)
    {
        return (String) this.list.get(i);
    }

    /** convenience method for getting the last element */
    public String getLastString()
    {
        final int last = size() - 1;
        return (String) this.list.get(last);
    }


    public void set(final int index, final String value)
    {
        this.list.set(index, value);
    }

    public void setAll(final String value)
    {
        for (int i = 0; i < size(); i++)
        {
            set(i, value);
        }
    }

    public void addString(final String value)
    {
        this.list.add(value);
    }


    /***********************************************************************************************
     *
     *  <!-- addStringWithoutDuplicates() -->
     *
     *  Adds an integer to the list if it is not already in the list.
     *
     ***********************************************************************************************
     */
    public void addStringWithoutDuplicates(final String value)
    {
        // determine if the value is in the list
        final boolean inList = findForward(value) != -1;
        if ( !inList )
        {
            // this value is not in the list yet, add it to the list
            addString(value);
        }
    }

    /***********************************************************************************************
     *
     *  <!-- addWithoutDuplicates() -->
     *
     *  Adds all of the elements in the given list to this list if each
     *  element is not already in the list.
     *
     ***********************************************************************************************
     */
    public void addWithoutDuplicates(final StringArrayList values)
    {
        for (int i = 0; i < values.size(); i++)
        {
            addStringWithoutDuplicates(values.getString(i));
        }
    }



    /***********************************************************************************************
     *
     *  <!-- addToValues() -->
     *
     *  This method adds the given value to all of the elements in the array list.
     *
     ***********************************************************************************************
     */
    public void addToValues(final String value)
    {
        for (int i = 0; i < size(); i++)
        {
            set(i, getString(i) + value);
        }
    }



    public void increment(final int index)
    {
        set(index, new String(getString(index)+1));
    }


    /***********************************************************************************************
     *
     *  <!-- removeLast() -->
     *
     *  This method adds the given value to all of the elements in the array list.
     *
     ***********************************************************************************************
     */
    public void removeLast()
    {
        final int last = size() - 1;
        this.list.remove(last);
    }


    /***********************************************************************************************
     *
     *  <!-- findForward() -->
     *
     *  This method finds the first occurrence of the given value in the list.
     *
     ***********************************************************************************************
     */
    public int findForward(final String value)
    {
        for (int i = 0; i < size(); i++)
        {
            if ( getString(i).equals(value) )
                return i;
        }
        return -1;
    }



    /***********************************************************************************************
     *
     *  <!-- findBackward() -->
     *
     *  This method finds the last occurrence of the given value in the list.
     *
     ***********************************************************************************************
     */
    public int findBackward(final String value)
    {
        for (int i = size()-1; i >= 0 ; i--)
        {
            if ( getString(i) == value )
                return i;
        }
        return -1;
    }


    /***********************************************************************************************
     *
     *  <!-- contains() -->
     *
     *  This method determines if the given value is in the list.
     *
     ***********************************************************************************************
     */
    public boolean contains(final String value)
    {
        final int index = findForward(value);
        return index != -1;
    }



    /***********************************************************************************************
     *
     *  <!-- equals() -->
     *
     *  @return
     *      true only if the specified object is also a BatchFolderList and it is
     *      equal to this list
     *
     *  This method is a model equals() method for lists that are not derived from ArrayList.
     *
     ***********************************************************************************************
     */
     public boolean equals(Object obj)
    {
        if ( ! (obj instanceof StringArrayList) ) return false;

        StringArrayList listA = this;
        StringArrayList listB = (StringArrayList) obj;

        if ( listA.size() != listB.size() )
            return false;

        return listA.list.equals(listB.list);
    }



    /***********************************************************************************************
     *
     *  <!-- equals() -->
     *
     *
     ***********************************************************************************************
     */
    public boolean equals(final String[] expectedColumnLabel)
    {
        StringArrayList listA = this;
        StringArrayList listB = new StringArrayList(expectedColumnLabel);

        if ( listA.size() != listB.size() )
            return false;

        return listA.list.equals(listB.list);
    }



    private static final String toStringDelimiter = "\t"; // a tab is a good delimiter for ints (makes nice columns when lists of these are stored)
    private static final int toStringScanlineStride = 30;



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method stores a StringArrayList to a String.
     *
     *  @return
     *      a String representation of the RangeList
     *
     ***********************************************************************************************
     */
    public String toString()
    {
        return toString(toStringScanlineStride, toStringDelimiter);
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method can be used to trace the values in the array list.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public String toString(final int scanlineStride, final String delimiter)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size(); i++)
        {
            sb.append(getString(i));
            if ( i < size()-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }

        return sb.toString();
    }


    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method can be used to trace the values in the array list.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public String toString(final int scanlineStride, final String delimiter, final NumberFormat nf)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size(); i++)
        {
            sb.append(nf.format(getString(i)));
            if ( i < size()-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }

        return sb.toString();
    }


    /**
     *  This method gets a new list that consists of the values in the given range, inclusive.
     *
     *  Note:
     *    The method ArrayList.subList(int, int) returns a List that cannot be cast to
     *    a StringArrayList.
     */
    public StringArrayList getSubList(final int start, final int end)
    {
        StringArrayList subList = new StringArrayList();
        for (int i = start; i <= end; i++)
        {
            subList.addString(this.getString(i));
        }
        return subList;
    }

    /***********************************************************************************************
     *
     *  <!-- getInt() -->
     *
     *  This method is used for converting a byte to an integer.
     *                                                                                          <p>
     *  For example, in the following code, y would not have the expected value.<br>
     *    byte x;                                                                               <br>
     *    int y;                                                                                <br>
     *    y = x; // error                                                                       <br>
     *                                                                                          <p>
     *  So, instead we would write                                                              <br>
     *    byte x;                                                                               <br>
     *    int y;                                                                                <br>
     *    y = ArrayToStringOps.getInt(x); // ok                                                         <br>
     *
     ***********************************************************************************************
     */
    public static int getInt(final byte value)
    {
        final int intForDisplay = (int)(((byte)value)&0xff);
        return intForDisplay;
    }


    /***********************************************************************************************
     *
     *  <!-- needNewline() -->
     *
     *  This method determines if a newline is needed in a trace routine.  For example, if
     *  scanlineStride is 5, then we need a newline after each set of 5 values is written.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param i
     *  @return
     *
     ***********************************************************************************************
     */
    public static boolean needNewline
        (
        final int scanlineStride,
        final String delimiter,
        final int i
        )
    {
        // If "no scanline stride" is specified via scanlineStride = -1, then
        // we never need a newline.  Also, if the delimiter is already a newline, then
        // we don't need to worry about scanlines.
        if ( scanlineStride == -1 || scanlineStride == 0 || delimiter.equals(LINE_SEP) )
        {
            return false;
        }

        // If scanlineStride is 1, then we always write 1 value per line, so we
        // always need a newline.
        if ( scanlineStride == 1 )
        {
            return true;
        }

        // If we have written scanlineStride elements already, then it is
        // time for a newline.
        if ( (i!=0 && (i+1)%scanlineStride==0) )
        {
            return true;
        }

        return false;
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method can be used to trace the values in the array.
     *
     *  @param  array
     *      array of values to be traced
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final boolean[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            final boolean value = array[i];
            sb.append(value?"T" : "F");
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for byte values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final byte[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            final int value = getInt(array[i]);
            sb.append(value);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for byte values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString(final short[] array, final int scanlineStride, final String delimiter)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            final short value = array[i];
            sb.append(value);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for integer values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final int[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of floats to a String.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final float[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of floats to a String.  The given
     *  DecimalFormat is used to format the floats.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final float[] array,
        final DecimalFormat decimalFormat,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(decimalFormat.format(array[i]));
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of floats to a String.  The given
     *  maximumFractionDigits is used to create a DecimalFormat to be used
     *  to format the floats.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final float[] array,
        final int maximumFractionDigits,
        final int scanlineStride,
        final String delimiter
        )
    {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(maximumFractionDigits);

        return toString(array, decimalFormat, scanlineStride, delimiter);
    }


    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of doubles to a String.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final double[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }


    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of doubles to a String.  The given
     *  DecimalFormat is used to format the doubles.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final double[] array,
        final DecimalFormat decimalFormat,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(decimalFormat.format(array[i]));
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method converts an array of doubles to a String.  The given
     *  maximumFractionDigits is used to create a DecimalFormat to be used
     *  to format the doubles.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final double[] array,
        final int maximumFractionDigits,
        final int scanlineStride,
        final String delimiter
        )
    {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(maximumFractionDigits);

        return toString(array, decimalFormat, scanlineStride, delimiter);
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for double values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final Double[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for
     *  Point.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final Point[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append("(" + array[i].x + "," + array[i].y + ")");
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for
     *  Point2D.Double values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final Point2D.Double[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append("(" + array[i].getX() + "," + array[i].getY() + ")");
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method is the same as the previous, except it is for String values.
     *
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *
     ***********************************************************************************************
     */
    public static String toString(final String[] array, final int scanlineStride, final String delimiter)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            final String element = array[i];
            if ( element == null )
            {
                sb.append("");
            }
            else
            {
                sb.append(element);
            }
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString()'s -->
     *
     *  This method will toString the values in the given array using a standard format.
     *
     ***********************************************************************************************
     */
    public static String toString(final Collection arrayList)
     {
         return toString(arrayList, 30, " ");
     }
     public static String toString(final boolean[] array)
    {
        return toString(array, 30, " ");
    }
    public static String toString(final byte[] array)
    {
        return toString(array, 30, " ");
    }
    public static String toString(final int[] array)
    {
        return toString(array, 30, " ");
    }
    public static String toString(final float[] array)
    {
        return toString(array, 1, ""); // float's are most easily viewed 1-per line this also facilitates importation into Excel
    }
    public static String toString(final double[] array)
    {
        return toString(array, 1, ""); // double's are most easily viewed 1-per line this also facilitates importation into Excel
    }
    public static String toString(final Double[] array)
    {
        return toString(array, 1, ""); // double's are most easily viewed 1-per line this also facilitates importation into Excel
    }
    public static String toString(final Point[] array)
    {
        return toString(array, 1, ""); // Point2D.Double's are most easily viewed 1-per line this also facilitates importation into Excel
    }
    public static String toString(final Point2D.Double[] array)
    {
        return toString(array, 1, ""); // Point2D.Double's are most easily viewed 1-per line this also facilitates importation into Excel
    }
    public static String toString(final String[] array)
    {
        return toString(array, 5, " ");
    }



    /***********************************************************************************************
     *
     *  <!-- toStringExcelFriendly()'s -->
     *
     *  This method can be used to trace the values in the given array using a standard format
     *  that facilitates importation into Excel.
     *
     ***********************************************************************************************
     */
    public static String toStringExcelFriendly(final Point2D.Double[] array)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i].getX() + "\t" + array[i].getY());
            if ( i < array.length-1 ) sb.append(LINE_SEP);
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toStringExcelFriendly() -->
     *
     *  This method is the same as the previous, except it is for Point values.
     *
     ***********************************************************************************************
     */
    public static String toStringExcelFriendly(final Point[] array)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i].x + "\t" + array[i].y);
            if ( i < array.length-1 ) sb.append(LINE_SEP);
        }
        return sb.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- write() -->
     *
     *  This method will write the values in the array to the given BufferedWriter.
     *
     *  @param  BufferedWriter
     *      buffered writer to which the values are written
     *  @param  array
     *      array of values to be written
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static void write
        (
        BufferedWriter out,
        final int[] array,
        final int scanlineStride,
        final String delimiter
        ) throws IOException
    {
        for (int i = 0; i < array.length; i++)
        {
            out.write(Integer.toString(array[i]));
            if ( i < array.length-1 ) out.write(delimiter); // write delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) out.newLine(); // write newline
        }
        out.newLine();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method will convert the values in the array to a formatted string representing the
     *  contents of the array.
     *
     *  @param  array
     *      array of values to be traced
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static String toString(int[] array, final String delimiter)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            buffer.append(Integer.toString(array[i]));
            if ( i < array.length-1 ) buffer.append(delimiter);
        }
        return buffer.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method will convert the values in the array to a formatted
     *  string representing the contents of the array.
     *
     *  @param  array
     *      array of values to be traced
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static String toString(double[] array, final String delimiter)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            buffer.append(Double.toString(array[i]));
            if ( i < array.length-1 ) buffer.append(delimiter);
        }
        return buffer.toString();
    }



    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method can be used to trace the values in the array.  If the objects
     *  have simple string representations, then the output will be properly
     *  formatted; however, if the objects have complex string representations
     *  (i.e., contain newline characters), then it might be helpful for the
     *  client of this method to use a LINE_SEP as the delimiter, instead of
     *  a tab.
     *
     *  @param  array
     *      array of values to be traced
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final Object[] array,
        final int scanlineStride,
        final String delimiter
        )
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i].toString());
            if ( i < array.length-1 ) sb.append(delimiter); // trace delimiter
            if ( needNewline(scanlineStride, delimiter, i) ) sb.append(LINE_SEP); // trace newline
        }
        return sb.toString();
    }




    /***********************************************************************************************
     *
     *  <!-- toString() -->
     *
     *  This method can be used to trace the values in the array.  If the objects
     *  have simple string representations, then the output will be properly
     *  formatted; however, if the objects have complex string representations
     *  (i.e., contain newline characters), then it might be helpful for the
     *  client of this method to use a LINE_SEP as the delimiter, instead of
     *  a tab.
     *
     *  @param  arrayList
     *      array of values to be traced
     *  @param scanlineStride
     *      number of values to print per line; -1 or 0 to avoid newlines
     *  @param  delimiter
     *      string to be written between values
     *
     ***********************************************************************************************
     */
    public static String toString
        (
        final Collection arrayList,
        final int scanlineStride,
        final String delimiter
        )
    {
        Object[] array = arrayList.toArray();
        return toString(array, scanlineStride, delimiter);
    }

}
