package oy.tol.tra;

/**
 * This class instantiates a queue implementing the {@code QueueInterface}.
 * 
 * @author Antti Juustila
 */
public class QueueFactory {

   private QueueFactory() {
   }

   /**
    * Creates an instance of QueueInterface for Integer type.
    * @param size Number of elements the queue can hold.
    * @return The queue object.
    */
   public static QueueInterface<Integer> createIntegerQueue(int size) {
      QueueImplementation<Integer> queue = new QueueImplementation<>();
      queue.init(Integer.class, size);
      return queue;
   }

}
