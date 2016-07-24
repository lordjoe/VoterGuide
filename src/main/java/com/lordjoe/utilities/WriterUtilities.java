package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * utility methods for dealing with writers
 * @author Steve Lewis
 */
public abstract class WriterUtilities {
    public static final PrintWriter gSysOut = new PrintWriter(new OutputStreamWriter(System.out));

    public static PrintWriter sysOut() {
        return (gSysOut);
    }

}
