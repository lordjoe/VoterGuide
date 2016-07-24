package com.lordjoe.utilities;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * com.lordjoe.utilities.JConsole
 * Quick class to display some long text on the screen
 * @author slewis
 * @date May 2, 2005
 */
public class JConsole extends JFrame
{
    public static final Class THIS_CLASS = JConsole.class;
    public static final JConsole EMPTY_ARRAY[] = {};

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 500;

    //- *******************
    //- Fields
    /**
    *  <Add Comment Here>
    */
    JTextField m_Title;
    JTextArea m_Text;

     /**
    *  <Add Comment Here>
    */
    JButton m_Close;

    /**
    *  <Add Comment Here>
    */
    private boolean m_isOpen;


    //- *******************
    //- Methods
// Added S.Lewis

    /**
    *  Constructor of Console
    */
    public JConsole(String Title,String in) {
        super(Title == null? "Snapshot" : Title);
        m_Title = new JTextField(Title);
        m_Title.setEditable(false);
        m_Text = new JTextArea();
        m_Text.setEditable(false);
        m_Text.setText(in);
        m_Text.setEditable(false);
        m_Close = new JButton("Close");
        m_Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
        );
        JPanel buttonPanel = new JPanel();
   //     buttonPanel.setLayout(new GridLayout(1,0));
        buttonPanel.add(m_Close);
        getContentPane().setLayout(new BorderLayout());
        JScrollPane Scroller = new JScrollPane(m_Text);
        getContentPane().add(m_Title,BorderLayout.NORTH);
        getContentPane().add(Scroller,BorderLayout.CENTER);
        getContentPane().add(buttonPanel,BorderLayout.SOUTH);
       setEnabled(true);
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.doLayout();
        setVisible(true);
    }

    /**
    *
    *  Gets the minimumn size.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */
    public Dimension getMinimumSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    /**
    *
    *  Gets the preferred size.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }



}
