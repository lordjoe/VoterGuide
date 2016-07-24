package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.LimitedString
 *
 * @author Steve Lewis
 * @date Dec 17, 2007
 */
public class LimitedString {
    public static LimitedString[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = LimitedString.class;
    public static final int DEFAULT_LENGTH = 256;
    public static final int DEFAULT_LONG_LENGTH = 8096;

    private final int m_MaxLength;
    private String m_Value;
    private boolean m_Locked;
    public LimitedString() {
        this(null,DEFAULT_LENGTH);
    }
    public LimitedString(String s) {
        this(s,DEFAULT_LENGTH);
    }
    public LimitedString(int maxLength) {
        this(null,maxLength);
    }
    public LimitedString(String s,int maxLength) {
        m_MaxLength = maxLength;
        setValue(s);
    }

    public boolean isLocked() {
        return m_Locked;
    }

    public void setLocked(boolean pLocked) {
        m_Locked = pLocked;
    }

    public boolean isNull()
    {
        return getValue() == null;
    }

    public String getValue() {
        return m_Value;
    }

    public void setValue(String pValue) {
        if(Util.objectEqual(m_Value,pValue))
             return;
        if(isLocked())
            throw new IllegalStateException("Limited String is locked and cannot be set"); 
        if(pValue != null && pValue.length() > m_MaxLength)
            throw new IllegalArgumentException("String " + pValue +
              " exceeds Maximum length " +   m_MaxLength);
        m_Value = pValue;
    }

    public int getMaxLength() {
        return m_MaxLength;
    }


    public int hashCode() {
        if(m_Value == null)
            return 0;
        return m_Value.hashCode();

    }

    public String toString() {
        if(m_Value == null)
            return "null";
        return m_Value;

    }

    public boolean equals(Object obj) {
        if(getClass() != obj.getClass())
            return false;
        return Util.objectEqual(m_Value,((LimitedString)obj).getValue());
    }
}
