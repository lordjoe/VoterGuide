   	 /**
*  ExitOnClose.java
*  Window listener to exit when main frame closes 
*  swing does this for free
* @author> Steven M. Lewis
* <>copyright> 
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:51 PDT 1998
* @version> 1.0
*/ 
 
package com.lordjoe.utilities;
import java.awt.event.*;
import java.awt.*;
   
  //  Window Listener
public  class ExitOnClose extends WindowAdapter {
	// Default Constructor
	public ExitOnClose() {}
	/**
	  This constructor adds this as a WindowListener
	  @param  Frame - Target frame
	*/
	public ExitOnClose(Frame f) {
		this();
		f.addWindowListener(this);
	}

     public  void windowClosing(WindowEvent e) {
        System.exit(0);
       }
 // end class MyWindowAdaptor
 }
