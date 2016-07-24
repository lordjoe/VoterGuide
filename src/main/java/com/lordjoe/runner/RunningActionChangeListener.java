package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunningActionChangeListener
 *  the test runner can queue multiple tests - this
 * listens for changes in this queue
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface RunningActionChangeListener
{
    public static RunningActionChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunningActionChangeListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onRunningActionChange(IActionRunner pAction);

    /**
     * called when actual test being run changes
     * @param isRunning
     */
    public void onCurrentRunActionChange(IActionRunner pAction);

}
