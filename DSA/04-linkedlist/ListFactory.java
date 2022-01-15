package oy.tol.tra;

/**
 * This class creates different types of lists implementing the {@code LinkedListInterface} interface.
 * 
 * @author Antti Juustila
 */
public class ListFactory {
   /**
    * Creates an instance of ListImplementation for String type.
    * @return The list object.
    */
   public static LinkedListInterface<String> createStringLinkedList() {
      return new LinkedListImplementation<>();
   }
}
