package com.lordjoe.tasks;

import com.lordjoe.tasks.AbstractRunnableTask;
import com.lordjoe.tasks.RunTask;
import com.lordjoe.tasks.Task;
import com.lordjoe.tasks.MarkerException;

import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.tasks.TestUtilities
 * Useful functions for testing - especially
 * where using concurrence
 * @author Steve Lewis
 * @date
 */
public abstract class TestUtilities
{
 //   public static final int NUMBER_TEST_PRIMES = 5 * 1000 * 1000;
    public static final int NUMBER_TEST_PRIMES = 30 * 1000 * 1000;
//    public static final int NUMBER_TEST_PRIMES = 100 * 1000 * 1000;

    //  public static final int EXPECTED_NUMBER_PRIMES = 5761455; //  100 * 1000 * 1000
   public static final int EXPECTED_NUMBER_PRIMES = 1857859; // 30 * 1000 * 1000
  //  public static final int EXPECTED_NUMBER_PRIMES = 348513; // 5 * 1000 * 1000

    private TestUtilities()
    {
    }

    public static RunTask getSieveRunnable()
    {
        return new SieveTask();
    }

    public static Callable<Integer> getSieveCallable()
    {
        return new SieveCallable();
    }

    public static Callable<Integer> getSieveCallable(int n)
    {
        return new SieveCallable(n);
    }


    public static Task<Integer> getFailedTask()
    {
        return new Task(new FailingSieveCallable());
    }

    public static Task<Integer> getNestedFailedTask()
    {
        return new Task(new NestedFailingSieveCallable());
    }

    public static Task<Integer> getSieveTask()
    {
        return new Task(new SieveCallable());
    }

    public static Task<Integer> getSieveTask(int n)
    {
        return new Task(new SieveCallable(n));
     }

     /**
     * compute bound task which takes several seconds to run
     */
    public static class SieveTask extends AbstractRunnableTask
    {
        private final int m_NumberTest;

        public SieveTask()
        {
            this(NUMBER_TEST_PRIMES);
        }

        public SieveTask(int nTest)
        {
            m_NumberTest = nTest;
        }

        public void run()
        {
            BitSet b = sieve(m_NumberTest);
        }
    }

    /**
     * compute bound task which takes several seconds to run
     */
    public static class SieveCallable implements Callable<Integer>
    {
        private final int m_NumberTest;

        public SieveCallable()
        {
            this(NUMBER_TEST_PRIMES);
        }

        public SieveCallable(int nTest)
        {
            m_NumberTest = nTest;
        }

        public Integer call()
        {
            BitSet b = sieve(m_NumberTest);
            int nPrimes = TestUtilities.numberOn(b);
            return new Integer(nPrimes);
        }
    }
    /**
      * compute bound task which takes several seconds to run
     * and will throw an UnsupportedOperationException
      */
     public static class FailingSieveCallable extends SieveCallable
     {
          public FailingSieveCallable()
         {
             super(NUMBER_TEST_PRIMES / 20);
         }

         public Integer call()
         {
             Integer ret = super.call();
             throw new UnsupportedOperationException("This is supposed to fail");
         }
     }
    /**
      * compute bound task which takes several seconds to run
     * and will throw an UnsupportedOperationException
      */
     public static class NestedFailingSieveCallable extends SieveCallable
     {
          public NestedFailingSieveCallable()
         {
             super(1);
         }

         public Integer call()
         {
             Task<Integer> failTask = new Task(new FailingSieveCallable());
             failTask.execute();
             failTask.waitForCompletion();
             if(failTask.getException() != null)
                throw new MarkerException(failTask.getException());
             return failTask.getReturn();
         }
     }

    /**
     * return the number of true bits in a bitset
     *
     * @param b non-null bitset
     * @return number true
     */
    public static int numberOn(BitSet b)
    {
        int size = b.size();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (b.get(i))
                count++;
        }
        return count;
    }


    /**
     * This program runs the Sieve of Eratosthenes benchmark.
     * It computes all primes up to maxTest.
     * http://www.phptr.com/articles/article.asp?p=368648&seqNum=5&rl=1
     *
     * @param maxTest max number to test
     * @return array with true where n is prime
     */
    public static BitSet sieve(int maxTest)
    {
        int n = maxTest;
        BitSet b = new BitSet(n + 1);
        int count = 0;
        int i;
        for (i = 2; i <= n; i++)
            b.set(i);
        i = 2;
        while (i * i <= n) {
            if (b.get(i)) {
                count++;
                int k = 2 * i;
                while (k <= n) {
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }
        while (i <= n) {
            if (b.get(i))
                count++;
            i++;
        }
        return b;
    }

}
