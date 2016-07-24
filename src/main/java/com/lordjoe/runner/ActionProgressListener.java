package com.lordjoe.runner;

/**
 * com.lordjoe.runner.ActionProgressListener
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface ActionProgressListener
{
    public static ActionProgressListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionProgressListener.class;

    public void onRunProgress(IActionRunner pAction,int step, int totalSteps);
}
