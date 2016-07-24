package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunStateEnum
 *  states of a test
 * @author Steve Lewis
 * @date Mar 19, 2007
 */
public enum RunStateEnum
{
    Succeeded,
    Failed,
    TimedOut,
    Completed,
    Running,
    Queued,
    NotRun;

    public static RunStateEnum[] EMPTY_ARRAY={};
    public static Class THIS_CLASS = RunStateEnum.class;
}
