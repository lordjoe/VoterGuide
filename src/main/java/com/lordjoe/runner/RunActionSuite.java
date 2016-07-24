package com.lordjoe.runner;

import java.util.*;

/**
 * com.lordjoe.runner.RunActionSuite
 *  a RunAction suite is a list of actions
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class RunActionSuite extends AbstractRunAction
{
    public static RunActionSuite[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionSuite.class;

    private final List<IRunAction> m_RunActions;

    public RunActionSuite(String name)
    {
        super(name);
        m_RunActions = new ArrayList<IRunAction>();
    }

    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return SUITE_TAG;
    }


    /**
     * a suits is a collection of tests
     *
     * @return
     */
    public boolean isSuite()
    {
        return true;

    }


    /**
     * These conditions must be true before
     * a test can be run
     *
     * @return
     */
    public IRunCondition[] getRunConditions()
    {
        List<IRunCondition> holder = new ArrayList<IRunCondition>();
        IRunAction[] tests = getRunActions();
        for (int i = 0; i < tests.length; i++) {
            IRunAction test = tests[i];
            IRunCondition[] tc = test.getRunConditions();
            for (int j = 0; j < tc.length; j++) {
                IRunCondition tt = tc[j];
                if(!holder.contains(tt))
                    holder.add(tt);
            }
        }
        IRunCondition[] ret = new IRunCondition[holder.size()];
        holder.toArray(ret);
        return ret;

    }

    /**
     * These are all the tests to run
     * @return
     */
    public IRunAction[] getRunActions()
    {
        IRunAction[] ret = new IRunAction[m_RunActions.size()];
        m_RunActions.toArray(ret);
        return ret;
    }


    public void addRunAction(IRunAction test)
    {
       m_RunActions.add(test);
    }

    /**
     * run the test
     *
     * @param pEnv
     * @param runnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunner)
    {
        RunActionManager tm =  RunActionManager.setupRunActionManager();
        String ret = null;
        try {
            IRunAction[] tests = getRunActions();
            for (int i = 0; i < tests.length; i++) {
                IRunAction test = tests[i];
                ret = tm.perform(test);
                if(ret != null)
                    return ret;
            }
            return ret;
        }
        catch (Exception e) {
            ret = e.getMessage();
            return ret;
        }
        finally {
            pRunner.notifyRunActionResultListeners(ret);
        }
    }
}
