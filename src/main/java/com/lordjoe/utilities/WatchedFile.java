
package com.lordjoe.utilities;

import java.util.*;
import java.io.*;

/**
 * holds a file and reports if it is modified since last set
 * only actually checks each TEST_INTERVAL
 * com.lordjoe.Utilities.WatchedFile
 * @author Steve Lewis
 * @see IObjectDescructor
 */
public class WatchedFile {
    public static final int TEST_INTERVAL = (int)TimeDuration.ONE_MINUTE;


    //- *******************
    //- Fields
    private long m_ModifiedTime;
    private long m_NextInterval;
    private IIntegerValue m_IntervalTime = IntegerValueFactory.buildValue(TEST_INTERVAL);
    private File m_File;

    //- *******************
    //- Methods
    /** constructor
     @name WatchedFile
     @function Constructor of WatchedFile
     @param ModifiedTime iModifiedTime millisec
     */
    public WatchedFile(File in) {
        super();
        setFile(in);
    }

    /** constructor
     @name WatchedFile
     @function Constructor of WatchedFile
     @param ModifiedTime iModifiedTime millisec
     */
    public WatchedFile(File in, int time) {
        this(in);
        setIntervalTime(IntegerValueFactory.buildValue(time));
    }

    /** constructor
     @name WatchedFile
     @function Constructor of WatchedFile
     @param ModifiedTime iModifiedTime millisec
     */
    public WatchedFile(File in, IIntegerValue time) {
        this(in);
        setIntervalTime(time);
    }


    public long getModifiedTime() {
        return (m_ModifiedTime);
    }

    public void setModifiedTime(long in) {
        m_ModifiedTime = in;
    }

    public File getFile() {
        return (m_File);
    }

    public void setFile(File in) {
        m_File = in;
        setModifiedTime(in.lastModified());
    }

    public void setIntervalTime(IIntegerValue in) {
        m_IntervalTime = in;
    }

    public IIntegerValue getIntervalTime() {
        return (m_IntervalTime);
    }

    public void setCurrent()
    {
       setModifiedTime(getFile().lastModified());
    }

    public boolean isCurrent() {
        long TestTime = SysTime.getTic();
        if (TestTime < m_NextInterval) {
            return (true); // only test every TEST_INTERVAL
        }
        File TestFile = getFile();
        if (TestFile == null)
            return (true); // ???
        if (TestFile.lastModified() == getModifiedTime()) {
            m_NextInterval = TestTime + m_IntervalTime.intValue();
            return (true);
        }

        return (false); // OK easier to breakpoint
    }


    // Makes a WatchedFile
    // Adds 10 Renew able String
    // and keeps adding new renewable strings which
    // keep expiring and being recreated
    //
    public static void main(String[] args) {
    }



//- *******************
//- End Class WatchedFile
}
