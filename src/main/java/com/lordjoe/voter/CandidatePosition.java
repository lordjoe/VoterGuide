package com.lordjoe.voter;

import com.lordjoe.utilities.DualList;

import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.voter.CandidatePosition
 * User: Steve
 * Date: 7/4/2016
 */
public class CandidatePosition {

    private static Map<String,CandidatePosition> byText = new HashMap<String, CandidatePosition>();
    private static DualList<Issue,CandidatePosition> byIssue = new DualList<Issue,CandidatePosition>(Issue.class,CandidatePosition.class);

    public static CandidatePosition getPosition(Issue issue, String text) {
        CandidatePosition ret = byText.get(text);
        if(ret == null)   {
            ret = new CandidatePosition(issue,text);
        }
        return ret;
    }

        public final Issue issue;
    public final String text;

    private CandidatePosition(Issue issue, String text) {
        this.issue = issue;
        this.text = text;
        byText.put(text,this);
        byIssue.register(issue,this);
    }
}
