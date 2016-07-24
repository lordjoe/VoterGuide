package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.Utilities.TeeInputStream
 * Version of a printwriter which tees its output
 * to an alternative writer -
 * Really useful in debiggung and logging
 * I.e. to see what a servlet is reading
 * @author Steve Lewis
 */
public class TeeInputStream extends InputStream {
    private PrintWriter m_Tee;
    private StringReader m_in;

    public TeeInputStream(InputStream in, OutputStream Tee) {
        super();
        m_Tee = new PrintWriter(Tee);
        init(in);
    }

    protected void init(InputStream in) {
        String InText = FileUtilities.readInFile(in);
        m_in = new StringReader(InText);
        m_Tee.println(InText);
        m_Tee.flush();
    }


    public int read() throws IOException {
        return (m_in.read());
    }

}
