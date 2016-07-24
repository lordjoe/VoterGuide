package com.lordjoe.ui.general;

import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.util.*;

/**
 * com.lordjoe.ui.general.JParameterizedLabel
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public class JParameterizedLabel extends JInelasticLabel implements LocaleChangeListener
{
    public static JParameterizedLabel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JParameterizedLabel.class;



    private final String m_ParameterName;
    public JParameterizedLabel(String parameter) {
        super(ResourceString.parameterToText(parameter));
        m_ParameterName =  parameter;
        ResourceUtilities.addLocaleChangeListener(this);
    }



    /**
     * reset based on a new locale
     * @param evt  non-null event
     */
    public void onLocaleChange(LocaleChangeEvent evt)
    {
         Locale l = evt.getNewLocale();
         setText(ResourceString.parameterToText(m_ParameterName,l));
    }

}
