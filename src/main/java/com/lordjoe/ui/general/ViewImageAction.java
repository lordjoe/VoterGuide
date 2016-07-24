package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.ui.ImageViewerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * com.lordjoe.ui.general.ViewImageAction
 *
 * @author Steve Lewis
 * @date Sep 22, 2005
 */
public class ViewImageAction extends AbstractAction
{
    private final JFrame m_Frame;
    private final Image m_Image;
    public ViewImageAction(Image img,String name,JFrame frame)
    {
        m_Image = img;
        m_Frame = frame;
        putValue(Action.NAME,name);
    }

    public Image getImage()
    {
        return m_Image;
    }

    public void actionPerformed(ActionEvent e)
    {
        ImageViewerPanel vwr = new ImageViewerPanel(getImage());
        JDialog jf = new JDialog(m_Frame);
        jf.setLayout(new BorderLayout());
        jf.add(vwr,BorderLayout.CENTER);
        jf.setSize(800,600);
        DefaultFrame currentFrame = DefaultFrame.getCurrentFrame();
        if(currentFrame != null)
              UIUtilities.centerOnFrame(jf,currentFrame);
        jf.setVisible(true);

    }
}
