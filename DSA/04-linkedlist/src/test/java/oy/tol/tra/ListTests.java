package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

/*

/**
 * Unit tests for testing the list implementation.
 * 
 * DO NOT change anything here, just implement the listInterface, instantiate it in
 * listBuilder.createIntegerlist and perform the tests.
 */
@DisplayName("Basic tests for the listImplementation class.")
@TestMethodOrder(OrderAnnotation.class)
public class ListTests {
    static LinkedListInterface<String> listToTest = null;
    static int listSize = 10;
    static Random randomizer = null;
    static final int MAX_list_SIZE = 100;

    /**
     * Initialize the test.
     */
    @Test
    @BeforeAll
    @DisplayName("Initializing the test data and list object to test.")
    public static void initializelistImplementation() {
        randomizer = new Random();
        listToTest = ListFactory.createStringLinkedList();
        assertNotNull(listToTest,
                () -> "Could not create list object to test. Implement listBuilder.createIntegerlist().");
    }

    @Test
    @DisplayName("Test the empty list behaviour.")
    public void emptylistTest() {
        // Test that count of just initialized list is zero and pop returns null.
        assertEquals(0, listToTest.size(), () -> "Expected list to be empty, size() returning 0.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(-1), "Expecting to get exception when getting index -1.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(0), "Expecting to get exception when getting index 0 from empty list.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(5), "Expecting to get exception when getting index 5 from empty list.");
        assertThrows(NullPointerException.class, () -> listToTest.indexOf(null),
                "Expecting to get -1 when querying index of null from empty list");
        assertEquals(-1, listToTest.indexOf("Item"),
                () -> "Expecting to get -1 when querying index of something from empty list");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.add(-1, "Item"),
                "Expecting result false when trying to add to index < 0.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.add(3, "Item"),
                "Expecting result false when trying to add to index > 0 for an empty list.");
        assertFalse(listToTest.remove("Item"),
                () -> "Expecting to fail to remove an item from the list when it is empty");
        assertThrows(NullPointerException.class, () -> listToTest.remove(null),
                "Expecting to fail to remove null from the list when it is empty");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.remove(-1),
                "Expecting to fail to remove an item from index -1 of the list");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.remove(0),
                "Expecting to fail to remove an item from the beginning of the list when it is empty");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.remove(6),
                "Expecting to fail to remove an item from the list when it is empty");
        assertDoesNotThrow(() -> listToTest.add(0, "Adding to list"), "Adding to index 0 on an empty list should succeed");
        listToTest.reset();
    }

    @Test
    @DisplayName("Test filling the list and emptying it using add and remove.")
    public void getAndRemoveFromlistTest() {
        List<String> testData = fillWithTestData();
        // Push the test data to the list, asserting that push succeeded.
        listToTest.reset();
        for (String value : testData) {
            assertDoesNotThrow(() -> listToTest.add(value), "In this test add must succeed, but failed.");
        }
        // Check that the list has the correct element of items.
        assertEquals(testData.size(), listToTest.size(),
                () -> "list must have the number of elements pushed into it.");
        assertFalse(listToTest.remove("this-string-surely-is-not-in-the-list"),
                () -> "Does not remove an item not in the list");
        assertThrows(NullPointerException.class, () -> listToTest.remove(null), "Should not remove null.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.remove(listToTest.size()), "Cannot remove an index past the end of list.");
        // Get elements from the list and compare that the values are in the same order
        // than in the test data list.
        while (listToTest.size() > 0) {
            String itemFromlist = listToTest.get(0);
            assertNotNull(itemFromlist, () -> "Item from list should not be null.");
            assertEquals(testData.get(0), itemFromlist, () -> "Items must be in the order they were put into list.");
            listToTest.remove(0);
            testData.remove(0);
        }
        // And since popping all out, test now that the list is really empty.
        assertEquals(0, listToTest.size(), () -> "After removing all items, list must be empty.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(0), "get must throw after all items have been removed out.");
    }

    @Test
    @DisplayName("Test resetting the list so state is correct after reset.")
    public void resetlistTest() {
        // Put something in the list, then reset it and check if it is empty.
        listToTest.reset();
        assertEquals(0, listToTest.size(), () -> "After resetting a list, it has to have zero items.");
        listToTest.add("Item 1");
        listToTest.add("Item 2");
        listToTest.reset();
        assertEquals(0, listToTest.size(), () -> "After resetting a list, it has to have zero items.");
    }

    @Test
    @DisplayName("Testing the add and get methods")
    public void addAndGetTest() {
        listToTest.reset();
        assertDoesNotThrow(() -> listToTest.add("Item 1"), "Could not add item to the list");
        assertEquals(1, listToTest.size(), () -> "Count must equal the number of objects in the list.");
        assertDoesNotThrow(() -> listToTest.add("Item 2"), "Could not add item to the list");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.add(100, "Must fail"), "Adding to index beyound end must fail.");
        assertEquals("Item 1", listToTest.get(0), () -> "Get must return the correct value based on index.");
        assertEquals("Item 2", listToTest.get(1), () -> "Get must return the correct value based on index.");
        assertThrows(NullPointerException.class, () -> listToTest.indexOf(null),
                "Expecting exception when querying index of null");
        assertEquals(1, listToTest.indexOf("Item 2"), () -> "indexOf must return the correct index of an item.");
        assertEquals(2, listToTest.size(), () -> "Count must equal the number of objects in the list.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(-2), "Getting with negative index should fail.");
        assertThrows(IndexOutOfBoundsException.class, () -> listToTest.get(listToTest.size() + 1), "Getting an index too large must fail.");
        assertDoesNotThrow(() -> listToTest.add(0, "Item 3"), () -> "Must be able to add to the beginning of list.");
        assertEquals(0, listToTest.indexOf("Item 3"),
                () -> "indexOf must return the correct index of an item after add(index, element).");
        assertEquals(2, listToTest.indexOf("Item 2"),
                () -> "indexOf must return the correct index of an item after add(index, element).");
        assertDoesNotThrow(() -> listToTest.add(2, "Item 4"), () -> "Adding to the end of list using add(index, element) failed.");
        assertEquals(2, listToTest.indexOf("Item 4"),
                () -> "indexOf must return the correct index of an item after add(index, element).");
        assertEquals(3, listToTest.indexOf("Item 2"),
                () -> "indexOf must return the correct index of an item after add(index, element).");
        assertEquals(-1, listToTest.indexOf("non-existing-element"), () -> "Must return -1 for non-existent element.");
    }

    @Test
    @DisplayName("Testing removing items from the middle, leaving both ends.")
    public void removeFromMiddleTest() {
        List<String> testData = fillWithTestData();
        // Push the test data to the list, asserting that push succeeded.
        listToTest.reset();
        for (String value : testData) {
            assertDoesNotThrow(() -> listToTest.add(value), "In this test add must succeed, but failed.");
        }
        // Check that the list has the correct element of items.
        assertEquals(testData.size(), listToTest.size(),
                () -> "list must have the number of elements pushed into it.");
        // Get elements from the list and compare that the values are in the same order
        // than in the test data list.
        while (listToTest.size() > 2) {
            listToTest.remove(1);
        }
        // And since removing some, test now that the list has first and last element of
        // test data.
        assertEquals(testData.get(0), listToTest.get(0), () -> "Item must match the first in the test data.");
        assertEquals(testData.get(testData.size() - 1), listToTest.get(1),
                () -> "Item must match the last item in test data.");
    }

    /**
     * Utility method to create a list with random test data.
     * 
     * @param itemCount Number of items to put into the testa data list.
     * @return A list of test data to use with the test list.
     */
    private List<String> fillWithTestData() {
        List<String> testData = new ArrayList<String>();
        String[] italianCheeses = { "Caprino a coagulazione lattica", "Caprino a coagulazione presamica",
                "Caprino al lattice di fico", "Caprino al pepe di Bagnolo", "Chabri stagionato", "Charbonet", "Cherz",
                "Ciabutin", "Cimbro", "Formaggela della Val di Sabbia", "Formaggella della Val di Scalve",
                "Formaggella della Val Seriana", "Gorgonzola con la coda", "Gorgonzola bresciano",
                "Gorgonzola tipo piccante", "Nocciolino di ceva", "Nostrale d'alpe", "Pecorino della costa apuana",
                "Pecorino della Garfagnana", "Pecorino della Lunigiana", "Raveggiolo", "Ricotta affu micata di Mammola",
                "Robiola piemontese classica", "Valsevia ubriaca" };
        testData.addAll(0, Arrays.asList(italianCheeses));
        return testData;
    }
}
