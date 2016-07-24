package com.lordjoe.ui.general;

import com.lordjoe.lang.*;
import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.JSnapshotPanel
 *
 * @author Steve Lewis
 * @date Jan 5, 2006
 */
public class JSnapshotPanel extends JPanel
{
    public static final long DEFAULT_INTERVAL = 4000;
    private long m_Interval;
    private long m_LastImageTime;
    private JLabel m_Label;
    private JComponent m_RepresentedComponent;
    private boolean m_BuildingImage;

    public JSnapshotPanel()
    {
        setLayout(new GridLayout(1, 1));
        m_Label = new JLabel();
        add(m_Label);
        m_Interval = DEFAULT_INTERVAL;
    }


    public boolean isBuildingImage()
    {
        return m_BuildingImage;
    }

    public void setBuildingImage(boolean pBuildingImage)
    {
        m_BuildingImage = pBuildingImage;
    }

    public JComponent getRepresentedComponent()
    {
        return m_RepresentedComponent;
    }


    public void paint(Graphics g)
    {
        if (!isBuildingImage() && getRepresentedComponent() != null) {
            long now = TimeOps.now();
            long lastImageTime = getLastImageTime();
            long interval = getInterval();
            long lastGood = lastImageTime + interval;
            if (lastGood < now) {
                GeneralUtilities.printString("Rebuilding Image after " + (now - lastImageTime) / 1000);
                buildImage();
                repaint(200);
                return;
            }
        }
        super.paint(g);
        repaint(1000); // recheck after another second
    }

    public void setRepresentedComponent(JComponent pRepresentedComponent)
    {
        if (m_RepresentedComponent == pRepresentedComponent)
            return;
        m_RepresentedComponent = pRepresentedComponent;
        if (m_RepresentedComponent != null && !isBuildingImage()) {
            setBuildingImage(true);
            SwingThreadUtilities.invoke(new ImageBuilder());
        }
    }

    private class ImageBuilder implements Runnable
    {
        public void run()
        {
            buildImage();
        }
    }

    private void buildImage()
    {
        if (m_RepresentedComponent == null)
            return;
        setBuildingImage(true);
        try {
            Image img = FastDrawUtilities.buildImage(m_RepresentedComponent);
            if (img != null) {
                m_Label.setIcon(new ImageIcon(img));
                setLastImageTime(TimeOps.now());
            }
        }
        finally {
            setBuildingImage(false);
        }
    }

    public long getInterval()
    {
        return m_Interval;
    }

    public void setInterval(long pInterval)
    {
        m_Interval = pInterval;
    }


    public JLabel getLabel()
    {
        return m_Label;
    }

    public void setLabel(JLabel pLabel)
    {
        m_Label = pLabel;
    }

    public long getLastImageTime()
    {
        return m_LastImageTime;
    }

    public void setLastImageTime(long pLastImageTime)
    {
        m_LastImageTime = pLastImageTime;
    }
}
