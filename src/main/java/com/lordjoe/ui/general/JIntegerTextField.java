package com.lordjoe.ui.general;

import java.awt.event.KeyEvent;


/**
 * com.lordjoe.ui.general.JIntegerTextField
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
public class JIntegerTextField extends JSingleLineTextField {
    public static final Class THIS_CLASS = JIntegerTextField.class;
    public static final JIntegerTextField[] EMPTY_ARRAY = {};

    public final static String badchars
            = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";


    private Integer m_MinimumValue;
    private Integer m_MaximumValue;

    public JIntegerTextField() {
        super();
    }

    public JIntegerTextField(int cols) {
        super(cols);
    }

    public Integer getMinimumValue() {
        return m_MinimumValue;
    }

    public void setMinimumValue(Integer pMinimumValue) {
        m_MinimumValue = pMinimumValue;
    }

    public Integer getMaximumValue() {
        return m_MaximumValue;
    }

    public void setMaximumValue(Integer pMaximumValue) {
        m_MaximumValue = pMaximumValue;
    }


    public boolean isNegativeAllowed() {
        return m_MinimumValue != null && m_MinimumValue < 0;
    }


    public boolean isZeroAllowed() {
        return m_MinimumValue == null || m_MinimumValue <= 0;
    }


    public void processKeyEvent(KeyEvent ev) {
        char c = ev.getKeyChar();

        if ((Character.isLetter(c) && !ev.isAltDown())
                || badchars.indexOf(c) > -1) {
            consomeEvent(ev);
            return;
        }
        if (c == '-') {
            if (getDocument().getLength() > 0)  {
                consomeEvent(ev);
                return;
            }
            if (!isNegativeAllowed())  {
                consomeEvent(ev);
                return;
            }
            if (getText().length() > 0)  {
                consomeEvent(ev);
                return;
            }
            else {
                super.processKeyEvent(ev);
                return;                
            }
        }
        super.processKeyEvent(ev);

    }

    public Integer getIntegerValue() {
        String text = getText();
        if (text.length() == 0)
            return null;
        return new Integer(text);
    }


    public void setIntegerValue(Integer in) {
        if (in == null) {
            setText("");
            return;
        }
        Integer min = getMinimumValue();
        if (min != null) {
            if (in < min)
                throw new IllegalArgumentException("value " + in + " < minimum " + min);
        }
        Integer max = getMaximumValue();
        if (max != null) {
            if (in > max)
                throw new IllegalArgumentException("value " + in + " > Maximum " + max);
        }
        setText(in.toString());
    }


    public boolean isValid() {
        if(!isLegal(getText()))
            return false;
        Integer value = getIntegerValue();
        if (value == null)
            return false;
        Integer min = getMinimumValue();
        if (min != null) {
            if (value < min)
                return false;
        }
        Integer max = getMaximumValue();
        if (max != null) {
            if (value > max)
                return false;
        }
        return true;
    }

    public boolean isLegal(String text) {
        if(text == null || text.length() == 0)
            return true;
        try {
            if("-".equals(text)) {
                return isNegativeAllowed();
            }
            Integer value = new Integer(text);
            Integer min = getMinimumValue();
            if (value == null)
                return false;
            if (min != null) {
                if (value < min)
                    return false;
            }
            Integer max = getMaximumValue();
            if (max != null) {
                if (value > max)
                    return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}

