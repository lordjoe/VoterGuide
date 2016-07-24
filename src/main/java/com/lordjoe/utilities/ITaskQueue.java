package com.lordjoe.utilities;

/**{ class
 @name TaskQueue
 @function keep a queue of Tasks to run - also a runnable to run those tasks
 }*/
public interface ITaskQueue {

    /**
     * Executes a given Runnable on a thread in the pool, block if needed.
     * @param r non-null runnable
     */
    public void runIt(Runnable r);

    /**
     * Executes a given Runnable on a thread in the pool, block if needed.
     * @param r non-null runnable
     */
    public ITaskWatcher runItWithWatch(Runnable r);
}
