package com.lordjoe.utilities;

/**
 * LoanManager - this class is used to allow threads to borrow a
 * resource for a given period of time
 * @author Steve Lewis
 */
public class LoanManager {
    public static ILoanedObject[] EMPTY_LOANED_OBJECT_ARRAY = {};
    private ILoanedObject[] m_Instances;
    private Thread[] m_LoanHolder;
    private int m_NumberLoaned;
    private Class m_InstanceClass; // class to create

    /**
     * Default Constructor
     */
    public LoanManager() {
        m_Instances = EMPTY_LOANED_OBJECT_ARRAY;
        m_LoanHolder = Util.EMPTY_THREAD_ARRAY;
    }

    /**
     * Constructor - used DefaultLoanedObject
     * @param instances - number of allowed instances greater than 0
     */
    public LoanManager(int instances) {
        this(instances, DefaultLoanedObject.class);
    }

    /**
     * Constructor
     * @param instances - number of allowed instances greater than 0
     * @param LoanedClass - non-null class of load must implement ILoadInterface
     */
    public LoanManager(int instances, Class LoanedClass) {
        this();
        setLoanedClass(LoanedClass);
        setNumberInstances(instances);
    }

    protected void setLoanedClass(Class LoanedClass) {
        if (!LoanedClass.isAssignableFrom(ILoanedObject.class))
            throw new IllegalArgumentException("Must implement ILoanedObject");
        m_InstanceClass = LoanedClass;
    }

    protected ILoanedObject buildInstance() {
        return ((ILoanedObject) ClassUtilities.createInstance(m_InstanceClass));
    }

    /**
     * Constructor
     * @param instances - number of allowed instances greater than 0
     * @param LoanedClass - non-null class of load must implement ILoadInterface
     */
    public LoanManager(ILoanedObject[] actualInstances) {
        this();
        m_Instances = actualInstances;
        m_LoanHolder = new Thread[actualInstances.length];
    }

    /**
     * Set the number of instances available to loan
     * Usually to this as part of the constructor
     * @param instances - number of allowed instances greater than 0
     * @throws IllegalArgumentException if instances <= 0
     */
    public synchronized void setNumberInstances(int instances) {
        if (instances <= 0)
            throw new IllegalArgumentException("Must set more than 0 instances");
        if (m_Instances.length == instances)
            return;
        ILoanedObject[] items = new ILoanedObject[instances];
        Thread[] itemHolders = new Thread[instances];
        System.arraycopy(m_Instances, 0, items, 0, m_Instances.length);
        System.arraycopy(m_LoanHolder, 0, itemHolders, 0, m_LoanHolder.length);
        for (int i = m_Instances.length; i < instances; i++) {
            items[i] = buildInstance();
        }
        m_Instances = items;
        m_LoanHolder = itemHolders;
    }

    public int getNumberLoaned() {
        return (m_NumberLoaned);
    }

    public int getNumberAvailable() {
        return (m_Instances.length);
    }

    /**
     * Obtain a loan
     * @return non-null ILoanedObject - may wait for it
     */
    public ILoanedObject getLoan() {
        return (getLoan(0));
    }

    /**
     * Obtain a loan
     * @param duration non-negative period in millisec - if 0 loan is unlimited
     * @return non-null ILoanedObject - may wait for it
     */
    public synchronized ILoanedObject getLoan(int duration) {
        ILoanedObject ret = null;
        while (getNumberLoaned() == getNumberAvailable()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                throw new IllegalStateException("Some wants this thread to die");
            }
        }
        for (int i = 0; i < m_LoanHolder.length; i++) {
            if (m_LoanHolder[i] == null) {
                m_LoanHolder[i] = Thread.currentThread();
                ret = m_Instances[i];
                break;
            }
        }
        m_NumberLoaned++;
        ret.startTimeout(duration);
        return (ret);
    }

    /**
     * return a loan
     * @param returned non-null ILoanedObject
     */
    public synchronized void release(ILoanedObject returned) {
        for (int i = 0; i < m_Instances.length; i++) {
            if (m_Instances[i] == returned) {
                m_LoanHolder[i] = null;
                break;
            }
        }
        m_NumberLoaned--;
        returned.stopTimeout();
        notifyAll();
    }


}