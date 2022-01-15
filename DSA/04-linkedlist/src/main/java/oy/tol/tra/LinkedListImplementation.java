package oy.tol.tra;

import javax.lang.model.element.Element;

public class LinkedListImplementation<Element> implements LinkedListInterface<Element> {

   private class Node<E> {
      Node(E data) {
         element = data;
         next = null;
      }
      E element;
      Node<E> next;
   }

   private Node<Element> head = null;
   private int size = 0;

   @Override
   public void add(Element element) throws NullPointerException, LinkedListAllocationException {
      if (element == null){ throw new NullPointerException(); }
      Node<Element> newNode = new Node<>(element);

      try {
         if (head == null){
            head = newNode;
         } else {
            Node<Element> tmp = head;
            while (tmp.next != null){
               tmp = tmp.next;
            }
            tmp.next = newNode;
         }
      } catch (LinkedListAllocationException e) {
         throw new LinkedListAllocationException(e.getMessage());
      }
      size++;
   }

   @Override
   public void add(int index, Element element) throws NullPointerException, LinkedListAllocationException, IndexOutOfBoundsException {
      if (element == null){
         throw new NullPointerException();
      } else if (index > size || index < 0){
         throw new IndexOutOfBoundsException();
      }
      Node<Element> newNode = new Node<Element>(element);
      try {
         if (head == null || index == 0){
            newNode.next = head;
            head = newNode;
         } else {
            Node <Element> tmp = head;
            while(--index > 0){
               tmp = tmp.next;
            }
            newNode.next = tmp.next;
            tmp.next = newNode;
         }
         } catch (LinkedListAllocationException e) {
            throw new LinkedListAllocationException(e.getMessage());
      }
      size++;
   }

   @Override
   public boolean remove(Element element) throws NullPointerException {
      if (element == null){
         throw new NullPointerException();
      } else if (head == null){
         return false;
      }
      Node<Element> tmp = head;
      while (tmp.next != null){
         if (tmp.next.element == element){
            tmp.next = tmp.next.next;
            size--;
            return true;
         } else {
            tmp = tmp.next;
         }
      }
      return false;
   }

   @Override
   public Element remove(int index) throws IndexOutOfBoundsException {
      if (index >= size() || index < 0){
         throw new IndexOutOfBoundsException();
      }
      Node<Element> curr = head;
      Node<Element> prev = null;
      if(index == 0){
         head = curr.next;
         size--;
         return curr.element;
      }
      int count = 0;
      while(count < index){
         prev = curr;
         curr = curr.next;
         count++;
      }
      prev.next = curr.next;
      size--;
      return curr.element;
   }

   @Override
   public Element get(int index) throws IndexOutOfBoundsException {
      if (index < 0 || index >= size()){
         throw new IndexOutOfBoundsException();
      }
      if (index == 0 && head != null){
         return head.element;
      }

      Node<Element> tmp = head;
      int count = 0;
      while (tmp != null){
         if(count == index){
            return tmp.element;
         }
         count++;
         tmp = tmp.next;
      }
      return null;
   }

   @Override
   public int indexOf(Element element) {
      if (element == null){
         throw new NullPointerException();
      } else if (size <= 0){
         return -1;
      }
      int count = 0;
      Node<Element> tmp = head;
      while(tmp != null){
         if (tmp.element.equals(element)){
            return count;
         }
         tmp = tmp.next;
         count++;
         }
      return -1;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public void reset() {
      head = null;
      size = 0;
   }

   @Override
   public void reverse() {
      Node<Element> prev = null;
      Node<Element> curr = head;

      while(curr != null){
         Node<Element> next = curr.next;
         curr.next = prev;
         prev = curr;
         curr = next;
      }
      head = prev;
   }
   
}
