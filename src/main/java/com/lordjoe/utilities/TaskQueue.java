//{	class ********************************************
//.
//.
//   holding references	to objects
//.
//}	*************************************************

/**{ file
 @name TaskQueue.java
 @function keep a queue of Tasks to run - also a runnable to run those tasks
 @author> Steve Lewis
 @copyright>
 ************************
 *  Copyright (c) 1996,97,98
 *  Steven M. Lewis
 *  www.LordJoe.com
 ************************
 @date> Mon Jun 22 21:48:24 PDT 1998
 @version> 1.0
 }*/
package com.lordjoe.utilities;


//// TODO: This is just temp fix for better logging in CDOiC3 - MWR
//
//import org.apache.log4j.NDC; //- MWR

import java.util.Stack;

import com.lordjoe.exceptions.*;

//- MWR

/**{ class
 @name TaskQueue
 @function keep a queue of Tasks to run - also a runnable to run those tasks
 }*/
public class TaskQueue implements ITaskQueue, ITaskWatcher, Runnable {
    public static final int MIMIMUM_QUEUE_SIZE = 32;

    //- *******************
    //- Fields
    /**{ field
     @name m_thread
     @function thread running the queue
     }*/
    private Thread m_thread;
    //- *******************
    //- Fields
    /**{ field
     @name m_queuesize
     @function  number elements in the queue
     }*/
    private int m_queuesize;

    /**{ field
     @name m_InPointer
     @function index of the location to add
     }*/
    private int m_InPointer;

    /**{ field
     @name m_OutPointer
     @function index of the location to pop
     }*/
    private int m_OutPointer;

    /**{ field
     @name m_QueueHolds
     @function  number of items the queue holds
     }*/
    private int m_QueueHolds;

    /**{ field
     @name m_TheQueue
     @function - array forming the queue
     }*/
    private Runnable[] m_TheQueue;

    /* A NDC Stack to use for the task thread */
    private Stack m_NDCStack;  //- MWR
    /* A label for the TaskQueue */
    private String m_Label;    // - MWR

    //- *******************
    //- Methods
    /**{ constructor
     @name OQueue
     @function Constructor of OQueue
     }*/
    public TaskQueue() {
        clear();
    }

    /**{ constructor
     @name OQueue
     @function Constructor of OQueue
     }*/
    public TaskQueue(int size) {
        this();
        m_queuesize = Math.max(size, MIMIMUM_QUEUE_SIZE);
        m_TheQueue = new Runnable[m_queuesize];
    }

    /**
     * Creates a new <code>TaskQueue</code> instance.
     * @param size an <code>int</code> value that is the size of the queue
     * @param label a <code>String</code> to use for a label
     */
    public TaskQueue(int size, String label) { // - MWR
        this(size);
        m_Label = label;
    }

    /**
     * Creates a new <code>TaskQueue</code> instance.
     * @param label a <code>String</code> to use for a label
     */
    public TaskQueue(String label) {  // - MWR
        this();
        m_Label = label;
    }


    public boolean inMyThread() {
        return (Thread.currentThread() == m_thread);
    }

    /**
     * Run method keeps looking for a Runnable - then runs it
     */
    public synchronized void start() {
        if (m_thread != null && m_thread.isAlive())
            throw new IllegalStateException("Queue already runnning");
        m_thread = new Thread(this, "TaskQueue");
        m_thread.setDaemon(true); // plan to run forever

        // TODO: This is just temp fix for better logging in CDOiC3
       // m_NDCStack = NDC.cloneStack();	 //- MWR

        m_thread.start();
    }

    /**
     * Run method keeps looking for a Runnable - then runs it
     */
    public void run() {
//        NDC.inherit(m_NDCStack);  //- MWR
//        if (m_Label != null) {
//            NDC.push(m_Label);         //- MWR
//        }

        try {
            while (true) {
                Runnable action = pop();
                action.run();
            }
        } catch (InterruptedException ex) {
        } finally {
//            NDC.clear();   //- MWR
//            NDC.remove();  //- MWR
        }

    }

    /**{ method
     @name clear
     @function drop all elements
     }*/
    public synchronized void clear() {
        m_QueueHolds = 0;
        m_InPointer = 0;
        m_OutPointer = 0;
        if (m_TheQueue != null) {
            for (int i = 0; i < m_TheQueue.length; i++) {
                m_TheQueue[i] = null;
            }
        }
        notifyAll();
    }

    /**{ method
     @name isEmpty
     @function true if the queue is empty
     @return as above
     }*/
    public boolean isEmpty() {
        return (m_QueueHolds == 0);
    }

    /**{ method
     @name size
     @function return the number of tasks inthe queue
     @return as above
     }*/
    public int size() {
        return (m_QueueHolds);
    }

    /**
     * Executes a given Runnable on a thread in the pool, block if needed.
     * @param r non-null runnable
     */
    public void runIt(Runnable r) {
        push(r);
    }

    /**
     * Executes a given Runnable on a thread in the pool, block if needed.
     * @param r non-null runnable
     */
    public ITaskWatcher runItWithWatch(Runnable r) {
        //TODO: Que? look's like this should be doing more - MWR
        return (this);
    }

    /**
     * return when the task has completed -
     * after Timeout millisec thtow
     * TaskTImeoutException
     */
    public synchronized void waitCompletion(Runnable r, int timeout) {
        long start = System.currentTimeMillis();
        while (queueHolds(r)) {
            try {
                wait(timeout);
            } catch (InterruptedException ex) {
                throw new ThreadInterruptedException();
            }
            if (!queueHolds(r))
                break;
            if (start + timeout < System.currentTimeMillis())
                throw new TaskTimeoutException();
        }
    }

    public synchronized boolean queueHolds(Runnable r) {
        if (isEmpty())
            return (false);
        int index = m_OutPointer;
        for (int i = 0; i < m_QueueHolds; i++) {
            if (m_TheQueue[index++] == r)
                return (true);
            if (index >= m_queuesize) {
                index = 0;
            }
        }
        return (false);
    }


    /**{ method
     @name push
     @function Add a task to the queue
     @param Plus - non-null task to add
     @return number of elements in the queus
     }*/
    public synchronized int push(Runnable Plus) {
        if (m_QueueHolds >= m_queuesize)
        /* increase m_queuesize if needed and allowed */ {
            extend();
        }
        //    Assertion.validate(m_TheQueue[m_InPointer] == null);
        m_TheQueue[m_InPointer++] = Plus;
        if (m_InPointer >= m_queuesize) {
            m_InPointer = 0;
        }
        m_QueueHolds++;
        notifyAll();
        return (m_QueueHolds);
    }


    /**{ method
     @name pop
     @function remove the top and return it - wait until a top available
     @return as above non-null
     }*/
    public synchronized Runnable pop() throws InterruptedException {
        Runnable theret = null;

        while (isEmpty()) {
            wait();
        }
        /* anything in the queue */
        m_QueueHolds--;
        theret = m_TheQueue[m_OutPointer];
        m_TheQueue[m_OutPointer] = null;
        m_OutPointer++;
        if (m_OutPointer >= m_queuesize) {
            m_OutPointer = 0;
        }
        return (theret);
    }


    /**{ method
     @name extend
     @function extend the size of th equeus
     @return new number of elements
     }*/
    protected synchronized int extend() {
        int Oldqueuesize;
        int i;
        int TheSize = Math.max(2 * m_queuesize, MIMIMUM_QUEUE_SIZE);
        Runnable[] Old = m_TheQueue;
        Oldqueuesize = m_queuesize;
        m_queuesize = TheSize;
        m_TheQueue = new Runnable[m_queuesize];
        for (i = 0; i < m_QueueHolds; i++) {
            m_TheQueue[i] = Old[m_OutPointer++];
            if (m_OutPointer >= Oldqueuesize) {
                m_OutPointer = 0;
            }
        }
        m_OutPointer = 0;
        m_InPointer = m_QueueHolds;
        return (TheSize);
    }


//- *******************
//- End Class OQueue
}
