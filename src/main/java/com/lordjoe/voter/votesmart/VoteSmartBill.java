package com.lordjoe.voter.votesmart;

import java.util.*;

import static javafx.scene.input.KeyCode.H;

/**
 * com.lordjoe.voter.votesmart.VoteSmartBill
 * User: Steve
 * Date: 7/3/2016
 */
public class VoteSmartBill {

    private static Map<Integer, VoteSmartBill> idToBill = new HashMap<>();
    private Set<VoteSmartIssueCategory> issues = new HashSet<>();
    private final Integer id;
    private final String title;
    private List<VoteSmartRollCall> rollCalls;

    public static synchronized VoteSmartBill getBill(Integer id, String title) {
        VoteSmartBill ret = idToBill.get(id);
        if (ret == null) {
            ret = new VoteSmartBill(id, title);
            idToBill.put(id, ret);
        } else {
            if (!ret.getTitle().equals(title)) {
                VoteSmartVotes.doFillInBill(ret);  // huh

                throw new IllegalArgumentException("wromg title");
            }
        }
        return ret;
    }

    private VoteSmartBill(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addCategory(VoteSmartIssueCategory category) {
        issues.add(category);
    }

    public boolean inCategory(VoteSmartIssueCategory category) {
        return issues.contains(category);
    }

    public List<VoteSmartIssueCategory> getIssues() {
        List<VoteSmartIssueCategory> ret = new ArrayList<>(issues);
        Collections.sort(ret);
        return ret;
    }

    public Integer getId() {
        return id;
    }

    public String getIdStr() {
        return Integer.toString(id);
    }

    public String getTitle() {
        return title;
    }

    public List<VoteSmartRollCall> getRollCalls() {
        return rollCalls;
    }

    public void setRollCalls(List<VoteSmartRollCall> rollCalls) {
        this.rollCalls = rollCalls;
    }

    public boolean hasRollCall() {
        return rollCalls != null && rollCalls.size() > 0;
    }

    @Override
    public String toString() {
        return title;
    }
}
