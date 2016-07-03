package com.lordjoe.voter;

import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.voter.Bills
 * User: Steve
 * Date: 7/1/2016
 */
public class Bills {
    private static final Map<Bill,Bill> bills = new HashMap<Bill,Bill>();

      /**
     *   rturn an existing district or make an new one
     * @param type
     * @param state
     * @param number
     * @return
     */
    public static Bill getBill( LegislativeBranch branch,String name) {
          throw new UnsupportedOperationException("Fix This"); // ToDo
//        Bill ret = new Bill(branch, name);
//        Bill existing = bills.get(ret);
//        if(existing == null) {
//            bills.put(ret,ret);
//            existing = ret;
//        }
//
//        return existing;
    }




}
