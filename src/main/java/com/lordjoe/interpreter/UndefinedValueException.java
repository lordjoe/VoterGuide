package com.lordjoe.interpreter;

import java.util.*;

/**
 * com.lordjoe.interpreter.UndefinedValueException
 *
 * @author Steve Lewis
 * @date Jan 19, 2006
 */
public class UndefinedValueException extends InterpreterException
{
    public UndefinedValueException(String name, Map values)
    {
        super(buildMessage(name,values));
    }

    private static String buildMessage(String name, Map values)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("The variable " + name + " is undefined.");
        return sb.toString();
    }
}
