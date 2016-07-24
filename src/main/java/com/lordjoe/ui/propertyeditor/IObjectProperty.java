package com.lordjoe.ui.propertyeditor;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.ui.propertyeditor.IObjectProperty
 * Describes an Object's property - typically used to allow
 * construction of an editor
 * @author slewis
 * @date Apr 21, 2005
 */
public interface IObjectProperty extends INamedObject
{
    public static final Class THIS_CLASS = IObjectProperty.class;
    public static final IObjectProperty EMPTY_ARRAY[] = {};


    /**
     * property readable name
     * @return non-null name a user can read
     */
    public String getReadableName();

   /**
    * set a readable name
    * @param newName non-null name
    */
    public void setReadableName(String newName);


    /**
      * get a readable description
      * @param as above
      */
     public String getDescription();

    /**
     * property type
     * @return non-null class
     */
    public Class getType();

    /**
     * if non-null this will be the default value
     * @return possibly null object
     */
    public Object getDefaultValue();

    /**
     * if non-null this will ne a set of legal values
     * @return possibly null collection
     */
    public Object[] getOptions();

    /**
     * for numbers only this is the units
     * @return possibly null String
     */
    public String getUnits();

    /**
     * can null be used as a value
     * @return as above
     */
    public boolean isNullAllowed();


}