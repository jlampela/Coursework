package oy.tol.tra;

import java.util.Arrays;

/**
 * For finding the mode of a number from an array of integers.
 * 
 * Usage: Mode.Result foundMode = Mode.findMode(array);
 * 
 * Result contains the number most often found in the array, as well as the
 * count of the said number.
 */
public class Mode {

   public static class Result {
      public int number = 0;
      public int count = 0;
   }

   private Mode() {
      // Empty
   }

   /**
    * Finds the mode of the specified array, -1 if one could not be found. Mode
    * means the number most often found in the array.
    * 
    * @return The Result, containing the mode number and the count how many time it
    *         was in the array.
    */
   public static Result findMode(int[] array) {
      Result result = new Result();
      result.number = -1;
      result.count = -1;

      /* Arrays.sort() time complexity is O(n*log(n)) and my algorithm is done
      in O(n) so it's time complexity is O(n) + O(n*log(n)) => O(n*log(n)) is time
      complexity because of O(n*log(n)) is the dominant term */

      if(array == null || array.length < 2){ //If array is null returns or only 1 value -> result = -1
         return result;
      }

      Arrays.sort(array);
      int tmp = 1;
      for(int i = 0; i<array.length-1; i++){ //Iterates the array
         if(array[i] == array[i+1]){ //Checks if current index is same as the next index if so adds 1 
            tmp++;
            if (tmp > result.count){ // Checks if larger mode 
               result.number = array[i]; //Mode
               result.count = tmp; //Number of times mode value occurs
            }
         } else { //Resets count for new number
            tmp = 1;
         }
      }
      return result;
   }
}