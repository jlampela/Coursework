package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

/*

/**
 * Unit tests for testing the stack implementation.
 * 
 * DO NOT change anything here, just implement the StackInterface, instantiate it in
 * StackBuilder.createIntegerStack and perform the tests.
 */
@DisplayName("Basic tests for the StackImplementation class.")
@TestMethodOrder(OrderAnnotation.class)
 public class StackTests 
{
    static StackInterface<Integer> stackToTest = null;
    static int stackSize = 10;
    static Random randomizer = null;
    static final int MAX_STACK_SIZE = 100;
    static Integer numberFromStack = null;

    /**
     * Initialize the test.
     */
    @Test
    @BeforeAll
    @DisplayName("Initializing the test data and stack object to test.")
    static void initializeStackImplementation() {
        randomizer = new Random();
        stackSize = randomizer.nextInt(MAX_STACK_SIZE);
        stackToTest = StackFactory.createIntegerStack(stackSize);
        assertNotNull(stackToTest, () -> "Could not create stack object to test. Implement StackBuilder.createIntegerStack().");
        assertEquals(stackSize, stackToTest.capacity(), () -> "Stack capacity must be what was requested in creating it.");
    }

    @Test
    @Order(1)
    @DisplayName("Test the empty stack behaviour.")
    void emptyStackTest() {
        // Test that count of just initialized stack is zero and pop returns null.
        assertEquals(0, stackToTest.count(), () -> "Expected stack to be empty, count() returning 0.");
        assertThrows(StackIsEmptyException.class, () -> stackToTest.pop(), "Expecting to get StackIsEmptyException when popping from empty stack.");
        assertThrows(StackIsEmptyException.class, () -> stackToTest.peek(), "Expecting to get StackIsEmptyException when peeking from empty stack");
        assertThrows(NullPointerException.class, () -> stackToTest.push(null), () -> "Must get NullPointerException when trying to push null to stack.");
    }

    @Test
    @Order(2)
    @DisplayName("Test filling the stack and emptying it using push and pop.")
    void pushPopStackTest() {
        // Create a random count to fill the stack to.
        int elementCount = randomizer.nextInt(stackSize);
        // Fill the list with test data.
        List<Integer> testData = fillWithTestData(elementCount);
        // Push the test data to the stack, asserting that push succeeded.
        for (Integer value : testData) {
            assertDoesNotThrow(() -> stackToTest.push(value), "In this test push must succeed, but push failed.");
        }
        // Check that the stack has the correct element of items.
        assertEquals(elementCount, stackToTest.count(), () -> "Stack must have the number of elements pushed into it.");
        // Pop elements from the stack and compare that the values are in the same order than in the test data list, reversed.
        int counter = testData.size() - 1;
        
        while (stackToTest.count() > 0) {
            assertDoesNotThrow( () -> numberFromStack = stackToTest.pop(), "Pop should not throw in this test, but it did.");
            assertNotNull(numberFromStack, () -> "Item popped from stack should not be null.");
            assertEquals(testData.get(counter), numberFromStack, () -> "Items popped must be in the order they were pushed into stack.");
            counter--;
        }
        // And since popping all out, test now that the stack is really empty.
        assertEquals(0, stackToTest.count(), () -> "After poppong all items, stack must be empty.");
        assertThrows(StackIsEmptyException.class, () -> stackToTest.pop(), "Pop must throw StackIsEmptyException if stack is empty.");
    }

    @Test
    @Order(3)
    @DisplayName("Try to put more items in the stack it should be able to hold.")
    void overFillStackTest() {
        // Reset the stack to be empty.
        stackToTest.reset();
        // Fill the stack to contain max number of items.
        List<Integer> testData = fillWithTestData(stackSize);
        for (Integer value : testData) {
            assertDoesNotThrow(() -> stackToTest.push(value), "In this test push must succeed, but push failed.");
        }
        // Stack should be now full so the next push must reallocate internal array and capacity should be increased.
        int oldCapacity = stackToTest.capacity();
        assertDoesNotThrow( () -> stackToTest.push(42), "Pushing to a full stack must not fail.");
        int newCapacity = stackToTest.capacity();
        assertTrue(newCapacity > oldCapacity, () -> "The capacity did not grow when it should have.");
        assertEquals(42, stackToTest.pop(), () -> "Last thing pushed was not popped from stack.");
        assertEquals(testData.get(testData.size()-1), stackToTest.pop(), () -> "Reallocated stack has no second to last element in place.");
    }

    @Test
    @Order(4)
    @DisplayName("Test resetting the stack so state is correct after reset.")
    void resetStackTest() {
        // Put something in the stack, then reset it and check if it is empty.
        stackToTest.reset();
        stackToTest.push(109);
        stackToTest.push(111);
        stackToTest.reset();
        assertThrows(StackIsEmptyException.class, () -> stackToTest.pop(), "Pop must throw StackIsEmptyException if stack is empty.");
        assertEquals(0, stackToTest.count(), () -> "After resetting a stack, count must return zero.");
    }

    @Test
    @DisplayName("Testing the peek method")
    void peekTest() {
        stackToTest.reset();
        stackToTest.push(109);
        stackToTest.push(111);
        assertEquals(111, stackToTest.peek(), () -> "Peek must return the last value put in");
        assertEquals(2, stackToTest.count(), () -> "After peeking, count must be the same as before.");
        stackToTest.reset();
        assertThrows(StackIsEmptyException.class, () -> stackToTest.peek(), "Peek must throw StackIsEmptyException if stack is empty.");
    }

    /**
     * Utility method to create a list with random test data.
     * @param itemCount Number of items to put into the testa data list.
     * @return A list of test data to use with the test stack.
     */
    private List<Integer> fillWithTestData(int itemCount) {
        List<Integer> testData = new ArrayList<Integer>();
        for (int count = 0; count < itemCount; count++) {
            testData.add(randomizer.nextInt(Integer.MAX_VALUE-1));
        }
        return testData;
    }
}
