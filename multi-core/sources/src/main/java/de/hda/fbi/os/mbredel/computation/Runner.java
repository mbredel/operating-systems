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
package de.hda.fbi.os.mbredel.computation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculates the matrix product of two
 * matrices. Moreover, it allows to specify
 * a sub-set of matrix values, by only taking
 * specific rows of the first matrix into
 * account. The sub-set is specified by an
 * offset to identify the row to start, and
 * a length, i.e. the number of rows following
 * the offset-row.
 *
 * @author Michael von Rueden
 */
public class Runner extends Thread {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    /** Two-dimensional arrays that contain the various matrices. */
    int[][] firstMatrix, secondMatrix, result;
    /** The offset and length of the first array to calculate. */
    int offset, length;

    /**
     * The default constructor.
     *
     * @param firstMatrix The first matrix to multiply.
     * @param secondMatrix The second matrix to multiply.
     * @param result The resulting matrix
     * @param offset The offset of the first matrix subset.
     * @param length The length of the first matrix subset.
     */
    public Runner(int[][] firstMatrix, int[][] secondMatrix, int[][]result, int offset, int length) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.result = result;
        this.offset = offset;
        this.length = length;

        // Check that no. of columns of the first matrix
        // equals no. of rows of the second matrix.
        // Otherwise, matrix multiplication is not possible.
        // However, the code below might not recognise this.
        if (firstMatrix[0].length != secondMatrix.length) {
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {
        LOGGER.info(String.format("Started thread: %s", this));
        for (int row = offset; row < length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
    }

    /**
     * Calculating the cell value of the resulting matrix by
     * summing up the products of the row and column values
     * of the first and second matrix. That is
     *
     *    r_i,j = sum_k^n a_i,k * b_k,j
     *
     * where r_i,j is the resulting field in the i-th row
     * and j-th column of the resulting vector; a is the
     * value in the i-th row and k-th column of the
     * first vector; and b is the value in the k-th
     * row and j-th column of the second vector.
     *
     * @param firstMatrix The first matrix to multiply.
     * @param secondMatrix The second matrix to multiply.
     * @param row The index of the row of the first matrix.
     * @param col The index of the column of the second matrix.
     * @return The resulting value of the row/col cell of the resulting matrix.
     */
    private int multiplyMatricesCell(int[][] firstMatrix, int[][] secondMatrix, int row, int col) {
        int cell = 0;

        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }

        return cell;
    }
}
