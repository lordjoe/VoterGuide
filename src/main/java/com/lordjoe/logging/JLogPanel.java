package com.lordjoe.logging;

import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import com.lordjoe.ui.*;

/**
 * com.lordjoe.logging.JLogPanel
 *
 * @author Steve Lewis
 * @date Jun 5, 2008
 */
public class JLogPanel extends JPanel
{
    public static final Class THIS_CLASS = JLogPanel.class;
    public static final JLogPanel EMPTY_ARRAY[] = {};
    public static final String CAMERA_IMAGE_NAME = "camera_icon.gif";
    private final JButton m_Snapshot1;
    private final JButton m_Snapshot2;
    private final JTextArea m_Text;
    private final TitledBorder m_Border;
    private final JPanel m_UpperPanel;
    private final JPanel m_SnapshotOnly;
    private final CardLayout m_Cards;

    public JLogPanel()
    {
        m_Snapshot1 = new JButton(new CameraAction());
        m_Snapshot2 = new JButton(new CameraAction());

        m_Text = new JTextArea(24, 80);
        setLayout(new BorderLayout());
        m_UpperPanel = new JPanel();
        m_Cards = new CardLayout();
        m_UpperPanel.setLayout(m_Cards);
        m_SnapshotOnly = new JPanel();
        m_SnapshotOnly.add(m_Snapshot1);
        m_UpperPanel.add(m_SnapshotOnly,"SnapShot");
        m_Cards.show(m_UpperPanel,"SnapShot");

        add(m_UpperPanel, BorderLayout.NORTH);
        add(new JScrollPane(m_Text), BorderLayout.CENTER);
        m_Border = new TitledBorder(new EtchedBorder());
        setBorder(m_Border );
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

