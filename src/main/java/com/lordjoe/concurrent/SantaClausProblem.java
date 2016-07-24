package com.lordjoe.concurrent;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * A simulation of the Santa Claus Problem using java.util.concurrent.
 *
 * <p>See <a href="http://www.crsr.net/Notes/SantaClausProblem.html">
 * http://www.crsr.net/Notes/SantaClausProblem.html</a> for a
 * solution using Haskell and Software Transactional Memory (STM).
 * See <a href="http://research.microsoft.com/~nick/santa.pdf">
 * http://research.microsoft.com/~nick/santa.pdf</a> for a solution
 * written in Polyphonic C#.
 *
 * @author Joe Bowbeer
 */
public class SantaClausProblem {

    static final int NUM_REINDEER = 9;
    static final int NUM_ELVES = 10;
    static final int ELF_QUORAM = 3;
    enum Priority {REINDEER, ELF} // Reindeers before elves

    static class Reindeer implements Runnable {

        private final CyclicBarrier stable;
        private final CyclicBarrier sleigh;
        private final Runnable takeHoliday;

        public Reindeer(CyclicBarrier stable, CyclicBarrier sleigh,
                Runnable takeHoliday) {
            this.stable = stable;
            this.sleigh = sleigh;
            this.takeHoliday = takeHoliday;
        }

        public void run() {
            try {
                while (true) {
                    takeHoliday.run();
                    stable.await();
                    sleigh.await();
                }
            } catch (InterruptedException ex) {
                // quit
            } catch (BrokenBarrierException ex) {
                // quit
            }
        }
    }

    static class Elf implements Runnable {

        private final MeetingManager manager;
        private final Runnable makeToys;

        public Elf(MeetingManager manager, Runnable makeToys) {
            this.manager = manager;
            this.makeToys = makeToys;
        }

        public void run() {
            try {
                while (true) {
                    makeToys.run();
                    manager.makeReservation().await();
                }
            } catch (InterruptedException ex) {
                // quit
            } catch (BrokenBarrierException ex) {
                // quit
            }
        }
    }

    static class Santa implements Runnable {

        private final BlockingQueue<SantaTask> queue;

        public Santa(BlockingQueue<SantaTask> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true) {
                    System.out.println("Santa is sleeping");
                    queue.take().run();
                }
            } catch (InterruptedException ex) {
                // quit
            }
        }
    }

    static class SantaTask implements Comparable<SantaTask>, Runnable {

        private final Priority priority;
        private final CyclicBarrier barrier;

        public SantaTask(Priority priority, CyclicBarrier barrier) {
            this.priority = priority;
            this.barrier = barrier;
        }

        public int compareTo(SantaTask other) {
            return priority.compareTo(other.priority);
        }

        public void run() {
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // stop Santa
            } catch (BrokenBarrierException ex) {
                Thread.currentThread().interrupt(); // stop Santa
            }
        }
    }

    static class MeetingManager {

        private final BlockingQueue<SantaTask> queue;
        private final Runnable holdMeeting;
        private CyclicBarrier meeting;
        private int attendees;

        public MeetingManager(BlockingQueue<SantaTask> queue,
                Runnable holdMeeting) {
            this.queue = queue;
            this.holdMeeting = holdMeeting;
        }

        public synchronized CyclicBarrier makeReservation() {
            if (meeting == null) {
                meeting = new CyclicBarrier(ELF_QUORAM+1, holdMeeting);
            }
            CyclicBarrier reservation = meeting;
            if (++attendees == ELF_QUORAM) {
                queue.add(new SantaTask(Priority.ELF, meeting));
                meeting = null;
                attendees = 0;
            }
            return reservation;
        }
    }

    static final Random RAND = new Random();

    static Runnable makeRandomDelay(final int delay, final String what) {
        return new Runnable() {
            public void run() {
                try {
                    if (what != null)
                        System.out.println(what);
                    Thread.sleep(RAND.nextInt(delay));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // propogate
                }
            }
        };
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        final BlockingQueue<SantaTask> queue =
                new PriorityBlockingQueue<SantaTask>();

        Runnable designToys = makeRandomDelay(200,
                "Santa is meeting with elves");
        Runnable makeToys = makeRandomDelay(400, null);
        Runnable deliverToys = makeRandomDelay(400,
                "Santa is delivering toys");
        Runnable takeHoliday = makeRandomDelay(600, null);

        MeetingManager manager = new MeetingManager(queue, designToys);

        for (int i = 0; i < NUM_ELVES; i++)
            executor.execute(new Elf(manager, makeToys));

        final CyclicBarrier sleigh =
                new CyclicBarrier(NUM_REINDEER+1, deliverToys);

        CyclicBarrier stable =
                new CyclicBarrier(NUM_REINDEER, new Runnable() {
            public void run() {
                queue.add(new SantaTask(Priority.REINDEER, sleigh));
            }
        });

        for (int i = 0; i < NUM_REINDEER; i++)
            executor.execute(new Reindeer(stable, sleigh, takeHoliday));

        executor.execute(new Santa(queue));

        // Run simulation for 30 seconds and then shut it down.
        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }
}
