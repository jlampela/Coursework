package oy.tol.tra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Testing basic functionality of the list reverse().")
public class ReorderListTests {

   @Test
   @DisplayName("Basic test for the linked list reverse()")
   void basicTests() {
      // Create an empty list.
      LinkedListInterface<String> linkedListToTest = ListFactory.createStringLinkedList();
      // Reversing should not throw
      assertDoesNotThrow(() -> linkedListToTest.reverse(), () -> "Reversing empty linked list should not throw");
      linkedListToTest.add("String 1");
      assertDoesNotThrow(() -> linkedListToTest.reverse(), () -> "Reversing linked list with one element should not throw");
      linkedListToTest.add("String 2");
      assertDoesNotThrow(() -> linkedListToTest.reverse(), () -> "Reversing linked list with two element should not throw");
      assertEquals("String 2", linkedListToTest.get(0), () -> "String 2 must be in the beginning after reverse");
      assertEquals("String 1", linkedListToTest.get(1), () -> "String 1 must be in the end after reverse");
   }

   @Test
   @DisplayName("Testing the reverse, comparing performance to Java ArrayList and LinkedList.")
   void reorderListTest() {
      try {
         List<String> originalListOfGrapes = fillWithTestData("ArrayList");
         List<String> javaLinkedList = fillWithTestData("LinkedList");
         LinkedListInterface<String> linkedListToTest = ListFactory.createStringLinkedList();
         long start = System.nanoTime();
         for (String item : originalListOfGrapes) {
            assertDoesNotThrow(() -> linkedListToTest.add(item.trim()), "Could not add item to list");
         }
         long duration = System.nanoTime() - start;
         System.out.format("Your linked list: adding grapes took\t%10d ns.\n", duration);
         assertEquals(originalListOfGrapes.size(), linkedListToTest.size(), () -> "Elements got lost in translation!");
         System.out.println("Linked list has " + linkedListToTest.size() + " items.");
         // Benchmarking reverse with Java ArrayList
         start = System.nanoTime();
         Collections.reverse(originalListOfGrapes);
         duration = System.nanoTime() - start;
         System.out.format("Java ArrayList: reverse took\t\t%10d ns%n", duration);
         // Benchmarking reverse with Java LinkedList
         start = System.nanoTime();
         Collections.reverse(javaLinkedList);
         duration = System.nanoTime() - start;
         System.out.format("Java LinkedList: reverse took\t\t%10d ns%n", duration);
         // Benchmarking reverse with student linked list implementation
         start = System.nanoTime();
         linkedListToTest.reverse();
         duration = System.nanoTime() - start;
         System.out.format("Your linked list: reverse took\t\t%10d ns%n", duration);
         assertEquals(originalListOfGrapes.size(), linkedListToTest.size(), () -> "Elements got lost in translation!");
         // Benchmarking get using index from containers.
         // Java ArrayList
         start = System.nanoTime();
         String grape1 = originalListOfGrapes.get(originalListOfGrapes.size()-1);
         duration = System.nanoTime() - start;
         System.out.println("Getting grape " + grape1 + " using index -- get(int) method -- from containers...");
         System.out.format("Java ArrayList: getting grape took\t%10d ns.\n", duration);
         // Java LinkedList
         start = System.nanoTime();
         grape1 = javaLinkedList.get(originalListOfGrapes.size()-1);
         duration = System.nanoTime() - start;
         System.out.format("Java LinkedList: getting grape took\t%10d ns.\n", duration);
         // Student linked list
         start = System.nanoTime();
         String grape2 = linkedListToTest.get(linkedListToTest.size()-1);
         duration = System.nanoTime() - start;
         assertNotNull(grape2, () -> "Should get the grape from the linked list");
         assertEquals(grape1, grape2, () -> "Grapes in same indexes should match");
         System.out.format("Your linked list: getting grape took\t%10d ns.\n", duration);
         // Benchmarking indexOf using element from containers.
         // Java ArrayList
         start = System.nanoTime();
         String toSearch = new String("Adalmiina");
         int index = originalListOfGrapes.indexOf(toSearch);
         duration = System.nanoTime() - start;
         System.out.println("Getting grape Sciacarello -- using indexOf(String) -- from containers...");
         System.out.format("Java ArrayList: getting grape took\t%10d ns.\n", duration);
         // Java LinkedList
         start = System.nanoTime();
         index = javaLinkedList.indexOf(toSearch);
         duration = System.nanoTime() - start;
         System.out.format("Java LinkedList: getting grape took\t%10d ns.\n", duration);
         // Student linked list
         start = System.nanoTime();
         index = linkedListToTest.indexOf(toSearch);
         duration = System.nanoTime() - start;
         assertNotNull(grape2, () -> "Should get the grape from the linked list");
         assertEquals(grape1, grape2, () -> "Grapes in same indexes should match");
         System.out.format("Your linked list: getting grape took\t%10d ns.\n", duration);
         System.out.println("-----------------------------------------------------------------------------------");
         System.out.println("*** COMPARE the execution times between your linked list and the Java ArrayList ***");
         System.out.println("-----------------------------------------------------------------------------------");

         // System.out.println("Printing out reversed linked list: ");
         for (index = 0; index < originalListOfGrapes.size(); index++) {
            assertEquals(originalListOfGrapes.get(index), linkedListToTest.get(index), () -> "List reversed is not equal to original list reversed.");
            // System.out.print(linkedListToTest.get(index) + ", ");
         }
      } catch (IOException e) {
         fail("Cannot execute the test due to " + e.getMessage());
      }   
   }

   /**
    * Utility method to create a list with random test data.
    * 
    * @param itemCount Number of items to put into the testa data list.
    * @return A list of test data to use with the test list.
    * @throws IOException
    */
   private List<String> fillWithTestData(String whichJavaList) throws IOException {
      String toCheck;
      toCheck = new String(getClass().getClassLoader().getResourceAsStream("Grapes.txt").readAllBytes());
      List<String> testData = null;
      if ("ArrayList".equalsIgnoreCase(whichJavaList)) {
         testData = new ArrayList<String>();
      } else if ("LinkedList".equalsIgnoreCase(whichJavaList)) {
         testData = new LinkedList<String>();
      } else {
         throw new IOException("Not supported");
      }
      String[] grapes = toCheck.split("/|\\r?\\n");
      long start = System.nanoTime();
      for (String str : grapes) {
         testData.add(str.trim());
      }
      long duration = System.nanoTime() - start;
      System.out.format("Java %10s : adding grapes took\t%10d ns.\n", whichJavaList, duration);
      return testData;
  }
}
