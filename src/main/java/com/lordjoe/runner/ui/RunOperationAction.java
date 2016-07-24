package com.lordjoe.runner.ui;

import javax.swing.*;
import java.awt.event.*;


/**
 * com.lordjoe.runner.ui.RunOperationAction
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public abstract class RunOperationAction extends AbstractAction implements
       Runnable
{

    public RunOperationAction(String actionName)
    {
        this.putValue(Action.NAME, actionName);
    }



    public final void run()
    {
        performTask();
    }

    /**
     * do real work here
     */
    protected abstract void performTask();


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        performTask();

    }
}

