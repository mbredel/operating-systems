package de.hda.fbi.os.mbredel.queue;

import de.hda.fbi.os.mbredel.Good;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueueSemaphore implements IQueue {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueSemaphore.class);
    /** The default queue size. */
    private static final int DEFAULT_QUEUE_SIZE = 5;

    /** A semaphore that controls the producer input. */
    private Semaphore prodSemaphore = new Semaphore(DEFAULT_QUEUE_SIZE);
    /** A semaphore that controls the consumer output. */
    private Semaphore consSemaphore = new Semaphore(0);
    /** A lock (in Java also called monitor) to secure critical sections. */
    private Lock lock = new ReentrantLock();
    /** The queue that is used to exchange goods. */
    private Queue<Good> queue;

    /**
     * Default constructor.
     */
    public QueueSemaphore() {
        queue = new LinkedList<>();
    }

    @Override
    public void put(Good good) throws InterruptedException {

        prodSemaphore.acquire();
        try {
            lock.lock();
            this.queue.add(good);
            lock.unlock();
            LOGGER.info("Added [{}] to queue. Queue size is now: {}", good.getName(), queue.size());
        } finally {
            consSemaphore.release();
        }

    }

    @Override
    public Good take() throws InterruptedException {

        consSemaphore.acquire();
        try {
            lock.lock();
            Good good = this.queue.poll();
            lock.unlock();
            LOGGER.info("Removed a good [{}] from queue. Queue size is now: {}", good.getName(), queue.size());
            return good;
        } finally {
            prodSemaphore.release();
        }

    }
}
