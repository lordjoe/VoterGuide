package com.lordjoe.voter;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lordjoe.voter.Evidence
 * User: Steve
 * Date: 7/1/2016
 */
public class Evidence {
     private final List<String> sources = new ArrayList<>() ;


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
        return new ArrayList<>(sources);
    }
}
