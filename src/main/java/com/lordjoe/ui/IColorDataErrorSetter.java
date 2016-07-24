package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IColorDataErrorSetter
 * this simply sets errors in a color data array
 * always implementd as IArrayColorDataWithErrors
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IColorDataErrorSetter
{
    public static final Class THIS_CLASS = IColorDataErrorSetter.class;
    public static final IColorDataErrorSetter EMPTY_ARRAY[] = {};

    /**
     * clear errors
     */
    public void clearErrors();


    /**
     * set the given location as having an error
     * @param location
     */
    public void setError(int location);

    /**
     * true if the location has an error
     * @param location
     * @return
     */
    public boolean hasError(int location);


}