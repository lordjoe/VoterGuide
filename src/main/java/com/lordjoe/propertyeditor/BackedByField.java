package com.lordjoe.propertyeditor;

import java.lang.annotation.*;

/**
 * give the field name of the field behind this property - used
 * when a different field Backsd the data
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BackedByField
{
    public abstract String FieldName();
}