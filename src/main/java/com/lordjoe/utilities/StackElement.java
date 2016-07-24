package com.lordjoe.utilities;

import java.util.*;


/**
 * represents one item on the stack
 */
public class StackElement {
    public Class m_Class;
    public String m_Method;
    public int m_Line;

    public StackElement() {
    }

    public StackElement(String s) {
        if (s.charAt(0) == 9)
            s = s.substring(1);
        if (s.startsWith("at"))
            s = s.substring(2);
        int ParenIndex = s.indexOf("(");
        int LastDot = s.lastIndexOf(".", ParenIndex);
        String ClassName = s.substring(0, LastDot).trim();
        String MethodName = s.substring(LastDot + 1, ParenIndex).trim();
        int LineIndex = s.lastIndexOf(":");
        String LineNum = null;
        if (LineIndex > 0) {
            LineNum = s.substring(LineIndex + 1);
            LineNum = LineNum.substring(0, LineNum.lastIndexOf(")"));
        }

        m_Method = MethodName;
        if (LineNum != null)
            m_Line = Integer.parseInt(LineNum);
        try {
            m_Class = Class.forName(ClassName);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(s);
        }
    }

    public static boolean isStackElement(String s) {
        return (s.charAt(0) == 9 && s.indexOf("at") == 1 &&
                s.indexOf("(") > 0 && s.indexOf(")") > 0);
    }

    /**
     * custom string
     * @return non-null string similar to stack dump
     */
    public String toString() {
        StringBuffer st = new StringBuffer();
        st.append(m_Class.getName());
        st.append(".");
        st.append(m_Method);
        st.append("(");
        if (m_Line > 0) {
            st.append("Line:");
            st.append(m_Line);
        }
        st.append(")");
        return (st.toString());
    }
}
