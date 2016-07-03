package com.lordjoe.voter.votesmart;

import com.lordjoe.utilities.CollectionUtilities;
import com.lordjoe.utilities.DualList;
import com.lordjoe.utilities.DualMap;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.VotesClass;
import org.votesmart.data.CategoryMin;
import org.votesmart.data.VotesCategories;

import java.util.*;

/**
 * com.lordjoe.voter.votesmart.VoteSmartVotes
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteSmartVotes {

    private static DualMap<String, Integer> categoryNameToId = new DualMap<>(String.class, Integer.class);
    private static Set<String> categoryNames = new HashSet<>();

    protected static void initIfNeeded() {
        int end = Integer.parseInt(MyVoteSmart.CURRENT_YEAR);
        int start = Integer.parseInt(MyVoteSmart.FIRST_INTERESTING_YEAR);
        if (categoryNameToId.size() == 0) {
            try {
                VotesClass vc = new VotesClass();
                for (int i = start; i <= end; i++) {
                    VotesCategories categories = null;
                    try {
                        categories = vc.getCategories(Integer.toString(i));
                        for (CategoryMin categoryMin : categories.category) {
                            Integer categoryId = Integer.parseInt(categoryMin.categoryId);
                            String name = categoryMin.name;
                            if (categoryNames.contains(name)) {
                                Integer id = categoryNameToId.forwardLookup(name);
                                if (!categoryId.equals(id))
                                    throw new IllegalStateException("problem"); // ToDo change
                            } else {
                                categoryNames.add(name);
                            }
                            categoryNameToId.register(name, categoryId);
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
        }
    }

    public static List<String> getCatgoryNames() {
          return   CollectionUtilities.sortedCollection(categoryNames);
    }

    public static Integer getCatgoryId(String name) {
        return categoryNameToId.forwardLookup(name);
    }
    public static String getCatgoryName(Integer id) {
        return categoryNameToId.reverseLookup(id);
    }


    /**
     * write code for the enums in VoteSmartIssueCategory to stdout
     * should only be run every few years
     */
    public static void categoriesAsCode() {
        List<String> catgoryNames = VoteSmartVotes.getCatgoryNames();
        for (String catgoryName : catgoryNames) {

            String cateetgoryId = catgoryName.replace(" ","_").replace(",","").replace("-","");
            System.out.print(cateetgoryId);
            System.out.print("(\"");
            System.out.print(catgoryName);
            System.out.print("\",");
            System.out.print(VoteSmartVotes.getCatgoryId(catgoryName));
            System.out.println("),");
        }
    }

}
