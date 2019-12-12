/*
 Copyright (c) 2019, Michael von Rüden, H-DA
 ALL RIGHTS RESERVED.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Neither the name of the H-DA and Michael Bredel
 nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written
 permission.
 */
package de.hda.fbi.os.mbredel.queue;

import de.hda.fbi.os.mbredel.Consumer;
import de.hda.fbi.os.mbredel.Good;
import de.hda.fbi.os.mbredel.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The channel for {@link Producer}-{@link Consumer} exchange.
 * Internally it uses Semaphores to block additional
 * put-calls when the queue is full and additional take-calls
 * when the queue is empty, respectively.
 *
 * @author Michael von Rüden
 */
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
        lock.lock();
        try {
            this.queue.add(good);
            LOGGER.info("Added [{}] to queue. Queue size is now: {}", good.getName(), queue.size());
        } finally {
            lock.unlock();
        }
        consSemaphore.release();

    }

    @Override
    public Good take() throws InterruptedException {

        Good good;
        consSemaphore.acquire();
        lock.lock();
        try {
            good = this.queue.poll();
            LOGGER.info("Removed a good [{}] from queue. Queue size is now: {}", good.getName(), queue.size());
        } finally {
            lock.unlock();
        }
        prodSemaphore.release();
        return good;

    }
}
