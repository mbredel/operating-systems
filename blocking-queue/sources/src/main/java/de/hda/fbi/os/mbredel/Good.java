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

/**
 * The goods that are produced and sent to the consumer.
 * Is is implemented as an immutable object. That is, the object
 * state (the object's data) cannot change after construction.
 * Immutable objects are automatically thread-safe and have
 * no synchronization issues.
 *
 *  @author Michael Bredel
 */
public final class Good {
    /* The name of the good. */
    private final String name;
    /** The name of the producer. */
    private final String producerName;
    /** The value of the good. */
    private final int value;

    /**
     * The default constructor that initialized the good object.
     *
     * @param name The name of the good.
     * @param producerName The name of the producer.
     * @param value The value of the good.
     */
    public Good(String name, String producerName, int value) {
        this.name = name;
        this.producerName = producerName;
        this.value = value;
    }

    /**
     * Getter for the name.
     *
     * @return The name of the good.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the name of the producer that
     * created the good.
     *
     * @return The name of the producer.
     */
    public String getProducerName() {
        return this.producerName;
    }

    /**
     * Getter for the value.
     *
     * @return The value of the good.
     */
    public int getValue() {
        return this.value;
    }
}
