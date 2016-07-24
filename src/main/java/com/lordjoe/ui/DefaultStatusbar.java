package com.lordjoe.ui;


import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.DefaultStatusbar
 *
 * @author slewis
 * @date Feb 23, 2005
 */
public class DefaultStatusbar extends DefaultComponentPanel

{
    public static final Class THIS_CLASS = DefaultStatusbar.class;
    public static final DefaultStatusbar EMPTY_ARRAY[] = {};

    private final JLabel m_Label;
  //  private final Map<String,JMenu> m_Menus;
    public DefaultStatusbar(IDefaultComponent parent) {
       super(parent);
        m_Label = new JLabel("");
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(m_Label);
    }

    public String getText()
    {
        return m_Label.getText();
    }


    public void setText(String text)
    {
         m_Label.setText(text);
    }

}