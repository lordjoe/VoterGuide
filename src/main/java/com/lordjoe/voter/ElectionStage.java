package com.lordjoe.voter;

/**
 * com.lordjoe.voter.votesmart.ElectionStage
 * User: Steve
 * Date: 7/1/2016
 */
public enum ElectionStage {
    General("G"),Primary("P") ;
    ;
    public final String votesmartID;

    ElectionStage(String votesmartID) {
        this.votesmartID = votesmartID;
    }

    public static ElectionStage fromId(String id)
    {
        for (ElectionStage electionStage : values()) {
             if(electionStage.votesmartID.equalsIgnoreCase(id))
                 return electionStage;
        }
        return null;
    }
}
