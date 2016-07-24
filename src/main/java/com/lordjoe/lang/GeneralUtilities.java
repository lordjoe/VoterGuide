package com.lordjoe.lang;


import java.util.*;


/**
 * com.lordjoe.lang.GeneralUtilities
 *
 * @author slewis
 * @date Jul 26, 2005
 */
public class GeneralUtilities
{
    public static final Class THIS_CLASS = GeneralUtilities.class;
    public static final GeneralUtilities EMPTY_ARRAY[] = {};
    public static final Random RND = new Random();

    /**
     * wrapper around GeneralUtilities.printString
     * allows interception
     * @param s non-null String
     */
    public static void  showString(String s)
    {
        System.out.print(s);
    }
    /**
     * wrapper around GeneralUtilities.printString
     * allows interception
     */
    public static void  printString()
    {
        GeneralUtilities.printString("");
    }
    /**
     * wrapper around GeneralUtilities.printString
     * allows interception
     * @param s non-null String
     */
    public static void  printString(String s)
    {
        if(s.startsWith("Done"))
            ObjectOps.breakHere();
        System.out.println(s);
    }

    /**
     * wrapper around GeneralUtilities.printString
     * allows interception
     * @param s non-null String
     */
    public static void  printString(Object o)
    {
        GeneralUtilities.printString(o.toString());
    }

    /**
     * wrapper around GeneralUtilities.printString
     * allows interception
     * @param s non-null String
     */
    public static void  printString(int s)
    {
        printString(Integer.toString(s));
    }

    /**
     * tests two byte arrays for equivalence
     *
     * @param b1 non-null byte array
     * @param b2 non-null byte array
     * @return true if equivalent
     */
    public static boolean equivalentBytes(byte[] b1, byte[] b2)
    {
        if (b1.length != b2.length)
            return false;
        for (int i = 0; i < b1.length; i++)
        {
            if (b1[i] != b2[i])
                return false;

        }
        return true;
    }

    /**
     * return the maximum binary number with numberBits
     * bits
     * @param numberBits
     * @return
     */
    public static int maximimBinaryValue(int numberBits)
    {
        if(numberBits > 30)
            throw new IllegalArgumentException("numberBits too large " + numberBits);
        return (1 << numberBits) - 1;
    }

    public static long randomLong()
    {
        return RND.nextLong();
    }

    /**
      * tests two byte arrays for equivalence
      *
      * @param b1 non-null IEquivalent array
      * @param b2 non-null IEquivalent array
      * @return true if equivalent
      */
     public static boolean equivalentArrays(IEquivalent[] b1, IEquivalent[] b2)
     {
         if (b1.length != b2.length)
             return false;
         for (int i = 0; i < b1.length; i++)
         {
             if (b1[i].equivalent(b2[i]))
                 return false;

         }
         return true;
     }


    /**
      * helper to compare two arrays for equality - handles tha case where
      * one or both are null
      * @param test1 possibly null test object
      * @param test2 possibly null test object
      * @return true if test1.equals(test2) or both are null
      */
     public static boolean equivalentCollectionPair(IEquivalent[] test1,IEquivalent[] test2)
       {
           if(test1 == null)
               return test2 == null;
           if(test2 == null)
               return false;
          if(test1.length != test2.length)
             return false;
           for (int i = 0; i < test1.length; i++)
           {
               IEquivalent t1 = test1[i];
               IEquivalent t2 = test2[i];
               if(t1 == t2)
                    continue;
                if(!t1.equivalent(t2)) {
                    t1.equivalent(t2); // retry to debug
                    return false;
                }
           }
           return true;
       }

    /**
     * helper to compare two object for equa;lite - handles tha case where
     * one or both are null
     * @param test1 possibly null test object
     * @param test2 possibly null test object
     * @return true if test1.equals(test2) or both are null
     */
    public static boolean equivalentObject(Object test1,Object test2)
    {
        if(test1 == null)
            return test2 == null;
        if(test2 == null)
            return false;
        if(test1 instanceof IEquivalent && test2 instanceof IEquivalent)
            return ((IEquivalent)test1).equivalent(test2);
        return test1.equals(test2);
    }

    public static String getShortClassName(Object o){
        String classname = o.getClass().toString();
        int index = classname.lastIndexOf(".");
        return classname.substring(index+1, classname.length());
    }

}
