/*
 Copyright (c) 2017, Michael Bredel, H-DA
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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The channel for {@link Producer}-{@link Consumer} exchange.
 * Internally it uses a BlockingQueue that blocks additional
 * put-calls when the queue is full and additional take-calls
 * when the queue is empty, respectively.
 *
 * @author Michael Bredel
 */
public class GoodQueue implements IQueue {
    /** The default queue size. */
    public static final int DEFAULT_QUEUE_SIZE = 5;

    /** The queue that is used to exchange goods. */
    private BlockingQueue<Good> queue;

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
        queue = new LinkedBlockingQueue<>(queueSize);
    }

    @Override
    public void put(Good good) throws InterruptedException {
        queue.put(good);
    }

    @Override
    public Good take() throws InterruptedException {
        return queue.take();
    }
}
