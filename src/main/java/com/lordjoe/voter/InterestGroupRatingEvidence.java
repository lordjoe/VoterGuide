package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * com.lordjoe.voter.votesmart.VoteEvidence
 * User: Steve
 * Date: 7/1/2016
 */
public class InterestGroupRatingEvidence extends Evidence {


    public final InterestGroup group;
    public final String rating;

    public InterestGroupRatingEvidence(InterestGroup group, String rating,Key key) {
        super(key);
        this.group = group;
        this.rating = rating;
    }


    @Override
    public Entity asEntity() {
        return null;
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }
}
