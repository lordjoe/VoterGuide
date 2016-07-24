package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

public abstract class ReaderUtilities {
    public static final Reader gSysIn = new BufferedReader(new InputStreamReader(System.in));

    public static Reader SysIn() {
        return (gSysIn);
    }

    public static void dumpStream(InputStream in) {
        new StreamDumper(in);
    }

    public static void dumpStream(InputStream in, PrintWriter Out) {
        new StreamDumper(in, Out);
    }

    public static class StreamDumper implements Runnable {
        private BufferedReader m_Reader;
        private PrintWriter m_Out;

        public StreamDumper(InputStream in) {
            this(in, WriterUtilities.sysOut());
        }

        public StreamDumper(InputStream in, PrintWriter Out) {
            m_Out = Out;
            m_Reader = new BufferedReader(new InputStreamReader(in));
            Thread Tr = new Thread(this, "StreamDumper");
            Tr.setDaemon(true);
            Tr.start();
        }

        public void run() {
            try {
                String line = m_Reader.readLine();
                while (line != null) {
                    m_Out.println(line);
                    line = m_Reader.readLine();
                }
            } catch (IOException ex) {
            }
        }
    }


}
