package com.lordjoe.voter;

import com.google.appengine.api.datastore.Key;
import com.lordjoe.utilities.CollectionUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * com.lordjoe.voter.Politicians
 * User: Steve
 * Date: 6/24/2016
 */
public class Politicians {
    public static final Map<Person,Politician> byPerson = new HashMap<Person, Politician>();
    public static final Map<Integer,Politician> byVoteSmartId = new HashMap<Integer, Politician>();
    public static final Map<Key,Politician> byKey = new HashMap<Key, Politician>();

    public static Politician get(Integer id,String firstName,String lastName)  {
        Politician ret = byVoteSmartId.get(id);
        if(ret == null) {
            ret = new Politician( firstName,lastName,id);
            register(ret);
         }
        else {
            if(!ret.getFirstName().equalsIgnoreCase(firstName))
                throw new IllegalStateException("bad first name");
            if(!ret.getLastName().equalsIgnoreCase(lastName))
                throw new IllegalStateException("bad last name");
        }
         return ret;

    }

    public static List<Politician> getPoliticians()
    {
        Collection<Person> persons = byPerson.keySet();
        List<Person> ts = CollectionUtilities.sortedCollection(persons);
        List<Politician> pols = new ArrayList<Politician>();
        for (Person p  : ts) {
           pols.add(byPerson.get(p));
        }
        return pols;
    }

    private static void register(Politician ret) {
        byVoteSmartId.put(ret.getVoteSmartId(),ret);
        Key key = ret.getKey();
        if(key != null)
            byKey.put(key,ret) ;
        byVoteSmartId.put(ret.getVoteSmartId(),ret);
        byPerson.put(new Person(ret.getFirstName(),ret.getLastName(),null),ret);
    }


    public static Politician getByID(Integer id)  {
        Politician politician = byVoteSmartId.get(id);
        return politician;
    }

    public static Politician getByName(String first,String last)
    {
        Person test = new Person(first,last,null);
        return byPerson.get(test);
    }

    public static void writePoliticians(File outFile)  {
        try {
            PrintWriter candidateWriter = new PrintWriter(new FileWriter(outFile));
            for (Politician politician : byVoteSmartId.values()) {
                candidateWriter.println(buildCandidateText(politician));
            }
            candidateWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }


    private static String buildCandidateText(Politician pol ) {
        StringBuilder sb = new StringBuilder();
        sb.append(pol.getVoteSmartId());
        sb.append("\t");
        sb.append(pol.getFirstName());
        sb.append("\t");
        sb.append(pol.getLastName());
        sb.append("\t");
        sb.append(pol.getImageURL());
        sb.append("\t");

        return sb.toString();
    }


}
