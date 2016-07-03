package com.lordjoe.voter;

/**
 * com.lordjoe.voter.votesmart.QuestionairEvidence
 *
 * User: Steve
 * Date: 7/1/2016
 */
public class QuestionnaireEvidence extends Evidence {


    private final String question ;
    private final String answer;


    public QuestionnaireEvidence(String question, String answer) {
        this.question = question;
        this.answer = answer;
        // todo all wrone we need to think about questionairs
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
