package com.lordjoe.runner;

/**
 * com.lordjoe.runner.SignificantActionEventListener
 *  the test runner can queue multiple tests - this
 * listens for changes in this queue
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface SignificantActionEventListener
{
    public static SignificantActionEventListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = SignificantActionEventListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onSignificantEvent(String event);

}
