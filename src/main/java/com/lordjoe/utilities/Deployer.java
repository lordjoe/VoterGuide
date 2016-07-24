package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

/**
 * com.lordjoe.utilities.Deployer
 *
 * @author Steve Lewis
 * @date Apr 3, 2007
 */
public class Deployer
{
    public static Deployer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = Deployer.class;

    public static final String WINDOWS_DIRECTORY_SEPARATOR = "\\";
    public static final String LINUX_DIRECTORY_SEPARATOR = "/";
    public static final String WINDOWS_PATH_SEPARATOR = ";";
    public static final String LINUX_PATH_SEPARATOR = ":";

    public static final String[] EEXCLUDED_JARS_LIST = {"idea_rt.jar"};
    public static final Set<String> EXCLUDED_JARS = new HashSet(Arrays.asList(EEXCLUDED_JARS_LIST));

    private int gJarNumber = 0;
    private static boolean isQuiet;

    public static File[] filterClassPath(String[] pathItems, String javaHome, File libDir)
    {
        List holder = new ArrayList();
        for (int i = 0; i < pathItems.length; i++) {
            String item = pathItems[i];
            if (".".equals(item))
                continue;
            if (EXCLUDED_JARS.contains(item))
                continue;
            if (item.indexOf(javaHome) > -1)
                continue;
            File itemFile = new File(item);
            if (!itemFile.exists())
                continue;
            if (itemFile.isFile()) {
                continue;
            }
            if (itemFile.isDirectory()) {
                holder.add(makeJar(libDir, itemFile));
            }

        }

        for (int i = 0; i < pathItems.length; i++) {
            String item = pathItems[i];
            if (".".equals(item))
                continue;
            if (inExcludedJars(item))
                continue;
            if (item.indexOf(javaHome) > -1)
                continue;
            File itemFile = new File(item);
            if (!itemFile.exists())
                continue;
            if (itemFile.isFile()) {
                holder.add(itemFile);
                continue;
            }
            if (itemFile.isDirectory()) {
                continue;
            }

        }
        File[] ret = new File[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected static boolean inExcludedJars(String s)
    {
        for (int i = 0; i < EEXCLUDED_JARS_LIST.length; i++) {
            String test = EEXCLUDED_JARS_LIST[i];
            if(s.endsWith(test))
                return true;
        }
        return false;
    }

    public static String pathToJarName(File itemFile)
    {
        String test = itemFile.getName();
        if ("classes".equalsIgnoreCase(test)) {
            test = itemFile.getParentFile().getName();
        }
        return test + ".jar";
    }

    public static File makeJar(File libDir, File itemFile)
    {
        String jarName = pathToJarName(itemFile);
        File jarFile = new File(libDir, jarName);
        String cmd = "jar -cvf " + jarFile.getAbsolutePath() + " -C " + itemFile.getAbsolutePath() + " .";
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jarFile;
    }

    public static void copyLibraries(File libDir, File[] libs)
    {
        for (int i = 0; i < libs.length; i++) {
            File lib = libs[i];
            if (libDir.equals(lib.getParentFile()))
                continue;
            File dst = new File(libDir, lib.getName());
            copyFile(lib, dst);
            System.out.println("copying lib to " + dst.getName());
        }
    }

    /**
     * { method
     *
     * @param dst destination file name
     * @param src source file name
     * @return true for success
     *         }
     * @name copyFile
     * @function copy file named src into new file named dst
     */
    public static boolean copyFile(File src, File dst)
    {
        int bufsize = 1024;
        try {
            RandomAccessFile srcFile = new RandomAccessFile(src, "r");
            long len = srcFile.length();
            if (len > 0x7fffffff) {
                return (false);
            }
            // too large
            int l = (int) len;
            if (l == 0) {
                return (false);
            }
            // failure - no data
            RandomAccessFile dstFile = new RandomAccessFile(dst, "rw");

            int bytesRead = 0;
            byte[] buffer = new byte[bufsize];
            while ((bytesRead = srcFile.read(buffer, 0, bufsize)) != -1) {
                dstFile.write(buffer, 0, bytesRead);
            }
            srcFile.close();
            dstFile.close();
            return true;
        }
        catch (IOException ex) {
            return (false);
        }
    }


    /**
     * { method
     *
     * @param FileName name of file to create
     * @param data     date to write
     * @return true = success
     *         }
     * @name writeFile
     * @function write the string data to the file Filename
     */
    public static boolean writeFile(File TheFile, String data)
    {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(TheFile));
            if (out != null) {
                out.print(data);
                out.close();
                return (true);
            }
            return (false);
            // failure
        }
        catch (IOException ex) {
            return (false); // browser disallows
        }
        catch (SecurityException ex) {
            return (false); // browser disallows
        }
    }


    public static File[] deployLibraries(File deployDir)
    {
        String javaHome = System.getProperty("java.home");
        String classpath = System.getProperty("java.class.path");
        String[] pathItems = null;
        if (classpath.contains(";")) {
            pathItems = classpath.split(";");
        }
        else {
            if (classpath.contains(":")) {
                pathItems = classpath.split(":");   // Linux stlye
            }
            else {
               String[] items = {classpath};
                pathItems = items; // only 1 I guess
            }
        }
        File libDir = new File(deployDir, "lib");
        File[] pathLibs = filterClassPath(pathItems, javaHome, libDir);
        libDir.mkdirs();
        copyLibraries(libDir, pathLibs);
        return pathLibs;
    }

    public static String buildBatchFile(File[] pathLibs, File deployDir, String[] args)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("ECHO OFF\n");
        sb.append("set JAR_PATH=lib\n");
        sb.append("set q4path=%JAR_PATH%/" + pathLibs[0].getName() + "\n");
        for (int i = 1; i < pathLibs.length; i++) {
            File athLib = pathLibs[i];
            sb.append("set q4path=%q4path%" + WINDOWS_PATH_SEPARATOR + "%JAR_PATH%/" + athLib.getName() + "\n");
        }
        sb.append("ECHO ON\n");
        if (isQuiet) {
        	sb.append("jre" + WINDOWS_DIRECTORY_SEPARATOR + "bin" + WINDOWS_DIRECTORY_SEPARATOR + "javaw ");
        } else {
        	sb.append("jre\\bin\\java ");
        }
        sb.append(" -Xmx400m -Xms64m -cp %q4path% " + args[1] + " ");
        for (int i = 2; i < args.length; i++) {
            sb.append(" " + args[i]);
        }
        sb.append(" %1 %2 %3 %4 \n");
        return sb.toString();

    }

    public static String buildShellFile(File[] pathLibs, File deployDir, String[] args)
    {

        StringBuffer sb = new StringBuffer();
        sb.append("JAR_PATH=lib\n");
        sb.append("q4path=$JAR_PATH/" + pathLibs[0].getName() + "\n");
        for (int i = 1; i < pathLibs.length; i++) {
            File athLib = pathLibs[i];
            sb.append("q4path=$q4path" + LINUX_PATH_SEPARATOR + "$JAR_PATH/" + athLib.getName() + "\n");
        }
        sb.append("jre/bin/java -cp $q4path " + args[1] + " ");
        for (int i = 2; i < args.length; i++) {
            sb.append(" " + args[i].replace('%', '$'));
        }
        sb.append("\n");
        return sb.toString();

    }

    public static void makeRunners(File[] pathLibs, File deployDir, String[] args)
    {
        File binDir = new File(deployDir, "bin");
        binDir.mkdirs();
        String bf = buildBatchFile(pathLibs, deployDir, args);
        File rb = new File(binDir, "run.bat");
        writeFile(rb, bf);
        String sf = buildShellFile(pathLibs, deployDir, args);
        File rs = new File(binDir, "run.sh");
        writeFile(rs, sf);
    }



    public static void main(String[] args)
    {
      //   testRegex();
    	String[] rightArgs;
    	if ( "-q".equals( args[0])) {
    		isQuiet = true;
    		rightArgs = new String[args.length-1];
    		System.arraycopy(args, 1, rightArgs, 0, args.length-1);
    	} else {
    		rightArgs = args;
    	}
        File deployDir = new File(args[0]);
        deployDir.mkdirs();
        File[] pathLibs = deployLibraries(deployDir);
        makeRunners(pathLibs, deployDir, rightArgs);

    }
}