/**{ file
    @name TimedDialog.java
    @function - This is a modal message box which will automatically 
      timeout and go away after a specified interval
    @author> Steven M. Lewis
    @copyright> 
	************************
	*  Copyright (c) 1999
	*  Steven M. Lewis
	************************
}*/ 
package com.lordjoe.lib.ftp;
import com.lordjoe.utilities.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
/**{ class
    @name UserCanceledException
    @function Exception thrown when the user cancels an operation.
}*/ 
public class TimedDialog extends Dialog implements Runnable,ActionListener
{
    public static final int DEFAULT_WIDTH  = 350;
    public static final int DEFAULT_HEIGHT = 200;
    
    private boolean m_isActive;
    private int m_Timeout;
    private Frame    m_Parent;
    private TextArea m_Message;
    private Panel    m_ButtonPanel;
    private Button   m_OKButton;
    private Label    m_TimeoutLabel;
    private String   m_MessageText;
    
    //- ******************* 
    //- Methods 
    /**{ constructor
        @name UserCanceledException
        @function Constructor of UserCanceledException
        @param parent - parent frame
        @parsm timeout - time to display in sec
    }*/ 
    public TimedDialog(Frame parent,int timeout,String Title,String Message) {
        super(parent);
        m_Parent = parent;
        m_MessageText = Message;
        m_Timeout = timeout;
        init();
        setTitle(Title);
    }
    
    public void init() 
    {
       setLayout(new BorderLayout());
        
         
        m_ButtonPanel = new Panel();
        m_ButtonPanel.setLayout(new FlowLayout());

        m_Message = new TextArea(80,24);
        m_Message.setText(m_MessageText);
   //     m_Message.setEnabled(false);
        add(m_Message,BorderLayout.CENTER);

        m_ButtonPanel = new Panel();
        m_ButtonPanel.setLayout(new FlowLayout());
        add(m_ButtonPanel,BorderLayout.SOUTH);

        m_TimeoutLabel = new Label("");
        setTimeoutLabel(m_Timeout);
        m_ButtonPanel.add(m_TimeoutLabel);

        m_OKButton  = new Button("OK");
        m_OKButton.addActionListener(this);
        m_ButtonPanel.add(m_OKButton);
        

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        int LocX = 200;
        int LocY = 250;
        // center on parent
        if(m_Parent != null) {
            Point ParentLoc  = m_Parent.getLocation();
            Dimension ParentSize = m_Parent.getSize();
            LocX = ParentLoc.x  + (ParentSize.width - DEFAULT_WIDTH) / 2;
            LocY = ParentLoc.y  + (ParentSize.height - DEFAULT_HEIGHT) / 2;
        }
        setDialogColor(Color.lightGray);
        
        setLocation(LocX,LocY);
        setVisible(true);
        
    }
    
    public void setDialogColor(Color in) 
    {
        m_Message.setBackground(in);
        m_TimeoutLabel.setBackground(in);
        m_ButtonPanel.setBackground(in);
    }

    @SuppressWarnings(value = "depricated")
    public void show() 
    {
        super.show();
        m_isActive = true;
        if(m_Timeout > 0) {
            Thread WatchDog = new Thread(this,"Timed Dialog");
            WatchDog.setDaemon(true);
            WatchDog.start();
        }
    }
    
    protected void setTimeoutLabel(int left) 
    {
          m_TimeoutLabel.setText("This Dialog will timout in " + left + " Seconds ");
    }
    
    @SuppressWarnings(value = "depricated")
    public void run() 
    {
        long start = System.currentTimeMillis() / 1000;
        long end = start + m_Timeout;
        int TimeLeft = m_Timeout;
        while((System.currentTimeMillis() / 1000) < end) {
            try {
                Thread.currentThread().sleep(1000);
            }
            catch(InterruptedException ex) {}
            if(m_isActive) {
                 int del = (int)(end - (System.currentTimeMillis() / 1000));
                 setTimeoutLabel(del);
            }
            else {
                break;
            }
        }
        if(m_isActive)
            setVisible(false);
     }
            
    public void actionPerformed(ActionEvent ev)
    {
        Object source = ev.getSource();
        if(source == m_OKButton) {
            m_isActive = false;
            setVisible(false);
            return;
        }
    }
   

//- ******************* 
//- End ClassTimedDialog
}
