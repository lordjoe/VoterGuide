package com.lordjoe.interpreter;

/**
 * com.lordjoe.interpreter.UndefinedValueException
 *
 * @author Steve Lewis
 * @date Jan 19, 2006
 */
public class BadExpressionException extends InterpreterException
{
    public BadExpressionException(String exp)
    {
        super("There is a problem with the expression \'" +
          exp + "\'" );
    }
    public BadExpressionException(String exp,Exception ex)
    {
        super("There is a problem with the expression \'" +
          exp + "\' namely " + ex.getMessage());
    }

}
