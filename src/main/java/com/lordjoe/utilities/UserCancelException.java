   	 /**
*  UserCancelException.java
*
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

/**
   Exception Thrown when user cancels an operation
  */

 public class UserCancelException extends RuntimeException {
    public UserCancelException() {
    }
    
    public UserCancelException(String s) {
        super(s);
    }
    
 }
