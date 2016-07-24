package com.lordjoe.runner;

import com.lordjoe.exceptions.*;
import com.lordjoe.utilities.*;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.runner.ObjectUtilities
 *
 * @author Steve Lewis
 * @date May 30, 2008
 */

public class ObjectUtilities
{
    public static ObjectUtilities[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ObjectUtilities.class;
    public static final Random RND = new Random();

    /**
     * return a resouce as a string
     * @param resource non-null existing resource
     * @param clz non-null relative class
     * @return non-null String is resource present
     */
    public static String getResourceAsString(String resource,Class clz)
    {
        InputStream is = clz.getResourceAsStream(resource);
        if(is == null)
               return null;
             return FileUtilities.readInFile(is);
     }
    /**
     * choose a random element
     *
     * @param items list
     * @return some element - null
     */
    public static <T> T getRandomElement(T[] items)
    {
        if (items == null)
            return null;
        int len = items.length;
        switch (len) {
            case 0:
                return null;
            case 1:
                return items[0];
            default:
                return items[RND.nextInt(len)];
        }
    }

    /**
     * add to an existing array
     * @param items  non-null existing array
     * @param added object suitable to att to items
     * @return new array - same type as items with added at the end
     */
    public static <T> T[] appendToArray(T[] items,T added)
    {
        Class inClass = items.getClass();
        if (!inClass.isArray())
            throw new IllegalArgumentException("must pass array");
        int InSize = Array.getLength(items); // size of input array
        Object ret = Array.newInstance(inClass.getComponentType(), InSize + 1);
        System.arraycopy(items, 0, ret, 0, InSize);
        //   validateArrayNonNull(ret);
        Array.set(ret,InSize,added);
        return ((T[])ret);

    }

    /**
     * just a note to put in a break point
     */
    public static void breakHere()
    {

    }

    public static final Class[] STRING_ARGS = { String.class };

    public static Object buildFromString(String value,String clsName)
    {
        try {
            Class<?> cls = Class.forName(clsName);
            return buildFromString(value, cls);
        }
        catch (ClassNotFoundException e) {
            throw new WrappingException(e);
        }
    }
    public static Object buildFromString(String value,Class cls)
    {
        try {
            Constructor constructor = cls.getConstructor(String.class);
            return constructor.newInstance(value);
        }
        catch (NoSuchMethodException e) {
            throw new WrappingException(e);
        }
        catch (InstantiationException e) {
            throw new WrappingException(e);
        }
        catch (IllegalAccessException e) {
            throw new WrappingException(e);
        }
        catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }
    }

}
