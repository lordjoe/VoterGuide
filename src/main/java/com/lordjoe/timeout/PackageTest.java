package com.lordjoe.timeout;
//
//import org.apache.log4j.*;
//import junit.framework.*;
//
//import java.util.*;

///**
// ***************************************************************************************************
// *                                                                                              <p/>
// * <!-- PackageTest -->
// *                                                                                              <p>
// * This is the test class for this package.
// * TODO Add some tests
// *                                                                                              <p>
// ***************************************************************************************************
// */
//public class PackageTest
//{
//
//
//    /**
//     ***********************************************************************************************
//     *                                                                                          <p>
//     * Note:  re keeping test class lists up-to-date
//     *                                                                                          <p>
//     * Periodically, it is necessary to confirm that no unit tests have
//     * been overlooked in the lists in the various PackageTest.suite()
//     * methods in each package.  Unfortunately, it is the developer's
//     * responsibility to ensure that no tests have been accidentally
//     * omitted.  It can be done using the following procedure:
//     *                                                                                          <p>
//     * 1.  Find PackageTest.java in main.
//     * 2.  Open all of these.
//     * 3.  For each PackageTest.java file,
//     * a) search for *Test.java in the folder containing the
//     * package's files
//     * b) make sure each *Test.java file found on the hard drive
//     * is listed in the PackageTest.java file.
//     *                                                                                          <p>
//     * Com.cbmx.math.PackageTest is the reference copy for this
//     * comment that is duplicated in all PackageTest.java files.  Changes
//     * should be made there and then copied.
//     *                                                                                          <p>
//     ***********************************************************************************************
//     ***********************************************************************************************
//     *                                                                                          <p>
//     * <!-- suiteExternal() -->
//     *                                                                                          <p>
//     * This method retrieves a TestSuite containing all of the test classes
//     * in this package.  It is a standard method recommended by the JUnit
//     * framework.
//     *
//     * @return the tests suite for this package that contains all unit tests
//     *         in the package
//     *                                                                                          <p>
//     ***********************************************************************************************
//     */
//
//    /***********************************************************************************************
//     *
//     *  <!-- suiteExternal() -->
//     *
//     *  This method retrieves a TestSuite containing all of the test classes
//     *  in this package.  It is a standard method recommended by the JUnit
//     *  framework.
//     *
//     *  @return
//     *      the tests suite for this package that contains all unit tests
//     *      in the package
//     *
//     ***********************************************************************************************
//     */
//    public static Test suiteExternal()
//    {
//        CbmxTestSuite suite = new CbmxTestSuite();
//
//        suite.addCbmxTestSuite(new TestSuite(TaskTimerTest.class));
//        return suite;
//    }
//
//
//    /**
//     ***********************************************************************************************
//     *                                                                                          <p>
//     * <!-- suiteInternal() -->
//     *                                                                                          <p>
//     * This method retrieves a TestSuite containing additional tests
//     * that can only be run by developers with access to the test files
//     * in "main\testfiles\...".
//     *                                                                                          <p>
//     ***********************************************************************************************
//     */
//    public static Test suiteInternal()
//    {
//        TestSuite suite = (TestSuite) com.cbmx.timeout.PackageTest.suiteExternal();
//
//        return suite;
//    }
//
//
//    /**
//     ***********************************************************************************************
//     *                                                                                          <p>
//     * <!-- suite() -->
//     *                                                                                          <p>
//     * This method retrieves a TestSuite containing all tests.
//     * It can only be run by developers with access to the test files
//     * in "main\testfiles\...".
//     *                                                                                          <p>
//     ***********************************************************************************************
//     */
//    public static Test suite()
//    {
//        TestSuite suite = (TestSuite) com.cbmx.timeout.PackageTest.suiteInternal();
//
//        return suite;
//    }
//
//
//    /**
//     ***********************************************************************************************
//     *                                                                                          <p>
//     * <!-- main() -->
//     *                                                                                          <p>
//     * This method allows the package tests to be invoked standalone (i.e., inside JBuilder).
//     * This method is the same in all PackageTest classes.
//     *                                                                                          <p>
//     ***********************************************************************************************
//     */
//    public static void main(String[] args)
//    {
//        CbmxTestRunner.runLoggedTestSuite(com.cbmx.timeout.PackageTest.class, com.cbmx.timeout.PackageTest.suite());
//    }
//
//} // end class PackageTest
