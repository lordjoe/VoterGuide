package com.lordjoe.tasks;

import com.lordjoe.utilities.Util;

import java.util.*;
import java.lang.reflect.Array;

/**
 * com.lordjoe.tasks.TaskUtilities
 *   a collection of static methods to support tasks
 * @author Steve Lewis
 * @date Aug 19, 2005
 */
public abstract class TaskUtilities
{
    private TaskUtilities() {}  /// do not build me

    // Packages we want tp see on the stack
    private static String[] gGoodPackages = { "com.lordjoe." };
    // Packages we do not need to see on the stack
    private static String[] gSystemPackages = { "java.", "sun.","junit.","com.intellij."};

    public static void addGoodPackage(String s)
    {
        for (int i = 0; i < gGoodPackages.length; i++) {
            String goodPackage = gGoodPackages[i];
            if(s.equals(goodPackage))
                return;
        }
        gGoodPackages = addToArray(gGoodPackages,s);
    }

    public static void addSystemPackage(String s)
    {
        for (int i = 0; i < gSystemPackages.length; i++) {
            String goodPackage = gSystemPackages[i];
            if(s.equals(goodPackage))
                return;
        }
        gSystemPackages = addToArray(gSystemPackages,s);
    }

    public static StackTraceElement[] filterStackTrace(StackTraceElement[] in)
    {
        int lastGoodElement = findLastGoodElement(in);
        StackTraceElement[] ret = new StackTraceElement[lastGoodElement];
        System.arraycopy(in,0,ret,0,lastGoodElement);
        return ret;
    }
    protected static int findLastGoodElement(StackTraceElement[] in)
     {
         StackTraceElement prev = null;
         for(int i = in.length; i > 0; i--)
         {

             StackTraceElement current = in[i - 1];
             if(!isSystemElement(current,prev))
                 return i;
             prev = current;
         }
         return 0;
     }

    protected static boolean isSystemElement(StackTraceElement in,StackTraceElement prev)
     {
         String test = in.getClassName();
         for(String s : gGoodPackages)
         {
             if(test.startsWith(s))
                return false;
         }
         for(String s : gSystemPackages)
         {
             if(test.startsWith(s))
                return true;
         }
         return false;
     }
    
    protected static boolean isTaskCall(StackTraceElement in,StackTraceElement prev)
      {
          String test = in.getClassName();
          if(!test.equals(Task.class.getName()))
            return false;
          if("call".equals(in.getMethodName()))
             return true;
          return false;
      }

    /**
     * merge two possibly overlapping stack traces
     * @param excTrace
     * @param causeTrace
     * @return
     */
    public static StackTraceElement[] mergeStackTraces(StackTraceElement[] excTrace,StackTraceElement[] causeTrace)
    {
        excTrace = filterStackTrace(excTrace);
        causeTrace = filterStackTrace(causeTrace);
        int m = excTrace.length-1, n = causeTrace.length-1;
        while (m >= 0 && n >=0 && excTrace[m].equals(causeTrace[n])) {
            m--; n--;
        }
        int framesInCommon = excTrace.length - 1 - m;
        List holder = new ArrayList();
        for (int i = framesInCommon; i < causeTrace.length; i++) {
            StackTraceElement st = causeTrace[i];
            holder.add(st);
        }
        for (int i = 0; i < excTrace.length; i++) {
             StackTraceElement st = excTrace[i];
             holder.add(st);
         }

        StackTraceElement[] ret = new StackTraceElement[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    /**
     * adds one object to an array - Yes this is inefficient
     * but usually arrays are accessed much more frequently than they grow
     * @param in - non-null array of unknown type
     * @param addTo - non-null object to add to the array
     * @return - non-null array of input type
     */
    public static <T> T[] addToArray(T[] in, T addTo) {
        Class inClass = in.getClass();
        if (!inClass.isArray())
            throw new IllegalArgumentException("must pass array");
        int InSize = Array.getLength(in); // size of input array
        T[] ret = (T[]) Array.newInstance(inClass.getComponentType(), InSize + 1);
        System.arraycopy(in, 0, ret, 0, InSize);
        Array.set(ret, InSize, addTo);
        //     validateArrayNonNull(ret);
        return (ret);
    }

    /**
      * removes all instances of an object from an array
      * @param in - non-null array of unknown type
      * @param addTo - non-null object to remove from the array
      * @return - non-null array of input type
      */
     public static <T> T[] removeFromToArray(T[] in, T addTo) {
         Class inClass = in.getClass();
         if (!inClass.isArray())
             throw new IllegalArgumentException("must pass array");
          List holder = new ArrayList();
        for (int i = 0; i < in.length; i++) {
            Object o = in[i];
            if(!o.equals(in))
                holder.add(o);
        }
         T[] ret = (T[]) Array.newInstance(inClass.getComponentType(), holder.size());
         holder.toArray(ret);
         //     validateArrayNonNull(ret);
         return (ret);
     }

}
