package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

/**
 * Collection of Utilities for dealing with threads
 * @author Steve Lewis
 */
public class ThreadUtilities {
    public static final long WAIT_INCR = 10 * TimeDuration.ONE_SECOND;

    /**
     * waits forever
     * @throws com.lordjoe.exceptions.ThreadInterruptedException on interruption
     */
    public static void waitForever() {
        while (true) {
            waitFor(WAIT_INCR);
        }
    }
    /**
      * waits forever
      * @throws com.lordjoe.exceptions.ThreadInterruptedException on interruption
      */
     public static void waitForObject(Object o ) {
        waitForObject(o,Long.MAX_VALUE);
     }
    /**
      * waits forever
      * @throws com.lordjoe.exceptions.ThreadInterruptedException on interruption
      */
     public static void waitForObject(Object o ,long wait) {
         synchronized (o)   {
             try {
                 o.wait(wait);
             } catch (InterruptedException e) {
                 throw new ThreadInterruptedException();
             }
         }
     }

    public static void guaranteeNoInterrupt()
    {
        if(Thread.currentThread().isInterrupted())
            throw new IllegalStateException("problem");
    }

    /**
     * waits for millis milliseconds then returns
     * @param millis positive milliseconds to wait
     * @throws com.lordjoe.exceptions.ThreadInterruptedException on interruption
     */
    public static void waitFor(long millis) {
        if (millis < WAIT_INCR) {
            doWaitFor(millis);
        } else {
            doMultiWaitFor(millis);
        }
    }

    /**
     * waits for millis milliseconds then returns
     * @param millis positive milliseconds to wait
     * @throws com.lordjoe.exceptions.ThreadInterruptedException on interruption
     */
    protected static void doWaitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            throw new ThreadInterruptedException("Watch unexpectedly interrupted");
        }
    }

    /**
     * waits for millis milliseconds then returns
     * @param millis positive milliseconds to wait
     * @throws  ThreadInterruptedException on interruption
     */
    protected static void doMultiWaitFor(long millis) {
        long Stop = SysTime.getTic() + millis;
        while (SysTime.getTic() < Stop) {
            doWaitFor(WAIT_INCR);
        }
    }

    /**
     * waits for millis milliseconds then returns
     * in this case the current thread will release locks on lock and
     * wait and will be restarted by notification
     * @param millis positive milliseconds to wait
     * @throws  ThreadInterruptedException on interruption
     */
    public static void waitFor(long millis, Object lock) {
        try {
            lock.wait(millis);
        } catch (InterruptedException ex) {
            throw new ThreadInterruptedException("Watch unexpectedly interrupted");
        }
    }

    /**
     * waits for a random interval which is at least MinWait
     * evenly distributes between MinWait and MinWait + AverageWait
     * @param MinWait positive milliseconds minimum wait
     * @param AverageWait positive milliseconds minimum wait
     * @throws  ThreadInterruptedException on interruption
     */
    public static void randomSleep(int MinWait, int AverageWait) {
        ThreadUtilities.waitFor(MinWait + Util.randomInt(AverageWait));
    }
}
