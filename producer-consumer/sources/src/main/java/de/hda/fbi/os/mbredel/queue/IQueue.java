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

import de.hda.fbi.os.mbredel.Good;

/**
 * The queue interface that has to be implemented
 * by the various queues for the goods.
 *
 * @author Michael Bredel
 */
public interface IQueue {

    /**
     * Adds a good to the end of the queue.
     *
     * @param good The good to add.
     * @throws InterruptedException When the thread is interrupted while waiting.
     */
    public void put(Good good) throws InterruptedException;

    /**
     * Takes a good from the beginning of the queue.
     *
     * @return A good from the queue.
     * @throws InterruptedException When the thread is interrupted while waiting.
     */
    public Good take() throws InterruptedException;

}