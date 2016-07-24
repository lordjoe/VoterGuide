package com.lordjoe.utilities;

/**
 * IMutablePropertyHolder - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IPropertyHolderDef which supports adding props
 * @see MutablePropertyHolderBase for base class
 */
public interface IMutablePropertyHolder extends IPropertyHolderDef {
    /**
     * set property value
     * @param PropName non-null test string
     * @param added non-null value
     */
    public void setProperty(String PropName, Object added);

    /**
     * remove a property
     * @param PropName non-null test string
     */
    public void removeProperty(String PropName);

    /**
     * add a multivalued property
     * @param PropName non-null test string
     * @param added non-null value
     */
    public void addMultiProperty(String PropName, Object added);

    /**
     * remove a multivalued property
     * @param PropName non-null test string
     * @param added non-null value
     */
    public void removeMultiProperty(String PropName, Object added);
}
