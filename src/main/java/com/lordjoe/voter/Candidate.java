package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.Comparator;

/**
 * com.lordjoe.voter.Candidate
 * User: Steve
 * Date: 6/23/2016
 */
public class Candidate extends PersistentVoterItem {
    public static final Comparator<? super Candidate> BY_DISTRICT = new Comparator<Candidate>() {
        @Override
        public int compare(Candidate o1, Candidate o2) {
            if(o1 ==o2)
                return 0;
            District d1 = o1.race.district;
            District d2 = o2.race.district;
            if(!d1.equals(d2))
                return d1.compareTo(d2);
            Party p1 = o1.party;
            Party p2 = o2.party;
            if(p1 != p2)
                return p1.compareTo(p2);
            return o1.pol.toString().compareTo(o2.pol.toString());

        }
    };
    public  final   Politician pol;
    public   final  Party party;
    public  final Race race;

    public Candidate(Politician pol, Party party, Race race) {
        super(GoogleDatabase.createKey(pol.getVoteSmartIdStr(),Candidate.class));
        this.pol = pol;
        this.party = party;
        this.race = race;
    }

    public boolean isIncumbent()
    {
        return race.isIncumbent(this) ;
    }

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }

    public String displayText() {
        StringBuilder sb = new StringBuilder();
         sb.append(pol.toString() );
        sb.append(" " );
        sb.append(party) ;
        return sb.toString();
    }

    @Override
    public String toString() {
        return  displayText() ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        if (!pol.equals(candidate.pol)) return false;
        if (party != candidate.party) return false;
        return race.equals(candidate.race);

    }

    @Override
    public int hashCode() {
        int result = pol.hashCode();
        result = 31 * result + (party != null ? party.hashCode() : 0);
        result = 31 * result + race.hashCode();
        return result;
    }
}
