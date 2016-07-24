package com.lordjoe.utilities;


/**
 * interface implemented by a nested exception to return the original stack
 * trace
 */
public interface IStackTraceable {

    public String[] getStackTraceElements();

    public void setStackTraceElements(String[] in);

    /**
     * return the stack trace - if there is a nested exception
     * this is the nested stack trace
     * @return non-null trace
     */
    public String getStackTraceString();

    /**
     * return the message of all causes
     * this is the nested stack trace
     * @return non-null string
     */
    public String getCummulativeMessages();

    /**
     * return fir st message representing teh original cause
     * @return non-null possibly empty string
     */
    public String getOriginalMessage();
}
