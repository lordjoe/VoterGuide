package com.lordjoe.propertyeditor;

import java.lang.annotation.*;

/**
 * the value of the property is not be changed
 * Usually this is a hint to editors that even if there is a setNethod it
 * should not be called
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly
{
}