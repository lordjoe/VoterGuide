package com.lordjoe.lang;

/**
 * com.lordjoe.lang.IState
 * largely a tagging interface this is a base interface for devices reporting
 * state
 *
 * @author slewis
 * @date Jan 7, 2005
 */
public interface IState extends IEquivalent
{
    public static final Class THIS_CLASS = IState.class;
    public static final IState EMPTY_ARRAY[] = {};

    /**
     * true if the state can be determined otherwise false
     *
     * @return as above
     */
    public boolean isKnown();

    /**
     * returning false says state is not relevant for
     * comparisons - used in equivalent
     *
     * @return as above
     */
    public boolean isApplicable();


    /**
     * return a description of the current state
     * @return
     */
    public String getStateDescription();

    /**
     * return a description of the state
     * @return
     */
    public String getDescription();

}