package com.lordjoe.ui.general;

import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.RunningIndication
 *
 * @author Steve Lewis
 * @date Dec 12, 2005
 */
public class RunningIndication extends JDialog
{
    public static final String RUNNING_IMAGE = "RunningMan.gif";
    private static final RunningIndication INSTANCE = new RunningIndication();
    private static int gCount;

    public static synchronized void centerRunner(JFrame frame)
    {
        SwingThreadUtilities.invoke(new DoCenterRunner(frame));
    }

    private static class DoCenterRunner implements Runnable
    {
        private final JFrame m_Frame;
        public DoCenterRunner(JFrame frame) {
           m_Frame = frame;
        }
        public void run()
        {
            RunningIndication me = getInstance();
            Rectangle bounds = m_Frame.getBounds();
            Rectangle myBounds = me.getBounds();
            Point loc = bounds.getLocation();
            me.setLocation((int)(loc.getX() + (bounds.getWidth() - myBounds.getWidth()) / 2),
                 (int)(loc.getY() + (bounds.getHeight() - myBounds.getHeight()) / 2));
        }
    }


    public static synchronized void showRunner(String text)
    {
        SwingThreadUtilities.invoke(new DoShowRunner(text));
    }
    private static class DoShowRunner implements Runnable
    {
        private final String m_Text;
        public DoShowRunner(String text) {
           m_Text = text;
        }
        public void run()
        {
            RunningIndication me = getInstance();
            me.setTitle(m_Text);
            me.setVisible(true);
            gCount++;
        }
    }

    public static synchronized void hideRunner()
    {
        SwingThreadUtilities.invoke(new DoHideRunner());
    }

    private static class DoHideRunner implements Runnable
     {
          public DoHideRunner() {
         }
         public void run()
         {
             gCount--;
             RunningIndication me = getInstance();
             if (gCount <= 0)
                 me.setVisible(false);
          }
     }


    public static RunningIndication getInstance()
    {
        return INSTANCE;
    }


    private RunningIndication()
    {
        setBackground(Color.white);
        // setAlwaysOnTop(true);
        ImageIcon img = ResourceImages.getTransparentImage(RUNNING_IMAGE);
        JLabel runner = new JLabel(img);
        runner.setOpaque(false);
        setLayout(new BorderLayout());
        add(runner, BorderLayout.CENTER);
        setSize(300,200);
    }

    public static void main(String[] args)
    {
        showRunner("Testing Runner");
        ThreadUtilities.waitFor(5000);
        hideRunner();
    }

}
