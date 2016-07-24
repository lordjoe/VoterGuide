package com.lordjoe.runner;

/**
 * com.lordjoe.runner.ForEachRunAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class ForEachAction extends CompositeRunAction
{
    public static ForEachAction[] gEMPTY_ARRAY = {};
    public static Class THIS_CLASS = ForEachAction.class;

    private final String m_Iterator;
    private final String m_Collection;

    public ForEachAction(String iteratorName, String collectionName)
    {
        super("ForEach");
        m_Iterator = iteratorName;
        m_Collection = collectionName;
    }


    public String getIterator()
    {
        return m_Iterator;
    }

    public String getCollection()
    {
        return m_Collection;
    }



    /**
     * if the test is multistep return number of steps
     * else return 1
     * @return
     */
    public int getTotalRunActionSteps(IRunEnvironment pEnv)
    {
        String collectionName = getCollection();
         Object itemsObj = pEnv.getParameter(collectionName);
        if (itemsObj.getClass().isArray()) {
            Object[] items = (Object[]) itemsObj;
            return Math.max(1,items.length);
        }
        return 1;
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
        String iteratorName = getIterator();
        String collectionName = getCollection();
        Object itemsObj = pEnv.getParameter(collectionName);
        if (itemsObj.getClass().isArray()) {
            Object[] items = (Object[]) itemsObj;
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];
                pEnv.setParameter(iteratorName, item);
                String test = super.performInRunner(pEnv, pRunner);
                if (test != null)
                    return test;

            }
        }
        else {
            // todo decide is non-array are OK collections
            pEnv.setParameter(iteratorName, itemsObj);
            String test = super.performInRunner(pEnv, pRunner);
            return test;
        }

        return null;

    }


    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return "ForEach";

    }
}
