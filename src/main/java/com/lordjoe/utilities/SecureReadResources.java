
/**{ file
    @name SecureReadResources.java
    @function - Interface implemented by classes accessing specific security manager features
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:27 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**{ class
    @name SecureReadResources
    @function Interface implemented by classes accessing specific security manager features
}*/
public interface SecureReadResources
{
    public Object runSecurely(Executor ex);
    public Icon getResourceIcon(Class target,String name);
    public StringBuffer readInResource(Class target,String name) ;
}
