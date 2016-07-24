package com.lordjoe.lang;

/**
 * com.lordjoe.lang.ITimedLogger
 *
 * @author Steve Lewis
 * @date Jun 5, 2008
 */
public interface ITimedLogger extends ILoggerObject
{
    public static ITimedLogger[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ITimedLogger.class;

    public String getElapsedTimeString();

    public String getRealTimeString();

}
