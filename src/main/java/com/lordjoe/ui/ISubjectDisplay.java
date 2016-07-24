package com.lordjoe.ui;

import com.lordjoe.utilities.*;
import com.lordjoe.ui.general.*;

import java.awt.*;
import java.util.*;

/**
 * com.lordjoe.ui.ISubjectDisplay
 *
 * @author Steve Lewis
 * @date Sep 26, 2008
 */
public interface ISubjectDisplay<T>
{
    public static ISubjectDisplay[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ISubjectDisplay.class;

    /**
     * return the subject for this display
     * @return  possibly null subject
     */
    public T getSubject();

    /**
     * set a subject for this display
     * @param subject usually non-null subject
     */
     public void setSubject(T subject);

    /**
     * return the allowed class for subjects of this display
     * @return
     */
    public Class getSubjectClass();

    /**
     * Thread safe call to make the models consistent with the
     * subject
     * @return usually non-null Subject
     */
    public void reconcile();

    /**
     * Implementing code for reconcile - must be run in the
     * swing thread
     * @return usually non-null Subject
     */
    public void swingReconcile();

    /**
     * useful for debugging this shows a dialog of conents of the system
     */
    public void showInfoDialog();


}
