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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class that contains the
 * main method that starts threads.
 *
 * @author Michael Bredel
 */
public class Main {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * The main method that starts the
     * producer-consumer pattern example.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        // Initiate a new queue for goods.
        GoodQueue goodQueue = new GoodQueue();

        // Create producer.
        Producer beerProducer = new Producer("NiceBeer", goodQueue);

        // Create consumer.
        Consumer beerConsumer = new Consumer("Michael", goodQueue);

        // Create the threads.
        Thread p1 = new Thread(beerProducer);
        Thread c1 = new Thread(beerConsumer);

        // Start the threads.
        p1.start();
        c1.start();

    }

}
