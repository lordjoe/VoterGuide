package com.lordjoe.utilities;

import java.util.*;


/*
 * com.lordjoe.Utilities.CompareCSV
 * @author smlewis
 * Date: Mar 12, 2003
 */

public class CompareCSV 
{
    public static final Class THIS_CLASS = CompareCSV.class;
    public static final CompareCSV[] EMPTY_ARRAY = {};

    public static final int START_TIME_INDEX = 4;
    public static final int NUMBER_INDEX = 0;
    public static final int CHARGE_INDEX = 5;
    public static final int USAGE_TYPE_INDEX = 9;
    public static final int CALL_TYPE_INDEX = 10;
    public static final int PROGRAM_ID_INDEX = 14;
    public static final int ORIG_PLACE_INDEX = 20;
    public static final int TERM_PLACE_INDEX = 27;
    public static final int TERM_STATE_INDEX = 28;
    public static final int TERM_LATA_INDEX = 30;
    public static final int RECORD_NUMBER_INDEX = 33;
    public static final int TXT_NET_ID_INDEX = 34;

     public static final int CALL_TYPE_WIDTH = 54;
    public static final int ORIG_PLACE_WIDTH = 27;
    public static final int TERM_LATA_WIDTH = 28;
    public static final String RECORD_NUMBER_VALUE = "6939";
    public static final String TXT_NET_ID_VALUE = "04169";
    public static final String USAGE_TYPE_VALUE = "31";



    protected static String buildLineIndex(String[] items)
    {
        Date start = Util.parseDate(items[START_TIME_INDEX],Util.US_DATE_TIME);
        return(items[NUMBER_INDEX] + ":" + start.toString() + ":" + items[TERM_PLACE_INDEX] + ","+ items[TERM_STATE_INDEX] +
                ":" + items[PROGRAM_ID_INDEX]);
    }

    private String[] m_Captions;
    private String[] m_lines1;
    private String[] m_lines2;
    private Map m_IndexedLines1 = new HashMap();
    private Map m_IndexedLines2 = new HashMap();

    public CompareCSV(String[] lines1,String[] lines2)
    {
        m_Captions = Util.parseCommaDelimited(lines1[0]);
        m_lines1 = new String[lines1.length - 1];
        System.arraycopy(lines1,1,m_lines1,0,lines1.length - 1);
        m_lines2 = new String[lines2.length - 1];
        System.arraycopy(lines2,1,m_lines2,0,lines2.length - 1);
    }

    public String compare()
    {
        StringBuffer sb = new StringBuffer();
        compare(sb);
        return(sb.toString());
    }

    protected void indexLines1()
     {
         for (int i = 0; i < m_lines1.length; i++)
         {
             String[] items =  Util.parseCommaDelimited(m_lines1[i]);
             transformInput(items);
             m_IndexedLines1.put(buildLineIndex(items),items);
         }

     }
    protected void transformInput(String[] items)
    {
        transformInput2(items);  // trim
   //      if(items[CALL_TYPE_INDEX].equals("Interstate"))
  //              items[USAGE_TYPE_INDEX] = "27";

        int usage = Integer.parseInt(items[USAGE_TYPE_INDEX]);
        int oldUsage = usage;
        if(usage == 85 && items[CALL_TYPE_INDEX].startsWith("Wireless Domestic"))
                items[CALL_TYPE_INDEX] = "Airtime";
        if(usage == 28 && !items[CALL_TYPE_INDEX].startsWith("Airtime Roaming"))
        {
           items[USAGE_TYPE_INDEX] = "32";
            usage = 32;
        }
        if(usage == 32)  {
           if(items[CALL_TYPE_INDEX].startsWith("Airtime Roaming"))
               items[USAGE_TYPE_INDEX] = "28";
           else
                items[USAGE_TYPE_INDEX] = "27";
        }
        if(usage == 86 || usage == 85 )  {
            items[USAGE_TYPE_INDEX] = "31";
             usage = 31;
          }
          if(usage == 31 )
             items[CHARGE_INDEX] = "$0.00"; // zero out charges


        if(usage == 31 && items[CALL_TYPE_INDEX].startsWith("Wireless Canadian"))
                 items[CALL_TYPE_INDEX] = "Airtime";

    }
    protected void transformInput2(String[] items)
    {
        for (int i = 0; i < items.length; i++)
        {
            items[i] = items[i].trim();
        }
    }



    protected void indexLines2()
     {
         for (int i = 0; i < m_lines2.length; i++)
         {
             String[] items =  Util.parseCommaDelimited(m_lines2[i]);
             transformInput2(items);
             m_IndexedLines2.put(buildLineIndex(items),items);
         }

     }

    protected void showUnusedLine(StringBuffer sb,String index)
    {
        String[] items1 =  (String[])m_IndexedLines1.get(index);
        String added = "Uncarried Line " + Util.buildDelimitedString(items1, ',') + "\n";
        sb.append(added);
    }
    protected String compare(StringBuffer sb)
    {
        indexLines1();
        indexLines2();
        String[] indices = Util.collectionToStringArray(m_IndexedLines1.keySet());
        Arrays.sort(indices);
       showUnusedLines(sb,indices);
        int count = 0;
         for (int i = 0; i < indices.length; i++)
        {
            String index = indices[i];
            String[] items2 =  (String[])m_IndexedLines2.get(index);
            if(items2 == null)
                continue;
            String[] items1 =  (String[])m_IndexedLines1.get(index);
            boolean different = compareLine(sb,items1,items2,i);
            if(different)
                    count++;
            if(count > 100)
                break;
        }
        return(sb.toString());
    }

    protected void showUnusedLines(StringBuffer sb,String[] indices)
    {
        int NUnused = 0;
        for (int i = 0; i < indices.length; i++)
          {
              String index = indices[i];
              if(m_IndexedLines2.get(index) == null) {
                   showUnusedLine(sb,index);
                   NUnused++;
              }
          }
         System.out.println("Number Unused Lines = " + NUnused);
    }

    protected boolean compareLine(StringBuffer sbx,String[] items1,String[] items2,int line)
    {
        StringBuffer sb = new StringBuffer();
        if(items1.length != items2.length)
            sb.append("length differs length1 = " + items1.length + " length 2 = " + items2.length + "\n");
        int l = Math.min(items1.length,items2.length);
        for (int i = 0; i < l; i++)
        {
            if(i == TXT_NET_ID_INDEX)
                continue;
            if(i == RECORD_NUMBER_INDEX)
                continue;
            String s1 = items1[i];
            String s2 = items2[i];
            if(!s1.equals(s2)) {
                String issue = "On line " + line + " column " + i + " " + m_Captions[i] + " file1 = \'" + s1 +
                       "\' file2 = \'" + s2 + "\'";
               if(s1.trim().equals(s2.trim())) {
                   issue += " made length " + s2.length() + " spaces";
               }
               sb.append(issue + "\n");
            }
        }
        if(sb.length() > 0) {
            sbx.append("Original Line " + Util.buildDelimitedString(items1, ',') + "\n");
            sbx.append("Modified Line " + Util.buildDelimitedString(items2, ',') + "\n");
            String dif = sb.toString();
            sbx.append(dif);
            return(true);
        }
        return(false);
    }


    protected static void usage()
    {
        System.out.println("Compareg 2 cvs files:");
        System.out.println("Usage:");
        System.out.println("java com.lordjoe.Utilities.CompareCSV <file1> <file>");
      }
    public static void main(String[] args)
    {
        if(args.length < 2) {
            usage();
            return;
        }
        String[] file1 = FileUtilities.readInLines((args[0]));
        String[] file2 = FileUtilities.readInLines((args[1]));
        System.out.println("Lines in Base " + file1.length + " Lines in file 2 " + file2.length );
        CompareCSV comparer = new CompareCSV(file1,file2);
        String difs = comparer.compare();
        System.out.println(difs);
     }
}