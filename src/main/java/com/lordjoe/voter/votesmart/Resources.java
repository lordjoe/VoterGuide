package com.lordjoe.voter.votesmart;

import com.lordjoe.voter.State;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * com.lordjoe.voter.votesmart.Resources
 * User: Steve
 * Date: 7/4/2016
 */
public class Resources {

    /**
     * page from on the issues on gubenatorial races
     * @param st
     * @return
     */
    public URL onTheIssuesGovernor(State st)   {
        String url = "http://governor.ontheissues.org/2016_" + st.getAbbreviation() + "_Gov.htm";
        try {
            URL ret = new URL(url);
            return ret;
        } catch (MalformedURLException e) {
           return null;

        }
    }

}
