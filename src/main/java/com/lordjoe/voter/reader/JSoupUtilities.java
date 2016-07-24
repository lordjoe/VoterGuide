package com.lordjoe.voter.reader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.lordjoe.voter.reader.JSoupUtilities
 * User: Steve
 * Date: 6/24/2016
 */
public class JSoupUtilities {

    public static Map<String, String> parsePropertyTable(Element table) {
        Map<String, String> ret = new HashMap<String, String>();
        Elements tr = table.select("tr");
        for (Element element : tr) {
            addProperty(ret, element);
        }
        return ret;
    }

    private static void addProperty(Map<String, String> ret, Element element) {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }


    public static String mergeLines(List<String> lines)  {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line) ;
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Element getOneSubItemOfClass(Element parent, String className)   {
        return getOneSubItemOfClass(parent,"div",className);

    }

    public static Element getOneSubItemOfClass(Element parent,String tag, String className)   {
        Elements select = parent.select(tag + "[class*=" + className + "]");
        if(select.size() != 1)
            throw new IllegalStateException("not one item of class " + className);
        return  select.first();

    }

    public static InputStream fromStringList(List<String> lines)  {
          return   fromString(mergeLines( lines) );
    }


    public static List<String> readInFile(File f) {
        List<String> ret = new ArrayList<String>();
        LineNumberReader rdr = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line;
             rdr = new LineNumberReader(new FileReader(f));
            line = rdr.readLine();
            while (line != null) {
                ret.add(line);
                   line = rdr.readLine();
            }

            return ret;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        finally {
            if(rdr != null)  {
                try {
                    rdr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
    }

    public static List<String> readInFile(InputStream f) {
        List<String> ret = new ArrayList<String>();
        LineNumberReader rdr = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            rdr = new LineNumberReader(new InputStreamReader(f));
            line = rdr.readLine();
            while (line != null) {
                ret.add(line);
                line = rdr.readLine();
            }

            return ret;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        finally {
            if(rdr != null)  {
                try {
                    rdr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
    }


    /**
     * VoteSmart loves to comment out data
     * @param in
     * @return
     */
    public static String removeHTMLComments(String in) {
        return in.replace("//-->","-->").replace("</div><!--","</div>").replace("</div>-->","</div>") ;

    }

    public static InputStream fromString(String in)   {
        return new ByteArrayInputStream(in.getBytes());
    }

    public static void writeFile(String name,String text)   {
        FileWriter fw = null;
        try {
            fw = new FileWriter(name);
            fw.write(text );
            fw.close();
            fw = null;
        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            if(fw !=null)
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
        }
    }


}
