package com.lordjoe.runner;


import java.util.*;

/**
 * com.lordjoe.runner.RunEnvironmentImpl
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class RunEnvironmentImpl implements IRunEnvironment
{
    public static RunEnvironmentImpl[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunEnvironmentImpl.class;

    private final Map<String,Object> m_Parameters;
    private final IRunEnvironment m_parent;
    private final IActionRunner m_runner;
    private IActionRunner m_currentActionRunner;
    private boolean m_Terminated;
    private boolean m_Paused;
    public RunEnvironmentImpl(IActionRunner pRunner,Map<String, Object> defaults)
    {
        m_Parameters = new HashMap<String,Object>(defaults);
        m_parent = null;
        m_runner = pRunner;
     }
    public RunEnvironmentImpl(RunEnvironmentImpl parent)
    {
        m_runner = parent.getRunner();
        m_parent = parent;
        m_Parameters = new HashMap<String,Object>(parent.m_Parameters);
    }



    public void setParameter(String name, Object value)
    {
        m_Parameters.put(name,value);
    }


    public IActionRunner getRunner()
    {
        return m_runner;
    }

    public IRunEnvironment getParent()
    {
        return m_parent;
    }


    public boolean isTerminated()
    {
        IRunEnvironment parent = getParent();
        if(parent == null)
            return m_Terminated;
        else
            return getParent().isTerminated();
    }

    public void setTerminated()
    {
        IRunEnvironment parent = getParent();
        if(parent == null)
            m_Terminated = true;
        else
             getParent().setTerminated();

    }

    public boolean isPaused()
    {
        IRunEnvironment parent = getParent();
        if(parent == null)
            return m_Paused;
        else
            return getParent().isPaused();
    }

    public synchronized void setPaused(boolean paused)
    {
        IRunEnvironment parent = getParent();
        if(parent == null)
            m_Paused = true;
        else
             getParent().setPaused(paused);
        notifyAll();
    }


    public IActionRunner getCurrentRunner()
    {
        return m_currentActionRunner;
    }

    public IRunAction getCurrentRunAction()
    {
        IActionRunner runner = getCurrentRunner();
        if(runner != null)
              return runner.getRunAction();
        return null;
    }

    public void setCurrentRunActionRunner(IActionRunner pAction)
    {
        if(m_currentActionRunner != null)
            throw new IllegalStateException("current pAction can only be set once");
        m_currentActionRunner = pAction;

    }

    public Object getParameter(String value)
    {
        Object o = m_Parameters.get(value);
        return o;
     }


    public Object getParameter(String value, Object defaultValue)
    {
        Object o = getParameter(value);
        if(o == null)
            return defaultValue;
        return o;
    }

    public IRunEnvironment getParentEnvironment()
    {
        return m_parent;
    }

    public IRunEnvironment createChildEnvironment()
    {
        return new RunEnvironmentImpl(this);
    }
}
