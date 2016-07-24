/**{ file
    @name SystemProperties.java
    @function this class - a Nullity will retrieve system
         properties
    @author> Steven M. Lewis
         , John D. Mitchell, Apr 28, 1998
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
import java.util.*;
/**{ class
    @name SystemProperties
    @function <Add Comment Here>
}*/ 
public abstract class SystemProperties {

    //- ******************* 
    //- Fields 
    /**{ field
        @name gProperties
        @function <Add Comment Here>
    }*/ 
    private static Hashtable gProperties;


    //- ******************* 
    //- Methods 
    /**{ method
        @name getProperty
        @function <Add Comment Here>
        @param name <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static Object getProperty(String name) {
        if(gProperties == null) {
            initProperties();
        }
        return(gProperties.get(name));
    }

    /**{ method
        @name getBrowserName
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String getBrowserName() {
        if(gProperties == null) {
            initProperties();
        }
        return((String) gProperties.get("BrowserName"));
    }

    /**{ method
        @name getJavaVendor
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String getJavaVendor() {
        if(gProperties == null) {
            initProperties();
        }
        return((String) gProperties.get("JavaVendor"));
    }

    /**{ method
        @name getBrowserVersion
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static double getBrowserVersion() {
        if(gProperties == null) {
            initProperties();
        }
        Double d =(Double) gProperties.get("BrowserVersionNumber");
        if(d == null) {
            return(0.0);
        }
        return(d.doubleValue());
    }

    /**{ method
        @name getJavaVersion
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static double getJavaVersion() {
        if(gProperties == null) {
            initProperties();
        }
        Double d =(Double) gProperties.get("JavaVersionNumber");
        if(d == null) {
            return(0.0);
        }
        return(d.doubleValue());
    }

    /**{ method
        @name initProperties
        @function <Add Comment Here>
    }*/ 
    protected static void initProperties() {
        try {
            getProperties();
            String BrowserName =(String) gProperties.get("browser.vendor");
            if(BrowserName != null) {
                int dotLoc = BrowserName.indexOf(".");
                if(dotLoc > 0) {
                    BrowserName = BrowserName.substring(0,dotLoc);
                }
                gProperties.put("BrowserName",BrowserName);
            }
            String BrowserVersion =(String) gProperties.get("browser.version");
            if(BrowserVersion != null) {
                Double d = new Double(versionToDouble(BrowserVersion));
                gProperties.put("BrowserVersionNumber",d);
            }
            String JavaVendor =(String) gProperties.get("java.vendor");
            if(JavaVendor != null) {
                gProperties.put("JavaVendor",JavaVendor);
            }
            String JavaVersion =(String) gProperties.get("java.version");
            if(JavaVersion != null) {
                Double d = new Double(versionToDouble(JavaVersion));
                while(d.doubleValue() > 10.0) 
                    // version 1.1 is given as 11 - convert to 1.1
                d = new Double(d.doubleValue() / 10.0);
                gProperties.put("JavaVersionNumber",d);
            }
        }
        catch(Exception ex) {
            // not important enough to worry
        }
    }

    /**{ method
        @name getProperties
        @function <Add Comment Here>
    }*/ 
    protected static void getProperties() {
        String key = null;
        String value = null;
        gProperties = new Hashtable();
        // Alas, rather than doing something intelligent when trying
        // to foil crackers and other nefarious folks, the following
        // will likely trigger a security exception if your applet
        // is running in a restricted environment (like a browser).
        try {
            Properties props = System.getProperties();
            Enumeration e = props.propertyNames();
            while(e.hasMoreElements()) {
                key =(String) e.nextElement();
                value = props.getProperty(key);
                gProperties.put(key,value);
            }
        }
        catch(SecurityException se) {
            initListedProperties();
        }
    }

    /**{ method
        @name initListedProperties
        @function <Add Comment Here>
    }*/ 
    protected static void initListedProperties() {
        // Oh well, no access to everything.
        // The following list of properties are generally
        // available in any context.
        String keys[] = {
            "java.vendor","java.vendor.url","java.version","java.class.version","os.name","os.arch","os.version","file.separator","path.separator","line.separator","browser","browser.vendor","browser.version" }
        ;
        for(int i = 0; i < keys.length; i ++) {
            try {
                String key = keys[i];
                String value = System.getProperty(key);
                gProperties.put(key,value);
            }
            catch(SecurityException see) {
                ;
            }
        }
    }

    /**{ method
        @name versionToDouble
        @function <Add Comment Here>
        @param s <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static double versionToDouble(String s) {
        return(versionToDouble(s,1.0));
    }

// turn 1.2.4 into 1.24 // will not work 

    /**{ method
        @name versionToDouble
        @function <Add Comment Here>
        @param s <Add Comment Here>
        @param factor <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    protected static double versionToDouble(String s,double factor) {
        if(s == null || s.length() == 0) {
            return(0.0);
        }
        int dotIndex = s.indexOf(".");
        if(dotIndex > 0) {
            try {
                double ret = 0;
                String element = s.substring(0,dotIndex);
                ret = factor * Integer.parseInt(element);
                dotIndex ++;
                if(dotIndex < s.length()) {
                    ret += versionToDouble(s.substring(dotIndex),factor / 10.0);
                }
                return(ret);
            }
            catch(NumberFormatException ex) {
                ex = null;
            }
        }
        else {
            try {
                return(factor * Integer.parseInt(s));
            }
            catch(NumberFormatException ex) {
                ex = null;
            }
        }
        return(0.0);
    }


//- ******************* 
//- End Class SystemProperties
}
