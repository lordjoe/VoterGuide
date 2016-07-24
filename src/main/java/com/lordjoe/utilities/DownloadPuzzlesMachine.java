package com.lordjoe.utilities;



//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.auth.*;
///**
// * com.lordjoe.utilities.DownloadPuzzlesMachine
// *
// * @author Steve Lewis
// * @date Apr 12, 2004
// */
//public class DownloadPuzzlesMachine
//{
//    public static Class THIS_CLASS = DownloadPuzzlesMachine.class;
//    public static final DownloadPuzzlesMachine[] EMPTY_ARRAY = {};
//
//    public static final String NY_TIMES = "http://select.nytimes.com/premium/xword/";
//    public static final Calendar START = Calendar.getInstance();
//
//    public static void usage()
//    {
//        System.out.println("Usage");
//        System.out.println(
//                "java com.lordjoe.utilities.DownloadMachine urlBase extension start <end>");
//        System.out.println(
//                "java com.lordjoe.utilities.DownloadMachine http://www.hinet.hr/ahorv149/clips/ .zip 500 1000");
//        System.out.println(
//                "java com.lordjoe.utilities.DownloadMachine http://france1.eol.fr/adeater/DATA/mpeg/ .mpg 500");
//
//    }
//
//
//    public static final String TEMP_FILE = "c:/temp/temp";
//    public static final String DOWNLOAD_DIR = "C:/Software";
//
//    public static String buildURL(String template, int number)
//    {
//        String n = Integer.toString(number);
//        return template.replace("$1", n);
//    }
//
//
//    public static String buildURL(Calendar c)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(NY_TIMES);
//        sb.append(buildFileName(c));
//
//        return sb.toString();
//    }
//
//    protected static String buildFileName(Calendar c)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(FarTime.ShortMonth[c.get(Calendar.MONTH)]);
//        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
//        if (day.length() == 1)
//            day = "0" + day;
//        if (day.length() == 1)
//            day = "0" + day;
//
//        sb.append(day);
//        String year = Integer.toString(c.get(Calendar.YEAR) % 100);
//        if (year.length() == 1)
//            year = "0" + year;
//        sb.append(year);
//        sb.append(".puz");
//
//        return sb.toString();
//    }
//
//
//    protected static void downloadPuzzle(Calendar pC, File pBaseDir)
//            throws IOException
//    {
//        HttpClient client = new HttpClient();
//        // To be avoided unless in debug mode
//        Credentials defaultcreds = new UsernamePasswordCredentials("smlewis@lordjoe.com", "verna");
//        client.getState().setCredentials(AuthScope.ANY, defaultcreds);
//
//        String url = buildURL(pC);
//        String fileName = buildFileName(pC);
//        File outFile = new File(DOWNLOAD_DIR + "/" + fileName);
//        if (outFile.exists())
//            return;
//        String command = "\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\" " + url;
//        System.out.println(command);
//        Process p = Runtime.getRuntime().exec(command);
//        try {
//            Thread.sleep(30000);
//        }
//        catch (InterruptedException e) {
//        }
//        p.destroy();
////        System.out.println(url);
////
////        String fileName = buildFileName(pC);
////        int day = pC.get(Calendar.DAY_OF_WEEK);
////        String dayStr = FarTime.TheWeek[day - 1];
////        File dailyDir = new File(pBaseDir, dayStr);
////        dailyDir.mkdirs();
////        URL theUrl = new URL(url);
////        HttpMethod met = new GetMethod(url);
////        int status = client.executeMethod(met);
////        InputStream in =
////                 met.getResponseBodyAsStream();
////        File outFile = new File(dailyDir, fileName);
////        FileOutputStream fo = new FileOutputStream(outFile);
////        FileUtilities.copyStream(in,fo);
////  //      FileUtilities.downloadBinary(theUrl, new File(dailyDir,fileName));
//    }
//
//
//    protected static void copyPuzzle(Calendar pC, File pBaseDir)
//    {
//        String fileName = buildFileName(pC);
//        File inFile = new File(DOWNLOAD_DIR + "/" + fileName);
//        if (!inFile.exists())
//            return;
//
//        int day = pC.get(Calendar.DAY_OF_WEEK);
//        String dayStr = FarTime.TheWeek[day - 1];
//        File dailyDir = new File(pBaseDir, dayStr);
//        dailyDir.mkdirs();
//        File outFile = new File(dailyDir, fileName);
//        if (!outFile.exists()) {
//            FileUtilities.copyFile(inFile, outFile);
//            System.out.println(outFile);
//        }
//    }
//
//
//    public static void copyPuzzles()
//    {
//        Calendar c = Calendar.getInstance();
//        c.set(2007, 6, 20);
//        Calendar end = Calendar.getInstance();
//        end.set(1997, 1, 1);
//        Calendar start = Calendar.getInstance();
//        start.set(2007, 6, 20);
//        File baseDir = new File("/Puzzles");
//        baseDir.mkdirs();
//
//        while (c.after(end)) {
//            copyPuzzle(c, baseDir);
//            c.add(Calendar.DATE, -1);
//        }
//        System.out.println("Done");
//    }
//
//    private static void downloadPuzzles()
//            throws IOException
//    {
//        Calendar c = Calendar.getInstance();
//        c.set(2007, 4, 20);
//        Calendar end = Calendar.getInstance();
//        end.set(1997, 1, 1);
//        Calendar start = Calendar.getInstance();
//        start.set(2007, 6, 20);
//        File baseDir = new File("/Puzzles");
//        baseDir.mkdirs();
//
//        while (c.after(end)) {
//           downloadPuzzle(c, baseDir);
//            c.add(Calendar.DATE, -1);
//        }
//    }
//
//    public static void main(String[] args) throws MalformedURLException, IOException
//    {
//        copyPuzzles();
//      // downloadPuzzles();
//        System.out.println("Done");
//    }
//
//
//}
