package com.lordjoe.runner.ui;

import com.lordjoe.runner.*;


import javax.swing.*;
import java.awt.event.*;

/**
 * com.lordjoe.runner.ui.RunActionCancelAction
 *
 * @author Steve Lewis
 * @date Jan 9, 2008
 */
public class ActionCancelAction extends AbstractAction implements RunActionRunningListener,
        ActionQueueChangeListener
{
    public static ActionCancelAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionCancelAction.class;

    /**
     * Defines an <code>Action</code> object with a default
     * description string and default icon.
     */
    public ActionCancelAction()
    {
        putValue(Action.NAME,"Cancel All RunActions");
        RunActionManager tm = RunActionManager.getInstance();
        tm.addRunActionRunningListener(this);
        tm.addRunActionQueueChangeListener(this);
        setEnabled(tm.getRunningRunAction() != null);
    }

    /**
     * called when queued list of tests changes
     *
     * @param isRunning
     */
    public void onRunQueueChange(IActionRunner[] pQueuedActions)
    {
        if(pQueuedActions.length > 0)
            setEnabled(true);

    }

    /**
     * called when test runner availability changes
     *
     * @param isRunning
     */
    public void onRunActionRunnerAvailabilityChange(boolean isRunning)
    {
        RunActionManager tm = RunActionManager.getInstance();
        if(tm.getQueuedRunActions().length > 0) {
            setEnabled(true);
            return;
        }
        if(tm.getRunningRunAction() != null) {
            setEnabled(true);
            return;
        }
        setEnabled(isRunning);

    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        RunActionManager tm = RunActionManager.getInstance();
        tm.terminate();

    }
}
