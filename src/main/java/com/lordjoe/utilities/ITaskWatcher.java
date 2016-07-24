package com.lordjoe.utilities;


public interface ITaskWatcher {
    /**
     * return when the task has completed -
     * after Timeout millisec thtow
     * TaskTImeoutException
     */
    public void waitCompletion(Runnable r, int Timeout);
}