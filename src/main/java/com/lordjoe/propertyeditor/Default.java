package com.lordjoe.propertyeditor;

import java.lang.annotation.*;

/**
 * the value of the property is not allowed to be null
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Default
{
    public abstract String value();

}