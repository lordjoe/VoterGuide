package com.lordjoe.general;

/**
 * com.lordjoe.general.ISynthesizableSequence
 *
 * @author Steve Lewis
 * @date Jul 26, 2006
 */
public interface ISynthesizableSequence
{
    public static final ISynthesizableSequence[] EMPTY_ARRAY = {};

    char getSequenceChar(int i);

    boolean isSynthesizable();
}
