package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

@DisplayName("Basic tests for the Mode class.")
public class ModeTests {
    static final int MIN_ARRAY_SIZE = 100000;
    static final int ARRAY_SIZE_INCREMENT = 1000000;
    static final int MEASUREMENTS = 50;
    static final int MIN_VALUE = 1;

    @Test
    @DisplayName("Tests the Mode.findMode() implementation")
    void findModeTests() {
        try {
            ThreadLocalRandom tlr = ThreadLocalRandom.current();
            int arraySize = MIN_ARRAY_SIZE;
            int measurementCounter = 0;
            System.out.format("%10s\t%10s\t%20s\t%10s\t%10s\t%10s\t%10s%n", "Array size (n)", "ns", "n^2", "log(n)", "n * log(n)", "log2(n)", "n * log2(n)");
            while (measurementCounter < MEASUREMENTS) {
                int preferredNumber = tlr.nextInt(MIN_VALUE, arraySize);
                int [] array = generateArray(arraySize, MIN_VALUE, preferredNumber);
                long start = System.nanoTime();
                Mode.Result foundMode = Mode.findMode(array);
                long duration = System.nanoTime() - start;
                System.out.format("%10d\t%10d\t%20.0f\t%10.0f\t%10.0f\t%10.0f\t%10.0f%n", 
                                  arraySize, 
                                  duration, 
                                  Math.pow(arraySize, 2),
                                  Math.log10(arraySize),
                                  ((double)arraySize) * Math.log10(arraySize), 
                                  Math.log10(arraySize) / Math.log(2),
                                  ((double)arraySize) * Math.log10(arraySize) / Math.log(2));
                assertEquals(preferredNumber, foundMode.number);
                arraySize += ARRAY_SIZE_INCREMENT;
                measurementCounter++;
            }
            System.out.println("Done!");
        } catch (Exception e) {
            fail("Something went wrong in finding the mode." + e.getMessage());
        }

    }

    /**
     * Generates an array with specified size and fills it with random numbers from
     * the range minValue..<maxValue. Creates more numbers with preferred value.
     * 
     * @param size            The size of the array.
     * @param minValue        Minimum value put in the array.
     * @param maxValue        Maximum value (exclusive) to put in the array.
     * @param preferredNumber Number preferred (more of this value is put into the
     *                        array)
     */
    private int[] generateArray(int size, int minValue, int preferredNumber) {
        // DO NOT touch this method!
        int[] array = new int[size];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int counter = 0; counter < size; counter++) {
            array[counter] = tlr.nextInt(minValue, size);
        }
        int countOfPreferredNumber = size / 3; // Should be enough.
        for (int counter = 0; counter < countOfPreferredNumber; counter++) {
            array[tlr.nextInt(0, size)] = preferredNumber;
        }
        return array;
    }

}
