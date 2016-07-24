package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import java.awt.event.KeyEvent;
import java.awt.*;


/**
 * com.lordjoe.ui.general.JDoubleTextField
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
public class JDoubleTextField extends JSingleLineTextField {
    public static final Class THIS_CLASS = JDoubleTextField.class;
    public static final JDoubleTextField[] EMPTY_ARRAY = {};

    public final static String badchars
            = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";


    private Double m_MinimumValue;
    private Double m_MaximumValue;

    public JDoubleTextField() {
        super();
    }

    public JDoubleTextField(int cols) {
        super(cols);
    }

    public boolean isNegativeAllowed() {
        return m_MinimumValue != null && m_MinimumValue < 0;
    }


    public boolean isZeroAllowed() {
        return m_MinimumValue == null || m_MinimumValue <= 0;
    }

    public Double getMinimumValue() {
        return m_MinimumValue;
    }

    public void setMinimumValue(Double pMinimumValue) {
        m_MinimumValue = pMinimumValue;
    }

    public Double getMaximumValue() {
        return m_MaximumValue;
    }

    public void setMaximumValue(Double pMaximumValue) {
        m_MaximumValue = pMaximumValue;
    }

    public void setText(String t) {
        if ("0".equals(t))
            super.setText(t); // break here
        super.setText(t);

    }

    public void processKeyEvent(KeyEvent ev) {
        int id = ev.getID();
        char c = ev.getKeyChar();

        String oldText = getText();
        if (isNegativeAllowed() && c == '-') {
            int pos = getCaretPosition();
            if (pos == 0) {
                if (oldText.length() == 0 || oldText.charAt(0) != '-')
                    super.processKeyEvent(ev);
                return;
            }
        }

        if (oldText.endsWith("e") || oldText.endsWith("E")) {
            if (c == '-') {
                super.processKeyEvent(ev);
                return;
            }
        }
        // Allow exactly 1 e or E
        if (c == 'e' || c == 'E') {
            if (oldText.indexOf('e') > -1) {
                ev.consume();
                AWTUtil.doBeep();
                return;
            }
            if (oldText.indexOf('E') > -1) {
                consomeEvent(ev);
                return;
            }
            if (oldText.indexOf('.') == -1) {
                consomeEvent(ev);
                return;
            }
            super.processKeyEvent(ev);
            return;
        }
        if (c == '.') {
            if (oldText.indexOf(c) > -1) {
                consomeEvent(ev);
            } else
                super.processKeyEvent(ev);
            return;

        }
        if ((Character.isLetter(c) && !ev.isAltDown())
                || badchars.indexOf(c) > -1) {
            consomeEvent(ev);
            return;
        }
        if (c == '-' ) {
            if (!isNegativeAllowed()) {
                consomeEvent(ev);
                return;
            }
            if (oldText.indexOf(c) > -1) {
                consomeEvent(ev);
                return;
            }
            if (getCaretPosition() > 0) {
                consomeEvent(ev);
                return;
            }

        }
        super.processKeyEvent(ev);
//
//        if(!isLegal(getText()))  {
//            setCaretPosition(Math.max(getCaretPosition() - 1,0));
//            resetText(oldText);
//        }
    }


    public boolean isValid() {
        if (!isLegal(getText()))
            return false;
        Double value = getDoubleValue();
        if (value == null)
            return false;
        Double min = getMinimumValue();
        if (min != null) {
            if (value < min)
                return false;
        }
        Double max = getMaximumValue();
        if (max != null) {
            if (value > max)
                return false;
        }
        return true;
    }

    public boolean isLegal(String text) {
        if (text == null || text.length() == 0)
            return true;
        try {
            Double min = getMinimumValue();
            if ("-".equals(text)) {
                return min == null || min < 0;
            }
            Double value = new Double(text);
            if (value == null)
                return false;
            if (min != null) {
                if (value < min)
                    return false;
            }
            Double max = getMaximumValue();
            if (max != null) {
                if (value > max)
                    return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public Double getDoubleValue() {
        String text = getText();
        if (text.length() == 0)
            return null;
        Double aDouble = null;
        try {
            aDouble = new Double(text);
        }
        catch (NumberFormatException e) {
            return null;
        }
        return aDouble;
    }

    public Float getFloatValue() {
        String text = getText();
        if (text.length() == 0)
            return null;
        Float aFloat = new Float(text);
        return aFloat;
    }


    public void setFloatValue(Float in) {
        setDoubleValue(new Double(in.doubleValue()));
    }

    public void setDoubleValue(Double in) {
        if (in == null) {
            setText("");
            return;
        }
        Double min = getMinimumValue();
        if (min != null) {
            if (in < min)
                throw new IllegalArgumentException("value " + in + " < minimum " + min);
        }
        Double max = getMaximumValue();
        if (max != null) {
            if (in > max)
                throw new IllegalArgumentException("value " + in + " > Maximum " + max);
        }
        setText(in.toString());
        setText(in.toString());
    }

}

