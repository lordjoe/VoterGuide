package com.lordjoe.ui.general;

import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;


/**
 * com.lordjoe.ui.general.JLogPanel
 *
 * @author slewis
 * @date May 2, 2005
 */
public class JLogPanel extends JPanel
{
    public static final Class THIS_CLASS = JLogPanel.class;
    public static final JLogPanel EMPTY_ARRAY[] = {};
    public static final String CAMERA_IMAGE_NAME = "camera_icon.gif";
    private final JButton m_Snapshot;
    private final JTextArea m_Text;
    private final TitledBorder m_Border;
    private final JPanel m_UpperPanel;

    public JLogPanel()
    {
        m_Snapshot = new JButton(new CameraAction());
        m_Text = new JTextArea(24, 80);
        setLayout(new BorderLayout());
        m_UpperPanel = new JPanel();
        m_UpperPanel.add(m_Snapshot);
        add(m_UpperPanel, BorderLayout.NORTH);
        add(new JScrollPane(m_Text), BorderLayout.CENTER);
        m_Border = new TitledBorder(new EtchedBorder());
        setBorder(m_Border );
   }

    public JPanel getUpperPanel()
    {
        return m_UpperPanel;
    }

    public String getTitle()
    {
        String title = m_Border.getTitle();
        if (title == null)
            return "Snapshot";
        return title;
    }

    public void setTitle(String title)
    {
        m_Border.setTitle(title);
    }

    public void clear()
    {
        m_Text.setText("");
    }

    public void setText(String text)
    {
        m_Text.setText(text);
    }

    public void append(String text)
    {
        m_Text.append(text);
    }

    public String getText()
    {
        return m_Text.getText();
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
            JConsole console = new JConsole(getTitle(), getText());
            console.setVisible(true);
        }
    }


}
