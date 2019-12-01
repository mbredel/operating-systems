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
package de.hda.fbi.os.mbredel;

import de.hda.fbi.os.mbredel.queue.GoodQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The consumer that consumes goods produced
 * by the producer.
 *
 *  @author Michael Bredel
 */
public class Consumer implements Runnable {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    /** The name of the consumer. */
    private final String name;
    /** The queue the consumer gets its goods from. */
    private final GoodQueue queue;
    /** A runner-variable to run the thread - and be able to stop it. */
    private boolean isRunning;

    /**
     * The default constructor.
     *
     * @param name The name of the consumer.
     * @param queue The queue/channel to the producer.
     */
    public Consumer(String name, GoodQueue queue) {
        this.name = name;
        this.queue = queue;
        this.isRunning = true;
    }

    /**
     * Consume good from the queue.
     *
     * @throws InterruptedException When the thread is interrupted while waiting.
     */
    public void consume() throws InterruptedException {
        Good good = queue.take();
        LOGGER.info("Consumer [{}] consume good [{}] produced by [{}]", name, good.getName(), good.getProducerName());
    }

    /**
     * Consume goods whenever available.
     */
    @Override
    public void run() {
        while(isRunning) {
            try {
                this.consume();
            } catch (InterruptedException e) {
                LOGGER.info("Harsh wake-up due to an InterruptedException while waiting for new goods: ", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        }
    }
}
