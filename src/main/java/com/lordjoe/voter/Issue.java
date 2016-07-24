package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.lordjoe.voter.votesmart.GoogleDatabase;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * com.lordjoe.voter.Issue
 * User: Steve
 * Date: 6/23/2016
 */
public class Issue  extends PersistentVoterItem {

    private static Map<String, Issue> byText = new HashMap<String, Issue>();


    public static Issue getPosition(IssueCategory issue, String text) {
        Issue ret = byText.get(text);
        if (ret == null) {
            ret = new Issue(issue, text);
        }
        return ret;
    }

    public final IssueCategory category;
    public final String question;
    private final Set<Evidence> evidencs = new HashSet<Evidence>();
    private final Set<EvidenceSource> sources = new HashSet<EvidenceSource>();
    // relevant interest groups with weight -1..1 -1 low rating is good 1 high rating good
    private final Map<InterestGroup, Double> importantInterestGroups = new HashMap<InterestGroup, Double>();
    // relevant interest groups with weight -1..1 -1 low rating is good 1 high rating good
    private final Map<Bill, Double> importantBills = new HashMap<Bill, Double>();


    private Issue(IssueCategory category, String question) {
        super(GoogleDatabase.createKey(question,Issue.class));
        this.category = category;
        this.question = question;
        byText.put(question, this);
        Issues.registerIssue(category, this);

    }



    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {

    }

}
