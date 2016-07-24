/* 
    file PropertyGrid.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/

package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.awt.*;
import java.lang.reflect.*;

public class PropertyGrid extends OverlordGrid
{
    private Class    m_DisplayClass;  // class we display
    private String[] m_PropertyNames; // properties we display
    private Method[] m_GetMethods;    // Methods associated with property names
    private Object[] m_DisplayedData;  // Data to display
    
    public PropertyGrid() {
    }
    
    public PropertyGrid(Class type,String[] props,Object[] data) {
        setDisplayClass(type);
        setDisplayProperties(props);
        setDisplayedObjects(data);
        setMultipleSelectionAllowed(false);
    }
    
    public void setDisplayProperties(String[] props) {
        if(m_DisplayClass == null)
            throw new IllegalStateException("Class must be defined before properties are set");
        m_GetMethods = new Method[props.length];
        for(int i = 0; i < props.length; i++) {
            m_GetMethods[i] = ClassUtilities.findGetMethod(m_DisplayClass,props[i]);
            if(m_GetMethods[i] == null)
                 throw new IllegalArgumentException("Unknown property " + props[i]);              
        }
        m_PropertyNames = props;
    }
    
    
    public void setDisplayedObjects(Object[] data) {
        if(m_DisplayClass == null)
            throw new IllegalStateException("Class must be defined before data are set");
        for(int i = 0; i < data.length; i++) {
            if(!m_DisplayClass.isInstance(data[i]))
                throw new IllegalArgumentException("Display Data must be instance of " + m_DisplayClass.getName());
        }
        m_DisplayedData = data;
        populate();
         
    }
    
    protected void populate()
    {
        try {
            setGridDimensions(m_DisplayedData.length,m_GetMethods.length);
            Object[] NOArgs = new Object[0];
            for(int row = 0; row < m_DisplayedData.length; row++) {
                for(int col = 0; col < m_GetMethods.length; col++) {
                    Object data = m_GetMethods[col].invoke(m_DisplayedData[row],NOArgs);
                    setGridObject(row,col,data);
                    setCellColor(row,col,Color.white);
                }
            }
            for(int col = 0; col < m_PropertyNames.length; col++) {
                setColumnHeader(col,propNameToHeader(m_PropertyNames[col]));
            }
                
        }
        catch(IllegalAccessException ex) {
            Assertion.fatalException(ex);
        }
        catch(IllegalArgumentException ex) {
            Assertion.fatalException(ex);
        }
        catch(InvocationTargetException ex) {
            Assertion.fatalException(ex);
        }
    }
    
    protected String propNameToHeader(String in)
    {
        StringBuffer sb = new StringBuffer(in.length() + 4);
        sb.append(Character.toUpperCase(in.charAt(0)));
        for(int i = 1; i < in.length(); i++) {
            char c = in.charAt(i);
            if(Character.isUpperCase(c))
                sb.append(' ');
            sb.append(c);
        }
        return(sb.toString());
    }
    
    public void setDisplayClass(Class in) 
    {
        m_DisplayClass = in;  // class we display
    }
    
    public Object getRowObject(int row)
    {
        return(m_DisplayedData[row]);
    }
    
}