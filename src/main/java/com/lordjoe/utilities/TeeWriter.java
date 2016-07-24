package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.Utilities.TeeWriter
 * Version of a printwriter which tees its output
 * to an alternative writer -
 * Really useful in debiggung and logging
 * I.e. to see what a servlet is returning
 * @author Steve Lewis
 */
public class TeeWriter extends PrintWriter {
    private PrintWriter m_Tee;

    public TeeWriter(OutputStream out, OutputStream Tee) {
        super(out);
        m_Tee = new PrintWriter(Tee);
    }

    /** Flush the stream. */
    public void flush() {
        super.flush();
        m_Tee.flush();
    }

    /** Write a single character. */
    public void write(int c) {
        super.write(c);
        m_Tee.write(c);
    }

    /** Write a portion of an array of characters. */
    public void write(char buf[], int off, int len) {
        super.write(buf, off, len);
        m_Tee.write(buf, off, len);
    }

    /** Write a portion of an array of characters. */
    public void write(char buf[]) {
        super.write(buf);
        m_Tee.write(buf);
    }

    /** Write a portion of a string. */
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        m_Tee.write(s, off, len);
    }

    public void write(String s) {
        super.write(s);
        m_Tee.write(s);
    }

    public void print(boolean b) {
        super.print(b);
        m_Tee.print(b);
    }

    public void print(char c) {
        super.print(c);
        m_Tee.print(c);
    }

    public void print(int i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(long i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(String i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(short i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(float i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(double i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(char[] i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void print(Object i) {
        super.print(i);
        m_Tee.print(i);
    }

    public void println() {
        super.println();
        m_Tee.println();
    }

    public void println(boolean x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(char x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(short x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(int x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(long x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(byte x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(float x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(double x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(String x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(char[] x) {
        super.println(x);
        m_Tee.println(x);
    }

    public void println(Object x) {
        super.println(x);
        m_Tee.println(x);
    }


}
