/*
 * Modified Steve Lewis to 
 *     1) Add Observer thread and cache added text to minimize redraw
 *     2) Add static useConsole to create console - display it 
 *         and sent output to console
 *     3) add exitOnClose to keep console up and 
 *          exit when console closes
 *   This allows console based applications to have a standing scrollable
 *      console window that is relatively efficient
 * 
 * Copyright (c) 1998, Subrahmanyam Allamaraju. All Rights Reserved.
 * 
 * Useage:
 *  import Utilities.*; // access console
 *   ...
 * 	public static void main(String args[]) {
 *		Console c = Console.useConsole(); // add console
 *		...
 *	    c.exitOnClose(); // wait for m_Close
 *	}
 *
 * 
 * Then add Console.java, ObservableStream.java and StreamObserver.java to the
 * project
 *
 * Permission to use, copy, modify, and distribute this software for
 * NON-COMMERCIAL purposes and without fee is hereby granted provided that this
 * copyright notice appears in all copies.
 *
 * This software is intended for demonstration purposes only, and comes without
 * any explicit or implicit warranty.
 *
 * Send all queries about this software to sallamar@cvimail.cv.com
 *
 */

/**
* 
*  Class Console creates a Java Console for GUI based Java Applications. Once
*  created, a Console component receives all the data directed to the standard
*  output (System.out) and error (System.err) streams.
*  <p>
*  For example, once a Java Console is created for an application, data passed
*  on to any methods of System.out (e.g., System.out.println(" ")) and
*  System.err (e.g., stack trace in case of uncought exceptions) will be
*  received by the Console.
*  <p>
*  Note that a Java Console can not be created for Applets to run on any
*  browsers due to security violations. Browsers will not let standard output
*  and error streams be redicted (for obvious reasons).
* 
* @author> Subrahmanyam Allamaraju (sallamar@cvimail.cv.com)
* <>copyright> 
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:56 PDT 1998
*/ 
package com.lordjoe.utilities;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* 
*  Class Console creates a Java Console for GUI based Java Applications. Once
*  created, a Console component receives all the data directed to the standard
*  output (System.out) and error (System.err) streams.
*  <p>
*  For example, once a Java Console is created for an application, data passed
*  on to any methods of System.out (e.g., System.out.println(" ")) and
*  System.err (e.g., stack trace in case of uncought exceptions) will be
*  received by the Console.
*  <p>
*  Note that a Java Console can not be created for Applets to run on any
*  browsers due to security violations. Browsers will not let standard output
*  and error streams be redicted (for obvious reasons).
* 
* @author> Subrahmanyam Allamaraju (sallamar@cvimail.cv.com)
* <>copyright> 
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:56 PDT 1998
*/ 
public class Console extends JFrame implements /*StreamObserver, */  Runnable {

    //- ******************* 
    //- Fields 
    /**
    *  <Add Comment Here>
    */ 
    Thread m_Observer;

// Added Steve Lewis

    /**
    *  <Add Comment Here>
    */ 
    StringBuffer m_Pending;

// Added Steve Lewis - temporary cache for added text

    /**
    *  <Add Comment Here>
    */ 
    public static final int REDRAW_INTERVAL = 250;

// how often redraws allowed msec

    /**
    *  <Add Comment Here>
    */ 
    JTextArea aTextArea;

    /**
    *  <Add Comment Here>
    */ 
    ObservableStream errorDevice;

    /**
    *  <Add Comment Here>
    */ 
    ObservableStream outputDevice;

    /**
    *  <Add Comment Here>
    */ 
    ByteArrayOutputStream _errorDevice;

    /**
    *  <Add Comment Here>
    */ 
    ByteArrayOutputStream _outputDevice;

    /**
    *  <Add Comment Here>
    */ 
    PrintStream errorStream;

    /**
    *  <Add Comment Here>
    */ 
    PrintStream outputStream;

    /**
    *  <Add Comment Here>
    */ 
    PrintStream _errorStream;

    /**
    *  <Add Comment Here>
    */ 
    PrintStream _outputStream;

    /**
    *  <Add Comment Here>
    */ 
    JButton clear;

    /**
    *  <Add Comment Here>
    */ 
    JButton close;

    /**
    *  <Add Comment Here>
    */ 
    private boolean m_isOpen;


    //- ******************* 
    //- Methods 
// Added S.Lewis

    /**
    *  Constructor of Console
    */ 
    public Console() {
        super("Java Console");
        aTextArea = new JTextArea();
        aTextArea.setEditable(false);
        clear = new JButton("Clear");
        close = new JButton("Close");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aTextArea.setText("");
            }
        }
        );
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
        );
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,0));
        buttonPanel.add(clear);
        buttonPanel.add(close);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center",aTextArea);
        getContentPane().add("South",buttonPanel);
        _errorStream = System.err;
        _outputStream = System.out;
        _outputDevice = new ByteArrayOutputStream();
        _errorDevice = new ByteArrayOutputStream();
        this.pack();
        this.setError();
        this.setOutput();
        setEnabled(true);
        m_Pending = new StringBuffer();
        m_Observer = new Thread(this);
        m_Observer.start();
    }

    /**
    * 
    *  Clears the Console.
    * <>policy <Add Comment Here>
    */ 
    public void clear() {
        try {
            outputDevice.writeTo(_outputDevice);
        }
        catch(IOException e) {
        }
        outputDevice.reset();
        try {
            errorDevice.writeTo(_errorDevice);
        }
        catch(IOException e) {
        }
        errorDevice.reset();
        aTextArea.setText("");
    }

    /**
    * 
    *  Sets the error device to the Console if not set already.
    * @see #resetError
    */ 
    public final void setError() {
        errorDevice = new ObservableStream();
    //    errorDevice.addStreamObserver(this);
        errorStream = new PrintStream(errorDevice,true);
        System.setErr(errorStream);
    }

    /**
    * 
    *  Resets the error device to the default. Console will no longer receive
    *  data directed to the error stream.
    * @see #setError
    */ 
    public final void resetError() {
        System.setErr(_errorStream);
    }

    /**
    * 
    *  Sets the output device to the Console if not set already.
    * @see #resetOutput
    */ 
    public final void setOutput() {
        outputDevice = new ObservableStream();
    //    outputDevice.addStreamObserver(this);
        outputStream = new PrintStream(outputDevice,true);
        System.setOut(outputStream);
    }

    /**
    * 
    *  Resets the output device to the default. Console will no longer receive
    *  data directed to the output stream.
    * @see #setOutput
    */ 
    public final void resetOutput() {
        System.setOut(_outputStream);
    }

    /**
    * 
    *  Gets the minimumn size.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public Dimension getMinimumSize() {
        return new Dimension(500,400);
    }

    /**
    * 
    *  Gets the preferred size.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    /**
    *  <Add Comment Here>
    * @param s <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    protected synchronized void addInput(String s) {
        if(s == null || s.length() == 0) {
            return;
        }
        m_Pending.append(s);
    }

    /**
    *  <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    protected synchronized void testPendingInput() {
        if(m_Pending.length() > 0) {
            aTextArea.append(m_Pending.toString());
            m_Pending.setLength(0);
        }
    }

    /**
    *  <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public void streamChanged() {
        addInput(outputDevice.toString());
        try {
            outputDevice.writeTo(_outputDevice);
        }
        catch(IOException e) {
        }
        outputDevice.reset();
        errorStream.checkError();
        addInput(errorDevice.toString());
        try {
            errorDevice.writeTo(_errorDevice);
        }
        catch(IOException e) {
        }
        errorDevice.reset();
    }

    /**
    * 
    *  Returns contents of the error device directed to it so far. Calling
    *  <a href="#clear">clear</a> has no effect on the return data of this method.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public ByteArrayOutputStream getErrorContent() throws IOException {
        ByteArrayOutputStream newStream = new ByteArrayOutputStream();
        _errorDevice.writeTo(newStream);
        return newStream;
    }

    /**
    * 
    *  Returns contents of the output device directed to it so far. Calling
    *  <a href="#clear">clear</a> has no effect on the return data of this method.
    * @return <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public ByteArrayOutputStream getOutputContent() throws IOException {
        ByteArrayOutputStream newStream = new ByteArrayOutputStream();
        _outputDevice.writeTo(newStream);
        return newStream;
    }

    /**
    *  <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public void run() {
        while(m_isOpen) {
            try {
                Thread.sleep(REDRAW_INTERVAL);
            }
            catch(InterruptedException ex) {
            }
            testPendingInput();
        }
    }

//
// Override sets is Open to unblock
// wait for m_Close

    /**
    *  <Add Comment Here>
    * @param b <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public void setVisible(boolean b) {
        super.setVisible(b);
        setIsOpen(b);
    }

//
// Will not return until the console is closed
// frequently usew with exitOnClose

    /**
    *  <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public synchronized void waitForClose() {
        while(m_isOpen) {
            try {
                wait();
            }
            catch(InterruptedException ex) {
            }
        }
    }

//
// When the console is closed exit the program
// Use this where a program wants wo write to the 
// console allowing the user to look at the data 
// and exit when he wants to m_Close

    /**
    *  <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public void exitOnClose() {
        waitForClose();
        System.exit(0);
    }

//
// Used to unblock waitForClose

    /**
    *  <Add Comment Here>
    * @param b <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public synchronized void setIsOpen(boolean b) {
        m_isOpen = b;
        notifyAll();
    }

    /**
    *  <Add Comment Here>
    * @return <Add Comment Here>
    */ 
    public static Console useConsole() {
        Console console = new Console();
        console.setVisible(true);
        console.setOutput();
        return(console);
    }


//- ******************* 
//- End Class Console
}

class ObservableStream extends ByteArrayOutputStream {

    //- ******************* 
    //- Fields 
    /**
    *  <Add Comment Here>
    */ 
    Vector streamObservers = new Vector();


    //- ******************* 
    //- Methods 
    /**
    *  <Add Comment Here>
    * @param o <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
//    void addStreamObserver(StreamObserver o) {
//        streamObservers.addElement(o);
//    }
//
//    /**
//    *  <Add Comment Here>
//    * @param o <Add Comment Here>
//    * <>policy <Add Comment Here>
//    */
//    void removeStreamObserver(StreamObserver o) {
//        streamObservers.removeElement(o);
//    }
//
//    /**
//    *  <Add Comment Here>
//    * <>policy <Add Comment Here>
//    */
    void notifyObservers() {
//        for(int i = 0; i < streamObservers.size(); i ++)
//            ((StreamObserver) streamObservers.elementAt(i)).streamChanged();
    }

    /**
    *  <Add Comment Here>
    * @param b <Add Comment Here>
    * @param off <Add Comment Here>
    * @param len <Add Comment Here>
    * <>policy <Add Comment Here>
    */ 
    public void write(byte[] b,int off,int len) {
        super.write(b,off,len);
        notifyObservers();
    }


//- ******************* 
//- End Class ObservableStream
}
