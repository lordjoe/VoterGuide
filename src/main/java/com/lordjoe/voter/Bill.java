package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * com.lordjoe.voter.votesmart.Bill
 * User: Steve
 * Date: 7/1/2016
 */
public class Bill extends PersistentVoterItem {
    public final LegislativeBranch branch;
    public final String name;
    public final String votesmartName;
    public final Integer year;

    public Bill(LegislativeBranch branch, String name, String votesmartName, Integer year) {
        super(GoogleDatabase.createKey(votesmartName,Bill.class));
        this.branch = branch;
        this.name = name;
        this.votesmartName = votesmartName;
        this.year = year;
    }

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (branch != bill.branch) return false;
        if (!name.equals(bill.name)) return false;
        if (!votesmartName.equals(bill.votesmartName)) return false;
        return year.equals(bill.year);

    }

    @Override
    public int hashCode() {
        int result = branch.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + votesmartName.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }
}
