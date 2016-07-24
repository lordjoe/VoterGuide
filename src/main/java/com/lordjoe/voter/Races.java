package com.lordjoe.voter;

import com.lordjoe.utilities.CollectionUtilities;

import java.io.IOException;
import java.util.*;

/**
 * com.lordjoe.voter.Races
 * User: Steve
 * Date: 6/24/2016
 */
public class Races {
    public static final Map<District,Race> byDistrict = new HashMap<District, Race>();



    private static void register(Race ret) {
        byDistrict.put( ret.district ,ret);
    }

    public static Race getRace(District d,Integer year,ElectionStage stage) {
        Race race = byDistrict.get(d);
        if(race == null)  {
            race = new  Race(d,year,stage);
            register(race);
        }
        return race;
    }

    public static List<Race> getRace(State s) {
        List<Race> ret = new ArrayList<Race>();
        for (Race race : byDistrict.values()) {
           if(race.district.state == s)
               ret.add(race);
        }
        Collections.sort(ret);
        return ret;
    }

    public static Race getRace(District d,Integer year) {
         return getRace(d,year,ElectionStage.General);
    }


    public static void listRaces(Appendable out) {
        try {
            Collection<Race> values = byDistrict.values();
               List<Race>  races =  CollectionUtilities.sortedCollection(values);
            for (Race race : races) {
               out.append(race.displayText());
                out.append("\n");
                out.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}
