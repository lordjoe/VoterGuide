package com.lordjoe.ui;


import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.general.ArrayUtilities;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.runner.*;

import javax.swing.*;
import java.util.*;

/**
 * com.lordjoe.runner.ui.Expo
 * sedOperationAction
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public class ExposedOperationAction extends RunOperationAction implements
        RunStatusChangeListener, ActionResultListener
{


    public static Map<String, ExposedOperationAction[]> buildSatisfiedRequirements(
            ExposedOperationAction[] actions)
    {
        Map<String, ExposedOperationAction[]> ret = new HashMap<String, ExposedOperationAction[]>();
        for (int i = 0; i < actions.length; i++) {
            ExposedOperationAction action = actions[i];
            String[] testedRequirements = action.getAllRunActionsRequirements();
            for (int j = 0; j < testedRequirements.length; j++) {
                String testedRequirement = testedRequirements[j];
                addRunActionedRequirement(ret, testedRequirement, action);
            }
        }
        return ret;
    }

    private static void addRunActionedRequirement(Map<String, ExposedOperationAction[]> holder,
                                                  String req,
                                                  ExposedOperationAction pAction)
    {
        ExposedOperationAction[] actions = holder.get(req);
        if (actions == null) {
            ExposedOperationAction[] val = {pAction};
            holder.put(req, val);
        }
        else {
            actions = ArrayUtilities.addToArray(actions, pAction);
            holder.put(req, actions);
        }

    }

    public static final String CLEANUP_NAME = "ResetAll";
    private final IRunAction m_Operation;
    private IActionRunner m_runner;
    private RunStateEnum m_State;
    private final List<RunStatusChangeListener> m_listeners;

    public ExposedOperationAction(String opName, String actionName, JComponent cmp)
    {
        super(actionName.trim());
        opName = opName.trim();
        m_listeners = Collections.synchronizedList(new ArrayList<RunStatusChangeListener>());
        if (cmp != null) {
            cmp.setBackground(RunnerDisplayProperties.testStatusToColor(null));
        }
        RunActionManager tm = RunActionManager.getInstance();
        m_Operation = tm.getRunAction(opName);
        if (m_Operation == null) {
            throw new IllegalArgumentException("Cannot find operation " + opName);
        }
        m_State = RunStateEnum.NotRun;
        buildDescription(actionName);
    }

    protected void buildDescription(String actionName)
    {
        IRunAction test = getOperation();

        StringBuilder sb = new StringBuilder();
        sb.append(actionName + " " + m_State);
        String description = test.getDescription();
        String msg = sb.toString();
        if (description != null) {
            sb.append("\n");
            sb.append(description);
            msg = UIUtilities.convertTooltipToHtml(sb.toString());
        }
        putValue(Action.LONG_DESCRIPTION, msg);
    }


    public String[] getAllRunActionsRequirements()
    {
        return getOperation().getAllRunActionsRequirements();
    }

    public RunStateEnum getState()
    {
        return m_State;
    }

    public void setState(RunStateEnum pState)
    {
        if (m_State == pState)
            return;
        // cases broken out for debugging
        if (pState == RunStateEnum.Succeeded)
            m_State = pState;
        else if (pState == RunStateEnum.Failed)
            m_State = pState;
        else if (pState == RunStateEnum.Running)
            m_State = pState;
        else if (pState == RunStateEnum.Completed)
            m_State = pState;
        else
            m_State = pState;

        String name = getValue(Action.NAME).toString();
        buildDescription(name);
        notifyRunActionStatusChangeListener(pState);
    }

    /**
     * called when queued list of tests changes
     *
     * @param isRunning
     */
    public void onRunStatusChange(RunStateEnum state)
    {
        setState(state);
    }


    public IActionRunner getRunner()
    {
        return m_runner;
    }

    public void setRunner(IActionRunner pRunner)
    {
        if (m_runner == pRunner)
            return;
        if (pRunner != null) {
            setEnabled(false);
        }
        else {
            setEnabled(true);
            if (m_runner != null) {
                m_runner.removeRunActionResultListener(this);
                m_runner.removeRunActionStatusChangeListener(this);
            }
        }
        m_runner = pRunner;
        if (m_runner != null) {
            m_runner.addRunActionResultListener(this);
            m_runner.addRunActionStatusChangeListener(this);
            RunStateEnum state = m_runner.getState();
            setState(state);
        }

    }


    /**
     * Enables or disables the action.
     *
     * @param newValue true to enable the action, false to
     *                 disable it
     * @see javax.swing.Action#setEnabled
     */
    public void setEnabled(boolean newValue)
    {
        super.setEnabled(newValue);

    }

    /**
     * respond to a final test result
     *
     * @param pAction non-null test
     * @param result  null for success non-null failure
     */
    public void onRunResult(IActionRunner pAction, String result)
    {
        try {
            if (result == null)
                setState(RunStateEnum.Succeeded);
            else
                setState(RunStateEnum.Failed);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new WrappingException(e);
        }
        finally {
            setRunner(null);
        }

    }

    protected void performTask()
    {
        RunActionManager tm = RunActionManager.getInstance();
        IRunEnvironment env = tm.getEnvironment();
        IActionRunner runner =  m_Operation.buildRunner(env);
        setRunner(runner);
        tm.addQueuedRunAction(runner);
     }

    public IRunAction getOperation()
    {
        return m_Operation;
    }


    public void addRunActionStatusChangeListener(RunStatusChangeListener pListener)
    {
        m_listeners.add(pListener);
    }

    public void removeRunActionStatusChangeListener(RunStatusChangeListener pListener)
    {
        m_listeners.remove(pListener);
    }

    public void notifyRunActionStatusChangeListener(RunStateEnum pState)
    {
        RunStatusChangeListener[] listeners = null;
        synchronized (m_listeners) {
            listeners = new RunStatusChangeListener[m_listeners.size()];
            m_listeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            RunStatusChangeListener listener = listeners[i];
            listener.onRunStatusChange(pState);
        }

    }


}
