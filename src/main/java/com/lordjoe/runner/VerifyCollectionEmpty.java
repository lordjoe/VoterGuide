package com.lordjoe.runner;


/**
 * com.lordjoe.runner.VerifyCollectionEmpty
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class VerifyCollectionEmpty extends AbstractRunAction
{
    public static VerifyCollectionEmpty[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = VerifyCollectionEmpty.class;


    public VerifyCollectionEmpty(String name)
    {
        super(name);
    }


    /**
     * run the test internal code
     *
     * @param pEnv
     * @param pRunnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunnner)
    {
        pEnv.getParameter(PropertyNames.COLLECTION_PROP);
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }
}
