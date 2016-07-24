package com.lordjoe.ui.general;

import com.lordjoe.utilities.ResourceString;
import com.lordjoe.ui.*;

import javax.swing.*;


/**
 * com.lordjoe.ui.general.ProblemDisabledAction
 *
 * @author slewis
 * @date May 3, 2005
 */
public abstract class ProblemDisabledAction extends AbstractAction implements ProblemChangeListener
{
    public static final Class THIS_CLASS = ProblemDisabledAction.class;
    public static final ProblemDisabledAction EMPTY_ARRAY[] = {};

    private String m_FunctionString;
    public ProblemDisabledAction(String name)
    {
        this.putValue(Action.NAME, name);
        setEnabled(false);
    }

    public String getFunctionString()
    {
        return m_FunctionString;
    }

    public void setFunctionString(String pFunctionString)
    {
        m_FunctionString = pFunctionString;
        if(getValue(Action.SHORT_DESCRIPTION) != null)
           putValue(Action.SHORT_DESCRIPTION, m_FunctionString);
    }


    /**
     * what to do when problem status changes
     *
     * @param handler
     */
    public void onProblemChange(IProblemHandler handler)
    {
        if (!handler.isNoProblems())
        {
            String problemText = ResourceString.getStringFromText("This button is disabled because");
            String newValue = problemText + ":\n" + handler.getProblems();
            newValue = UIOps.convertTooltipToHtml(newValue);
            putValue(Action.SHORT_DESCRIPTION, newValue);
            setEnabled(false);
        }
        else
        {
            putValue(Action.SHORT_DESCRIPTION, getFunctionString()); 
            setEnabled(true);
        }
    }


}
