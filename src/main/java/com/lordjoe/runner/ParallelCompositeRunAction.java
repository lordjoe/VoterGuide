package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.runner.ParallelCompositeRunAction
 *    like a composite run action  except all actions run in parallel
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class ParallelCompositeRunAction extends AbstractRunAction
{
    public static ParallelCompositeRunAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ParallelCompositeRunAction.class;
    public static final int NUMBER_THREADS = 40;

    private final List<IRunAction> m_SubRunActions;
    private final Executor m_Runner;
    public ParallelCompositeRunAction()
    {
        super("Parallel");
        m_Runner = Executors.newFixedThreadPool(NUMBER_THREADS);
        m_SubRunActions = new ArrayList<IRunAction>();
    }

    public void addRunAction(IRunAction added)
    {
        m_SubRunActions.add(added);
    }


    public IRunAction[] getAllRunActions()
    {
        IRunAction[] ret = new IRunAction[m_SubRunActions.size()];
        m_SubRunActions.toArray(ret);
        return ret;
    }


    public long getTimeoutMillisec()
    {
       IRunAction[] steps = getAllRunActions();
       long ret = 0;
        for (int i = 0; i < steps.length; i++) {
            IRunAction step = steps[i];
            ret = Math.max(ret,step.getTimeoutMillisec());
         }
        return ret;
    }

    public long getTimeoutMillisec(IRunEnvironment pEnv)
    {
       IRunAction[] steps = getAllRunActions();
       long ret = 0;
        for (int i = 0; i < steps.length; i++) {
            IRunAction step = steps[i];
            ret = Math.max(ret,step.getTimeoutMillisec(pEnv));
        }
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
        IRunAction[] tests = getAllRunActions();
        int nRunActions = tests.length;
        CountDownLatch doneSignal = new CountDownLatch(nRunActions);

        // Todo do a progress bar
        for (int i = 0; i < nRunActions; i++) {
            IRunAction test = tests[i];
            Runnable caller = new RunRunAction(test, pEnv.createChildEnvironment(),doneSignal);
            m_Runner.execute(caller);
            test = tests[i];
        }
        try {
            doneSignal.await();
        }
        catch (InterruptedException e) {
            result = "Interrupted";
        }
        pRunner.notifyRunActionResultListeners(result);
        return result;
    }

    public class RunRunAction implements Runnable
    {
        private final IRunEnvironment m_env;
        private final IRunAction m_RunAction;
        private final CountDownLatch m_Done;
        public RunRunAction(IRunAction test, IRunEnvironment pEnv,CountDownLatch doneSignal)
        {
            m_RunAction = test;
            m_env = pEnv;
           m_Done = doneSignal;
        }
        public void run()
        {
           String result = m_RunAction.performRunAction(m_env);
           m_Done.countDown();
        }
    }

    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        if("Step".equals(TagName)) {
            String name = XMLUtil.handleRequiredNameValueString("name",attributes);
            RunActionManager tm = RunActionManager.getInstance();
            IRunAction runAction = tm.getRunAction(name);
            addRunAction(runAction);
            return runAction;
        }
        if("ForEach".equals(TagName)) {
            String iterator = XMLUtil.handleRequiredNameValueString("iterator",attributes);
            String collection = XMLUtil.handleRequiredNameValueString("collection",attributes);
               ForEachAction runAction = new ForEachAction(iterator,collection);
            addRunAction(runAction);
            return runAction;
        }
        return super.handleTag(TagName, attributes);

    }

}
