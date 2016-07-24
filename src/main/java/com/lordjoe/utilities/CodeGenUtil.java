package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;
import java.util.*;


import org.w3c.dom.*;


/**
 * A colleciton of functions for generating Code from Templates
 * com.lordjoe.Utilities.CodeGenUtil
 */
public abstract class CodeGenUtil extends Nulleton {
    public static final String EMPTY_LINE_TAG = "__EMPTY_LINE";

    public static String handleTemplate(Element doc, String TagName, Map Items, int indent) {
        Element[] DocItems = XMLUtil.getElementsWithName(doc, TagName);
        if (DocItems.length == 0)
            throw new IllegalArgumentException("Tag '" + TagName + "' not found in document");
        if (DocItems.length > 1)
            throw new IllegalArgumentException("Tag '" + TagName + "' multiply found in document");
        String[] TextItems = XMLUtil.getElementText(DocItems[0]);
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < TextItems.length; i++) {
            // System.out.println(TextItems[i]);
            out.append(TextItems[i]); //.trim());
            out.append("\n");
        }
        String RealTemplate = out.toString();
        return (handleTemplate(RealTemplate, Items, indent));
    }

    public static String handleTemplate(String Template, Map Items, int indent) {
        StringBuffer out = new StringBuffer(Template.length());
        String[] lines = Util.parseLines(Template);
        for (int i = 0; i < lines.length; i++) {
            //           out.append(Util.indentString(indent));
            out.append(handleTemplateLine(lines[i], Items, indent));
            out.append("\n");
        }
        return (out.toString());
    }


    public static String handleTemplateLine(String Template, Map Items, int indent) {
        String OneItem = findTemplate(Template);
        while (OneItem != null) {
            Template = substituteTemplate(Template, OneItem, Items, indent);
            OneItem = findTemplate(Template);
        }
        StringBuffer out = new StringBuffer(Util.indentString(indent));
        out.append(Template);
   //     out.append(escapeForQuotes(Template));
        return (out.toString());
    }
    /**{ method
     @param in input string
     @return string without control characters
     }*/
    public static String escapeForQuotes(String in) {
        StringBuffer sb = new StringBuffer(in.length());
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            switch(c)  {
                case '\"':
                case '\'':
                case '\\':
                    sb.append('\\');
                 default:
                sb.append(c);
            }
        }
        return (sb.toString());
    }

    protected static String substituteTemplate(String Template, String Item, Map Items, int indent) {
        String Test = Item.substring(1, Item.length() - 1); // strip %
        // Hard code this to keep empty lines
        if (Test.equals(EMPTY_LINE_TAG)) {
            return (Util.replaceOnceInString(Template, Item, "\n" + Util.indentString(indent)));
        }
        String Substitute = (String) Items.get(Test);
        if (Substitute == null)
            throw new IllegalArgumentException("Key '" + Test + "' not found\n" +
                    "Available Keys are: " + Util.buildListString(Util.getKeyStrings(Items), 1));
        return (Util.replaceOnceInString(Template, Item, Substitute));
    }

    protected static String findTemplate(String Template) {
        int start = Template.indexOf("%");
        if (start == -1 || start > Template.length() - 1)
            return (null); // no start
        int end = Template.indexOf("%", start + 1);
        if (end == -1 )
            return (null); // no endreturn (null); // no end
        String ret = Template.substring(start, end + 1);
        if (ret.length() == 0)
            return (null); // not empty
        if (Util.containsNonLetterOrUnderscore(ret.substring(1,ret.length() - 1)))
            return (null); // no non-letters
        return (ret);
    }

    public static void buildEscapedFile(String fileIn,String fileout)
    {
        String[] lines = FileUtilities.readInLines(fileIn);
        String outStr = stringsToTemplate(lines);
        FileUtilities.writeFile(fileout,outStr);
    }

    public static String stringsToTemplate(String[] in)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length; i++) {
            String s = in[i];
            appendStringTemplate(sb,s);
        }
        return(sb.toString());
    }

    protected static void appendStringTemplate(StringBuffer sb,String s)
    {
        String escaped = Util.buildEscapedString(s);
        sb.append("    \"");
        sb.append(escaped);
        sb.append("\" + \n");
    }

   public static void main(String[] args)
   {
       buildEscapedFile("D:/OneWeb/eagle/com/microsoft/cdo/enum/ActMsgAddressListTypesEnum.java","C:/temp/enumtempl.txt");
   }
}

