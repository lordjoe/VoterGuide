package com.lordjoe.ui;


import com.lordjoe.exceptions.*;

import javax.swing.*;


/**
 * com.lordjoe.ui.SwingThreadUtilities
 *  class to invoke on the swing thread guaranteeing exception handling
 * @author Steve Lewis
 * @date Jan 26, 2006
 */
public class SwingThreadUtilities
{
    /**
     * invoke on the swing thread logging all exceptions
     *
     * @param task non-null task
     */
    public static void invoke(Runnable task)
    {
        // guarantee exceptiopn handling
        invoke(ExceptionHandlingManager.getExceptionHandlingRunner(task));
    }
    /**
     * invoke onthe swing thread logging all exceptions
     *
     * @param task non-null task
     */
    public static void invoke(ExceptionHandlingRunner task)
    {
        if (SwingUtilities.isEventDispatchThread()) {
                 task.run();
           }
        else {
            // wrap code in good exception handling
            SwingUtilities.invokeLater(task);
        }
    }

 }
