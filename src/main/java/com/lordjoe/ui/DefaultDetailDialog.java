package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


/**
 * com.lordjoe.ui.DefaultDetailDialog
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DefaultDetailDialog extends JDialog implements IDefaultComponent {
    public static final Class THIS_CLASS = DefaultDetailDialog.class;
    public static final DefaultDetailDialog EMPTY_ARRAY[] = {};


    private final IDefaultComponent m_ParentFrame;
    private DefaultStatusbar m_StatusBar;
    private JButton m_Close;
    private JPanel m_MainPanel;
     private final JPanel m_ActionPanel;
    private String m_TitleString;
     public DefaultDetailDialog(String title, IDefaultComponent parent)
    {
        super(UIUtilities.buildParentFrame(parent));
        m_ParentFrame = parent;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitleString(title);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        m_MainPanel = new JPanel();
        m_MainPanel.setLayout(new BorderLayout());
        pane.add(m_MainPanel, BorderLayout.CENTER);

        m_ActionPanel = buildActionPanel();
        pane.add(m_ActionPanel, BorderLayout.SOUTH);

    //     getResourceBundle(String applicationName)
        setStatus("Loaded");
     }

         public DefaultDetailDialog(String title,JFrame parent)
    {
        super(parent);
        m_ParentFrame = null;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitleString(title);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        m_MainPanel = new JPanel();
        m_MainPanel.setLayout(new BorderLayout());
        pane.add(m_MainPanel, BorderLayout.CENTER);

        m_ActionPanel = buildActionPanel();
        pane.add(m_ActionPanel, BorderLayout.SOUTH);

    //     getResourceBundle(String applicationName)
        setStatus("Loaded");
     }
    public DefaultDetailDialog(String title)
   {
       super();
       m_ParentFrame = null;
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setTitleString(title);

       Container pane = getContentPane();
       pane.setLayout(new BorderLayout());

       m_MainPanel = new JPanel();
       m_MainPanel.setLayout(new BorderLayout());
       pane.add(m_MainPanel, BorderLayout.CENTER);

       m_ActionPanel = buildActionPanel();
       pane.add(m_ActionPanel, BorderLayout.SOUTH);

   //     getResourceBundle(String applicationName)
       setStatus("Loaded");
    }

    protected JPanel buildActionPanel()
    {
        JPanel ret = new JPanel();
        ret.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        m_Close = new JButton();
        m_Close.setAction(UIUtilities.buildHideAction(this));

        buttonPanel.add(m_Close);
        ret.add(buttonPanel,BorderLayout.CENTER);
        m_StatusBar = new DefaultStatusbar(this);
        ret.add(m_StatusBar, BorderLayout.SOUTH);
        return ret;

    }

    public String getTitleString()
    {
        return m_TitleString;
    }

    public void setTitleString(String name)
    {
        m_TitleString = name;
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return m_ParentFrame.getDefaultFrame();
    }

    public void setStatus(String text)
    {
        m_StatusBar.setText(text);
    }


    public ResourceBundle getResources()
    {
        return getDefaultFrame().getResources();
    }

    public String getResourceString(String nm)
     {
          return getDefaultFrame().getResourceString(nm);
     }

    public Object getResourceObject(String nm)
     {
         return getDefaultFrame().getResourceObject(nm);
      }


    public void setMainDisplay(JPanel panel)
    {
        m_MainPanel.removeAll();
        m_MainPanel.add(panel, BorderLayout.CENTER);
    }


}