package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

public abstract class Visualizer extends Nulleton {
    // do not build this
    private Visualizer() {
    }

    public static final String[] INDENT_STRINGS = {"", "    ", "        ", "             ",
                                                   "                ", "                     ", "                         ",
                                                   "                             "
    };

    public static void show(Object in) {
        show(null, in);
    }

    public static void show(String message, Object in) {
        show(System.out, message, in);
    }

    public static void show(PrintStream out, String message, Object in) {
        String fullMessage = buildString(message, in);
        out.println(fullMessage);
    }

    public static String buildString(Object in) {
        return (buildString(null, in));
    }

    public static String buildString(String message, Object in) {
        StringBuffer sb = new StringBuffer();
        buildString(sb, message, in, 0);
        return (sb.toString());
    }

    protected static void buildString(StringBuffer sb, String message, Object in, int indent) {
        if (message != null) {
            appendWithCr(sb, message, indentString(indent));
        }
        String objectText = buildObjectText(in, indent);
        appendWithCr(sb, objectText, indentString(indent));
    }

    protected static String buildObjectText(Object in, int indent) {
        if (in == null)
            return ("null");
        if (in instanceof String)
            return ((String) in);
        if (in instanceof Collection)
            return (buildCollectionText((Collection) in, indent));
        if (in instanceof Map)
            return (buildMapText((Map) in, indent));
        return (in.toString());
    }

    protected static String buildCollectionText(Collection in, int indent) {
        StringBuffer sb = new StringBuffer();
        appendWithCr(sb, "{", indentString(indent));
        Iterator it = in.iterator();
        while (it.hasNext()) {
            Object item = it.next();
            String itemText = buildObjectText(item, indent + 1);
            appendWithCr(sb, itemText + ",", indentString(indent));
        }
        appendWithCr(sb, "}", indentString(indent));
        return (sb.toString());
    }

    protected static String buildMapText(Map in, int indent) {
        StringBuffer sb = new StringBuffer();
        appendWithCr(sb, "{", indentString(indent));
        Iterator it = in.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = in.get(key);
            String itemText = buildObjectText(key, indent + 1);
            String valueText = buildObjectText(value, indent + 1);
            appendWithCr(sb, itemText + " = " + valueText + ",", indentString(indent));
        }
        appendWithCr(sb, "}", indentString(indent));
        return (sb.toString());
    }


    protected static void appendWithCr(StringBuffer sb, String message, String indent) {
        int N = message.length();
        for (int i = 0; i < N; i++) {
            char c = message.charAt(i);
            sb.append(c);
            if (c == '\n')
                sb.append(indent);
        }
        sb.append("\n");
        sb.append(indent);
    }


    public static String indentString(int in) {
        if (in < INDENT_STRINGS.length)
            return (INDENT_STRINGS[in]);
        return (buildIndentString(in));
    }

    protected static String buildIndentString(int in) {
        StringBuffer sb = new StringBuffer(4 * in);
        for (int i = 0; i < in; i++) {
            sb.append(INDENT_STRINGS[1]);
        }
        return (sb.toString());
    }

}
