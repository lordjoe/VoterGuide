package com.lordjoe.ui;

import javax.swing.*;
import java.awt.*;

import org.apache.log4j.*;
import java.awt.event.*;

public class DialogViewer extends JDialog
{
    private static final Logger logger = Logger.getLogger(DialogViewer.class);
    public static final String FILE_SEP = System.getProperty("file.separator");
    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea jTextAreaLog = new JTextArea();
    private JPanel jPanelFooter = new JPanel();
    private JPanel jPanelTitle = new JPanel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JButton jButtonExit = new JButton();
    private JLabel jLabelTitle = new JLabel();
    //private final String screenTitle = "Protocol File Viewer: ";


    public DialogViewer (JFrame owner, String title, boolean modal)
    {
        super(owner,title,modal);

        try
        {
            jbInit();
        }

        catch (Exception ex)
        {
            logger.error("",ex);
        }
    }
    public DialogViewer()
    {
        try
        {
             jbInit();
        }
        catch(Exception e)
        {
            logger.error("",e);
        }
    }
    private void jbInit() throws Exception {
        jPanelFooter.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jButtonExit.setBackground(Color.lightGray);
        jButtonExit.setMaximumSize(new Dimension(79, 21));
        jButtonExit.setMinimumSize(new Dimension(79, 21));
        jButtonExit.setPreferredSize(new Dimension(79, 21));
        jButtonExit.setText("Exit");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonExit_actionPerformed(e);
            }
        });

        jTextAreaLog.setEditable(false);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(jPanelFooter, BorderLayout.SOUTH);
        jPanelFooter.add(jButtonExit, null);
        scrollPane.getViewport().add(jTextAreaLog, null);
        this.getContentPane().add(jPanelTitle, BorderLayout.NORTH);
        jPanelTitle.add(jLabelTitle, null);
    }

    protected void setScreenTitle (String screenTitle)
    {
        jLabelTitle.setText(screenTitle);
    }

    protected void addButton (JButton button)
    {
        jPanelFooter.remove(jButtonExit);
        jPanelFooter.add(button, null);
        jPanelFooter.add(jButtonExit, null);
    }

    protected void setBackgoundColor (Color color)
    {
        jTextAreaLog.setBackground(color);
    }

    protected void displayText (String text)
    {
        this.jTextAreaLog.setText(text);
        this.jTextAreaLog.setCaretPosition(0);
    }


    void jButtonExit_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

}
