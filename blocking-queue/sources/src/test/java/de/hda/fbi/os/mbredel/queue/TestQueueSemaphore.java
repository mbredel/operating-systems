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

import de.hda.fbi.os.mbredel.Good;
import de.hda.fbi.os.mbredel.Producer;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael von Rueden
 */
public class TestQueueSemaphore {

    /**
     * This is a copy of the regular QueueSemaphore class.
     * In addition to the regular class, this class adds
     * some wait()s to demonstrate the semaphore queue
     * works correctly.
     *
     * This, of course, is kind of unusual for a test.
     * This is for demonstrating purposes only!
     */
   public class QueueSemaphore implements IQueue {

        /** A semaphore that controls the producer input. */
        private final Semaphore prodSemaphore;
        /** A semaphore that controls the consumer output. */
        private final Semaphore consSemaphore;
        /** A lock (in Java also called monitor) to secure critical sections. */
        private final Lock lock;
        /** The queue that is used to exchange goods. */
        private Queue<Good> queue;

        /**
         * Default constructor.
         */
        public QueueSemaphore(int queueSize) {
            prodSemaphore = new Semaphore(queueSize);
            consSemaphore = new Semaphore(0);
            lock = new ReentrantLock();
            queue = new LinkedList<>();
        }

       @Override
       public void put(Good good) throws InterruptedException {
           prodSemaphore.acquire();
           lock.lock();
           try {
               this.queue.add(good);
               System.out.println(Thread.currentThread().getName() + ": Added good to queue. Queue size is now: " + queue.size());
               System.out.println(Thread.currentThread().getName() + ": Going to sleep");
               Thread.sleep(10000);
               System.out.println(Thread.currentThread().getName() + ": Wakeup");
           } finally {
               lock.unlock();
           }
           consSemaphore.release();
       }

       @Override
       public Good take() throws InterruptedException {
           return null;
       }
   }

   @Test
   public void testQueue() throws InterruptedException {

       // Initiate a new queue.
       IQueue goodQueue = new QueueSemaphore(5);

       // Create producer.
       Producer p1 = new Producer("1", goodQueue);
       Producer p2 = new Producer("2", goodQueue);

       // Create the threads.
       Thread pt1 = new Thread(p1);
       Thread pt2 = new Thread(p2);

       // Start the threads.
       pt1.start();
       pt2.start();

       pt1.join();
       pt2.join();
   }
}
