// in order to read files in internat Explorer we need to assert
// make a ms Security manager call
// import com.ms.security.*;

/**{ file
    @name SecurityUtilities.java
    @function - Fuinctions whuch involve secutiry
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
    @name FileUtilities
    @function FileUtilities offer a number of services relating to files including
         file dialog, directory enumerators and keeping track of open files
}*/
public abstract class SecurityUtilities extends Nulleton {
    
    public static Object runSecurely(Executor ex) 
    {
        SecurityManager sm = System.getSecurityManager();
        Assertion.validate(sm != null);
        String SMName = sm.getClass().getName().toLowerCase();
        if(SMName.indexOf("netscape") > -1)
            return(netscapeRunSecurely(ex));
        if(SMName.indexOf("microsoft") > -1)
            return(microsoftRunSecurely(ex));
        return(null);
    }
    
    public static Icon secureGetResourceIcon(Class target,String name) 
    {
        SecurityManager sm = System.getSecurityManager();
        Assertion.validate(sm != null);
        String SMName = sm.getClass().getName().toLowerCase();
        if(SMName.indexOf("netscape") > -1)
            return(netscapeSecureGetResourceIcon(target,name));
        if(SMName.indexOf("microsoft") > -1)
            return(microsoftSecureGetResourceIcon(target,name));
        return(null);
    }
    
    public static Icon netscapeSecureGetResourceIcon(Class target,String name) 
    {
        SecureReadResources Reader = genSecureClass("com.lordjoe.Utilities.NetscapeSecureReader");
        if(Reader != null)
            return(Reader.getResourceIcon(target,name));
         return(null);
    }
    
    public static StringBuffer netscapeSecureReadInResource(Class target,String name)
    {
        SecureReadResources Reader = genSecureClass("com.lordjoe.Utilities.NetscapeSecureReader");
        if(Reader != null)
            return(Reader.readInResource(target,name));
         return(null);
    }
   
    public static Icon microsoftSecureGetResourceIcon(Class target,String name) 
    {
        SecureReadResources Reader = genSecureClass("com.lordjoe.Utilities.MicrosoftSecureReader");
        if(Reader != null)
            return(Reader.getResourceIcon(target,name));
         return(null);
    }
   
    public static StringBuffer microsoftSecureReadInResource(Class target,String name)
    {
        SecureReadResources Reader = genSecureClass("com.lordjoe.Utilities.MicrosoftSecureReader");
        if(Reader != null)
            return(Reader.readInResource(target,name));
         return(null);
    }
     /**{ method
        @name readInResource
        @function reads all the data in a resource into a StringBuffer
        @param Source - class for resource
        @param name the file name
        @return StringBuffer holding file bytes
    }*/
    public static StringBuffer secureReadInResource(Class Source,String name) {
        SecurityManager sm = System.getSecurityManager();
        Assertion.validate(sm != null);
        String SMName = sm.getClass().getName().toLowerCase();
        if(SMName.indexOf("netscape") > -1)
            return(netscapeSecureReadInResource(Source,name));
        if(SMName.indexOf("microsoft") > -1)
            return(microsoftSecureReadInResource(Source,name));
        return(null);
    }
    
    
    protected static SecureReadResources genSecureClass(String name)
    {
        try {
            Class reader = Class.forName(name);
            SecureReadResources Instance = (SecureReadResources)reader.newInstance();
            return(Instance);
        }
        catch(Exception ex) {
            return(null);
        }
    }
    
    public static Object netscapeRunSecurely(Executor ex) 
    {
        SecureReadResources Reader = genSecureClass("com.lordjoe.Utilities.NetscapeSecureReader");
        if(Reader != null)
            return(Reader.runSecurely(ex));
         return(null);
    }

    public static Object microsoftRunSecurely(Executor ex) 
    {
         return(null);
    }

//- *******************
//- End Class IsDirectoryFilter
}
