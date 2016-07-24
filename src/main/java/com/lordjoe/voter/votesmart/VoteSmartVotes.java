package com.lordjoe.voter.votesmart;

import com.lordjoe.utilities.DualList;
import com.lordjoe.voter.Politician;
import com.lordjoe.voter.Politicians;
import com.lordjoe.voter.VotePosition;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.VotesClass;
import org.votesmart.data.*;

import java.util.*;

/**
 * com.lordjoe.voter.votesmart.VoteSmartVotes
 * User: Steve
 * Date: 7/1/2016
 */
public class VoteSmartVotes {

    private static VotesClass vc;
    private static DualList<VoteSmartIssueCategory, VoteSmartBill> billsByCategory = new DualList<VoteSmartIssueCategory, VoteSmartBill>(VoteSmartIssueCategory.class, VoteSmartBill.class);
    private static Map<Integer, VoteSmartBill> billById = new HashMap<Integer, VoteSmartBill>();
    private static Map<Integer, VoteSmartBill> rollCallBillById = new HashMap<Integer, VoteSmartBill>();
    private static Map<Integer, VoteSmartBill> badBillById = new HashMap<Integer, VoteSmartBill>();

    protected static void initIfNeeded() {
        if (vc != null)
            return;

        try {
            vc = new VotesClass();
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);
        }
        for (VoteSmartIssueCategory category : VoteSmartIssueCategory.values()) {
            Set<VoteSmartBill> bills = getBills(category);
            for (VoteSmartBill bill : bills) {
                billsByCategory.register(category, bill);
            }

        }
    }

    private static Set<VoteSmartBill> getBills(VoteSmartIssueCategory category) {
        Set<VoteSmartBill> ret = new HashSet<VoteSmartBill>();
        int end = Integer.parseInt(MyVoteSmart.CURRENT_YEAR);
        int start = Integer.parseInt(MyVoteSmart.FIRST_INTERESTING_YEAR);
        for (int i = start; i < end; i++) {
            String year = Integer.toString(i);
            Bills bills = null;
            try {
                bills = vc.getBillsByCategoryYearState(category.getIdString(), year);
            } catch (VoteSmartException e) {
                continue;
            } catch (VoteSmartErrorException e) {
                continue;
            }
            for (BillMin billMin : bills.bill) {
                String billId = billMin.billId;
                String title = billMin.title;
                if (VoteSmartUtilities.isEmptyOrNull(title))
                    continue;
                String number = billMin.billNumber;
                String type = billMin.type;
                if (!type.equals("Legislation"))
                    continue;
                int id = Integer.parseInt(billId);
                if (id == 3189)
                    MyVoteSmart.breakHere();
                try {
                    VoteSmartBill bill = VoteSmartBill.getBill(id, title);
                    bill.addCategory(category);
                    fillInBill(bill);
                    if (bill.hasRollCall()) {
                        ret.add(bill);
                        rollCallBillById.put(id, bill);
                    }
                    billById.put(id, bill);
                } catch (Exception e) {
                    badBillById.put(id, billById.get(id));
                    continue; // whi are we having issues with bills with the same id

                }
            }
        }
        return ret;

    }

    protected static void fillInBill(VoteSmartBill bill) {
        List<VoteSmartRollCall> rollCalls = bill.getRollCalls();
        if (rollCalls != null)
            return; // already done
        doFillInBill(bill);
    }

    protected static void doFillInBill(VoteSmartBill bill) {
        List<VoteSmartRollCall> rollCalls;
        rollCalls = new ArrayList<VoteSmartRollCall>();
        try {
            Bill bill1 = vc.getBill(bill.getIdStr());
            for (Bill.Actions.Action action : bill1.actions.action) {
                String actionId = action.actionId;
                if (VoteSmartUtilities.isEmptyOrNull(action.rollNumber))
                    continue;   // no vote
                VoteSmartRollCall rollCall = new VoteSmartRollCall(bill, Integer.parseInt(actionId));
                fillInRollCall(rollCall);

                rollCalls.add(rollCall);
            }
            bill.setRollCalls(rollCalls);
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }
    }

    private static void fillInRollCall(VoteSmartRollCall rollCall) {
        try {
            BillActionVotes billActionVotes = vc.getBillActionVotes(rollCall.getIdStr());
            for (BillActionVotes.Vote vote : billActionVotes.vote) {
                String action = vote.action;
                int id = Integer.parseInt(vote.candidateId);
                Politician pol = Politicians.getByID(id);
                 if(pol == null)
                     continue;
                VotePosition position = fromAction(action);
                if(position == null)
                    continue;
                rollCall.addVote(pol,position);
            }
            int ayes = rollCall.getAyes();
            int nays = rollCall.getNay();
            int abstains = rollCall.getAbstains();
         //    System.out.println(rollCall.bill + " " + ayes + " " + nays + " " + abstains);

        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }

    }

    private static VotePosition fromAction(String action) {
        try {
            VotePosition votePosition = VotePosition.valueOf(action);
            return votePosition;
        } catch (IllegalArgumentException e) {

        }
        if("Pair Nay".equals(action))
            return VotePosition.Nay;
        if("Pair Aye".equals(action))
            return VotePosition.Yea;
        if("Pair Yea".equals(action))
            return VotePosition.Yea;
        if("NA".equals(action))
            return null;
        if("Did Not Vote".equals(action))
            return VotePosition.Abstain;
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

// private static Set<String> categoryNames = new HashSet<>();
//    private static void readCategories() {
//        int end = Integer.parseInt(MyVoteSmart.CURRENT_YEAR);
//        int start = Integer.parseInt(MyVoteSmart.FIRST_INTERESTING_YEAR);
//        if (categoryNameToId.size() == 0) {
//            for (int i = start; i <= end; i++) {
//                VotesCategories categories = null;
//                try {
//                    categories = vc.getCategories(Integer.toString(i));
//                    for (CategoryMin categoryMin : categories.category) {
//                        Integer categoryId = Integer.parseInt(categoryMin.categoryId);
//                        String name = categoryMin.name;
//                        if (categoryNames.contains(name)) {
//                            Integer id = categoryNameToId.forwardLookup(name);
//                            if (!categoryId.equals(id))
//                                throw new IllegalStateException("problem"); // ToDo change
//                        } else {
//                            categoryNames.add(name);
//                        }
//                        categoryNameToId.register(name, categoryId);
//                    }
//                } catch (VoteSmartException e) {
//                    continue;
//
//                } catch (VoteSmartErrorException e) {
//                    continue;
//
//                }
//
//            }
//        }
//    }
//
//    public static List<String> getCatgoryNames() {
//        return CollectionUtilities.sortedCollection(categoryNames);
//    }
//
//    public static Integer getCatgoryId(String name) {
//        return categoryNameToId.forwardLookup(name);
//    }
//
//    public static String getCatgoryName(Integer id) {
//        return categoryNameToId.reverseLookup(id);
//    }
//
//
//    /**
//     * write code for the enums in VoteSmartIssueCategory to stdout
//     * should only be run every few years
//     */
//    public static void categoriesAsCode() {
//        List<String> catgoryNames = VoteSmartVotes.getCatgoryNames();
//        for (String catgoryName : catgoryNames) {
//
//            String cateetgoryId = catgoryName.replace(" ", "_").replace(",", "").replace("-", "");
//            System.out.print(cateetgoryId);
//            System.out.print("(\"");
//            System.out.print(catgoryName);
//            System.out.print("\",");
//            System.out.print(VoteSmartVotes.getCatgoryId(catgoryName));
//            System.out.println("),");
//        }
//    }
//
}
