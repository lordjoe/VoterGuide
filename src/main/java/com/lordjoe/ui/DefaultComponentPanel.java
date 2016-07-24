package com.lordjoe.ui;

import com.lordjoe.ui.general.*;

import javax.swing.*;
import java.util.*;

/**
 * DefaultComponentLabel.DefaultComponentPanel
 *
 * @author Steve Lewis
 * @date Feb 28, 2005
 */
public class DefaultComponentPanel extends SubjectDisplayPanel implements IDefaultComponent,
        LocaleChangeListener
{
    public static final Class THIS_CLASS = DefaultComponentPanel.class;
    public static final DefaultComponentPanel[] EMPTY_ARRAY = {};

    private final IDefaultComponent m_DefaultParent;

    public DefaultComponentPanel(IDefaultComponent DefaultParent)
    {
        m_DefaultParent = DefaultParent;
        ResourceUtilities.addLocaleChangeListener(this); // get locale changes
    }

    public IDefaultComponent getDefaultParent()
    {
        return m_DefaultParent;
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return getDefaultParent().getDefaultFrame();
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
        String newName = ResourceUtilities.changeStringLocale(oldName, evt.getOldLocale(),
                evt.getNewLocale());
        setName(newName);
    }

}