package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.IDisplay
 *
 * @author slewis
 * @date Feb 24, 2005
 */
public interface IDisplay
{
    public static final Class THIS_CLASS = IDisplay.class;
    public static final IDisplay EMPTY_ARRAY[] = {};
    /**
      * return the class of the represented object
      * @return non-null class 
      */
     public Class getSubjectClass();

    /**
      * return the represented object
      * @return usually nopn-null Subject
      */
     public Object getSubjectObject();
    /**
      * set the represented object
      * @param usually nopn-null Subject
      */
     public void setSubjectObject(Object subject);

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

}