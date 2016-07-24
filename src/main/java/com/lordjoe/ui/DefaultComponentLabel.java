package com.lordjoe.ui;


import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * com.lordjoe.ui.general.DefaultComponentLabel
 *
 * @author Steve Lewis
 * @date Feb 28, 2005
 */
public class DefaultComponentLabel extends JLabel implements  LocaleChangeListener
{
    public static final Class THIS_CLASS = DefaultComponentLabel.class;
    public static final DefaultComponentLabel[] EMPTY_ARRAY = {};

    private final JComponent m_NDCParent;
    public DefaultComponentLabel(JComponent ndcParent)
    {
         m_NDCParent = ndcParent;
        ResourceUtilities.addLocaleChangeListener(this); // get locale changes
    }

    public JComponent getNDCParent()
    {
        return m_NDCParent;
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return (DefaultFrame) UIUtilities.getTopAncestor(this);
    }

    public ResourceBundle getResources()
     {
         return getDefaultFrame().getResources();
     }

     public String getResourceString(String nm)
      {
           return getDefaultFrame().getResourceString(nm);
      }

     public Object getResourceObject(String nm)
      {
          return getDefaultFrame().getResourceObject(nm);
       }

    /**
     * reset based on a new locale
     *
     * @param evt non-null event
     */
    public void onLocaleChange(LocaleChangeEvent evt)
    {
        String oldName = getName();
        if (oldName == null || oldName.length() == 0)
            return;
        String newName = ResourceUtilities.changeStringLocale(oldName, evt.getOldLocale(), evt.getNewLocale());
        setName(newName);
    }

}