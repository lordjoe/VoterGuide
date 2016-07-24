package com.lordjoe.general;

import com.lordjoe.utilities.*;
import com.lordjoe.tasks.*;
import com.lordjoe.exceptions.*;

import java.io.*;

/**
 * com.lordjoe.general.SlowWriter
 *
 * @author John Connor
 *         created Aug 17, 2007
 */
public class SlowWriter implements Runnable
{
    public static SlowWriter[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = SlowWriter.class;

    public static void writeShowly(File inFile, File outFile, int linesPerSec, int groupSize)
    {
        SlowWriter sw = new SlowWriter( inFile,  outFile,  linesPerSec,  groupSize);
        TaskManager.runNotification(sw);
    }
    private final String[] m_Lines;
    private final File m_OutputFile;
    private int m_Index;
    private int m_IntervalMillisec;
    private final int m_GroupSize;

    private SlowWriter(File inFile, File outFile, int linesPerSec, int groupSize) {
        m_Lines = FileUtilities.readInAllLines(inFile);
        m_GroupSize = groupSize;
        m_IntervalMillisec = (1000 * groupSize) / linesPerSec;
        m_OutputFile = outFile;
    }


    public void run() {
        try {
            while(m_Index < m_Lines.length) {
                writeLines();
                ThreadUtilities.waitFor(m_IntervalMillisec);
            }
        } catch (IOException e) {
            throw new WrapperException(e);
        }

    }

    private void writeLines() throws IOException{
         FileOutputStream os = new FileOutputStream(m_OutputFile,true);
         PrintWriter pw = new PrintWriter(os);
        int i = m_Index;
        for (; i < Math.min(m_Lines.length,m_Index + m_GroupSize); i++) {
            pw.println(m_Lines[i]);
        }
        pw.close();
        m_Index = i;

    }
}
