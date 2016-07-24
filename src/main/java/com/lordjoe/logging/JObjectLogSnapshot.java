package com.lordjoe.logging;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.utilities.JConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * com.lordjoe.utilities.JObjectLogSnapshot
 * Quick class to display a snapshot of an object log
 * @author slewis
 * @date May 2, 2008
 */
public class JObjectLogSnapshot<T> extends JFrame
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
    JList m_Data;

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
    public JObjectLogSnapshot(String title,LoggedObject<T>[] in,ListCellRenderer renderer) {
        super(title == null? "Snapshot" : title);
        UIUtilities.setWindowIcon(this);
        setTitle(title);
        m_Data = new JList(in);
        m_Data.setCellRenderer(renderer);
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
        getContentPane().add(new JScrollPane(m_Data),BorderLayout.CENTER);
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