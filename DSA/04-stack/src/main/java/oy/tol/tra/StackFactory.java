package oy.tol.tra;

/**
 * This class instantiates different types of stacks implementing the {@code StackInterface} interface.
 * <p>
 * objects in the TASK-2 of this exercise.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class StackFactory {

   private StackFactory() {
   }

   /**
    * Creates an instance of StackImplementation for Integer type.
    * @param size Number of elements the stack can hold.
    * @return The stack object.
    */
   public static StackInterface<Integer> createIntegerStack(int size) {
      StackImplementation<Integer> stack = new StackImplementation<>();
      stack.init(Integer.class, size);
      return stack;
   }

   /**
    * Instantiates a stack of Characters.
    * @param size The initial size for the new stack.
    * @return The stack implementation holding Characters.
    */
   public static StackInterface<Character> createCharacterStack(int size) {
      StackImplementation<Character> stack = new StackImplementation<>();
      stack.init(Character.class, size);
      return stack;
   }
}
