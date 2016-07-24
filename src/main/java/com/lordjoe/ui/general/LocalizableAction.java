package com.lordjoe.ui.general;

import com.lordjoe.ui.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.general.LocalizableAction
 *  Action capbale of revieving and responding to LocaleChange events
 * @author Steve Lewis
 * @date Mar 1, 2005
 */
public abstract class LocalizableAction extends AbstractAction implements LocaleChangeListener
{
    public static final Class THIS_CLASS = LocalizableAction.class;
    public static final LocalizableAction[] EMPTY_ARRAY = {};


    public LocalizableAction() {
         ResourceUtilities.addLocaleChangeListener(this);
     }
    public LocalizableAction(String name) {
          super(name);
          ResourceUtilities.addLocaleChangeListener(this);
      }
    public LocalizableAction(String name,Icon icn) {
          super(name,icn);
          ResourceUtilities.addLocaleChangeListener(this);
      }
        /**
     * reset based on a new locale
     *
     * @param evt non-null event
     */
    public void onLocaleChange(LocaleChangeEvent evt)
    {
         String oldName = (String)getValue(Action.NAME);
         String newName = ResourceUtilities.changeStringLocale( oldName,evt.getOldLocale(),evt.getNewLocale());
         putValue(Action.NAME,newName);
    }
}

