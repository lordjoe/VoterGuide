package com.lordjoe.voter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * com.lordjoe.voter.Issue
 * User: Steve
 * Date: 6/23/2016
 */
public class Issue {
    public final IssueCategory category;
    public final String question;
    private final Set<Evidence>  evidencs = new HashSet<>();
    private final Set<EvidenceSource> sources = new HashSet<>();
    // relevant interest groups with weight -1..1 -1 low rating is good 1 high rating good
    private final Map<InterestGroup,Double> importantInterestGroups = new HashMap<>();
    // relevant interest groups with weight -1..1 -1 low rating is good 1 high rating good
    private final Map<Bill,Double> importantBills = new HashMap<>();


    public Issue(IssueCategory category, String question) {
        this.category = category;
        this.question = question;
    }
}
