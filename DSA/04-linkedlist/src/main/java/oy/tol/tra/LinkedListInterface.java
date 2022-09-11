package oy.tol.tra;

/**
 * A generic interface to linked list class.
 */
public interface LinkedListInterface<Element> {
   
   /**
    * Add an element to the end of the list.
    * @param element The element to add, must not be null.
    * @throws NullPointerException If the parameter element is null.
    * @throws LinkedListAllocationException If failed to allocate a new list element.
    */
   public void add(Element element) throws NullPointerException, LinkedListAllocationException;

   /**
    * Add an element to the specified index in the list.
    * @param index The index where to add the element, must be 0...count().
    * @param element The element to add, must not be null.
    * @throws NullPointerException If the parameter element is null.
    * @throws LinkedListAllocationException If failed to allocate a new list element.
    * @throws IndexOutOfBoundsException If the specified index to the list is out of bounds.
    */
    public void add(int index, Element element) throws NullPointerException, LinkedListAllocationException, IndexOutOfBoundsException;

   /**
    * Removes an element from the list. Element must not be null.
    * @return True if element was found and removed, false otherwise.
    * @throws NullPointerException If the parameter element is null.
    */
    public boolean remove(Element element) throws NullPointerException;

   /**
    * Removes an element from the list.
    * @param index The index of the element to remove.
    * @return True if element was found and removed, false otherwise.
    * @throws IndexOutOfBoundsException If the specified index to the list is out of bounds.
    */
    public Element remove(int index) throws IndexOutOfBoundsException;

    /**
    * Returns the element at the index, not removing it from the list.
    * If index < 0 or >= size(), throws IndexOutOfBoundsException.
    * @return The element in the specified index.
    * @throws IndexOutOfBoundsException If the specified index to the list is out of bounds.
    */
   public Element get(int index) throws IndexOutOfBoundsException;

   /**
    * Queries the index of the element in the list.
    * @param element The element to search for, must not be null.
    * @return The index of the element, or -1 if it was not found.
    * @throws NullPointerException If the parameter element is null.
    */
   public int indexOf(Element element) throws NullPointerException;

   /**
    * Returns the count of elements currently in the list.
    * @return Count of elements in the list.
    */
   public int size();

   /**
    * Resets the list to an empty list.
    */
   public void reset();

   /**
    * Reverses the items in the list to opposite order.
    * Reversal happens in place; so the old order in this list is reversed.
    */
    public void reverse();
}
