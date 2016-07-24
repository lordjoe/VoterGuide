package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;

import java.util.*;

/**
 * com.lordjoe.runner.CompositeRunAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class CompositeRunAction extends AbstractRunAction
{
    public static CompositeRunAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CompositeRunAction.class;

    private final List<IParameterizedRunActionStep> m_SubRunActions;
    private boolean m_ExitOnError;

    public CompositeRunAction(String name)
    {
        super(name);
        m_SubRunActions = new ArrayList<IParameterizedRunActionStep>();
        m_ExitOnError = true;
    }

    public boolean isExitOnError()
    {
        return m_ExitOnError;
    }

    public void setExitOnError(boolean pExitOnError)
    {
        m_ExitOnError = pExitOnError;
    }



    public void addRunAction(IParameterizedRunActionStep added)
    {
        m_SubRunActions.add(added);
    }


    protected void accumulateRunActionsRequirements(Set<String> holder)
    {
        super.accumulateRunActionsRequirements(holder);
        IParameterizedRunActionStep[] steps = getAllRunActions();
        for (int i = 0; i < steps.length; i++) {
            IParameterizedRunActionStep step = steps[i];
            IRunAction test = step.getRunAction();
            if(test instanceof AbstractRunAction)
                ((AbstractRunAction)test).accumulateRunActionsRequirements(holder);
        }
    }



    /**
     * if the test is multistep return number of steps
     * else return 1
     * @return
     */
    public int getTotalRunActionSteps(IRunEnvironment pEnv)
    {
        return Math.max(1,m_SubRunActions.size());
    }


    public long getTimeoutMillisec()
    {
       IParameterizedRunActionStep[] steps = getAllRunActions();
       long ret = 0;
        for (int i = 0; i < steps.length; i++) {
            IParameterizedRunActionStep step = steps[i];
            ret += step.getRunAction().getTimeoutMillisec();
        }
        return ret;
    }

    public long getTimeoutMillisec(IRunEnvironment pEnv)
    {
       IParameterizedRunActionStep[] steps = getAllRunActions();
       long ret = 0;
        for (int i = 0; i < steps.length; i++) {
            IParameterizedRunActionStep step = steps[i];
            ret += step.getRunAction().getTimeoutMillisec(pEnv);
        }
        return ret;
    }

    public IParameterizedRunActionStep[] getAllRunActions()
    {
        IParameterizedRunActionStep[] ret = new IParameterizedRunActionStep[m_SubRunActions.size()];
        m_SubRunActions.toArray(ret);
        return ret;
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
        String result = null;
        StringBuilder sb = new StringBuilder();
        IParameterizedRunActionStep[] tests = getAllRunActions();
        pRunner.notifyRunActionStepChangeListeners(0,tests.length);
        pRunner.notifyRunActionProgressListeners(0,tests.length);
        for (int i = 0; i < tests.length; i++) {
            waitNotPaused(pEnv);
            if(pEnv.isTerminated())
                return "Terminated by request";
            IParameterizedRunActionStep test = tests[i];
            IRunEnvironment childEnv = pEnv.createChildEnvironment();
            test.setEnvironment(childEnv);
            IRunAction realRunAction = test.getRunAction();
            result = realRunAction.performRunAction(childEnv);
            if(result != null)  {
                if(realRunAction instanceof IRunIfAction)
                    return null; // success but test cannot be run
                if(isExitOnError()) {
                  break;
                }
                else {
                    sb.append(test);
                    sb.append("\n");
                }
            }
            pRunner.setCurrentStep(i);
            pRunner.notifyRunActionStepChangeListeners(i,tests.length);
            pRunner.notifyRunActionProgressListeners(i,tests.length);
        }
        pRunner.notifyRunActionStepChangeListeners(tests.length,tests.length);
        pRunner.notifyRunActionProgressListeners(tests.length,tests.length);
        if(!isExitOnError() && sb.length() > 0)
            result = sb.toString();
        pRunner.setResult(result);
        return result;
    }

    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        if("Step".equals(TagName)) {
            String name = XMLUtil.handleRequiredNameValueString("name",attributes);
            IParameterizedRunActionStep step = ActionObjectFactory.buildRunActionStep(name);
            addRunAction(step);
            return step;
        }
        if("ForEach".equals(TagName)) {
            String iterator = XMLUtil.handleRequiredNameValueString("iterator",attributes);
            String collection = XMLUtil.handleRequiredNameValueString("collection",attributes);
               ForEachAction runAction = new ForEachAction(iterator,collection);
            IParameterizedRunActionStep step = ActionObjectFactory.buildRunActionStep(runAction);
            addRunAction(step);
            return runAction;
        }
        if ("Repeat".equals(TagName)) {
            int count = XMLUtil.handleRequiredNameValueInt("count", attributes);
            String exitOnErrorStr = XMLUtil.handleOptionalNameValueString("exitOnError", attributes);
            RepeatAction runRunAction = new RepeatAction(count);
            if(exitOnErrorStr != null && "false".equals(exitOnErrorStr))
                runRunAction.setExitOnError(false);
            IParameterizedRunActionStep step = ActionObjectFactory.buildRunActionStep(runRunAction);
            addRunAction(step);
            return runRunAction;
        }
        if ("Parallel".equals(TagName)) {
            IRunAction runRunAction = new ParallelCompositeRunAction();
            IParameterizedRunActionStep step = ActionObjectFactory.buildRunActionStep(runRunAction);
            addRunAction(step);
            return runRunAction;
        }

        return super.handleTag(TagName, attributes);

    }

     @Override
    public String toString()
    {
        return getName();

    }

}
