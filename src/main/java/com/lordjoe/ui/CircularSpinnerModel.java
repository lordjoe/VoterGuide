package com.lordjoe.ui;

import javax.swing.*;

/**
 * com.lordjoe.ui.CircularSpinnerModel
 *
 * @author Steve Lewis
 * @date Dec 28, 2007
 */
public class CircularSpinnerModel extends SpinnerNumberModel
{
    public static CircularSpinnerModel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CircularSpinnerModel.class;

    private int m_MaxValue;
    public CircularSpinnerModel(   int maximum ) {
        this(  0, maximum );
        m_MaxValue = maximum;
    }
    public CircularSpinnerModel(int value,  int maximum ) {
        super(value, 0, maximum, 1);
    }

    public Object getNextValue() {
        int value =  getNumber().intValue();
        int ret = value + 1;
        if(ret >= m_MaxValue)
            ret = 0;
        return new Integer(ret);

    }

    public Object getPreviousValue() {
        int value =  getNumber().intValue();
         int ret = value - 1;
         if(ret <  0)
             ret = m_MaxValue - 1;
         return new Integer(ret);
    }

    public void setMaximum(Comparable maximum) {
        super.setMaximum(maximum);
        m_MaxValue = ((Number)maximum).intValue();
    }
}
