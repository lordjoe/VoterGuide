package com.lordjoe.runner;

import com.lordjoe.utilities.*;


/**
 * com.lordjoe.runner.GuaranteePolling
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class WaitFor extends AbstractRunAction
{
    public static WaitFor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = WaitFor.class;


    public WaitFor(String name)
    {
        super(name);
    }


    /**
     * run the test
     *
     * @param pEnv
     * @param pRunnner
     * @return
     */
    public String performInRunner(IRunEnvironment pEnv, IActionRunner pRunnner)
    {
        Object o = pEnv.getParameter(PropertyNames.MILLISEC_PROP);
        int millsec = Integer.parseInt(o.toString());
        ThreadUtilities.waitFor(millsec);
        return null;
    }

}
