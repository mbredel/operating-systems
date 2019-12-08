/*
 Copyright (c) 2019, Michael von Rueden, H-DA
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

 Neither the name of the H-DA and Michael von Rueden
 nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written
 permission.
 */
package de.hda.fbi.os.mbredel.queue;

import de.hda.fbi.os.mbredel.WaitingConsumer;
import de.hda.fbi.os.mbredel.Good;
import de.hda.fbi.os.mbredel.WaitingProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The channel for {@link WaitingProducer}-{@link WaitingConsumer} exchange.
 * Internally it uses a BlockingQueue that blocks additional
 * put-calls when the queue is full and additional take-calls
 * when the queue is empty, respectively.
 *
 * @author Michael von Rueden
 */
public class GoodQueue implements IQueue {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodQueue.class);

    /** The default queue size. */
    public static final int DEFAULT_QUEUE_SIZE = 5;

    /** The queue that is used to exchange goods. */
    private Queue<Good> queue;
    /** The queue size. */
    private int queueSize;

    /** The monitor used to synchronize with producer threads*/
    private Object producerSignal;
    /** The monitor used to synchronize with consumer threads*/
    private Object consumerSignal;

    /**
     * Default constructor that initializes
     * the queue with a default queue size of 5.
     */
    public GoodQueue() {
        this(DEFAULT_QUEUE_SIZE);
    }

    /**
     * Constructor that initializes the with an
     * arbitrary queue size.
     *
     * @param queueSize The size of the queue.
     */
    public GoodQueue(int queueSize) {
        this.queue = new LinkedList();
        this.queueSize = queueSize;
    }

    @Override
    public synchronized boolean put(Good good) {
        if (queue.size() < queueSize) {
            queue.offer(good);
            LOGGER.info("Added [{}] to queue. Queue size is now: {}", good.getName(), queue.size());
            // Send a signal.
            synchronized (consumerSignal) {
                consumerSignal.notifyAll();
            }
            return true;
        }
        return false;
    }

    @Override
    public synchronized Good take() {
        if (!queue.isEmpty()) {
            Good good = queue.poll();
            LOGGER.info("Removed a good [{}] from queue. Queue size is now: {}", good.getName(), queue.size());
            // Send a signal.
            synchronized (producerSignal) {
                producerSignal.notifyAll();
            }
            return good;
        } else {
            return null;
        }
    }

    @Override
    public IQueue addProducerSignal(Object o) {
        producerSignal = o;
        return this;
    }

    @Override
    public IQueue addConsumerSignal(Object o) {
        consumerSignal = o;
        return this;
    }
}
