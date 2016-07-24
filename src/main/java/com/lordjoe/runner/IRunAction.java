package com.lordjoe.runner;

import com.lordjoe.lang.*;


/**
 * com.lordjoe.runner.IRunAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public interface IRunAction extends INamableObject
{
    public static IRunAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IRunAction.class;

    public static final String SUITE_TAG = "RunActionSuite";
    public static final String TEST_TAG = "RunAction";

    /**
     * return text describing the test
     * @return  possibly null text
     */
    public String getDescription();

    /**
     * return all documented requirements addressed by a specific test
     * @return
     */
    public String[] getAllRunActionsRequirements();

    /**
     * if the test is multistep return number of steps
     * else return 1
     * @return
     */
    public int getTotalRunActionSteps(IRunEnvironment pEnv);

    /**
     * a suits is a collection of tests
     * @return
     */
    public boolean isSuite();

    /**
     * create a test runner
     * @param pEnv  non-null environment
     * @return non-null runner
     */
    public IActionRunner buildRunner(IRunEnvironment pEnv);

    /**
     *  These conditions must be true before
     * a test can be run
     * @return
     */
    public IRunCondition[] getRunConditions();

    /**
     *  These conditions must be true after
     * a test can be run
     * @return
     */
    public IRunCondition[] getPreConditions();


    /**
     *  These conditions must be true after
     * a test can be run
     * @return
     */
    public IRunCondition[] getPostConditions();

    /**
     * set all emvironment variables
     * @param pEnv
     */
    public void initializeEnvironment(IRunEnvironment pEnv);

    /**
     * run the test - this makes a runner
     * @param pEnv
     * @return
     */
    public String performRunAction(IRunEnvironment pEnv);


    /**
     * run the test - when a runner already exists
     * @param env
     * @return
     */
    public String performRunAction(IActionRunner pRunner);


    /**
     * run the test only called by the runner
     * @param pEnv
     * @param pRunnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunnner);

    /**
     * better timeout estimate based on exact test
     * @return
     */
     public long getTimeoutMillisec(IRunEnvironment pEnv);

    /**
     * time after which the test is thought to have failed
     * @return
     */
     public long getTimeoutMillisec();

    /**
     *
     * @return
     */
     public RunActionParameter[] getAllRunActionParameters();

}
