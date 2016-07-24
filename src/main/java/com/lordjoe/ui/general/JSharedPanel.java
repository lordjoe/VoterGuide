package com.lordjoe.ui.general;

import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * com.lordjoe.ui.general.JSharedPanel
 * panel backed by a Card Layout
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public class JSharedPanel extends JPanel implements IDisplayStack {
    public static JSharedPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JSharedPanel.class;

    private final CardLayout m_Layout;
    private final Map<String, JComponent> m_Components;
    private final Stack<String> m_DisplayStack;
    private String m_DefaultName;

    private final List<IDisplayStackChangeListener> m_IDisplayStackChangeListeners;

    public JSharedPanel() {
        m_Layout = new CardLayout();
        m_Components = new HashMap<String, JComponent>();
        m_DisplayStack = new Stack<String>();
        setLayout(m_Layout);
        m_IDisplayStackChangeListeners = new CopyOnWriteArrayList<IDisplayStackChangeListener>();
    }

    // Add to constructor
    // m_IDisplayStackChangeListeners = new CopyOnWriteArrayList<IDisplayStackChangeListener>();


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addIDisplayStackChangeListener(IDisplayStackChangeListener added) {
        if (!m_IDisplayStackChangeListeners.contains(added))
            m_IDisplayStackChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeIDisplayStackChangeListener(IDisplayStackChangeListener removed) {
        while (m_IDisplayStackChangeListeners.contains(removed))
            m_IDisplayStackChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyIDisplayStackChangeListeners(Object changeTHis) {
        if (m_IDisplayStackChangeListeners.isEmpty())
            return;
        for (IDisplayStackChangeListener listener : m_IDisplayStackChangeListeners) {
            listener.onDisplayStackChange(this);
        }
    }

    public JComponent getComponent(String name) {
        return m_Components.get(name);
    }

    /**
     * return the number of elements on the stack
     *
     * @return as above
     */
    public int getStackSize() {
        return m_DisplayStack.size();
    }

    /**
     * return the names of the stacked items
     *
     * @return as above
     */
    public String[] getStackItems() {
        String[] items = new String[m_DisplayStack.size()];
        m_DisplayStack.toArray(items);
        List<String> holder = new ArrayList<String>();
        for (int i = 0; i < items.length; i++) {
            String s = items[i];
            holder.add(ResourceString.parameterToText(s));

        }

        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    /**
     * set what to show if there is nothing in the display statc
     *
     * @param name component name
     * @param c    component
     */
    public void setDefaultComponent(String name, JComponent c) {
        addComponent(name, c);
        m_DefaultName = name;
        // m_Layout.addLayoutComponent(c,name);
    }

    public void addComponent(String name, JComponent c) {
        m_Components.put(name, c);
        add(c, name);
        // m_Layout.addLayoutComponent(c,name);
    }

    public void removeComponent(String name, JComponent c) {
        m_Components.remove(name);
        remove(c);
        // m_Layout.removeLayoutComponent(c);
    }

    public void showComponent(final String name) {
        if (SwingUtilities.isEventDispatchThread()) {
            m_Layout.show(JSharedPanel.this, name);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    m_Layout.show(JSharedPanel.this, name);
                }
            });
        }

    }

    /**
     * show the previously displayed comonent
     */
    public void popComponent() {
        if (getStackSize() > 0) {
            m_DisplayStack.pop();
            if (getStackSize() > 0)
                showComponent(m_DisplayStack.peek());
            else
                showComponent(m_DefaultName);
        }
        notifyIDisplayStackChangeListeners(this);
    }

    /**
     * push the component onto a component stack
     *
     * @param name
     * @param c
     */
    public void pushComponent(String name, JComponent c) {
        if (m_Components.get(name) == null) ;
        addComponent(name, c);
        m_DisplayStack.push(name);
        showComponent(m_DisplayStack.peek());
        notifyIDisplayStackChangeListeners(this);
    }

    public void paint(Graphics g) {
        super.paint(g);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void clearStack() {
        m_DisplayStack.clear();
        if (m_DefaultName != null)
            showComponent(m_DefaultName);
        String[] items = m_Components.keySet().toArray(new String[0]);
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (item.equals(m_DefaultName))
                continue;
            JComponent c = m_Components.get(item);
            removeComponent(item, c);
        }
    }


}
