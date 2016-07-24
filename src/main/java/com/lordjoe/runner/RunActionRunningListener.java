package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunActionRunningListener
 *  the test runner can only run one test at a time
 *  THis listener listens for changes
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface RunActionRunningListener
{
    public static RunActionRunningListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionRunningListener.class;

    /**
     * called when test runner availability changes
     * @param isRunning
     */
    public void onRunActionRunnerAvailabilityChange(boolean isRunning);
}
