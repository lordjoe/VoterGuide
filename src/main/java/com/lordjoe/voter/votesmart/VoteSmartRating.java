package com.lordjoe.voter.votesmart;

import com.lordjoe.utilities.CollectionUtilities;
import com.lordjoe.utilities.DualList;
import com.lordjoe.utilities.DualMap;
import com.lordjoe.voter.*;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.RatingClass;
import org.votesmart.classes.VotesClass;
import org.votesmart.data.*;

import java.util.*;

/**
 * com.lordjoe.voter.votesmart.VoteSmartRating
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteSmartRating {
    private static  RatingClass rc;
    private static DualList<InterestGroup, VoteSmartIssueCategory> categoryNameToId = new DualList<>(InterestGroup.class, VoteSmartIssueCategory.class);
    private static Map<InterestGroup, Map<Politician, InterestGroupRatingEvidence>> groupRatings = new HashMap<>();

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
            //showInterestGroups();
        }
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
        ratings =  new HashMap<>() ;
        String mostRecentRating = getMostRecentRating(group);
        if(mostRecentRating != null)
            readGroupRating(group,mostRecentRating,ratings);


        groupRatings.put(group,ratings);
    }

    private static void readGroupRating(InterestGroup group, String mostRecentRating,Map<Politician, InterestGroupRatingEvidence> ratings ) {
        try {
        //    System.out.println(group.name);
            Rating rating = rc.getRating(mostRecentRating);
            for (Rating.CandidateRating candidateRating : rating.candidateRating) {
                String candidateId = candidateRating.candidateId;
                String ratingStr = candidateRating.rating;
                Integer id = Integer.parseInt(candidateId);
                Politician pol = Politicians.getByID(id);
                InterestGroupRatingEvidence evidence = new InterestGroupRatingEvidence(group,ratingStr);
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

    private static InterestGroupRatingEvidence buildEvidence(InterestGroup group,String ratingStr)
    {
         int rating = Integer.parseInt(ratingStr);
         InterestGroupRatingEvidence ret = new InterestGroupRatingEvidence(group,ratingStr);
        return ret;
    }

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
        for (InterestGroup interestGroup : categoryNameToId.reverseValues()) {
            Set<VoteSmartIssueCategory> voteSmartIssueCategories = categoryNameToId.forwardLookup(interestGroup);
            List<VoteSmartIssueCategory> vc = CollectionUtilities.sortedCollection(voteSmartIssueCategories);
            System.out.print(interestGroup);
            System.out.print("=>");
            for (VoteSmartIssueCategory vcat : vc) {
                System.out.print(vcat);
                System.out.print(",");
            }
            System.out.println();
        }
    }


}
