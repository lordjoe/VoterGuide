/*
 *  JLocaleChooser.java  - A bean for choosing locales
 *  Copyright (C) 2004 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.toedter.components;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

/**
 * JLocaleChooser is a bean for choosing locales.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 85 $
 * @version $LastChangedDate: 2006-04-28 13:50:52 +0200 (Fr, 28 Apr 2006) $
 */
public class JLocaleChooser extends JComboBox implements ItemListener {
	private static final long serialVersionUID = 8152430789764877431L;
    public static final Comparator<Locale> ALPHABETIZER = new LocaleDisplayNameComparator();

    // Added SLewis to allow sorting be display name
    private static class LocaleDisplayNameComparator implements Comparator<Locale>
    {
        public int compare(Locale o1, Locale o2) {
            // Pig Latin goes at the end
//            if(o1 == PigLatin.PIG_LATIN)
//                return 1;
//            if(o2 == PigLatin.PIG_LATIN)
//                return -1;
            int ret = o1.getDisplayLanguage().compareTo(o2.getDisplayLanguage());
            if(ret != 0)
                return ret;
            ret = o1.getDisplayCountry().compareTo(o2.getDisplayCountry());
             if(ret != 0)
                 return ret;
            ret = o1.getDisplayVariant().compareTo(o2.getDisplayVariant());
             if(ret != 0)
                 return ret;

            return 0;
        }
    }

    protected JComponent component;
    /**
	 * Default JLocaleChooser constructor.
	 */
	public JLocaleChooser() {
	    this(null);
	}

    /**
     * Returns "JLocaleChoose".
     *
     * @return the name value
     */
    public String getName() {
        return "JLocaleChoose";
    }

	/**
	 * Default JLocaleChooser constructor.
	 */
	public JLocaleChooser(JComponent component) {
		super();
		this.component = component;
		addItemListener(this);
		locales = Calendar.getAvailableLocales();
        Locale[] realLocs = new Locale[locales.length];
        System.arraycopy(locales,0,realLocs,0,locales.length);
         locales = realLocs;
        Arrays.sort(locales,ALPHABETIZER);
        localeCount = locales.length;

        for (int i = 0; i < localeCount; i++) {
            Locale locale1 = locales[i];
            if (locale1.getCountry().length() > 0) {
                String dn = locale1.getDisplayName();
                addItem(dn);
            }
          }

		setLocale(Locale.getDefault());
	}

	/**
	 * The ItemListener for the locales.
	 */
	public void itemStateChanged(ItemEvent iEvt) {
		String item = (String) iEvt.getItem();
		int i;
 		for (i = 0; i < localeCount; i++) {
			if (locales[i].getDisplayName().equals(item))
				break;
		}
		setLocale(locales[i], false);
	}

	/**
	 * Sets the locale.
	 * 
	 * @see #getLocale
	 */
	private void setLocale(Locale l, boolean select) {
		Locale oldLocale = locale;
		locale = l;
		int n = 0;

         if (select) {
			for (int i = 0; i < localeCount; i++) {
				if (locales[i].getCountry().length() > 0) {
					if (locales[i].equals(locale))
						setSelectedIndex(n);
					n += 1;
				}
			}
		}

		firePropertyChange("locale", oldLocale, locale);
		if(component != null) {
		    component.setLocale(l);
		}
	}

	/**
	 * Sets the locale. This is a bound property.
	 * 
	 * @see #getLocale
	 */
	public void setLocale(Locale l) {
		setLocale(l, true);
	}

	/**
	 * Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Creates a JFrame with a JLocaleChooser inside and can be used for
	 * testing.
	 */
	static public void main(String[] s) {
		JFrame frame = new JFrame("LocaleChooser");
		frame.getContentPane().add(new JLocaleChooser());
		frame.pack();
		frame.setVisible(true);
	}

	private Locale[] locales;
	private Locale locale;
	private int localeCount;
}

