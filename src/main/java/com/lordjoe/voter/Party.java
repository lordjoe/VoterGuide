package com.lordjoe.voter;

/**
 * com.lordjoe.voter.Party
 * User: Steve
 * Date: 6/23/2016
 */
public enum Party {
    Democrat, Republican, Libertarian, Independent, WriteIn, Constitution, Green_Party, Reform, At_Large;

    public static Party fromName(String name) {
        if ("democratic".equalsIgnoreCase(name))
            return Democrat;
        try {
            Party ret = valueOf(name);
            return ret;
        } catch (IllegalArgumentException e) {
            return Independent;

        }
    }

}
