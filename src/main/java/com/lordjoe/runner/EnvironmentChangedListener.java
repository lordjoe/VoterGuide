package com.lordjoe.runner;

/**
 * com.lordjoe.runner.EnvironmentChangedListener
 *  notification that the test envivonment changed
 *  Listeners need to determine what has changes and if theay are
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public interface EnvironmentChangedListener
{
    public static EnvironmentChangedListener[] EMPTY_ARRAYs = {};
    public static Class THIS_CLASS = EnvironmentChangedListener.class;

    /**
     * called when the enivronment changes
     */
    public void onEnvironmentChanged();

}
