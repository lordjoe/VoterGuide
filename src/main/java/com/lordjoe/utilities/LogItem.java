package com.lordjoe.utilities;

import java.util.*;

public class LogItem  implements INameable
{
    private final String m_Name;
    private int m_Count;

    public LogItem(String Name) {
        m_Name = Name;
    }

    public String getName() {
        return (m_Name);
    }

    public int increment() {
        return (m_Count++);
    }

    public int decrement() {
        return (m_Count--);
    }


    public int reset() {
        int oldCount = m_Count;
        m_Count = 0;
        return (oldCount);
    }

    public int getCount() {
        return (m_Count);
    }
}
