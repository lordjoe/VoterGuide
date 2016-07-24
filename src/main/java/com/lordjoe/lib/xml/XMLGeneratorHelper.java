package com.lordjoe.lib.xml;


import com.lordjoe.utilities.*;

import java.util.*;


/*
 * com.lordjoe.lib.xml.XMLGeneratorHelper
 * @author smlewis
 * Date: Mar 6, 2003
 */

public class XMLGeneratorHelper 
{
    public static final Class THIS_CLASS = XMLGeneratorHelper.class;
    public static final XMLGeneratorHelper[] EMPTY_ARRAY = {};

    public static final Set gUniversalExcludedProperties = new HashSet();
    public static final String[] UNIVERSAL_EXCLUDES = { "Class" , "XMLString" };

    static {
        gUniversalExcludedProperties.addAll(Arrays.asList(UNIVERSAL_EXCLUDES));
    }


    private final Object m_Client;

    public XMLGeneratorHelper(Object client)
    {
        m_Client = client;
    }

    public String getXMLString()
     {
         StringBuffer sb = new StringBuffer();
         XMLPropertySet props = new XMLPropertySet();
         appendXML(props,sb, 0);
         String xml = sb.toString();
         //   System.out.println(xml);
         return (xml);
     }


    public void appendXML(XMLPropertySet props,StringBuffer sb, int indent)
    {
        String className = buildStartTag(sb, indent);
        appendProperties(props,sb, indent);
        sb.append(Util.indentString(indent));
        sb.append("</" + className + " >\n");

      //  System.out.println("Generating XML for " + className);
    }

    protected String buildStartTag(StringBuffer sb, int indent)
    {
        String className = ClassUtilities.shortClassName(m_Client);
        sb.append(Util.indentString(indent));
        sb.append("<" + className + " >\n");
        return (className);
    }

    protected void appendProperties(XMLPropertySet props,StringBuffer sb, int indent)
    {
        ClassProperty[] properties = ClassAnalyzer.getProperties(m_Client.getClass());
        Arrays.sort(properties,Util.NAME_COMPARATOR);
        for (int i = 0; i < properties.length; i++) {
            ClassProperty theProp = properties[i];
            String propName = theProp.getName();

            if(propName.equalsIgnoreCase("xmlstring") )
                   propName = theProp.getName();

            if(gUniversalExcludedProperties.contains(propName))
                continue;
            appendProperty(props,sb, indent + 1, theProp);
        }

    }

    protected void appendProperty(XMLPropertySet props,StringBuffer sb, int indent, ClassProperty prop)
    {
        Class type = prop.getType();
        if (type.isArray())
        {
            handleArrayProperty(props,sb, indent, prop);
            return;
        }
        String propName = prop.getName();
        Object value = ClassAnalyzer.getProperty(m_Client, propName);
        if (value == null)
            return;
        if(value instanceof IXMLWriter) {
            IXMLWriter realWriter = (IXMLWriter)value;
            realWriter.appendXML(props,sb, indent);
            return;

        }
        sb.append(Util.indentString(indent));
        sb.append("<");
        sb.append(propName);
        sb.append(" value=\"");
        String val = value.toString();
        sb.append(val);
        sb.append("\" />");
        sb.append("\n");
    }


    protected void handleArrayProperty(XMLPropertySet props,StringBuffer sb, int indent, ClassProperty prop)
    {
        Class type = prop.getType().getComponentType();
        String propName = prop.getName();
        if (IXMLWriter.class.isAssignableFrom(type))
        {
            IXMLWriter[] value = (IXMLWriter[]) ClassAnalyzer.getProperty(m_Client, propName);
            if (value == null)
                return;
            for (int i = 0; i < value.length; i++)
            {
                IXMLWriter ixmlWriter = value[i];
                ixmlWriter.appendXML(props,sb, indent + 1);
            }
        }
    }
}