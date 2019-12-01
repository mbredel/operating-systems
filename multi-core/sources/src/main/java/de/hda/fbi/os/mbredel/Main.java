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
package de.hda.fbi.os.mbredel;

import de.hda.fbi.os.mbredel.computation.Runner;
import de.hda.fbi.os.mbredel.configuration.CliParameters;
import de.hda.fbi.os.mbredel.configuration.CliProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class that contains the
 * main method that starts the program.
 *
 * @author Michael von Rueden 
 */
public class Main {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * The main method.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        // Parse the command line arguments.
        CliProcessor.getInstance().parseCliOptions(args);
	
	    // Initialize the matrices.
	    int[][] firstMatrix = initMatrix(2000, 2000);
	    int[][] secondMatrix = initMatrix(1000, 1000);
        int[][] result = new int[firstMatrix.length][secondMatrix[0].length];

        int numberOfThreads = CliParameters.getInstance().getThreads();
        LOGGER.info("Number of threads: " + numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            int offset = i * Math.round(firstMatrix.length/numberOfThreads);
            int length = Math.min((i+1) * Math.round(firstMatrix.length/numberOfThreads), firstMatrix.length);
            new Runner(firstMatrix, secondMatrix, result, offset, length).start();
        }
    }

    /**
     * Initialize a matrix with # rows and # columns.
     * Sets all cell values to 2 - just to have
     * something to compute.
     *
     * @param rows The number of rows.
     * @param columns The number of columns.
     * @return The resulting matrix.
     */
    public static int[][] initMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];
 
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = 2;
            }
        }
        return matrix;
    }

}
