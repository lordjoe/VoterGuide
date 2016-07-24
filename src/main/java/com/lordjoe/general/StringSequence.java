package com.lordjoe.general;

/**
 * com.nanomaterials.general.PackedSequence
 *
 * @author slewis
 * @date May 25, 2005
 */
public class StringSequence  implements ISynthesizableSequence
{
    public static final Class THIS_CLASS = StringSequence.class;
    public static final StringSequence EMPTY_ARRAY[] = {};

    private final int m_Hash;
    private final String m_Sequence;
    public StringSequence(String s)
    {
        m_Sequence = s;
        m_Hash = s.hashCode();
    }


    public char getSequenceChar(int i)
    {
        if(i < m_Sequence.length())
            return m_Sequence.charAt(i);
        return 0;
    }

     public boolean isSynthesizable()
    {

        return true;
    }

    public String toString()
    {
        return m_Sequence;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return true;
        if(obj == this)
            return true;
        if(getClass() != obj.getClass())
            return false;
        ISynthesizableSequence seq = (ISynthesizableSequence)obj;
        return seq.toString().equals(toString());

    }


    public int hashCode()
    {
        return m_Hash;

    }
}
