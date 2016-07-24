package com.lordjoe.general;

import java.util.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.general.ArrayUtilities
 *
 * @author Steve Lewis
 * @date Nov 23, 2005
 */
public abstract class ArrayUtilities
{
    private ArrayUtilities() {}
    public static final Random RND = new Random();

    /**
     * choose a random element from an array
     * @param items non-null array
     * @return possibly null value - null only if the array is empty
     */
    public static <T> T randomElement(T[] items)
    {
       switch(items.length) {
           case 0 :
               return null;
           case 1 :
               return items[0];
           default:
               return items[RND.nextInt(items.length)];
       }
    }
    /**
     * add all added arrays to items
     * returned type is the first array type
     * @param added
     */
     public static <T> T[] buildMergedArrays(T[]... added)
     {
         if(added.length == 0)
            throw new IllegalArgumentException("Needs at least one argument");
         T[] examplar = added[0];
         int length = 0;
         for (int i = 0; i < added.length; i++) {
             T[] item = added[i];
             length += item.length;
          }
         T[] ret = (T[])Array.newInstance(examplar.getClass().getComponentType(),
                  length);
         mergeArrays(ret,added);
         return ret;
      }

    /**
     * add all added arrays to items
     * @param items
     * @param added
     */
     public static <T> void mergeArrays(T[] items,T[]... added)
     {
         int index = 0;
         for (int i = 0; i < added.length; i++) {
             T[] item = added[i];
             System.arraycopy(item,0,items,index,item.length);
             index += item.length;
         }
     }

    /**
     * copy an array
     * @param items non-null array
     * @return non-null copy
     */
     public static <T> T[] cloneToArray(T[] items)
     {
         T[] ret = (T[])Array.newInstance(items.getClass().getComponentType(),
                 items.length);
         System.arraycopy(items,0,ret,0,items.length);
         return ret;
     }


    /**
     * add one item to the end of an array
     * @param items non-null array
     * @param added non-null item added
     * @return non-null merged array
     */
     public static <T> T[] addToArray(T[] items,T added)
     {
         T[] ret = (T[])Array.newInstance(items.getClass().getComponentType(),
                 items.length + 1);
         System.arraycopy(items,0,ret,0,items.length);
         ret[items.length] = added;
         return ret;
     }


    /**
      * add one item to the end of an array
      * @param items non-null array
      * @param removed non-null item removed
      * @return non-null merged array
      */
      public static <T> T[] removeFromArray(T[] items,T removed)
      {
          int index = firstIndex(items,removed);
          if(index == -1)
            return items; // nothine to remove
          T[] ret = (T[])Array.newInstance(items.getClass().getComponentType(),
                  items.length - 1);
          if(index == 0) {
              System.arraycopy(items,1,ret,0,ret.length);

          }
          else {
              System.arraycopy(items,0,ret,0,index);
              System.arraycopy(items,index + 1,ret,index,ret.length - index);
           }
          return ret;
      }

    /**
      * remove all instances of removed from items
      * @param items non-null array
      * @param removed non-null item removed
      * @return non-null updated array
      */
      public static <T> T[] removeAllFromArray(T[] items,T removed)
      {
          int index = firstIndex(items,removed);
          if(index == -1)
            return items; // nothing to remove
          List<T> holder = new ArrayList<T>();
          for (int i = 0; i < items.length; i++) {
              T item = items[i];
              if(!item.equals(removed)) {
                  holder.add(item);
              }
          }
          T[] ret = (T[])Array.newInstance(items.getClass().getComponentType(),
                  holder.size());
          holder.toArray(ret);
          return ret;
      }

    /**
     *  fine the first occurrance of test in items
     * @param items non-null array
     * @param test non-null item to test
     * @return -1 if test not in items otherwise the index of test
     */
      public static <T> int firstIndex(T[] items,T test)
      {
          for (int i = 0; i < items.length; i++) {
              T item = items[i];
              if(test.equals(item))
                return i;
          }
          return -1;
      }
}
