package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.Utilities.PropertyDumper
 * Written largely to test Runtime.exec this dumpe the System properties
 * @author Steve Lewis
 */
public class PropertyDumper {
    public static NameValue[] getSystemProperties() {
        Properties Props = System.getProperties();
        return (Util.mapToNameValues(Props));
    }

    public static void dumpSystemProperties() {
        dumpSystemProperties(System.out);
    }

    public static void dumpSystemProperties(PrintStream out) {
        NameValue[] values = getSystemProperties();
        for (int i = 0; i < values.length; i++) {
            out.print(values[i].m_Name);
            out.print("=");
            out.println(values[i].m_Value);
        }
    }

    public static void dumpSystemProperties(String[] items) {
        dumpSystemProperties(items, System.out);
    }

    public static void dumpSystemProperties(String[] items, PrintStream out) {
        for (int i = 0; i < items.length; i++) {
            out.print(items[i]);
            out.print("=");
            out.println(System.getProperty(items[i]));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            dumpSystemProperties();
            ThreadUtilities.waitFor(2000);
        }

    }

}
