//{	class ********************************************
//.
//.
//   holding references	to objects
//.
//}	*************************************************

/**{ file
    @name OQueue.java
    @function <Add Comment Here>
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
/**{ class
    @name OQueue
    @function <Add Comment Here>
}*/ 
public class OQueue {
    public static final int MIMIMUM_QUEUE_SIZE = 32;

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_queuesize
        @function <Add Comment Here>
    }*/ 
    private int m_queuesize;

    /**{ field
        @name m_InPointer
        @function <Add Comment Here>
    }*/ 
    private int m_InPointer;

    /**{ field
        @name m_OutPointer
        @function <Add Comment Here>
    }*/ 
    private int m_OutPointer;

    /**{ field
        @name m_QueueHolds
        @function <Add Comment Here>
    }*/ 
    private int m_QueueHolds;

    /**{ field
        @name m_TheQueue
        @function <Add Comment Here>
    }*/ 
    Object[] m_TheQueue;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name OQueue
        @function Constructor of OQueue
    }*/ 
    public OQueue() {
        clear();
    }
    //- ******************* 
    //- Methods 
    /**{ constructor
        @name OQueue
        @function Constructor of OQueue
    }*/ 
    public OQueue(int size) {
        this();
        m_queuesize = Math.max(size,MIMIMUM_QUEUE_SIZE);
        m_TheQueue = new Object[m_queuesize];
    }

    /**{ method
        @name clear
        @function <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public synchronized void clear() {
        m_QueueHolds = 0;
        m_InPointer = 0;
        m_OutPointer = 0;
    }

    /**{ method
        @name isEmpty
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public boolean isEmpty() {
        return(m_QueueHolds == 0);
    }
    /**{ method
        @name isEmpty
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int size() {
        return(m_QueueHolds);
    }

    /**{ method
        @name push
        @function <Add Comment Here>
        @param Plus <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public synchronized int push(Object Plus) {
        if(m_QueueHolds >= m_queuesize) 
            /* increase m_queuesize if needed and allowed */
        {
            extend();
        }
        Assertion.validate(m_TheQueue[m_InPointer] == null);
        m_TheQueue[m_InPointer ++] = Plus;
        if(m_InPointer >= m_queuesize) {
            m_InPointer = 0;
        }
        m_QueueHolds ++;
        return(m_QueueHolds);
    }

    /**{ method
        @name member
        @function <Add Comment Here>
        @param Plus <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public synchronized boolean member(Object Plus) {
        int i = m_OutPointer;
        int count = 0;
        while(count < m_QueueHolds) {
            if(m_TheQueue[i].equals(Plus)) {
                return(true);
            }
            count ++;
            i ++;
            if(i >= m_queuesize) {
                i = 0;
            }
        }
        return(false);
        //	All	OK
    }

    /**{ method
        @name top
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public Object top() {
        Object theret = null;
        if(! isEmpty()) {
            /* anything in the queue */
            theret = m_TheQueue[m_OutPointer];
        }
        return(theret);
    }

    /**{ method
        @name pop
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public synchronized Object pop() {
        Object theret = null;
        if(! isEmpty()) {
            /* anything in the queue */
            m_QueueHolds --;
            theret = m_TheQueue[m_OutPointer];
            m_TheQueue[m_OutPointer] = null;
            m_OutPointer++;
            if(m_OutPointer >= m_queuesize) {
                m_OutPointer = 0;
            }
        }
        return(theret);
    }

    /**{ method
        @name unPop
        @function <Add Comment Here>
        @param Plus <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public synchronized int unPop(Object Plus) {
        if(m_QueueHolds >= m_queuesize) 
            /* increase m_queuesize if needed and allowed */
        {
            extend();
        }
        if(! isEmpty()) {
            return(push(Plus));
        }
        m_OutPointer --;
        /* predecrement m_OutPointer */
        if(m_OutPointer < 0) {
            m_OutPointer = m_queuesize - 1;
        }
        /* Loop around */
        m_TheQueue[m_OutPointer] = Plus;
        m_QueueHolds ++;
        return(m_QueueHolds);
    }

    /**{ method
        @name extend
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    protected synchronized int extend() {
        int Oldqueuesize;
        int i;
        int TheSize = Math.max(2 * m_queuesize,MIMIMUM_QUEUE_SIZE);
        Object[] Old = m_TheQueue;
        Oldqueuesize = m_queuesize;
        m_queuesize = TheSize;
        m_TheQueue = new Object[m_queuesize];
        for(i = 0; i < m_QueueHolds; i ++) {
            m_TheQueue[i] = Old[m_OutPointer ++];
            if(m_OutPointer >= Oldqueuesize) {
                m_OutPointer = 0;
            }
        }
        m_OutPointer = 0;
        m_InPointer = m_QueueHolds;
        return(TheSize);
    }


//- ******************* 
//- End Class OQueue
}
