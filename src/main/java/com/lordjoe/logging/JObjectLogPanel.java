package com.lordjoe.logging;

import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.reflect.*;

import com.lordjoe.ui.*;

/**
 * com.lordjoe.logging.JLogPanel
 *
 * @author Steve Lewis
 * @date Jun 5, 2008
 */
public class JObjectLogPanel<T> extends JPanel
{
    public static final Class THIS_CLASS = JLogPanel.class;
    public static final JLogPanel EMPTY_ARRAY[] = {};
    public static final String CAMERA_IMAGE_NAME = "camera_icon.gif";
    public static final int MAX_HEIGHT = 250;

    private final JButton m_Snapshot1;
    private final JButton m_Snapshot2;
    private final DefaultListModel m_ObjectModel;
    private final JList m_List;
    private final TitledBorder m_Border;
    private final JPanel m_UpperPanel;
    private final JPanel m_SnapshotOnly;
    private final CardLayout m_Cards;
    private final T[] m_Type;

    public JObjectLogPanel(T[] type)
    {
        m_Type = type;
        m_Snapshot1 = new JButton(new CameraAction());
        m_Snapshot1.setOpaque(false);

        // Why do we need a second button SL
        m_Snapshot2 = new JButton(new CameraAction());
        m_Snapshot2.setOpaque(false);

        m_ObjectModel = new DefaultListModel();
        m_List = new JList(m_ObjectModel);
        Class typeClass = getTypeClass();
        ListCellRenderer renderer = LoggingUtilities.getCellRenderer(typeClass);
        if (renderer != null)
            m_List.setCellRenderer(renderer);
        else
            renderer = LoggingUtilities.getCellRenderer(typeClass); // break here
        setLayout(new BorderLayout());
        m_UpperPanel = new JPanel();
        m_UpperPanel.setOpaque(false);
        m_Cards = new CardLayout();
        m_UpperPanel.setLayout(m_Cards);
        m_SnapshotOnly = new JPanel();
        m_SnapshotOnly.setOpaque(false);
        m_SnapshotOnly.add(m_Snapshot1);
        m_UpperPanel.add(m_SnapshotOnly, "SnapShot");
        m_Cards.show(m_UpperPanel, "SnapShot");

        add(m_UpperPanel, BorderLayout.NORTH);
        JScrollPane comp = new JScrollPane(m_List);
        ScrollPanel sc = new ScrollPanel(comp);

        add(sc, BorderLayout.CENTER);
        m_Border = new TitledBorder(new EtchedBorder());
        setBorder(m_Border);
    }

    public class ScrollPanel extends JPanel
    {
        public ScrollPanel(JComponent com)
        {
            setLayout(new GridLayout(1, 1, 0, 0));
            add(com);
        }

        /**
         * If the <code>preferredSize</code> has been set to a
         * non-<code>null</code> value just returns it.
         * If the UI delegate's <code>getPreferredSize</code>
         * method returns a non <code>null</code> value then return that;
         * otherwise defer to the component's layout manager.
         *
         * @return the value of the <code>preferredSize</code> property
         * @see #setPreferredSize
         * @see javax.swing.plaf.ComponentUI
         */
        @Override
        public Dimension getPreferredSize()
        {
            Dimension preferredSize = super.getPreferredSize();
            return new Dimension(preferredSize.width,
                    Math.min( MAX_HEIGHT - 60,
                            preferredSize.height));

        }


        /**
         * If the <code>preferredSize</code> has been set to a
         * non-<code>null</code> value just returns it.
         * If the UI delegate's <code>getPreferredSize</code>
         * method returns a non <code>null</code> value then return that;
         * otherwise defer to the component's layout manager.
         *
         * @return the value of the <code>preferredSize</code> property
         * @see #setPreferredSize
         * @see javax.swing.plaf.ComponentUI
         */
        @Override
        public Dimension getMaximumSize()
        {
            Dimension preferredSize = super.getMaximumSize();
            return new Dimension(preferredSize.width,
                    Math.min(MAX_HEIGHT - 60,
                            preferredSize.height));

        }

    }

    protected CardLayout getCards()
    {
        return m_Cards;
    }


    protected void buildSelectionPanel(JPanel p)
    {
        p.add(m_Snapshot2);
    }

    public JPanel getUpperPanel()
    {
        return m_UpperPanel;
    }


    public String getTitle()
    {
        String title = m_Border.getTitle();
        if (Util.isEmptyString(title))
            return "Snapshot";
        return title;
    }

    public void setTitle(String title)
    {
        m_Border.setTitle(title);
    }

    public void clear()
    {
        synchronized (m_ObjectModel) {
            m_ObjectModel.clear();
        }
    }


    public void append(LoggedObject<T> text)
    {
        synchronized (m_ObjectModel) {
            m_ObjectModel.addElement(text);
        }
    }

    public void setObjects(LoggedObject<T>[] text)
    {
        synchronized (m_ObjectModel) {
            clear();
            for (int i = 0; i < text.length; i++) {
                LoggedObject<T> tLoggedObject = text[i];
                m_ObjectModel.addElement(tLoggedObject);

            }
        }
    }

    public Class getTypeClass()
    {
        return getType().getClass().getComponentType();
    }

    public T[] getType()
    {
        return m_Type;
    }

    public LoggedObject<T>[] getList()
    {
        synchronized (m_ObjectModel) {
            LoggedObject<T>[] ret = (LoggedObject<T>[]) Array.newInstance(LoggedObject.class,
                    m_ObjectModel.size());
            m_ObjectModel.copyInto(ret);
            return ret;
        }
    }

    public class CameraAction extends AbstractAction
    {
        public CameraAction()
        {
            ImageIcon img = ResourceImages.getTransparentImage(CAMERA_IMAGE_NAME);
            putValue(Action.SMALL_ICON, img);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            String title = getTitle();
            LoggedObject<T>[] ts = getList();
            JObjectLogSnapshot<T> console = new JObjectLogSnapshot<T>(title, ts,
                    m_List.getCellRenderer());
            console.setVisible(true);
        }
    }


}