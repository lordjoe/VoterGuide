package com.lordjoe.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.concurrent.Accumulator
 *   This class addresses the issue where you have data comming in very fast and
 * a process which consumed the data which has a certain setup time. and would run
 * more efficiently with batches of data. Inserts into a database and transmission to
 * a webservice both have this behavior   with fixed setup costs for single transmission and
 * batch.
 *    Furthermore, a certain loss of data on crash must be tollerable.
 *    As data is entered, it is sent as an array to a handler after wither a certain number of
 *  items are present or a certain time has elapsed. Thus data is guaranteed to stay
 *  in the accumulator for less that DrainTimeout and be sent on whenever  BatchSize are accumulated.
 *
 *   Data are queued up for an IBatch handler to process. The class is written to be thread safe and
 *  to quarantee that
 *   1) Data coming in on one thread are processed in the order entered.
 *   2) Data coming in on multiple threads are approximately processed in the order entered.
 *
 *
 * @author Steve Lewis
 * @date Jul 5, 2009
 */
public class Accumulator<T> implements Runnable
{
    public static Accumulator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = Accumulator.class;

    public static final int DEFAULT_BATCH_SIZE = 128;
    public static final int DEFAULT_DRAIN_MILLISEC = 1000;

    private static Timer gTimer = new Timer();

    protected static Timer getTimer()
    {
        return gTimer;
    }

    private ArrayBlockingQueue<T> m_Queue;
    private final ConcurrentLinkedQueue<T[]> m_DrainedSets;
    private T[] m_Sample;
    private int m_BatchSize;
    private long m_DrainTimeout;
    private AccumulatorTimer m_CurrentTimer;
    private IBatchHandler m_Handler;

    /**
     * build with defeult capacity and timeout
     * @param handler non-null handler for data
     * @param sample non-null sample array for generating internal arrays
     */
    public Accumulator(IBatchHandler handler,T[] sample)
    {
        this(handler,sample, DEFAULT_BATCH_SIZE);
    }

    /**
     *
     * build with defeult   timeout
     * @param handler non-null handler for data
     * @param sample non-null sample array for generating internal arrays
     * @param batchSize positive number of items to trigger psssing to handler
     */
    public Accumulator(IBatchHandler handler,T[] sample, int batchSize)
    {
        this(handler,sample, batchSize, DEFAULT_DRAIN_MILLISEC);
    }

    /**
     *
     * build with defeult   timeout
     * @param handler non-null handler for data
     * @param sample non-null sample array for generating internal arrays
     * @param batchSize positive number of items to trigger psssing to handler
     * @param drainMillisec max time an item is allowed to stay in the queue
     */
    public Accumulator(IBatchHandler handler,T[] sample, int batchSize, int drainMillisec)
    {
        m_DrainedSets = new ConcurrentLinkedQueue<T[]>();
        // make  an array of size 0 of the target type
        if (sample.length == 0)
            m_Sample = sample;
        else
            m_Sample = (T[]) Array.newInstance(sample.getClass().getComponentType(), 0);
        setBatchSize(batchSize);
        setDrainTimeout(drainMillisec);
        Thread drain = new Thread(this, "Accumulator Drain");
        drain.setDaemon(true);
        setHandler(handler);
        drain.start();
    }

    /**
     * object to handle data
     * @return
     */
    public IBatchHandler getHandler()
    {
        return m_Handler;
    }

    public void setHandler(IBatchHandler pHandler)
    {
        m_Handler = pHandler;
    }

    /**
     * when an item is added to   m_DrainedSets call
     * the handler with the items
     */
    public void run()
    {
        while (true) {
            T[] dump = null;
            synchronized (m_DrainedSets) {
                while (m_DrainedSets.isEmpty()) {
                    try {
                        m_DrainedSets.wait();
                    }
                    catch (InterruptedException e) {
                        // I quess this means quit
                    }
                }
                if (m_DrainedSets.size() == 1) {
                    dump = m_DrainedSets.remove();
                }
                else {
                    List<T> holder = new ArrayList<T>();
                    while (!m_DrainedSets.isEmpty()) {
                       holder.addAll(Arrays.asList(m_DrainedSets.remove()));
                    }
                }
            }
            IBatchHandler batchHandler = getHandler();
            // todo handle in a new thread
            if (batchHandler != null)
                batchHandler.handleBatch(dump);


        }

    }

    /**
     * insert one item
     * @param added non-null item to add
     */
    public void addItem(T added)
    {

        if (!m_Queue.offer(added)) {
            doDrain();
            m_Queue.offer(added);
        }
        synchronized (m_Queue) {
            if (m_Queue.size() >= getBatchSize()) {
                doDrain();
            }
        }
    }

    /**
     * send the queue contents on for processing -
     * this actually sticks the current contents into a
     * processing queue
     */
    protected void doDrain()
    {
        List<T> drained = new ArrayList<T>();
        synchronized (this) {
            m_Queue.drainTo(drained);
        }
        if(drained.size() == 0)
            return;
        synchronized (m_DrainedSets) {
            T[] ts = drained.toArray(m_Sample);
            m_DrainedSets.add(ts);
            m_DrainedSets.notifyAll();
        }

    }

    /**
     * when BatchSize are entered they are sent for processing
     * @return
     */
    public int getBatchSize()
    {
        return m_BatchSize;
    }

    /**
     * make sure the queue is 4 * the batch size
     *
     * @param pBatchSize
     */
    public synchronized void setBatchSize(int pBatchSize)
    {
        if (m_BatchSize == pBatchSize)
            return;
        int oldBatchSize = m_BatchSize;
        m_BatchSize = pBatchSize;
        if (pBatchSize < oldBatchSize)
            return;
        ArrayBlockingQueue<T> newQueue = new ArrayBlockingQueue<T>(4 * pBatchSize);
        if (m_Queue != null) {
            newQueue.drainTo(newQueue);
        }
        m_Queue = newQueue;
    }

    /**
     *  when  DrainTimeout expires all itens are sent
     *  for processing - this prevent items from sitting forever
     *   in the queue
     * @return
     */
    public long getDrainTimeout()
    {
        return m_DrainTimeout;
    }

    public void setDrainTimeout(long pDrainTimeout)
    {
        if (m_DrainTimeout == pDrainTimeout)
            return;
        m_DrainTimeout = pDrainTimeout;
        Timer timer = getTimer();
         m_CurrentTimer = new AccumulatorTimer(pDrainTimeout);
        timer.schedule(m_CurrentTimer, pDrainTimeout, pDrainTimeout);

    }

    /**
     * causes the accumulator to drain periodically if even one sample
     * is present
     */
    public class AccumulatorTimer extends TimerTask
    {
        private final long TImeout;
        public AccumulatorTimer(long pTImeout)
        {
            TImeout = pTImeout;
        }

        public void run()
        {
            // if superceded do not run
            if (m_DrainTimeout != TImeout)
                cancel();
            synchronized (this) {
                if (m_Queue.size() > 0) {
                    doDrain();
                }
            }

        }
    }
}
