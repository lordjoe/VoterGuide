package com.lordjoe.runner;

/**
 * com.lordjoe.runner.TopLeveActionCompleteListener
 *  called when a test queued by the ui has completed
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface TopLeveActionCompleteListener
{
    public static TopLeveActionCompleteListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = TopLeveActionCompleteListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onTopLevelRunActionComplete(IActionRunner pAction);

}
