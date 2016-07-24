package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lordjoe.voter.Evidence
 * User: Steve
 * Date: 7/1/2016
 */
public abstract class Evidence extends PersistentVoterItem {
     private final List<String> sources = new ArrayList<String>() ;

    public Evidence(Key key) {
        super(key);
    }

    public Evidence(Entity e) {
        super(e);
    }


    public String getSource() {
         if(sources.isEmpty())
             return null;
         return sources.get(0);
     }

    public void addSource(String source) {
        if(!sources.contains(source))
            sources.add(source);
    }

    public List<String> getSources() {
        return new ArrayList<String>(sources);
    }
}
