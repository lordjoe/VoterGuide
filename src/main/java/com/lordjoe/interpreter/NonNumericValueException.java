package com.lordjoe.interpreter;

/**
 * com.lordjoe.interpreter.UndefinedValueException
 *
 * @author Steve Lewis
 * @date Jan 19, 2006
 */
public class NonNumericValueException extends InterpreterException
{
    public NonNumericValueException(Object value,String name)
    {
        super(buildMessage(value,name));
    }

    private static String buildMessage(Object value,String name)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("The variable " + name + " is not numeric but of class " +
            value.getClass().getName());
        return sb.toString();
    }
}
