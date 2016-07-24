package com.lordjoe.ui.resources;


import java.util.*;


/**
 * com.boeing.obeds.ui.resources.ResourceString
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class ResourceString
{
    public static final Class THIS_CLASS = ResourceString.class;
    public static final ResourceString EMPTY_ARRAY[] = {};

    public static String getStringFromText(String englishText)
     {
        return getStringFromText(englishText,Locale.getDefault());
     }


    public static String getStringFromText(String englishText,Locale locale)
     {
         return INSTANCE.doGetStringFromText(englishText,locale);
     }

    private static final ResourceString INSTANCE = new ResourceString();

    public ResourceString()
    {

    }

    protected String doGetStringFromText(String englishText,Locale locale)
     {
//         if(locale.equals(PigLatin.PIG_LATIN))
//            return PigLatin.translate(englishText);
         return englishText;
     }


 }
