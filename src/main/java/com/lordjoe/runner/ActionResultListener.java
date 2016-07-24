package com.lordjoe.runner;

/**
 * com.lordjoe.runner.ActionResultListener
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface ActionResultListener
{
    public static ActionResultListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionResultListener.class;

    /**
     * respond to a final test result
     * @param pAction non-null test
     * @param result null for success non-null failure
     */
    public void onRunResult(IActionRunner pAction,String result);
}
