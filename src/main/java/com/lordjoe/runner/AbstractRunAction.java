package com.lordjoe.runner;



import com.lordjoe.general.*;
import com.lordjoe.exceptions.*;
import com.lordjoe.lib.xml.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.runner.AbstractRunAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public abstract class   AbstractRunAction extends ImplementationBase implements IRunAction
{
    public static AbstractRunAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractRunAction.class;
    public static final long DEFAULT_TEST_TIMEOUT = 60 * 60 * 1000; // 1000 // defeuklt to long time for debugging

    private final List<IRunCondition> m_preconditions;
    private final List<IRunCondition> m_postconditions;
    private final List<IRunCondition> m_runconditions;
    private final Set<RunActionParameter> m_Parameters;
    private long m_TimeoutMillisec;
    private String m_RunActionsRequirement;

    public AbstractRunAction(String name)
    {
       super();
        setName(name);
        m_preconditions = new ArrayList<IRunCondition>();
        m_postconditions = new ArrayList<IRunCondition>();
        m_runconditions = new ArrayList<IRunCondition>();
        m_Parameters = new HashSet<RunActionParameter>();
        m_TimeoutMillisec = DEFAULT_TEST_TIMEOUT;
    }

    /**
     * return all documented requirements addressed by a specific test
     * @return
     */
    public String[] getAllRunActionsRequirements()
    {
        Set<String> holder = new HashSet<String>();
        accumulateRunActionsRequirements(holder);
        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected void accumulateRunActionsRequirements(Set<String> holder)
    {
        String requirement = getRunActionsRequirement();
        if(requirement != null)
            holder.add(requirement);
    }

    /**
     * return a spacific requirement tested by this test
     * @return
     */
    public String getRunActionsRequirement()
    {
        return m_RunActionsRequirement;
    }

    public void setRunActionsRequirement(String pRunActionsRequirement)
    {
        m_RunActionsRequirement = pRunActionsRequirement;
    }

    public long getTimeoutMillisec()
    {
        return m_TimeoutMillisec;
    }


    /**
     * better timeout estimate based on exact test
     *
     * @return
     */
    public long getTimeoutMillisec(IRunEnvironment pEnv)
    {
        return getTimeoutMillisec();
    }

    public void setTimeoutMillisec(long pTimeoutMillisec)
    {
        m_TimeoutMillisec = pTimeoutMillisec;
    }

    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return TEST_TAG;
    }

    /**
     * if the test is multistep return number of steps
     * else return 1
     * @return
     */
    public int getTotalRunActionSteps(IRunEnvironment pEnv)
    {
        return 1;
    }


    /**
     * a suits is a collection of tests
     *
     * @return
     */
    public boolean isSuite()
    {
        return false;
    }

    /**
     * @return
     */
    public RunActionParameter[] getAllRunActionParameters()
    {
       return getRequiredParameters();
    }

    /**
     * These parameters are defined in the environment
     * @return
     */
    public RunActionParameter[] getRequiredParameters()
    {
        RunActionParameter[] ret = new RunActionParameter[m_Parameters.size()];
        m_Parameters.toArray(ret);
        return ret;
    }
 
    /**
     * These conditions must be true before
     * a test can be run
     *
     * @return
     */
    public IRunCondition[] getRunConditions()
    {
        IRunCondition[] ret = new IRunCondition[m_runconditions.size()];
        m_runconditions.toArray(ret);
        return ret;
    }

    /**
     * These conditions must be true after
     * a test can be run
     *
     * @return
     */
    public IRunCondition[] getPreConditions()
    {
        IRunCondition[] ret = new IRunCondition[m_preconditions.size()];
        m_preconditions.toArray(ret);
        return ret;
    }

    /**
     * These conditions must be true after
     * a test can be run
     *
     * @return
     */
    public IRunCondition[] getPostConditions()
    {
        IRunCondition[] ret = new IRunCondition[m_postconditions.size()];
        m_postconditions.toArray(ret);
        return ret;
    }

    
    protected String requiredParametersPresent()
    {
        RunActionParameter[] tps = getRequiredParameters();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tps.length; i++) {
            RunActionParameter tp = tps[i];
            
        }
        return sb.toString();
    }

    /**
     * run the test
     *
     * @param pEnv
     * @return
     */
    public final String performRunAction(IRunEnvironment pEnv)
    {
        IActionRunner runner = buildRunner(pEnv);
        return performRunAction(runner);
    }


    protected  void waitNotPaused(IRunEnvironment pEnv)
    {
        synchronized(pEnv) {
            while(pEnv.isPaused())  {
                try {
                    pEnv.wait();
                }
                catch (InterruptedException e) {
                    throw new WrappingException(e);
                }
            }
        }
    }

    /**
     * run the test
     *
     * @param pRunner
     * @return
     */
    public final String performRunAction(IActionRunner pRunner)
    {

        IRunEnvironment env = pRunner.getEnv();
        long timeout = getTimeoutMillisec();
        Object timeoutOverride = env.getParameter(PropertyNames.TIMEOUT_PROP);
        if(timeoutOverride != null )
            timeout =  RunActionUtilities.buildRunActionLong(timeoutOverride);
        waitNotPaused(env);
        if(env.isTerminated())
            return "Terminated by request";
        RunActionManager tm = RunActionManager.getInstance();
        tm.pushCurrentRunAction(pRunner);
        long start = System.currentTimeMillis();
        try {
            ExecutorService executor = tm.getExecutor();
            Future<String> fut = executor.submit(pRunner);
            String ret = fut.get(timeout,TimeUnit.MILLISECONDS);
            return ret;
        }
        catch (InterruptedException e) {
            return "RunAction Interrupted";
        }
        catch (ExecutionException e) {
           return RunActionUtilities.buildExceptionString(e);
        }
        catch (TimeoutException e) {
            pRunner.setState(RunStateEnum.TimedOut);
            Object expected = env.getParameter(PropertyNames.EXPECTED_PROP);
            if("Timeout".equals(expected))
                return null;
              long del = System.currentTimeMillis() - start;
            String msg = "RunAction Timedout after  " + del / 1000 + " sec";
            System.out.println(msg);
            return msg;
        }
        catch (RuntimeException e) {
            return RunActionUtilities.buildExceptionString(e);
         }
         finally {
            tm.popCurrentRunAction();
        }
    }

    /**
     * set all emvironment variables
     *
     * @param pEnv
     */
    public void initializeEnvironment(IRunEnvironment pEnv)
    {
        RunActionParameter[] params = getRequiredParameters();
        for (int i = 0; i < params.length; i++) {
            RunActionParameter param = params[i];
             initializeParameter(param, pEnv);
        }

    }
    
    public String getPath(IRunEnvironment pEnv)
    {
        if(pEnv == null)
            return "";
        IRunAction currentRunAction = pEnv.getCurrentRunAction();
        String testName = currentRunAction.getName();
        IRunEnvironment environment = pEnv.getParentEnvironment();
        return testName + "->" + getPath(environment);
    }
    /**
     * set all emvironment variables
     *
     * @param pEnv
     */
    protected void initializeParameter(RunActionParameter param, IRunEnvironment pEnv)
    {
        String name = param.getName();
        Object value = param.getObjectValue();
        if(value != null)   {
            pEnv.setParameter(name,value);
            return;
        }
        Object current = pEnv.getParameter(name);
        if(current == null) {
            String defaultStr = param.getDefault();
            if(defaultStr == null) {
                throw new RunActionFailureException("In test " + getPath(pEnv) + "Parameter " + name +
                 " not present and no default supplied");
            }
            // todo convert default
            pEnv.setParameter(name,defaultStr);
        }


    }

    public IActionRunner buildRunner(IRunEnvironment pEnv)
    {
        IActionRunner runner = ActionObjectFactory.buildActionRunner(this, pEnv);
        return runner;
    }


    /**
     * run the test internal code
     *
     * @param pEnv
     * @param pRunnner
     * @return
     */
    public abstract String performInRunner(IRunEnvironment pEnv, IActionRunner pRunnner);


    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        if("Parameter".equals(TagName)) {
            String name = XMLUtil.handleRequiredNameValueString("name",attributes);
            String value = XMLUtil.handleOptionalNameValueString("value",attributes);
            String defaultVal = XMLUtil.handleOptionalNameValueString("default",attributes);
            String type = XMLUtil.handleRequiredNameValueString("type",attributes);
            RunActionParameter tp  = new RunActionParameter(name,type,defaultVal,value);
            m_Parameters.add(tp);
        }
        return super.handleTag(TagName, attributes);

    }
    
}
