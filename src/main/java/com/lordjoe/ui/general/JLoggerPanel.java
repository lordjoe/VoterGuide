package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.ILoggerObject;
import com.lordjoe.lang.ITextFilter;
import com.lordjoe.ui.IInfoTextBuilder;
import com.lordjoe.ui.SwingThreadUtilities;
import com.lordjoe.utilities.LogChangedEvent;
import com.lordjoe.utilities.LogChangedListener;
import com.lordjoe.utilities.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;


/**
 * com.lordjoe.ui.general.JLoggerPanel
 *
 * @author slewis
 * @date May 2, 2005
 */
public class JLoggerPanel extends JLogPanel implements LogChangedListener, IInfoTextBuilder,
        ItemListener
{
    public static final Class THIS_CLASS = JLoggerPanel.class;
    public static final JLoggerPanel EMPTY_ARRAY[] = {};

    private ILoggerObject m_Logger;
    private ITextFilter m_Filter;
    private ITextFilter[] m_Filters;
    private RadioButtonCollection m_FilterButtons;

    public JLoggerPanel()
    {
    }

    public ILoggerObject getLogger()
    {
        return m_Logger;
    }

    public void setLogger(ILoggerObject logger)
    {
        if (m_Logger == logger)
            return;
        m_Filter = null;
        if (m_Logger != null)
            m_Logger.removeLogChangedListener(this);
        m_Logger = logger;
        if (m_Logger != null)
            m_Logger.addLogChangedListener(this);
        setText(m_Logger.getText());
        JPanel upperPanel = getUpperPanel();
        if (m_FilterButtons != null) {
            m_FilterButtons.removeItemListener(this);

            upperPanel.remove(m_FilterButtons);
            m_FilterButtons = null;
            upperPanel.doLayout();
            upperPanel.repaint(200);
        }
    }

    public ITextFilter getFilter()
    {
        return m_Filter;
    }

    public void setFilter(ITextFilter pFilter)
    {
        if (m_Filter == pFilter)
            return;
        m_Filter = pFilter;
        if (m_Filter == null)
            setText(getLogger().getText());
        else
            setText(m_Filter.applyFilter(getLogger().getText()));
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getStateChange() != ItemEvent.SELECTED)
            return;
        ITextFilter filter = (ITextFilter) e.getItem();
        setFilter(filter);
    }

    /**
     * do whatever is called for when a command is sent
     *
     * @param evt non-null event
     */
    public void onLogChanged(LogChangedEvent evt)
    {
        switch (evt.getType()) {
            case ILoggerObject.LOG_CLEARED :
                SwingThreadUtilities.invoke(new DoClear());
                return;
            case ILoggerObject.LOG_APPENDED :
                String text = evt.getText();
                if (m_Filter != null)
                   text = m_Filter.applyFilter(evt.getText());
                SwingThreadUtilities.invoke(new DoAppend(text));
                return;
            default:
                throw new IllegalArgumentException("Unknown event type");
        }
    }

    public class DoClear implements Runnable
    {
        public void run()
        {
            clear();
        }
    }
    public class DoAppend implements Runnable
    {
        private final String m_Text;
        public DoAppend(String text) {
            m_Text = text;
        }
        public void run()
        {
            append(m_Text);
        }
    }

    /**
     * build a string describing the current object
     *
     * @return
     */
    public String buildInfoText()
    {
        return buildInfoText(0, false);
    }

    /**
     * build a string describing the current object
     *
     * @param indent indent level
     * @return
     */
    public String buildInfoText(int indent, boolean asChild)
    {
        Set<Component> handled = new HashSet<Component>();
        StringBuffer sb = new StringBuffer();
        sb.append(buildSelfInfoText(indent));
        if (!asChild)
            sb.append(UIUtilities.buildParentInfoText(this, indent + 1));
        Component[] components = getComponents();
        if (components.length > 0) {
            for (Component c : components) {
                if (handled.contains(c))
                    continue;
                handled.add(c);
                sb.append(UIUtilities.buildInfoText(c, indent + 1, true, handled));
            }
        }
        return sb.toString();
    }


    /**
     * build a string describing the current object
     *
     * @param indent non-negative indent level
     * @return
     */
    public String buildSelfInfoText(int indent)
    {
        StringBuffer sb = new StringBuffer();
        Util.indentStringBuffer(sb, indent);
        sb.append("Class " + getClass().getName());
        sb.append("\n");
        ILoggerObject subjectO = getLogger();
        if (subjectO != null) {
            Util.indentStringBuffer(sb, indent);
            sb.append("Log " + subjectO.getName());
            sb.append("\n");
            Util.indentStringBuffer(sb, indent);
            sb.append("Log Class " + subjectO.getClass().getName());
            sb.append("\n");
        }
        return sb.toString();
    }

}
