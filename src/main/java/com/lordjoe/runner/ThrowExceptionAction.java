package com.lordjoe.runner;

/**
 * com.lordjoe.runner.ThrowExceptionAction
 *  Always fails
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class ThrowExceptionAction extends AbstractRunAction
{
    public static ThrowExceptionAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ThrowExceptionAction.class;


    public ThrowExceptionAction(String name)
    {
        super(name);
    }



    /**
     * run the test
     *
     * @param pEnv
     * @param runnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunner)
    {
        if(true)
            throw new IllegalStateException("This test is supposed to throw an exception");
   //     pRunner.setResult(result);
    //    return result;
        return null; // never get here
    }


}
