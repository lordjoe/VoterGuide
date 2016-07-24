package com.lordjoe.general;


/**
 * com.lordjoe.general.AggregateUtilities
 *
 * @author slewis
 * @date Feb 17, 2005
 */
public class AggregateUtilities
{
    public static final Class THIS_CLASS = AggregateUtilities.class;
    public static final AggregateUtilities EMPTY_ARRAY[] = {};

    /**
     * test two arrays are equivalent
     * @param in1 non-null array
     * @param in2  non-null array
     * @return   true if all members equal
     */
    public static boolean equivalentArrays(byte[] in1, byte[] in2)
    {
        if (in1.length != in2.length)
            return false;
        for (int i = 0; i < in1.length; i++)
        {
            if (in1[i] != in2[i])
                return false;

        }
        return true;
    }
    /**
     * test two arrays are equivalent
     * @param in1 non-null array
     * @param in2  non-null array
     * @return   true if all members equal
     */
    public static boolean equivalentArrays(int[] in1, int[] in2)
    {
        if (in1.length != in2.length)
            return false;
        for (int i = 0; i < in1.length; i++)
        {
            if (in1[i] != in2[i])
                return false;

        }
        return true;
    }

       /**
     * test two arrays are equivalent
     * @param in1 non-null array
     * @param in2  non-null array
     * @return   true if all members equal
     */
    public static boolean equivalentArrays(double[] in1, double[] in2)
    {
        if (in1.length != in2.length)
            return false;
        for (int i = 0; i < in1.length; i++)
        {
            if (in1[i] != in2[i])
                return false;

        }
        return true;
    }

    /**
     * append two arrays
     * @param in1 non-null array
        * @param in2  non-null array
        * @return  array of members oa 1 then members of 2
        */
    public static byte[] appendArrays(byte[] in1, byte[] in2)
    {
        byte[] ret = new byte[in1.length + in2.length];
        System.arraycopy(in1, 0, ret, 0, in1.length);
        System.arraycopy(in2, 0, ret, in1.length, in2.length);
        return ret;
    }
    /**
     * append two arrays
     * @param in1 non-null array
        * @param in2  non-null array
        * @return  array of members oa 1 then members of 2
        */
    public static int[] appendArrays(int[] in1, int[] in2)
    {
        int[] ret = new int[in1.length + in2.length];
        System.arraycopy(in1, 0, ret, 0, in1.length);
        System.arraycopy(in2, 0, ret, in1.length, in2.length);
        return ret;
    }
    /**
      * append two arrays
      * @param in1 non-null array
         * @param in2  non-null array
         * @return  array of members oa 1 then members of 2
         */
     public static char[] appendArrays(char[] in1, char[] in2)
     {
         char[] ret = new char[in1.length + in2.length];
         System.arraycopy(in1, 0, ret, 0, in1.length);
         System.arraycopy(in2, 0, ret, in1.length, in2.length);
         return ret;
     }
    /**
      * append two arrays
      * @param in1 non-null array
         * @param in2  non-null array
         * @return  array of members oa 1 then members of 2
         */
     public static double[] appendArrays(double[] in1, double[] in2)
     {
         double[] ret = new double[in1.length + in2.length];
         System.arraycopy(in1, 0, ret, 0, in1.length);
         System.arraycopy(in2, 0, ret, in1.length, in2.length);
         return ret;
     }
 }
