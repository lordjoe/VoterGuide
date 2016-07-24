package com.lordjoe.general;

import com.lordjoe.lang.StringOps;


/**
 * com.lordjoe.general.StringUtilities
 *
 * @author slewis
 * @date Feb 23, 2005
 */
public class StringUtilities
{
    public static final Class THIS_CLASS = StringUtilities.class;
    public static final StringUtilities EMPTY_ARRAY[] = {};

    public static final char[] SPACE_DELIMITER = { ' ' };
    public static final char[] COMMA_DELIMITER = { ',' };
    public static final char[] TAB_DELIMITER = { '\t' };
    public static final char[] WHITE_SPACE_DELIMITER = { ' ','\n','\t' };



    public static void handleProblem(StringBuffer sb,String problem)
    {
        if(StringOps.isBlank(problem) )
            return;
       sb.append(problem + "\n");
    }


}
