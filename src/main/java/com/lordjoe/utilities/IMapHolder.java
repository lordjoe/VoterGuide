package com.lordjoe.utilities;

/**
 * IMapHolder an object whoes properties are held in an internal map
 * @author Steve Lewis
 */
public interface IMapHolder {
    /**
     * return the collection of supported Properties
     * @return non-null array
     */
    public String[] getPropertyNames();

    /**
     * set a property
     * @param PropertyName non-null name of the property
     * @param possibly null Value setting to null clears
     */
    public void setProperty(String PropertyName, Object Value);

    /**
     * set a property
     * @param PropertyName non-null name of the property
     * @return possibly null Value
     */
    public Object getProperty(String PropertyName);


}
