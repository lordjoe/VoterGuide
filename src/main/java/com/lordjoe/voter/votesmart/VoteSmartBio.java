package com.lordjoe.voter.votesmart;

import com.lordjoe.voter.*;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.CandidateBioClass;
import org.votesmart.data.AddlBio;
import org.votesmart.data.Bio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lordjoe.voter.votesmart.VoteSmartRating.readCandidateRating;

/**
 * com.lordjoe.voter.votesmart.VoteSmartBio
 * User: Steve
 * Date: 7/3/2016
 */
public class VoteSmartBio {
    private static CandidateBioClass bc;

    protected static void initIfNeeded() {
        if (bc == null) {
            try {
                bc = new CandidateBioClass();
                List<Candidate> candidates = Candidates.getCandidates();
                for (Candidate candidate : candidates) {
                    updateFromBio(candidate.pol);
                }
            } catch (VoteSmartException e) {
                throw new RuntimeException(e);

            }
            readCandidateRating();
            //showInterestGroups();
        }
    }

    private static void updateFromBio(Politician politician) {
        try {

            Bio bio = null;
            AddlBio addlBio = null;
            bio = bc.getBio(politician.getVoteSmartIdStr());
            try {
                addlBio = bc.getAddlBio(politician.getVoteSmartIdStr());
            } catch (VoteSmartException e) {


            } catch (VoteSmartErrorException e) {
            }

            updateFromBios(politician, bio, addlBio);
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }

    }


    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    private static void updateFromBios(Politician politician, Bio bio, AddlBio addlBio) {
        PersonalInformation info = politician.getInfo();
        Bio.Candidate candidate = bio.candidate;
        String birthDate = candidate.birthDate;
        if (!VoteSmartUtilities.isEmptyOrNull(birthDate)) {
            try {
                Date birthday = DATE_FORMATTER.parse(birthDate);
                info.setBirthday(birthday);
            } catch (Exception e) {
                //System.out.println("Cannot parse " + birthDate);
            }
        }

        try {
            Gender gender = Gender.valueOf(candidate.gender);
            info.setGender(gender);
        } catch (IllegalArgumentException e) {
        }

        info.setReligion(candidate.religion);

        String photo = candidate.photo;
        politician.setImageURL(photo);

        String family = candidate.family;
        parseFamily(info, family);


    }

    private static void parseFamily(PersonalInformation info, String family) {
        if (VoteSmartUtilities.isEmptyOrNull(family))
            return;
        String[] items = family.split(";");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (item.startsWith("Wife:"))
                info.setSponse(new Person(item.replace("Wife:", ""), null,null));
            if (item.startsWith("Husband:"))
                info.setSponse(new Person(item.replace("Husband:", ""), null,null));
            if (item.contains("Child")) {
                handleChildren(info, item);
            }
        }
    }

    private static void handleChildren(PersonalInformation info, String item) {
        item = item.trim();
        String[] split = item.split(",");
        String[] child1 = split[0].split(" ");
        if(child1.length != 3)
            return;
        Person child =  new Person(child1[2], null,null);
        info.addChild(child);
        for (int i = 1; i < split.length; i++) {
            String s = split[i].replace(",","");
              child = new Person(s, null,null);
            info.addChild(child);
        }

    }


}
