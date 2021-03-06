package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.voter.InterestGroup
 * User: Steve
 * Date: 7/1/2016
 */
public class InterestGroup   extends PersistentVoterItem implements Comparable<InterestGroup> {
    private static Map<String,InterestGroup> byName = new HashMap<String, InterestGroup>() ;
    private static Map<Integer,InterestGroup> byId = new HashMap<Integer, InterestGroup>();


    public static InterestGroup getInterestGroup(String name)  {
        return byName.get(name);
    }

    public static InterestGroup getInterestGroup(Integer id)  {
        return byId.get(id);
    }

    public static InterestGroup getInterestGroup(String name,Integer id)  {
        InterestGroup ret = byName.get(name);
        if(ret == null)  {
            ret = new  InterestGroup(name,id);
            byName.put(name,ret) ;
            byId.put(id,ret) ;
        }
        return ret;
    }

    public final String name;
    public final Integer id;


    public String getIdString() {
        return Integer.toString(id);
    }
    private InterestGroup(String name, Integer id) {
        super(GoogleDatabase.createKey(Integer.toString(id),InterestGroup.class));
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {
           throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public int compareTo(InterestGroup o) {
        return name.compareTo(o.name);
    }
}
