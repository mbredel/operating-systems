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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.MessageFormat;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Michael von Rueden
 */
public class RunnerTest {

    int[][] matrixA = {
                {2, 3, 4},
                {5, 6 ,7}
            };
    int[][] matrixB = {
                {2, 5},
                {3, 6},
                {4, 7}
            };
    int[][] resultAxB = {
                {29, 56},
                {56, 110}
            };
    int[][] resultBxA = {
                {29, 36, 43},
                {36, 45, 54},
                {43, 54, 65}
            };

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void run() {
        // AxB should result in an 2x2 matrix.
        int[][] result = new int[2][2];
        Runner runner = new Runner(matrixA, matrixB, result, 0, 2);
        runner.run();
        assertTrue(checkExpectation(result, resultAxB));

        // BxA should result in an 3x3 matrix.
        result = new int[3][3];
        runner = new Runner(matrixB, matrixA, result, 0, 3);
        runner.run();
        assertTrue(checkExpectation(result, resultBxA));

        // After this point, a matching exception is expected
        exception.expect(RuntimeException.class);
        runner = new Runner(matrixB, resultBxA, result, 0, 3);
        runner = new Runner(resultAxB, resultBxA, result, 0, 3);
        runner = new Runner(matrixA, resultAxB, result, 0, 3);
    }

    /**
     * Checks that two matrices are equal. This end, it first
     * checks the sizes of the matrices are equal. Finally , it
     * checks that all fields of the matrices contain same values.
     *
     * @param result The matrix to check.
     * @param truth The ground truth matrix to check against.
     * @return True if the matrices are equal, false otherwise.
     */
    public boolean checkExpectation(int[][] result, int[][] truth) {
        if (result.length != truth.length || result[0].length != truth[0].length) {
            System.out.println("Wrong size.");
            return false;
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (result[i][j] != truth[i][j]) {
                    System.out.println(MessageFormat.format("Wrong value at: [{0}, {1}]: {2} != {3}", i, j, result[i][j], truth[i][j]));
                    System.out.println(Arrays.deepToString(result));
                    return false;
                }
            }
        }
        return true;
    }
}