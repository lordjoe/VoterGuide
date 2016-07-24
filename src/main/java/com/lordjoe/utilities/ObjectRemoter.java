package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

/**
 * Class keeps track of objects exported to RMI
 * Allos app to unexport all objects
 * @author Steve Lewis
 */
public abstract class ObjectRemoter {
    protected static List gExportedObjects = new ArrayList();

    public static void exportObject(Remote in) throws RemoteException {
        UnicastRemoteObject.exportObject(in);
        gExportedObjects.add(in);
    }

    public static void unexportObject(Remote in) throws RemoteException {
        if (gExportedObjects.contains(in)) {
            UnicastRemoteObject.unexportObject(in, true);
            gExportedObjects.remove(in);
        }
    }

    public static void unexportAll() {
        Remote[] items = new Remote[gExportedObjects.size()];
        gExportedObjects.toArray(items);
        for (int i = 0; i < items.length; i++) {
            try {
                unexportObject(items[i]);
            } catch (RemoteException ex) {
            }
        }
    }
}
