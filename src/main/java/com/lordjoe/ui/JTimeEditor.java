package com.lordjoe.ui;

import com.lordjoe.utilities.*;

import javax.swing.*;

/**
 * com.lordjoe.ui.JTimeEditor
 *
 * @author Steve Lewis
 * @date Dec 28, 2007
 */
public class JTimeEditor extends JPanel
{
    public static JTimeEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JTimeEditor.class;


    private final JSpinner m_Hours;
    private final JSpinner m_Minutes;

    public JTimeEditor() {
        m_Hours = new JSpinner(new CircularSpinnerModel(24));
        m_Minutes = new JSpinner(new CircularSpinnerModel(60));
    //    setLayout(new GridLayout(1,2));
        add(new JLabel(ResourceString.getStringFromText("Time")));
        add(m_Hours);
        add(new JLabel(":"));
        add(m_Minutes);
    }

    public JTimeEditor(int value) {
        this();
        setMinutes(value);
    }

    public void setMinutes(int value) {
        int hours = Math.min(23,value / 60);
        Integer integer = new Integer(hours);
        m_Hours.setValue(integer);
        m_Minutes.setValue(new Integer(value % 60));
    }

    public int getMinutes() {
        return ((Number) m_Hours.getValue()).intValue() * 60 +
                ((Number) m_Minutes.getValue()).intValue();
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.add(new JTimeEditor());
        jf.pack();
        jf.setVisible(true);
    }

}
