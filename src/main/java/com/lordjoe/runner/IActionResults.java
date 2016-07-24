package com.lordjoe.runner;

/**
 * com.lordjoe.runner.IRunActionResults
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IActionResults
{
    public static IActionResults[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IActionResults.class;

    public boolean isSuccess();

    public boolean isFailure();

    public boolean isCompleted();

    public boolean isRunning();

    public void setStarted(boolean pStarted);

    public void setRunning(boolean pRunning);


    public boolean isStarted();

    public String getLog();

    public String getErrors();

    public IActionRunner getRunAction();

}
