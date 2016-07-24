package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.ValidityChangeListener
 *
 * @author Steve Lewis
 * @date Nov 28, 2007
 */
public interface ValidityChangeListener
{
    public static ValidityChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ValidityChangeListener.class;

    /**
     * handle a change in validity of the underlying objact
     * @param source non-null souce object
     * @param isValid
      */
    public void onValidityChange(Object source,boolean isValid);
}
