package com.lordjoe.propertyeditor;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import com.lordjoe.ui.propertyeditor.DateReflectionPropertyEditor;
import com.lordjoe.utilities.ResourceString;

/**
 * com.lordjoe.propertyeditor.IValidationReason
 *    returned when there is a joint invalidation
 * @author Steve Lewis
 * @date Jan 28, 2008
 */
public class IValidationReason {
    public static IValidationReason[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IValidationReason.class;
    protected static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DateReflectionPropertyEditor.DATE_FORMAT);

    private final String m_Reason;
    private final String[] m_PropertyNames;
    private Object[] m_DisplayPatternValues;

    public IValidationReason(String pReason, String[] pPropertyNames) {
        m_Reason = pReason;
        m_PropertyNames = pPropertyNames;
    }
    /**
     * return internationalized
     * string to display,
     * explaining why the current candidate 
	 * property values
     * are not valid.
     * @return
     */
    public String getReason() {
    	if (null == m_DisplayPatternValues ||
    			0 == m_DisplayPatternValues.length ) {
    		return ResourceString.parameterToText(m_Reason);
    	} else {
    		MessageFormat messageToFormat = new MessageFormat(ResourceString.parameterToText(m_Reason));
    		for (int i = 0; i < m_DisplayPatternValues.length; i++) {
				if (m_DisplayPatternValues[i] instanceof java.util.Date) {
					messageToFormat.setFormatByArgumentIndex(i, DATE_FORMATTER);
				}
			}
    		StringBuffer sb = new StringBuffer();
    		messageToFormat.format(
    				m_DisplayPatternValues, sb, null);
    		return sb.toString();
    	}
    }

    public String[] getPropertyNames() {
        return m_PropertyNames;
    }
    /**
     * If the reason needs to include 
     * concrete values in it's pattern,
     * set them here.
     * @param values
     */
    public void setSubstitutionValues(Object[] values) {
    	/*
    	 * values set here
    	 * used in java.text.MessageFormat#format(String,Object[])
    	 */
    	m_DisplayPatternValues = values;
    }
}
