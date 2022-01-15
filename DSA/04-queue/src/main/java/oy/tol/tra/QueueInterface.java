package oy.tol.tra;

/**
 * A generic interface to queue class. Queues work following 
 * the first-in-first-out principle.
 */
public interface QueueInterface<E> {
   /**
    * Initializes the queue with a count of elements it can hold initially.
    * <p>
    * Must implement an internal data storage for the elements, an array of elements E.
    * Initialize the queue like this (for holding Integers): {@code queue.init(Integer.class, size)};
    * <p>
    * Make sure to check tha the size parameter is sensible, e.g. >= 2.
    * @param elementClass The type of things to store in the queue.
    * @param size The max number of elements the queue data structure can hold before reallocating room for more.
    * @throw QueueAllocationException If the allocation for the queue failed.
    */
   public void init(Class<E> elementClass, int size) throws QueueAllocationException;

   /**
    * For querying the current capacity of the queue.
    @return The number of elements the queue can currently hold.
    */
   public int capacity();
   
   /**
    * Add an element to the queue.
    * @param element The element to add, must not be null.
    * @return True if was able to add the item to queue, otherwise false.
    * @throw QueueAllocationException If the reallocation for the queue failed in case queue needs reallocation.
    * @throw NullPointerException If the element is null.
    */
   public void enqueue(E element) throws QueueAllocationException, NullPointerException;

   /**
    * Removes an element from the queue.
    * @return The element from the head of the queue.
    * @throws QueueIsEmptyException If the queue is empty.
    */
   public E dequeue() throws QueueIsEmptyException;

   /**
    * Returns the element at the head of the queue, not removing it from the queue.
    * @return The element in the head of the queue.
    * @throws QueueIsEmptyException If the queue is empty.
    */
   public E element() throws QueueIsEmptyException;
   /**
    * Dequeues elements and enqueues them to a new array
    * @return new array
    * @throws QueueIsEmptyException
    */
    public void reallocateArray(int n);
   /**
    * Returns the count of elements currently in the queue.
    * @return Count of elements in the queue.
    */
   public int count();

   /**
    * Can be used to check if the queue is empty.
    * @return True if the queue is empty, false otherwise.
    */
   public boolean empty();

   /**
    * Resets the queue to empty state. State of the queue should be as if it was just created.
    */
   public void reset();

   /**
    * Returns the queue as a string in the format "[1, 2, 3, 4, 5]", where 1 is the next
    * element to take out from the queue (head), and 5 was the element most recenty entered
    * into the queue (tail). If the queue is empty, returns "[]". Must not print the "empty"
    * slots in the queue, just the elements between head and tail, in that order.
    * Note that the internal array may at some point have the elements there like this (an
    * example with ints, array size is 10):
    *  0   1  2 3 4 5 6 7 8  9
    * [11,12,13,_,_,_,_,8,9,10]
    *           ^       ^
    *         tail     head
    * User has added numbers from 1 onwards and removed some (1...7) while also adding numbers from 8...13.
    * In the example above, this method should return "[8, 9, 10, 11, 12, 13]"
    @return The queue as a string.
    */
    public String toString();
}
