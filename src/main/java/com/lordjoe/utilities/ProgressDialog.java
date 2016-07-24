
/**{ file
    @name ProgressDialog.java
    @function <Add Comment Here>
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97
	*  Steven M. Lewis
	************************

    @date> Wed May 28 17:44:32  1997
    @version> 1.0
}*/
package com.lordjoe.utilities;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;
import java.beans.*;
/**{ class
    @name ProgressDialog
    @function <Add Comment Here>
}*/
public class ProgressDialog extends JDialog
{
    public static final int SCROLL_HEIGHT = 30;
    public static final int PROGRESS_DIALOG_WIDTH = 300;
    public static final int PROGRESS_DIALOG_HEIGHT = 8 * SCROLL_HEIGHT;
    public static final int BUTTON_HEIGHT = 30;
    public static final int BUTTON_WIDTH = 100;
    public static final int PANEL_INSET = 5;
    
    protected static Icon gDisplayImage;
    
    private JScrollPane  m_Scroller;
    private JPanel       m_Progress;
    private CancelButton m_Cancel;
    private ITaskProgress[] m_displayItems;
    private JProgressBar[] m_displays;
    private int m_NumberTasks;
    
    public static void setRunner() 
    {
        if(gDisplayImage != null)
            return;
        try {
            Icon TheImage = FileUtilities.getResourceIcon(ProgressDialog.class,"images/bc_runner1.gif");
            setImage(TheImage);
			return;
        }
        catch(Exception ex) {
            Assertion.handleException(ex); // forgive problems
        }
		if(gDisplayImage == null) {
			try {
				Icon TheImage = new ImageIcon("com/lordjoe/Utilities/images/bc_runner1.gif");
				setImage(TheImage);
			}
			catch(Exception ex) {
			    Assertion.handleException(ex); // forgive problems
			}
		}
    }
        
    public static void setImage(Icon TheImage) 
    {
        gDisplayImage = TheImage;
        if(TheImage == null || !(TheImage instanceof ImageIcon))
            return;
        // load the image at this time
        new Thread(new Runnable() { 
            public void run() { ((ImageIcon)gDisplayImage).getImage(); } 
        }).start();
    }
    
    public static Icon getImage() 
    {
        return(gDisplayImage);
    }

    /**{ field
        @name m_IsShown
        @function <Add Comment Here>
    }*/
    private boolean m_IsShown = false;

    /**{ field
        @name m_DisplayThread
        @function - showing a modal dialog blocks the
          thread - this causes a block that only this class cares about
    }*/
    private Thread m_DisplayThread;
    
    //- *******************
    //- Methods
    /**{ constructor
        @name ProgressDialog
        @function Constructor of ProgressDialog
    }*/
    public ProgressDialog() {
        super(ProgressManager.getActiveFrame(),true);
        setSize(PROGRESS_DIALOG_WIDTH,PROGRESS_DIALOG_HEIGHT);
        Container ThePane = getContentPane();
        ThePane.setLayout(new BorderLayout());
        m_Progress = new JPanel();
        m_Progress.setLayout(new BoxLayout(m_Progress,BoxLayout.Y_AXIS));
        m_Scroller = new JScrollPane(m_Progress);
        m_Scroller.setBorder(new EtchedBorder());
        ThePane.add(m_Scroller,BorderLayout.CENTER);
        Dimension size = ThePane.getSize();
        Insets ins = ThePane.getInsets();
        m_Scroller.setBounds(ins.left + PANEL_INSET,ins.top + PANEL_INSET,
            size.width - ins.left - ins.right - 2 * PANEL_INSET,
            size.height - ins.top - ins.bottom - 3 * PANEL_INSET - BUTTON_HEIGHT);
        
        JPanel SouthPanel = new JPanel();
        SouthPanel.setLayout(new BoxLayout(SouthPanel,BoxLayout.Y_AXIS));
        SouthPanel.setLayout(new BorderLayout());
        if(gDisplayImage != null) {
            JLabel ImageLabel = new JLabel(gDisplayImage);
            SouthPanel.add(ImageLabel,BorderLayout.CENTER);
        }
        m_Cancel = new CancelButton();
        SouthPanel.add(m_Cancel,BorderLayout.EAST);
        
        ThePane.add(SouthPanel,BorderLayout.SOUTH);
        m_Cancel.setMaximumSize(m_Cancel.getPreferredSize());
        
        m_displayItems = new TaskProgress[0];
        m_displays = new JProgressBar[0];
        
        setTitle("The Application is working");
    //    addNotify();
        setBackground(Color.lightGray);
        
        // Center on the active frame
        if(ProgressManager.getActiveFrame() != null && ProgressManager.getActiveFrame().getBounds().x >= 0) {
            Rectangle r = ProgressManager.getActiveFrame().getBounds();
            size = getSize();
            setLocation(r.x + (r.width - size.width) / 2,r.y + (r.height - size.height) / 2);
        }
        else {
            Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
            size = getSize();
            setLocation((screenSize.width - size.width) / 2,(screenSize.height - size.height) / 2);
        }
        new Thread(new ProgressWatchDog(),"ProgressWatchDog").start();
    }
    
    
    public synchronized void  updateDisplay(TaskProgress[] items) 
    {
        m_NumberTasks = items.length;
        ITaskProgress[] OldItems = m_displayItems;
        JProgressBar[] OldDisplays = m_displays;
        
        m_displayItems = items;
            
        try {
            m_displays = new JProgressBar[items.length];
            for(int j = 0; j < m_displayItems.length; j++) {
                for(int i = 0; i < OldItems.length; i++) {
                    if(m_displayItems[j] == OldItems[i]) {
                        m_displays[j] = OldDisplays[i];
                        OldDisplays[i] = null;
                    }
                }
            }
            for(int j = 0; j < OldDisplays.length; j++) {
                if(OldDisplays[j]!= null) {
                    m_Progress.remove(OldDisplays[j]);
                }
            }
            for(int j = 0; j < m_displayItems.length; j++) {
                if(m_displays[j] == null) {
                    m_displays[j] = new NamedProgressBar(m_displayItems[j]);
                    m_Progress.add(m_displays[j]);
                }
            }
            Dimension size = m_Progress.getSize();
            m_Progress.setSize(size.width,SCROLL_HEIGHT * m_displayItems.length);
        }
        catch(Exception ex) {
            Assertion.handleException(ex); // keep running
        }
        finally {
            boolean doShow = m_NumberTasks > 0;
            if(!doShow)
                Assertion.doNada();
            if(inBrowser()) { // I may be having trouble with invokeLater
                setVisible(doShow);
			    if(doShow)
		    	    toFront();
                setShown(doShow);
            }
            else {
               SwingUtilities.invokeLater(new ShowRun(doShow));
            }
        }
    }
    
    public void setShown(boolean isSet)
    {
        m_IsShown = isSet;
    }

    /**{ method
        @name setBounds
        @function
             Reshapes the Component to the specified bounding box.
        @param height the height of the component
        @param width the width of the component
        @param y the y coordinate
        @param x the x coordinate
        @overrideReason> <Add Comment Here>
        @policy <Add Comment Here>
        @primary
        @see #setSize

        @see #move

        @see #bounds

    }*/
    public synchronized void setBounds(int x,int y,int width,int height) {
        super.setBounds(x,y,width,height);
    }
    
    public void doCancel() {
        
    }
    
    public static boolean inBrowser()
    {
        SecurityManager sm = System.getSecurityManager();
        if(sm == null)
            return(false);
        String test = sm.getClass().getName().toUpperCase();
        return(test.indexOf("NETSCAPE") >= 0 || test.indexOf("MICROSOFT") >= 0);
    }

    
    public class ShowRun implements Runnable
    {
        private boolean m_setvis;
        public ShowRun(boolean doit) {
            m_setvis = doit;
        }
        public void run() {
            setVisible(m_setvis);
			if(m_setvis)
			   toFront();
            setShown(m_setvis);
        }
    }

    public class ProgressWatchDog implements Runnable
    {
         public void run() {
            while(true) {
                try {
                    Thread.sleep(500);
                    if(m_NumberTasks == 0)
                        ProgressManager.update();
                    
                }
                catch(InterruptedException ex) {
                    break;
                }
            }
        }
   }
    
        
    /**{ class
        @name CancelButton
        @function <Add Comment Here>
    }*/
    class CancelButton extends JButton implements ActionListener {

        //- *******************
        //- Methods
        /**{ constructor
            @name CancelButton
            @function Constructor of CancelButton
        }*/
        public CancelButton() {
            super("Cancel");
            addActionListener(this);
            this.setMaximumSize(this.getPreferredSize());
        }
        
        public void actionPerformed(ActionEvent ev) {
            ProgressDialog theFrame = (ProgressDialog)AWTUtil.getAncestorOfClass(this,ProgressDialog.class);
            theFrame.doCancel();
        }


    //- *******************
    //- End Class CancelButton
    }
    
    public static class LongTask implements Runnable
    {
        public void run() {
            TaskProgress Task1 = new TaskProgress("Task1",10);
            for(int i = 0; i < 10 ; i++) {
                Task1.setValue((double)i);
                TaskProgress Task2 = new TaskProgress("Task2",10);
                for(int j = 0; j < 10 ; j++) {
                    Task2.setValue((double)j);
                    TaskProgress Task3 = new TaskProgress("Task3",10);
                    for(int k = 0; k < 10 ; k++) {
                        Task3.setValue((double)k);
                        try {
                            Thread.sleep(200);
                        }
                        catch(Exception ex) {}
                    }
                    Task3.done();
                }
                Task2.done();
            }
            Task1.done();
        }
    }
    
    public static void main(String[] args) 
    {
        ProgressDialog.setRunner();
        new Thread(new LongTask()).start();
    }

    public class NamedProgressBar extends JProgressBar implements PropertyChangeListener
    {
        private ITaskProgress m_data;
        public NamedProgressBar(ITaskProgress data) {
            super(data.getModel());
            String name = data.getName();
            setStringPainted(true);
            setString(name);
        }
        
        public void setData(ITaskProgress model)
        {
            if(model == m_data)
                return;
            if(m_data != null)
                m_data.removePropertyChangeListener(this);
            m_data = model;
            m_data.addPropertyChangeListener(this);
        }
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(getName())) {
                setString((String)evt.getNewValue());
                return;
            }
        }
    }


}
