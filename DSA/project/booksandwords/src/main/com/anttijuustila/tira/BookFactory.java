package com.anttijuustila.tira;

/**
 * This class is used to create an instance of your implementation of the Book interface.
 * <p>
 * Implement the <code>createBook()</code> method to return your instance of the Book interface.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public final class BookFactory {
    private BookFactory() {
    }

    /**
     * Concrete class implementing the Book interface.
     * @return Your implementation of the Book interface.
     */
    public static Book createBook() {
        return new BookImplementation();
    }
}
