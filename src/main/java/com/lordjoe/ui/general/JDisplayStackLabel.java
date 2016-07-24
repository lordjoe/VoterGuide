package com.lordjoe.ui.general;

import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;

/**
 * com.lordjoe.ui.general.JDisplayStackLabel
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public class JDisplayStackLabel extends JLabel implements IDisplayStackChangeListener{
    public static JDisplayStackLabel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JDisplayStackLabel.class;

    private IDisplayStack m_Stack;
    public JDisplayStackLabel(IDisplayStack ds)
    {
        setStack(ds);
    }

    public IDisplayStack getStack() {
        return m_Stack;
    }

    public void setStack(IDisplayStack pStack) {
        if(m_Stack != null) {
           m_Stack.removeIDisplayStackChangeListener(this);
        }
        m_Stack = pStack;
        m_Stack.addIDisplayStackChangeListener(this);
        setText( getEditorStackString());
    }

    public String getEditorStackString()
    {
        String[] items = m_Stack.getStackItems();
        if(items.length == 0)
            return ResourceString.parameterToText("Editor.Start");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if(sb.length() > 0)
                sb.append("->");
            sb.append(item);
        }
        return sb.toString();
    }

    public void onDisplayStackChange(IDisplayStack stk) {
        if (m_Stack == stk)
             setText( getEditorStackString());

    }
}
