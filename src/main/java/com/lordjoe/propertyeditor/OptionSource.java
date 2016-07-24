package com.lordjoe.propertyeditor;

import java.lang.annotation.*;

/**
 * calling this method - applied to an add to a collection
 * gives a static method returing options
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionSource
{
    public Class SourceClass();
    public String MethodName();
}