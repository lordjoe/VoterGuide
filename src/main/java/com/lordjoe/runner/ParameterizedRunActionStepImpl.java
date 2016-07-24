package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;
import com.lordjoe.general.*;

import java.util.*;

/**
 * com.lordjoe.runner.ParameterizedRunActionStepImpl
 *
 * @author Steve Lewis
 * @date Feb 22, 2007
 */
public class ParameterizedRunActionStepImpl extends ImplementationBase implements
        IParameterizedRunActionStep
{
    public static ParameterizedRunActionStepImpl[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ParameterizedRunActionStepImpl.class;

    private final IRunAction m_RunAction;
    private final List<RunActionParameter> m_Parameters;
    public ParameterizedRunActionStepImpl(IRunAction test)
    {
        m_RunAction = test;
        m_Parameters = new ArrayList<RunActionParameter>();
    }


    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return TAG_NAME;
    }


    public IRunAction getRunAction()
    {
        return m_RunAction;
    }

    /**
     * add parameters to environment
     *
     * @param pEnv
     */
    public void setEnvironment(IRunEnvironment pEnv)
    {
        RunActionParameter[] params = new RunActionParameter[m_Parameters.size()];
        m_Parameters.toArray(params);
        for (int i = 0; i < params.length; i++) {
            RunActionParameter param = params[i];
            pEnv.setParameter(param.getName(),param.getObjectValue());
        }
    }


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


    @Override
    public String toString()
    {
        return getRunAction().toString();

    }
}
