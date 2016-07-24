package com.lordjoe.utilities;

import java.util.*;
import java.io.*;

/**
 * IPropertyHolder - interface which is implemented by a class which
 * behaves like a properties object
 * @author Steve Lewis
 * @see IMutablePropertyHolder which supports adding props
 * @see MutablePropertyHolderBase for base class
 */
public class PropertyHolderBase implements IPropertyHolderDef {
    private Properties m_props = new Properties();

    private File m_FileContext = null;

    public PropertyHolderBase() {
    }

    protected void setFileContext(File context) {
        m_FileContext = context;
    }

    /**
     * return true if setting properties is allowed
     * @return as above
     */
    public boolean isMutable() {
        return (false);
    }

    /**
     * return true if the property exists
     * @param PropName non-null test string
     * @return as above
     */
    public boolean hasProperty(String PropName) {
        return (m_props.containsKey(PropName));

    }

    /**
     * property value or null if none exists
     * @param PropName non-null test string
     * @return possibly null value
     */
    public Object getProperty(String PropName) {
        return (m_props.get(PropName));
    }

    /**
     * property value or null if none exists
     * @param PropName non-null test string
     * @return possibly non-null list of values
     */
    public String[] getPropertyList(String PropName) {
        return (Util.getPropertiesList(m_props, PropName));
    }


    protected void doSetProperty(String PropName, Object in) {
        m_props.put(PropName, in);
    }

    protected void doRemoveProperty(String PropName) {
        m_props.remove(PropName);
    }

    protected Properties getPropertiesMap() {
        return (m_props);
    }

    protected int getPropertiesCount() {
        return (m_props.size());
    }

    /**
     * return nultiple values for a single property
     * @param PropName non-null test string
     * @return non-null array of values
     */
    public Object[] getPropertyValues(String PropName) {
        return (Util.getPropertiesList(m_props, PropName));
    }

    /**
     * return all property names
     * @return as above
     */
    public String[] getPropertyNames() {
        return (Util.getKeyStrings(m_props));
    }


    /**
     * Returns the value of an environment property or the default string if null
     * @param propertyName a non-null <code>String</code> that is the property name
     * @param defaultProp - a string value to return if the property is null.
     * @return a <code>String</code> that is the property value or null if there is no such property
     */
    public Object getProperty(String propertyName, Object defaultProp) {
        Object ret;
        ret = getProperty(propertyName);
        if (ret == null)
            ret = defaultProp;
        return (ret);
    }

    public String getPropertyFileName() {
        throw new UnsupportedOperationException("Method needed only to pair with set");
    }

    public void setPropertyFileName(String Name) {
        if(Name == null)
            return;
        Properties TheProps = getPropertiesMap();
        int propSize = TheProps.size();
        if (propSize > 0) {
            addPropertiesFile(Name);
            return;
        }
        InputStream in = inputStreamFromFile(Name);
        if (in != null) {
            try {
                TheProps.load(in);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Error reading file '" + Name + "'");
            }
        }
    }

    protected File fileFromName(String fileName)
    {
        File theFile = null;
        if (m_FileContext == null) {
            theFile = new File(fileName);
         } else {
            // get file realative to context
            theFile = new File(fileName);
            if (!theFile.isAbsolute()) {
                theFile = new File(m_FileContext, fileName);
            }
        }
        if(!theFile.exists())
            return(null);
        if(!theFile.canRead())
            return(null);
        return(theFile);
    }

    protected InputStream inputStreamFromFile(String fileName) {
        File file = fileFromName(fileName);
        if(file == null)
            return(null);
        return FileUtilities.getInputStream(file);
     }


    protected void addPropertiesFile(String fileName) {
        InputStream in = inputStreamFromFile(fileName);
        if (in != null) {
            Properties added = new Properties();
            try {
                added.load(in);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Error reading file '" + fileName + "'");
            }
            Properties TheProps = getPropertiesMap();
            TheProps.putAll(added);
        }
    }

}
