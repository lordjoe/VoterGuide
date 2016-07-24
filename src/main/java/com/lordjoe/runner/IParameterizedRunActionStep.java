package com.lordjoe.runner;

/**
 * com.lordjoe.runner.IParameterizedRunActionStep
 *
 * @author Steve Lewis
 * @date Feb 22, 2007
 */
public interface IParameterizedRunActionStep
{
    public static IParameterizedRunActionStep[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IParameterizedRunActionStep.class;

    public static final String TAG_NAME = "Parameter";

    public IRunAction getRunAction();

    /**
     * add parameters to environment
     * @param pEnv
     */
    public void setEnvironment(IRunEnvironment pEnv);
}
