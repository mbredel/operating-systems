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
package de.hda.fbi.os.mbredel.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

/**
 * A container class that contains all the
 * CLI parameters. It is implemented using
 * the Singleton pattern (GoF) to make sure
 * we only have one object of this class.
 *
 * @author Michael von Rueden 
 */
@Data
public class CliParameters {

    /** The one and only instance of CLI parameters. */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static CliParameters instance;

    /** The number of threads. */
    private int threads = Defaults.THREADS;

    /**
     * The static getter for the CLI parameters instance.
     *
     * @return The CLI parameters instance.
     */
    public static CliParameters getInstance() {
        if (instance == null)
            instance = new CliParameters();
        return instance;
    }

    /**
     * Getter that casts a string. Thus, not
     * using Lombok here.
     */
    public void setThreads(String threads) {
        this.threads = Integer.parseInt(threads);
    }

    /**
     * A private constructor to avoid
     * instantiation.
     */
    private CliParameters() {}
}
