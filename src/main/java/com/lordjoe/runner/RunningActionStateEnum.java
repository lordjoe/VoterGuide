package com.lordjoe.runner;

/**
 * com.lordjoe.runner.RunningActionStateEnum
 *  states of a test being run
 * @author Steve Lewis
 * @date Mar 19, 2007
 */
public enum RunningActionStateEnum
{
    Finished,
    Running,
    Paused,
    NotStarted;

    public static RunningActionStateEnum[] EMPTY_ARRAY={};
    public static Class THIS_CLASS = RunningActionStateEnum.class;
}
