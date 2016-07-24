package com.lordjoe.voter.votesmart;

import com.lordjoe.voter.Politician;
import com.lordjoe.voter.VotePosition;

import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.voter.votesmart.VoteSmartRollCall
 * User: Steve
 * Date: 7/3/2016
 */
public class VoteSmartRollCall {

    public final Integer id;
    public final VoteSmartBill bill;
    private final Map<Politician, VotePosition> votes = new HashMap<Politician, VotePosition>();

    public VoteSmartRollCall(VoteSmartBill bill,Integer id) {
        this.id = id;
        this.bill = bill;
    }

    public String getIdStr()
    {
        return Integer.toString(id);
    }

    public void addVote(Politician pol, VotePosition vote) {
        votes.put(pol,vote);
    }

    public int getAyes() {
        int ret = 0;
        for (VotePosition votePosition : votes.values()) {
            if(votePosition == VotePosition.Yea)
                ret++;
        }
        return ret;
    }

    public int getNay() {
        int ret = 0;
        for (VotePosition votePosition : votes.values()) {
            if(votePosition == VotePosition.Nay)
                ret++;
        }
        return ret;
    }

    public int getAbstains() {
        int ret = 0;
        for (VotePosition votePosition : votes.values()) {
            if(votePosition == VotePosition.Abstain)
                ret++;
        }
        return ret;
    }
}
