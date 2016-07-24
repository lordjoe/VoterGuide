package com.lordjoe.utilities;

import java.io.*;

/**
 * com.lordjoe.utilities.InstrumentedWrappedAppendable
 *    Add Break points - this is used to tract whan
 *  creatain Strings are
 * @author Steve Lewis
 * @date Mar 9, 2008
 */
public class InstrumentedWrappedAppendable extends WrappedAppendable {
    public static InstrumentedWrappedAppendable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = InstrumentedWrappedAppendable.class;

    private String[] m_BreakPoints;

    public InstrumentedWrappedAppendable(Appendable worker) {
        super(worker);
        m_BreakPoints = Util.EMPTY_STRING_ARRAY;
    }


    public synchronized void addBreakPoint(String s) {
        m_BreakPoints = Util.addToArray(m_BreakPoints, s);
    }

    public Appendable append(CharSequence csq)  throws IOException {
        String[] tests = m_BreakPoints;
        if (tests.length > 0) {
            String testStr = csq.toString();
            for (int i = 0; i < tests.length; i++) {
                String test = tests[i];
                if (testStr.contains(test))
                    Util.breakHere();

            }
        }
        return super.append(csq);

    }
}
