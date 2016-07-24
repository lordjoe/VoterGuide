package com.lordjoe.voter;

/**
 * com.lordjoe.voter.IssueCategory
 * User: Steve
 * Date: 7/1/2016
 */
public enum IssueCategory {
    Budget,
    Campaign_Finance,
    Crime,
    Economy,
    Education,
    Environment,
    Gay_Marriage,
    Gun_Control,
    Healthcare,
    Immigration,
    Iraq,
    Social_Issues,
    Supreme_Court,
    Taxes,
    Voting_Rights;

    public static IssueCategory fromString(String s) {
        return valueOf(s.replace(" ","_"));
    }

    @Override
    public String toString() {
        return super.toString().replace("_"," ");
    }
}
