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
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.Icon;
import java.awt.*;
import javax.swing.ImageIcon;

/**{ class
    @name FileUtilities
    @function FileUtilities offer a number of services relating to files including
         file dialog, directory enumerators and keeping track of open files
}*/
public abstract class NetscapeSecureReader implements SecureReadResources
{
//    public Object runSecurely(Executor operation)
//    {
//        try {
//            PrivilegeManager privMgr  = PrivilegeManager.getPrivilegeManager();
//            privMgr.enablePrivilege("UniversalConnect");
//            privMgr.enablePrivilege("UniversalFileAccess");
//            return(operation.doExecute());
//         }
//        catch(Exception ex) {
//        }
//        return(null);
//    }
//
//
//    public Icon getResourceIcon(Class target,String name)
//    {
//        try {
//            AppletSecurity TheManager = (netscape.security.AppletSecurity)System.getSecurityManager();
////            TheManager.setScopePermission();
//                InputStream is;
//                ByteArrayOutputStream baos;
//                Image img1;
//
//                is = this.getClass().getResourceAsStream(name);
//
//                if (is == null)
//                    return(null);
//                baos = new ByteArrayOutputStream();
//                try
//                {
//                    int c;
//                    while((c = is.read()) >= 0)
//                    baos.write(c);
//                    img1 = Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
//                    if(img1 != null)
//                        return(new ImageIcon(img1));
//                    return(null);
//                }
//                catch(IOException e)
//                {
//                    return(null);
//                }
//
// //           TheManager.resetScopePermission();
//        }
//        catch(Exception ex) {
//            return(null);
//        }
//    }
//    public StringBuffer readInResource(Class target,String name)
//    {
//        try {
//            AppletSecurity TheManager = (netscape.security.AppletSecurity)System.getSecurityManager();
//   //         TheManager.setScopePermission();
//            InputStream in = target.getResourceAsStream(name);
//   //         TheManager.resetScopePermission();
//            if(in != null)
//                return(FileUtilities.readInFile(in));
//            return(null);
//        }
//        catch(Exception ex) {
//            return(null);
//        }
//    }
//
}
    
