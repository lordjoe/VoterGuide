package com.lordjoe.voter;

/**
 * com.lordjoe.voter.Person
 * User: Steve
 * Date: 6/24/2016
 */
public class Person implements Comparable<Person> {
    private final String firstName;
    private final String lastName;   // might be null - unknown for children, wives

    public Person(String firstName, String lastName) {
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
}
