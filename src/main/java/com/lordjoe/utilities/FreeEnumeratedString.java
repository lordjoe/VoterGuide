package com.lordjoe.utilities;

import java.util.*;

/**
 * This alows enumerations and non-enumerations to share a
 * type
 */
public class FreeEnumeratedString extends EnumeratedString {
    public FreeEnumeratedString(String name, String value) {
        super(name, value);
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



