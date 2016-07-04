package com.lordjoe.voter.votesmart;

import com.lordjoe.voter.Politician;
import com.lordjoe.voter.VotePosition;

/**
 * com.lordjoe.voter.votesmart.LegislativeVote
 * User: Steve
 * Date: 7/3/2016
 */
public class LegislativeVote {
    public final Politician pol;
    public final VotePosition position;

    public LegislativeVote(Politician pol, VotePosition position) {
        this.pol = pol;
        this.position = position;
    }
}
