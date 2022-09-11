package oy.tol.tra;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class to create larger and larger arrays of int and permutating the contents of the arrays.
 * 
 * @author Antti Juustila
 */
public class PermutationArray {

   // Run from terminal:
   // java -Xms4g -Xmx16g -jar target/03-permutation-1.0-SNAPSHOT-jar-with-dependencies.jar
   public static void main(String[] args) {
      try {
         System.out.println("NOTE: Running this app will take __a long time__ , be patient...");
         OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
         String osName = os.getName();
         int processors = os.getAvailableProcessors();
         String architecture = os.getArch();
         String osVersion = os.getVersion();
         System.out.print("OS: " + osName + " " + osVersion + " - " + architecture + " - processors: " + processors);
         System.out.format(Locale.getDefault(), " - JVM heap size: %,15d\n", Runtime.getRuntime().totalMemory());
         System.out.format(Locale.getDefault(), "%15s\t%10s\n", "Array size", "ms");
         for (int counter = 100000; /* intentionally empty */; counter *= 2) {
            if (counter > 0) {
               fillArray(counter);
            } else {
               System.out.println("Integer overflow, Java only allows int (signed 32bit) as array index.");
               System.out.println("Trying out with 2 000 000 000...");
               fillArray(2000000000);
               System.out.println("Trying out with Integer.MAX_VALUE..." + Integer.MAX_VALUE);
               fillArray(Integer.MAX_VALUE);
               break;
            }
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

   private static void fillArray(int size) {
      int[] randomArray = new int[size];
      long start = System.currentTimeMillis();

      for(int i=0; i<=size-1; i++){
         randomArray[i] = i + 1;
      }
      for(int i=0; i<=size-1; i++){
         int x = ThreadLocalRandom.current().nextInt(0,size-1);
         int tmp = randomArray[i];
         randomArray[i] = randomArray[x];
         randomArray[x] = tmp;
      }
      
      
      // End of your implementation, don't change code below this line.
      long duration = System.currentTimeMillis() - start;
      System.out.format(Locale.getDefault(), "%,15d\t%,10d\n", size, duration);
      randomArray = null;
   }
}
