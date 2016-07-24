package com.lordjoe.interpreter;
/*
import groovy.lang.*;
import org.codehaus.groovy.control.*;
*/

import java.util.*;


/**
 * com.lordjoe.interpreter.Interpreter
 *
 * @author Steve Lewis
 * @date Jan 19, 2006
 */
public abstract class Interpreter
{

    public static double getValue(String name, Map values)
    {
        Object item = values.get(name);
        if (item == null)
            throw new UndefinedValueException(name, values);
        if (item instanceof Number)
            return ((Number) item).doubleValue();
        else
            throw new NonNumericValueException(item, name);

    }


    /**
     * evaluate an expression with parameters given by values
     * @param expression  non-null expression
     * @param values map of values
     * @return  string value
     */
    public static String eval(String expression, Map values)
    {
//        if(expression.startsWith("maxUsedSyringeVolume") )  {
//            Object o1 = values.get("argonFlushLineACNBase");
//            Object o2 = values.get("maxUsedSyringeVolume");
//      //      Object o3 = values.get("oneChamberOnePumpReagentVolume");
//            o1 = null; // break here;
//        }
     /*
        Binding binding = buildBinding(values);
        GroovyShell shell = new GroovyShell(binding);
        try {
            String returnExpression = expression;
            if(expression.indexOf("return") == -1)
                 returnExpression = "return(" + expression + ")";
            Object answer = shell.evaluate(returnExpression);
            return answer.toString();
        }
        catch (CompilationFailedException ex) {
            throw new BadExpressionException(expression,ex);
        }
        catch (MissingPropertyException ex) {
            throw new BadExpressionException(expression,ex);
        }
        catch (RuntimeException ex) {
            throw new BadExpressionException(expression,ex);
        }
  */
     throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    /**
     * evaluate an expression with parameters given by values
     * @param expression  non-null expression
     * @param values map of values
     * @return double value
     */
    public static double evaluateNumericExpression(String expression, Map values)
    {
        String answer = eval(expression, values);
        return Double.parseDouble(answer);
    }

//    /**
//     * build a goovy binding having the listed values
//     * @param values non-null map<String,Object>
//     * @return non-null binding with values set
//     */
//    protected static Binding buildBinding(Map values)
//    {
//        Binding binding = new Binding();
//        for (Iterator iterator = values.keySet().iterator(); iterator.hasNext();) {
//            String key = (String) iterator.next();
//            Object value = values.get(key);
//            binding.setVariable(key, value);
//        }
//        return binding;
//    }
//


    public static void main(String[] args) throws Exception
    {
        Map values = new HashMap();
        values.put("x", new Integer(10));
        values.put("y", new Integer(20));
        String answer = eval("x + 2 * y", values);

        double dans = evaluateNumericExpression("x + 2 * y", values);

        double dans2 = evaluateNumericExpression("@x + 2 * @y", values);

        dans = 0;
 //        test1();

    }


}
