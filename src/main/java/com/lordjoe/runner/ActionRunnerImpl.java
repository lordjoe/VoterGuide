package com.lordjoe.runner;


import java.util.*;

/**
 * com.lordjoe.runner.ActionRunnerImpl
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class ActionRunnerImpl implements IActionRunner
{
    public static ActionRunnerImpl[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionRunnerImpl.class;

    private final IRunAction m_RunAction;
    private final IRunEnvironment m_env;
    private final Set<ActionResultListener> m_resultListeners;
    private final Set<ActionStepChangeListener> m_StepListeners;
    private final Set<ActionProgressListener> m_ProgressListeners;
    private final Set<RunStatusChangeListener> m_statusListeners;
    private final Set<SignificantActionEventListener> m_significantActionEventListeners;
    private Throwable m_Exception;
    private long m_Start;
    private int m_CurrentStep;
    private final Map<String, Object> m_Parameters;
    private final StringBuffer m_Log;
    private final StringBuffer m_Errors;
    private RunStateEnum m_State;
    private boolean m_Paused;

    public ActionRunnerImpl(IRunAction test, IRunEnvironment pEnv)
    {
        m_Parameters = new HashMap<String, Object>();
        m_RunAction = test;
        m_env = pEnv;
        m_State = RunStateEnum.NotRun;
        m_Log = new StringBuffer();
        m_Errors = new StringBuffer();

        m_ProgressListeners = new HashSet<ActionProgressListener>();
        m_resultListeners = new HashSet<ActionResultListener>();
        m_StepListeners = new HashSet<ActionStepChangeListener>();
        m_statusListeners = new HashSet<RunStatusChangeListener>();
        m_significantActionEventListeners = new HashSet<SignificantActionEventListener>();
    }

    public void setParameter(String name, Object value)
    {
        m_Parameters.put(name, value);
    }



    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString()
    {
        return getRunAction().getName();

    }


    public long getStart()
    {
        return m_Start;
    }


    public void setStart(long pStart)
    {
        if (m_Start > 0)
            throw new IllegalStateException("Start can only be set once");
        m_Start = pStart;
    }


    public int getCurrentStep()
    {
        return m_CurrentStep;
    }

    public void setCurrentStep(int step)
    {
        m_CurrentStep = step;
    }


    /**
     * log an event to the UI
     *
     * @param s non-null event
     */
    public void addSignificentEvent(String s)
    {
        notifySignificantRunActionEventListeners(s);

    }

    public String getLog()
    {
        return m_Log.toString();
    }

    public String getErrors()
    {
        if (m_Errors.length() == 0)
            return null;
        else
            return m_Errors.toString();
    }

    /**
     * append an error string
     *
     * @param s non-null error
     */
    public void addError(String s)
    {
        m_Errors.append(s);
    }


    /**
     * test is complete null is success non-null failure
     *
     * @param s
     */
    public void setResult(String s)
    {
        if (s == null) {
            setState(RunStateEnum.Succeeded);
        }
        else {
            setState(RunStateEnum.Failed);
            addError(s);

        }
        notifyRunActionResultListeners(s);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    public String call() throws Exception
    {
        try {
            return runRunAction();
        }
        catch (Exception e) {
            return RunActionUtilities.buildExceptionString(e);
        }
    }

    /**
     * run the test enclosed
     *
     * @return results - null is success
     */
    public String runRunAction()
    {
        String s = null;
        try {
            IRunAction test = getRunAction();
            IRunEnvironment env = getEnv();
            env.setCurrentRunActionRunner(this);
            test.initializeEnvironment(env);
            for (Iterator<String> iterator = m_Parameters.keySet().iterator(); iterator.hasNext();)
            {
                String key = iterator.next();
                Object value = m_Parameters.get(key);
                env.setParameter(key, value);
            }
            s = test.performInRunner(env, this);
            if (s != null)
                return s;
            return s;
        }
        catch (Exception e) {
            e.printStackTrace();
            setException(e);
            s = getErrors();
            return s;
        }
        finally {
            notifyRunActionResultListeners(s);
        }

    }


    public void setException(Throwable pException)
    {
        m_Exception = pException;
        String s = RunActionUtilities.buildExceptionString(pException);
        setResult(s);
    }

    public Throwable getException()
    {
        return m_Exception;
    }

    public IRunEnvironment getEnv()
    {
        return m_env;
    }

    public IActionRunner getParentRunner()
    {
        IRunEnvironment parent = getEnv().getParentEnvironment();
        if (parent == null)
            return null;
        return parent.getCurrentRunner();
    }


    public boolean isSuccess()
    {
        return getState() == RunStateEnum.Succeeded;
    }


    public RunStateEnum getState()
    {
        return m_State;
    }


    public void setState(RunStateEnum pState)
    {
        if (m_State == pState)
            return;

        // cases broken out for debugging
        if (pState == RunStateEnum.Succeeded)
            m_State = pState;
        else if (pState == RunStateEnum.Failed)
            m_State = pState;
        else if (pState == RunStateEnum.Completed)
            m_State = pState;
        else if (pState == RunStateEnum.Running)
            m_State = pState;
        else
            m_State = pState;
        notifyRunActionStatusChangeListeners();

    }

    public boolean isFailure()
    {
        RunStateEnum state = getState();
        return state == RunStateEnum.Failed || state == RunStateEnum.TimedOut;
    }

    public boolean isRunning()
    {
        return getState() == RunStateEnum.Running;
    }

    public boolean isStarted()
    {
        return
                getState() == RunStateEnum.Running ||
                        isCompleted()
                ;
    }

    /**
     * test is finished
     *
     * @return
     */
    public boolean isCompleted()
    {
        RunStateEnum state = getState();
        return
                state == RunStateEnum.Completed ||
                        state == RunStateEnum.TimedOut ||
                        state == RunStateEnum.Succeeded ||
                        state == RunStateEnum.Failed


                ;
    }


    public IRunAction getRunAction()
    {
        return m_RunAction;
    }


    public void waitForCompletion()
    {
        return;

    }

    public void addRunActionResultListener(ActionResultListener pListener)
    {
        m_resultListeners.add(pListener);
    }

    public void removeRunActionResultListener(ActionResultListener pListener)
    {
        m_resultListeners.remove(pListener);

    }

    public void notifyRunActionResultListeners(String result)
    {
        ActionResultListener[] listeners = null;
        synchronized (m_resultListeners) {
            if (m_resultListeners.isEmpty()) {
                return;
            }
            listeners = new ActionResultListener[m_resultListeners.size()];
            m_resultListeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            ActionResultListener listener = listeners[i];
            listener.onRunResult(this, result);
        }
    }


    public void addRunActionStepChangeListener(ActionStepChangeListener listener)
    {
        m_StepListeners.add(listener);
    }

    public void removeRunActionStepChangeListener(ActionStepChangeListener listener)
    {
        m_StepListeners.remove(listener);

    }

    public void notifyRunActionStepChangeListeners(int step, int totalSteps)
    {
        ActionStepChangeListener[] listeners = null;
        synchronized (m_StepListeners) {
            if (m_StepListeners.isEmpty())
                return;
            listeners = new ActionStepChangeListener[m_StepListeners.size()];
            m_StepListeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            ActionStepChangeListener listener = listeners[i];
            listener.onRunActionStepChange(this, step, totalSteps);
        }
    }


    public void addRunActionStatusChangeListener(RunStatusChangeListener pListener)
    {
        m_statusListeners.add(pListener);
    }

    public void removeRunActionStatusChangeListener(RunStatusChangeListener pListener)
    {
        m_statusListeners.remove(pListener);

    }

    public void notifyRunActionStatusChangeListeners()
    {
        RunStatusChangeListener[] listeners = null;
        synchronized (m_statusListeners) {
            if (m_statusListeners.isEmpty())
                return;
            listeners = new RunStatusChangeListener[m_statusListeners.size()];
            m_statusListeners.toArray(listeners);
        }
        RunStateEnum state = getState();
        for (int i = 0; i < listeners.length; i++) {
            RunStatusChangeListener listener = listeners[i];
            listener.onRunStatusChange(state);
        }
    }


    public void terminate()
    {
        IRunEnvironment env = getEnv();
        if (env != null)
            env.setTerminated();
        RunStateEnum state = getState();
        if (state == RunStateEnum.Failed || state == RunStateEnum.Succeeded ||
                state == RunStateEnum.TimedOut )
            return;
        setState(RunStateEnum.Failed);
        notifyRunActionResultListeners(null);
    }


    public void setPaused(boolean isSo)
    {
        m_Paused = isSo;

    }

    public boolean isPaused()
    {
        return m_Paused;
    }

    /**
     * add a listener
     *
     * @param pListener non-null listener to remove
     */
    public void addSignificantRunActionEventListener(SignificantActionEventListener pListener)
    {
        m_significantActionEventListeners.add(pListener);
    }

    /**
     * remove a listener
     *
     * @param pListener non-null listener to remove
     */
    public void removeSignificantRunActionEventListener(SignificantActionEventListener pListener)
    {
        m_significantActionEventListeners.remove(pListener);
    }

    /**
     * Notify any listners
     */
    public void notifySignificantRunActionEventListeners(String arg)
    {
        SignificantActionEventListener[] listeners = null;
        synchronized (m_significantActionEventListeners) {
            if (!m_significantActionEventListeners.isEmpty()) {
                listeners = new SignificantActionEventListener[m_significantActionEventListeners.size()];
                m_significantActionEventListeners.toArray(listeners);

                for (int i = 0; i < listeners.length; i++) {
                    SignificantActionEventListener listener = listeners[i];
                    listener.onSignificantEvent(arg);
                }
            }
        }
        IActionRunner pr = getParentRunner();
        if (pr != null)
            pr.notifySignificantRunActionEventListeners(arg);

    }


    public void addRunActionProgressListener(ActionProgressListener listener)
    {
        m_ProgressListeners.add(listener);
    }

    public void removeRunActionProgressListener(ActionProgressListener listener)
    {
        m_ProgressListeners.remove(listener);

    }

    public void notifyRunActionProgressListeners(int now, int total)
    {
        ActionProgressListener[] listeners = null;
        synchronized (m_ProgressListeners) {
            if (m_ProgressListeners.isEmpty())
                return;
            listeners = new ActionProgressListener[m_ProgressListeners.size()];
            m_ProgressListeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            ActionProgressListener listener = listeners[i];
            listener.onRunProgress(this, now, total);
        }
    }

}
