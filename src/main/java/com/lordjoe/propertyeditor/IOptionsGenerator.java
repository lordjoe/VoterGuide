package com.lordjoe.propertyeditor;

/**
 * com.lordjoe.propertyeditor.IOptionsGenerator
 *
 * @author Steve Lewis
 * @date Jan 1, 2008
 */
public interface IOptionsGenerator<T extends Object,V> {
    public static IOptionsGenerator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IOptionsGenerator.class;

    /**
     * given an input object generate an array of options -
     *  vague as to how this is done or whether the input is needed -
     * Some implementations may use reflection to call a method on the input
     * @param input possibly null input - NOTE some implementations may require this to be non-null
     * @return possibly null array
     */
    public T[] generateOptions(V input);
}
