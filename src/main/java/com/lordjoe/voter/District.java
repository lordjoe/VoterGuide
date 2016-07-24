package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * com.lordjoe.voter.District
 * User: Steve
 * Date: 6/23/2016
 */
public class District  extends PersistentVoterItem  implements Comparable<District> {
    public final OfficeType type;
    public final State state; // null for federal
    public final Integer number; // null if none
    protected Set<Politician> incumbent = new HashSet<Politician>();

    protected static String districtIdStr(OfficeType type, State state, Integer number) {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

        /**
         * use Districts.getDistrict
         * @param type
         * @param state
         * @param number
         */
    protected District(OfficeType type, State state, Integer number) {
        super(GoogleDatabase.createKey(districtIdStr(  type,   state,   number),District.class));
        this.type = type;
        this.state = state;
        this.number = number;
    }


    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }

    public void setIncumbent(Politician incumbent) {
        this.incumbent.add(incumbent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        District district = (District) o;

        if (type != district.type) return false;
        if (state != district.state) return false;
        return number != null ? number.equals(district.number) : district.number == null;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(District o) {
        if (o == this)
            return 0;
        if (state != o.state)
            return state.compareTo(o.state);
        if (number != null && o.number != null && number != o.number)
            return number.compareTo(o.number);
        if (type != o.type)
            return type.compareTo(o.type);
        return 0;
    }

    public String displayText() {
        StringBuilder sb = new StringBuilder();
        sb.append(state);
        if (number != null)
            sb.append(" " + number);
        sb.append(" " + type);
        return sb.toString();
    }

    @Override
    public String toString() {
        return displayText();
    }

    public boolean isIncumbent(Politician pol) {
        return incumbent.contains(pol);
    }
}
