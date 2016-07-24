package com.lordjoe.utilities;

import java.util.*;

import com.lordjoe.lib.xml.*;

/**
 * NestedPropertyHolderBase - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IMutablePropertyHolder which supports adding props
 * @see PropertyHolderBase for base class
 */
public class NestedPropertyHolderBase extends MutablePropertyHolderBase implements INestedPropertyHolder {
    private String m_Name;
    private Map m_SubElements;
    private String[] m_SubElementNames;
    private char m_Separator = ':';


    public NestedPropertyHolderBase() {
        m_SubElements = new HashMap();
    }

    /**
     * Method <Add Comment Here>
     * @return
     * @see
     */
    public char getSeparator() {
        return (m_Separator);
    }

    /**
     * Method <Add Comment Here>
     *
     * @param name
     * @see
     */
    public void setName(char in) {
        m_Separator = in;
    }


    /**
     * Method <Add Comment Here>
     * @return
     * @see
     */
    public String getName() {
        return (m_Name);
    }

    /**
     * Method <Add Comment Here>
     *
     * @param name
     * @see
     */
    public void setName(String name) {
        m_Name = name;
    }


    protected Map getSubElements() {
        return (m_SubElements);
    }

    public INestedPropertyHolder getSubElement(String in) {
        return ((INestedPropertyHolder) m_SubElements.get(in));
    }

    public synchronized String[] getDesignatedSubElementNames() {
        if (m_SubElementNames == null)
            m_SubElementNames = Util.getKeyStrings(m_SubElements);
        return (m_SubElementNames);
    }

    protected String getDesignatedSubElement(String in) {
        int index = in.indexOf(getSeparator());
        if (index == -1)
            return (null);
        return (in.substring(0, index));
    }

    public Object getProperty(String in) {
        Object ret = null;
        String SubItem = getDesignatedSubElement(in);
        if (SubItem != null) {
            INestedPropertyHolder handler = getSubElement(SubItem);
            if (handler == null) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("Sub-element '").append(SubItem).append("' not found");
                sb.append(System.getProperty("line.separator"));
                sb.append("Valid sub-elements are: ");
                sb.append(Util.buildListString(getDesignatedSubElementNames(), 1));
                throw new IllegalArgumentException(sb.toString());
            }
            ret = handler.getProperty(in.substring(SubItem.length() + 1));
        } else {
            ret = super.getProperty(in);
            if (ret == null)
                ret = getSubElementProperty(in);
        }
        if (ret != null)
            return (ret);
        return (null);
    }


    public Object getSubElementProperty(String in) {
        Object ret = null;
        String[] Items = getDesignatedSubElementNames();
        for (int i = 0; i < Items.length; i++) {
            ret = getSubElement(Items[i]).getProperty(in);
            if (ret != null)
                break;
        }
        return (ret);
    }

    public void addSubElement(INestedPropertyHolder in) {
        m_SubElements.put(in.getName(), in);
    }


    public void removeSubElement(INestedPropertyHolder in) {
        m_SubElements.remove(in.getName());
    }


    public int getSubElementCount() {
        return (m_SubElements.size());
    }


    public Iterator getSubElementIterator() {
        return (m_SubElements.values().iterator());
    }

    /**
     * This returns the object which will handle the tag - the handler
     * may return itself or create a sub-object to manage the tag
     * @param tagName non-null name of the tag
     * @param attributes non-null array of name-value pairs
     * @return possibly null handler
     */
    public Object handleTag(String tagName, NameValue[] attributes) {
        if (tagName.equalsIgnoreCase("SubSpace")) {
            NameValue ClassNameValue = XMLUtil.findRequiredNameValue("class", attributes);
            String ClassName = (String) ClassNameValue.getValue();
            INestedPropertyHolder TheHolder = (INestedPropertyHolder) ClassUtilities.createInstance(ClassName);
            if (TheHolder == null)
                throw new IllegalStateException("Cannot create class '" + ClassName + "'");
            addSubElement(TheHolder);
            return (TheHolder);
        }
        return (super.handleTag(tagName, attributes));
    }

}
