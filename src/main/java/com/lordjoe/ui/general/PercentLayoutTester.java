package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.ui.PercentConstraint;
import com.lordjoe.ui.PercentLayout;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.general.PercentLayoutTester
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class PercentLayoutTester
{
    public static final Class THIS_CLASS = PercentLayoutTester.class;
    public static final PercentLayoutTester EMPTY_ARRAY[] = {};
    public static final long REVERSE_INTERVAL = 2000;


    public static class ImagePanel extends JPanel
    {

        public ImagePanel()
        {
            PercentConstraint pc = null;
            Component c = null;
            double space = 0.05;
            double start = 0.1;
            double height = 0.2;
            double rowstart = 0.1;

            setLayout(new PercentLayout());

            height = 0.2;
            c = new JLabel("Hello World");
            pc = new PercentConstraint(rowstart,start,-rowstart,start + height);
            add(c,pc);
            start += height + space;


            height = 0.3;
            c = new JButton("Push Me");
            pc = new PercentConstraint(rowstart,start,-rowstart,start + height);
            add(c,pc);
            start += height + space;

            height = 0.1;
            JProgressBar jp = new JProgressBar(JProgressBar.HORIZONTAL);
            jp.setMaximum(100);
            jp.setValue(50);
            jp.setString("Progress");
            jp.setStringPainted(true);
            c = jp;
            pc = new PercentConstraint(rowstart,start,-rowstart,start + height);
            add(c,pc);
            start += height + space;

        }

     }

    public static void main(String[] args)
    {
        try {
            DefaultFrame test = new DefaultFrame();

            ImagePanel cmd = new ImagePanel();
            test.setMainDisplay(cmd);

            test.setSize(500, 400);
            UIUtilities.becomeVisible(test);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
