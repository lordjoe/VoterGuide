package com.lordjoe.utilities;

/**
 * Holds a value which makrs whether the value has been changed or
 * nulled
 * @see ValueMap
 * @author Steve Lewis
 */
public class ValueHolder implements java.io.Serializable {
    private Object m_Value;
    private transient boolean m_Altered;

    public ValueHolder() {
    }

    public ValueHolder(Object value) {
        this();
        setValue(value);
    }

    public boolean isAltered() {
        return (m_Altered);
    }

    public void clearAltered() {
        m_Altered = false;
    }

    public Object getValue() {
        return (m_Value);
    }

    public void setValue(Object in) {
        if (in != null) {
            if (m_Value != null) {
                if (m_Value.equals(in)) {
                    return;
                }
            } else {
                if (in == null)
                    return;
            }
        } else {
            if (m_Value == null)
                return;
        }
        m_Value = in;
        m_Altered = true;
    }


    public boolean equals(Object test) {
        if(test == null)
              return false;
         if (!(test instanceof ValueHolder))
            return (false);
        ValueHolder realTest = (ValueHolder) test;
        if (m_Value != null)
            return (m_Value.equals(realTest.m_Value));
        else
            return (realTest.m_Value == null);
    }

    public int hashCode() {
        if (m_Value != null)
            return (m_Value.hashCode());
        else
            return (super.hashCode());
    }

}
