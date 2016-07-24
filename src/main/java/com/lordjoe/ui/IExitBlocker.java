package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IExitBlocker
 * This interface may be called by an application which wants to exit
 * Implementers
 * @author Steve Lewis
 * @date Sep 14, 2005
 */
public interface IExitBlocker
{
    /**
     * null allows the exit non-null expoains why the exit is blocked
     * @return as above
     */
    public String reasonForBlockingExit();
}
