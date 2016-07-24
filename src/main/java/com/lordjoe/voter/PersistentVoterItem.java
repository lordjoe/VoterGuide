package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * org.votesmart.api.PersistentVoterItem
 * User: Steve
 * Date: 7/21/2016
 */
public abstract class PersistentVoterItem {
    private final Key key;

    public PersistentVoterItem(Key key) {
        this.key = key;
    }

    public PersistentVoterItem(Entity e) {
        this(e.getKey());
    }

    public Key getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistentVoterItem that = (PersistentVoterItem) o;

        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : super.hashCode();
    }

    public abstract @NonNull  Entity asEntity();

    /**
     * given an entity set values
     * @param e
     */
    public abstract void populateFromEntity(@NonNull  Entity e);
}
