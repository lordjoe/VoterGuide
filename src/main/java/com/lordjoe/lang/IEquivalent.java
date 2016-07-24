package com.lordjoe.lang;

/**
 * com.lordjoe.lang.IEquivalent
 * allows testing for equivalence - this is important in
 * units tests since it allows a weaker comparisin than
 * equals -
 * equivalent can all errors and ignore some fields such as creation time
 * @author slewis
 * @date Dec 23, 2004
 */
public interface IEquivalent
{
    public static final Class THIS_CLASS = IEquivalent.class;
    public static final IEquivalent EMPTY_ARRAY[] = {};

    /**
     * similar to equals but will not allow use in hashes
     * @param test non-null test object
     * @return true of test is equivalent to this
     */
    public boolean equivalent(Object test);

}