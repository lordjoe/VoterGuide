package com.lordjoe.propertyeditor;

/**
 * com.lordjoe.propertyeditor.IllegalValueException
 *  exception thrown when a value is set illegally
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class IllegalValueException extends RuntimeException
{
    public static IllegalValueException[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IllegalValueException.class;

    public static String buildMessage(String propertyName,Object value)
    {
        return "The value of the property \'" + propertyName +
                " of " + value + " is illegal";
    }
    public static String buildMessage(String propertyName,Object value,String reason)
    {
        return buildMessage( propertyName, value) +
                " because " + reason;
    }

    public IllegalValueException(String propertyName,Object value)
    {
         super(buildMessage(propertyName,value));
    }

    public IllegalValueException(String propertyName,Object value,String reason)
    {
         super(buildMessage(propertyName,value));
    }
}
