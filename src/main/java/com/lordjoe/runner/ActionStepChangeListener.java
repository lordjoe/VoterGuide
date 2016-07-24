package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunActionStepChangeListener
 *  
 * listens for changes in step of this test
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface ActionStepChangeListener
{
    public static ActionStepChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionStepChangeListener.class;

    /**
     * called when queued list of tests changes
     * @param isRunning
     */
    public void onRunActionStepChange(IActionRunner pAction,int currentStep,int totalSteps);

}
