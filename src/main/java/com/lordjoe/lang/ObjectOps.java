/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.lang;


import com.lordjoe.exceptions.*;

import java.lang.reflect.*;
import java.util.*;


/**
 * Title:        Miscellaneous
 * Description:
 * Company:      Lordjoe

 */


/**
 ***************************************************************************************************
 *                                                                                              <p>
 * <!-- ObjectOps -->
 *                                                                                              <p>
 * This class defines static methods that perform various Object related operations.
 *                                                                                              <p>
 ***************************************************************************************************
 */
public class ObjectOps
{
    private static final String LINE_SEP = System.getProperty("line.separator");

    public static final Object[] EMPTY_OBJECT_ARRAY = {};
    public static final Class[] EMPTY_CLASS_ARRAY = {};
    public static final String[] EMPTY_STRING_ARRAY = {};

    /**
     * This class should not be instantiated.
     */
    private ObjectOps()
    {
    }

    /**
     * useful nonsense operation
     */
    public static void breakHere()
    {

    }

    /**
     * throw NonNullArgumentRequiredException if value is null
     * @param argument non-null argument name
     * @param value possibly null value
     * @throws NonNullArgumentRequiredException  if value is null
     */
    public static void requireNonNull(String argument,Object value)  throws  NonNullArgumentRequiredException
    {
        if(value == null)
            throw new NonNullArgumentRequiredException(argument);
    }

    /**
     * retutn the number of true items in a boolean array
     *
     * @param items non-null array
     * @return as above
     */
    public static int numberTrue(boolean[] items)
    {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            boolean item = items[i];
            if (item)
                count++;
        }
        return count;
    }


    /**
     * or two equal lenngth boolean arrays
     *
     * @param items  non-null array
     * @param items2 non-null array
     * @return non-null array of ored values
     */
    public static boolean[] performOr(boolean[] items, boolean[] items2)
    {
        int len = items.length;
        if (items2.length != len)
            throw new IllegalArgumentException("Length 1 " + len +
                    " != Length 2" + items2.length);
        boolean[] ret = new boolean[len];
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            ret[i] = items[i] | items2[i];
        }
        return ret;
    }


    /**
     * and two equal lenngth boolean arrays
     *
     * @param items  non-null array
     * @param items2 non-null array
     * @return non-null array of ored values
     */
    public static boolean[] performAnd(boolean[] items, boolean[] items2)
    {
        int len = items.length;
        if (items2.length != len)
            throw new IllegalArgumentException("Length 1 " + len +
                    " != Length 2" + items2.length);
        boolean[] ret = new boolean[len];
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            ret[i] = items[i] & items2[i];
        }
        return ret;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- nullSync() -->
     *                                                                                          <p>
     * This is a helper method for equalsNullSafe().  
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean nullSync(Object objA, Object objB)
    {
        if (objA == null && objB != null) {
            return false;
        }
        else if (objA != null && objB == null) {
            return false;
        }

        return true;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- equalsNullSafe() -->
     *                                                                                          <p>
     * This method compares two Objects in a way that allows for nulls.
     *                                                                                          <p>
     ***********************************************************************************************
      */
     public static boolean equalsNullSafe(Object objA, Object objB)
     {
         if (!nullSync(objA, objB)) {
             return false;
         }

         if (objA == null) {
             return true;
         }
         else {
             return objA.equals(objB);
         }
    }
    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- equivalentNullSafe() -->
     *                                                                                          <p>
     * This method compares two Equivalents in a way that allows for nulls.
     *                                                                                          <p>
     ***********************************************************************************************
     */
     public static boolean equivalentNullSafe(IEquivalent objA, IEquivalent objB)
     {
         if (!nullSync(objA, objB)) {
             return false;
         }

         if (objA == null) {
             return true;
         }
         else {
             return objA.equivalent(objB);
         }
    }

    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- equalsNullSafe() -->
     *                                                                                          <p>
     * This method compares two Objects in a way that allows for nulls.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static int compareNullSafe(Comparable objA, Comparable objB)
    {

          if (objB == null) {
               if(objA == null) {
                   return 0;
               }
              else {
                   return 1;
               }
           }
          if (objA == null) {
               return -1;
           }
            else {
              return objA.compareTo(objB);
          }
     }

    /**
     * return true if o1 and o2 hold the same items
     *
     * @param o1 possibly null array
     * @param o2 possibly null array
     * @return true if o1 == 02 == null or contents of o1 equals contents o2
     */
    public static boolean equivalentArrays(Object[] o1, Object[] o2)
    {
        if (o1 == null)
            return o2 == null;
        if (o2 == null)
            return false;
        if (o1.length != o2.length)
            return false;
        Set s1 = new HashSet(Arrays.asList(o1));
        Set s2 = new HashSet(Arrays.asList(o2));
        if (s1.size() != s2.size())
            return false;
        s1.removeAll(s2);
        return s1.isEmpty();
    }

    /**
     * return true if o1 and o2 hold the same items
     *
     * @param o1 possibly null array
     * @param o2 possibly null array
     * @return true if o1 == 02 == null or contents of o1 equals contents o2
     */
    public static boolean equivalentArrays(byte[] o1, byte[] o2)
    {
        if (o1 == null)
            return o2 == null;
        if (o2 == null)
            return false;
        if (o1.length != o2.length)
            return false;
        for (int i = 0; i < o1.length; i++) {
            byte b = o1[i];
            if (o2[i] != b)
                return false;
        }
        return true;
    }

    /**
     * adds one object to an array - Yes this is inefficient
     * but usually arrays are accessed much more frequently than they grow
     *
     * @param in    - non-null array of unknown type
     * @param addTo - non-null object to add to the array
     * @return - non-null array of input type
     */
    public static Object[] addToArray(Object[] in, Object addTo)
    {
        Class inClass = in.getClass();
        if (!inClass.isArray())
            throw new IllegalArgumentException("must pass array");
        int InSize = Array.getLength(in); // size of input array
        Object[] ret = (Object[]) Array.newInstance(inClass.getComponentType(), InSize + 1);
        System.arraycopy(in, 0, ret, 0, InSize);
        Array.set(ret, InSize, addTo);
        //     validateArrayNonNull(ret);
        return (ret);
    }

    /**
     * reomve one object from an array - Yes this is inefficient
     * but usually arrays are accessed much more frequently than they grow
     *
     * @param in         - non-null array of unknown type
     * @param removeFrom - non-null object to removeFrom the array
     * @return - non-null array of input type
     */
    public static Object removeFromArray(Object in, Object removeFrom)
    {
        Class inClass = in.getClass();
        if (!inClass.isArray())
            throw new IllegalArgumentException("must pass array");
        int InSize = Array.getLength(in); // size of input array
        int index = -1;
        for (int i = 0; i < InSize; i++) {
            if (removeFrom.equals(Array.get(in, i))) {
                index = i;
                break;
            }
        }
        if (index == -1)
            return (in); // not found

        Object ret = Array.newInstance(inClass.getComponentType(), InSize - 1);
        if (index == 0) {
            System.arraycopy(in, 1, ret, 0, InSize - 1);
            //      validateArrayNonNull(ret);
            return (ret);
        }
        if (index == InSize - 1) {
            System.arraycopy(in, 0, ret, 0, InSize - 1);
            //    validateArrayNonNull(ret);
            return (ret);
        }
        System.arraycopy(in, 0, ret, 0, index);
        System.arraycopy(in, index + 1, ret, index, InSize - 1 - index);
        //   validateArrayNonNull(ret);
        return (ret);

    }

    /**
     * using reflection call methodName in  className
     * Usually this will be wrapped in a wrapper which will throw the proper exceptions
     *
     * @param className  non-null name of an existing class
     * @param methodName non-null name of an existing static method
     * @param classes    possibly null argument class list
     * @param args       possibly null arguments list
     * @return whever is returned
     * @throws InvocationTargetException
     */
    public static Object safeMethodCall(String className, String methodName, Class[] classes,
                                        Object[] args) throws InvocationTargetException
    {
        return safeMethodCall(null,className,methodName,classes,args);
    }
    /**
     * using reflection call methodName in  className
     * Usually this will be wrapped in a wrapper which will throw the proper exceptions
     *
     * @param className  non-null name of an existing class
     * @param methodName non-null name of an existing  method
     * @param classes    possibly null argument class list
     * @param args       possibly null arguments list
     * @return whever is returned
     * @throws InvocationTargetException
     */
    public static Object safeMethodCall(Object target,String className, String methodName, Class[] classes,
                                        Object[] args) throws InvocationTargetException
    {
        try {
            Class cls = Class.forName(className);
            Method met = cls.getMethod(methodName, classes);
            Object ret = met.invoke(target, args);
            return ret;
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                    "Cannot find class " + className);
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalStateException(
                    "Cannot find method " + methodName + " in class " + className +
                            " with arguments " + makeClassList(classes));
        }
        catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                    "Cannot find method " + methodName + " in class " + className +
                            " with arguments " + makeClassList(classes));
        }
        catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException)
                throw (RuntimeException) cause;

            throw ex;
        }
    }

    public static String makeClassList(Class[] classes)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < classes.length; i++) {
            Class aClass = classes[i];
            if (sb.length() > 0)
                sb.append(",");
            sb.append(aClass.getName());
        }
        return sb.toString();
    }

    /**
     * order on identityHashCode
     * @param o1 non-null object
     * @param o2  non-null object
     * @return   as above - last resort os a compare function
     */
    public static int compareObjects(Object o1,Object o2)
    {
        if(o1 == o2)
            return 0;
        if(System.identityHashCode(o1) < System.identityHashCode(o2))
            return 1;
        else
            return -1;
    }

    /**
     * make a clone of an array of arbitaray type
     * @param in non-null array
     * @return non-null array of the same type and contents
     */
    public static Object cloneArray(Object in)
    {
        Class inClass = in.getClass();
        if (!inClass.isArray())
            throw new IllegalArgumentException("must pass array");
        int InSize = Array.getLength(in); // size of input array
        Object ret =  Array.newInstance(inClass.getComponentType(), InSize);
        if(InSize > 0)
            System.arraycopy(in, 0, ret, 0, InSize);
        return (ret);

    }

    /**
      * copy an int array into a short array
      * @param data  non-nill array
      * @return    non-nill array os same size and data
      */
     public static short[] cloneAsShort(int[] data)
     {
         short[] iData = new short[data.length];
         for (int i = 0; i < iData.length; i++) {
             iData[i] = (short)data[i];
         }
         return iData;
     }

    /**
      * copy an int array into a short array
      * @param data  non-nill array
      * @return    non-nill array os same size and data
      */
     public static int[] cloneAsInt(short[] data)
     {
         int[] iData = new int[data.length];
         for (int i = 0; i < iData.length; i++) {
             iData[i] = data[i];
         }
         return iData;
     }

    

    /**
     * find the medial value of an array
     * @param data  non-null data
     * @return  median
     */
    public static int findMedian(int[] data)
    {
        int length = data.length;
        if(length == 0)
            return 0; // Perhaps an exception is in order
        if(length == 1)
            return data[0];
        int[] sorted = (int[])cloneArray(data);
         Arrays.sort(sorted);
        boolean isOdd = (length % 2) == 1;
        int halfLength =  length / 2;
        if(isOdd)
            return sorted[halfLength + 1];
        else
            return (sorted[halfLength ] + sorted[halfLength + 1]) / 2;

    }

    /**
     * find the medial value of an array
     * @param data  non-null data
     * @return  median
     */
    public static int findMedian(short[] data)
    {
        int length = data.length;
        if(length == 0)
            return 0; // Perhaps an exception is in order
        if(length == 1)
            return data[0];
        int[] sorted = (int[])cloneArray(data);
         Arrays.sort(sorted);
        boolean isOdd = (length % 2) == 1;
        int halfLength =  length / 2;
        if(isOdd)
            return sorted[halfLength + 1];
        else
            return (sorted[halfLength ] + sorted[halfLength + 1]) / 2;

    }



    /**
     * find the medial value of an array
     * @param data  non-null data
     * @return  median
     */
    public static double findMedian(double[] data)
    {
        int length = data.length;
        if(length == 0)
            return 0; // Perhaps an exception is in order
        if(length == 1)
            return data[0];
        double[] sorted = (double[])cloneArray(data);
         Arrays.sort(sorted);
        boolean isOdd = (length % 2) == 1;
        int halfLength =  length / 2;
        if(isOdd)
            return sorted[halfLength + 1];
        else
            return (sorted[halfLength ] + sorted[halfLength + 1]) / 2;

    }

    /**
      * find the medial value of an array
      * @param data  non-null data
      * @return  median
      */
     public static int[] flatten(int[][] data)
     {
         int position = 0;
         int totalLength = getTotalLength(data);
         int[] ret = new int[totalLength];
         for (int i = 0; i < data.length; i++) {
             int[] ints = data[i];
             int len = ints.length;
             System.arraycopy(ints,0,ret,position,len);
             position += len;
         }

         if(position != totalLength)
            throw new IllegalStateException("problem with array flatten");
         return ret;
     }

        /**
      * find the medial value of an array
      * @param data  non-null data
      * @return  median
      */
     public static short[] flatten(short[][] data)
     {
         int position = 0;
         int totalLength = getTotalLength(data);
         short[] ret = new short[totalLength];
         for (int i = 0; i < data.length; i++) {
             short[] ints = data[i];
             int len = ints.length;
             System.arraycopy(ints,0,ret,position,len);
             position += len;
         }

         if(position != totalLength)
            throw new IllegalStateException("problem with array flatten");
         return ret;
     }

    /**
     * return the total length of a dual array
     * @param data non-null array of non-null arrays
     * @return total members all elements
     */
    public static <T> int getTotalLength(T[][] data)
    {
        int totalLength = 0;
        for (int i = 0; i < data.length; i++) {
            T[] ints = data[i];
            if(ints != null)
                totalLength += ints.length;
            else
                ObjectOps.breakHere();
        }
        return totalLength;
    }
    /**
     * return the total length of a dual array
     * @param data non-null array of non-null arrays
     * @return total members all elements
     */
    public static int getTotalLength(int[][] data)
    {
        int totalLength = 0;
        for (int i = 0; i < data.length; i++) {
            int[] ints = data[i];
            if(ints != null)
                totalLength += ints.length;
            else
                ObjectOps.breakHere();
        }
        return totalLength;
    }
    /**
     * return the total length of a dual array
     * @param data non-null array of non-null arrays
     * @return total members all elements
     */
    public static  int getTotalLength(short[][] data)
    {
        int totalLength = 0;
        for (int i = 0; i < data.length; i++) {
            short[] ints = data[i];
            if(ints != null)
                totalLength += ints.length;
            else
                ObjectOps.breakHere();
        }
        return totalLength;
    }


} // end ObjectOps
