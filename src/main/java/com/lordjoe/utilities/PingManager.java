package com.lordjoe.utilities;

import java.rmi.*;

public class PingManager {
    protected static long gLastPing = System.currentTimeMillis();
    protected static final String PING_RESPONSE = Util.getIPAddress();

    /**
     * responds with the System name to a ping
     */
    public synchronized static String ping() {
        gLastPing = System.currentTimeMillis();
        //   System.out.println("Pinged at " + gLastPing);
        return (PING_RESPONSE);
    }

    public synchronized static long getLastPingTime() {
        return (gLastPing);
    }

}
