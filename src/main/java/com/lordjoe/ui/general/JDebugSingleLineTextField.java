package com.lordjoe.ui.general;

import com.lordjoe.utilities.Util;

/**
 * com.lordjoe.ui.general.JDebugSingleLineTextField
 *  Just like  JSingleLineTextField but to be used for debugging
 *  UI issues when a distinction needs to be made
 * @author Steve Lewis
 * @date Nov 5, 2007
 */
public class JDebugSingleLineTextField extends  JSingleLineTextField
{
    public static JDebugSingleLineTextField[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JDebugSingleLineTextField.class;

    public JDebugSingleLineTextField() {
        super();

    }

    public JDebugSingleLineTextField(String s) {
        super(s);

    }

    public JDebugSingleLineTextField(int columns) {
        super(columns);

    }

    public void setText(String t) {
        if(t == null)   {
            setText("");
            return;
        }
        String oldText = getText();
        if(t.length() > 20) {
            Util.breakHere();  // there is a bug in the code
        }
        if(Util.objectEqual(t, oldText))
            return;
        super.setText(t);

    }
}
