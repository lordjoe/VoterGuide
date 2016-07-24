package com.lordjoe.utilities;

/**
 * ILoanedObject - token used to represent a loan
 * @see DefaultLoadedObject for an implementation
 * @author Steve Lewis
 */
public interface ILoanedObject {
    /**
     * This is called upon timeout - i.e the loan has expired
     */
    public void onTimeout();

    /**
     * call at the start of a loan to get the clock ticking
     * @param millisec number millisec to wait non-negative
     * @param action non-null runnable to run if the loan expires
     */
    public void startTimeout(int millisec, Runnable action);

    /**
     * call at the start of a loan to get the clock ticking
     * @param millisec number millisec to wait non-negative
     */
    public void startTimeout(int millisec);

    /**
     * this is called to stop the cloak i.e. the loan
     * is returned
     */
    public void stopTimeout();

}
