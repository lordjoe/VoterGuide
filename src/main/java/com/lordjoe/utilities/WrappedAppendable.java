package com.lordjoe.utilities;

import java.io.*;

/**
 * com.lordjoe.utilities.WrappedAppendable
 *   my appendable - used to act as a
 *  facade to an appendable usually for instrumentation purposes
 * @see InstrumentedWrappedAppendable
 * @author Steve Lewis
 * @date Mar 9, 2008
 */
public class WrappedAppendable implements Appendable {
    public static WrappedAppendable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = WrappedAppendable.class;

    private final Appendable m_Sb;
    public WrappedAppendable(Appendable worker)
    {
       m_Sb = worker;
    }


    public Appendable append(CharSequence csq) throws IOException  {
        m_Sb.append(csq);
        return this;
    }

    public Appendable append(CharSequence csq, int start, int end) throws IOException  {
        m_Sb.append(csq,start,end);
        return this;
    }

    public Appendable append(char c)  throws IOException {
        m_Sb.append(c);
        return this;
    }

    @Override
    public String toString()
    {
        return m_Sb.toString();
    }
}
