package com.lordjoe.propertyeditor;

import java.lang.annotation.*;

/**
 * calling this method - applied to an add to a collection
 * gives a  method on the current class returing options
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionMethod
{
    public abstract String MethodName();
}