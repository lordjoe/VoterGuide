package com.lordjoe.utilities;

import java.util.*;

/**
 * NestedPropertyHolderBase - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IMutablePropertyHolder which supports adding props
 * @see PropertyHolderBase for base class
 */
public interface INestedPropertyHolder extends INameable,IMutablePropertyHolder {
    /**
     * return the char designating separation i.e. SOAP:PropNasme
     * @return as above
     */
    public char getSeparator();

    /**
     * return a name
     * @return non-null Name
     */
    public String getName();

    public INestedPropertyHolder getSubElement(String in);

    public String[] getDesignatedSubElementNames();


}
