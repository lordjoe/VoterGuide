package com.lordjoe.voter;

/**
 * com.lordjoe.voter.votesmart.VoteEvidence
 * User: Steve
 * Date: 7/1/2016
 */
public class InterestGroupRatingEvidence extends Evidence {


    private final InterestGroup group;
    private final String rating;

    public InterestGroupRatingEvidence(InterestGroup group, String rating) {
        this.group = group;
        this.rating = rating;
    }



}
