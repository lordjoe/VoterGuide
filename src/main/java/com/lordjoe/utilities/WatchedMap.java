
package com.lordjoe.utilities;

import java.util.*;
import java.lang.reflect.*;

/**
 * A map holding objects which have a timeout - if not
 * accessed within the timeout interval, the object is dropped
 * from the map and presumably garbage collected
 * com.lordjoe.Utilities.WatchedMap
 * @author Steve Lewis
 * @see IObjectDescructor
 */
public class WatchedMap extends HashMap implements Runnable {
    public static int MINIMUM_TIMEOUT = 1000; // 1 sec

    //- *******************
    //- Fields
    private IIntegerValue m_TimeOut;
    private Thread m_Watcher;
    private IObjectDestructor m_action;

    //- *******************
    //- Methods
    /** constructor
     @name WatchedMap
     @function Constructor of WatchedMap
     @param timeout itimeout millisec
     */
    public WatchedMap(int timeout) {
        super();
        timeout = Math.max(timeout, MINIMUM_TIMEOUT);
        setTimeout(IntegerValueFactory.buildValue(timeout));
        m_Watcher = new Thread(this, "Map Watcher");
        m_Watcher.setDaemon(true);
        m_Watcher.start();
    }

    public WatchedMap(IIntegerValue timeout) {
        super();
        setTimeout(timeout);
        m_Watcher = new Thread(this, "Map Watcher");
        m_Watcher.setDaemon(true);
        m_Watcher.start();
    }

    public IIntegerValue getTimeout() {
        return (m_TimeOut);
    }

    public void setTimeout(IIntegerValue in) {
        m_TimeOut = in;
    }

    public void run() {
        try {
            while (true) {
                int value = m_TimeOut.intValue() / 2;
                Thread.sleep(value);
                weedObjects();
            }
        } catch (InterruptedException ex) {
        }
    }

    protected void weedObjects() {
        List RemoveHolder = null;
        List ActionHolder = null;
        synchronized (this) {
            long TestTime = System.currentTimeMillis();
            Iterator it = keySet().iterator();
            RemoveHolder = new ArrayList(size() / 4);
            boolean first = true;
            while (it.hasNext()) {
                Object key = it.next();
                WatchedObject test = (WatchedObject) baseGet(key);
                if (test != null) {
                    long lease = test.getLastAccess() + m_TimeOut.intValue();
                    long del = TestTime - lease;
                    if (del > 0)
                        RemoveHolder.add(key);
                }
            }
            if (m_action != null)
                ActionHolder = new ArrayList(RemoveHolder.size());
            for (int i = 0; i < RemoveHolder.size(); i++) {
                Object key = RemoveHolder.get(i);
                WatchedObject test = (WatchedObject) baseGet(key);
                if (m_action != null) {
                    ActionHolder.add(test.unmarkingGetData());
                }
                remove(key);
            }
        } // end synchronized block
        if (m_action != null) {
            for (int i = 0; i < ActionHolder.size(); i++) {
                Object data = ActionHolder.get(i);
                m_action.onObjectClose(data);
            }
        }

    }

    public synchronized void setAction(IObjectDestructor action) {
        m_action = action;
    }

    public synchronized IObjectDestructor getAction() {
        return (m_action);
    }

    public synchronized Object put(Object key, Object data) {
        return (super.put(key, new WatchedObject(data)));
    }

    public synchronized Object get(Object key) {
        Object ret = super.get(key);
        if (ret == null)
            return (null);
        WatchedObject RealGet = (WatchedObject) ret;
        return (RealGet.getData());
    }

    /**
     * return the enclosed Objects as an r-array of the type
     * used in template.
     * if template.length == size() then template is returned
     * *return non-null array as above
     */
    public synchronized Object[] values(Object[] template) {
        Object[] ret = template;
        Collection items = this.values();
        if (ret.length != items.size())
            ret = (Object[]) Array.newInstance(template.getClass().getComponentType(), items.size());
        Iterator it = items.iterator();
        int index = 0;
        while (it.hasNext()) {
            WatchedObject RealGet = (WatchedObject) it.next();
            ret[index++] = RealGet.getData();
        }
        return (ret);
    }


    /**
     * return the enclosed object without
     * marking access - used internally
     */
    protected synchronized Object baseGet(Object key) {
        return (super.get(key));
    }

    /**
     * internal class which holds an Object and marks the time
     * when it is last accessed
     */
    protected class WatchedObject {
        private long m_LastAccess;
        private Object m_Data;

        /**
         * @param Data non-null Object to store
         */
        protected WatchedObject(Object Data) {
            m_LastAccess = System.currentTimeMillis();
            m_Data = Data;
        }

        /**
         * get the last tie the object was accessed
         * this is used to test for timeout
         * @return positive long representing time in millisec
         */
        public long getLastAccess() {
            return (m_LastAccess);
        }

        /**
         * get the data and update the time of access
         * @return non-null data
         */
        public Object getData() {
            m_LastAccess = System.currentTimeMillis();
            return (m_Data);
        }

        /**
         * internal method to fetch data without marking time
         * @return non-null data
         */
        protected Object unmarkingGetData() {
            return (m_Data);
        }
        //- *******************
        //- End InnerClass WatchedObject
    }

    // Test Section

    protected static class TestDestructor implements IObjectDestructor {
        public void onObjectClose(Object Target) {
            System.out.println("Destroying " + Target.toString()); // test
        }

        //- *******************
        //- End InnerClass TestDestructor
    }

    protected static class TestRunner implements Runnable {
        public static final int MAX_COUNT = 100;
        public static final int N_EXPIRE = 10;
        private WatchedMap m_Map;
        private int m_renew;
        private int m_expire;

        protected TestRunner(WatchedMap m) {
            m_Map = m;
        }

        public void run() {
            long start = System.currentTimeMillis();
            long interval = m_Map.getTimeout().intValue() / 6;
            long end = start + MAX_COUNT * interval;
            try {
                while (System.currentTimeMillis() < end) {
                    Thread.sleep(interval);
                    synchronized (m_Map) {
                        addObjects();
                        accessObjects();
                    }
                }
            } catch (InterruptedException ex) {
            }
        }

        protected void addObjects() {
            int NExpire = 0;
            int NRenew = 0;
            Iterator it = m_Map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (key.toString().startsWith("Renew"))
                    NRenew++;
                if (key.toString().startsWith("Expire"))
                    NExpire++;
            }
            for (int i = NExpire; i < N_EXPIRE; i++) {
                String added = "Expire" + m_expire++;
                m_Map.put(added, added);
            //    System.out.println("Adding " + added); // test
            }
            for (int i = NRenew; i < N_EXPIRE; i++) {
                String added = "Renew" + m_renew++;
                m_Map.put(added, added);
           //     System.out.println("Adding " + added); // test
            }
        }

        protected void accessObjects() {
            Iterator it = m_Map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (key.toString().startsWith("Renew"))
                    m_Map.get(key);
            }
        }


        //- *******************
        //- End InnerClass TestRunner
    }

    // Makes a WatchedMap
    // Adds 10 Renew able String
    // and keeps adding new renewable strings which
    // keep expiring and being recreated
    //
    public static void main(String[] args) {
        WatchedMap test = new WatchedMap(2000);
        test.setAction(new TestDestructor());
        TestRunner run = new TestRunner(test);
        Thread T = new Thread(run, "Test Run Thread");
        T.start();
    }



//- *******************
//- End Class WatchedMap
}
