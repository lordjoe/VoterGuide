package com.lordjoe.runner;


/**
 * com.lordjoe.runner.IRunEnvironment
 * Data which is passed to a test
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IRunEnvironment
{
    public static IRunEnvironment[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IRunEnvironment.class;

    public Object getParameter(String name);

    public Object getParameter(String name, Object defaultValue);

    public void setParameter(String name, Object value);

    public IRunEnvironment getParentEnvironment();

    public IRunEnvironment createChildEnvironment();


    public IRunAction getCurrentRunAction();

    public IActionRunner getCurrentRunner();

    public void setCurrentRunActionRunner(IActionRunner pAction);



     public boolean isTerminated();

    public void setTerminated();

    public void setPaused(boolean isSo);

    public boolean isPaused();
}
