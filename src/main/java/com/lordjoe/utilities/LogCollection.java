package com.lordjoe.utilities;

import java.util.*;

public class LogCollection {
    private Map m_Items;
    private List m_SubCollections;

    public LogCollection() {
        m_Items = new HashMap();
        m_SubCollections = new ArrayList();

    }

    public String[] getItemNames() {
        SortedSet holder = new TreeSet();
        holder.addAll(m_Items.keySet());
        Iterator it = m_SubCollections.iterator();
        while (it.hasNext()) {
            LogCollection test = (LogCollection) it.next();
            holder.addAll(java.util.Arrays.asList(test.getItemNames()));
        }
        String[] ret = Util.collectionToStringArray(holder);
        return (ret);
    }

    public LogItem getItem(String Name) {
        LogItem ret = (LogItem) m_Items.get(Name);
        if (ret != null)
            return (ret);
        Iterator it = m_SubCollections.iterator();
        while (it.hasNext()) {
            LogCollection test = (LogCollection) it.next();
            ret = test.getItem(Name);
            if (ret != null)
                return (ret);
        }
        return (null);
    }

    public void addItem(String Name) {
        if (getItem(Name) != null)
            return;
        m_Items.put(Name, new LogItem(Name));
    }

    public void incrementItem(String Name) {
        LogItem TheItem = getItem(Name);
        if (TheItem == null)
            throw new IllegalArgumentException("LogItem " + Name + " not Found");
        TheItem.increment();
    }

    public void addCollection(LogCollection Added) {
        if (Added == this)
            throw new IllegalArgumentException("Cannot add a logger to itself");
        if (m_SubCollections.contains(Added))
            return;
        m_SubCollections.add(Added);
    }
}
