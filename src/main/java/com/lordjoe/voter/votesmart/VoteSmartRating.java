package com.lordjoe.voter.votesmart;

import com.google.appengine.api.datastore.Key;
import com.lordjoe.utilities.CollectionUtilities;
import com.lordjoe.utilities.DualList;
import com.lordjoe.voter.*;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.RatingClass;
import org.votesmart.data.Rating;
import org.votesmart.data.SigRating;
import org.votesmart.data.Sigs;

import java.util.*;

import static com.lordjoe.voter.votesmart.MyVoteSmart.breakHere;

/**
 * com.lordjoe.voter.votesmart.VoteSmartRating
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteSmartRating {
    private static  RatingClass rc;
    private static DualList<InterestGroup, VoteSmartIssueCategory> categoryNameToId = new DualList<InterestGroup, VoteSmartIssueCategory>(InterestGroup.class, VoteSmartIssueCategory.class);
    private static Map<InterestGroup, Map<Politician, InterestGroupRatingEvidence>> groupRatings = new HashMap<InterestGroup, Map<Politician, InterestGroupRatingEvidence>>();

    protected static void initIfNeeded() {
        if (rc == null) {
            try {
                rc = new RatingClass();
                for (VoteSmartIssueCategory issue : VoteSmartIssueCategory.values()) {
                    try {
                        Sigs sigList = rc.getSigList(issue.getIdString());
                        for (Sigs.Sig sig : sigList.sig) {
                            String sigId = sig.sigId;
                            String name = sig.name;
                            InterestGroup ig = InterestGroup.getInterestGroup(name, Integer.parseInt(sigId));
                            categoryNameToId.register(ig, issue);
                        }

                    } catch (VoteSmartException e) {
                        continue;

                    } catch (VoteSmartErrorException e) {
                        continue;

                    }

                }
            } catch (VoteSmartException e) {
                throw new RuntimeException(e);

            }
            readCandidateRating();
            showInterestGroups();
        }
    }

    public static  Map<Politician, InterestGroupRatingEvidence> getGroupRatings(InterestGroup group)  {
        return  groupRatings.get(group);
    }

    public static void readCandidateRating() {
        for (VoteSmartIssueCategory issue : VoteSmartIssueCategory.values()) {
            Set<InterestGroup> interestGroups = categoryNameToId.reverseValues();
               for (InterestGroup interestGroup : interestGroups) {
                guaranteeInterestGroupRatings(interestGroup);
            }

        }

    }

    private static void guaranteeInterestGroupRatings(InterestGroup group) {
        Map<Politician, InterestGroupRatingEvidence> ratings = groupRatings.get(group);
        if(ratings != null)
            return; // already done
        ratings =  new HashMap<Politician, InterestGroupRatingEvidence>() ;
        String mostRecentRating = getMostRecentRating(group);
        if(mostRecentRating != null)
            readGroupRating(group,mostRecentRating,ratings);


        groupRatings.put(group,ratings);
    }

    private static void readGroupRating(InterestGroup group, String mostRecentRating,Map<Politician, InterestGroupRatingEvidence> ratings ) {
        try {
        //    System.out.println(group.name);
            Rating rating = rc.getRating(mostRecentRating);
            ArrayList<Rating.CandidateRating> candidateRating1 = rating.candidateRating;
            if(group.name.equals("National Rifle Association"))
                breakHere();
            for (Rating.CandidateRating candidateRating : candidateRating1) {
                String candidateId = candidateRating.candidateId;
                String ratingStr = candidateRating.rating;
                Integer id = Integer.parseInt(candidateId);
                Politician pol = Politicians.getByID(id);
                if(pol == null)
                    continue;
                Key key = GoogleDatabase.createKey(group.name,InterestGroupRatingEvidence.class,pol) ;
                InterestGroupRatingEvidence evidence = new InterestGroupRatingEvidence(group,ratingStr,key);
                if(evidence != null && pol != null) {
                    ratings.put(pol,evidence);
                }
            }
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }
    }

//    private static InterestGroupRatingEvidence buildEvidence(InterestGroup group,String ratingStr)
//    {
//         int rating = Integer.parseInt(ratingStr);
//        Key key = PersistentVoterItem.createKey(group.name,InterestGroupRatingEvidence.class,pol) ;
//        InterestGroupRatingEvidence ret = new InterestGroupRatingEvidence(group,ratingStr);
//        return ret;
//    }

    private static String getMostRecentRating(InterestGroup group) {
        try {
            SigRating sigRatings = rc.getSigRatings(group.getIdString());
            int latestYear = 1;
            String mostRecentRatingId = null;
            for (SigRating.Rating rating : sigRatings.rating) {
                String ratingId = rating.ratingId;
                String timespan = rating.timespan;
                int ratingYear = VoteSmartUtilities.timeSpanToYear(timespan) ;
                if(ratingYear > latestYear)  {
                    mostRecentRatingId = ratingId;
                    latestYear = ratingYear;
                }
            }
            return mostRecentRatingId;
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            return null;

        }
    }

    public static void showInterestGroups() {
        List<InterestGroup> groups = new ArrayList<InterestGroup>(categoryNameToId.reverseValues());
        Collections.sort(groups, new Comparator<InterestGroup>() {
            @Override
            public int compare(InterestGroup o1, InterestGroup o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        for (InterestGroup interestGroup : groups) {
            Map<Politician, InterestGroupRatingEvidence> ratings = groupRatings.get(interestGroup);
            if(ratings == null || ratings.size() == 0)
                continue; // no ratings
            int numberRatings = ratings.size();
            Set<VoteSmartIssueCategory> voteSmartIssueCategories = categoryNameToId.forwardLookup(interestGroup);
            List<VoteSmartIssueCategory> vc = CollectionUtilities.sortedCollection(voteSmartIssueCategories);
            System.out.print(interestGroup);
            System.out.print("=>");
            System.out.print(numberRatings);
            System.out.print("=>");
            for (VoteSmartIssueCategory vcat : vc) {
                System.out.print(vcat);
                System.out.print(",");
            }
            System.out.println();
        }
    }


}
