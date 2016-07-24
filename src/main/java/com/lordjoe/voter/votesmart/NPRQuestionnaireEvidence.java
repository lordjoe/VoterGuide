package com.lordjoe.voter.votesmart;

import com.google.appengine.api.datastore.Key;
import com.lordjoe.voter.QuestionnaireEvidence;

/**
 * com.lordjoe.voter.votesmart.NPRquestionnaireEvidence
 * User: Steve
 * Date: 7/2/2016
 */
public class NPRQuestionnaireEvidence extends QuestionnaireEvidence {
    public NPRQuestionnaireEvidence(String question, String answer,Key key) {
        super(question, answer,key);
    }
}
