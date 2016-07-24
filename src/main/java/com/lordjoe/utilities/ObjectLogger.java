package com.lordjoe.utilities;

import java.util.*;

/**
 * Class to count how many objects of a given class are alive
 * @author Steve Lewis
 */
public class ObjectLogger implements INameable
{
    /**
     * global to turn on more intense logging
     */
    protected static Map gLoggers = new HashMap();

    public static String getObjectLoggerReport() {
        StringBuffer sb = new StringBuffer();
        String[] keys = Util.getKeyStrings(gLoggers);
        for (int i = 0; i < keys.length; i++) {
            ObjectLogger item = (ObjectLogger) gLoggers.get(keys[i]);
            if (!item.isDisabled())
                sb.append(item.report());
        }
        return (sb.toString());
    }

    private final String m_Name;
    private int m_Total;
    private int m_Live;
    private boolean m_Disabled;
    private CounterMap m_Properties;

    public ObjectLogger(String name) {
        m_Name = name;
        gLoggers.put(name, this);
    }

    public ObjectLogger(String name, boolean disable) {
        this(name);
        setDisabled(disable);
    }

    public String getName() {
        return (m_Name);
    }

    public int getTotal() {
        return (m_Total);
    }

    public int getLive() {
        return (m_Live);
    }

    public int getKilled() {
        return (m_Total - m_Live);
    }

    public boolean isDisabled() {
        return (m_Disabled);
    }

    public void setDisabled(boolean in) {
        m_Disabled = in;
    }

    public synchronized void add() {
        m_Total++;
        m_Live++;
    }

    public synchronized void drop() {
        m_Live--;
    }

    public synchronized void addProperty(String name) {
        if (isDisabled())
            return; // this code is expensive

        if (m_Properties == null)
            m_Properties = new CounterMap();
        m_Properties.add(name);
    }

    public synchronized void dropProperty(String name) {
        if (isDisabled())
            return; // this code is expensive

        if (m_Properties == null)
            return;
        m_Properties.drop(name);
    }

    public synchronized String reportProperties() {
        StringBuffer sb = new StringBuffer();
        String[] keys = m_Properties.keys();
        for (int i = 0; i < keys.length; i++) {
            sb.append(keys[i] + " = " + m_Properties.value(keys[i]));
            sb.append("\n");
        }
        return (sb.toString());
    }

    public synchronized String report() {
        StringBuffer sb = new StringBuffer();
        sb.append("Logger: " + getName());
        sb.append(" Total " + getTotal());
        sb.append(" Live " + getLive());
        int killed = getKilled();
        sb.append(" killed " + killed + "\n");
        if (m_Properties != null)
            sb.append(reportProperties());
        return (sb.toString());
    }

}