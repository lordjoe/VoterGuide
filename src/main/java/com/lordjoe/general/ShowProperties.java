package com.lordjoe.general;

import com.lordjoe.utilities.*;

import java.util.*;
import java.io.*;


/**
 * com.lordjoe.general.ShowProperties
 *
 * @author slewis
 * @date Mar 17, 2005
 */
public class ShowProperties
{
    public static final Class THIS_CLASS = ShowProperties.class;
    public static final ShowProperties EMPTY_ARRAY[] = {};

    public static final String PROP_FILE_NAME = "station.properties";
    public static final String OUT_FILE_NAME = "station.properties.txt";

    public static String compareProperties(Properties p1,Properties p2)
    {
        StringBuffer sb = new StringBuffer();

        Set p1OnlyKeys = keysNotPresent(p1,p2);
        Set p2OnlyKeys = keysNotPresent(p2,p1);
        Set commonKeys = commonKeys(p2,p1);
         if(!p1OnlyKeys.isEmpty()) {
             sb.append("Keys in first properties only\n");
             reportKeysNotPresent(sb,p1OnlyKeys,p1);
         }
        if(!p2OnlyKeys.isEmpty()) {
             sb.append("Keys in second properties only\n");
             reportKeysNotPresent(sb,p2OnlyKeys,p2);
         }
        if(commonKeys.isEmpty())  {
            sb.append("No differences in common keys\n");
        }
        else {
            sb.append("Differences in common keys\n");
            String[] items = Util.collectionToStringArray(commonKeys);
            Arrays.sort(items);
             for (int i = 0; i < items.length; i++) {
                String item = items[i];
                String prop1 = p1.getProperty(item);
                String prop2 = p2.getProperty(item);
                sb.append("key " + item + " value 1=" + p1 +
                     " value 2=" + p2 +
                  "\n");
            }
        }

        return sb.toString();

   }

    protected static void reportKeysNotPresent( StringBuffer sb,Set keys,Properties p)
    {
        String[] items = Util.collectionToStringArray(keys);
        Arrays.sort(items);
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            String property = p.getProperty(item);
            sb.append(item + "=" + property + "\n");
        }
    }

    public static boolean isValueCommon(String key,Map... props)
        {
            if(props.length == 0)
                throw new IllegalArgumentException("no Map passed");

            Object firstVal = props[0].get(key);
            for (int i = 1; i < props.length; i++) {
                Map rop = props[i];
                Object val =  rop.get(key);
                if(val == null || !val.equals(firstVal))
                    return false; // differences exist
            }
            return true; // all values the same
        }


    public static Set commonKeys(Map m1,Map m2)
     {
         Set ret = new HashSet(m1.keySet());
         ret.retainAll(m2.keySet());
         return ret;
     }

    public static Set commonKeys(Map... m1)
     {
         Set ret = new HashSet(m1[0].keySet());
         for (int i = 1; i < m1.length; i++) {
             Map map = m1[i];
             ret.retainAll(map.keySet());
         }
         return ret;
     }



    public static Set keysNotPresent(Map m1,Map m2)
     {
         Set ret = new HashSet(m1.keySet());
         ret.removeAll(m2.keySet());
         return ret;
     }

    public static Properties loadProperties(String name)
     {
         try
         {
             InputStream is = new FileInputStream(name) ;
             Properties ret = new Properties();
             ret.load(is);
             return ret;
         }
         catch (IOException e)
         {
             throw new RuntimeException(e);  //ToDo Add better handling
         }
     }
    public static void dumpProperties(Properties props,String name)
     {
         try
         {
             PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name))) ;
             Set keys = props.keySet();
             String[] keyStrs = new String[keys.size()];
             keys.toArray(keyStrs);
             Arrays.sort(keyStrs);
             for (int i = 0; i < keyStrs.length; i++)
             {
                 String keyStr = keyStrs[i];
                 String value = props.getProperty(keyStr);
                 out.print(keyStr);
                 out.print("=");
                 out.print(value);
                 out.print("\n");
             }
             out.flush();
             out.close();

         }
         catch (IOException e)
         {
             throw new RuntimeException(e);  //ToDo Add better handling
         }
     }
      public static void main(String[] args)
    {
         Properties props = loadProperties(PROP_FILE_NAME);
         dumpProperties(props,OUT_FILE_NAME);
    }

}
