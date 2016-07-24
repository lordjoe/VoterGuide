package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.ISequence
 *
 * @author John Connor
 *         created Jul 30, 2007
 */
public interface ISequence {
    public static ISequence[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ISequence.class;

    /**
     * returns the next value for a sequence
     * @return a value queranteed to be unique in the sequence
     */
    public int nextValue();
}