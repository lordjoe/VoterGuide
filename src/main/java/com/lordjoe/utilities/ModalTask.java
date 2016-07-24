   	 /**
*  ModalTask.java
*
* @author> Steven M. Lewis
* <>copyright>
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:51 PDT 1998
* @version> 1.0
*/

package com.lordjoe.utilities;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
    This is a modal dialog representing a long task
    which can be canceled by the user
    A process running in some non-UI thread calls
    ModalTask.set(String title) or
    ModalTask.set(Frame f,String title)
    to start the task - this registers the task's thread with
    the Modal Task
    The task may call ModalTask.setProgress(double fraction) to
    set progress from 0 to 1.0
    or ModalTask.setProgressName(String text) to set the text  on the
    progress bar.
    If the user wishes to cancel the task he hits the cancel button which
      throws a UserCancel Exception into the registered thread.
*/

  //  Window Listener
public  class ModalTask extends JDialog implements Runnable,ActionListener {
    private Thread m_Current; // thread of the process being monitored
    private Thread m_MyThread;
    private JButton m_CancelButton;
    private JProgressBar m_Progress;

    protected static ModalTask gActiveTask;


    public static void setProgress(double fraction) {
        if(gActiveTask != null) {
            int set = (int)(fraction * MAX_PROGRESS_LENGTH);
            gActiveTask.setTaskProgress(set);
        }
    }

    public static void setProgressName(String s) {
        if(gActiveTask != null) {
            gActiveTask.setTaskName(s);
        }
    }

    public static final int MAX_PROGRESS_LENGTH = 20;
    public static final int MODAL_FRAME_WIDTH = 300;
    public static final int MODAL_FRAME_HEIGHT = 200;

    protected ModalTask(Frame f,String title)
    {
        super(((f == null) ? new Frame() : f),title,true);

        setTitle(title);
        getContentPane().setLayout(new FlowLayout());


        m_Progress = new JProgressBar(0,MAX_PROGRESS_LENGTH);
        getContentPane().add(m_Progress);

        JButton m_CancelButton = new JButton("Cancel");
        m_CancelButton.addActionListener(this);
        getContentPane().add(m_CancelButton);

        setSize(MODAL_FRAME_WIDTH,MODAL_FRAME_HEIGHT);
        Dimension size;
        if(f != null) {
            size = f.getSize();
        }
        else {
            size =  Toolkit.getDefaultToolkit().getScreenSize();
        }
        setLocation((size.width - MODAL_FRAME_WIDTH) / 2,
                    (size.height - MODAL_FRAME_HEIGHT) / 2);
	    m_MyThread = new Thread(this);
	    gActiveTask = this;
    }

    protected ModalTask(String title)
    {
        this(null,title);
    }

    /**
        This is the critical line -
        stops the registered thread by throwing a UserCancelException
     */
    @SuppressWarnings(value = "deprecated")
    public void actionPerformed(ActionEvent ev) {
        // well this is not quite kosher
        // todo add better stop code
        m_Current.stop(new UserCancelException());
    }


	public static ModalTask set(String title) {
	    return(set(null,title));
	}

	public static ModalTask set(Frame f,String title) {
	    ModalTask ret = new ModalTask(f,title);
	    ret.m_Current = Thread.currentThread();
	    ret.m_MyThread.start();
	    return(ret);
	}

	public void setTaskName(String s) {
	    if(s != null) {
    	    m_Progress.setStringPainted(true);
	    }
	    else {
	        m_Progress.setStringPainted(false);
	    }

	    m_Progress.setString(s);
	}

	public void setTaskProgress(int n) {
	    m_Progress.setValue(n);
	}

	public  void release(Thread releaser) {
	    if(m_Current == releaser) {
	        setVisible(false);
	        dispose();
	        gActiveTask = null;
	    }
	}
	/**
	  This constructor adds this as a WindowListener
	  @param  Frame - Target frame
	*/
	public  void run() {
	    setVisible(true);
	}

 // end class MyWindowAdaptor
 }

