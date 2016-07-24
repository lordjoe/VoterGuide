package com.lordjoe.utilities;

import java.util.*;

/**
 * This alows enumerations and non-enumerations to share a
 * type
 */
public class FreeEnumeratedInt extends EnumeratedInt {
    public FreeEnumeratedInt(String name, Integer value) {
        super(name, value);
    }

    public FreeEnumeratedInt(String name, int value) {
        this(name, new Integer(value));
    }

    protected void rememberName() {
    }

    protected void rememberValue() {
    }

    protected Map getOptionMap() {
        throw new UnsupportedOperationException("Free enumerations have no maps");
    }

    protected Map getValueMap() {
        throw new UnsupportedOperationException("Free enumerations have no maps");
    }

}



