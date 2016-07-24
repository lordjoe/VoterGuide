package com.lordjoe.lang;

/**
 * com.lordjoe.lang.StringPair
 *
 * @author Steve Lewis
 * @date Nov 21, 2007
 */
public class StringPair {
    public final static StringPair[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = StringPair.class;

    private final String m_P1;
    private final String m_P2;

    public StringPair(String p1, String p2) {
        m_P1 = p1;
        m_P2 = p2;
    }

    public StringPair(String p1p2) {
        String[] items = p1p2.split(",");
        m_P1 = items[0];
        m_P2 = items[1];
    }

    public String getP1() {
        return m_P1;
    }

    public String getP2() {
        return m_P2;
    }

    public int hashCode() {
        return getP1().hashCode() ^ getP2().hashCode();

    }

    public boolean equals(Object obj) {
        if (obj.getClass() != getClass())
            return false;
        StringPair realObj = (StringPair) obj;
        return realObj.getP1().equals(getP1()) &&
                realObj.getP2().equals(getP2());

    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return getP1() + "," + getP2();

    }
}
