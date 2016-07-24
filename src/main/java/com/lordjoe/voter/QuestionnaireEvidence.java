package com.lordjoe.voter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.sun.javafx.beans.annotations.NonNull;

/**
 * com.lordjoe.voter.votesmart.QuestionairEvidence
 *
 * User: Steve
 * Date: 7/1/2016
 */
public class QuestionnaireEvidence extends Evidence {


    private final String question ;
    private final String answer;


    public QuestionnaireEvidence(String question, String answer,Key key) {
        super(key);
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

    @Override
    public Entity asEntity() {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    @Override
    public void populateFromEntity(@NonNull Entity e) {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

}
