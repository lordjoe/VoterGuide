package com.lordjoe.ui.general;

import java.awt.event.KeyEvent;


/**
 * com.lordjoe.ui.general.JBinaryTextField
 * The IntegerTextField class below is a simple example that only
 * allows a user to enter 1 or 0 so only binary numbers can be entered. It
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
public class JBinaryTextField extends JSingleLineTextField
{
    public static final Class THIS_CLASS = JBinaryTextField.class;
    public static final JBinaryTextField[] EMPTY_ARRAY = {};


    public void processKeyEvent(KeyEvent ev)
    {

        char c = ev.getKeyChar();

        if (c != '0' && c != '1' ) {
            ev.consume();
            return;
        }
       super.processKeyEvent(ev);

    }

    public Integer getIntegerValue()
    {
        String text = getText();
        if (text.length() == 0)
            return null;
        int value = Integer.parseInt(text,2);
        return new Integer(value);
    }
}

