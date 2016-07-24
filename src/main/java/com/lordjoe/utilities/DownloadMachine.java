package com.lordjoe.utilities;

import java.net.*;
import java.io.IOException;
import java.io.File;

/**
 * com.lordjoe.utilities.DownloadMachine
 *
 * @author Steve Lewis
 * @date Apr 12, 2004
 */
public class DownloadMachine
{
    public static Class THIS_CLASS = DownloadMachine.class;
    public static final DownloadMachine[] EMPTY_ARRAY = {};

  //  public static final String FUNNY_VIDEOS = "http://www.viralx.com/file.php?id=$1";
    public static final String FUNNY_VIDEOS = "http://www.funnyplace.org/videos/$1.zip";

    public static void usage()
    {
        System.out.println("Usage");
        System.out.println(
                "java com.lordjoe.utilities.DownloadMachine urlBase extension start <end>");
        System.out.println(
                "java com.lordjoe.utilities.DownloadMachine http://www.hinet.hr/ahorv149/clips/ .zip 500 1000");
        System.out.println(
                "java com.lordjoe.utilities.DownloadMachine http://france1.eol.fr/adeater/DATA/mpeg/ .mpg 500");

    }

    public static final String TEMP_FILE = "c:/temp/temp";
    public static final String TARGET_DIR = "K:\\Movies\\Commercials\\Viralx";

    public static String buildURL(String template, int number)
    {
        String n = Integer.toString(number);
        return template.replace("$1", n);
    }

    private static void getZipURL(String pUrl,int i) //, File pBaseDir, String pFileName)
    {
        String s = TEMP_FILE + i + ".zip";
        try {
            URL theUrl = new URL(pUrl);
            FileUtilities.downloadBinary(theUrl, new File(s));
            ZipUtilities.unzip(s, TARGET_DIR);
            System.out.println("Good Download " + s);
        }
        catch (MalformedURLException ex) {
            System.out.println("Bad Download " + s);
        }
        catch (IOException ex2) {
            System.out.println("Bad Download " + s);
            // FileUtilities.writeFile(new File(pBaseDir,pFileName + "_bad"),"");
        }
    }

    private static void getURL(String pUrl, File pBaseDir, String pFileName)
    {
        try {
            URL theUrl = new URL(pUrl);
            FileUtilities.downloadBinary(theUrl, new File(TEMP_FILE));
            ZipUtilities.unzip(TEMP_FILE, TARGET_DIR);
            System.out.println("Good Download " + TEMP_FILE);
        }
        catch (MalformedURLException ex) {
            System.out.println("Bad Download " + TEMP_FILE);
        }
        catch (IOException ex2) {
            System.out.println("Bad Download " + TEMP_FILE);
            // FileUtilities.writeFile(new File(pBaseDir,pFileName + "_bad"),"");
        }
    }

    private static void main1(String[] args)
    {
        if (args.length < 3) {
            usage();
            return;
        }
        String added = "";
        String base = args[0];
        String ext = args[1];
        int start = Integer.parseInt(args[2]);
        int end = start + 200;
        if (args.length > 3)
            end = Integer.parseInt(args[3]);

        if (args.length > 4)
            added = args[4];

        File baseDir = new File(System.getProperty("user.dir"));
        for (int i = start; i < end; i++) {
            String fileName = Integer.toString(i) + ext;
            String url = buildURL(base,i);
//            if (added.length() == 0)
//                url = Base + fileName;
//            else
//                url = Base + Integer.toString(i) + added;
            getURL(url, baseDir, fileName);

        }
    }


    public static void main(String[] args)
    {
        if(args.length < 3) {
            usage();
            return;
        }
        String baseUrl = args[0];

        int start = Integer.parseInt(args[1]);
        int end  = Integer.parseInt(args[2]);
        for (int i = start; i <= end; i++) {

            String url = buildURL(baseUrl, i);
            getZipURL(url,i);

        }
        System.out.println("Done");
   }


}
