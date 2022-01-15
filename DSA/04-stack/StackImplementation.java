package oy.tol.tra;

import java.lang.reflect.Array;

public class StackImplementation<E> implements StackInterface<E> {

   private E [] itemArray;
   private int capacity;
   private int currentIndex = -1;

   @Override
   public void init(Class<E> elementClass, int size) throws StackAllocationException {
      if (size < 2) {
         throw new StackAllocationException("Stack size should be greater than 1");
      }
      try {
         capacity = size;
         currentIndex = -1;
         itemArray = (E []) Array.newInstance(elementClass, capacity);   
      } catch (Exception e) {
         throw new StackAllocationException(e.getMessage());
      }
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null){
         throw new NullPointerException();
      }
      try {
         if(currentIndex == itemArray.length - 1){
            reallocate(capacity*2);
         }
         currentIndex++;
         itemArray[currentIndex] = element;
      } catch (Exception e){
         throw new StackAllocationException(e.getMessage());
      }
   }

   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex == -1){
         throw new StackIsEmptyException("Stack is empty.");
      }
      currentIndex--;
      return itemArray[currentIndex + 1];
   }

   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex == -1){
         throw new StackIsEmptyException("Stack is empty.");
      }
      return itemArray[currentIndex];
   }

   @Override
   public int count() {
      return currentIndex + 1;
   }

   @Override
   public void reset() {
      for(int i = 0; i < itemArray.length; i++){
         itemArray[i] = null;
      }
      currentIndex = -1;
   }

   @Override
   public boolean empty() {
      return currentIndex == -1;
   }

   @Override
   public void reallocate(int n){
      E[] tmp = (E[]) new Object[n];
      for(int i = 0; i < capacity; i++){
         tmp[i] = itemArray[i];
      }
      capacity = n;
      itemArray = tmp;
   }
}
