package com.lordjoe.propertyeditor;

/**
 * com.lordjoe.propertyeditor.IGenericWrapper
 *    interface allowing property warppers and collection
 * @author Steve Lewis
 * @date Dec 17, 2007
 */
public interface IGenericWrapper {
    public static IGenericWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IGenericWrapper.class;


    /**
     * return the class of the property
     * @return
     */
    public Class getPropertyClass();

    /**
     * return the name of the property as seen by the user -
     * @return non-null String
     */
    public String getDisplayName();


}
