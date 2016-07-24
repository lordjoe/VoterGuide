package com.lordjoe.voter.votesmart;

import com.google.appengine.api.datastore.Key;
import com.lordjoe.voter.*;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.NpatClass;
import org.votesmart.data.Npat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lordjoe.voter.votesmart.VoteSmartUtilities.isEmptyOrNull;

/**
 * com.lordjoe.voter.votesmart.VoteSmartQuestionaire
 * User: Steve
 * Date: 7/2/2016
 */
public class VoteSmartQuestionaire {
    private static NpatClass nc;

    private static Map<Politician, Map<VoteSmartIssueCategory,List<QuestionnaireEvidence>>>  questionnaireEvidence = new HashMap<Politician, Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>>>();

    public static void initIfNeeded() {
        if (nc != null)
            return;
        try {
            nc = new NpatClass();
            List<Candidate> candidates = Candidates.getCandidates();
            int numberCandidates = candidates.size();
            int numberReturned = 0;
            int numberNotReturned = 0;

            for (Candidate c : candidates) {
                try {
                    Politician pol = c.pol;
                    //           if(pol.getVoteSmartId() != 49717)
                    //                continue; // drop all candidates BUT  Ruby Woolridge

                    Npat npat = nc.getNpat(pol.getVoteSmartIdStr());
                    if (npat == null)
                        continue;
                    if (npat.section != null) {
                        numberReturned++;
                        //       System.out.println("Returned " + pol);;
                        processNPat(pol, npat);
                    } else {
                        //System.out.println("Not returned " + pol);;
                        numberNotReturned++;
                        continue;
                    }
                } catch (VoteSmartException e) {
                    continue;

                } catch (VoteSmartErrorException e) {
                    continue;
                }
            }
            System.out.println("returned " + numberReturned + "out of " + numberCandidates);
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        }
    }

    public static  Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> getEvidence(Politician pol)  {
        initIfNeeded();
        return questionnaireEvidence.get(pol);
    }

    private static void processNPat(Politician pol, Npat npat) {
        Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> evidence = new HashMap<VoteSmartIssueCategory, List<QuestionnaireEvidence>>();
        for (Npat.Section section : npat.section) {
            processNPatSection(pol, evidence, npat, section);

        }
        questionnaireEvidence.put(pol,evidence) ;
    }

    private static void processNPatSection(Politician pol, Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> evidence, Npat npat, Npat.Section section) {
        if (section == null)
            return;
        if (section.row == null)
            return;
        for (Npat.Section.Row row : section.row) {
            processNPatSectionRow(pol, evidence, npat, section, row);

        }
    }

    private static void processNPatSectionRow(Politician pol, Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> evidence, Npat npat, Npat.Section section, Npat.Section.Row row) {
        if (row.row != null) {
            for (Npat.Section.Row row1 : row.row) {
                processNPatSectionSubRow(pol, evidence, npat, section, row, row1);
            }
        } else {

            String answerText = row.answerText;
            String optionText = row.optionText;
            String rowText = row.rowText;
            String rowType = row.rowType;
            processNPatResponse(pol, evidence, section, answerText, optionText, rowText, rowType);
        }
    }

    private static void processNPatResponse(Politician pol, Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> evidence, Npat.Section section, String answerText, String optionText, String rowText, String rowType) {
        String answer = getAnswer(answerText, optionText);
        if (answer == null)
            return; // no answer
        String name = section.name;
        VoteSmartIssueCategory category = categoryFromName(name);
        if (category == null)
            return;
        String question = rowText;
        List<QuestionnaireEvidence> questionnaireEvidences = evidence.get(category);
        if (questionnaireEvidences == null) {
            questionnaireEvidences = new ArrayList<QuestionnaireEvidence>();
            evidence.put(category, questionnaireEvidences);
        }
        Key key = GoogleDatabase.createKey(question,NPRQuestionnaireEvidence.class,pol) ;
        questionnaireEvidences.add(new NPRQuestionnaireEvidence(question, answer,key));
    }

    public static VoteSmartIssueCategory categoryFromName(String name) {
        try {
            VoteSmartIssueCategory category = VoteSmartIssueCategory.getByName(name);
            return category;
        } catch (IllegalArgumentException e) {
        }
        if (name.equalsIgnoreCase("Budget"))
            return VoteSmartIssueCategory.Government_Operations;
        if (name.equalsIgnoreCase("Economy"))
            return VoteSmartIssueCategory.Business_and_Consumers;
        if (name.equalsIgnoreCase("Campaign Finance"))
            return VoteSmartIssueCategory.Campaign_Finance_and_Elections;
        if (name.equalsIgnoreCase("Health Care"))
            return VoteSmartIssueCategory.Health_and_Health_Care;
        if (name.equalsIgnoreCase("Marriage"))
            return VoteSmartIssueCategory.Marriage_Family_and_Children;
        if (name.equalsIgnoreCase("National Security"))
            return VoteSmartIssueCategory.Defense;
        if (name.equalsIgnoreCase("Social Security"))
            return VoteSmartIssueCategory.Senior_Citizens;
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    public static String getAnswer(String answerText, String optionText) {
        if (isEmptyOrNull(answerText) && isEmptyOrNull(optionText))
            return null; // no amswer
        if (!isEmptyOrNull(answerText))
            return answerText;
        return optionText;

    }

    private static void processNPatSectionSubRow(Politician pol, Map<VoteSmartIssueCategory, List<QuestionnaireEvidence>> evidence, Npat npat, Npat.Section section, Npat.Section.Row superRow, Npat.Section.Row row) {

        if (row.row != null) {
            for (Npat.Section.Row row1 : row.row) {
                processNPatSectionRow(pol, evidence, npat, section, row1);

            }
        } else {
            String answerText = row.answerText;
            String optionText = row.optionText;
            String rowText = row.rowText;
            String rowType = row.rowType;
            processNPatResponse(pol, evidence, section, answerText, optionText, rowText, rowType);
        }

    }

}
