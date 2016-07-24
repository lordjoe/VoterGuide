package com.lordjoe.runner;

/**
 * com.lordjoe.runner.ActionQueueChangeListener
 *  the test runner can queue multiple tests - this
 * listens for changes in this queue
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface ActionQueueChangeListener
{
    public static ActionQueueChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionQueueChangeListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onRunQueueChange(IActionRunner[] pQueuedActions);

}
