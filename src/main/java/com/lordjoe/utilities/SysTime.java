package com.lordjoe.utilities;

/**
 * since reading the System time is frequently inefficient we use one
 * instance to keep a record good to the second or so
 * @author Steve Lewis
 */
public class SysTime implements Runnable {
    public static final int TEST_INTERVAL = 1 * 1000; // One sec
    protected static long gTic;

    public static synchronized long getTic() {
        return (gTic);
    }

    protected static synchronized void setTic(long in) {
        gTic = in;
    }

    protected static SysTime gInstance = new SysTime();


    private SysTime() {
        setTic(System.currentTimeMillis());
        Thread Thr = new Thread(this, "System Time");
        Thr.setDaemon(true);
        Thr.start();
    }

    public void run() {
        while (true) {
            ThreadUtilities.waitFor(1000);
            setTic(System.currentTimeMillis());
        }
    }


//- *******************
//- End Class SysTime
}
