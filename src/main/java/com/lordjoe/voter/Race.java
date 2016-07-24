package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lordjoe.voter.Race
 * User: Steve
 * Date: 6/23/2016
 */
public class Race  extends PersistentVoterItem implements Comparable<Race> {

    public static String buildIdString(District district, Integer year, ElectionStage stage) {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    public final District district;
    public final Integer year;
    public final ElectionStage stage;
    private final List<Candidate> candidates = new ArrayList<Candidate>();

    protected Race(District district, Integer year, ElectionStage stage) {
        super(GoogleDatabase.createKey(buildIdString(district,   year,   stage),Race.class));
        this.district = district;
        this.year = year;
        this.stage = stage;
    }

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }

    /**
     * incumbency unknown
     *
     * @param pol
     * @param party
     */

    public void addCandidate(Politician pol, Party party) {
        Candidate c = Candidates.getCandidate(pol, party, this);
        candidates.add(c);
    }

    public void addCandidate(Candidate c) {
        if (c.race != this)
            throw new IllegalStateException("problem"); // ToDo change
        candidates.add(c);
    }

    public List<Candidate> getCandidates()
    {
        return new ArrayList<Candidate>(candidates) ;
    }
    /**
     * return a candidate corresponding to the politician
     * @param pol as above
     * @return  possibly mull candidate
     */
    public Candidate getCandidate(Politician pol) {
        for (Candidate candidate : candidates) {
            if(candidate.pol.equals(pol))
                return candidate;
        }
        return null;
    }

    public boolean isIncumbent(Politician pol) {
        return district.isIncumbent(pol);
    }

    public ElectionStage getStage() {
        return stage;
    }

    public boolean isOpen()
    {
        for (Candidate candidate : candidates) {
             if(isIncumbent(candidate.pol))
                 return false;
        }
        return true;
    }

    public boolean isPrimary()
    {
        return stage == ElectionStage.Primary;
    }

    public boolean isGeneral()
    {
        return stage == ElectionStage.General;
    }

    public boolean isIncumbent(Candidate pol) {
        return isIncumbent(pol.pol);
    }


    public int getCandidateCount() {
        return candidates.size();
    }

    @Override
    public int compareTo(Race o) {
        if (o == this)
            return 0;
        if (district != o.district)
            return district.compareTo(o.district);
        if (year != null && year != o.year)
            return year.compareTo(o.year);
        return 0;
    }

    public String displayText() {
        StringBuilder sb = new StringBuilder();
        sb.append(district.displayText());
        sb.append("\n");
        for (Candidate candidate : candidates) {
            sb.append(candidate.displayText());
            if (isIncumbent(candidate))
                sb.append(" Incumbent");
            sb.append("\n");
        }
        return sb.toString();
    }
}
