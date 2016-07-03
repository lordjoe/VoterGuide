package com.lordjoe.voter;

/**
 * com.lordjoe.voter.EvidenceSourceType
 * User: Steve
 * Date: 7/1/2016
 */
public enum EvidenceSourceType {
    Vote,Veto,Public_Statement,Private_Statement,Website,Article;
    public static EvidenceSourceType fromString(String s) {
        return valueOf(s.replace(" ","_"));
    }

    @Override
    public String toString() {
        return super.toString().replace("_"," ");
    }

}
