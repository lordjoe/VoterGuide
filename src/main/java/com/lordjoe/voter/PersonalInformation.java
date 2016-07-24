package com.lordjoe.voter;


import java.util.*;

/**
 * com.lordjoe.voter.PersonalInformation
 * User: Steve
 * Date: 6/24/2016
 */
public class PersonalInformation {
    private Gender gender;
    private Person sponse;
    private final Set<Person> children = new HashSet<Person>();
    private String religion;
    private  Date birthday;

    public void setFrom(PersonalInformation p) {
        setGender(p.getGender());
        setBirthday(p.getBirthday());
        setSponse(p.getSponse());
        setReligion(p.getReligion());
        for (Person child : p.getChildren()) {
            addChild(child);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Person getSponse() {
        return sponse;
    }

    public void setSponse(Person sponse) {
        this.sponse = sponse;
    }

    public List<Person> getChildren() {
        return new ArrayList<Person> (children);
    }

    public void addChild(Person child) {
        children.add(child);
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(int year,int month,int day) {
        setBirthday( new Date( year,month,day));
    }

    public int getAge() {
        Date today = new Date();
        long ageMillisec = today.getTime() - getBirthday().getTime();
        return (int)(ageMillisec / (365.25 * 24 * 60 * 60 * 1000));
    }
}
