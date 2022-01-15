package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@DisplayName("Basic tests for the binary search method.")
public class BinarySearchTests {

    private static final int NUMBERS_TO_SEARCH = 10;

    @Test
    @DisplayName("Tests the Mode.findMode() implementation")
    void findFromArrayTests() {
        try {
            System.out.println("-- Starting the test --");
            int [] array = generateArray(9999999, 1);
            ThreadLocalRandom tlr = ThreadLocalRandom.current();

            int counter = NUMBERS_TO_SEARCH;
            long start = 0;
            long linearDuration = 0;
            while (counter-- >= 0) {
                int toFind = tlr.nextInt(1, array.length);
                start = System.nanoTime();
                int linearIndex = SearchArray.linearSearch(toFind, array, 0, array.length - 1);
                linearDuration += System.nanoTime() - start;
                System.out.println("Index of " + toFind + " is: " + linearIndex);    
            }
            linearDuration /= NUMBERS_TO_SEARCH;
            
            System.out.println("Sorting the array...");
            start = System.currentTimeMillis();
            Arrays.sort(array);
            System.out.println("Sorting the array took " + (System.currentTimeMillis() - start) + " ms");

            counter = NUMBERS_TO_SEARCH;
            start = 0;
            long binaryDuration = 0;
            while (counter-- >= 0) {
                int toFind = tlr.nextInt(1, array.length);
                start = System.nanoTime();
                int binaryIndex = SearchArray.binarySearch(toFind, array, 0, array.length - 1);
                binaryDuration += System.nanoTime() - start;
                System.out.println("Index of " + toFind + " is: " + binaryIndex);    
            }
            binaryDuration /= NUMBERS_TO_SEARCH;
            System.out.println("----------------------------------------------");
            System.out.format("Average linear search duration: %10d ns%n", linearDuration);
            System.out.format("Average binary search duration: %10d ns%n", binaryDuration);
            assertTrue(binaryDuration <= linearDuration, () -> "Binary search should be much faster in most cases.");
        } catch (Exception e) {
            fail("Something went wrong in the tests: " + e.getMessage());
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
    private int[] generateArray(int size, int minValue) {
        // DO NOT touch this method!
        int[] array = new int[size];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int counter = 0; counter < size; counter++) {
            array[counter] = tlr.nextInt(minValue, size);
        }
        return array;
    }

}
