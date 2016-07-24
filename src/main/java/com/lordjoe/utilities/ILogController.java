package com.lordjoe.utilities;


/*
 * com.lordjoe.Utilities.ILogController
 * @author smlewis
 * Date: Mar 13, 2003
 */

public interface ILogController 
{
    public static final Class THIS_CLASS = ILogController.class;
    public static final ILogController[] EMPTY_ARRAY = {};

    public void setLogDebugMessages(Class in);
    public void setLogDebugMessages(Class in, boolean doit);
    public void setLogAllDebugMessages(boolean doit);
    public boolean logDebugMessages(Class in);
    public boolean logDebugMessages(Object in);
 }