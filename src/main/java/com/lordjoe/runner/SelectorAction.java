package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;

import java.util.*;

/**
 * com.lordjoe.runner.SelectorAction
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public abstract class SelectorAction extends AbstractRunAction implements IRunSelectorAction
{
    public static SelectorAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = SelectorAction.class;


    private final List<AddedParameter> m_AddedParameters;
    private final List<IRestriction>  m_Restrictions;
    private final String m_TargetProperty;
    public SelectorAction(String name,String targetProp)
    {
        super(name);
        m_TargetProperty = targetProp;
        m_Restrictions = new ArrayList<IRestriction>();
        m_AddedParameters = new ArrayList<AddedParameter>();
    }


    public void addRestriction(IRestriction restr)
    {
       m_Restrictions.add(restr);
    }

    public abstract Object[] getPossibleValues(IRunEnvironment pEnv);


    public String getTargetProperty()
    {
        return m_TargetProperty;
    }

    public IRestriction[] getRestrictions()
    {
        IRestriction[] ret = new IRestriction[m_Restrictions.size()];
        m_Restrictions.toArray(ret);
        return ret;
    }

    protected Object[] restrictChoices(Object[] in)
    {
        List<Object> holder = new ArrayList<Object>();
        IRestriction[] restrictions = getRestrictions();
        for (int i = 0; i < in.length; i++) {
            boolean isOK = true;
            Object o = in[i];
            for (int j = 0; j < restrictions.length; j++) {
                IRestriction restriction = restrictions[j];
                isOK = restriction.isSatisfied(o);
                if(!isOK)
                    break;
            }
            if(isOK)
                holder.add(o);
        }
        Object[] ret = new Object[holder.size()];
        holder.toArray(ret);
        return ret;
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

        try {
            Object[] values = getPossibleValues(pEnv);
            if(values == null || values.length == 0)
                return "Selection set for " + getTargetProperty() + " is empty";
            Object[] restrictedValues = restrictChoices(values);
            if(restrictedValues.length == 0)
                return "Restricted Selection set for " + getTargetProperty() + " is empty";

            placeValue(pEnv,restrictedValues);

            return  null; // success
        }
        catch (RunActionFailureException e) {
            return e.getMessage();
        }
    }

    protected void placeValue(IRunEnvironment pEnv,Object[] choices)
    {
        Object choice = ObjectUtilities.getRandomElement(choices);
        pEnv.getParentEnvironment().setParameter(getTargetProperty(),choice);
    }


    public AddedParameter[] getAddedParameters()
    {
        AddedParameter[] ret = new AddedParameter[m_AddedParameters.size()];
        m_AddedParameters.toArray(ret);
        return ret;
    }


    public void addAddedParameters(AddedParameter added)
    {
        m_AddedParameters.add(added);
    }




    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String TagName, NameValue[] attributes)
    {
        if(ADDS_PATAMETER_TAG.equals(TagName)) {
            String name = XMLUtil.handleOptionalNameValueString("name",attributes);
            String type = XMLUtil.handleOptionalNameValueString("type",attributes);
            AddedParameter adding = new AddedParameter(name,type);
            addAddedParameters(adding);
            return this;
        }
        return super.handleTag(TagName, attributes);

    }
}
