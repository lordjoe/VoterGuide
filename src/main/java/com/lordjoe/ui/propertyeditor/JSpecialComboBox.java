package com.lordjoe.ui.propertyeditor;

import com.lordjoe.utilities.Util;

import javax.swing.*;
import java.awt.*;


/**
 * com.lordjoe.ui.propertyeditor.JSpecialComboBox
 * Implements custom 
 * behaviors needed for combo box,
 * particularly with very large lists.
 * @author slewis
 * @date Mar 12, 2008
 */
public class JSpecialComboBox extends JComboBox
{
    public static final Class THIS_CLASS = JComboBoxPropertyEditor.class;
    public static final JComboBoxPropertyEditor EMPTY_ARRAY[] = {};

    public JSpecialComboBox(ComboBoxModel comboBoxModel) {
        super(comboBoxModel);
    }

    public JSpecialComboBox(Object[] objects) {
        super(objects);
    }

    public JSpecialComboBox() {
    }
	/**
	 * special fixed preferred size
	 * for slow rendering of large
	 * combobox lists on Mac
	 */
    public Dimension getPreferredSize() {
        // Mac has issues with really large combo boxes
        if(Util.isMac())  {
            if(getModel() != null) {
                if(getModel().getSize() < 200)
                    return super.getPreferredSize();

            }
            return new Dimension(300,20);

        }
        else  {
           return super.getPreferredSize();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    /**
     * Sets the specified boolean to indicate whether or not this
     * <code>TextComponent</code> should be editable.
     * A PropertyChange event ("editable") is fired when the
     * state is changed.
     *
     * @param b the boolean to be set
     * @beaninfo description: specifies if the text can be edited
     * bound: true
     * @see #isEditable
     */
    public void setEditable(boolean b)
    {
        super.setEditable(b);
        setEnabled(b);
        setForeground(Color.black);
    }

}