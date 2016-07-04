package com.lordjoe.voter;

import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.voter.Politician
 * User: Steve
 * Date: 6/23/2016
 */
public class Politician extends Person {

    private Integer voteSmartId;
    private String imageURL;
    private final Map<Issue, Position> Positions = new HashMap<Issue, Position>();
    private final PersonalInformation info = new PersonalInformation();

    protected Politician(String firstName, String lastName, Integer voteSmartId) {
        super(firstName, lastName);
        this.voteSmartId = voteSmartId;
    }

    public String getVoteSmartIdStr() {
        return Integer.toString(getVoteSmartId());
    }

    public Integer getVoteSmartId() {
        return voteSmartId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public PersonalInformation getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Politician that = (Politician) o;

        return voteSmartId != null ? voteSmartId.equals(that.voteSmartId) : that.voteSmartId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (voteSmartId != null ? voteSmartId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " id = " + getVoteSmartId();
    }
}
