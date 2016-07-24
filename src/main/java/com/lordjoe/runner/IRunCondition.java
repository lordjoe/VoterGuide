package com.lordjoe.runner;

/**
 * com.lordjoe.runner.IRunCondition
 *  this is a testable condition
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IRunCondition
{
    public static IRunCondition[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IRunCondition.class;

    /**
     * return true if the condition is met
     * @param pEnv - non-null test environment
     * @return  null for the condition met otherwise an
     *   explanation
     */
    public String isMet(IRunEnvironment pEnv);

    /**
     * if teh condition is not met try to make it so
     * @param pEnv - non-null test environment
     * @return   null for the condition met otherwise an
     *   explanation
     */
    public String guaranteeMet(IRunEnvironment pEnv);
}
