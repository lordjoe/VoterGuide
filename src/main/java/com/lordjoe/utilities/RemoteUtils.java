package com.lordjoe.utilities;

import java.rmi.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * A collection of utility functions for
 * testing the suitability of classes for serialization and
 * remoting
 * @author Steve Lewis
 */
public abstract class RemoteUtils {
    /**
     * classes we accept as being acceptble since there is
     * no good way to test
     */
    public static final Class[] KNOWN_REMOTABLE_CLASSES = {
        java.lang.String.class, java.lang.Object.class,
        java.util.Enumeration.class, java.util.Iterator.class,
        java.util.Collection.class
    };

    /**
     * allow the user to add other class he knows are OK to remote
     */
    protected static List gRegisteredKnownRemotable;

    /**
     * allow the user to add other class he knows are OK to remote
     * @param non-null CLass know to be OK ot remote
     */
    public static synchronized void addKnownRemotable(Class TheClass) {
        if (gRegisteredKnownRemotable == null) {
            gRegisteredKnownRemotable = new ArrayList();
        }
        gRegisteredKnownRemotable.add(TheClass);
    }

    /**
     * Method true if we assume the class is remotable
     * these are classes such as Object and Collection
     * which must be assumed correct for certain classes to
     * be remotable
     * @param non-null TestClass
     * @return
     */
    protected static synchronized boolean isKnownRemotable(Class TestClass) {
        for (int i = 0; i < KNOWN_REMOTABLE_CLASSES.length; i++) {
            if (TestClass == KNOWN_REMOTABLE_CLASSES[i]) {
                return (true);
            }
        }
        // Test against registered good classes
        if (gRegisteredKnownRemotable != null) {
            Iterator it = gRegisteredKnownRemotable.iterator();
            while (it.hasNext()) {
                if (TestClass == it.next()) {
                    return (true);
                }
            }
        }
        return (false);
    }


    /**
     * returns non-null if the Class is not a valid remoteinterface - returned String contains
     * an explanation of the error
     * @param TestClass class to test
     * @return as above
     */
    public static String isValidRemoteInterface(Class TestClass) {

        StringBuffer sb = new StringBuffer();
        String problem = null;
        if (!TestClass.isInterface())
            sb.append(TestClass.getName() + " is not an Interface");
        if (!Remote.class.isAssignableFrom(TestClass))
            sb.append(TestClass.getName() + " is not a Remote Interface");

        Method[] TheMethods = TestClass.getMethods();

        for (int i = 0; i < TheMethods.length; i++) {
            problem = isMethodValidRemote(TheMethods[i]);
            if (problem != null) {
                sb.append(problem);
            }
        }

        if (sb.length() == 0) {
            return (null);	  // all OK
        }

        return (sb.toString());
    }

    /**
     * returns non-null if the Class is not a valid EJB interface - returned String contains
     * an explanation of the error
     * @param TestMethod non-null method to test
     * @return as above
     */
    protected static String isMethodValidRemote(Method TestMethod) {
        StringBuffer sb = new StringBuffer();

        // Make sure return is remotable
        Class ReturnClass = TestMethod.getReturnType();

        if (!isRemotable(ReturnClass)) {
            isRemotable(ReturnClass);	 // repead for debug
            sb.append("Return Class " + ReturnClass.getName() + " of Method "
                    + TestMethod.getName() + " in class "
                    + TestMethod.getDeclaringClass().getName()
                    + " is Not Remotable \n");
        }

        // Make sure all paramters are remotable
        Class[] TheClasses = TestMethod.getParameterTypes();

        for (int i = 0; i < TheClasses.length; i++) {
            if (!isRemotable(TheClasses[i])) {
                isRemotable(TheClasses[i]);	   // repead for debug
                sb.append("Parameter " + i + " of class "
                        + ReturnClass.getName() + " of Method "
                        + TestMethod.getName() + " in class "
                        + TestMethod.getDeclaringClass().getName()
                        + " is Not Remotable \n");
            }
        }

        // Make sure a remote exception is thrown
        Class[] TheExceptions = TestMethod.getExceptionTypes();
        boolean throwsRemoteException = false;

        for (int i = 0; i < TheExceptions.length; i++) {
            if (java.rmi.RemoteException.class.isAssignableFrom(TheExceptions[i])) {
                throwsRemoteException = true;

                break;
            }
        }

        if (!throwsRemoteException) {
            sb.append("Method " + TestMethod.getName() + " in class "
                    + TestMethod.getDeclaringClass().getName()
                    + " is Not Remotable because it does not throw RemoteException \n");
        }

        if (sb.length() == 0) {
            return (null);	  // all OK
        }

        return (sb.toString());
    }

    /**
     * returns true if a class is Serializable - tests all fields recursively
     * @param non-null TestClass class to test
     * @return as above
     */
    public static boolean isRemotable(Class TestClass) {
        // Test against known good classes
        if (isKnownRemotable(TestClass)) {
            return (true);
        }
        if (isSerializable(TestClass)) {
            return (true);
        }
        if (TestClass.isArray()) {
            return (isRemotable(TestClass.getComponentType()));
        }
        if (java.rmi.Remote.class.isAssignableFrom(TestClass)) {
            Class[] interfaces = TestClass.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (Remote.class.isAssignableFrom(interfaces[i])) {
                    if (isValidRemoteInterface(interfaces[i]) != null)
                        return (false); // bad remote interface
                }
            }
            return (fieldsAreRemotable(TestClass));
        }
        return (false);	   // guess not
    }

    /**
     * returns true if all fields in the class are Remotable
     * @param non-null TestClass class to test
     * @return as above
     */
    protected static boolean fieldsAreRemotable(Class TestClass) {
        Field[] fields = TestClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (!fieldIsRemotable(fields[i])) {
                return (false);
            }
        }

        return (true);
    }

    /**
     * returns true if all fields in the class are Remotable
     * @param non-null TestClass class to test
     * @return as above
     */
    protected static boolean fieldIsRemotable(Field TestField) {

        // transient does not count
        if (Modifier.isTransient(TestField.getModifiers())) {
            return (true);
        }

        return (isRemotable(TestField.getType()));
    }

    /**
     * returns true if a class is Serializable - tests all fields recursively
     * @param non-null TestClass class to test
     * @return as above
     */
    public static boolean isSerializable(Class TestClass) {
        if (TestClass.isPrimitive()) {
            return (true);
        }

        if (TestClass.isArray()) {
            return (isSerializable(TestClass.getComponentType()));
        }

        if (java.io.Serializable.class.isAssignableFrom(TestClass)) {
            return (fieldsAreSerializable(TestClass));
        }

        if (isKnownRemotable(TestClass)) {
            return (true);
        }

        String ClassName = TestClass.getName();

        ClassName = null;

        return (false);
    }

    /**
     * returns true if all fields in the class are serializable
     * @param non-null TestClass class to test
     * @return as above
     */
    protected static boolean fieldsAreSerializable(Class TestClass) {
        Field[] fields = TestClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (!fieldIsSerializable(fields[i])) {
                fieldIsSerializable(fields[i]);	   // repeat for debug
                return (false); // fail
            }
        }
        return (true);
    }

    /**
     * returns true if all fields in the class are serializable
     * @param non-null TestClass class to test
     * @return as above
     */
    protected static boolean fieldIsSerializable(Field TestField) {

        // transient does not count
        if (Modifier.isTransient(TestField.getModifiers())) {
            return (true);

        }
        // static fields to not count
        if (Modifier.isStatic(TestField.getModifiers())) {
            return (true);
        }
        return (isSerializable(TestField.getType()));
    }

}

