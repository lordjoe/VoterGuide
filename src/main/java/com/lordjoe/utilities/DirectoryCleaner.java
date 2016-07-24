package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

import com.lordjoe.utilities.*;

/**
 * class com.lordjoe.Utilities.DirectoryCleaner
 * This runs through a directory deleting files modified earlier
 * then a specific time
 * @author Steve Lewis
 */
public class DirectoryCleaner {
    public static long DEFAULT_INTERVAL = TimeDuration.ONE_DAY; // 24 hours
    // Interval to test
    private IIntegerValue m_Interval;

    public DirectoryCleaner() {
        m_Interval = IntegerValueFactory.buildValue((int)DEFAULT_INTERVAL);
    }


    public int getInterval() {
        return (m_Interval.intValue());
    }

    public void setIntervalValue(IIntegerValue in) {
        m_Interval = in;
    }


    public void clean(String name) {
        clean(new File(name));
    }

    public void clean(File Dir) {
        long now = new Date().getTime();
        clean(Dir, now - getInterval(), false);
    }

    protected void clean(File Test, long cutoff, boolean cleanDirectory) {
        if (Test.isFile()) {
            cleanFile(Test, cutoff);
        } else {
            cleanDirectory(Test, cutoff, cleanDirectory);
        }
    }

    protected void cleanFile(File Test, long cutoff) {
        long ModTime = Test.lastModified();
        if (ModTime < cutoff) {
            try {
                if (!Test.isDirectory())
                    Test.delete();
            } catch (Exception ex) { // I guess we allow errors
            }
        }
    }

    protected void cleanDirectory(File Test, long cutoff, boolean cleanDirectory) {
        // System.out.println("Cleaning " + Test.getAbsolutePath());
        String[] files = Test.list();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File SubTest = new File(Test, files[i]);
                clean(SubTest, cutoff, true); // ok to  kill subdirectores
            }
            files = Test.list();
            if (cleanDirectory && files.length == 0) {
                try {
                    Test.delete();
                } catch (Exception ex) { // I guess we allow errors
                }
            }
        }
    }


    /**
     * test case - args[0] is a directory to watch
     * @param args non-null array of strings
     */
    public static void main(String[] args) {
        DirectoryCleaner TheWatcher = new DirectoryCleaner();
        TheWatcher.clean(args[0]);
    }
}
