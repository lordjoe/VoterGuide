package com.lordjoe.voter;

/**
 * com.lordjoe.voter.votesmart.VoteEvidence
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteEvidence extends Evidence {


    private final Bill bill;
    private final VotePosition position;

    public VoteEvidence(Bill bill, VotePosition position) {
        this.bill = bill;
        this.position = position;
    }



}
