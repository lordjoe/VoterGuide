package com.lordjoe.utilities;

import java.rmi.*;

public interface IPingable extends Remote {
    /**
     * responds with the System name to a ping
     */
    public String ping() throws RemoteException;

}
