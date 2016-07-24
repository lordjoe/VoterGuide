package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * com.lordjoe.voter.Person
 * User: Steve
 * Date: 6/24/2016
 */
public class Person extends PersistentVoterItem implements Comparable<Person> {
    private   String firstName;
    private   String lastName;   // might be null - unknown for children, wives

    public Person(String firstName, String lastName, Key key) {
        super(key);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(firstName);
         if(lastName != null) {
             sb.append(" ");
             sb.append(lastName);
         }
         return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Person person = (Person) o;

        if (!firstName.equals(person.firstName)) return false;
        return lastName != null ? lastName.equals(person.lastName) : person.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Person o) {
        int ret = lastName.compareTo(o.lastName);
        if(ret != 0) return ret;
        return firstName.compareTo(o.firstName);
    }

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {
        firstName = (String)e.getProperty("firstName");
        lastName = (String)e.getProperty("lastName");
    }
}
