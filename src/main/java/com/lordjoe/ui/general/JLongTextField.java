package com.lordjoe.ui.general;

import java.awt.event.KeyEvent;
import java.net.*;


/**
 * com.lordjoe.ui.general.JLongTextField
 * The IntegerTextField class below is a simple example that only
 * allows a user to enter positive or negative integer values. It
 * consumes any KeyEvents for letters, unless the ALT key is pressed,
 * in which case the event may be needed to activate JButton mnemonics.
 * It also consumes all whitespace and punctuation-type characters contained
 * in the String "`~!@#$%^&*()_+=\\|\"':;?/>.<, ". It allows a minus sign only
 * if it is the first character entered, to signify a negative value.
 * <p/>
 * This class could easily be enhanced, perhaps to allow the entry o
 * f floating-point numbers or restrict input to non-negative values. The basic idea,
 * however, is the same: Process the allowable KeyEvents, and consume the rest.
 * http://www.devx.com/tips/Tip/14311
 *
 * @author David Glasser
 * @date Mar 1, 2005
 */
public class JLongTextField extends JSingleLineTextField
{
    public static final Class THIS_CLASS = JLongTextField.class;
    public static final JLongTextField[] EMPTY_ARRAY = {};

    public final static String badchars
            = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";


    private Long m_MinimumValue;
      private Long m_MaximumValue;
    public JLongTextField() {
        super();
    }
    public JLongTextField(int cols) {
        super(cols);
    }

    public Long getMinimumValue() {
        return m_MinimumValue;
    }

    public void setMinimumValue(Long pMinimumValue) {
        m_MinimumValue = pMinimumValue;
    }

    public Long getMaximumValue() {
        return m_MaximumValue;
    }

    public void setMaximumValue(Long pMaximumValue) {
        m_MaximumValue = pMaximumValue;
    }


    public boolean isNegativeAllowed() {
        return m_MinimumValue != null && m_MinimumValue < 0;
    }


    public boolean isZeroAllowed() {
        return m_MinimumValue == null || m_MinimumValue <= 0;
    }



    public void processKeyEvent(KeyEvent ev)
    {

        char c = ev.getKeyChar();

        if ((Character.isLetter(c) && !ev.isAltDown())
                || badchars.indexOf(c) > -1) {
            consomeEvent(ev);
            return;
        }
        if (c == '-' && getDocument().getLength() > 0)
            consomeEvent(ev);
        else
            super.processKeyEvent(ev);

    }


    public Long getLongValue() {
        String text = getText();
        if (text.length() == 0)
            return null;
        try {
            return new Long(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public void setLongValue(Long in) {
        if (in == null) {
            setText("");
            return;
        }
        Long min = getMinimumValue();
        if (min != null) {
            if (in < min)
                throw new IllegalArgumentException("value " + in + " < minimum " + min);
        }
        Long max = getMaximumValue();
        if (max != null) {
            if (in > max)
                throw new IllegalArgumentException("value " + in + " > Maximum " + max);
        }
        setText(in.toString());
    }

 
}

