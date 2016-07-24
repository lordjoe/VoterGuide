package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunStatusChangeListener
 *  listens for changes on the status of a test
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface RunStatusChangeListener
{
    public static RunStatusChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunStatusChangeListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onRunStatusChange(RunStateEnum pState);

}
