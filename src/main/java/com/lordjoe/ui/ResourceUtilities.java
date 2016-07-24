package com.lordjoe.ui;

import com.lordjoe.utilities.WeakHashSet;

import java.util.*;


/**
 * com.lordjoe.ui.ResourceUtilities
 *
 * @author slewis
 * @date Feb 23, 2005
 */
public class ResourceUtilities
{
    public static final Class THIS_CLASS = ResourceUtilities.class;
    public static final ResourceUtilities EMPTY_ARRAY[] = {};
    private static final String RESOURCE_BASE = "resources.";


    private static final Set<LocaleChangeListener> gLocaleListeners =
            new WeakHashSet<LocaleChangeListener>();

    public static void addLocaleChangeListener(LocaleChangeListener added)
    {
        synchronized (gLocaleListeners) {
            gLocaleListeners.add(added);
        }
    }

    public static void removeLocaleChangeListener(LocaleChangeListener removed)
    {
        synchronized (gLocaleListeners) {
            gLocaleListeners.remove(removed);
        }
    }

    protected static void notifyLocaleListeners(LocaleChangeEvent evt)
    {
        synchronized (gLocaleListeners) {
            if (gLocaleListeners.isEmpty())
                return;
            for (LocaleChangeListener l : gLocaleListeners) {
                  l.onLocaleChange(evt);
            }
        }
    }


    public static Locale getCurrentLocale()
    {
        return Locale.getDefault();
    }

    public static void setCurrentLocale(Locale currentLocale)
    {
        if (Locale.getDefault().equals(currentLocale))
            return;
        Locale oldLocale = Locale.getDefault();
        Locale.setDefault(currentLocale);
        LocaleChangeEvent evt = new LocaleChangeEvent(oldLocale,currentLocale);
        notifyLocaleListeners(evt);
    }

    public static String changeStringLocale(String oldString,Locale oldLocale,Locale newLocale)
    {
        return oldString;
    }
    /**
     * convertn from an English string to the current locale
     * @param oldString
     * @return
     */
    public static String fromEnglishString(String oldString)
    {
        return changeStringLocale(oldString,Locale.US,getCurrentLocale());
    }

    public static ResourceBundle getResourceBundle(String applicationName)
    {
        return getResourceBundle(applicationName, Locale.getDefault());
    }

    public static String buildResourceString(String base)
    {
        return RESOURCE_BASE + base;
    }

    public static ResourceBundle getResourceBundle(String applicationName, Locale theLocale)
    {
        String bundleName = buildResourceString(applicationName);
        try {

            ResourceBundle ret = ResourceBundle.getBundle(bundleName, theLocale);
            return ret;
        }
        catch (MissingResourceException mre) {
            throw new IllegalArgumentException("problem " + bundleName.replace('.', '/') + ".properties not found");
        }
    }

}
