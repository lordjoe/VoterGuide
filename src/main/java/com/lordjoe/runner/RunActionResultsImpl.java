package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunActionResultsImpl
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class RunActionResultsImpl implements IActionResults
{
    public static RunActionResultsImpl[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionResultsImpl.class;

    private final IActionRunner m_action;
    private final StringBuffer m_Log;
    private final StringBuffer m_Errors;
    private boolean m_Started;
    private boolean m_Running;

    public RunActionResultsImpl(IActionRunner pAction)
    {
        m_action = pAction;
        m_Log = new StringBuffer();
        m_Errors = new StringBuffer();

    }


    public boolean isSuccess()
    {
        if(!isStarted() || isRunning())
            return false;
        return m_Errors.length() == 0;
    }

    public boolean isFailure()
    {
        if(!isStarted() || isRunning())
            return false;
        return m_Errors.length() > 0;
    }


    public boolean isCompleted()
    {
        return isStarted() && ! isRunning();
    }

    public boolean isRunning()
    {
        return m_Running;
    }

    public boolean isStarted()
    {
        return m_Started;
    }

    public String getLog()
    {
        return m_Log.toString();
    }

    public String getErrors()
    {
        if(m_Errors.length() == 0)
            return null;
        else
            return m_Errors.toString();
    }


    public IActionRunner getRunAction()
    {
        return m_action;
    }

    public void setStarted(boolean pStarted)
    {
        m_Started = pStarted;
    }

    public void setRunning(boolean pRunning)
    {
        m_Running = pRunning;
        // on or off we should be started
         setStarted(true);
    }
}
