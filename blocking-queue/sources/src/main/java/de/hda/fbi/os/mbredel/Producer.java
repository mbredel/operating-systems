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

import de.hda.fbi.os.mbredel.queue.IQueue;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The producer that produces goods and sends
 * them to the consumer.
 *
 *  @author Michael Bredel
 */
public class Producer implements Runnable {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    /** The average waiting time in ms.*/
    private static final int WAITING_TIME = 1000;
    /** The name of the goods produced by this producer. */
    private static final String GOOD_NAME = "beer";
    /** The value of the goods produced by this producer. */
    private static final int VALUE = 10;

    /** The name of the producer. */
    private final String name;
    /** The queue the producer pushes its goods to. */
    private final IQueue queue;
    /** A runner-variable to run the thread - and be able to stop it. */
    private boolean isRunning;
    /** A random number generator. */
    private ExponentialDistribution random;

    /**
     * The default constructor.
     *
     * @param name The name of the producer.
     * @param queue The queue/channel to the consumer.
     */
    public Producer(String name, IQueue queue) {
        this.name = name;
        this.queue = queue;
        this.isRunning = true;
        this.random = new ExponentialDistribution(WAITING_TIME);
    }

    /**
     * Create a good and put it in the queue
     *
     * @throws InterruptedException When the thread is interrupted while waiting.
     */
    public void produce() throws InterruptedException {
        Good good = new Good(GOOD_NAME, name, VALUE);
        queue.put(good);
        LOGGER.info("Producer [{}] created good [{}]", name, good.getName());
    }

    /**
     * Produce goods regularly.
     */
    @Override
    public void run() {

        while(isRunning) {
            // Produce a good.
            try {
                this.produce();
            } catch (InterruptedException e) {
                LOGGER.info("Harsh wake-up due to an InterruptedException while waiting for a drained queue: ", e);
            }

            // Just wait for some time.
            try {
                Thread.sleep((long) random.sample());
            } catch (InterruptedException e) {
                LOGGER.info("Harsh wake-up due to an InterruptedException while sleeping: ", e);
            }
        }

    }
}
