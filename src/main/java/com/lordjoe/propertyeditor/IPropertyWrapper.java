package com.lordjoe.propertyeditor;

import java.beans.*;

/**
 * com.lordjoe.propertyeditor.IPropertyWrapper
 *   this represents a general way to handle a property
 *   in a UI component
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public interface IPropertyWrapper<T> extends PropertyChangeListener,IGenericWrapper
{
    public static IPropertyWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IPropertyWrapper.class;

    /**
     * return the class owning the property
     * @return
     */
     public Class getOwningClass();

    /**
     * return the name of the property - usually as a java bean name
     * @return non-null String
     */
    public String getPropertyName();


    /**
     * if true the property cannor be non-null
     * @return
     */
    public boolean isRequired();

    /**
     * if true the property cannot be changed
     * @return
     */
    public boolean isReadOnly();

    /**
     * true of the test value is a legal value
     * @param test possibly null test value
     * @return as above
     */
    public boolean isAcceptable(T test);

    /**
     * true of a the value is a  collection of
     * values from th eoptions - if options is null
     * this should return false
     * @return  as above
     */
    public boolean isMultipleValuesSupported( );

    /**
     *  special set method to set multiple items - only used if
     *     isMultipleValuesSupported is true
     * @param items non-null array of items
     * @throws UnsupportedOperationException unless isMultipleValuesSupported is true
     */
    public void setMultipleValues(T[] items )  throws UnsupportedOperationException;

    /**
     * return the values as an array
     * @return non-null array - will be of length > 1 only of
     *    isMultipleValuesSupported is true
     * @throws UnsupportedOperationException unless isMultipleValuesSupported is true
     * in real life it is ok to implement this method to return arrays of length
     * 0 or 1 for most objects
     */

    public T[] getMultipleValues( )  throws UnsupportedOperationException;

    /**
     * return the value represented by the editor
     * @return
     */
    public T getCurrentValue();

    /**
     * set the current value of the editor
     * @param value
     */
    public void setCurrentValue(T value) ;

    /**
     * if non-null these are the only acceptabll values
     *  for the property
     * @return
     */
    public T[] getOptions();

    /**
     * if implemented converts a string to a legal value
     * @param s possibly null or empty string - null or empty shoud return null
     * @return possibly null object
     * @throws UnsupportedOperationException this may not be implemented
     * @throws IllegalArgumentException the String cannot be converted
     *
     */
    public T fromString(String s) throws UnsupportedOperationException,
            IllegalArgumentException;


}
