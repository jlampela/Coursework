package com.tol.tira.graph;

/**
 * Vertex is the node of a graph. This generic class
 * holds the actual vertex data object. For example, in a
 * graph having the train network, vertices would be stations
 * and edges would be the train tracks.
 * 
 * The data object T must implement the Comparable interface.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Vertex<T extends Comparable<T>> {
   
   /** The generic data element of the vertex. */
   private T element = null;

   /**
    * Constructs a vertex with the data element.
    * @param element The data to put inside the vertex.
    */
   Vertex(T element) {
      assert(element != null);
      this.element = element;
   }

   /**
    * Gets the value object inside the element.
    * @return The element inside the vertex.
    */
   public T getElement() {
      return element;
   }

   /**
    * Provides a string representation of the vertex.
    * @return The vertex element as string.
    */
   @Override
   public String toString() {
      return element.toString();
   }

   /**
    * Calculates a hash for the vertex using the element.
    */
   @Override
   public int hashCode() {
      return element.hashCode();
   }

   /**
    * Checks wheter two vertices are similar.
    * @param another The another object to compare this to.
    * @return Returns true if the vertices are identical (their elements match).
    */
   @SuppressWarnings("unchecked")
   @Override
   public boolean equals(Object another) {
      if (another == null) {
         return false;
      }
      if (another instanceof Vertex<?>) {
         return this.element.equals(((Vertex<T>)another).element);
      }
      return false;
   }

};
