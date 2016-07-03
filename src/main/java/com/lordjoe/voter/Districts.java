package com.lordjoe.voter;

import java.util.*;

/**
 * com.lordjoe.voter.Districts
 * User: Steve
 * Date: 6/25/2016
 */
public class Districts {
    private static final Map<District,District> districts = new HashMap<District,District>();

    /**
     *   rturn an existing district or make an new one
     * @param type
     * @param state
     * @param number
     * @return
     */
    public static District getDistrict( OfficeType type, State state, Integer number) {

        District ret = new District(type, state, number);
        District existing = districts.get(ret);
        if(existing == null) {
            districts.put(ret,ret);
            existing = ret;
        }

        return existing;
    }

    /**
     *   rturn an existing district or make an new one
     * @param type
     * @param state
     * @param number
     * @return
     */
    public static District getDistrict( OfficeType type, State state) {

        switch (type)  {
            case Governor:
                break;
            case Senator:
                break;
            default:
                throw new IllegalArgumentException("Offices other than Governao or Senator need a district"); // ToDo change  if state offices added
        }
        return getDistrict(type,state,(Integer)null);
    }


    public static District getDistrict( OfficeType type, State state, String id) {
        Integer number = null;
        try {
            number = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            number = null;
         }
        return getDistrict(type,   state,   number);
    }

}
