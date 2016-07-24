package com.lordjoe.exceptions;

/**
 * com.lordjoe.exceptions.NonNullArgumentRequiredException
 *
 * @author Steve Lewis
 * @date Apr 24, 2006
 */
public class NonNullArgumentRequiredException extends IllegalArgumentException
{
    public final static NonNullArgumentRequiredException[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = NonNullArgumentRequiredException.class;

    public NonNullArgumentRequiredException(String argName) 
    {
        super("The argument " + argName + " is trqyured to be non-null");
    }
}
