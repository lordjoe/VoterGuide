package com.lordjoe.ui;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.ParentWidthPanel
 *  panel prefers its parents width
 * @author Steve Lewis
 * @date Feb 11, 2008
 */
public class ParentWidthPanel extends JPanel {
    public static ParentWidthPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ParentWidthPanel.class;

    public ParentWidthPanel() {
    }

    protected int getPreferredHeight()
    {
        Dimension preferredSize = super.getPreferredSize();
        return  preferredSize.height;
    }


    protected int getPreferredWidth()
    {
        int width = 10;
          Container container = AWTUtil.getParentPanel(this);
          if(container != null)  {
              width =container.getSize().width;
          }
        else {
              Dimension preferredSize = super.getPreferredSize();
              width = preferredSize.width;

          }
        return width;
    }

    public Dimension getPreferredSize() {
      return new Dimension(getPreferredWidth(),getPreferredHeight());
   }

}
