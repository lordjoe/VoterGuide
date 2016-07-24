package com.lordjoe.propertyeditor;

/**
 * com.lordjoe.propertyeditor.IPropertyStringConverter
 *  Object to override standard string handling
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IPropertyStringConverter<T>
{
    public static IPropertyStringConverter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IPropertyStringConverter.class;

    /**
     *
     * @param target
     * @param value
     * @return
     */
    public T convertString(Object target,String value);
}
