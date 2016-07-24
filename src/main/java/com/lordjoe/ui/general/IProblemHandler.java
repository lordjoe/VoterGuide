package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.IProblemHandler
 *
 * @author slewis
 * @date May 2, 2005
 */
public interface IProblemHandler
{
    public static final Class THIS_CLASS = IProblemHandler.class;
    public static final IProblemHandler EMPTY_ARRAY[] = {};


    /**
      * return if there are no problems
      * @return as above
      */
     public boolean isNoProblems();

    /**
      * as below
      * @return null if no problems otherwise a list of problems
      */
     public String getProblems();

    /**
     * adds a listerer
     * @param added
     */
    public void addProblemChangeListener(ProblemChangeListener added);

    /**
     * removes a listener
     * @param removed
     */
    public void removeProblemChangeListener(ProblemChangeListener removed);


}