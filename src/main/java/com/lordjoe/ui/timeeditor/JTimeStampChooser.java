/*
 *  JDateChooser.java  - A bean for choosing a date
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
package com.lordjoe.ui.timeeditor;

import com.toedter.calendar.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.sql.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lordjoe.ui.timeeditor.JTimeStampDateEditor;

/**
 * A date chooser containig a date editor and a button, that makes a jcalendartime
 * visible for choosing a date. If no date editor is specified, a
 * JTextFieldDateEditor is used as default.
 *
 * @author Kai Toedter
 * @version $LastChangedRevision: 101 $
 * @version $LastChangedDate: 2006-06-04 14:42:29 +0200 (So, 04 Jun 2006) $
 */
public class JTimeStampChooser extends JPanel implements ActionListener,
		PropertyChangeListener {

	private static final long serialVersionUID = -4306412745720670722L;

	protected IDateEditor dateEditor;

	protected JButton calendarButton;

	protected JCalendarTime jcalendartime;

	protected JPopupMenu popup;

	protected boolean isInitialized;

	protected boolean dateSelected;

	protected Date lastSelectedDate;

	private ChangeListener changeListener;

	/**
	 * Creates a new JDateChooser. By default, no date is set and the textfield
	 * is empty.
	 */
	public JTimeStampChooser() {
		this(null, null, null, null);
	}

	/**
	 * Creates a new JDateChooser with given IDateEditor.
	 *
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JTimeStampChooser(IDateEditor dateEditor) {
		this(null, null, null, dateEditor);
	}

	/**
	 * Creates a new JDateChooser.
	 *
	 * @param date
	 *            the date or null
	 */
	public JTimeStampChooser(Date date) {
		this(date, null);
	}

	/**
	 * Creates a new JDateChooser.
	 *
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM SimpleDateFormat
	 *            format is used)
	 */
	public JTimeStampChooser(Date date, String dateFormatString) {
		this(date, dateFormatString, null);
	}

	/**
	 * Creates a new JDateChooser.
	 *
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM SimpleDateFormat
	 *            format is used)
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JTimeStampChooser(Date date, String dateFormatString,
			IDateEditor dateEditor) {
		this(null, date, dateFormatString, dateEditor);
	}

	/**
	 * Creates a new JDateChooser. If the JDateChooser is created with this
	 * constructor, the mask will be always visible in the date editor. Please
	 * note that the date pattern and the mask will not be changed if the locale
	 * of the JDateChooser is changed.
	 *
	 * @param datePattern
	 *            the date pattern, e.g. "MM/dd/yy"
	 * @param maskPattern
	 *            the mask pattern, e.g. "##/##/##"
	 * @param placeholder
	 *            the placeholer charachter, e.g. '_'
	 */
	public JTimeStampChooser(String datePattern, String maskPattern, char placeholder) {
		this(null, null, datePattern, new JTimeStampDateEditor(datePattern,
				maskPattern, placeholder));
	}

	/**
	 * Creates a new JDateChooser.
	 *
	 * @param jcal
	 *            the JCalendar to be used
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM Date format is
	 *            used)
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JTimeStampChooser(JCalendarTime jcal, Date date, String dateFormatString,
			IDateEditor dateEditor) {
		setName("JDateChooser");

		this.dateEditor = dateEditor;
		if (this.dateEditor == null) {
			this.dateEditor = new JTimeStampDateEditor();
		}
		this.dateEditor.addPropertyChangeListener("date", this);

		if (jcal == null) {
			jcalendartime = new JCalendarTime(date);
		} else {
			jcalendartime = jcal;
			if (date != null) {
				jcalendartime.setDate(date);
			}
		}

		setLayout(new BorderLayout());

		jcalendartime.getDayChooser().addPropertyChangeListener("day", this);
		// always fire"day" property even if the user selects
		// the already selected day again
		jcalendartime.getDayChooser().setAlwaysFireDayProperty(true);

		setDateFormatString(dateFormatString);
		setDate(date);

		// Display a calendar button with an icon
        ImageIcon icon = getButtonIcon();

		calendarButton = new JButton(icon) {
			private static final long serialVersionUID = -1913767779079949668L;

			public boolean isFocusable() {
				return false;
			}
		};
		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		calendarButton.addActionListener(this);

		// Alt + 'C' selects the calendar.
		calendarButton.setMnemonic(KeyEvent.VK_C);

		add(calendarButton, BorderLayout.EAST);
		add(this.dateEditor.getUiComponent(), BorderLayout.CENTER);

		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		// calendarButton.addFocusListener(this);

		popup = new JPopupMenu() {
			private static final long serialVersionUID = -6078272560337577761L;

			public void setVisible(boolean b) {
				Boolean isCanceled = (Boolean) getClientProperty("JPopupMenu.firePopupMenuCanceled");
				if (b
						|| (!b && dateSelected)
						|| ((isCanceled != null) && !b && isCanceled
								.booleanValue())) {
					super.setVisible(b);
				}
			}
		};

		popup.setLightWeightPopupEnabled(true);

		popup.add(jcalendartime);

		lastSelectedDate = date;

		// Corrects a problem that occured when the JMonthChooser's combobox is
		// displayed, and a click outside the popup does not close it.

		// The following idea was originally provided by forum user
		// podiatanapraia:
		changeListener = new ChangeListener() {
			boolean hasListened = false;

			public void stateChanged(ChangeEvent e) {
				if (hasListened) {
					hasListened = false;
					return;
				}
				if (popup.isVisible()
						&& JTimeStampChooser.this.jcalendartime.monthChooser
								.getComboBox().hasFocus()) {
					MenuElement[] me = MenuSelectionManager.defaultManager()
							.getSelectedPath();
					MenuElement[] newMe = new MenuElement[me.length + 1];
					newMe[0] = popup;
					for (int i = 0; i < me.length; i++) {
						newMe[i + 1] = me[i];
					}
					hasListened = true;
					MenuSelectionManager.defaultManager()
							.setSelectedPath(newMe);
				}
			}
		};
		MenuSelectionManager.defaultManager().addChangeListener(changeListener);
		// end of code provided by forum user podiatanapraia

		isInitialized = true;
	}

    protected ImageIcon getButtonIcon() {
        Class aClass = getClass();
        URL iconURL = aClass.getResource(
				"images/JTimeStampChooserIcon.gif");
        ImageIcon icon = new ImageIcon(iconURL);
        return icon;
    }

    /**
	 * Called when the jalendar button was pressed.
	 *
	 * @param e
	 *            the action event
	 */
	public void actionPerformed(ActionEvent e) {
		int x = calendarButton.getWidth()
				- (int) popup.getPreferredSize().getWidth();
		int y = calendarButton.getY() + calendarButton.getHeight();

		Calendar calendar = Calendar.getInstance();
		Date date = dateEditor.getDate();
		if (date != null) {
			calendar.setTime(date);
		}
		jcalendartime.setCalendar(calendar);
		popup.show(calendarButton, x, y);
		dateSelected = false;
	}

	/**
	 * Listens for a "date" property change or a "day" property change event
	 * from the jcalendartime. Updates the date editor and closes the popup.
	 *
	 * @param evt
	 *            the event
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("day")) {
			if (popup.isVisible()) {
				dateSelected = true;
				popup.setVisible(false);
                Calendar calendar = jcalendartime.getCalendar();
                Date date = calendar.getTime();
                setDate(date);
			}
		} else if (evt.getPropertyName().equals("date")) {
			if (evt.getSource() == dateEditor) {
				firePropertyChange("date", evt.getOldValue(), evt.getNewValue());
			} else {
                Date date = (Date) evt.getNewValue();
                setDate(date);
			}
		}
	}

	/**
	 * Updates the UI of itself and the popup.
	 */
	public void updateUI() {
		super.updateUI();
		setEnabled(isEnabled());

		if (jcalendartime != null) {
			SwingUtilities.updateComponentTreeUI(popup);
		}
	}

	/**
	 * Sets the locale.
	 *
	 * @param l
	 *            The new locale value
	 */
	public void setLocale(Locale l) {
		super.setLocale(l);
		dateEditor.setLocale(l);
		jcalendartime.setLocale(l);
	}


    public void setBackground(Color bg) {
        super.setBackground(bg);
        if(dateEditor instanceof JComponent) {
           ((JComponent)dateEditor).setBackground(bg); 
        }
    }


    /**
	 * Gets the date format string.
	 *
	 * @return Returns the dateFormatString.
	 */
	public String getDateFormatString() {
		return dateEditor.getDateFormatString();
	}

	/**
	 * Sets the date format string. E.g "MMMMM d, yyyy" will result in "July 21,
	 * 2004" if this is the selected date and locale is English.
	 *
	 * @param dfString
	 *            The dateFormatString to set.
	 */
	public void setDateFormatString(String dfString) {
		dateEditor.setDateFormatString(dfString);
		invalidate();
	}

    /**
     * Returns the date. If the JDateChooser is started with a null date and no
     * date was set by the user, null is returned.
     *
     * @return the current date
     */
    public Timestamp getTime() {
        Date date = dateEditor.getDate();
        if(date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    public void setTime(Timestamp t)
    {
        setDate(t);
    }


    /**
	 * Returns the date. If the JDateChooser is started with a null date and no
	 * date was set by the user, null is returned.
	 *
	 * @return the current date
	 */
	public Date getDate() {
		return dateEditor.getDate();
	}

	/**
	 * Sets the date. Fires the property change "date" if date != null.
	 *
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date) {
        Date old = dateEditor.getDate();
        dateEditor.setDate(date);
		if (getParent() != null) {
			getParent().invalidate();
		}
        firePropertyChange("date",old,date);
    }

	/**
	 * Returns the calendar. If the JDateChooser is started with a null date (or
	 * null calendar) and no date was set by the user, null is returned.
	 *
	 * @return the current calendar
	 */
	public Calendar getCalendar() {
		Date date = getDate();
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Sets the calendar. Value null will set the null date on the date editor.
	 *
	 * @param calendar
	 *            the calendar.
	 */
	public void setCalendar(Calendar calendar) {
		if (calendar == null) {
			dateEditor.setDate(null);
		} else {
			dateEditor.setDate(calendar.getTime());
		}
	}

	/**
	 * Enable or disable the JDateChooser.
	 *
	 * @param enabled
	 *            the new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (dateEditor != null) {
			dateEditor.setEnabled(enabled);
			calendarButton.setEnabled(enabled);
		}
	}

	/**
	 * Returns true, if enabled.
	 *
	 * @return true, if enabled.
	 */
	public boolean isEnabled() {
		return super.isEnabled();
	}

	/**
	 * Sets the icon of the buuton.
	 *
	 * @param icon
	 *            The new icon
	 */
	public void setIcon(ImageIcon icon) {
		calendarButton.setIcon(icon);
	}

	/**
	 * Sets the font of all subcomponents.
	 *
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		if (isInitialized) {
			dateEditor.getUiComponent().setFont(font);
			jcalendartime.setFont(font);
		}
		super.setFont(font);
	}

	/**
	 * Returns the jcalendartime component. THis is usefull if you want to set some
	 * properties.
	 *
	 * @return the jcalendartime
	 */
	public JCalendarTime getJCalendarTime() {
		return jcalendartime;
	}

	/**
	 * Returns the calendar button.
	 *
	 * @return the calendar button
	 */
	public JButton getCalendarButton() {
		return calendarButton;
	}

	/**
	 * Returns the date editor.
	 *
	 * @return the date editor
	 */
	public IDateEditor getDateEditor() {
		return dateEditor;
	}

	/**
	 * Sets a valid date range for selectable dates. If max is before min, the
	 * default range with no limitation is set.
	 *
	 * @param min
	 *            the minimum selectable date or null (then the minimum date is
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date is
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		jcalendartime.setSelectableDateRange(min, max);
		dateEditor.setSelectableDateRange(jcalendartime.getMinSelectableDate(),
				jcalendartime.getMaxSelectableDate());
	}

	public void setMaxSelectableDate(Date max) {
		jcalendartime.setMaxSelectableDate(max);
		dateEditor.setMaxSelectableDate(max);
	}

	public void setMinSelectableDate(Date min) {
		jcalendartime.setMinSelectableDate(min);
		dateEditor.setMinSelectableDate(min);
	}

	/**
	 * Gets the maximum selectable date.
	 *
	 * @return the maximum selectable date
	 */
	public Date getMaxSelectableDate() {
		return jcalendartime.getMaxSelectableDate();
	}

	/**
	 * Gets the minimum selectable date.
	 *
	 * @return the minimum selectable date
	 */
	public Date getMinSelectableDate() {
		return jcalendartime.getMinSelectableDate();
	}

	/**
	 * Should only be invoked if the JDateChooser is not used anymore. Due to popup
	 * handling it had to register a change listener to the default menu
	 * selection manager which will be unregistered here. Use this method to
	 * cleanup possible memory leaks.
	 */
	public void cleanup() {
		MenuSelectionManager.defaultManager().removeChangeListener(changeListener);
		changeListener = null;
	}

	/**
	 * Creates a JFrame with a JDateChooser inside and can be used for testing.
	 *
	 * @param s
	 *            The command line arguments
	 */
	public static void main(String[] s) {
		JFrame frame = new JFrame("JDateChooser");
		JDateChooser dateChooser = new JDateChooser();
		// JDateChooser dateChooser = new JDateChooser(null, new Date(), null,
		// null);
		// dateChooser.setLocale(new Locale("de"));
		// dateChooser.setDateFormatString("dd. MMMM yyyy");

		// dateChooser.setPreferredSize(new Dimension(130, 20));
		// dateChooser.setFont(new Font("Verdana", Font.PLAIN, 10));
		// dateChooser.setDateFormatString("yyyy-MM-dd HH:mm");

		// URL iconURL = dateChooser.getClass().getResource(
		// "/com/toedter/calendar/images/JMonthChooserColor32.gif");
		// ImageIcon icon = new ImageIcon(iconURL);
		// dateChooser.setIcon(icon);

		frame.getContentPane().add(dateChooser);
		frame.pack();
		frame.setVisible(true);
	}

}