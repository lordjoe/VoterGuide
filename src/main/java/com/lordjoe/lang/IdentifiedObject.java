package com.lordjoe.lang;

import com.lordjoe.general.ImplementationBase;
import com.lordjoe.lib.xml.*;


/**
 * com.lordjoe.lang.IdentifiedObject
 *
 * @author Steve Lewis
 * @date Nov 11, 2007
 */
public abstract class IdentifiedObject extends ImplementationBase implements Comparable
{
    public static IdentifiedObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IdentifiedObject.class;

    private String m_Id;

    protected IdentifiedObject() {
    }

    public String getId() {
        return m_Id;
    }

    public void setId(String pId) {
        if(m_Id != null && !m_Id.equals(pId))
            throw new IllegalStateException("Id can only be set once");
        m_Id = pId;
    }
    /**
     * equality based on ID's if set,
     * otherwise Names,
     * otherwise punt to super class.
     */
    public boolean equals(Object o) {
    	if(o instanceof IdentifiedObject) {
            IdentifiedObject ioComp = (IdentifiedObject)o;
            String myIdent = null;
            String ioIdent = null;
            if (null == getId() || null == ioComp.getId()) {
            	myIdent = getName();
            	ioIdent = ioComp.getName();
            } else {
            	myIdent = getId();
            	ioIdent = ioComp.getId();
            }
            if (null == myIdent || null == ioIdent) {
            	return super.equals(o);
            } else {
            	return myIdent.equals(ioIdent);
            }
    	} else {
    		return false;
    	}
    }

    public int compareTo(Object o) {
    	if(o instanceof IdentifiedObject) {
            IdentifiedObject ioComp = (IdentifiedObject)o;
            String myIdent = null;
            String ioIdent = null;
            if (null == getId() || null == ioComp.getId()) {
            	myIdent = getName();
            	ioIdent = ioComp.getName();
            } else {
            	myIdent = getId();
            	ioIdent = ioComp.getId();
            }
            if (null != myIdent && null != ioIdent) {
            	return myIdent.compareTo(ioIdent);
            }
    	}
        return toString().compareTo(o.toString());
    }

    protected void appendAttributes(XMLPropertySet props, StringBuffer sb) {
        super.appendAttributes(props, sb);
        addPropertyAttribute("id", props, sb);

    }

}
