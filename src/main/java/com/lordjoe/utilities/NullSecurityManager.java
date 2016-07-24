package com.lordjoe.utilities;


import java.io.*;
import java.net.*;
import java.security.*;
import java.io.FileDescriptor;
import java.io.File;
import java.io.FilePermission;
import java.awt.AWTPermission;
import java.util.PropertyPermission;
import java.lang.RuntimePermission;
import java.net.SocketPermission;
import java.net.NetPermission;
import java.util.Hashtable;
import java.net.InetAddress;
import java.lang.reflect.Member;
import java.lang.reflect.*;
import java.net.URL;


/**
 * @author Symantec Internet Tools Division
 * @version 1.0
 * @since VCafe 3.0
 */

public class NullSecurityManager
        extends SecurityManager {
    /**
     * Throws a <code>SecurityException</code> if the requested
     * access, specified by the given permission, is not permitted based
     * on the security policy currently in effect.
     * <p>
     * This method calls <code>AccessController.checkPermission</code>
     * with the given permission.
     *
     * @param     perm   the requested permission.
     * @exception SecurityException if access is not permitted based on
     *		  the current security policy.
     * @since     JDK1.2
     */
    public void checkPermission(Permission perm) {
    }

    /**
     * @since VCafe 3.0
     */
    public void checkCreateClassLoader() {
    }

    /**
     * @param g TODO
     * @since VCafe 3.0
     */

    public void checkAccess(Thread g) {
    }

    /**
     * @param g non-null thread group
     * @since VCafe 3.0
     */

    public void checkAccess(ThreadGroup g) {
    }

    /**
     * @param status non-null thread group
     * @since VCafe 3.0
     */

    public void checkExit(int status) {
    }

    /**
     * @param cmd non-null thread group
     * @since VCafe 3.0
     */

    public void checkExec(String cmd) {
    }

    /**
     * @param lib non-null thread group
     * @since VCafe 3.0
     */

    public void checkLink(String lib) {
    }

    /**
     * @param fd non-null thread group
     * @since VCafe 3.0
     */

    public void checkRead(FileDescriptor fd) {
    }

    /**
     * @param file non-null thread group
     * @since VCafe 3.0
     */

    public void checkRead(String file) {
    }

    /**
     * @param file non-null thread group
     * @param context non-null thread group
     * @since VCafe 3.0
     */

    public void checkRead(String file, Object context) {
    }

    /**
     * @param fd non-null thread group
     * @since VCafe 3.0
     */

    public void checkWrite(FileDescriptor fd) {
    }

    /**
     * @param file non-null thread group
     * @since VCafe 3.0
     */

    public void checkWrite(String file) {
    }

    /**
     * @param file non-null thread group
     * @since VCafe 3.0
     */

    public void checkDelete(String file) {
    }

    /**
     * @param host non-null thread group
     * @param port non-null thread group
     * @since VCafe 3.0
     */

    public void checkConnect(String host, int port) {
    }

    /**
     * @param host non-null thread group
     * @param port non-null thread group
     * @param context non-null thread group
     * @since VCafe 3.0
     */

    public void checkConnect(String host, int port, Object context) {
    }

    /**
     * @param port non-null thread group
     * @since VCafe 3.0
     */

    public void checkListen(int port) {
    }

    /**
     * @param host non-null thread group
     * @param port non-null thread group
     * @since VCafe 3.0
     */

    public void checkAccept(String host, int port) {
    }

    /**
     * @param maddr non-null thread group
     * @since VCafe 3.0
     */

    public void checkMulticast(InetAddress maddr) {
    }

// checkMulticast is depricated
//    /**
//     * @param maddr non-null thread group
//     * @param ttl non-null thread group
//     * @since VCafe 3.0
//     */
//
//    public void checkMulticast(InetAddress maddr, byte ttl) {
//    }

    /**
     * @since VCafe 3.0
     */

    public void checkPropertiesAccess() {
    }

    /**
     * @param key non-null thread group
     * @since VCafe 3.0
     */

    public void checkPropertyAccess(String key) {
    }

    /**
     * @param key non-null thread group
     * @param def non-null thread group
     * @since VCafe 3.0
     */

    public void checkPropertyAccess(String key, String def) {
    }

    /**
     * @param window non-null thread group
     * @since VCafe 3.0
     */

    public boolean checkTopLevelWindow(Object window) {
        return true;
    }

    /**
     * @since VCafe 3.0
     */

    public void checkPrintJobAccess() {
    }

    /**
     * @since VCafe 3.0
     */

    public void checkSystemClipboardAccess() {
    }

    /**
     * @since VCafe 3.0
     */

    public void checkAwtEventQueueAccess() {
    }

    /**
     * @param pkg non-null thread group
     * @since VCafe 3.0
     */

    public void checkPackageAccess(String pkg) {
    }

    /**
     * @param pkg non-null thread group
     * @since VCafe 3.0
     */

    public void checkPackageDefinition(String pkg) {
    }

    /**
     * @since VCafe 3.0
     */

    public void checkSetFactory() {
    }

    /**
     * @param clazz non-null thread group
     * @param which non-null thread group
     * @since VCafe 3.0
     */

    public void checkMemberAccess(Class clazz, int which) {
    }

    /**
     * @param provider non-null thread group
     * @since VCafe 3.0
     */

    public void checkSecurityAccess(String provider) {
    }
}
