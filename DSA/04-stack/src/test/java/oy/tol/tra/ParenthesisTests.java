package oy.tol.tra;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for checking if structured parenthesis in a string match correctly.
 * 
 */
@DisplayName("Testing the stack with two structured files with parentheses.")
public class ParenthesisTests {

   static StackInterface<Character> stackToTest = StackFactory.createCharacterStack(100);
   static int result = 0;

   /**
    * This test should not fail since the source file {@code SSN.java} is correct.
    */
   @Test
   @DisplayName("Java file with correct parentheses, should pass the test.")
   void correctJavaParenthesisTest() {
      try {
         String toCheck;
         toCheck = new String(getClass().getClassLoader().getResourceAsStream("SSN.java").readAllBytes());
         assertDoesNotThrow(() -> result = ParenthesisChecker.checkParentheses(stackToTest, toCheck),
               "SSN.java is valid so must not throw");
         assertEquals(72, result, () -> "Parentheses count did not match with expected.");
      } catch (IOException e) {
         fail("Cannot read the test file");
      }
   }

   /**
    * This test must fail since the file to analyse {@code Person.json} is not
    * valid JSON.
    */
   @Test
   @DisplayName("JSON file with incorrect parentheses should fail the test.")
   void incorrectJSONParenthesisTest() {
      try {
         String toCheck;
         toCheck = new String(getClass().getClassLoader().getResourceAsStream("Person.json").readAllBytes());
         assertThrows(ParenthesesException.class, () -> result = ParenthesisChecker.checkParentheses(stackToTest, toCheck),
               "Person.json is invalid JSON so must throw");
      } catch (IOException e) {
         fail("Cannot read the test file");
      }
   }

   @Test
   @DisplayName("Tests random text with random number of different parentheses")
   void randomCorrectParenthesesTest() {
      final String opening = new String("({[");
      final String closing = new String(")}]");
   }

}
