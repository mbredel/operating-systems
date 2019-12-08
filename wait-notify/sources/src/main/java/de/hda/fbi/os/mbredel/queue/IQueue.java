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

/**
 * The queue interface that has to be implemented
 * by the various queues for the goods.
 *
 * @author Michael von Rueden
 */
public interface IQueue {

    /**
     * Adds a good to the end of the queue.
     *
     * @param good The good to add.
     * @return true if good is added successfully, false otherwise.
     */
    public boolean put(Good good);

    /**
     * Takes a good from the beginning of the queue.
     *
     * @return A good from the queue or null if queue is empty.
     */
    public Good take();

    /**
     * Adds an object that can be used to synchronize
     * with producer threads using wait() and notify().
     *
     * @param o The monitor to add.
     * @return A IQueue object.
     */
    public IQueue addProducerSignal(Object o);

    /**
     * Adds an object that can be used to synchronize
     * with consumer threads using wait() and notify().
     *
     * @param o The monitor to add.
     * @return A IQueue object.
     */
    public IQueue addConsumerSignal(Object o);

}