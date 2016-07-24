package com.lordjoe.voter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.lordjoe.voter.Candidates
 * User: Steve
 * Date: 7/1/2016
 */
public class Candidates {
    private static final Map<Candidate,Candidate> candidates = new HashMap<Candidate,Candidate>();
    private static final Map<Politician,Candidate> politicianToCandidate     = new HashMap<Politician,Candidate>();


    public static Candidate asCandidate(Politician pol)   {
        return politicianToCandidate.get(pol);
    }

    public static boolean isCandidate(Politician pol)   {
        return politicianToCandidate.containsKey(pol);
    }

    public static List<Candidate>  getCandidates()
    {
        List<Candidate>  ret = new ArrayList<Candidate>();
        for (Politician politician : Politicians.getPoliticians()) {
             Candidate c = asCandidate(politician);
            if(c != null)
                ret.add(c);
        }
        return ret;
    }

    public static Candidate getCandidate(Politician pol, Party party, Race race) {
        Candidate ret = new Candidate(pol, party, race);
        Candidate existing = candidates.get(ret);
        if(existing == null) {
            candidates.put(ret,ret);
            politicianToCandidate.put(pol,ret);
            race.addCandidate(pol, party);
            existing = ret;
        }

        return existing;
    }




}
