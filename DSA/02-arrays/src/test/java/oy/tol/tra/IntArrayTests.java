package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing the IntArray.")
public class IntArrayTests {
   
   @Test
   @DisplayName("Testing the IntArray.reverse()") 
   void reverseTest() {
      Integer [] testArray = readArrayFrom("arraytoreverse.txt");

      List<Integer> list = new ArrayList<Integer>(Arrays.asList(testArray));
      Collections.reverse(list);
      Integer [] expectedReversedArray = new Integer [list.size()];
      list.toArray(expectedReversedArray);
      
      IntArray toTest = new IntArray(testArray);
      toTest.reverse();
      System.out.println("testArray: " + Arrays.toString(testArray));
      System.out.println("Reversed:  " + Arrays.toString(toTest.getArray()));
      assertTrue(Arrays.equals(expectedReversedArray, toTest.getArray()), () -> "Reversed array is not correct!");
      System.out.println("-- Reverse test finished");
   }

   @Test
   @DisplayName("Testing the IntArray.sort()")
   void sortTest() {
      Integer [] testArray = readArrayFrom("arraytosort.txt");
      
      List<Integer> list = new ArrayList<Integer>(Arrays.asList(testArray));
      Collections.sort(list);
      Integer [] correctlySorteddArray = new Integer [list.size()];
      list.toArray(correctlySorteddArray);

      IntArray toTest = new IntArray(testArray);
      toTest.sort();
      System.out.println("testArray: " + Arrays.toString(testArray));
      System.out.println("Sorted:  " + Arrays.toString(toTest.getArray()));
      assertTrue(Arrays.equals(correctlySorteddArray, toTest.getArray()), () -> "Sorted array is not correct!");
      System.out.println("-- Sort test finished");
   }

   Integer [] readArrayFrom(String fileName) {
      Integer [] numberArray = null;
      try {
         String fullPath = getFullPathToTestFile(fileName);
         FileReader fileReader = new FileReader(new File(fullPath), StandardCharsets.UTF_8);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         String line;
         if ((line = bufferedReader.readLine()) != null) {
             String[] items = line.split(",");
             if (items.length > 0) {
               numberArray = new Integer[items.length];
               int counter = 0;
               for (String item: items) {
                  numberArray[counter++] = Integer.parseInt(item);
               }
             }
         }
         fileReader.close();
      } catch (IOException e) {
         System.out.println("Cannot read test file " + fileName + " since: " +e.getMessage());
      }
      return numberArray;
   }

   private String getFullPathToTestFile(String fileName) {
      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource(fileName).getFile());
      return file.getAbsolutePath();
  }

}
