package com.lordjoe.utilities;

/**
 * IPropertyHolder - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IMutablePropertyHolder which supports adding props
 * @see PropertyHolderBase for base class
 */
public interface IPropertyHolderDef {
    /**
     * return true if setting properties is allowed
     * @return as above
     */
    public boolean isMutable();

    /**
     * return true if the property exists
     * @param PropName non-null test string
     * @return as above
     */
    public boolean hasProperty(String PropName);

    /**
     * property value or null if none exists
     * @param PropName non-null test string
     * @return possibly null value
     */
    public Object getProperty(String PropName);

    /**
     * return nultiple values for a single property
     * @param PropName non-null test string
     * @return non-null array of values
     */
    public Object[] getPropertyValues(String PropName);

    /**
     * return all property names
     * @return as above
     */
    public String[] getPropertyNames();

    /**
     * Returns the value of an  property or the default object if null
     * @param propertyName a non-null <code>String</code> that is the property name
     * @param defaultProp - a  value to return if the property is null.
     * @return a <code>object</code> that is the property value or defaultProp if there is no such property
     */
    public Object getProperty(String propertyName, Object defaultProp);
}
