package com.lordjoe.ui;

import com.lordjoe.lang.*;

/**
 * com.lordjoe.ui.IProgressMonitor
 *  General interface for an object which can monitor
 * progress of an operation
 * @author Steve Lewis
 * @date Oct 25, 2005
 */
public interface IProgressMonitor
{
    public static final IProgressMonitor NULL_MONITOR =
            new NullprogressMonitor();
    /**
     * set the display color
     * @param c
     */
    void setProgressState(OperationStateEnum c);

    /**
     * set the maximum value
     * @param c value
     */
    void setProgressMax(int c);

    /**
     * set the current value note
     * Integer.MAX_VALUE means done
     * @param c
     */
    void setProgress(int c);

    /**
     * set the displayed text
     * @param c
     */
    void setProgressText(String c);

    /**
     * default null implementation
     */
    public class NullprogressMonitor implements IProgressMonitor
    {
        private NullprogressMonitor() {}
        public void setProgressState(OperationStateEnum c) {}
        public void setProgressMax(int c){}
        public void setProgress(int c){}
        public void setProgressText(String c){}
    }
}
