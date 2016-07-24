package com.lordjoe.runner;


import java.util.concurrent.*;

/**
 * com.lordjoe.runner.IRunActionRunner
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IActionRunner extends Callable<String>
{
    public static IActionRunner[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IActionRunner.class;

    public IRunAction getRunAction();



    public RunStateEnum getState();


    public int getCurrentStep();

    public void setCurrentStep(int step);

    /**
     * test is complete null is success non-null failure
     * @param s
     */
    public void setResult(String s);

    public void setState(RunStateEnum state);

    public boolean isSuccess();

    public boolean isFailure();

    public boolean isRunning();

    public boolean isStarted();


    /**
     * get the start time of the test im millisec
     * return 0 if the test not started
     * @return
     */
    public long getStart();

    /**
     * set the start time-
     * @param val time millisec
     * @throws IllegalStateException if set more than once
     */
    public void setStart(long val) throws IllegalStateException;

    /**
     * test is finished
     * @return
     */
    public boolean isCompleted();


    /**
     * append an error string
     * @param s  non-null error
     */
    public void addError(String s);

    public String getLog();

    public String getErrors();

    /**
     * log an event to the UI
     * @param s  non-null event
     */
    public void addSignificentEvent(String s);

    public void terminate();

    public void setPaused(boolean isSo);

    public boolean isPaused();
    
    public IRunEnvironment getEnv();

    public Throwable getException();

    public void waitForCompletion();  

    public void addRunActionResultListener(ActionResultListener pListener);

    public void removeRunActionResultListener(ActionResultListener pListener);

    public void notifyRunActionResultListeners(String result);


    public void addSignificantRunActionEventListener(SignificantActionEventListener pListener);

    public void removeSignificantRunActionEventListener(SignificantActionEventListener pListener);

    public void notifySignificantRunActionEventListeners(String result);

    public void addRunActionProgressListener(ActionProgressListener listener);

    public void removeRunActionProgressListener(ActionProgressListener listener);

    public void notifyRunActionProgressListeners(int now,int total);


    public void addRunActionStepChangeListener(ActionStepChangeListener listener);

    public void removeRunActionStepChangeListener(ActionStepChangeListener listener);

    public void notifyRunActionStepChangeListeners(int step,int totalSteps);


    public void addRunActionStatusChangeListener(RunStatusChangeListener pListener);

    public void removeRunActionStatusChangeListener(RunStatusChangeListener pListener);

    public void notifyRunActionStatusChangeListeners();


    public void setParameter(String name,Object value);
}
