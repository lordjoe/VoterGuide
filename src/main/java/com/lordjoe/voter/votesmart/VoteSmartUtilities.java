package com.lordjoe.voter.votesmart;

/**
 * com.lordjoe.voter.votesmart.VoteSmartUtilieies
 * User: Steve
 * Date: 7/2/2016
 */
public class VoteSmartUtilities {

    public static boolean isEmptyOrNull(String s)  {
        if(s == null)
            return true;
        return s.length() == 0;
    }

    public static int timeSpanToYear(String timeSpan) {
        String[] split = timeSpan.split("-");
        String yearStr = split[split.length - 1];
        try {
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            return 0;

        }
    }

}
