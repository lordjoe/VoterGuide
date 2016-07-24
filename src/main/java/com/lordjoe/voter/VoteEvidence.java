package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * com.lordjoe.voter.votesmart.VoteEvidence
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteEvidence extends Evidence {


    private final Bill bill;
    private final VotePosition position;

    public VoteEvidence(Bill bill, VotePosition position,Key key) {
        super(key);
        this.bill = bill;
        this.position = position;
    }


    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }
}
