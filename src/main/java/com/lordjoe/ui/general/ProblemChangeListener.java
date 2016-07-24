package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.ProblemChangeListener
 *
 * @author slewis
 * @date May 3, 2005
 */
public interface ProblemChangeListener
{
    public static final Class THIS_CLASS = ProblemChangeListener.class;
    public static final ProblemChangeListener EMPTY_ARRAY[] = {};

    /**
     * what to do when problem status changes
     * @param handler
     */
    public void onProblemChange(IProblemHandler handler);

}