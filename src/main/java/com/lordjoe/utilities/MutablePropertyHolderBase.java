package com.lordjoe.utilities;

import java.util.*;

import com.lordjoe.lib.xml.*;

/**
 * IPropertyHolder - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IMutablePropertyHolder which supports adding props
 * @see PropertyHolderBase for base class
 */
public class MutablePropertyHolderBase extends PropertyHolderBase implements IMutablePropertyHolder, ITagHandler {
    // duplicated base class
    // private Properties m_props = new Properties();

    public MutablePropertyHolderBase() {
    }

    /**
     * return true if setting properties is allowed
     * @return as above
     */
    public boolean isMutable() {
        return (true);
    }

    /**
        * set property value
        * @param PropName non-null test string
        * @param added non-null value
        */
       public void setProperty(String PropName, boolean added) {
           setProperty(PropName, added ? Boolean.TRUE : Boolean.FALSE);
       }
    /**
        * set property value
        * @param PropName non-null test string
        * @param added non-null value
        */
       public void setProperty(String PropName, int added) {
           setProperty(PropName, new Integer(added));
       }
    /**
         * set property value
         * @param PropName non-null test string
         * @param added non-null value
         */
        public void setProperty(String PropName, double added) {
            setProperty(PropName, new Double(added));
        }
     /**
       * set property value
       * @param PropName non-null test string
       * @param added non-null value
       */
      public void setProperty(String PropName, Object added) {
          doSetProperty(PropName, added);
      }

    /**
     * remove a property
     * @param PropName non-null test string
     */
    public void removeProperty(String PropName) {
        doRemoveProperty(PropName);
    }

    /**
     * add a multivalued property
     * @param PropName non-null test string
     * @param added non-null value
     */
    public void addMultiProperty(String PropName, Object added) {
        throw new UnsupportedOperationException("Fix This");
    }

    /**
     * remove a multivalued property
     * @param PropName non-null test string
     * @param added non-null value
     */
    public void removeMultiProperty(String PropName, Object added) {
        throw new UnsupportedOperationException("Fix This");
    }


    /**
     * This returns the object which will handle the tag - the handler
     * may return itself or create a sub-object to manage the tag
     * @param tagName non-null name of the tag
     * @param attributes non-null array of name-value pairs
     * @return possibly null handler
     */
    public Object handleTag(String tagName, NameValue[] attributes) {
        if (tagName.equals("Property")) {
            NameValue AttributeName = XMLUtil.findRequiredNameValue("name", attributes);
            String Name = (String) AttributeName.getValue();
            NameValue AttributeValue = XMLUtil.findRequiredNameValue("value", attributes);
            String Value = (String) AttributeValue.getValue();
            setProperty(Name, Value);
            return (null);
        }
        return (null);
    }
}
