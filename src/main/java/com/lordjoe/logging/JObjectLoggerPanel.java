package com.lordjoe.logging;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.IInfoTextBuilder;
import com.lordjoe.ui.general.RadioButtonCollection;
import com.lordjoe.utilities.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

/**
 * com.lordjoe.logging.JLoggerPanel
 *
 * @author Steve Lewis
 * @date Jun 5, 2008
 */
public class JObjectLoggerPanel<T> extends JObjectLogPanel<T>   implements ObjectLogChangedListener, IInfoTextBuilder,
        ItemListener
{
    public static JLoggerPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JLoggerPanel.class;

    private IObjectLogger<T> m_Logger;
    private ILoggedObjectFilter<T>[] m_Filters;
    private RadioButtonCollection m_FilterButtons;
    private JPanel m_SnapshotAndSelection;

    public JObjectLoggerPanel(T[] type)
    {
        super(type);
        setOpaque(false);
        m_Filters = ILoggedObjectFilter.EMPTY_ARRAY;
        m_SnapshotAndSelection = new JPanel();
        buildSelectionPanel(m_SnapshotAndSelection);
        getUpperPanel().add(m_SnapshotAndSelection, "SnapShotAndButtons");
        getCards().show(getUpperPanel(), "SnapShot");
    }



    
    public JObjectLoggerPanel(IObjectLogger logger)
    {
        this((T[])logger.getTypeArray());
        setLogger(logger);
   }

    public IObjectLogger<T> getLogger()
    {
        return m_Logger;
    }

    protected void buildSelectionPanel(JPanel p)
    {
         super.buildSelectionPanel(p);
   }

    /**
     * do whatever is called for when a command is sent
     *
     * @param evt non-null event
     */
    public void onLogChanged(ObjectLogChangedEvent evt)
    {
        LoggedObject text = evt.getAdded();
        for (int i = 0; i < m_Filters.length; i++) {
            ILoggedObjectFilter<T> filter = m_Filters[i];
            if(!filter.acceptable(text))
                return;          
        }
        append(text);

    }

    /**
     * act when log is cleared
     */
    public void onLogCleared()
    {
        clear();

    }

    public void setLogger(IObjectLogger<T> logger)
    {
        if (m_Logger == logger)
            return;
        if (m_Logger != null)
            m_Logger.removeObjectLogChangedListener(this);
        m_Logger = logger;
        if (m_Logger != null)
            m_Logger.addObjectLogChangedListener(this);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                LoggedObject<T>[] objects = m_Logger.getObjects();
                setObjects(objects);
                getCards().show(getUpperPanel(), "SnapShot");
            }

        });
    }

    public String getTitle()
    {
        if(m_Logger != null)
            return m_Logger.getName();
        return super.getTitle();

    }

    public ILoggedObjectFilter[] getFilters()
    {
        return m_Filters;
    }

    public synchronized void addFilter(ILoggedObjectFilter pFilter)
    {
        m_Filters = Util.addToArray(m_Filters,pFilter);
    }

    public synchronized void removeFilter(ILoggedObjectFilter pFilter)
    {
        m_Filters = (ILoggedObjectFilter[])Util.removeFromToArray(m_Filters,pFilter);
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent e)
    {
        throw new UnsupportedOperationException("Fix This"); // ToDo
//        if (e.getStateChange() != ItemEvent.SELECTED)
//            return;
//        ILoggedObjectFilter filter = (ILoggedObjectFilter) e.getItem();
//        setFilter(filter);
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
        private final LoggedObject<T> m_Text;

        public DoAppend(LoggedObject<T> text)
        {
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
        if (!asChild) {
            sb.append(UIUtilities.buildParentInfoText(this, indent + 1));
        }
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
        IObjectLogger subjectO = getLogger();
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