package oy.tol.tra;

/**
 * An interface to stack class holding elements of type E.
 */
public interface StackInterface<E> {

   /**
    * Initializes the stack with a count of elements it can hold initially.
    * <p>
    * You must implement an internal data storage for the elements, an array of elements E.
    * Initialize the stack like this (for holding Integers): {@code stack.init(Integer.class, size)};
    * @param elementClass The type of things to store in the stack.
    * @param size The number of elements the stack data structure can initially hold. MUST be > 1.
    * @throws StackAllocationException if stack could not be allocated.
    */
   public void init(Class<E> elementClass, int size) throws StackAllocationException;

   /**
    * For querying the capacity of the stack.
    @return The number of elements the stack can currently hold.
    */
   public int capacity();
   
   /**
    * Push an element to the stack.
    * <p>
    * If the internal array does not have enough room for the element, the implementation MUST
    * first reallocate an array, copy the elements from the old array and then replace the old
    * array with the new array. The capacity of the array must be updated too.
    * @param element The element to push, must not be null.
    * @throws StackAllocationException if no additional room can be allocated for the stack
    * @throws NullPointerException if the element pushed is null.
    */
   public void push(E element) throws StackAllocationException, NullPointerException;

   /**
    * Pops an element out of the stack, removing it from the internal data storage.
    * @return The popped element.
    * @throws StackIsEmptyException if the stack is empty.
    */
   public E pop() throws StackIsEmptyException;

   /**
    * Returns the element at the top of the stack, not removing it from the stack.
    * @return The element.
    * @throws StackIsEmptyException if the stack is empty.
    */
   public E peek() throws StackIsEmptyException;

   /**
    * Returns the count of elements currently in the stack.
    * @return Count of elements in the stack.
    */
   public int count();

   /**
    *  Use to check if the stack is empty.
    * @return Returns true if the stack is empty, false otherwise.
    */
    public boolean empty();

   /**
    * Resets the stack. State of the stack should be as if it was just created and empty.
    */
   public void reset();

   /**
    * Reallocates the stack. Doubles the capacity of the original stack.
    */
   public void reallocate(int n);
}
