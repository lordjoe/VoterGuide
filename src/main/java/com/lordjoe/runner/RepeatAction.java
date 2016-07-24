package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;

/**
 * com.lordjoe.runner.RepeatAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class RepeatAction extends CompositeRunAction
{
    public static RepeatAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RepeatAction.class;

    private final int m_Count;
    public RepeatAction(int count)
    {
        super("Repeat");
        m_Count = count;
    }


    /**
     * if the test is multistep return number of steps
     * else return 1
     *
     * @return
     */
    public int getTotalRunActionSteps(IRunEnvironment pEnv)
    {
        return Math.max(1, getCount());
    }


    public int getCount()
    {
        return m_Count;
    }


    public long getTimeoutMillisec()
    {
        return super.getTimeoutMillisec();

    }

    public long getTimeoutMillisec(IRunEnvironment pEnv)
    {
        int count = getCount();
        return count * super.getTimeoutMillisec(pEnv);

    }

    /**
     * N
     * run the test
     *
     * @param pEnv
     * @param runnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunner)
    {
        StringBuilder sb = new StringBuilder();

        int count = getCount();
        long start = System.currentTimeMillis();
        notifyStep(pRunner, 0, count);
        for (int i = 0; i < count; i++) {
            waitNotPaused(pEnv);
            if (pEnv.isTerminated())
                return "Terminated by request";
            String test = super.performInRunner(pEnv, pRunner);
            if (test != null) {
              if(isExitOnError()) {
                return test;
              }
              else {
                  sb.append(test);
                  sb.append("\n");
              }
            }
            pRunner.setCurrentStep(i);
            notifyStep(pRunner, i, count);
            long del = System.currentTimeMillis() - start;
            System.out.println("Completed Step " + i + " after " + del);
        }
        notifyStep(pRunner, count, count);

        if(!isExitOnError() && sb.length() > 0)
            return sb.toString();
        
        return null;

    }

    /**
     * notify runner and parent of steps
     * @param pRunner
     * @param pI
     * @param pCount
     */
    private void notifyStep(IActionRunner pRunner, int pI, int pCount)
    {

        pRunner.notifyRunActionStepChangeListeners(pI, pCount);
        pRunner.notifyRunActionProgressListeners(pCount, pCount);
        IRunEnvironment parentEnv = pRunner.getEnv().getParentEnvironment();
        if (parentEnv != null) {
            pRunner = parentEnv.getCurrentRunner();
            if (pRunner != null) {
                pRunner.notifyRunActionStepChangeListeners(pI, pCount);
                pRunner.notifyRunActionProgressListeners(pCount, pCount);
            }
        }
    }

    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return "Repeat";

    }


    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        return super.handleTag(TagName, attributes);

    }
}
